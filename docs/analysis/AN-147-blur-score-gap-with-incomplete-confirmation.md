---
id: AN-147
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Blur's large score gap persists, with confirmation interrupted by a robot null wave
provenance: inferred
reversal-cost: low
---

# AN-147 — Blur's large score gap persists, with confirmation interrupted by a robot null wave

## Risk investigated

Whether Blur's historical Tank Royale score deficit persists under current matched artifacts, and what caused the failed fourth confirmation attempt.

## Evidence boundary

The read-only subject jar `cx.micro.Blur_0.2.jar` has SHA-256 `a1d62b084d88196ff938d3f189e0f7a84f89e7f034bd21f83a8afb0bc20ba979`. The current official confirmation `df6757b238884587` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `903b75fa82cbc9a2104bc53f036814e9f55d316a`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Three valid score pairs produced deltas of −77.4%, −74.1%, and −74.3%; Classic averaged 7,613.3 points and Tank Royale 1,885.3, for a −75.27% mean delta. Attempts 1–3 had empty skipped-turn telemetry. The fourth Tank Royale run ended without a result, so the five-pair confirmation is incomplete and the registry status is `DISCREPANCY (errors)`. Its bridge-only signature is `NullPointerException` from `cx.micro.Blur.onHitByBullet`.

Earlier observation `e0eb72591fc796b8` scored 7,997 in Classic and 1,952 in Tank Royale, a −75.6% delta without errors. Observation `cf57283fe7c065c6` had a Classic score of 7,918 and no Tank Royale result with two errors.

## Finding

The bundled `onHitByBullet()` source calls `closestWave.rateHit()` without a null check. `closestWave` is assigned from a scanned wave only when the scan handler has a nonempty wave list, so a hit callback before that assignment can dereference null. The current error origin matches this robot method; the registry diagnosis is `robot-null-surf-wave-on-hit-by-bullet`, owner `robot`. The large score deficit appears in all three valid current pairs and in the earlier score observation, but the current five-pair confirmation cannot be marked complete. No bridge code or rumble jar change is indicated by this evidence.

## M-006 handoff

Continue in registry order with `roborumble/cx.micro.Spark_0.6.jar` (`score-review`).
