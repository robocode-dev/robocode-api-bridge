---
id: AN-292
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: CloudBot's negative score gap grows with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-292 — CloudBot's negative score gap grows with the latest artifacts

## Risk investigated

Whether `deo.CloudBot_1.3.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `d62f0561529c81e5da65ad44c4c6db8b31dceca043ec2d9fcc4646aa15ed28f3`. The official five-pair confirmation `25cefe78b8eec2ca` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `88fece317986abcac66aa0245596105c88338789`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 5,810.0 points and Tank Royale averaged 3,689.0, for a −36.54% mean delta. Pair deltas were −36.0%, −33.7%, −48.6%, −33.6%, and −30.8%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-164's prior five-pair confirmation averaged 5,722.4 in Classic and 3,931.2 in Tank Royale, a −30.78% mean delta. The current run reproduces the Classic advantage with a larger mean magnitude.

## Finding

CloudBot's negative score gap persists and is larger under the latest matched artifacts than in AN-164. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the gap. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/deo.virtual.RainbowBot_1.0.jar` (`score-review`).
