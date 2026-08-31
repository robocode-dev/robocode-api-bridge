---
id: CH-004
type: change
status: proposed
links: [G-002, CAP-001, CAP-003, AN-010, P-001]
title: Repair the tag/test mismatches G-002 found
---

# CH-004 — Repair the tag/test mismatches G-002 found

## What

`G-002` found that three passing conformance tests in `CAP-001` prove behaviour their tags do not name, and `AN-010` found two smaller unit-tier gaps in `CAP-003`. This change closes both:

- Mint `EVT-011` (round and battle completion each reach their handler exactly once) and retag the two tests currently mistagged `EVT-005`.
- Mint `EVT-012` (winning a round reaches the win handler) and retag the test currently mistagged `EVT-004`.
- Retire `EVT-003` and mint `EVT-013` with a scenario that matches what classic's own `InteruptibleEvent` robot and its own suite actually assert — same-priority re-entry into an interruptible handler, not a higher-priority one. No classic robot in the source tree exercises genuine higher-priority re-entry, so the original wording promised evidence that cannot exist for this robot; `IDR-003` records the decision and why it forecloses rather than defers.
- Promote `EVT-011`, `EVT-012`, and `EVT-013` — each now has passing evidence under its correct name.
- Narrow `API-001`'s scenario to the one direction `AngleConverter` implements, marking it `(single-direction)`; the round-trip clause described a symmetry the adapter never needed, per `AN-010`.
- Add the missing `API-004` owner-departed test.
- Add plan doors `M-139`/`M-140`/`M-141` for `EVT-011`/`EVT-012`/`EVT-013`; mark `M-103` (`EVT-003`) superseded.

## Why

`G-002`'s own argument: a mistagged test is worse than a missing one, because it makes a criterion look ready to promote when nothing kept the promise. `CH-003` nearly promoted `EVT-005` on exactly this reasoning, and `M-001` cannot honestly close while its fourth clause — criteria active rather than `@draft` — can be satisfied by a mistag.

## Route

Full. Every mint, retirement, and promotion changes what `CAP-001`'s and `CAP-003`'s accepted contract says is proven.
