---
id: DES-008
type: design
status: active
links: [CAP-008, CAP-003, ARCH-002, PDR-001, AN-005, AN-007]
title: Bot API call-routing fidelity — design
provenance: inferred
reversal-cost: low
---

# CAP-008 — design

## The seam

`BotPeer` holds its `IBot` as an interface field and builds one in its public constructor. A package-private constructor takes one instead, so the peer can be driven without a server.

That is the whole enabling change, and it is small on purpose. Routing is only observable at the boundary between the two APIs, and in a battle that boundary sits behind a WebSocket and reports back as a score. With the seam it is a method call and an assertion.

## The fake

`RecordingBot` is a dynamic proxy over `IBot` that records every call with its arguments and answers canned values for getters.

A proxy rather than a hand-written stub because `IBot` is wide and still growing. A stub would need a new method body every time the Bot API gains one, and that compiler error arrives as an obstacle rather than as information — the test that should notice a new method is the coverage check, which reports it as a gap to fill.

Two details earn their place. `onlyCall` fails when a method was called more than once, because a duplicated command is a routing fault rather than something to take the first match of. And canned values are looked up by key presence rather than by null check, so the fake can answer `null` — which the real Bot API does, for a bot with no teammates. A fake that cannot express null tests its own default instead of the peer, which is how the first team test passed against nothing.

## What the tests assert

Not "the call succeeded". Each names the Bot API method reached and the argument passed, and the negative half asserts what was *not* called — that turning the body did not move it, that a queued command did not complete the turn, that setting one colour set no other.

That negative half is doing most of the work. A method that routes to the wrong neighbour still compiles, still runs, and still produces a battle; only an assertion that the neighbour was untouched distinguishes it.

## Completeness, and why it is enforced rather than reviewed

`PeerSurfaceCoverageTest` walks the peer interfaces reflectively from `ITeamRobotPeer` and `IJuniorRobotPeer`, and fails naming any method no routing test calls.

Coverage is judged by reading the routing tests' own source for a call through the peer. That is deliberately literal. A registry mapping methods to test names would prove a name exists, not that anything calls the method — and the failure being guarded against is precisely a method nobody exercised.

Three properties follow, and the second and third matter as much as the first:

- A method added to any peer interface fails the build until covered or exempted.
- An exemption naming a method that no longer exists fails too, so exemptions cannot outlive their subject and silently excuse a future method of the same name.
- The check verifies it can actually read the sources, because a scan that quietly found nothing would report perfect coverage while proving nothing.

Exemptions are stated individually with reasons rather than by pattern. A pattern absorbs the next method that happens to match it.

## The cross-path guard

`StatusAndPeerAgreementTest` compares the peer's getters against the status mapper field by field, signed, with a distinct value per field.

It exists because `AN-007` was a disagreement between two implementations that each had passing tests. A test written from an implementation's behaviour confirms that implementation; the only thing that finds a disagreement is a test written against the other side. This is that test, and it is the shape to reach for wherever two paths answer the same question.

## What this design cannot reach

Everything below the Bot API call. `AN-006` is the standing example: the peer's routing for the death handler is correct and the handler never fires. This tier proves the bridge asks correctly, and asking correctly is not the same as being answered.
