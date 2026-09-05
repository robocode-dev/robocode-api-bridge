import importlib.util
import json
import tempfile
import unittest
from pathlib import Path


MODULE_PATH = Path(__file__).with_name("compat_test.py")
SPEC = importlib.util.spec_from_file_location("compat_test", MODULE_PATH)
compat_test = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(compat_test)


class TeamStagingTest(unittest.TestCase):

    def setUp(self):
        self.temp_dir = tempfile.TemporaryDirectory()
        self.root = Path(self.temp_dir.name)

    def tearDown(self):
        self.temp_dir.cleanup()

    def create_member(self, name):
        member = self.root / name
        member.mkdir()
        (member / f"{name}.json").write_text(json.dumps({"name": name}), encoding="utf-8")
        (member / f"{name}.cmd").write_text("java Wrapper", encoding="utf-8")
        return member

    def create_team(self, members):
        team = self.root / "TestTeam"
        team.mkdir()
        (team / "TestTeam.json").write_text(
            json.dumps({"name": "TestTeam", "teamMembers": members}), encoding="utf-8")
        return team

    def testTEAM001_UnitPositive_team_copy_keeps_member_roster_and_separate_scripts(self):
        self.create_member("Leader")
        self.create_member("Droid")
        team = self.create_team(["Leader", "Droid", "Droid"])

        entries = compat_test.stage_team_dirs(team, 2)

        self.assertEqual([team, self.root / "TestTeam-2"], entries)
        copied_manifest = json.loads((self.root / "TestTeam-2" / "TestTeam-2.json").read_text())
        self.assertEqual(["Leader-2", "Droid-2", "Droid-2"], copied_manifest["teamMembers"])
        self.assertTrue((self.root / "Leader-2" / "Leader-2.cmd").exists())
        self.assertTrue((self.root / "Droid-2" / "Droid-2.cmd").exists())
        self.assertIn("stdout.log", (self.root / "Droid-2" / "Droid-2.cmd").read_text())

    def testTEAM001_UnitNegative_missing_member_is_rejected(self):
        self.create_member("Leader")
        team = self.create_team(["Leader", "Missing"])

        with self.assertRaisesRegex(ValueError, "team member directory not found"):
            compat_test.duplicate_team_dir(team, 2)


if __name__ == "__main__":
    unittest.main()
