---
id: REH-001
type: rehearsal
status: active
links: [CH-001, OQ-001]
title: CH-001 extraction rehearsal — report only
---

# REH-001 — CH-001 extraction rehearsal

Report-only pass. Nothing outside `changes/CH-001-adopt-cliewen/` and the new source mapping has been written, and no target corpus artifact exists yet. The mutate phase begins only on explicit human authorization.

Pinned at source revision `16980d442629e0bd1d3524a65b5407d59aade5fa`.

## Source inventory

The repository has no structured specification corpus. There is no OpenSpec tree, no MADR decision log, no requirements registry. What it has is ordinary project markdown that has been carrying specification duty, which is the case the new [ad-hoc markdown mapping](../../.agents/skills/clue-extract/mappings/adhoc-markdown.md) was written for — no mapping existed for this format, and writing one was this extraction's first task.

| Source | Class | What it actually holds |
|---|---|---|
| `README.md` | descriptive prose | The purpose statement, the two-component breakdown, and a Mermaid component diagram |
| `TODO.md` | intent prose | A "DONE" narrative of the event-dispatch redesign, then six numbered remaining items in priority order, plus a Tank Royale-side section |
| `compat-test/README.md` | operational prose | The harness's prerequisites, per-jar methodology, status vocabulary, and caveats |
| `compat-test/compatibility_report.md` | generated output | Tested jars with scores, deltas, error counts, and statuses |
| `C:\Code\robocode` at `1e70188f3` | external evidence source | A JUnit conformance suite over `RobotTestBed`, driving purpose-built test robots in `robocode.tests.robots` |

Two observations about the source that shape everything below.

**`compatibility_report.md` is untracked.** `compat-test/.gitignore` excludes it along with `errors/`, `work/`, and `test_progress.json`. The repository's only body of compatibility evidence therefore has no committed revision, exists solely in whoever last ran the sweep's working tree, and cannot be pinned. It is recorded as a blocked carrier for that reason, and the gap is real rather than procedural: the corpus is about to name evidence that no reviewer can open.

**The TODO's two halves disagree.** Its "DONE" section reports the event-dispatch redesign as landed and lists the score results that followed, while items 2 and 3 list score gaps and instruct the reader to re-test first because those numbers predate the redesign. Both are true and the disagreement is the finding: every bot the report flags currently has no interpretable status. Per the mapping's guidance, both are extracted and the disagreement becomes an analysis record rather than being resolved by picking whichever reads more recent.

## The divisions the harness does not run

Surfaced while resolving `OQ-001`, and large enough to change what `CAP-005` and `CAP-007` promise.

The official rumble parameters live in the classic installation at `C:/robocode/roborumble/`, one configuration file per division:

| Division | Battlefield | Rounds | Bots per battle | Collection directory |
|---|---|---:|---:|---|
| RoboRumble (1v1) | 800×600 | 35 | 2 | `roborumble/` |
| MeleeRumble | 1000×1000 | 35 | 10 | `meleerumble/` |
| TeamRumble | 1200×1200 | 10 | teams | `teamrumble/` |

The harness runs one setup for everything: 800×600, 10 rounds, a robot against a copy of itself. That approximates the 1v1 division at under a third of its rounds and matches neither of the others. Two consequences follow.

The **round count is part of why the numbers move.** The harness README already says a healthy robot can swing ±20–30% at ten rounds and advises `--rounds 35` for stability — which is the official 1v1 figure. The instrument has been running below the setting its own documentation recommends, and `A-001`'s noise finding is partly a description of that rather than of the bots.

**Melee has never been run at all**, and it is the division most likely to expose the bug class this bridge has already been bitten by. Ten bots on a field means a turn carries many scan events at a single priority, which is exactly the shape 0.33.1's queue dropped. The `meleerumble/` collection has no Tank Royale result of any kind. `EVT-010` and `SCORE-006` are minted for it.

Team jars are recorded `SKIPPED-TR` today because the wrapper has no team support, so the `teamrumble/` collection becomes `CAP-006`'s corpus once `M-005` lands rather than a gap to close now.

## Proposed mapping

