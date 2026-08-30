---
id: TASKS-001
type: tasks
status: open
links: [CH-001]
title: CH-001 task checklist
---

# CH-001 — tasks

Ordered; dependencies first. The rehearsal pass is mandatory and blocks every mutation task below it.

## Rehearsal (report-only)

- [x] Write the source mapping for this repository's format (ad-hoc markdown: a prose TODO, a project README, a harness README, and a generated compatibility report) — no mapping exists for it yet, and writing one is the extraction's first task
- [x] Write `rehearsal.md`: source inventory, proposed artifact mappings, minted IDs, confidence and reversal cost, test-purpose work, instruction conflicts, planned deletions, plan doors
- [x] Write `source-manifest.yaml`: pinned source revision and location, one disposition row per minted criterion with justification, disposition source location, and plan door
- [x] Write `carrier-inventory.yaml`: pinned revision, deleted paths, and one row per operational carrier (instructions, workflows, registries, links, diagram assets)
- [x] Record any conflict found during rehearsal in `open-questions.md` and stop before mutation
- [x] Report to the human and wait for explicit authorization to begin the mutate phase — both blocking questions answered, mutate phase authorized
- [x] Revise the pinned manifest and this checklist for the answers: official per-division parameters, five repeats, fifteen-point band, abort on a Tank-Royale-only exception, and the melee division the harness has never run

## Corpus (mutate phase; blocked on authorization)

- [ ] Write `G-001` — legacy Robocode robots run unmodified on Tank Royale and behave as they do on classic Robocode
- [ ] Write `P-001` with milestones M-001 test foundation, M-002 score gaps where Tank Royale scores lower, M-003 score gaps where it scores higher, M-004 file I/O sandboxing, M-005 team support, M-006 full sweep
- [ ] Write the constraints: `C-002` bot-api protocol compatibility with the runner's embedded server, `C-003` official per-division rumble parameters used unmodified, `C-004` the regression gate (five repeats averaged, fifteen-point band, abort on a Tank-Royale-only exception), `C-005` robot writes confined to the data directory, `C-006` Tank Royale Bot API changes spanning all four languages, `C-007` rumble jars never modified and read only for debugging
- [ ] Write `CAP-001` event dispatch and timing parity (`ac-prefix: EVT`) — README, criteria, design
- [ ] Write `CAP-002` robot physics and state parity (`ac-prefix: PHY`) — README, criteria, design
- [ ] Write `CAP-003` Robocode API surface fidelity (`ac-prefix: API`) — README, criteria, design
- [ ] Write `CAP-004` robot file I/O sandboxing (`ac-prefix: FIO`) — README, criteria, design; every criterion `@draft`, plan door M-004
- [ ] Write `CAP-005` score parity across the rumble collection (`ac-prefix: SCORE`) — README, criteria, design
- [ ] Write `CAP-006` team robot support (`ac-prefix: TEAM`) — README, criteria, design; every criterion `@draft`, plan door M-005
- [ ] Write `CAP-007` the compatibility harness (`ac-prefix: HARN`) — README, criteria, design
- [ ] Write the retroactive decision records: routing events through the Bot API `EventQueue`, the 0.33.1 to 1.0.2 upgrade, `getDataDirectory()` resolution, and the three-tier test strategy
- [ ] Write the analysis findings: `ad.last.Bottom` score noise requires multi-run averaging, and Tank Royale exposes no deterministic RNG seed
- [ ] Write the extraction report under `docs/analysis/` with its derived region rendered by `clue report`

## Test tier 1 — unit (serves API-001 onward)

- [ ] Add a JUnit 5 test source set to `robocode-api/build.gradle.kts`
- [ ] Write mapper unit tests under `robocode-api/src/test/java`, one purpose per executable, named for the criterion each proves
- [ ] Promote the criteria those tests prove from `@draft` to active

## Test tier 2 — semantic conformance (serves EVT-001 onward and PHY-001 onward)

- [ ] Add the `conformance-test` module to `settings.gradle.kts` and its build script, resolving classic Robocode and the Tank Royale runner by property and skipping when either is absent
- [ ] Write `ClassicTestBed` over the Control API with `-DRANDOMSEED=0`, factored from `compat-test/RcBattleWorker.java`
- [ ] Write `BridgeTestBed` over the Battle Runner API with an embedded server, factored from `compat-test/TrBattleWorker.java`
- [ ] Write the conformance tests that run one expectation against both beds, covering the event-semantics and physics groups
- [ ] Promote the criteria those tests prove from `@draft` to active

## Test tier 3 — statistical regression (serves SCORE-001 onward and HARN-001 onward)

- [ ] Add a `COLLECTION_ROOT` constant for `C:\Code\LiteRumble robots` and per-division setup constants read from the official rumble parameters (serves HARN-007)
- [ ] Add the three division setups to `compat-test/compat_test.py`: RoboRumble 800x600 at 35 rounds, MeleeRumble 1000x1000 at 35 rounds with ten bots, TeamRumble 1200x1200 at 10 rounds (serves HARN-007, SCORE-006, EVT-010)
- [ ] Abort the Tank Royale battle as soon as an exception signature appears that the classic side did not produce, and record the signature (serves HARN-006, SCORE-003)
- [ ] Write `compat-test/regression-set.json` seeded from `compatibility_report.md`, each entry carrying its baseline, its division, and a state of open, fixed, or noise (serves SCORE-002)
- [ ] Add `--regression` with five averaged repeats and a fifteen-point band, `--repeats`, and `--trace` to `compat-test/compat_test.py` (serves SCORE-002, HARN-004, HARN-005)
- [ ] Update `compat-test/README.md` for the division setups, the modes, and the read-only collection rule

## Routing, CI, and integration

- [ ] Fill the repo-local conventions section of `AGENTS.md`: toolchain, build and test commands, and where each test tier runs
- [ ] Review the generated `.github/workflows/clue.yml`, pin the `clue-version` exactly as generated, and add the tier 1 Gradle test job
- [ ] Reduce `TODO.md` to a pointer at `P-001`
- [ ] Run `clue parity` against the source manifest and resolve every finding
- [ ] Run `clue carriers` against the carrier inventory and resolve every finding
- [ ] Run `clue-verify` including its agentic review loop
