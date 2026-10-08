---
id: AN-266
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: WasteOfAmmo's Classic zero score persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-266 — WasteOfAmmo's Classic zero score persists with the latest artifacts

## Risk investigated

Whether `cli.WasteOfAmmo_1.0.jar`'s recurring Classic zero-score result persists under the latest matched artifacts, and whether the current pair clarifies its score difference.

## Evidence boundary

The read-only subject jar has SHA-256 `c899395217eb5b9a8c9bdcd3e8869616ec23576e787e4ce3e2c7c3263695b36f`. The official observation `951af18fa9967b7f` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ddece7d9aed03d06a48af835031661ee084055b0`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic scored 0 and Tank Royale scored 1,460, with zero errors on either engine. Tank Royale captured an empty skipped-turn event list. The registry status remains `DISCREPANCY (no score)` because Classic produced no score.

AN-134's earlier current-artifact observation also recorded a Classic score of 0, with Tank Royale at 540; two older Tank Royale scores were 1,380 and 1,400. The Classic zero therefore recurs, while the Tank Royale score varies. AN-134's bytecode inspection found that the robot's `run()` loop issues turns but does not call `execute()`; that behavior is a lead, not a proven explanation of the cross-engine score difference.

## Finding

WasteOfAmmo's Classic zero score persists with the latest matched artifacts, while Tank Royale scores 1,460 without runtime errors or captured skipped turns. The current pair does not resolve why its engines produce different scores. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/com.arsenic.NewTest_1.0.jar` (`DISCREPANCY (errors)`).
