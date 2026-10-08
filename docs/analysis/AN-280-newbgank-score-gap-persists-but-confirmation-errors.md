---
id: AN-280
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: NewBGank's negative score gap persists, but a bridge-classified callback error interrupts confirmation
provenance: inferred
reversal-cost: low
---

# AN-280 — NewBGank's negative score gap persists, but a bridge-classified callback error interrupts confirmation

## Risk investigated

Whether `da.NewBGank_1.4.jar`'s historical Classic score advantage persists under the latest matched artifacts and whether its five-pair confirmation completes without runtime errors.

## Evidence boundary

The read-only subject jar has SHA-256 `e47b2ea746b5bd9680b8dd884b82b822857acbd31071bbec0d7b1647c461b194`. The official five-attempt confirmation `1dbd76a05a7f3917` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `c0687e317ddc8775f40a7d8b4a88226cfedb2f6c`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Four pairs completed with deltas of −32.6%, −48.6%, −31.9%, and −39.0%. Their mean scores were 6,373.5 in Classic and 3,945.25 in Tank Royale, a −38.025% mean delta. On the fifth attempt, the registry recorded the bridge-classified signature `java.lang.ArrayIndexOutOfBoundsException` in `da.NewBGank.onScannedRobot`; no fifth score delta was recorded. Skipped-turn telemetry was empty in the first four attempts and incomplete in the fifth. The registry status is `DISCREPANCY (errors)` with four valid samples out of five.

AN-151's previous five-pair confirmation averaged 6,343.6 in Classic and 3,708.4 in Tank Royale, a −41.74% mean delta, with no errors. The current four valid pairs reproduce a similar negative score gap, but the new callback error prevents a complete confirmation.

## Finding

NewBGank's Classic score advantage persists across the four completed pairs, but the five-pair confirmation is incomplete because of a bridge-classified `ArrayIndexOutOfBoundsException` in the robot's scan callback. The available registry evidence does not establish the exception's cause. Keep the error open for follow-up; do not treat the four-pair score mean as a completed confirmation. No code or rumble-jar change is indicated by this measurement alone.

## M-006 handoff

Continue in registry order with `roborumble/daemons.DizzyA_1.0.jar` (`score-review`).
