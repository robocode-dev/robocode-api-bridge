---
id: PDR-003
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-005, CAP-007, C-004, P-001]
title: Treat every rumble subject as a versioned parity case against classic Robocode
---

# PDR-003 — Treat every rumble subject as a versioned parity case against classic Robocode

## Decision

The compatibility harness keeps an append-only, tracked parity registry for every robot jar and team. Classic Robocode is the behavioural reference. Each observation records the source identity, official setup, engine artifacts, outcomes, normalized errors, and any focused retest's immutable diagnosis and repair reference. Diagnosis is an append-only event history, so later triage cannot alter earlier evidence.

Errors compare by exception class and first legacy callback or application frame. Tank-Royale-only errors stop score collection immediately because classic has already established the reference. Completion and error mismatches in either direction remain unresolved cases.

First-pass work uses bounded batches. A repair retests only unresolved cases tagged to its named cause and cases sharing that cause; matched cases remain evidence and are not rerun by default. Score gaps are investigated only after the existing repeated-measurement rule confirms them.

## Consequences

The registry, rather than an ignored local report, is the evidence carrier for the population-level M-006 judgment. The checked-in roborumble checkpoint is an initial partial population result; teams and the other rumble divisions enter through later bounded batches. A repair can show which observed failures it fixed without rerunning healthy jars. The collection stays read-only, and an upstream Tank Royale repair is rebuilt as a compatible Bot API and runner pair before its cases are retested.
