---
id: AN-082
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-081]
title: RiOx's earlier scan-after-death error does not recur in the current pair
provenance: inferred
reversal-cost: low
---

# AN-082 — RiOx's earlier scan-after-death error does not recur in the current pair

## Risk investigated

Whether RiOx's previously observed `Index 9` exception still reproduces with the current compatible artifacts, and whether the current pair confirms a score gap.

## Evidence boundary

The read-only subject jar `cf.RiO.RiOx_4.2.1.jar` has SHA-256 `da01319579e24798cf6d7a05cfd13f5c8ba9fe4c704006c8fb4b8ff6fe5f9b9e`. The current official observation `5c54d07d0c37d704` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `388b68e2b1e9a7a803a7c9e7b9a9fb72a20b8485`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The prepared Windows environment used the locally built Bot API, runner, and wrapper artifacts recorded in the observation. The subject and opponent jars were not changed.

## What was tried

The earlier official observations `c242a347b379c873` and `491778d42b27fa0a` used bridge commit `2336f3b50339f6d84c62ccaaa8b7cdb06cf5a82c` and Tank Royale commit `8dd449aceb640b754b239e5ec4d301ebf423756d`; Tank Royale stopped without a score on `ArrayIndexOutOfBoundsException: Index 9 out of bounds for length 9` in `cf.OPs.RiOxM_OP.onScannedRobot`. AN-015's captured trace localized the immediate sequence to a `RobotDeathEvent` for target 3 followed by a scan of target 3 after RiOx had removed that target.

The current official one-pair measurement scored 113,848 in Classic and 111,623 in Tank Royale with zero Tank Royale errors. The −2.0% score delta is inside the 25% review threshold and does not confirm a score gap. Classic's 400 errors are named as `amk.guns.Aristocles.prepare` and `amk.ChumbaMini.saveData`, the pinned ChumbaWumba and ChumbaMini fixtures already identified in AN-015 and AN-081; the registry already tags this subject with `melee-opponent-pool-contamination`, owner `harness`.

## Finding

RiOx's previous subject-side exception does not reproduce in the current local pair. This observation does not explain which change or runtime condition prevented it, so do not claim that the historical trigger is repaired. The current score is within the review band; retain `DISCREPANCY (errors)` only for the named Classic-side fixture errors. No current bridge defect is evidenced by this pair.

## M-006 handoff

Continue in registry order with `meleerumble/cli.Dancer_1.1.jar`.
