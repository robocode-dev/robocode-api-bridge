---
id: AN-466
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: EEM.awful still produces no scores under current artifacts
provenance: inferred
reversal-cost: low
---

# AN-466 — EEM.awful still produces no scores under current artifacts

## Risk investigated

Whether `eem.awful_v1.2.jar`'s historical zero-score outcome persists under the current matched artifacts, and whether its earlier repeated null-pointer errors recur.

## Evidence boundary

The read-only subject jar has SHA-256 `4c64fc4c02da3db0aa5840312b72d080c1275374746a441e0deea03d623d7c11`. The official five-attempt retry `2591efac30b6fa62` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `f1e3d67ca92799db2af4a4eb06ffffa16ab0e022`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used different bridge and Tank Royale artifacts and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts scored 0 for both engines. The confirmation contains zero positive score samples, so no pair delta or mean score gap was calculated. Both engine records have `ok: false` and empty error-text arrays; the current record lists no exception signatures. Skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The jar remained read-only.

## What was tried

The registry status is `DISCREPANCY (outcome)`. The earlier observation `59537630aafb178b` also recorded 0–0 scores, but both engines had 70 `NullPointerException` entries from `eem.awful.run` and were marked `ok: true`. The current run retains the zero-score outcome with current artifacts, while its records do not list that earlier exception signature; both current `ok` fields are false.

## What was not pursued

The current record does not explain the zero scores or why both `ok` fields are false despite empty error-text arrays and captured telemetry. The absence of the prior signature from the retained fields does not prove that the robot's null-pointer condition was corrected. No code, source, or rumble-jar change was made.

## Finding

EEM.awful again scored 0–0 in all five attempts with current artifacts and has no usable score samples. Its earlier repeated `NullPointerException` signature is not present in the current record, but the zero-score outcome persists and its cause remains unresolved.

## M-006 handoff

Skip `roborumble/florent.small.LittleAngel_1.8.jar` (`MATCHED (score noise)`). Continue in registry order with `roborumble/genprog.Rinmorikazu_1.0.jar` (`DISCREPANCY (no score)`).
