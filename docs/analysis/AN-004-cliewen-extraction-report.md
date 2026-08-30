---
id: AN-004
type: analysis
status: active
links: [CAP-001, CAP-002, CAP-003, CAP-004, CAP-005, CAP-006, CAP-007, P-001, G-001]
title: Cliewen extraction report — what the repository's prose became
provenance: inferred
reversal-cost: low
---

# AN-004 — Cliewen extraction report

What the brownfield extraction found, what it mapped where, and what it deliberately did not carry across. The rehearsal that preceded it was pinned at source revision `16980d442629e0bd1d3524a65b5407d59aade5fa`.

## What the source was

No structured specification corpus: no OpenSpec tree, no decision log, no requirements registry. Ordinary project markdown that had been carrying specification duty — a project README, a prose TODO, a harness README, and a generated compatibility report. No mapping existed for that shape, so writing one was the extraction's first task; it lives beside the `clue-extract` skill as `adhoc-markdown`.

Two properties of the source shaped everything downstream.

**The compatibility report is untracked.** `compat-test/.gitignore` excludes it. The repository's only body of compatibility evidence had no committed revision and existed solely in whoever last ran the sweep's working tree, so it could not be pinned and a reviewer could not open it.

**The TODO's two halves disagreed.** Its "done" narrative reported the event-dispatch redesign as landed with the score results that followed; its numbered items listed score gaps and said to re-test first because those numbers predated the redesign. Both were true. The disagreement was extracted as a finding rather than resolved by picking whichever read more recent — it became `AN-001`.

## The mapping

<!-- clue:derived-from: docs/analysis/AN-004-extraction/source-manifest.yaml -->

Derived from `docs/analysis/AN-004-extraction/source-manifest.yaml` — source revision `16980d442629e0bd1d3524a65b5407d59aade5fa`, read from `TODO.md`. Regenerate with `clue report`; never write inside this region by hand.

| Outcome | Criteria |
|---|---|
| Proven in the target | 6 |
| Deferred with a plan door | 38 |
| Excluded from the migration | 0 |

