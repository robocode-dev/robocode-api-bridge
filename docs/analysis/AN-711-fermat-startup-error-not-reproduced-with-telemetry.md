---
id: AN-711
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, C-007, AN-015, AN-193, AN-319, AN-465]
title: Fermat's startup error does not recur in the latest captured run
provenance: inferred
reversal-cost: low
---

# AN-711 — Fermat's startup error does not recur in the latest captured run

## Risk investigated

Whether `meleerumble/ak.Fermat_2.0.jar`'s intermittent Tank Royale-only startup index error recurs under the current bridge, and whether skipped-turn telemetry identifies the triggering robot or turn sequence.

## Evidence boundary

The read-only subject jar has SHA-256 `814688bbe0f93cf5cc6a6be42ab0e69ad0259c54cba8547b20ddb4b1bd7107b3`. Official retest observation `eb08cb7a71a0a8a6` completed on 2026-10-24 using Classic Robocode 1.11.1, bridge commit `8c9f40ec2e6df97b8864f66a47249546928861b5`, and local Tank Royale commit `02639872ea90b0d8cc7e487eb89607935d341e9a`. It used the official MeleeRumble setup: ten participants, 35 rounds, and a 1000×1000 arena. The bridge API, wrapper, Bot API, and runner artifact hashes are pinned in the parity registry. The exact Classic JDK was not recorded. The subject and opponent jars remained read-only.

## What was tried

The retest was linked to diagnosis `initial-status-before-run` and repair `8e50c0b`, and enabled skipped-turn capture. Classic scored 114,377 with 210 errors; Tank Royale scored 111,309 with 30 errors, a −2.7% delta. The Classic errors came from `amk.guns.Aristocles.prepare` and `amk.ChumbaMini.saveData`; Tank Royale's errors came from `ChumbaMini.saveData`. The Fermat-specific `ArrayIndexOutOfBoundsException` did not recur.

Skipped-turn telemetry captured four events at round 1, turn 1, for Tank Royale bot IDs 4, 5, 6, and 9. The recorded evidence does not map those IDs to participant names, so it cannot establish whether Fermat skipped its initial turn. Earlier post-repair runs in AN-193 and AN-319 recorded intermittent Fermat failures, including runs without complete telemetry.

## Finding

This official run adds another non-reproducing sample but does not resolve the intermittent failure documented after repair `8e50c0b`. The startup telemetry confirms that four participants skipped their first turn, but without a bot-ID-to-name mapping it cannot connect those events to Fermat. No bridge change is justified by this run alone. The parity status remains `DISCREPANCY (errors)` because the official pair still contains asymmetric errors from the pinned melee opponent pool and the earlier Fermat-specific failures remain unexplained.

## Implications for M-006

Keep Fermat open for a targeted trace that maps participant IDs and captures the status and scan callback sequence around startup. The remaining callback-ordering, zero-based-time, file-I/O, and wrapper metadata repairs in this priority batch have repair-linked evidence without a reproducible bridge-only signature; their registry statuses remain discrepant where the fixed melee pool still reports errors. No rumble jar was modified.
