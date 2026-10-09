---
id: AN-368
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Grudge's current confirmation stops in a selected opponent
provenance: inferred
reversal-cost: low
---

# AN-368 — Grudge's current confirmation stops in a selected opponent

## Risk investigated

Whether `cs.Grudge_1.0.jar`'s historical melee error imbalance persists under the latest matched artifacts, and whether its current failed attempt identifies a bridge defect.

## Evidence boundary

The population is this single eligible M-006 registry row, `meleerumble/cs.Grudge_1.0.jar`, selected because its prior status was `DISCREPANCY (errors)`. The subject jar is read-only and has SHA-256 `5bb0defb7937b13e54cdeaefcdbaa374bb6ac6bfefa13cf0d992bd5ad4f22f2d`; the run manifest records the selected opponent jars and hashes. The partial confirmation `a01a9ab9b8056a98` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ce11db7e8e357113686351dd333e4ad3a918849d`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The local bridge API and wrapper jars and Tank Royale API 1.4.0 and runner jar are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with 10 participants, 35 rounds, and a 1000×1000 arena; this result does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The eligible sample was the current registry setup for this subject. The confirmation requested five attempts; it reached three attempts, produced two score samples, and the third attempt ended before producing a sample. No sample was discarded. This is a partial repeated measurement of one subject and one opponent setup, not a population-wide estimate. No confidence interval or significance test was calculated. The registry's 25% threshold is a screening boundary; the two-pair partial score delta is a quality observation and cannot classify a five-pair confirmation.

## What was tried

The two completed pairs averaged 116,568.0 points in Classic and 113,254.5 in Tank Royale, for a partial mean delta of −2.85%. Pair deltas were −2.6% and −3.1%. Neither engine reported errors in those two completed samples.

The third attempt stopped on `java.util.ConcurrentModificationException` at `amk.ShizzleStiX.Navigator.run`, a class in the selected opponent pool. The registry places this signature in `bridge_only_signatures`, but its recorded origin is the opponent class; that field alone does not establish bridge ownership. Skipped-turn telemetry was captured for the two completed attempts with event counts 0 and 6; telemetry for the third attempt is incomplete.

The preceding observation `17580fcdda9a3a94` on 2026-10-07 recorded 372 Classic errors and 30 Tank Royale errors, with a −2.3% score delta. Its signatures included `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`; the registry carries a historical harness diagnosis of melee opponent-pool contamination.

## What was not pursued

The registry's `bridge_only_signatures` label was not treated as proof of a bridge bug because the signature origin names the selected `amk.ShizzleStiX.Navigator` opponent class. The partial two-pair score result was not generalized to a five-pair confirmation, and no bridge or rumble-jar change was made from this evidence.

## Finding

Grudge's earlier error imbalance was absent from the two completed samples, but its current confirmation remains incomplete because a selected opponent raised `ConcurrentModificationException` on the third attempt. This run does not establish the cause of the older error imbalance or a bridge defect. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/cs.Wren_1.0.jar` (`DISCREPANCY (errors)`).
