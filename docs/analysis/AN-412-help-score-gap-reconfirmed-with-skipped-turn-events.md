---
id: AN-412
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-111]
title: Help's lower Tank Royale score is reconfirmed with unassigned skipped-turn events
provenance: inferred
reversal-cost: low
---

# AN-412 — Help's lower Tank Royale score is reconfirmed with unassigned skipped-turn events

## Risk investigated

Whether `ary.Help_1.0.jar`'s previously confirmed lower Tank Royale score persists under the latest matched artifacts, and whether its historical null-wave failure recurs.

## Evidence boundary

The read-only subject jar has SHA-256 `53b6ea8218bd5d68255505bee581a741a88511e58840a395b855ec8f69c2355d`. The official five-pair confirmation `511ed58efabfda38` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `26f82bcb83360debf53b9fc0310daef363359600`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 4,932.8 points and Tank Royale averaged 3,161.2 points, for a −35.88% mean delta. The five pair deltas were −37.6%, −39.7%, −30.8%, −35.2%, and −36.1%. Neither engine reported errors, no bridge-only signatures were recorded, and the registry status remains `CONFIRMED (score)`.

Skipped-turn telemetry was captured in all five attempts: no events in attempts 1–3, two events in attempt 4 at round 6 turn 1254 for bot IDs 1 and 2, and one event in attempt 5 at round 6 turn 622 for bot ID 1. The record does not identify which participant is Help, so the events are not attributed to the subject. The preceding five-pair observation `802cd57fbdc37a4d` on 2026-10-06 had a −33.32% mean delta and no skipped-turn events. AN-111 records a separate historical robot-owned null-wave exception; that exception did not recur in this confirmation, and the current telemetry does not explain the score gap.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Help from aggregate scores or unassigned telemetry. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Help's lower Tank Royale score remains confirmed at −35.88%, compared with −33.32% in the preceding five-pair result. The historical null-wave error did not recur; three skipped-turn events were captured in two runs but cannot be attributed to Help from this record.

## M-006 handoff

Continue in registry order with `roborumble/as.xbots_1.0.jar` (`DISCREPANCY (score)`).
