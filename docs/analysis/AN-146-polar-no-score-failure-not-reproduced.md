---
id: AN-146
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Polar's historical no-score failure does not recur
provenance: inferred
reversal-cost: low
---

# AN-146 — Polar's historical no-score failure does not recur

## Risk investigated

Whether Polar's historical Tank Royale no-score outcome recurs with current matched artifacts, and whether its repeated pattern-gun exception identifies its owner.

## Evidence boundary

The read-only subject jar `cw.megas.Polar_3.2.jar` has SHA-256 `5a65f71be6ed605815763385ebd3ed9db00f248a4e7f65ecc75a566fd317665e`. The current official observation `08028b163eba772a` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `0babe4519e4ff871a0f606b2187a093d17a88897` and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The current Classic run scored 7,480 with 20 errors; Tank Royale scored 7,663 with 6 errors, a +2.4% delta. Both engines reported `StringIndexOutOfBoundsException` from `cw.megas.Polar.gun`, and Tank Royale captured an empty skipped-turn event list. The registry status is `PASS`.

Earlier observation `35a21885ebf299e0` had a Classic score of 7,941 without errors and no Tank Royale score with 59 errors from `cw.megas.Polar.gun`. The first observation `275e5d14496de98c` scored 7,521 in Classic and 7,996 in Tank Royale without errors. The historical no-score outcome does not recur in the current run.

## Finding

The bundled `gun()` source uses the same pattern-search loop and unchecked `eLog.charAt(indX--)` access found in Blade. This matches the current exception origin in both engines. The diagnosis is `robot-enemy-pattern-reads-past-final-sample`, owner `robot`. Current Tank Royale completes with a score inside the score band and has no skipped-turn events; the robot-owned exception still occurs at a lower count. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cx.micro.Blur_0.2.jar` (`score-review`).
