---
id: AN-052
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-051]
title: BotM's melee error result comes from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-052 — BotM's melee error result comes from the pinned opponent pool

## Risk investigated

Whether the unresolved `meleerumble/apc.botM_3.0.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `9348a9b3c2e0f175bacf361dc3b20a0842f186c228564bcafd8b6d853d345d94`. The current official one-pair observation `d051bb63b263a568` completed on 2026-10-06 in the prepared Windows PowerShell environment with Python 3.13.15, Classic Robocode 1.11.1 on the auto-selected JDK 17.0.17, bridge commit `507e3a6cd49ee655cc1deea2fe27533b36a0cffb`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. The Bot API, bridge API, runner, and wrapper jar SHA-256 values were `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, `7b9694ef4dc67d4be47e36be69a5bba55f7c9006cc43ce27afa1747857afbab3`, `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and `bf5fa0ba77a8a7454a53f57dedbdf7b74cb7be3940ee806db1b490aa8a8f6b9d` respectively.

The battle used the official melee setup: 10 participants, 35 rounds, a 1000×1000 arena, and nine selected opponents from the pinned 12-jar pool. The selected opponents included `amk.ChumbaMini_0.2.jar` (SHA-256 `5770270c23f9223e4df93f5455fcf461ecd30309fc318f3d53cbbdf93e133d6b`) and `amk.ChumbaWumba_0.3.jar` (SHA-256 `f7594cdfa02419ae112013ec923387736a89840712162020ecb899e39d6127e9`). The one-pair run is not a multi-pair score confirmation. Skipped-turn telemetry captured three events at round 1, turn 1.

## What was tried

The harness force-ran this registry subject at official parameters with the current bridge and local Tank Royale builds, preserving the pinned opponent pool and leaving every rumble jar unchanged. Classic scored 113,812 with 584 errors; Tank Royale scored 113,761 with zero errors. The single-pair score delta rounded to −0.0%, and the registry status remains `DISCREPANCY (errors)`.

The Classic log identifies `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` as the source of the `ArrayIndexOutOfBoundsException`, called from `amk.ChumbaWumba.onScannedRobot`. Both classes belong to the selected opponent jars. This matches the fixed-pool error pattern established in AN-015 and observed for SuperStrike, NanoClone, and Frederick in AN-049, AN-050, and AN-051. Tank Royale recorded no error signature.

## Finding

The current evidence attributes the asymmetric errors to the fixed opponent pool, not to BotM or a Tank Royale exception. Tag the case `melee-opponent-pool-contamination`, owner `harness`, and retain its error-discrepancy status. The near-zero score delta from one pair is not evidence of a confirmed score gap. Do not edit the read-only subject or opponent jars.

## Rejected interpretations

The evidence does not support attributing the named Classic stack frames to BotM, treating the single score pair as a confirmed score divergence, or rewriting/removing a rumble jar to suppress the fixture errors.

## M-006 handoff

Keep the current official observation and the earlier observation in registry history with the harness-owned opponent-pool cause. Continue in registry order with `meleerumble/apollokidd.ApolloKidd_0.9.jar`.
