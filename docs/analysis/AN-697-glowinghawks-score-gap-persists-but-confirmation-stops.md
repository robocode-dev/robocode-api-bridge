---
id: AN-697
type: analysis
status: active
links: [P-001, PDR-001, PDR-003, AN-043, CAP-005, CAP-006, CAP-007]
title: GlowingHawks repeats its score gap but the confirmation stops on its known robot error
provenance: inferred
reversal-cost: low
---

# AN-697 — GlowingHawks repeats its score gap but the confirmation stops on its known robot error

## Risk investigated

Whether the Tank Royale score advantage for `teamrumble/rz.GlowingHawks_0.2.jar` is repeatable with current matched artifacts, and whether the known death-handler error indicates a bridge defect.

## Evidence boundary

The read-only team jar has SHA-256 `9aed0826521a57def3253fa9b220bae37b5b8f2261604e842c08f8d54a5b77ce`. The current measurements used official TeamRumble parameters (1200×1200, ten rounds, two teams), Classic Robocode 1.11.1, bridge commit `4c2ba05e3468ab9f09ffb48c0549b17994984753`, and Tank Royale commit `b952543aa604de43545f003d62488ef07271e08d`. The Bot API, runner, and wrapper hashes are recorded on each observation; no rumble jar was modified.

The completed one-pair observation is `7e1ce73adba714e3`. The subsequent five-repeat attempt is `3b6964d09b8049d7`; it stopped after two valid pairs, so the attempt is not a five-pair score confirmation.

## What was tried

The one-pair retest completed without errors: Classic scored 22,082 and Tank Royale scored 28,267, a +28.0% delta.

The five-repeat attempt completed two pairs at +35.6% and +29.6%, for a two-pair mean of +32.6%. Attempt three stopped after the known `rz.GHMember.onRobotDeath` null dereference appeared on the Tank Royale side. The confirmation contains only two completed score samples; telemetry for the failed attempt is incomplete.

## Finding

All three completed score comparisons exceed the 25% band, but only two of the five requested confirmation pairs completed. The score advantage remains an unresolved score signal, not a confirmed registry score result.

AN-043 traces the death-handler exception to the read-only robot assuming that every death event has a prior scan entry. The current exception matches that robot-owned defect and does not establish a bridge identity or event-dispatch defect.

The existing registry diagnosis remains `robot-death-event-assumes-prior-scan`, owner `robot`. No code changed.

## Implications for M-006

Keep the score question open until five valid pairs complete or a separate diagnostic resolves the trigger. This retest adds no bridge repair and does not close the team's parity case.

## M-006 handoff

Continue in registry order with `teamrumble/tmnr.TMNR_1.01.jar`.