| Criterion | Outcome | Detail |
|---|---|---|
| `API-001` | proven | Unit · positive · robocode-api/src/test/java/dev/robocode/tankroyale/bridge/AngleConverterTest.java |
| `API-002` | proven | Unit · positive · robocode-api/src/test/java/dev/robocode/tankroyale/bridge/ColorMapperTest.java |
| `API-003` | proven | Unit · positive · robocode-api/src/test/java/dev/robocode/tankroyale/bridge/ResultsMapperTest.java |
| `API-004` | proven | Unit · positive · robocode-api/src/test/java/dev/robocode/tankroyale/bridge/BulletMapperTest.java |
| `API-005` | proven | Unit · positive · robocode-api/src/test/java/dev/robocode/tankroyale/bridge/IBotToRobotStatusMapperTest.java |
| `API-006` | proven | Unit · positive · robocode-api/src/test/java/dev/robocode/tankroyale/bridge/EventClassMapperTest.java |
| `EVT-001` | deferred (draft) | door `M-101` · source `TODO.md "DONE: Event-dispatch redesign"` · Events are stated to flow through the Bot API EventQueue with classic priorities, but the claim was verified by re-running battles and reading scores; no attributable test exists. |
| `EVT-002` | deferred (draft) | door `M-102` · source `TODO.md "DONE: Event-dispatch redesign"` · The 0.33.1 dropped-deferred-event bug is described in the source as fixed by the upgrade, and its absence is exactly what has no regression test today. |
| `EVT-003` | deferred (draft) | door `M-103` · source `TODO.md "DONE: Event-dispatch redesign"` · setInterruptible is stated to be NPE-guarded and scan-interrupt semantics to work again, evidenced only by ad.last.Bottom's inversion disappearing. |
| `EVT-004` | deferred (draft) | door `M-104` · source `TODO.md "DONE: Event-dispatch redesign"` · Own-death reaching onDeath is stated as newly working; the source records that no DeathEvent case existed before, so this promise has never been tested. |
| `EVT-005` | deferred (draft) | door `M-105` · source `TODO.md "DONE: Event-dispatch redesign"` · Classic new-turn timing at the end of execute() is stated as a property of bot-api 1.0.2 rather than as something this bridge checks. |
| `EVT-006` | deferred (draft) | door `M-106` · source `robocode-api/src/main/java/dev/robocode/tankroyale/bridge/CustomEventMapper.java` · Custom event registration is implemented in the bridge's mapping helpers and exercised only incidentally by rumble bots. |
| `EVT-007` | deferred (draft) | door `M-107` · source `robocode-api/src/main/java/dev/robocode/tankroyale/bridge/RobotDeathEventMapper.java` · Other-robot death events are mapped but unverified independently of aggregate scores. |
| `EVT-008` | deferred (draft) | door `M-108` · source `TODO.md "DONE: Event-dispatch redesign"` · Skipped-turn reporting has no stated verification anywhere in the source corpus. |
| `EVT-009` | deferred (draft) | door `M-109` · source `compat-test/README.md "Test methodology (per jar)"` · Robot-thrown exception handling is observed through harness error counts, which cannot attribute a failure to this promise. |
| `EVT-010` | deferred (draft) | door `M-110` · source `C:/robocode/roborumble/meleerumble.txt` · Melee puts ten bots on the field, so a turn carries many scan events at one priority. That is the exact shape the 0.33.1 queue dropped, and the harness has never run a melee battle, so the division most likely to expose the bug class has no coverage at all. |
| `FIO-001` | deferred (draft) | door `M-119` · source `TODO.md "4. File I/O sandboxing (RobocodeFileOutputStream emulation)"` · The bridge performs no path redirection; dz.Gir writing root paths produces roughly ten thousand access-denied errors under Tank Royale. The promise is stated and known to be unmet. |
| `FIO-002` | deferred (draft) | door `M-120` · source `TODO.md "1. Commit pending bridge changes (done)"` · getDataDirectory() was changed to match what getDataFile() resolves against, and the source states it has not been re-tested against the bot that surfaced the inconsistency. |
| `FIO-003` | deferred (draft) | door `M-121` · source `robocode-api/src/main/java/robocode/RobocodeFileOutputStream.java` · RobocodeFileOutputStream's documentation states a 200000-byte cap that the implementation does not enforce. |
| `FIO-004` | deferred (draft) | door `M-122` · source `TODO.md "4. File I/O sandboxing (RobocodeFileOutputStream emulation)"` · Classic sandboxes all robot file I/O into the data directory; the bridge does not, and the source says so plainly. |
| `HARN-001` | deferred (draft) | door `M-132` · source `compat-test/README.md "Checkpointing / resume"` · Checkpoint-and-resume is a mechanical property that deserves a machine check, and Python is not a supported evidence carrier, so badging it Human would launder a missing test as a judgment call. |
| `HARN-002` | deferred (draft) | door `M-133` · source `compat-test/README.md "Prerequisites"` · Process-tree termination on timeout is mechanical for the same reason as HARN-001. |
| `HARN-003` | deferred (draft) | door `M-134` · source `compat-test/README.md "Status values"` · Status classification is a pure function of scores and error counts and should be machine-checked once a carrier exists. |
| `HARN-004` | deferred (draft) | door `M-135` · source `TODO.md "DONE: Event-dispatch redesign"` · The averaging regression mode does not exist yet; this change builds it. |
| `HARN-005` | deferred (draft) | door `M-136` · source `TODO.md "2. Score-gap bots (TR lower, no errors)"` · The per-turn trace mode does not exist yet; the source asks for behavioral comparison without naming a tool for it. |
| `HARN-006` | deferred (draft) | door `M-137` · source `OQ-001 Q2 resolution` · Aborting the Tank Royale battle the moment an exception signature appears that the classic side did not produce. The behaviour does not exist yet; this change builds it. |
| `HARN-007` | deferred (draft) | door `M-138` · source `C:/robocode/roborumble/roborumble.txt` · Each division runs at its official rumble parameters rather than one approximated 1v1 setup. The parameters are read from the classic installation's rumble configuration; the harness currently hard-codes a single setup that matches none of them exactly. |
| `PHY-001` | deferred (draft) | door `M-111` · source `README.md "Robocode API adapter library"` · Gun heat and cooling are part of the compatibility claim the README makes; nothing in the repository checks the rate. |
| `PHY-002` | deferred (draft) | door `M-112` · source `README.md "Robocode API adapter library"` · Velocity cap and acceleration curve are unverified; a divergence here would surface only as a score delta. |
| `PHY-003` | deferred (draft) | door `M-113` · source `README.md "Robocode API adapter library"` · Velocity-dependent body turn rate is unverified. |
| `PHY-004` | deferred (draft) | door `M-114` · source `README.md "Robocode API adapter library"` · Gun turn rate and setAdjustGunForRobotTurn are unverified. |
| `PHY-005` | deferred (draft) | door `M-115` · source `TODO.md "DONE: Event-dispatch redesign"` · Radar turn rate and setAdjustRadarForGunTurn are unverified; the source records radar locks breaking under 0.33.1, so this promise has a known failure history and no test. |
| `PHY-006` | deferred (draft) | door `M-116` · source `README.md "Robocode API adapter library"` · Direction reversal follows a specific classic deceleration path that nothing checks. |
| `PHY-007` | deferred (draft) | door `M-117` · source `robocode-api/src/main/java/robocode/RateControlRobot.java` · RateControlRobot's rate semantics are unverified. |
| `PHY-008` | deferred (draft) | door `M-118` · source `robocode-api/src/main/java/dev/robocode/tankroyale/bridge/BulletPeer.java` · Bullet state tracking is unverified independently of scores. |
| `SCORE-001` | deferred (human) | door `M-123` · source `compat-test/compatibility_report.md` · A maintainer reads the generated compatibility report and judges whether the population's parity is acceptable. That judgment is the instrument, not a stand-in for a missing test: the comparison is statistical, the run takes a night, and the threshold is a matter of interpretation rather than a machine verdict. |
| `SCORE-002` | deferred (draft) | door `M-124` · source `TODO.md "6. Full compat sweep"` · The regression watch list and its averaging gate do not exist yet; this change builds them. |
| `SCORE-003` | deferred (draft) | door `M-125` · source `compat-test/compatibility_report.md` · A Tank-Royale-only exception is decided against the classic side's own signature baseline, so it is a machine verdict rather than a maintainer's judgment; it is draft only because the harness is Python, which is not a supported evidence carrier. |
| `SCORE-004` | deferred (draft) | door `M-126` · source `TODO.md "2. Score-gap bots (TR lower, no errors)"` · CodaFirst, Bl4ck, and Aetos score materially lower under Tank Royale with no errors logged; the source says these numbers predate the event-dispatch fix and must be re-tested before anything is concluded. |
| `SCORE-005` | deferred (draft) | door `M-127` · source `TODO.md "3. Score-gap bots (TR higher)"` · ScalarR, Ar1, and BasicSurfer score materially higher under Tank Royale; the same re-test-first caveat applies. |
| `SCORE-006` | deferred (draft) | door `M-128` · source `C:/robocode/roborumble/meleerumble.txt` · MeleeRumble has 382 jars in the collection and no result of any kind on the Tank Royale side; parity for the division is unmeasured rather than unmet. |
| `TEAM-001` | deferred (draft) | door `M-129` · source `TODO.md "5. Team support in the robots wrapper"` · The robots wrapper contains no team handling; team jars are recorded SKIPPED-TR by the harness. |
| `TEAM-002` | deferred (draft) | door `M-130` · source `TODO.md "5. Team support in the robots wrapper"` · TeamRobot messaging is unimplemented in the wrapper path. |
| `TEAM-003` | deferred (draft) | door `M-131` · source `TODO.md "5. Team support in the robots wrapper"` · Droid semantics are unimplemented in the wrapper path. |

