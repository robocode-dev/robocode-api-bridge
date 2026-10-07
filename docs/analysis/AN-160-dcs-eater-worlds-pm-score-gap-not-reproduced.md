---
id: AN-160
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Eater of Worlds PM's historical positive score gap is not reproduced
provenance: inferred
reversal-cost: low
---

# AN-160 — Eater of Worlds PM's historical positive score gap is not reproduced

## Risk investigated

Whether Eater of Worlds PM's historical Tank Royale score advantage persists with current matched artifacts and the official five-pair confirmation.

## Evidence boundary

The read-only subject jar `dcs.PM.Eater_of_Worlds_PM_1.2.jar` has SHA-256 `103d8b2e0e831171d8bc2ad4d2272f17765dfc9ecc7075ee4cdbba2a1a7b3e85`. The official confirmation `a5c993bb51abfdae` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `05d56b3125337d9034fed3533ba76fa956fe91d1`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −7.5%, −11.4%, −8.1%, −11.0%, and −6.0%. Classic averaged 5,220.6 points and Tank Royale averaged 4,760.6, for a −8.8% mean delta. Both engines completed without errors, and Tank Royale captured an empty skipped-turn event list in all five runs. The registry status is `MATCHED (score noise)`; the mean is below the 25% confirmation threshold.

This is a repeated measurement of one selected RoboRumble robot, not an estimate across the full collection. The five pair deltas give the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

Earlier observations `ff977f89ff8c2e6b` and `f1ba2437b9b57a63` recorded +155.4% and +149.5% single-pair deltas without runtime errors. Those larger Tank Royale advantages are not reproduced by the current five-pair mean.

## Finding

The historical positive score discrepancy is not reproduced: the current five-pair mean is −8.8%, inside the score-noise band and in the opposite direction from the two earlier observations. No runtime errors or skipped turns were observed. The measurements do not identify why the earlier artifacts produced much higher Tank Royale scores. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/de.erdega.robocode.Polyphemos_0.4.jar`, whose five-pair confirmation is recorded in AN-161.