| Source | Target |
|---|---|
| `README.md` purpose statement | `G-001`, `status: accepted` |
| `README.md` component breakdown and Mermaid diagram | `ARCH-001` bridge runtime topology, diagram carried across unchanged |
| The reproduced `robocode.*` package tree under `robocode-api/src/main/java` | `ARCH-002` the frozen API surface |
| `compat-test/README.md` process and isolation model, extended to the new tiers | `ARCH-003` the two-engine evidence topology |
| `TODO.md` six numbered items, in their own order | `P-001` with milestones `M-001` through `M-006` |
| `TODO.md` "DONE" section | `IDR-001` (EventQueue routing), `ADR-001` (0.33.1 to 1.0.2), and the EVT criteria the redesign touched |
| `TODO.md` item 1's `getDataDirectory()` note | `IDR-002` |
| `TODO.md` item 4 | `CAP-004`, all criteria `@draft` |
| `TODO.md` item 5 | `CAP-006`, all criteria `@draft` |
| `TODO.md` noise observations | `A-001` |
| `README.md` compatibility claim | `CAP-001`, `CAP-002`, `CAP-003` |
| `compat-test/README.md` methodology and status vocabulary | `CAP-007` README, criteria, and design |
| `compat-test/README.md` bot-api pairing warning | `C-002` |
| `C:/robocode/roborumble/*.txt` official division parameters | `C-003` |
| `OQ-001` Q2 resolution — five repeats, fifteen-point band, abort on a Tank-Royale-only exception | `C-004` |
| `C:\Code\LiteRumble robots` as a read-only input behind a named constant | `C-007` |
| `compat-test/compatibility_report.md` flagged rows | the regression watch list plus `SCORE-004` and `SCORE-005` |
| Tank Royale's absent RNG seed | `A-002` |
| Python's absence from the supported carriers | `A-003` |

`TODO.md`'s Tank Royale-side section describes work committed in a different repository. It is not extracted as an artifact here — the corpus of another repository is not this one's to hold — but it seeds `C-005`, the constraint that Bot API changes span all four language implementations.

## Minted IDs

Seven namespaces. **No ID is preserved, because the source has none.** Every one is minted, each namespace starts at one, and the corpus becomes the registry. The per-criterion detail lives in `source-manifest.yaml`, and the population figures belong to the extraction report's derived region where `clue report` renders them from that manifest; they are not typed here or in any second committed registry.

| Capability | Prefix | Source |
|---|---|---|
| `CAP-001` event dispatch and timing parity | `EVT` | `TODO.md` DONE section, plus the melee division |
| `CAP-002` robot physics and state parity | `PHY` | `README.md` compatibility claim |
| `CAP-003` Robocode API surface fidelity | `API` | the bridge's mapper classes |
| `CAP-004` robot file I/O sandboxing | `FIO` | `TODO.md` item 4 |
| `CAP-005` score parity across the rumble collection | `SCORE` | `TODO.md` items 2, 3, 6, plus the melee division |
| `CAP-006` team robot support | `TEAM` | `TODO.md` item 5 |
| `CAP-007` the compatibility harness | `HARN` | `compat-test/README.md` and the `OQ-001` Q2 resolution |

One promise the source does not make is deliberately not minted: exact per-turn position parity with classic. Classic can assert it because `-DRANDOMSEED` makes a battle reproducible; Tank Royale exposes no seed, so the promise is unmeetable and inventing a criterion for it would manufacture a permanent draft. `A-002` records the gap instead.

## Dispositions

Most criteria resolve `@draft`. That is the honest reading of a repository that documented its intent carefully and verified it by running battles and reading scores, and it is the single most useful thing this extraction produces: it converts "the bridge is broadly compatible" into named promises, only a handful of which currently have any attributable proof.

- **The `API` namespace becomes machine-proven** — `Test-type: Unit`, both directions. These are pure functions over value objects with no engine dependency, so the extraction commits to building their evidence rather than deferring it.
- **`SCORE-001` becomes `Test-type: Human`**, and is the only criterion that does. A maintainer reads the sweep report and judges whether the population's parity is acceptable. That is genuine human judgment on a statistical instrument, not a placeholder.
- **Everything else becomes `@draft`**, each naming its plan door. The `HARN` namespace is worth singling out: it describes mechanical properties that deserve machine checks, and it is draft only because the harness is Python and Cliewen supports Go, JVM, and Cucumber carriers. Badging those Human would launder a missing test as a judgment call, so they stay draft and `A-003` records why.

`SCORE-003` was drafted as a second `Human` criterion and is no longer one. The reasoning is in `OQ-001` Q2: once the classic side's exception signatures are a baseline the Tank Royale side is compared against, "did this bot misbehave only under the bridge" stops being a judgment and becomes a verdict. The criterion is `@draft` for the Python carrier reason, not for a judgment reason, and that distinction is worth preserving rather than collapsing.

## Confidence and reversal cost

Everything extracted is born `provenance: inferred`. Reversal cost splits as follows.

