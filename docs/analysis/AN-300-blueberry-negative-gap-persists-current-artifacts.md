---
id: AN-300
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: BlueBerry's Classic score advantage persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-300 — BlueBerry's Classic score advantage persists with the latest artifacts

## Risk investigated

Whether `dmh.robocode.robot.BlueBerry_0.5.jar`'s historical Classic score advantage persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `3739fb06f3f805c42e7afccd8b0cc17e02916021789c6e504c22688a1ae160fb`. The official five-pair confirmation `b28966c382c77593` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `1165900f02513a45d1135b61cc927875cea0eca4`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 5,848.4 points and Tank Royale averaged 3,484.2, for a −40.22% mean delta. Pair deltas were −45.7%, −41.1%, −34.5%, −37.3%, and −42.5%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-174's prior five-pair confirmation averaged 5,717.8 in Classic and 3,506.0 in Tank Royale, a −38.54% mean delta. The current run reproduces the Classic advantage with a slightly larger magnitude.

## Finding

BlueBerry's Classic score advantage persists under the latest matched artifacts. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the gap. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dmp.micro.Aurora_1.41.jar` (`score-review`).
