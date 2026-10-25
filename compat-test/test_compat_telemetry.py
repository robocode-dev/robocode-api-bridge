import json
import tempfile
import unittest
from pathlib import Path

from compat_test import skipped_turn_telemetry


class SkippedTurnTelemetryTest(unittest.TestCase):

    def test_captured_event_includes_staged_bot_display_name(self):
        with tempfile.TemporaryDirectory() as temporary_directory:
            bot_dir = Path(temporary_directory) / "ak.Fermat_2.0"
            bot_dir.mkdir()
            (bot_dir / f"{bot_dir.name}.json").write_text(
                json.dumps({"name": "Fermat", "version": "2.0"}), encoding="utf-8")
            (bot_dir / "stdout.log").write_text(
                "BRIDGE_SKIPPED_TURN_TELEMETRY_READY botId=5\n"
                "BRIDGE_SKIPPED_TURN botId=5 round=1 turn=1\n"
                "BRIDGE_SKIPPED_TURN_TELEMETRY_COMPLETE botId=5 eventCount=1\n",
                encoding="utf-8")

            telemetry = skipped_turn_telemetry([bot_dir], True, True, 1)

        self.assertEqual("captured", telemetry["status"])
        self.assertEqual([{
            "bot_id": 5,
            "bot_name": "Fermat 2.0",
            "round": 1,
            "turn": 1,
        }], telemetry["events"])

    def test_missing_descriptor_keeps_event_capture_available(self):
        with tempfile.TemporaryDirectory() as temporary_directory:
            bot_dir = Path(temporary_directory) / "unknown-bot"
            bot_dir.mkdir()
            (bot_dir / "stdout.log").write_text(
                "BRIDGE_SKIPPED_TURN_TELEMETRY_READY botId=5\n"
                "BRIDGE_SKIPPED_TURN_TELEMETRY_COMPLETE botId=5 eventCount=0\n",
                encoding="utf-8")

            telemetry = skipped_turn_telemetry([bot_dir], True, True, 1)

        self.assertEqual("captured", telemetry["status"])
        self.assertEqual([], telemetry["events"])


if __name__ == "__main__":
    unittest.main()
