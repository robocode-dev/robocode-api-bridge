---
id: AN-347
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-071, AN-221]
title: Fenrir's bundled Doel arithmetic error aborts fifth-pair confirmation
provenance: inferred
reversal-cost: low
---

# AN-347 — Fenrir's bundled Doel arithmetic error aborts fifth-pair confirmation

## Risk investigated

Whether `bvh.fnr.Fenrir_0.36l.jar`'s historical melee error imbalance persists under the latest matched artifacts, and whether the current Tank Royale-only error identifies a bridge defect.

## Evidence boundary

The read-only subject jar SHA-256 is `986ed898d1fa2556d204321e33139543a61c6ddda15b419b877cc7009e91b5d9`; the selected opponent hashes are recorded in the registry. Observation `cacbff4a4509fc49` ran on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `546d7c330d13999567a07197916d7d8e199a153b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

The confirmation attempted five pairs but stopped after four completed pairs when Tank Royale raised `java.lang.ArithmeticException` in `bvh.fnr.Doel.setInfo`. The four pairs averaged 114,690.25 Classic points and 111,763.25 Tank Royale points, for a partial mean delta of −2.55%; pair deltas were −2.2%, −2.2%, −2.9%, and −2.9%. The fifth pair is incomplete, so this is not a five-pair confirmation. The registry status is `DISCREPANCY (errors)`.

`Doel.java` is bundled in the read-only Fenrir subject jar and `bvh.fnr.Doel` is not in the selected opponent pool. Its `setInfo` method rounds `sampleTijd` to an integer and later evaluates `scanTijd % sampleTijd == 1` in melee mode. A zero `sampleTijd` would raise `ArithmeticException`; the stored stack signature has no line number or message, so this is a plausible source, not a confirmed exact failing expression. The earlier latest observation `974a56d29aa9a5f8` recorded 262 Classic errors and 30 Tank Royale errors. The registry now preserves that harness diagnosis and adds a robot-owned diagnosis for the current subject-method exception.

Skipped-turn telemetry was captured for attempts 1, 3, and 4, unavailable for attempt 2, and incomplete for attempt 5. The captured event counts were 13, 0, and 6; bot IDs are recorded in the registry and are not attributed here.

## Finding

The current Tank Royale-only failure is in code bundled with Fenrir, while the prior opponent-pool diagnosis refers to earlier observations. The available evidence points to a robot-owned arithmetic error and does not demonstrate a bridge defect. The exact zero-divisor operation is not proven. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bvh.fry.Freya_0.82.jar` (`DISCREPANCY (errors)`).
