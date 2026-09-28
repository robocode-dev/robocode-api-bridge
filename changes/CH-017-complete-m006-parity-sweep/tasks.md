---
id: TASKS-010
type: tasks
status: open
links: [CH-017]
title: CH-017 implementation tasks
---

# Tasks

## Latest checkpoint

The second official melee checkpoint completed on 2026-09-28 with 25 observations: 20 error discrepancies, 4 outcome discrepancies, and 1 pass. The fixed-opponent contamination diagnosis was appended for all 25 measured subjects.

Dreadnaught's first Tank Royale run produced no result and recorded 8,139 seconds against the configured 600-second per-side timeout. The 36.1-second same-setup retest predates its diagnosis event and repair commit. After the timeout fix in `4d0fa88`, a linked official retest completed in 60.6 seconds on Tank Royale and recorded the opponent-contaminated error result (`e60cf1d52f296445`). The original observations remain intact in the append-only registry.

The three Tank-Royale-only subject exceptions were each reproduced under the same official 35-round, 1000-by-1000, ten-participant setup and pinned nine-opponent pool. Firestarter again threw `NullPointerException` in `C.I.I` (`0b2c1f3d52ecd23f`); RiOx again threw `ArrayIndexOutOfBoundsException: Index 9 out of bounds for length 9` in `cf.OPs.RiOxM_OP.onScannedRobot` (`491778d42b27fa0a`); and B26354 again threw `NullPointerException` in `darkcanuck.m.a` (`b336f45184cf831d`). The harness stopped each Tank Royale battle at the first error without a classic counterpart, as required; these runs establish reproducibility but not complete scores or root cause.

One earlier Firestarter retake recorded uncertain Tank Royale process-tree cleanup. The instrumented retry and the RiOx and B26354 retakes stopped with cleanup confirmed. The three bot exceptions remain unresolved and the full collection sweep remains in progress.

A separate trace-enabled RiOx diagnostic rerun captured the failure trigger at turn 239: `RobotDeathEvent(victim=3)` was delivered before `ScannedRobotEvent(target=3)` with `others=8`, immediately followed by RiOx's index-9 exception. All nine opponent IDs had already been scanned, and the post-death scan reused ID/name `3`; the trace therefore disproves the earlier tenth-distinct-name hypothesis. Both event queues prioritize death over scans when their time/turn keys match. The unresolved comparison is whether classic generates and delivers this same death/scan pair in one event time; the measured classic battle had no RiOx callback exception. The diagnostic was run in a detached scratch worktree and was not added to the registry.

Three focused official 35-round Roborumble replays of Fermat recorded no stream errors with round-boundary cleanup (`a00859152f1287d2`) and five-stream exceptions without it (`7b65988ff7e71a41`, 2 errors; `49b5fb75c478cf4b`, 7 errors). Stream tracing showed repeated unclosed `Fermat.data/*.txt` streams after the bot's `DataWriter` printed `Could not write Data`; its exception handler does not close the current stream, and the underlying exception is not logged. The repair now closes abandoned streams before the next round starts, after the previous main bot thread has stopped, while preserving the five-stream cap within a round. Three repair-linked official retests (`d5d2e35d49eabd9a`, `11ec421d416c630c`, and `ff31ace0ad193807`, repair `796a170`) completed all 35 rounds with zero errors on both engines; Fermat remains in score review at -43.2%, -41.2%, and -32.6%.

The Colossus2 observation (`aafa949ac07b6f62`) had 180 Tank Royale scan callback array errors and none on classic. Its bytecode uses reference equality on scan target names and appends every unmatched name to a 20-slot array; the bridge created a fresh numeric-id string per scan. The mapper now uses the classic name map and interns both mapped and fallback names. Unit coverage passes. The repair-linked official 35-round retest (`9c81fee190364e22`, repair `255fcb3`) completed with zero errors on both engines and `PASS` status at -12.9% score delta. Turn-by-turn skipped-turn data was not captured for this observation.

The official `ag.Gir_0.99.jar` observation (`e3c870e44a2cc066`) recorded 18 Tank Royale quota and downstream data-read errors and none on classic. Classic releases the prior file size from quota usage before a non-append open; the bridge did not, so Gir's repeated learned-data rewrites accumulated against the 200,000-byte quota. The bridge now applies that adjustment, and the FIO-003 overwrite regression passes on both engines. Its repair-linked official retest (`d61ab1a5cabc06c8`, repair `3878254`) removed quota errors but retained 12 `Stream Closed`, EOF, and null-network errors. The second cause, `round-end-stream-close-race`, records that cleanup ran before Tank Royale's internal stop/join; cleanup now runs at the next round start. Its repair-linked official retest is pending.
- [x] Confirm the post-CH-016 registry frontier, discoverable collection, official parameters, pinned melee pool, and available engine artifacts; serves `SCORE-006`.
- [x] Correct normal checkpoint synchronization to append only subjects completed in the current invocation, and add regression evidence preventing replay of accumulated progress; serves `HARN-001` and `SCORE-006`.
- [ ] Run the remaining official-parameter roborumble and melee subjects in bounded checkpoints, preserving every completed observation and its artifact manifest in the tracked registry; serves `SCORE-001` and `SCORE-006`. The first additional official melee checkpoint completed on 2026-09-28 with 25 observations from `compat_test.py`: 21 error discrepancies and 4 outcome discrepancies. The full collection sweep remains in progress.
- [ ] Diagnose every unresolved bridge-versus-classic error, completion, hang, or confirmed score gap, including opponent-contaminated melee outcomes, and record each diagnosis without rewriting earlier evidence; serves `SCORE-001`.
- [ ] Implement and verify each bridge-owned repair supported by the diagnoses, including the classic five-open-stream limit and legacy team-message serialization failure shape where still applicable; serves `SCORE-001`.
- [x] Resolve nested member-jar discovery for classic team archives and retest every diagnosed team entry; serves `SCORE-001`.
- [x] Restore classic team identity through generated bot metadata and the Tank Royale name map; prove `TEAM-002` with positive and negative two-engine evidence; serves `TEAM-002`, `P-001#M-005`, and `P-001#M-006`.
- [ ] Run a focused retest for every named repair or record the external blocker, then sync the retest observations and generated report; serves `SCORE-001`.
- [ ] Update the permanent plan, CAP-005 evidence/design bookkeeping, generated indexes, and user-facing changelog to reflect only what the completed registry proves; serves `P-001#M-006`, `SCORE-001`, and `SCORE-006`.
