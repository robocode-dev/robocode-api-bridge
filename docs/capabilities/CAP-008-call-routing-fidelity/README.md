---
id: CAP-008
type: capability
status: active
links: [G-001, ARCH-002, CAP-003]
goal: G-001
title: Bot API call-routing fidelity
provenance: inferred
reversal-cost: low
---

# CAP-008 — Bot API call-routing fidelity

When a robot calls something, the adapter routes that call to the Bot API method that performs it. This capability is the promise that each call reaches the right method, with the right arguments, in the right units and the right direction — and that no method on the surface a robot is given goes untested.

## Why it exists as its own capability

`CAP-003` covers what the adapter does to *values*: an angle changing frame, a Tank Royale event becoming its Robocode counterpart. This covers what it does to *calls*. They are different failures with different symptoms, and the distinction was not obvious until both had happened.

Every defect this project has found by testing lives here rather than in the conversions:

`AN-005` — remaining-turn values swapped between gun and radar in a wide positional constructor. Each value was converted correctly and arrived at the wrong field.

`AN-007` — remaining turn converted its unit but not its handedness, so a robot reading it through the status saw the opposite direction from one reading it through the peer. Both paths had conversion tests. Neither compared them.

Neither was a conversion error, and neither could have been caught by testing conversions harder.

## What it covers

The whole peer surface a robot is handed: movement and turning, firing, colours, the state getters, stopping and resuming, the independence flags, the queued commands, custom events and priorities, data access, team messaging, and the junior combined move.

It also covers two properties of the set rather than of any member:

**Completeness.** Every method is exercised by a test or exempt for a stated reason, checked by enumerating the interfaces reflectively rather than from a list. A method added to any peer interface fails the build until it is covered.

**Agreement between paths.** A robot can read its state through the peer's getters or through the `RobotStatus` it is handed in `onStatus`. Those are separate implementations of the same answers, and they must agree.

## What it does not cover

Whether the Bot API then does the right thing. This capability proves the bridge makes the correct call; `AN-006` is the standing reminder that a correct call can still produce nothing — the bridge overrides the Bot API's death handler and calls through properly, and the handler never fires. Catching that needs `CAP-001` and the conformance tier.

It also does not cover the reproduced `robocode.*` surface being complete. That is `ARCH-002`, and it is unverified.

## Status

`active`. Its criteria are proven by unit tests that run in CI with no engine present, and the completeness criterion is enforced rather than claimed.
