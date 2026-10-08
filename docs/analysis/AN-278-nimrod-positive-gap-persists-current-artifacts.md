---
id: AN-278
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Nimrod's large positive score gap persists with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-278 — Nimrod's large positive score gap persists with the latest artifacts

## Risk investigated

Whether `cx.mini.Nimrod_0.55.jar`'s large positive Tank Royale score gap persists under the latest matched artifacts and official five-pair confirmation.

## Evidence boundary

The read-only subject jar has SHA-256 `9ccc75f18f1ea296fefe441150fd552e546989e1e42af4abdc578f297564a321`. The official five-pair confirmation `ab38af2088cdb14c` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `c0687e317ddc8775f40a7d8b4a88226cfedb2f6c`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Across five pairs, Classic averaged 4,919.4 points and Tank Royale averaged 11,868.4, for a +141.14% mean delta. Pair deltas were +146.5%, +134.4%, +142.7%, +149.6%, and +132.5%. Both engines completed without errors, and Tank Royale captured empty skipped-turn event lists in all five attempts. The registry status is `CONFIRMED (score)`.

AN-149's prior five-pair confirmation averaged 4,910.8 in Classic and 11,595.6 in Tank Royale, a +136.12% mean delta. The current result reproduces and slightly increases the score gap, with the same error-free and empty-telemetry outcome.

## Finding

Nimrod's large positive score gap persists under the latest matched artifacts. No runtime errors or skipped turns were observed, and this measurement does not establish the behavioral cause of the score difference. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cx.nano.Smog_2.6.jar` (`score-review`).
