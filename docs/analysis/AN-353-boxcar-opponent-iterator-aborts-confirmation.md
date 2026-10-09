---
id: AN-353
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-077, AN-227]
title: BoxCar confirmation stops on a selected opponent iterator error
provenance: inferred
reversal-cost: low
---

# AN-353 — BoxCar confirmation stops on a selected opponent iterator error

## Risk investigated

Whether `bzdp.BoxCar_2.0.jar`'s historical melee error imbalance persists under the latest matched artifacts, and whether the current failed attempt identifies a bridge defect.

## Evidence boundary

The read-only subject jar SHA-256 is `22e13c11b29fb3ed958288b38d67ab6ae02bd899f16ddcf96295f621e795ecb6`; selected opponent hashes are recorded in the registry. Observation `c22e441df6157b44` ran on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ade8306f4bf43cc52948f00bcb594a12169a7221`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

The confirmation attempted three pairs but stopped during the third Tank Royale attempt, after two completed score pairs. Those pairs averaged 115,214 Classic points and 111,983.5 Tank Royale points, for a partial mean delta of −2.8%; both pair deltas were −2.8%. The third pair is incomplete, so this is not a five-pair confirmation. The registry status is `DISCREPANCY (errors)`.

Tank Royale's bridge-only signature was `java.util.ConcurrentModificationException` at `amk.ShizzleStiX.Navigator.run`. `amk.ShizzleStiX.ShizzleStiX_0.6.jar` is in the selected opponent pool. The previous observation `e6f44c512291adf7` recorded 370 Classic errors and 32 Tank Royale errors with a −3.2% score delta; AN-227 had already documented the selected-pool ChumbaMini failure after the bridge stream fix. The current signature names a different selected opponent and does not demonstrate a bridge failure.

Skipped-turn telemetry was captured without events in the first two attempts and was incomplete for the failed third attempt.

## Finding

BoxCar's current confirmation is blocked by an exception from a selected opponent, so its five-pair score and error parity remain unconfirmed. The evidence supports the existing opponent-pool contamination diagnosis; it does not identify a BoxCar or bridge defect. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/bzdp.Pansy_2.1.jar` (`DISCREPANCY (errors)`).
