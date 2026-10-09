---
id: AN-337
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: HataMoto's bundled Stratego strategy aborts current Tank Royale confirmation
provenance: inferred
reversal-cost: low
---

# AN-337 — HataMoto's bundled Stratego strategy aborts current Tank Royale confirmation

## Risk investigated

Whether `axeBots.HataMoto_3.09.jar`'s historical melee error imbalance and score difference persist under the latest matched artifacts, and whether a current error identifies a bridge defect.

## Evidence boundary

The read-only subject jar SHA-256 is `293a92846b40195a5de25c7379f72e0dcb08297f79d2b6fffd7d3a95b50afb2c`; selected opponent hashes are recorded in the registry. Observation `f0bb43058a20fc93` used Classic Robocode 1.11.1, bridge commit `23fc732088cb0a23d373c7e20a7f8f22357c6d48`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The setup used 10 participants, 35 rounds, and a 1000×1000 arena. The subject and opponent jars remained read-only.

## What was tried

The confirmation stopped after four completed score pairs when the fifth Tank Royale attempt raised `java.lang.ArrayIndexOutOfBoundsException` in `axeBots.Stratego.missed`. The four pairs averaged 114,175 Classic points and 111,724 Tank Royale points, with deltas −2.6%, −1.5%, −2.0%, and −2.5% (mean −2.15%). The fifth pair is incomplete, so this is not a five-pair confirmation; the registry retains `DISCREPANCY (errors)`.

`Stratego` was not in the selected opponent pool. Its `.class` and `.java` files are bundled inside the HataMoto subject jar. The read-only `missed` source updates `totFired[aimMethod][angle]` and `totFiredPR[aimMethod][angle][distance]` without bounds checks. The current stack signature does not identify which index was invalid, so the specific bounds failure remains unknown.

The previous observation `97b04c576f4c4caa` recorded 536 Classic errors and 30 Tank Royale errors and was diagnosed as a Classic undead-thread stop warning. That Classic warning is not the current failure signature. The registry's existing diagnosis field still describes that earlier observation and does not classify this new Tank Royale failure.

## Finding

The current run cannot confirm HataMoto's score parity because its bundled `axeBots.Stratego` class aborts a Tank Royale attempt. The evidence points to code packaged with the subject robot, rather than a selected opponent or a demonstrated bridge defect, but the exact invalid index is unproven. No bridge code or rumble-jar change is indicated by this observation.

## M-006 handoff

The next registry row was `meleerumble/az.Ololobot_0.2.4.jar`, which was retested next; see AN-338.
