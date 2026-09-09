import importlib.util
import tempfile
import unittest
from pathlib import Path


MODULE = Path(__file__).with_name("parity_registry.py")
SPEC = importlib.util.spec_from_file_location("parity_registry", MODULE)
registry = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(registry)


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

    def testHARN003_UnitNegative_SameExceptionFromDifferentCallbackIsDifferent(self):
        classic = [{"exception": "java.lang.NullPointerException", "origin": "legacy.Bot.run"}]
        tank = [{"exception": "java.lang.NullPointerException", "origin": "legacy.Bot.onScannedRobot"}]
        difference = registry.compare_errors(classic, tank)
        self.assertEqual(classic, difference["classic_only"])
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


if __name__ == "__main__":
    unittest.main()
