---
id: AN-365
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: MostlyHarmless's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-365 — MostlyHarmless's prior melee errors clear in five current pairs

## Risk investigated

Whether `com.spp.robocode.MostlyHarmless_010.jar`'s historical melee error imbalance persists under the latest matched artifacts, and whether the current results identify a bridge defect.

## Evidence boundary

The population is this single eligible M-006 registry row, `meleerumble/com.spp.robocode.MostlyHarmless_010.jar`, selected because its prior status was `DISCREPANCY (errors)`. The subject jar is read-only and has SHA-256 `79e3073e986fc6cdf59da91bb4f2b4c271770c7b31600fd2eafebb20bfca2710`; the run manifest records the selected opponent jars and hashes. The five-pair confirmation `c568c94da63c497f` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ce11db7e8e357113686351dd333e4ad3a918849d`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The local bridge API and wrapper jars and Tank Royale API 1.4.0 and runner jar are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with 10 participants, 35 rounds, and a 1000×1000 arena; this result does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The eligible sample was the official five-pair confirmation under the registry's current setup. All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and one opponent setup, not a population-wide estimate. No confidence interval or significance test was calculated. The registry's 25% threshold is a screening boundary; the measured score delta is a quality observation, not a deterministic acceptance criterion.

## What was tried

Across five pairs, Classic averaged 114,549.8 points and Tank Royale averaged 112,706.6 points, for a −1.62% mean delta. Pair deltas were −2.4%, −1.3%, −1.8%, −1.7%, and −0.9%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures.

The preceding observation `8cde5901af756fec` on 2026-10-07 recorded 478 Classic errors and 30 Tank Royale errors, with a −1.7% score delta. Its signatures included `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; the registry carries a historical harness diagnosis of melee opponent-pool contamination. Neither side reported errors in the current five-pair confirmation. Skipped-turn telemetry was captured in all pairs, with event counts 2, 3, 0, 7, and 10. The registry records bot IDs, but this finding does not attribute those events to MostlyHarmless.

## What was not pursued

The historical counts were not treated as proof that the subject needs a bridge workaround. The recorded signatures and harness diagnosis do not establish that the subject caused the errors, and the current confirmation produced no failures. Five pairs also do not support a broader compatibility claim.

## Finding

MostlyHarmless's prior error imbalance did not recur in this five-pair run, and its mean score delta remains inside the registry's score-noise threshold. This result does not establish why the earlier errors occurred and does not independently prove broad compatibility. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/com.syncleus.robocode.Dreadnaught_0.1.jar` (`DISCREPANCY (errors)`).
