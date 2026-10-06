---
id: AN-095
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-094]
title: Delitioner's current melee errors belong to the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-095 — Delitioner's current melee errors belong to the pinned opponent pool

## Risk investigated

Whether Delitioner's current asymmetric melee errors belong to the subject, the bridge, or the fixed opponent pool, and whether its score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `css.Delitioner_0.11.jar` has SHA-256 `6c718a72f59cec4464649d650469adfcccaf0de0744d7fd3e3ef7a2c5ce28c0b`. The current official one-pair observation `309b49ab69e7bfe5` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `ed9e317829cc0869ce0bb29b0a09536db2655b68`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The matched local Bot API and runner artifacts are recorded in the observation; the runner SHA-256 is `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`. The subject and opponent jars were not changed.

## What was tried

The harness force-ran Delitioner at official parameters. Classic scored 115,459 with 378 errors; Tank Royale scored 112,892 with zero errors. The single-pair score delta is −2.2%, inside the 25% review threshold, so it does not confirm a score gap. The Classic log `compat-test/errors/robocode/css.Delitioner_0.11.log` traces `ArrayIndexOutOfBoundsException` to `amk.guns.Aristocles.prepare` called by `amk.ChumbaWumba.onScannedRobot`, and traces the five-stream `SecurityException` to `amk.ChumbaMini.saveData`. AN-015 identifies ChumbaWumba and ChumbaMini as members of the pinned opponent pool. Some summary signatures have an unknown origin, but the saved event traces for the corresponding exception messages identify those same opponent classes.

The older observation `8be5aeec0bbfefd0` used bridge commit `2336f3b50339f6d84c62ccaaa8b7cdb06cf5a82c` and Tank Royale commit `8dd449aceb640b754b239e5ec4d301ebf423756d`; it recorded 166 Classic errors and 30 Tank Royale errors. The current matched local artifacts have zero Tank Royale errors, while the Classic log still identifies the pinned opponents.

## Finding

The traced current Classic errors belong to the pinned opponent pool, not Delitioner or the bridge. Keep `DISCREPANCY (errors)` while Classic reports those fixture errors. The score delta is within the review band and is not a confirmed divergence. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/cvt.Firsty_1.0.jar`.
