---
id: AN-271
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Idem's negative score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-271 — Idem's negative score gap persists with the latest artifacts

## Risk investigated

Whether `conscience.Idem_1.0a.jar`'s confirmed negative score gap persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `1635ea6692d7c89d41d0e089fced55ac334f24f429380fb686590ca58cecfb2d`. The official five-pair confirmation `384afc9089d9e705` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ddece7d9aed03d06a48af835031661ee084055b0`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

The official five-pair confirmation produced mean scores of 10,478.4 in Classic and 6,083.8 in Tank Royale, a −41.98% mean delta. The five pair deltas were −46.1%, −48.8%, −39.2%, −36.7%, and −39.1%. All five pairs completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-139's earlier confirmation had a −47.28% mean delta. The latest matched artifacts reproduce the negative direction with a smaller, consistent mean difference.

## Finding

Idem retains a confirmed negative score gap under the latest matched artifacts. All five pairs favor Classic, with no runtime errors or captured skipped turns. The behavioral cause remains open; no bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cre.Suicidal_1.1.jar` (`DISCREPANCY (no score)`).
