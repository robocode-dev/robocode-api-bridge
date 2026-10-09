---
id: AN-375
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Nimrod's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-375 — Nimrod's prior melee errors clear in five current pairs

## Risk investigated

Whether `cx.mini.Nimrod_0.55.jar`'s historical melee error imbalance persists under the latest matched artifacts, and whether the current results identify a bridge defect.

## Evidence boundary

The population is this single eligible M-006 registry row, `meleerumble/cx.mini.Nimrod_0.55.jar`, selected because its prior status was `DISCREPANCY (errors)`. The subject jar is read-only and has SHA-256 `9ccc75f18f1ea296fefe441150fd552e546989e1e42af4abdc578f297564a321`; the run manifest records the selected opponent jars and hashes. The five-pair confirmation `4c542f596559390b` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `b79eed85b7c70bbd5b003af9e7f5c14011ca4132`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The local bridge API and wrapper jars and Tank Royale API 1.4.0 and runner jar are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with 10 participants, 35 rounds, and a 1000×1000 arena; this result does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The eligible sample was the official five-pair confirmation under the registry's current setup. All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and one opponent setup, not a population-wide estimate. No confidence interval or significance test was calculated. The confirmation classification uses `REGRESSION_BAND_POINTS = 15.0` percentage points against the mean delta; the manifest's `threshold: 25.0` records the regular sweep setting and is not the five-pair confirmation gate. The measured score delta is a quality observation, not a deterministic acceptance criterion.

## What was tried

Across five pairs, Classic averaged 113,896.2 points and Tank Royale averaged 113,609.4 points, for a −0.26% mean delta. Pair deltas were +0.3%, −0.9%, +0.8%, −1.3%, and −0.2%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures. Neither engine reported errors in the current five-pair confirmation.

The preceding observation `2c47e2c1e6569b32` on 2026-10-07 recorded 280 Classic errors and 31 Tank Royale errors, with a +0.1% score delta. Its signatures included `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; the registry carries a historical harness diagnosis of melee opponent-pool contamination. Skipped-turn telemetry was captured in all pairs, with event counts 7, 4, 3, 0, and 0. The registry records bot IDs, but this finding does not attribute those events to Nimrod.

## What was not pursued

The historical counts were not treated as proof that Nimrod needs a bridge workaround. The recorded signatures and harness diagnosis do not establish that the subject caused the errors, and the current confirmation produced no failures. Five pairs also do not support a broader compatibility claim.

## Finding

Nimrod's prior error imbalance did not recur in this five-pair run, and its mean score delta remains inside the confirmation's 15.0-point score-noise band. This result does not establish why the earlier errors occurred and does not independently prove broad compatibility. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/dans.Cinnamon_1.2.jar` (`DISCREPANCY (errors)`).
