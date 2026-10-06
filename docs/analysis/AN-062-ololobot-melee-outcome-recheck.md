---
id: AN-062
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-061]
title: Ololobot's old Tank Royale outcome errors do not recur on current artifacts
provenance: inferred
reversal-cost: low
---

# AN-062 — Ololobot's old Tank Royale outcome errors do not recur on current artifacts

## Risk investigated

Whether the old `meleerumble/az.Ololobot_0.2.4.jar` Tank Royale-only runtime failure persists with the current local bridge and Tank Royale builds, and whether current errors identify the subject or the pinned melee pool.

## Evidence boundary

The read-only subject jar has SHA-256 `b6938411c048bde80bf0186892f03342e0c60dff05127052ed21932825765eb9`. The current official one-pair observation `0f473f90619606e6` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `6c042ab33f3c591be4d7202f577c9248270873bd`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The subject and fixture jars remained unchanged.

## What was tried

The current official run scored 114,818 in Classic with 602 errors and 111,770 in Tank Royale with zero errors. The single-pair score delta was −2.7%, within the 25% review threshold, and does not confirm a score gap.

The earlier observation `091cbdb379677185` had no Tank Royale score and 344 errors, including `ArrayIndexOutOfBoundsException` signatures at `az.Ololobot.onScannedRobot` and `az.Ololobot.run`. Those subject-origin signatures did not recur in the current Tank Royale run. The current Classic signatures instead name `amk.ChumbaMini.saveData` for the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` for the `ArrayIndexOutOfBoundsException`; these classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some Classic error origins remain `unknown`. The named errors match the pinned-pool failures established in AN-015 and observed in AN-049 through AN-061.

## Finding

The earlier Tank Royale runtime outcome does not reproduce in the current one-pair run. The current named Classic errors belong to the fixed opponent pool, not Ololobot or a Tank Royale exception. Tag the case `melee-opponent-pool-contamination`, owner `harness`, and retain `DISCREPANCY (errors)` while Classic still reports fixture errors. The −2.7% score delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/baal.nano.N_1.42.jar`.
