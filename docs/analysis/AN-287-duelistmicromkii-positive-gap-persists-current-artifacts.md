---
id: AN-287
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: DuelistMicroMkII's large positive score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-287 — DuelistMicroMkII's large positive score gap persists with the latest artifacts

## Risk investigated

Whether `davidalves.net.DuelistMicroMkII_1.1.jar`'s historical Tank Royale score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `0d3e784acde7d8156090280241668a002f890ee6d5d7fe6dadc971787f67b15e`. The official five-pair confirmation `9e51d456d63a233f` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `70a5022e8292d1185c1a4cf15065869d55e282ab`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 5,295.4 points and Tank Royale averaged 11,679.0, for a +120.98% mean delta. Pair deltas were +135.5%, +114.0%, +124.2%, +102.5%, and +128.7%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-157's prior five-pair confirmation averaged 5,215.6 in Classic and 11,028.6 in Tank Royale, a +111.56% mean delta. The latest run reproduces the large Tank Royale advantage with a larger mean magnitude.

## Finding

DuelistMicroMkII's large positive score gap persists under the latest matched artifacts. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the score difference. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/davidalves.net.DuelistMicro_1.22.jar` (`score-review`).
