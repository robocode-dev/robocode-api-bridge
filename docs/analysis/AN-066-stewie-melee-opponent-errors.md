---
id: AN-066
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-065]
title: Stewie's current melee errors come from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-066 — Stewie's current melee errors come from the pinned opponent pool

## Risk investigated

Whether the current `meleerumble/bigpete.Stewie_1.0.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `743ecaf04844e35915d24a80533652a29adaaf9539955f2178df0ef6148c481d`. The current official one-pair observation `4e87fc9eef955929` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `47742b9adf4bd7db3648927912187fcf28085738`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The subject and fixture jars remained unchanged.

## What was tried

The harness force-ran this registry subject at official parameters using the current bridge and local Tank Royale builds. Classic scored 115,060 with 310 errors; Tank Royale scored 112,144 with zero errors. The single-pair score delta was −2.5%, within the 25% review threshold, and does not confirm a score gap.

The updated exception parser names `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` as the source of the `ArrayIndexOutOfBoundsException`. These classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some Classic error origins remain `unknown`. The named errors match the pinned-pool failures established in AN-015 and observed in AN-049 through AN-065. The earlier observation `24e1c68ffaa4d90a` recorded 30 Tank Royale errors from ChumbaMini; the current local Tank Royale artifacts report zero errors.

## Finding

The current named Classic errors belong to the fixed opponent pool, not Stewie or a Tank Royale exception. Tag the case `melee-opponent-pool-contamination`, owner `harness`, and retain `DISCREPANCY (errors)` while Classic still reports the fixture errors. The −2.5% score delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/brainfade.melee.Dusk_0.44.jar`.
