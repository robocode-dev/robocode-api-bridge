---
id: CH-019
type: change
status: open
links: [P-001, CAP-007, CRIT-007, AN-003, AN-016]
title: Add supported integration evidence for skipped-turn telemetry
---

# CH-019 — Add supported integration evidence for skipped-turn telemetry

## Problem

CH-018 added opt-in skipped-turn telemetry and local probe evidence, but HARN-008 remains draft because its current checked-in tests are Python unit tests and the local runs are not a supported acceptance carrier. The callback-to-observation path needs an attributable JVM integration test before the evidence door M-148 can close.

## Change

Extend the existing JVM conformance test bed to run the compatibility harness with skipped-turn capture enabled and assert the result it produces. Reuse its existing staging and runner path; do not create a second battle implementation or change the evidence policy. Prove exact `(bot ID, round, turn)` correspondence to callback output, preservation of early turns, completed empty capture, and distinct disabled, unavailable, and incomplete outcomes. Activate HARN-008 and close M-148 only if the supported tests cover its full scenario.

## Plan and acceptance

This change serves `P-001/M-006` through evidence door `M-148`. M-006 remains open; one telemetry criterion does not complete the parity campaign.

## Challenge to the commitment

The main assumption is that a JUnit integration test which launches the existing Python compatibility harness and makes its own assertions over the returned observation is an honest supported JVM evidence carrier for the end-to-end contract. A credible alternative is to keep HARN-008 draft until telemetry aggregation moves to a supported implementation or Python becomes an accepted carrier. The cheapest useful test is a local JUnit probe that invokes the existing `--conformance` path with capture enabled and compares its structured telemetry with the probe's callback markers. Revise or stop if that path cannot expose the observation that HARN-008 promises, if it requires copying the Python parsing logic into Java, or if the test cannot reliably cover the warm-up events and status distinctions.

An implementation could pass by comparing callback markers to a transient JSON response while the compatibility checkpoint or registry still drops the telemetry, leaving the person using M-006 without durable evidence. The integration test must assert the same persisted observation path used by compatibility measurements, and the digest must state that this tier remains local and may skip where the two-engine environment is absent.
