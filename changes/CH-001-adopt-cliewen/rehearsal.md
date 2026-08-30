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
| `compat-test/compatibility_report.md` | generated output | Sixteen tested jars with scores, deltas, error counts, and statuses |
| `C:\Code\robocode` at `1e70188f3` | external evidence source | 48 JUnit tests over `RobotTestBed` driving 46 purpose-built test robots |

Two observations about the source that shape everything below.

**`compatibility_report.md` is untracked.** `compat-test/.gitignore` excludes it along with `errors/`, `work/`, and `test_progress.json`. The repository's only body of compatibility evidence therefore has no committed revision, exists solely in whoever last ran the sweep's working tree, and cannot be pinned. It is recorded as a blocked carrier for that reason, and the gap is real rather than procedural: the corpus is about to name evidence that no reviewer can open.

**The TODO's two halves disagree.** Its "DONE" section reports the event-dispatch redesign as landed and lists the score results that followed, while items 2 and 3 list score gaps and instruct the reader to re-test first because those numbers predate the redesign. Both are true and the disagreement is the finding: eight flagged bots currently have no interpretable status. Per the mapping's guidance, both are extracted and the disagreement becomes an analysis record rather than being resolved by picking whichever reads more recent.

## Proposed mapping

| Source | Target |
|---|---|
| `README.md` purpose statement | `G-001`, `status: accepted` |
| `README.md` component breakdown and Mermaid diagram | `docs/architecture/`, diagram carried across unchanged |
| `TODO.md` six numbered items, in their own order | `P-001` with milestones `M-001` through `M-006` |
| `TODO.md` "DONE" section | `IDR-001` (EventQueue routing), `ADR-001` (0.33.1 to 1.0.2), and the EVT criteria the redesign touched |
| `TODO.md` item 1's `getDataDirectory()` note | `IDR-002` |
| `TODO.md` item 4 | `CAP-004`, all criteria `@draft` |
| `TODO.md` item 5 | `CAP-006`, all criteria `@draft` |
| `TODO.md` noise observations | `A-001` |
| `README.md` compatibility claim | `CAP-001`, `CAP-002`, `CAP-003` |
| `compat-test/README.md` methodology and status vocabulary | `CAP-007` README, criteria, and design |
| `compat-test/README.md` bot-api pairing warning and score threshold | `C-002`, `C-003` |
| `compat-test/compatibility_report.md` flagged rows | the regression watch list plus `SCORE-004` and `SCORE-005` |
| Tank Royale's absent RNG seed | `A-002` |
| Python's absence from the supported carriers | `A-003` |

`TODO.md`'s Tank Royale-side section describes work committed in a different repository. It is not extracted as an artifact here — the corpus of another repository is not this one's to hold — but it seeds `C-005`, the constraint that Bot API changes span all four language implementations.

## Minted IDs

Forty criteria across seven namespaces. **No ID is preserved, because the source has none.** Every one is minted, each namespace starts at one, and the corpus becomes the registry. The per-criterion detail lives in `source-manifest.yaml`; it is not duplicated here or in any second committed registry.

| Capability | Prefix | Count | Source |
|---|---|---:|---|
| `CAP-001` event dispatch and timing parity | `EVT` | 9 | `TODO.md` DONE section |
| `CAP-002` robot physics and state parity | `PHY` | 8 | `README.md` compatibility claim |
| `CAP-003` Robocode API surface fidelity | `API` | 6 | the bridge's mapper classes |
| `CAP-004` robot file I/O sandboxing | `FIO` | 4 | `TODO.md` item 4 |
| `CAP-005` score parity across the rumble collection | `SCORE` | 5 | `TODO.md` items 2, 3, 6 |
| `CAP-006` team robot support | `TEAM` | 3 | `TODO.md` item 5 |
| `CAP-007` the compatibility harness | `HARN` | 5 | `compat-test/README.md` |

One promise the source does not make is deliberately not minted: exact per-turn position parity with classic. Classic can assert it because `-DRANDOMSEED` makes a battle reproducible; Tank Royale exposes no seed, so the promise is unmeetable and inventing a criterion for it would manufacture a permanent draft. `A-002` records the gap instead.

