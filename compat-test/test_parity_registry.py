import importlib.util
import json
import sys
import tempfile
import unittest
from pathlib import Path
from types import SimpleNamespace
from unittest.mock import patch


MODULE = Path(__file__).with_name("parity_registry.py")
SPEC = importlib.util.spec_from_file_location("parity_registry", MODULE)
registry = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(registry)
sys.path.insert(0, str(MODULE.parent))
HARNESS_SPEC = importlib.util.spec_from_file_location("compat_test", MODULE.with_name("compat_test.py"))
harness = importlib.util.module_from_spec(HARNESS_SPEC)
HARNESS_SPEC.loader.exec_module(harness)


class ParityRegistryTest(unittest.TestCase):
    def testHARN003_UnitPositive_ErrorSignatureUsesClassAndLegacyOrigin(self):
        signatures = registry.error_signatures([
            "java.lang.NullPointerException: boom\n"
            "  at dev.robocode.tankroyale.bridge.BotPeer.dispatch(BotPeer.java:10)\n"
            "  at legacy.Bot.onScannedRobot(Bot.java:20)"
        ])
        self.assertEqual(
            [{"exception": "java.lang.NullPointerException", "origin": "legacy.Bot.onScannedRobot"}],
            signatures,
        )

    def testHARN003_UnitPositive_EngineFramesDoNotObscureLegacyOrigin(self):
        signatures = registry.error_signatures([
            "java.lang.SecurityException: stream limit\n"
            "  at net.sf.robocode.host.io.RobotFileSystemManager.addStream(RobotFileSystemManager.java:66)\n"
            "  at robocode.RobocodeFileOutputStream.<init>(RobocodeFileOutputStream.java:80)\n"
            "  at amk.ChumbaMini.saveData(ChumbaMini.java:186)"
        ])
        self.assertEqual(
            [{"exception": "java.lang.SecurityException", "origin": "amk.ChumbaMini.saveData"}],
            signatures,
        )

    def testHARN003_UnitNegative_SameExceptionFromDifferentCallbackIsDifferent(self):
        classic = [{"exception": "java.lang.NullPointerException", "origin": "legacy.Bot.run"}]
        tank = [{"exception": "java.lang.NullPointerException", "origin": "legacy.Bot.onScannedRobot"}]
        difference = registry.compare_errors(classic, tank)
        self.assertEqual(classic, difference["classic_only"])
        self.assertEqual(tank, difference["tank_royale_only"])

    def testHARN003_UnitPositive_UnknownOriginMatchesKnownOriginForSameException(self):
        classic = [{"exception": "java.io.NotSerializableException", "origin": "legacy.Bot.onScannedRobot"}]
        tank = [{"exception": "java.io.NotSerializableException", "origin": "unknown"}]
        difference = registry.compare_errors(classic, tank)
        self.assertEqual([], difference["classic_only"])
        self.assertEqual([], difference["tank_royale_only"])

    def testHARN003_UnitNegative_UnknownBridgeExceptionRemainsDiscrepant(self):
        tank = [{"exception": "java.lang.IllegalStateException", "origin": "unknown"}]
        difference = registry.compare_errors([], tank)
        self.assertEqual(tank, difference["tank_royale_only"])

    def testHARN003_UnitNegative_UnknownOriginDoesNotHideKnownOriginDifference(self):
        classic = [
            {"exception": "java.io.NotSerializableException", "origin": "unknown"},
            {"exception": "java.io.NotSerializableException", "origin": "legacy.Bot.run"},
        ]
        tank = [{"exception": "java.io.NotSerializableException", "origin": "legacy.Bot.onScannedRobot"}]
        difference = registry.compare_errors(classic, tank)
        self.assertEqual([classic[1]], difference["classic_only"])
        self.assertEqual(tank, difference["tank_royale_only"])

    def testHARN001_UnitPositive_StateSyncAppendsWithoutReplacingEarlierObservation(self):
        state = {"robots": {"roborumble/a.Bot_1.0.jar": {
            "status": "PASS", "delta_pct": 1.0, "completed_at": "2026-09-09T00:00:00Z",
            "setup": {"team": False}, "rc": {}, "tr": {},
        }}}
        data = {"schema_version": 1, "subjects": {}}
        manifest = {"bridge_commit": "abc"}
        self.assertEqual(1, registry.sync_state(data, state, Path("missing"), manifest))
        self.assertEqual(0, registry.sync_state(data, state, Path("missing"), manifest))
        self.assertEqual("PASS", data["subjects"]["roborumble/a.Bot_1.0.jar"]["status"])

    def testHARN001_UnitPositive_NormalCheckpointSyncDoesNotReplayAccumulatedProgress(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            state_file = root / "test_progress.json"
            state_file.write_text(json.dumps({
                "version": 1,
                "settings": {},
                "robots": {"roborumble/prior.Bot_1.0.jar": {
                    "status": "PASS", "completed_at": "2026-09-10T00:00:00Z",
                    "setup": {}, "rc": {}, "tr": {},
                }},
            }), encoding="utf-8")
            current_jar = root / "roborumble" / "current.Bot_1.0.jar"
            current_jar.parent.mkdir()
            current_jar.write_bytes(b"current")
            captured = []

            def result():
                return {
                    "ok": True, "score": 1.0, "scores": [1.0], "error_count": 0,
                    "elapsed": 0.0, "errors": [], "log_text": "", "selected": "current.Bot",
                }

            with patch.object(harness, "STATE_FILE", state_file), \
                    patch.object(harness, "discover_jars", return_value=[("roborumble", current_jar)]), \
                    patch.object(harness, "check_prerequisites"), \
                    patch.object(harness, "division_setup", return_value={}), \
                    patch.object(harness, "run_rc_battle", side_effect=lambda *args, **kwargs: result()), \
                    patch.object(harness, "run_tr_battle", side_effect=lambda *args, **kwargs: result()), \
                    patch.object(harness, "write_error_log", return_value=False), \
                    patch.object(harness, "evaluate", return_value=("PASS", 0.0)), \
                    patch.object(harness, "regenerate_report"), \
                    patch.object(harness, "sync_parity_registry",
                                 side_effect=lambda checkpoint, opts: captured.append(checkpoint)), \
                    patch.object(sys, "argv", ["compat_test.py", "--collections", "roborumble", "--limit", "1"]):
                self.assertEqual(0, harness.main())

        self.assertEqual(
            {"roborumble/current.Bot_1.0.jar"},
            set(captured[0]["robots"]),
        )

    def testHARN001_UnitPositive_TeamMemberDirsReadWindows1252Metadata(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            team_dir = root / "legacy.Team_1.0"
            team_dir.mkdir()
            member_dir = root / "membre-é"
            member_dir.mkdir()
            (team_dir / "legacy.Team_1.0.json").write_bytes(
                json.dumps({"teamMembers": [member_dir.name]}).encode("cp1252"))

            self.assertEqual([member_dir], harness.team_member_dirs(team_dir))

    def testHARN008_UnitPositive_RobocodeVersionReadsInstallReleaseHeading(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            (root / "versions.md").write_text(
                "## Version 1.11.1 (13-Jul-2026)\n", encoding="utf-8")
            self.assertEqual("1.11.1", harness.resolve_robocode_version(root))
            self.assertIsNone(harness.robocode_version_error(root, "1.11.1"))

    def testHARN008_UnitPositive_RobocodeVersionFallsBackToUniqueEngineVersion(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            (root / "libs").mkdir()
            (root / "libs" / "robocode.core-1.11.1.jar").write_bytes(b"")
            (root / "libs" / "robocode.host-1.11.1.jar").write_bytes(b"")
            self.assertEqual("1.11.1", harness.resolve_robocode_version(root))

    def testHARN008_UnitNegative_UnsupportedRobocodeVersionIsRejected(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            (root / "versions.md").write_text("## Version 1.9.4.2\n", encoding="utf-8")
            error = harness.robocode_version_error(root)
        self.assertIn("not in LiteRumble's allowed client list", error)

    def testHARN008_UnitNegative_UnknownRobocodeVersionIsRejected(self):
        with tempfile.TemporaryDirectory() as directory:
            error = harness.robocode_version_error(Path(directory))
        self.assertIn("could not be determined", error)

    def testHARN001_UnitNegative_FailedCasesRemainUnresolved(self):
        self.assertTrue(registry.is_unresolved("FAIL (TR)"))
        self.assertTrue(registry.is_unresolved("DISCREPANCY (score)"))
        self.assertFalse(registry.is_unresolved("PASS"))

    def testSCORE002_UnitPositive_FiveRepeatedScoreGapsAreConfirmed(self):
        self.assertTrue(registry.score_gap_confirmed([20.0, 21.0, 22.0, 19.0, 20.0], 15.0))

    def testSCORE002_UnitNegative_InsufficientOrSmallSamplesStayInReview(self):
        self.assertFalse(registry.score_gap_confirmed([30.0, 30.0, 30.0, 30.0], 15.0))
        self.assertFalse(registry.score_gap_confirmed([2.0, 3.0, 4.0, 2.0, 3.0], 15.0))

    def testHARN001_UnitPositive_TeamIdentityIncludesItsJarDigest(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            jar = root / "teamrumble" / "legacy.Team_1.0.jar"
            jar.parent.mkdir()
            jar.write_bytes(b"team")
            identity = registry.subject_identity("teamrumble/legacy.Team_1.0.jar", {
                "setup": {"team": True}, "rc": {"selected": "legacy.Team 1.0"},
            }, root)
        self.assertEqual("team", identity["kind"])
        self.assertEqual(64, len(identity["jar_sha256"]))

    def testHARN001_UnitPositive_NewObservationRetainsPriorRun(self):
        data = {"schema_version": 1, "subjects": {}}
        entry = {
            "status": "DISCREPANCY (score)", "delta_pct": 40.0,
            "completed_at": "2026-09-09T00:00:00Z", "setup": {}, "rc": {}, "tr": {},
        }
        state = {"robots": {"roborumble/a.Bot_1.0.jar": entry}}
        registry.sync_state(data, state, Path("missing"), {"bridge_commit": "one"})
        entry["completed_at"] = "2026-09-10T00:00:00Z"
        entry["status"] = "PASS"
        registry.sync_state(data, state, Path("missing"), {"bridge_commit": "two"})
        subject = data["subjects"]["roborumble/a.Bot_1.0.jar"]
        self.assertEqual(2, len(subject["observations"]))
        self.assertEqual("PASS", subject["status"])

    def testHARN001_UnitPositive_ObservationKeepsJarIdentityWhenSameKeyChanges(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            jar = root / "roborumble" / "a.Bot_1.0.jar"
            jar.parent.mkdir()
            jar.write_bytes(b"first")
            state = {"robots": {"roborumble/a.Bot_1.0.jar": {
                "status": "PASS", "completed_at": "2026-09-09T00:00:00Z",
                "setup": {}, "rc": {}, "tr": {},
            }}}
            data = {"schema_version": 1, "subjects": {}}
            registry.sync_state(data, state, root, {"bridge_commit": "one"})
            jar.write_bytes(b"replacement")
            registry.sync_state(data, state, root, {"bridge_commit": "one"})
        observations = data["subjects"]["roborumble/a.Bot_1.0.jar"]["observations"]
        self.assertEqual(2, len(observations))
        self.assertNotEqual(observations[0]["source_identity"]["jar_sha256"],
                            observations[1]["source_identity"]["jar_sha256"])

    def testHARN001_UnitPositive_DiagnosisAndRepairRemainLinkedToRetest(self):
        subject = {"diagnosis_events": [], "observations": []}
        diagnosis = registry.add_diagnosis(subject, "lifecycle", "bridge", "2026-09-09T00:00:00Z")
        registry.add_diagnosis(subject, "runner", "tank-royale", "2026-09-10T00:00:00Z")
        self.assertEqual(diagnosis, registry.diagnosis_for_cause(subject, "lifecycle"))
        self.assertEqual("runner", registry.latest_diagnosis(subject)["cause"])
        state = {"robots": {"roborumble/a.Bot_1.0.jar": {
            "status": "PASS", "completed_at": "2026-09-11T00:00:00Z", "setup": {}, "rc": {}, "tr": {},
            "retest": {"cause": "lifecycle", "diagnosis_id": diagnosis["id"], "repair": "abc123"},
        }}}
        subject["identity"] = registry.subject_identity("roborumble/a.Bot_1.0.jar", state["robots"]["roborumble/a.Bot_1.0.jar"], Path("missing"))
        data = {"schema_version": 1, "subjects": {"roborumble/a.Bot_1.0.jar": subject}}
        registry.sync_state(data, state, Path("missing"), {"bridge_commit": "abc"})
        self.assertEqual("abc123", subject["observations"][0]["retest"]["repair"])

    def testC004_UnitPositive_LiveWorkerExceptionTriggersBridgeOnlyWatcher(self):
        watcher = harness.BridgeOnlyErrorWatcher([], [])
        self.assertTrue(watcher(
            "java.lang.IllegalStateException: bridge failure\n"
            "  at legacy.Bot.run(Bot.java:12)"))
        self.assertIn(("java.lang.IllegalStateException", "legacy.Bot.run"), watcher.found)

    def testC004_UnitNegative_ClassicWorkerExceptionDoesNotTriggerWatcher(self):
        signature = {"exception": "java.lang.IllegalStateException", "origin": "legacy.Bot.run"}
        watcher = harness.BridgeOnlyErrorWatcher([], [signature])
        self.assertFalse(watcher(
            "java.lang.IllegalStateException: classic equivalent\n"
            "  at legacy.Bot.run(Bot.java:12)"))

    def testC004_UnitNegative_CaughtClassicExceptionWithoutOriginDoesNotTriggerWatcher(self):
        signature = {"exception": "java.io.NotSerializableException", "origin": "legacy.Bot.onScannedRobot"}
        watcher = harness.BridgeOnlyErrorWatcher([], [signature])
        self.assertFalse(watcher("java.io.NotSerializableException: legacy payload"))

    def testC004_UnitPositive_ImmediateWorkerExitStillTriggersWatcher(self):
        watcher = harness.BridgeOnlyErrorWatcher([], [])
        returncode, output, timed_out = harness.run_java(
            [sys.executable, "-c", "print('java.lang.IllegalStateException: failure\\n  at legacy.Bot.run(Bot.java:12)')"],
            Path.cwd(), timeout=5, abort_when=watcher, poll_seconds=0.1)
        self.assertEqual(-1, returncode)
        self.assertFalse(timed_out)
        self.assertTrue(watcher.triggered)
        self.assertIn("IllegalStateException", output)

    def testHARN001_UnitNegative_CauseRetestRequiresRepairAndKnownSelection(self):
        missing_repair = SimpleNamespace(repair=None, retest_cause="lifecycle")
        unknown_cause = SimpleNamespace(repair="abc123", retest_cause="lifecycle")
        self.assertIn("requires --repair", harness.retest_option_error(missing_repair))
        self.assertIn("No unresolved", harness.retest_option_error(unknown_cause, []))

    def testHARN007_UnitPositive_FixedMeleeSelectionExcludesSubject(self):
        pool = [{"jar": f"robot-{index}.jar", "sha256": "hash"}
                for index in range(12)]
        selected = harness.select_melee_opponent_names("robot-3.jar", pool)

        self.assertEqual(9, len(selected))
        self.assertNotIn("robot-3.jar", selected)
        self.assertEqual([f"robot-{index}.jar" for index in (0, 1, 2, 4, 5, 6, 7, 8, 9)], selected)

    def testHARN007_UnitNegative_FixedMeleeSelectionRejectsTooSmallPool(self):
        pool = [{"jar": f"robot-{index}.jar", "sha256": "hash"}
                for index in range(9)]

        with self.assertRaises(ValueError):
            harness.select_melee_opponent_names("robot-0.jar", pool)


if __name__ == "__main__":
    unittest.main()