**Low** — `CAP-001`, `CAP-002`, `CAP-003`, `CAP-007`, and their criteria, plus `A-001` and `A-003`. These restate behaviour the code already has or observations the source already made. Getting one wrong costs an edit.

**High** — `G-001`, `P-001` and its milestone ordering, `C-002` through `C-005`, `ADR-001`, `IDR-001`, `IDR-002`, and `CAP-004`'s and `CAP-006`'s criteria. The plan's milestone order sequences the next several months of work; the constraints bind every future change; the decision records describe reasoning reconstructed from a TODO written after the fact rather than from a discussion at the time. `CAP-004` and `CAP-006` describe behaviour that does not exist, so their criteria are a specification rather than a description, which is the expensive kind of inference to get wrong.

No active capability depends by a single `links:` edge on high-cost inferred meaning: `CAP-004` and `CAP-006` are themselves `status: draft`, and the constraints are cross-cutting rather than joined to a capability by one edge.

## Test-purpose work

There is no existing test to normalize. The repository contains no `src/test` anywhere and no JUnit dependency in either build script, so there are no tags to preserve, no multi-criterion executables to split, and no class-level identities to relocate. Every test this change writes is new and carries exactly one purpose from the start.

The external evidence source at `C:\Code\robocode` is **not** a source corpus and is not edited. Its test robots are read and, subject to Q1, built and consumed. Its own JUnit tests stay where they are; what ports is the expectation each one encodes, restated against both engines.

## Instruction conflicts

One, and it is minor: the `clue-extract` skill directs a new source mapping into the skill's own `mappings/` folder, which is a generated, version-stamped tree that `CLAUDE.md` says not to hand-edit, mirrored across `.agents/skills/` and `.claude/skills/`. Tested during rehearsal — adding a file there does not trip `clue validate`'s drift check. Recorded as `OQ-001` Q3 and proceeding on the instruction's literal reading, with the upgrade risk carried into the extraction report.

`AGENTS.md` and `CLAUDE.md` were both created by `clue init` on this branch; neither pre-existed, so there are no prior assistant instructions to absorb or to conflict with.

## Planned deletions

**None.** `deleted-paths` is empty in the carrier inventory.

This extraction has no parallel specification tree to kill, which is unusual and worth stating plainly rather than leaving as an empty field. The source files are the repository's working documentation, and every one is retained: `README.md` and `compat-test/README.md` stay as reader entry points, and `TODO.md` is reduced to a pointer at `P-001` rather than removed, because it is where a contributor looks first. Nothing here creates a second system of record — after the digest, `TODO.md` states no work item of its own.

## Plan doors

Every deferred criterion names one. No gap is silent.

| Door | Milestone | Criteria waiting on it |
|---|---|---|
| `M-001` | test foundation | the whole `EVT`, `PHY`, and `HARN` namespaces, plus `SCORE-002` and `SCORE-003` |
| `M-002` | score gaps where Tank Royale scores lower | `SCORE-004` |
| `M-003` | score gaps where Tank Royale scores higher | `SCORE-005` |
| `M-004` | file I/O sandboxing | the whole `FIO` namespace |
| `M-005` | team robot support | the whole `TEAM` namespace |
| `M-006` | full sweep across all three divisions | `SCORE-001`, `SCORE-006` |

## Verification run during rehearsal

- `clue validate` — OK, 4 artifacts (the workspace).
- `clue parity changes/CH-001-adopt-cliewen/source-manifest.yaml` — 40 findings, 34 deferred. Every finding is `missing-criterion`, which is correct at rehearsal: the target corpus does not exist yet. Parity must be clean before the extraction is proposed for acceptance.
- `clue carriers changes/CH-001-adopt-cliewen/carrier-inventory.yaml` — OK, 14 entries.

## What stopped here, and what released it

The rehearsal held on `OQ-001`'s two blocking questions and reported. Both are now answered, and the answers are recorded in `OQ-001` for the digest to turn into decision records.

Q1 dissolved rather than resolved: the maintainer develops both engines, so the licensing concern the rehearsal raised did not exist. The same answer directed the tiers at `C:\Code\LiteRumble robots` behind a named constant, with its jars read-only.

Q2 was answered and extended. Official per-division parameters, five repeats averaged, a fifteen-point band — and a rule the rehearsal had not thought to ask about: abort the Tank Royale battle when a bot throws an exception classic did not. That converted `SCORE-003` from a judgment into a verdict and added `HARN-006`.

This report was revised once after those answers. The revision is recorded here rather than silently folded in, because a rehearsal that quietly matches whatever was decided afterwards is not evidence of anything.

The mutate phase is authorized and begins now.
