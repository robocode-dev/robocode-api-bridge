---
id: G-002
type: goal
status: proposed
links: [CAP-001, CAP-003, CAP-008, AN-010, PDR-001]
title: Every test proves the criterion its name claims
provenance: inferred
reversal-cost: low
---

# G-002 — Every test proves the criterion its name claims

## Who wants it

Anyone reading the corpus to find out what is proven — a maintainer deciding whether a milestone can close, a reviewer deciding whether a criterion may leave `@draft`, the next agent picking up the campaign. All three read the tag, not the assertion.

## What they want

That a test named `testEVT005_...` proves what `EVT-005` says, in whichever tier the test lives in. The conformance tier is where this broke; `AN-010` found the unit tier holds up far better, but not perfectly, so this is a whole-corpus concern rather than one class's problem.

In `RoundOutcomeEventsConformanceTest`, three tests pass and none of them proves the criterion it names:

- Two are tagged `EVT-005`, which says new-turn events arrive at the classic point in the turn. They assert that round and battle completion reach their handlers, and that a round end is reported once per round. Different behaviour.
- One is tagged `EVT-004`, which says a robot's own death reaches its death handler. It asserts that winning a round reaches the win handler. Also different behaviour, and `EVT-004`'s real test is the disabled one beside it.

No criterion in `CAP-001` covers round or battle completion reaching a handler, so what these tests prove is genuinely uncovered. The tags are the only thing claiming otherwise.

A third instance sits in the other conformance class, and it is the subtler kind. `EVT-003` says an interruptible handler is re-entered when a **higher-priority** event arrives. The robot proving it, classic's `InteruptibleEvent`, opens by setting `HitWallEvent` to the *same* priority as `ScannedRobotEvent`, so nothing higher-priority ever arrives; and the test asserts only that the scan marker appears somewhere in the battle, which an ordinary delivery after the wall handler returns would satisfy just as well. The scenario body and the test match line for line. Only the scenario's name overclaims — which is why `CH-003` nearly promoted it, and why the review that caught it had to read the robot's source to see it.

That is the shape to watch for: a tag can be wrong, and so can a name that the criterion's own body does not support.

## Why it matters

A mistagged test is worse than a missing one. A missing test leaves a criterion `@draft` and the gap is visible; a mistagged test makes a criterion look ready to promote, and promoting it records a promise as kept when nothing kept it. `CH-003` found this while trying to promote `EVT-005` on exactly that reasoning.

It also costs evidence in the other direction: the round-outcome behaviour these tests really do prove has no criterion, so a passing test is buying nothing.

`AN-010` found the unit tier does not have this problem at the same severity — every `API` and `ROUTE` criterion has a test, no test carries a nonexistent ID, and assertions name the specific call and argument rather than a loose marker, which is the vocabulary that let the conformance-tier mismatches through unnoticed. It still has two narrow gaps: `API-001`'s round-trip clause is untested because the adapter has no reverse angle conversion to test it against, and `API-004`'s owner-departed case has no test — the test carrying its tag proves a null-victim case instead, a different field. Neither is the promote-on-false-evidence failure the conformance tier produced; both are gaps in an otherwise-tight net.

## What was done

`CH-004` closed every instance this goal named. `EVT-011` and `EVT-012` were minted for what the round-completion and win-handler tests actually assert, and the tests retagged to them. `EVT-003` was retired rather than repaired — no robot in the source tree exercises genuine higher-priority re-entry, so its successor `EVT-013` claims only the same-priority re-entry the evidence can actually show (`IDR-003`). `API-001` was narrowed to the one direction the adapter implements; `API-004` gained the owner-departed test it was missing.

## What remains

Only `InterruptibleEventConformanceTest`, `RoundOutcomeEventsConformanceTest`, and the unit tier's thirteen test classes under `robocode-api/src/test/java/.../bridge/` have been read against their criteria. Nothing else in the corpus has been — the conformance classes that will be added for `CAP-002`'s physics criteria (`M-111`–`M-118`) carry the same risk this goal exists to catch, and each should be checked as it lands rather than assumed clean by analogy.

`M-001` should not close while any capability's criteria have not been checked this way, because its fourth clause is about criteria being active rather than `@draft`, and a mistag is the one thing that makes that clause easy to satisfy dishonestly.