## Dispositions

Thirty-four of forty criteria resolve `@draft`. That is the honest reading of a repository that documented its intent carefully and verified it by running battles and reading scores, and it is the single most useful thing this extraction produces: it converts "the bridge is broadly compatible" into forty named promises of which six currently have any attributable proof.

- **Six become machine-proven** — the `API` namespace, `Test-type: Unit`, both directions. These are pure functions over value objects with no engine dependency, so the extraction commits to building their evidence rather than deferring it.
- **Two become `Test-type: Human`** — `SCORE-001` and `SCORE-003`. A maintainer reads the sweep report and judges population parity and whether an error signature is a bridge defect. That is genuine human judgment on a statistical instrument, not a placeholder.
- **Thirty-two become `@draft`**, each naming its plan door. The `HARN` five are worth singling out: they describe mechanical properties that deserve machine checks, and they are draft only because the harness is Python and Cliewen supports Go, JVM, and Cucumber carriers. Badging them Human would launder a missing test as a judgment call, so they stay draft and `A-003` records why.

## Confidence and reversal cost

Everything extracted is born `provenance: inferred`. Reversal cost splits as follows.

**Low** — `CAP-001`, `CAP-002`, `CAP-003`, `CAP-007`, and their criteria, plus `A-001` and `A-003`. These restate behaviour the code already has or observations the source already made. Getting one wrong costs an edit.

**High** — `G-001`, `P-001` and its milestone ordering, `C-002` through `C-005`, `ADR-001`, `IDR-001`, `IDR-002`, and `CAP-004`'s and `CAP-006`'s criteria. The plan's milestone order sequences the next several months of work; the constraints bind every future change; the decision records describe reasoning reconstructed from a TODO written after the fact rather than from a discussion at the time. `CAP-004` and `CAP-006` describe behaviour that does not exist, so their criteria are a specification rather than a description, which is the expensive kind of inference to get wrong.

No active capability depends by a single `links:` edge on high-cost inferred meaning: `CAP-004` and `CAP-006` are themselves `status: draft`, and the constraints are cross-cutting rather than joined to a capability by one edge.

## Test-purpose work

There is no existing test to normalize. The repository contains no `src/test` anywhere and no JUnit dependency in either build script, so there are no tags to preserve, no multi-criterion executables to split, and no class-level identities to relocate. Every test this change writes is new and carries exactly one purpose from the start.

The external evidence source at `C:\Code\robocode` is **not** a source corpus and is not edited. Its 46 test robots are read and, subject to Q1, built and consumed. Its own JUnit tests stay where they are; what ports is the expectation each one encodes, restated against both engines.

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
| `M-001` | test foundation | all 9 `EVT`, all 8 `PHY`, `SCORE-002`, all 5 `HARN` |
| `M-002` | score gaps where Tank Royale scores lower | `SCORE-004` |
| `M-003` | score gaps where Tank Royale scores higher | `SCORE-005` |
| `M-004` | file I/O sandboxing | all 4 `FIO` |
| `M-005` | team robot support | all 3 `TEAM` |
| `M-006` | full compatibility sweep | `SCORE-001`, `SCORE-003` |

## Verification run during rehearsal

- `clue validate` — OK, 4 artifacts (the workspace).
- `clue parity changes/CH-001-adopt-cliewen/source-manifest.yaml` — 40 findings, 34 deferred. Every finding is `missing-criterion`, which is correct at rehearsal: the target corpus does not exist yet. Parity must be clean before the extraction is proposed for acceptance.
- `clue carriers changes/CH-001-adopt-cliewen/carrier-inventory.yaml` — OK, 14 entries.

## What stops here

`OQ-001` holds two blocking questions. Q1 decides whether tier two builds classic's test robots from source or vendors them, which changes what the conformance module looks like and whether it can ever run in CI. Q2 decides the regression gate's numbers, which the rehearsal can only guess at. Neither blocks the corpus, and both block the test tiers that give the corpus its evidence.

The mutate phase begins on explicit human authorization and not before.
