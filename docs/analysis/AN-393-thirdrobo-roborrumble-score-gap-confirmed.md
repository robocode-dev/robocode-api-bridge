---
id: AN-393
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ThirdRobo's lower RoboRumble score persists in five-pair confirmation
provenance: inferred
reversal-cost: low
---

# AN-393 — ThirdRobo's lower RoboRumble score persists in five-pair confirmation

## Risk investigated

Whether `roborumble/abud.ThirdRobo_1.0.jar`'s earlier lower Tank Royale score persists under the latest matched artifacts, and whether the retest identifies its cause.

## Evidence boundary

The read-only subject jar has SHA-256 `124b7067cca1b407845176fd26ad2388aab1ad20085b34bd5561539515272a16`. The official five-pair confirmation `1153e1e8f3883bad` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `916db0d2d23193d1651554d1980254cef9f55e25`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 6,097.6 points and Tank Royale averaged 3,789.6 points, for a −37.74% mean delta. The five pair deltas were −30.2%, −37.7%, −37.1%, −40.4%, and −43.3%. The registry status is `CONFIRMED (score)`. The current report row records zero errors for each engine, the confirmation has no bridge-only signatures, and skipped-turn telemetry was captured with zero events in all five attempts.

The preceding observation `ed9b79a036d61bd5` on 2026-09-28 had a −42.6% delta, 328 Classic errors, and 16,873 Tank Royale errors, including repeated `NotSerializableException: abud.EnemyInfo` records. The latest five-pair result confirms a similar-sized score gap without current report errors. This temporal association does not establish that the earlier serialization errors caused the score difference.

## What was not pursued

Aggregate scores do not locate the divergent behavior, so no cause was assigned to the bridge, Tank Royale, or ThirdRobo. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

ThirdRobo's lower Tank Royale score remains confirmed under the newer matched artifacts at −37.74%, compared with −42.6% in the preceding observation. The current report records no runtime errors, and the run does not identify the cause of the score gap.

## M-006 handoff

Continue in registry order with `roborumble/acid.Bl4ck_1.0.jar` (`DISCREPANCY (score)`).
