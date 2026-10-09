---
id: AN-350
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-074, AN-224]
title: Mini Fenrir's bundled Doel arithmetic error aborts retest
provenance: inferred
reversal-cost: low
---

# AN-350 — Mini Fenrir's bundled Doel arithmetic error aborts retest

## Risk investigated

Whether `bvh.mini.Fenrir_0.39.jar`'s historical melee error imbalance persists under the latest matched artifacts, and whether its current Tank Royale-only exception identifies a bridge defect.

## Evidence boundary

The read-only subject jar SHA-256 is `3d63cdf11e5846c7e295db9c27965b84e7d1944b923d09979819f188700220a1`; selected opponent hashes are recorded in the registry. Observation `037a67f46844027f` ran on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ade8306f4bf43cc52948f00bcb594a12169a7221`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

The confirmation attempted two pairs but stopped during the second Tank Royale attempt, after one completed score pair. The completed pair scored 113,759 Classic and 111,261 Tank Royale points, a −2.2% delta; this single sample is not a score confirmation. Tank Royale raised `java.lang.ArithmeticException` in `bvh.mini.Fenrir$Doel.setInfo`, and the registry status is `DISCREPANCY (errors)`.

`Doel` is a nested class in `Fenrir.java` bundled inside the subject jar, not a selected opponent. Its source initializes `sampleTijd` to 5, updates it from a rounded weighted value involving scan-time difference, then evaluates `scanTijd % sampleTijd == 1`. A zero sample interval would raise an arithmetic exception, but the current signature has no line number or message, so this candidate is not proven as the exact failing expression. The preceding observation `e860386ee4b78a6d` recorded 376 Classic errors and 30 Tank Royale errors with a −2.7% delta; the old registry diagnosis was opponent-pool contamination. The registry preserves that event and adds a robot-owned diagnosis for the current subject-method exception.

Skipped-turn telemetry was captured with no events in the first attempt and was incomplete for the failed second attempt.

## Finding

The current retest stops on a Tank Royale-only arithmetic exception in code bundled with Mini Fenrir. This identifies a robot-owned failure separate from the earlier opponent-pool errors; it does not demonstrate a bridge defect. The exact zero-divisor operation remains unconfirmed. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bvh.mini.Freya_0.55.jar` (`DISCREPANCY (errors)`).
