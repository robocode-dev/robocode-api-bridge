---
id: AN-366
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Dreadnaught's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-366 — Dreadnaught's prior melee errors clear in five current pairs

## Risk investigated

Whether `com.syncleus.robocode.Dreadnaught_0.1.jar`'s historical melee error imbalance persists under the latest matched artifacts, and whether the current results identify a bridge defect.

## Evidence boundary

The population is this single eligible M-006 registry row, `meleerumble/com.syncleus.robocode.Dreadnaught_0.1.jar`, selected because its prior status was `DISCREPANCY (errors)`. The subject jar is read-only and has SHA-256 `4a2a9926375fca4cf32ea72bdc8ab43930223f2090bae428474eafb9a6dd823c`; the run manifest records the selected opponent jars and hashes. The five-pair confirmation `338c113931e578be` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ce11db7e8e357113686351dd333e4ad3a918849d`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The local bridge API and wrapper jars and Tank Royale API 1.4.0 and runner jar are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with 10 participants, 35 rounds, and a 1000×1000 arena; this result does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The eligible sample was the official five-pair confirmation under the registry's current setup. All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and one opponent setup, not a population-wide estimate. No confidence interval or significance test was calculated. The confirmation classification uses `REGRESSION_BAND_POINTS = 15.0` percentage points against the mean delta; the manifest's `threshold: 25.0` records the regular sweep setting and is not the five-pair confirmation gate. The measured score delta is a quality observation, not a deterministic acceptance criterion.

## What was tried

Across five pairs, Classic averaged 117,141.8 points and Tank Royale averaged 114,353.6 points, for a −2.36% mean delta. Pair deltas were −3.8%, −1.9%, −1.2%, −2.6%, and −2.3%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures. Neither engine reported errors in the current five-pair confirmation.

The preceding observation `7504f3f0550a9a51` on 2026-10-07 recorded 384 Classic errors and 32 Tank Royale errors, with a −2.5% score delta. Its signatures included `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; the registry also carries a historical harness timeout-overrun diagnosis. Skipped-turn telemetry was captured in all pairs, with event counts 0, 0, 7, 0, and 4. The registry records bot IDs, but this finding does not attribute those events to Dreadnaught.

## What was not pursued

The historical counts were not treated as proof that Dreadnaught needs a bridge workaround. The recorded signatures and harness diagnoses do not establish that the subject caused the errors, and the current confirmation produced no failures. Five pairs also do not support a broader compatibility claim.

## Finding

Dreadnaught's prior error imbalance did not recur in this five-pair run, and its mean score delta remains inside the confirmation's 15.0-point score-noise band. This result does not establish why the earlier errors occurred and does not independently prove broad compatibility. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/conscience.Electron_1.3g.jar` (`DISCREPANCY (errors)`).
