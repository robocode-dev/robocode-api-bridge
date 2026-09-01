---
id: CH-006
type: change
status: proposed
links: [P-001, M-001, CAP-001, EVT-001, EVT-015, IDR-005]
title: Establish the first M-001 event-priority evidence door
---

# CH-006 — Establish the first M-001 event-priority evidence door

## What

Start M-001 with the event-priority behavior that classic Robocode's `EventPriorityFilter` actually exercises. Retire `EVT-001`, whose wording promises a recorded handler order that the named classic robot does not record, and mint `EVT-015` for the observable behavior it does prove: a lower-priority scan is suppressed while a higher-priority wall handler is blocked on its radar turn. Add focused integration evidence in both directions and repair the bridge so the same expectation passes on both engines.

## Why

M-001 cannot promote evidence that overclaims what the classic specification test observes. The current bridge run also exposes a real parity defect: classic emits no scan marker for `EventPriorityFilter`, while Tank Royale through the bridge emits scan markers. Narrowing the criterion keeps the corpus honest; the regression test then gives the bridge defect a named, repeatable failure.

## Route

Full. Retiring `EVT-001`, minting `EVT-015`, and recording why the accepted criterion must follow classic's actual evidence changes CAP-001's contract and its plan bookkeeping.
