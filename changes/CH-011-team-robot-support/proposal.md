---
id: CH-011
type: change
status: open
links: [P-001, CAP-006, M-005, TEAM-001, TEAM-002, TEAM-003, IDR-008]
title: Run team robots through the compatibility harness
---

# CH-011 — Run team robots through the compatibility harness

## What

Complete `M-005` by making the compatibility harness stage and run team jars on classic Robocode and Tank Royale, and by adding purpose-written team evidence for team messaging and droid behavior.

## Why

`CH-010` produced the wrapper and peer groundwork for team support, but the harness still treats every division as a flat participant list and records team jars as skipped. The bridge therefore has no integration evidence for `CAP-006`, and the team criteria remain draft even though the production paths they describe are partly implemented.

## Contract impact

This is a full change. It enables a previously skipped division and changes `TEAM-001`, `TEAM-002`, and `TEAM-003` from draft promises to active, evidence-backed criteria if their focused evidence passes. The plan exit criterion for `M-005` will be closed only when the team division is runnable and the wrapper output is verified.

## Scope

The change will update the Python orchestration and its classic/Tank Royale worker inputs to preserve team membership and roster identity across staging, preserve arbitrary classic Serializable team messages through the bridge, add team-focused conformance robots and evidence, keep the rumble collection read-only, and update the capability, plan, indexes, changelog, and acceptance brief during digest. The TwinDuel division remains out of scope.

## Non-goals

This change does not alter the frozen `robocode.*` API surface, replace Tank Royale's native team model, implement raw `java.io` confinement, or establish the full three-division sweep baseline in `M-006`.
