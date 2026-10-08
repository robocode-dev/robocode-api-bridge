---
id: AN-298
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: DivineBot's Classic file-load errors recur with near-equal scores
provenance: inferred
reversal-cost: low
---

# AN-298 — DivineBot's Classic file-load errors recur with near-equal scores

## Risk investigated

Whether DivineBot's historical Classic-only file-load errors recur under the latest matched artifacts, and whether the current score difference is material.

## Evidence boundary

The read-only subject jar has SHA-256 `8d127b2400ccfddf39d0666729cc749b95084f1550c76d604f8b1089ca688b8a`. The official observation `75750cf74ed4de65` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `1165900f02513a45d1135b61cc927875cea0eca4`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic scored 7,208 and recorded two `java.io.EOFException` errors, one from `divineomega.FileManager.load` and one with unknown origin. Tank Royale scored 7,262 with no errors and captured an empty skipped-turn event list. The delta was +0.7% in Tank Royale's favor, and the registry status is `DISCREPANCY (errors)`.

AN-171 also recorded two Classic `EOFException` errors from `FileManager.load` with no Tank Royale errors. The file-load error pattern recurs, while the current score difference is negligible. AN-171 did not treat the earlier +32.0% one-pair delta as a confirmed score gap; the current +0.7% result likewise does not establish a persistent score difference.

## Finding

DivineBot's Classic EOF errors recur while Tank Royale completes without runtime errors. The current score difference is only +0.7%, and the registry evidence does not establish why the saved data cannot be read or attribute the cross-engine difference to the bridge. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/djdjdj.NanoSkunk10_1.0.jar` (`score-review`).
