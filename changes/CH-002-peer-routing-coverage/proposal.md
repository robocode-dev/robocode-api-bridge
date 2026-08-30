---
id: CH-002
type: change
status: open
links: [P-001, CAP-003, ARCH-002]
title: Prove every peer method routes to the right Bot API call, and fail the build when one is untested
---

# CH-002 — Prove every peer method routes to the right Bot API call

## What

Add a capability covering **call routing**: for each method on the five `robocode.robotinterfaces.peer` interfaces, prove which Bot API call it makes and with what arguments. Enforce completeness with a reflective check that fails the build when a peer method has no test.

Serves `M-007`.

## Why

`CAP-003` covers the adapter's value conversions — an angle changing frame, a Tank Royale event becoming its Robocode counterpart. Nothing covers the other half of what the adapter does: taking a robot's call and routing it to the Bot API method that performs it, in the right units and the right direction.

That gap is not theoretical. Both defects found so far live in it.

`AN-005` was a positional swap: `RobotStatus` takes remaining turn amounts in body/**radar**/gun order while its headings run body/gun/radar, and the call site passed body/gun/radar. A robot reading `getGunTurnRemaining()` got the radar's rotation. No exception, no log line, both values plausible radians in the same range.

`AN-006` is an event that never dispatches, found only because a conformance robot printed a marker classic printed and the bridge did not.

Neither is a conversion error. Both are routing.

The deeper reason is `ARCH-002`. The `robocode.*` surface is frozen, and the awkwardness that fixes — a constructor whose argument order contradicts its own neighbours — is absorbed at the call site, which is exactly where a silent mistake goes unnoticed. There are 82 such call sites across the peer hierarchy and, before this change, six tests.

## Why enforcement is the point

A test suite covering most of a surface tells you nothing about the part it misses, and the part it misses is where the next `AN-005` is.

The reflective check turns "is this complete?" from a review question into a build failure. It also survives the surface changing: when the Bot API grows a method the bridge routes to, or a peer interface gains one, the build says so instead of the coverage quietly decaying.

This is the difference between claiming a 1-to-1 mapping and having one.

## Scope

In scope:

- A new capability for call-routing fidelity, with criteria grouped by peer interface, its own `ac-prefix`, and a criterion for the completeness check itself.
- A package-private `BotPeer` constructor accepting an `IBot`, so the peer can be driven with a recording fake. `IBot` is already an interface field; this exposes it to the test, and changes no behaviour.
- A recording fake `IBot` behind a dynamic proxy, capturing every call and its arguments.
- One test per peer method, asserting the Bot API call made and the arguments passed, including units, sign, and frame.
- The reflective coverage test.
- `M-007` bookkeeping.

Out of scope:

- Fixing anything the tests find. A defect found here gets a finding and its own change, exactly as `AN-006` did — except a defect that is a plain routing error with an unambiguous correct answer, which is a defect correction restoring an unchanged criterion and may ride along with its test.
- `AN-006` itself. Its cause is unestablished and may be upstream in the Bot API. This change cannot reach it: the tier proves the bridge calls the Bot API correctly, not that the Bot API then does the right thing.
- Anything under `robocode-api/src/main/java/robocode/`. `ARCH-002` forbids signature changes and nothing here needs one.

## Decision boundaries

Authorized: how the fake is built, how criteria are grouped across the peer interfaces, and what the coverage check treats as a legitimate exemption.

Not authorized: changing the frozen surface, or changing bridge behaviour beyond an unambiguous routing correction with a test that proves it.

## What this does not buy

Tier 1 evidence that the bridge makes the right call is not evidence that the right thing happens. `AN-006` is the standing illustration: the bridge overrides the Bot API's `onDeath` and calls through correctly, and the handler still never fires. Catching that needs tier 2, and this change does not change that.

## Route

Full. It adds a capability and acceptance criteria, which is accepted-contract meaning.
