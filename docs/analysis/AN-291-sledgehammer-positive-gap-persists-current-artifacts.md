---
id: AN-291
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: SledgeHammer's positive score gap grows with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-291 — SledgeHammer's positive score gap grows with the latest artifacts

## Risk investigated

Whether `demetrix.nano.SledgeHammer_0.22.jar`'s historical Tank Royale score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `8ca91c5c9cbe37fbd67f136a38ac4be45c2f8b9eb470367957c3fe20e0edd3cd`. The official five-pair confirmation `6570d180c7dffe97` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `88fece317986abcac66aa0245596105c88338789`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 10,590.0 points and Tank Royale averaged 16,692.0, for a +57.62% mean delta. Pair deltas were +60.2%, +59.8%, +56.8%, +56.3%, and +55.0%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-163's prior five-pair confirmation averaged 10,558 in Classic and 13,633.6 in Tank Royale, a +29.12% mean delta. The current run reproduces the positive direction with a substantially larger score gap.

## Finding

SledgeHammer's Tank Royale score advantage persists and is larger under the latest matched artifacts than in AN-163. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the gap. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/deo.CloudBot_1.3.jar` (`score-review`).