<!-- clue:derived-end -->

## What was minted, and why nothing was preserved

Every criterion ID is new. The source had none — no IDs, no scenarios, no declared proof types, no boundary between a promise and a note-to-self — so there was nothing to preserve and the corpus became the registry. Each capability declares its namespace and starts at one.

One promise was deliberately **not** minted: exact per-turn position parity with classic. Classic can assert it because a seed makes its battles reproducible; Tank Royale exposes no seed, so a criterion for it would be a permanent draft dressed as a promise. `AN-002` records the gap instead.

## What the dispositions say about this repository

Most criteria are `@draft`, and that is the single most useful thing the extraction produced. It converted "the bridge is broadly compatible" into named promises, only a handful of which had any attributable proof.

The `API` namespace became machine-proven because its subjects are pure functions over value objects with no engine dependency — the extraction committed to building that evidence rather than deferring it, and those tests found `AN-005` on their first run.

`SCORE-001` is the only `Test-type: Human` criterion. A maintainer reads the sweep and judges whether the population's parity is acceptable; no threshold decides that. `SCORE-003` was drafted as a second one and is not: once the classic side's exception signatures are a baseline, "did this bot misbehave only under the bridge" has an answer rather than an opinion.

The `HARN` namespace is the honest awkward case. Several of its criteria describe behaviour that already works — checkpoint-and-resume, process-tree termination, result classification. They are `@draft` because the harness is Python, which is not a supported evidence carrier. Badging them `Human` would have validated, would have made the capability look complete, and would have described a maintainer's judgment where an untested function is what actually exists. `AN-003` records that decision and the routes out of it.

