---
id: AN-054
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-053]
title: Shera's melee error result comes from the pinned opponent pool
provenance: inferred
reversal-cost: low
---

# AN-054 — Shera's melee error result comes from the pinned opponent pool

## Risk investigated

Whether the unresolved `meleerumble/ara.Shera_0.88.jar` errors identify the measured robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `9474b68598ae5e71010b835259467b6dc35427e2826ace23e484a6a11160a040`. The current official one-pair observation `0d66c8f11c237e42` completed on 2026-10-06 in the prepared Windows PowerShell environment with Python 3.13.15, Classic Robocode 1.11.1 on the auto-selected JDK 17.0.17, bridge commit `4942870fc27e5d05e9f01c62622f3abfb12ef932`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. The Bot API, bridge API, runner, and wrapper jar SHA-256 values match the current artifacts pinned in AN-052.

The battle used the official melee setup: 10 participants, 35 rounds, a 1000×1000 arena, and nine selected opponents from the pinned 12-jar pool. The selected opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The one-pair run is not a multi-pair score confirmation. Skipped-turn telemetry was captured.

## What was tried

The harness force-ran this registry subject at official parameters with the current bridge and local Tank Royale builds, preserving the pinned opponent pool and leaving every rumble jar unchanged. Classic scored 114,560 with 294 errors; Tank Royale scored 113,630 with zero errors. The single-pair score delta was −0.8%, within the 25% review threshold, and the registry status remains `DISCREPANCY (errors)`.

The Classic log identifies `amk.ChumbaMini.saveData` as the source of the five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` as the source of the `ArrayIndexOutOfBoundsException`, called from `amk.ChumbaWumba.onScannedRobot`. Both classes belong to the selected opponent jars. This matches the fixed-pool error pattern established in AN-015 and observed in AN-049 through AN-053. Tank Royale recorded no error signature.

## Finding

The current evidence attributes the asymmetric errors to the fixed opponent pool, not to Shera or a Tank Royale exception. Tag the case `melee-opponent-pool-contamination`, owner `harness`, and retain its error-discrepancy status. The −0.8% score from one pair is not evidence of a confirmed score gap. Do not edit the read-only subject or opponent jars.

## Rejected interpretations

The evidence does not support attributing the named Classic stack frames to Shera, treating the single score pair as a confirmed score divergence, or rewriting/removing a rumble jar to suppress the fixture errors.

## M-006 handoff

Keep the current official observation and the earlier observation in registry history with the harness-owned opponent-pool cause. Continue in registry order with `meleerumble/arthord.MannyPacquiao_Beta.jar`.
