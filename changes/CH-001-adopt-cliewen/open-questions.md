---
id: OQ-001
type: open-questions
status: open
links: [CH-001]
title: CH-001 open questions
---

# CH-001 — open questions

Three questions surfaced by the rehearsal. The first two block the mutate phase because they change what gets built; the third is recorded for the record and proceeds on the skill's literal instruction unless the human says otherwise.

## Q1 — May the conformance tier build classic Robocode's test robots from source, or must they be vendored?

**Blocking for tier 2's shape.** Not blocking for the corpus.

Classic Robocode ships 46 purpose-built test robots at `C:\Code\robocode\robocode.tests.robots\src\main\java\tested\robots`, driven by 48 JUnit tests over `RobotTestBed`. They are the executable specification of the semantics this bridge is trying to reproduce, and most of them assert by printing markers to the robot console rather than by comparing numbers, which is what makes them portable to Tank Royale.

Classic Robocode is EPL-1.0; this repository is Apache-2.0.

The rehearsal proposes to **build them from `C:\Code\robocode` and consume the resulting jar locally**, resolved through a Gradle property, distributing nothing and copying no source into this tree. On that reading there is no license question to answer, because nothing is redistributed and the two trees stay separate.

The alternative is to vendor the robots into this repository under their own EPL headers in a clearly separated directory, which makes the tier self-contained and CI-runnable but makes this repository multi-licensed.

The human is the copyright holder and project maintainer here, so this is their call rather than the extraction's.

## Q2 — What counts as a regression for a bot whose score is known to be noise?

**Blocking for tier 3's gate.**

`compat-test/compatibility_report.md` records `ad.last.Bottom` at RC=6, TR=126, a `+2000.0%` delta, and `TODO.md` records that the same bot swung RC=6 ↔ 274 between runs on the classic side alone. A percentage delta on a base of six is arithmetic, not evidence.

The rehearsal proposes: three repeats at 35 rounds, averaged before comparison; a bot in state `open` or `fixed` regresses when its averaged delta moves more than 25 percentage points from its recorded baseline; a bot in state `noise` is always reported and never fails the run.

Both numbers — the repeat count and the 25-point movement — are guesses calibrated to nothing. The threshold that already exists in the harness (`±25.0%` absolute delta) was chosen for a different question, so reusing its value here is a coincidence rather than a reason. The maintainer has run these sweeps and has the better number.

## Q3 — Where does a repository-authored source mapping live?

**Not blocking.** Recorded because the instruction and the tree it points at disagree.

`clue-extract`'s source-mappings reference says a new source format adds a mapping file to the skill's `mappings/` folder. That folder is inside a generated, version-stamped skill tree that `CLAUDE.md` says not to hand-edit, and it exists twice — `.agents/skills/clue-extract/mappings/` and its `.claude/skills/` mirror.

Tested during rehearsal: adding a file there does not trip `clue validate`'s skill drift check. Unknown: whether a future `clue migrate` preserves it.

Proceeding on the instruction's literal reading — the mapping is written into both trees — and recording the upgrade risk in the extraction report rather than inventing a second home for it.
