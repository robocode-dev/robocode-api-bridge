---
id: AN-376
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Cinnamon's prior melee errors clear in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-376 — Cinnamon's prior melee errors clear in five current pairs

## Risk investigated

Whether `dans.Cinnamon_1.2.jar`'s historical melee error imbalance persists under the latest matched artifacts, and whether the current results identify a bridge defect.

## Evidence boundary

The population is this single eligible M-006 registry row, `meleerumble/dans.Cinnamon_1.2.jar`, selected because its prior status was `DISCREPANCY (errors)`. The subject jar is read-only and has SHA-256 `cb352fa2412994d4160c5161124036464a48cc73b1725178a7ee048609c8b7da`; the run manifest records the selected opponent jars and hashes. The five-pair confirmation `e77675dcb81697e3` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `b79eed85b7c70bbd5b003af9e7f5c14011ca4132`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The local bridge API and wrapper jars and Tank Royale API 1.4.0 and runner jar are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with 10 participants, 35 rounds, and a 1000×1000 arena; this result does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The eligible sample was the official five-pair confirmation under the registry's current setup. All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and one opponent setup, not a population-wide estimate. No confidence interval or significance test was calculated. The registry's 25% threshold is a screening boundary; the measured score delta is a quality observation, not a deterministic acceptance criterion.

## What was tried

Across five pairs, Classic averaged 115,068.6 points and Tank Royale averaged 109,441.8 points, for a −4.88% mean delta. Pair deltas were −4.7%, −4.8%, −5.1%, −5.0%, and −4.8%. The registry status is `MATCHED (score noise)`, with no bridge-only error signatures. Neither engine reported errors in the current five-pair confirmation.

The preceding observation `68a19df6119cb9ac` on 2026-10-07 recorded 1,216 Classic errors and 30 Tank Royale errors, with a −4.4% score delta. Its signatures included `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; the registry carries a historical harness diagnosis of melee opponent-pool contamination. Skipped-turn telemetry was captured in all pairs, with event counts 13, 3, 6, 10, and 5. The registry records bot IDs, but this finding does not attribute those events to Cinnamon.

## What was not pursued

The historical counts were not treated as proof that Cinnamon needs a bridge workaround. The recorded signatures and harness diagnosis do not establish that the subject caused the errors, and the current confirmation produced no failures. Five pairs also do not support a broader compatibility claim.

## Finding

Cinnamon's prior error imbalance did not recur in this five-pair run, and its mean score delta remains inside the registry's score-noise threshold. This result does not establish why the earlier errors occurred and does not independently prove broad compatibility. No bridge code or rumble-jar change is indicated.

## M-006 handoff

The registry scan found no later `meleerumble/*` row with current status `DISCREPANCY (errors)`; check the remaining M-006 scope before selecting another row.
