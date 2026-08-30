---
id: G-002
type: goal
status: proposed
links: [CAP-001, PDR-001]
title: Every conformance test proves the criterion its name claims
provenance: inferred
reversal-cost: low
---

# G-002 — Every conformance test proves the criterion its name claims

## Who wants it

Anyone reading the corpus to find out what is proven — a maintainer deciding whether a milestone can close, a reviewer deciding whether a criterion may leave `@draft`, the next agent picking up the campaign. All three read the tag, not the assertion.

## What they want

That a test named `testEVT005_...` proves what `EVT-005` says. Today, in `RoundOutcomeEventsConformanceTest`, three tests pass and none of them proves the criterion it names:

- Two are tagged `EVT-005`, which says new-turn events arrive at the classic point in the turn. They assert that round and battle completion reach their handlers, and that a round end is reported once per round. Different behaviour.
- One is tagged `EVT-004`, which says a robot's own death reaches its death handler. It asserts that winning a round reaches the win handler. Also different behaviour, and `EVT-004`'s real test is the disabled one beside it.

No criterion in `CAP-001` covers round or battle completion reaching a handler, so what these tests prove is genuinely uncovered. The tags are the only thing claiming otherwise.

## Why it matters

A mistagged test is worse than a missing one. A missing test leaves a criterion `@draft` and the gap is visible; a mistagged test makes a criterion look ready to promote, and promoting it records a promise as kept when nothing kept it. `CH-003` found this while trying to promote `EVT-005` on exactly that reasoning.

It also costs evidence in the other direction: the round-outcome behaviour these tests really do prove has no criterion, so a passing test is buying nothing.

## What it would take

Mint criteria for what the tests actually assert — round and battle completion reaching their handlers, and the once-per-round shape — and retag the tests to them. `EVT-004` and `EVT-005` keep their meaning and stay `@draft`; nothing is retired, because neither criterion's meaning changes. Then re-audit the rest of the suite: this was found by reading two test names against two scenarios, and nothing has read the others.

`M-001` should not close before this is done, because its fourth clause is about criteria being active rather than `@draft`, and a mistag is the one thing that makes that clause easy to satisfy dishonestly.
