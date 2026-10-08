---
id: AN-288
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: DuelistMicro's positive score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-288 — DuelistMicro's positive score gap persists with the latest artifacts

## Risk investigated

Whether `davidalves.net.DuelistMicro_1.22.jar`'s historical Tank Royale score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `e51b32ab504ef17c0bcd47bb7953ccdd07f7e050b0fa1a3478e63281eaa0fe2a`. The official five-pair confirmation `50c4bd5434263b10` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `70a5022e8292d1185c1a4cf15065869d55e282ab`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 5,282.6 points and Tank Royale averaged 8,400.4, for a +58.98% mean delta. Pair deltas were +57.9%, +63.6%, +51.5%, +57.0%, and +64.9%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-158's prior five-pair confirmation averaged 5,223 in Classic and 8,193 in Tank Royale, a +57.06% mean delta. The latest run reproduces a very similar positive score gap.

## Finding

DuelistMicro's positive score gap persists under the latest matched artifacts. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the score difference. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/davv.DOne_b002.jar` (`score-review`).
