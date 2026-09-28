---
id: TASKS-010
type: tasks
status: open
links: [CH-017]
title: CH-017 implementation tasks
---

# Tasks

- [x] Confirm the post-CH-016 registry frontier, discoverable collection, official parameters, pinned melee pool, and available engine artifacts; serves `SCORE-006`.
- [x] Correct normal checkpoint synchronization to append only subjects completed in the current invocation, and add regression evidence preventing replay of accumulated progress; serves `HARN-001` and `SCORE-006`.
- [ ] Run the remaining official-parameter roborumble and melee subjects in bounded checkpoints, preserving every completed observation and its artifact manifest in the tracked registry; serves `SCORE-001` and `SCORE-006`. The first additional official melee checkpoint completed on 2026-09-28 with 25 observations from `compat_test.py`: 21 error discrepancies and 4 outcome discrepancies. The full collection sweep remains in progress.
- [ ] Diagnose every unresolved bridge-versus-classic error, completion, hang, or confirmed score gap, including opponent-contaminated melee outcomes, and record each diagnosis without rewriting earlier evidence; serves `SCORE-001`.
- [ ] Implement and verify each bridge-owned repair supported by the diagnoses, including the classic five-open-stream limit and legacy team-message serialization failure shape where still applicable; serves `SCORE-001`.
- [x] Resolve nested member-jar discovery for classic team archives and retest every diagnosed team entry; serves `SCORE-001`.
- [x] Restore classic team identity through generated bot metadata and the Tank Royale name map; prove `TEAM-002` with positive and negative two-engine evidence; serves `TEAM-002`, `P-001#M-005`, and `P-001#M-006`.
- [ ] Run a focused retest for every named repair or record the external blocker, then sync the retest observations and generated report; serves `SCORE-001`.
- [ ] Update the permanent plan, CAP-005 evidence/design bookkeeping, generated indexes, and user-facing changelog to reflect only what the completed registry proves; serves `P-001#M-006`, `SCORE-001`, and `SCORE-006`.
