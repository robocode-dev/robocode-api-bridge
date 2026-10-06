---
id: AN-100
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-099]
title: Cinnamon's current melee errors belong to the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-100 — Cinnamon's current melee errors belong to the pinned opponent pool

## Risk investigated

Whether Cinnamon's current asymmetric melee errors belong to the subject, the bridge, or the fixed opponent pool, and whether its score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `dans.Cinnamon_1.2.jar` has SHA-256 `cb352fa2412994d4160c5161124036464a48cc73b1725178a7ee048609c8b7da`. The current official one-pair observation `453a1f4ed8473f61` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `a8e3bfd8b0a22a658fe5abeb0ffed2f9edec7698`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The matched local Bot API and runner artifacts are recorded in the observation; the runner SHA-256 is `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`. The subject and opponent jars were not changed.

## What was tried

The harness force-ran Cinnamon at official parameters. Classic scored 115,102 with 299 errors; Tank Royale scored 110,596 with zero errors. The single-pair score delta is −3.9%, inside the 25% review threshold, so it does not confirm a score gap. The Classic log `compat-test/errors/robocode/dans.Cinnamon_1.2.log` traces `ArrayIndexOutOfBoundsException` to `amk.guns.Aristocles.prepare` called by `amk.ChumbaWumba.onScannedRobot`, and traces the five-stream `SecurityException` to `amk.ChumbaMini.saveData`. It also traces `ConcurrentModificationException` to `amk.ShizzleStiX.Navigator.run`. All three classes belong to the pinned melee opponent pool; the selected opponent jars are listed in the observation setup.

The older observation `b4dbfb16a2ffb2c0` used bridge commit `2336f3b50339f6d84c62ccaaa8b7cdb06cf5a82c` and Tank Royale commit `8dd449aceb640b754b239e5ec4d301ebf423756d`; it recorded 272 Classic errors and 30 Tank Royale errors, with the Tank Royale errors from ChumbaMini. The current matched local artifacts have zero Tank Royale errors, while the Classic log still identifies only members of the pinned pool.

## Finding

The traced current Classic errors belong to the pinned opponent pool, not Cinnamon or the bridge. Keep `DISCREPANCY (errors)` while Classic reports those fixture errors. The score delta is within the review band and is not a confirmed divergence. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/darkcanuck.B26354_1.06.jar`.
