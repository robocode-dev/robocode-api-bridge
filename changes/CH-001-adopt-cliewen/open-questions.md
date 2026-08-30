---
id: OQ-001
type: open-questions
status: resolved
links: [CH-001]
title: CH-001 open questions
---

# CH-001 — open questions

Three questions surfaced by the rehearsal. Both blocking questions are now answered; the third proceeds on the skill's literal instruction. The answers to Q1 and Q2 become decision records in the digest.

## Q1 — May the conformance tier build classic Robocode's test robots from source, or must they be vendored?

**Resolved.** The maintainer is the principal developer of both classic Robocode and Tank Royale, so there is no licensing question to answer: the test robots may be built from `C:\Code\robocode` and consumed directly, and vendoring them is equally available if the conformance tier later wants to be self-contained. The rehearsal's proposed approach stands, and its stated concern was misplaced.

The same answer opened a larger door. The maintainer directed the tiers at `C:\Code\LiteRumble robots`, the collection the harness already draws from, held behind a named constant rather than a repeated literal. Its jars are read-only inputs: never modified, with their bytecode and any bundled source read for debugging only. That becomes a constraint rather than a convention, because a modified rumble jar would silently invalidate every comparison made against it.

### Original question, retained for the record

**Was blocking for tier 2's shape.** Not blocking for the corpus.

Classic Robocode ships 46 purpose-built test robots at `C:\Code\robocode\robocode.tests.robots\src\main\java\tested\robots`, driven by 48 JUnit tests over `RobotTestBed`. They are the executable specification of the semantics this bridge is trying to reproduce, and most of them assert by printing markers to the robot console rather than by comparing numbers, which is what makes them portable to Tank Royale.

Classic Robocode is EPL-1.0; this repository is Apache-2.0.

The rehearsal proposes to **build them from `C:\Code\robocode` and consume the resulting jar locally**, resolved through a Gradle property, distributing nothing and copying no source into this tree. On that reading there is no license question to answer, because nothing is redistributed and the two trees stay separate.

The alternative is to vendor the robots into this repository under their own EPL headers in a clearly separated directory, which makes the tier self-contained and CI-runnable but makes this repository multi-licensed.

The human is the copyright holder and project maintainer here, so this is their call rather than the extraction's.

## Q2 — What counts as a regression for a bot whose score is known to be noise?

**Resolved**, and the answer went further than the question.

Each division runs at its **official rumble parameters**, read from `C:\robocode\roborumble\`: RoboRumble at 800×600 over 35 rounds, MeleeRumble at 1000×1000 over 35 rounds with ten bots, TeamRumble at 1200×1200 over 10 rounds. The harness's current 800×600 at 10 rounds approximates the 1v1 division at under a third of its rounds and covers neither of the others, which is a large part of why its numbers move. **Five repeats** are averaged per side, and a bot regresses when its averaged delta moves more than **15 percentage points** from its recorded baseline.

The addition, in the maintainer's words: **stop a battle if a bot throws exceptions in Tank Royale but not in classic Robocode**, because the same bot misbehaving only under the bridge is a clear signal rather than a score to be averaged.

That reshapes the tier rather than merely adding a rule to it. Comparing scores treats every divergence as a quantity; a Tank-Royale-only exception is a categorical fact that no amount of averaging improves, and waiting for the battle to finish before reporting it spends minutes to learn nothing further. The classic side already runs first and yields the exception baseline, so the Tank Royale side can be aborted the moment a signature appears that classic did not produce.

It also removes one of the rehearsal's two `Human` badges. `SCORE-003` was written as a maintainer reading error columns and judging whether a signature was a bridge defect; with a baseline to compare against, that judgment is mechanical. It becomes `@draft` against `M-001` rather than `Human` — draft only because the harness is Python, which is not a supported evidence carrier.

### Original question, retained for the record

**Was blocking for tier 3's gate.**

`compat-test/compatibility_report.md` records `ad.last.Bottom` at RC=6, TR=126, a `+2000.0%` delta, and `TODO.md` records that the same bot swung RC=6 ↔ 274 between runs on the classic side alone. A percentage delta on a base of six is arithmetic, not evidence.

The rehearsal proposes: three repeats at 35 rounds, averaged before comparison; a bot in state `open` or `fixed` regresses when its averaged delta moves more than 25 percentage points from its recorded baseline; a bot in state `noise` is always reported and never fails the run.

Both numbers — the repeat count and the 25-point movement — are guesses calibrated to nothing. The threshold that already exists in the harness (`±25.0%` absolute delta) was chosen for a different question, so reusing its value here is a coincidence rather than a reason. The maintainer has run these sweeps and has the better number.

## Q3 — Where does a repository-authored source mapping live?

**Not blocking.** Recorded because the instruction and the tree it points at disagree.

`clue-extract`'s source-mappings reference says a new source format adds a mapping file to the skill's `mappings/` folder. That folder is inside a generated, version-stamped skill tree that `CLAUDE.md` says not to hand-edit, and it exists twice — `.agents/skills/clue-extract/mappings/` and its `.claude/skills/` mirror.

Tested during rehearsal: adding a file there does not trip `clue validate`'s skill drift check. Unknown: whether a future `clue migrate` preserves it.

Proceeding on the instruction's literal reading — the mapping is written into both trees — and recording the upgrade risk in the extraction report rather than inventing a second home for it.
