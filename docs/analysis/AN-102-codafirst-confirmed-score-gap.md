---
id: AN-102
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-001, AN-002, AN-101]
title: CodaFirst's official score gap is confirmed but its behavioral cause remains open
provenance: inferred
reversal-cost: low
---

# AN-102 — CodaFirst's official score gap is confirmed but its behavioral cause remains open

## Risk investigated

Whether CodaFirst's repeated lower Tank Royale score is ordinary battle noise, a score-formula difference, or a behavioral divergence in the bridge or engine.

## Evidence boundary

The read-only subject jar `AD.CodaFirst_1.1.jar` has SHA-256 `140825976d19ac8cbbf3a92040a9bc8568acb15933f6888cd0adce705497421f`. The official 800×600, 35-round, two-participant run used Classic Robocode 1.11.1, bridge commit `5bc721950a23866b8eec99bd1833e1f6e5ed219b`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, and the locally built runner whose SHA-256 is `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`. The subject jar was not changed.

## What was tried

The first current-artifact pair scored 13,074 in Classic and 6,407 in Tank Royale, a −51.0% delta, with zero errors on both engines and no captured skipped-turn events. The official five-repeat confirmation completed all samples and produced mean scores of 12,886.8 in Classic and 6,294.6 in Tank Royale. Its deltas were −50.2%, −50.9%, −53.9%, −52.9%, and −47.8%, for a mean of −51.14%; the registry therefore classifies this as `CONFIRMED (score)`. The earlier observation `8a4dfbfbf555deff` used older bridge and Tank Royale commits and had a −47.2% delta, so the discrepancy is not confined to the current artifact pair.

Bytecode inspection shows CodaFirst uses scanned bearing and distance, body and radar headings, queued turns and movement, and power-3 fire. Source review found matching Classic and Tank Royale weights for bullet damage, bullet-kill bonus, survival, and last-survivor score; those score weights do not explain the gap. The standard 80-turn fixed-command trace did not provide a controlled comparison because its engines began at different random positions and headings. It does not isolate CodaFirst's strategy or identify a bridge defect.

## Finding

CodaFirst has a repeatable, confirmed score gap without runtime errors or skipped turns. The cause remains unresolved; available evidence does not identify a specific bridge, Tank Royale, or robot behavior defect. Keep `CONFIRMED (score)` and leave its cause unassigned until a controlled trace of CodaFirst's perceived scan, heading, aim, and movement state can locate the divergence. No code or rumble jar change is indicated by this evidence.

## M-006 handoff

Continue with the next unresolved registry subject, `roborumble/ArchAlpha.ArchimedesAlpha_1.0.jar`; the intervening AIR.iRobot and And.BasicSurfer rows are `PASS`.