## What was deleted

Nothing. `deleted-paths` is empty in the carrier inventory, which is unusual enough to state plainly.

This extraction had no parallel specification tree to kill. The source files are the repository's working documentation and every one is retained: the project and harness READMEs stay as reader entry points, and `TODO.md` is reduced to a pointer at the plan rather than removed, because it is where a contributor looks first. After the digest it states no work item of its own, so there is no second system of record.

## Where parity is not clean, and why that does not gate anything here

`clue parity` reports six `changed-evidence` findings, one per `API` criterion. They all say the same thing: the manifest pinned `direction: positive`, and the corpus provides both a positive and a negative test.

The corpus has **more** evidence than the rehearsal committed to, not less. The manifest's `direction` field holds a single token, and no form of it — a list, a pair, a separator — is accepted for an entry whose criterion is proven in both directions, so the shape the rehearsal could record and the shape that was built cannot be made to agree.

Two ways to make it agree were rejected. Dropping the negative tests would satisfy the tool by deleting evidence, and the negative half is where the real risk sits in a conversion — the angle at the wrap, the colour that is absent rather than black, the event with no counterpart. Splitting each criterion into a letter-suffixed pair, one per direction, would satisfy it by doubling the namespace to describe one promise.

The rule this leaves unmet is the one gating deletion of the source corpus: parity must be clean before the source dies. **Nothing is deleted here** — `deleted-paths` is empty, every source file is retained, and `TODO.md` is reduced to a pointer rather than removed. The gate exists to stop coverage disappearing along with the files that held it, and there are no files to disappear.

Recorded rather than suppressed, because a report that said parity was clean would be describing a different repository.

## Known risk carried forward

The `adhoc-markdown` mapping was written into the `clue-extract` skill's `mappings/` folder, as that skill's own instruction directs. That folder sits inside a generated, version-stamped skill tree mirrored across `.agents/skills/` and `.claude/skills/`. Adding a file there does not trip `clue validate`'s drift check — tested during the rehearsal — but whether a future `clue migrate` preserves it is unknown. If it disappears after an upgrade, it is in this repository's history.

## What the extraction changed about the code

It was scoped not to. One exception was made and is recorded rather than folded in: `AN-005`, a positional swap in the robot status mapper, found by the first unit test written during the extraction. Fixing it was a one-line change restoring behaviour the reproduced API already specified. The alternative was shipping a knowingly red build or writing an assertion that certified the defect.

A second defect found during the same work was **not** fixed: `AN-006`, where a robot's own death never reaches its death handler. Its cause is not established and may lie upstream in the Bot API, so it is recorded with its evidence and left to the milestone that owns it.
