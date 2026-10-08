---
id: AN-306
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Dragonbyte's small positive score gap falls within the score-noise band
provenance: inferred
reversal-cost: low
---

# AN-306 — Dragonbyte's small positive score gap falls within the score-noise band

## Risk investigated

Whether `dragonbyte.Neutrino_4.jar`'s historical Tank Royale score advantage remains above the confirmation band under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `c7c7c279979129da96e848c140b138ace728ccfdb96a5de7d90ed0f1d6e0a65e`. The official five-pair confirmation `b8b1156914dcf0d2` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `49248a488cd62fcce5233fd0b0e3a32d4c218182`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 1,839.8 points and Tank Royale averaged 2,093.2, for a +14.7% mean delta. Pair deltas were +22.9%, +29.9%, +2.0%, +17.1%, and +1.6%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `MATCHED (score noise)`.

AN-180's earlier five-pair confirmation averaged 1,827.2 in Classic and 2,103.2 in Tank Royale, a +15.78% mean delta. The latest mean remains similar but falls just below the 15-point confirmation band.

## Finding

Dragonbyte's small positive score gap remains close to the earlier result but is within the score-noise band under the latest matched artifacts. No runtime errors or skipped turns were observed. The measurements do not establish the behavioral cause of the score difference, and no bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/drm.CobraBora_1.12.jar` (`score-review`).
