---
id: AN-101
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-100]
title: B26354's earlier Tank Royale exception does not recur with the current matched artifacts
provenance: inferred
reversal-cost: low
---

# AN-101 — B26354's earlier Tank Royale exception does not recur with the current matched artifacts

## Risk investigated

Whether B26354's earlier Tank Royale-only `NullPointerException` still interrupts the official melee run, and whether the current score delta is a confirmed gap.

## Evidence boundary

The read-only subject jar `darkcanuck.B26354_1.06.jar` has SHA-256 `9dec0de94deb2d91527d98bfdfc56b76eb65734778e6096b23724d7480f8e58d`. The current official one-pair observation `e2187be09c14425c` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `52d480eca22cf35cfa2a5f4a8dc446dee2420331`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The matched local Bot API and runner artifacts are recorded in the observation; the runner SHA-256 is `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`. The subject and opponent jars were not changed.

## What was tried

The harness force-ran B26354 at official parameters. Classic scored 114,090 with 324 errors; Tank Royale scored 111,505 with zero errors. The single-pair score delta is −2.3%, inside the 25% review threshold, so it does not confirm a score gap. The current Classic signatures are `ArrayIndexOutOfBoundsException` in `amk.guns.Aristocles.prepare` and the five-stream `SecurityException` in `amk.ChumbaMini.saveData`; AN-015 identifies ChumbaWumba and ChumbaMini as members of the pinned opponent pool.

The earlier observation `b336f45184cf831d` completed on 2026-09-28 with bridge commit `2336f3b50339f6d84c62ccaaa8b7cdb06cf5a82c` and Tank Royale commit `8dd449aceb640b754b239e5ec4d301ebf423756d`. Tank Royale stopped without a score after three errors, including `NullPointerException` at `darkcanuck.m.a`; AN-015 records that the exception reproduced in a focused retake. The current matched-artifact run completed all 35 rounds with zero Tank Royale errors. This retest does not isolate which artifact or environmental difference accounts for the changed outcome, so it does not establish that the earlier robot failure was repaired.

## Finding

The earlier subject-origin Tank Royale failure does not reproduce with the current matched artifacts, and its cause remains unresolved. The current error discrepancy is attributable to the pinned opponent pool on the Classic side. Keep `DISCREPANCY (errors)` while those fixture errors occur. The current score delta is within the review band and is not a confirmed divergence. No code or rumble jar change is indicated.

## M-006 handoff

This is the last `meleerumble` subject in registry order; check the M-006 scope for the next collection before proceeding.
