---
id: AN-705
type: analysis
status: active
links: [P-001, PDR-001, AN-015, C-007]
title: DynaBot's Tank Royale no-score run reaches an unchecked robot gun-wave index
provenance: inferred
reversal-cost: low
---

# AN-705 — DynaBot's Tank Royale no-score run reaches an unchecked robot gun-wave index

## Risk investigated

Whether `meleerumble/djc.DynaBot_0.1.1.jar`'s Tank Royale no-score result comes from the bridge, the fixed opponent pool, or an unchecked index in the robot's own gun model.

## Evidence boundary

The read-only jar SHA-256 is `eb638456ca4a836ac872f0f779b66988b3009e94960d46188dc732550f8cde0a`. Its official observation is `cba46e68692acc3d`, pinned to bridge commit `7a18a7c35bc8c82add4b87e4a9f516b261c158e3` and Tank Royale commit `860aa075445ccf0a257310001aa6d4983053e56e`; the battle used Classic Robocode `1.11.1`, 35 rounds, ten participants, and a 1000×1000 arena. The prepared environment was Windows with Temurin JDK 25.0.1. The archive's bundled `BeeGun.java` was read without changing the jar.

## What was tried

Classic scored 114,378 and recorded 1,027 errors, including known opponent-pool failures and a robot console `NullPointerException` from its battle manager. Tank Royale produced no score and recorded 90 errors, including `ArrayIndexOutOfBoundsException: Index -2 out of bounds for length 5` at `djc.gun.GunWave.mostVisited`, a robot battle-manager `NullPointerException`, and the known `amk.ChumbaMini.saveData` opponent-pool error. Skipped-turn telemetry was incomplete.

The bundled source computes `bulletPowerIndex` as `(int) (bulletPower / 0.65)` without a lower-bound clamp, then calls `mostVisited()` from the scan callback. That method indexes several five-element feature dimensions. The exception therefore occurs in the measured robot's unchecked gun-model indexing. The recorded message does not identify which feature field held `-2` or which event value produced it; `bulletPowerIndex` is an evident candidate, not a proven field-level attribution.

## Finding

The Tank Royale no-score error is a robot-code failure at an unchecked feature-array access, separate from the opponent-pool failures. The robot-owned cause is recorded as `robot-unchecked-gun-wave-feature-index`. The evidence does not establish why the invalid index appears in Tank Royale but not Classic, so it does not rule out an engine-facing trigger difference; it does not identify a bridge frame or a bridge value mismatch either.

The interpretation that the 90 errors all belong to the harness was rejected because `GunWave.mostVisited` is in the measured jar. A bridge attribution was also rejected because the current observation contains no event-value trace tying the negative index to a bridge-produced value.

## Implications for M-006

The immediate no-score mechanism is diagnosed to the read-only robot's unbounded gun-model index; the robot cannot be repaired under `C-007`. Keep its result separate from the harness-owned opponent-pool errors and do not count it as a bridge fix.

## M-006 handoff

Continue with the next unregistered MeleeRumble jar. Revisit DynaBot only if later evidence can capture the feature input that became negative.
