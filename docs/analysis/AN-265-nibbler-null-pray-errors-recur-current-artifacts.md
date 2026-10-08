---
id: AN-265
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Nibbler's Classic-only null-Pray errors recur with the latest artifacts
provenance: inferred
reversal-cost: low
---

# AN-265 — Nibbler's Classic-only null-Pray errors recur with the latest artifacts

## Risk investigated

Whether `cbot.agile.Nibbler_0.2.jar`'s Classic-only null-Pray errors recur under the latest matched artifacts and whether the current pair identifies a bridge defect.

## Evidence boundary

The read-only subject jar has SHA-256 `b1b79c3c45f03c2098bee75bd1c379e51b8e45f66418b0ac15a9e9747fef1689`. The official observation `6724285259a5f928` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ddece7d9aed03d06a48af835031661ee084055b0`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic scored 5,497 and reported 10 errors. Eight messages explicitly say that `Pray.getDistance()` was invoked while `pray` was null; their stack traces point to `cbot.agile.driver.RandomPointDriver.getStopTicks`. The two remaining errors are generic `NullPointerException` messages. Tank Royale scored 6,553 with no errors, a +19.2% delta, and captured one skipped-turn event for bot 1 at round 1, turn 1. The registry status remains `DISCREPANCY (errors)`.

AN-132's bytecode inspection attributed the matching `getStopTicks(Pray)` dereference to the robot, and the current stack traces reproduce that origin. The additional generic messages do not contain enough detail to assign their call sites.

## Finding

Nibbler's Classic-only null-Pray failure recurs under the latest matched artifacts, with more recorded errors than in AN-132. The robot-owned unchecked dereference remains the supported cause for the identified stack traces; why `pray` is null is unresolved. Tank Royale has no errors. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cli.WasteOfAmmo_1.0.jar` (`DISCREPANCY (no score)`).
