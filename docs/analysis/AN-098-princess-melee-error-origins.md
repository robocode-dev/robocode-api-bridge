---
id: AN-098
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-097]
title: Princess's melee errors include pinned-pool failures and a matching missing-file message
provenance: inferred
reversal-cost: low
---

# AN-098 — Princess's melee errors include pinned-pool failures and a matching missing-file message

## Risk investigated

Whether Princess's current melee error discrepancy identifies a bridge defect, a subject failure, or the fixed opponent pool, and whether its score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `cx.Princess_1.0.jar` has SHA-256 `8a5137dcb2d3075bf8b4aa5ec609d499a981f1ed6650641a10e3c95cfe2e7cec`. The current official one-pair observation `b9e6b88d0f56d7ee` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `ea6d17c49b712de2db4756d6f7cbc383eea08bee`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The matched local Bot API and runner artifacts are recorded in the observation; the runner SHA-256 is `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`. The subject and opponent jars were not changed.

## What was tried

The harness force-ran Princess at official parameters. Classic scored 114,012 with 265 errors; Tank Royale scored 112,160 with one error. The single-pair score delta is −1.6%, inside the 25% review threshold, so it does not confirm a score gap. The Classic log `compat-test/errors/robocode/cx.Princess_1.0.log` traces the `ArrayIndexOutOfBoundsException` to `amk.guns.Aristocles.prepare` called by `amk.ChumbaWumba.onScannedRobot`, and traces the five-stream `SecurityException` to `amk.ChumbaMini.saveData`. AN-015 identifies ChumbaWumba and ChumbaMini as members of the pinned opponent pool.

The sole Tank Royale error is `FileNotFoundException` for `Princess.data/score.dat`. The Classic log records the same missing subject data-file path during round 1. This is a matching first-run observation in the clean per-engine work directories, not a Tank Royale-only bridge error. The error-origin summary labels it `unknown`, but the logged path identifies Princess's own data directory.

The older melee observation `011b7e15640d6efa` used bridge commit `2336f3b50339f6d84c62ccaaa8b7cdb06cf5a82c` and Tank Royale commit `8dd449aceb640b754b239e5ec4d301ebf423756d`; it recorded 303 Classic errors and 32 Tank Royale errors, including ChumbaMini fixture errors on both engines. The current matched local artifacts reduce Tank Royale's count to one; the remaining error is the matching first-run missing-file message.

## Finding

The current Classic errors traced to ChumbaWumba and ChumbaMini belong to the pinned opponent pool. Princess's one current Tank Royale error has a matching Classic missing-file message, so this run does not establish a bridge-only defect. Keep `DISCREPANCY (errors)` because Classic still reports the opponent-pool errors. The score delta is within the review band and is not a confirmed divergence. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/cx.mini.Nimrod_0.55.jar`.
