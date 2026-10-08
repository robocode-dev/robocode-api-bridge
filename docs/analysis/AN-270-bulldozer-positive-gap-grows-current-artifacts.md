---
id: AN-270
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Bulldozer's positive score gap grows with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-270 — Bulldozer's positive score gap grows with the latest artifacts

## Risk investigated

Whether `conscience.Bulldozer_1.0a.jar`'s confirmed positive score gap persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `f822506ece11632ca21436cfede847408f696140c474ae3b35e25187fbe27875`. The official five-pair confirmation `33e68230dc8e0629` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ddece7d9aed03d06a48af835031661ee084055b0`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 10,954.4 in Classic and 16,790.6 in Tank Royale, a +53.3% mean delta. The five pair deltas were +55.3%, +49.3%, +54.9%, +55.3%, and +51.7%. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-138's earlier confirmation had a +28.62% mean delta. The latest matched artifacts reproduce the positive direction at a larger magnitude, with all five pairs tightly grouped.

## Finding

Bulldozer retains a confirmed positive score gap under the latest matched artifacts. Its current mean is larger than in AN-138 and all five pairs favor Tank Royale. No runtime errors or captured skipped turns occurred. The behavioral cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/conscience.Idem_1.0a.jar` (`CONFIRMED (score)`).
