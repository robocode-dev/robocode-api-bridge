---
id: IDR-004
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-001, PDR-001, AN-009]
title: EVT-007's cross-engine death-order claim is retired; survivor delivery is measured directly
---

# IDR-004 — EVT-007's cross-engine death-order claim is retired; survivor delivery is measured directly

## Decision

Retire `EVT-007` and mint `EVT-014`: a surviving robot receives another robot's death event on each engine. The conformance probe reports its handler marker on both engines.

## Context

`EVT-007` required the same deaths in the same order across the engines. Tank Royale has no deterministic seed, so those are different battles; ordering cannot be compared honestly. `AN-009` established the observable defect: survivors received no death event at all.

## Consequences

The new criterion measures that missing behavior directly without claiming cross-engine sequence equality. The exact order of otherwise valid deaths remains outside this evidence boundary.
