---
id: IDR-005
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-001, PDR-001]
title: EVT-001's priority-order claim is retired; the filter boundary is measured directly
---

# IDR-005 — EVT-001's priority-order claim is retired; the filter boundary is measured directly

## Decision

Retire `EVT-001` and mint `EVT-015`: a lower-priority scan handler is not entered while a higher-priority wall handler is blocked on a radar turn, on classic Robocode and through the bridge.

## Context

The named classic robot prints a scan marker and its authoritative test asserts that the marker is absent while running against `sample.Target`; it does not record a handler order. A criterion about recorded order therefore claims behavior that its source evidence cannot observe. The conformance harness stages that opponent fixture for both engines and resets classic's deterministic test seed for this fixture. Because Tank Royale has no battle seed, the conformance test uses a bridge-owned probe that records only a scan entered during the blocked wall-handler window, avoiding unrelated scans before that window.

## Consequences

The conformance test measures the observable classic expectation without generalizing it into a complete event-order claim. Broader priority ordering remains unproven until a source robot records it directly.
