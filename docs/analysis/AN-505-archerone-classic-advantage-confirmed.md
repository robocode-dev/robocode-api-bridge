---
id: AN-505
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: ArcherOne's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-505 — ArcherOne's Classic score advantage is confirmed again

## Risk investigated

Whether `jcw.ArcherOne_1.0.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `8afa06292421338af912d381d06f8dcda0c1375fc9c8e6f184d2103ab44fb0f7`. The official five-pair confirmation `19691b662a7eb6f1` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `ea18af69323cf197eb1bd46489e6cf7296f5d3f8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 8,208.8 points and Tank Royale averaged 3,537.4 points, for a −56.8% mean delta. Pair deltas were −49.7%, −64.2%, −60.4%, −49.5%, and −60.2%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `3b8d0bac2d8b47ad` recorded Classic scores of 7,786 and Tank Royale scores of 3,053, for a −60.8% delta. The current five-pair mean confirms the same large Classic advantage at −56.8%, somewhat smaller than the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

ArcherOne's large Classic score advantage persists under current matched artifacts. The mean delta is −56.8%, smaller than the earlier −60.8% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/jcw.ArcherOne_1.0.jar` as `CONFIRMED (score)`. Skip `roborumble/jcz.linio.Linio_2.0.H.jar` (`PASS`). Record `roborumble/jdw.Hornet_1.0.jar` as `CONFIRMED (score)`. Skip `roborumble/jekl.DarkHallow_.90.9.jar` and `roborumble/jekl.Jekyl_.70.jar` (`PASS`). Continue in registry order with `roborumble/jekl.mini.BlackPearl_.91.jar` (`score-review`).
