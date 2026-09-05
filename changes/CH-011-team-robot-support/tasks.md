---
id: TASKS-007
type: tasks
status: open
links: [CH-011]
title: Team robot support change tasks
provenance: inferred
reversal-cost: low
---

# Tasks

- [x] Add team-aware staging in `compat-test/compat_test.py`, including roster discovery, independent member copies, manifest rewriting, script patching, and per-member log collection for `TEAM-001`.
- [x] Update the classic and Tank Royale worker invocation/participant limits so one selected team is expanded into its members while two selected team entries remain two teams for `TEAM-001`.
- [x] Add focused positive and negative harness tests for valid team staging and missing or malformed member configuration, covering `TEAM-001`.
- [x] Add purpose-written team message and droid probe robots plus the conformance execution path needed to compare both engines for `TEAM-002` and `TEAM-003`.
- [x] Run the team integration checks against a real read-only collection jar and record evidence for all three criteria; investigate and record any bridge-only behavior before activating a criterion.
- [x] Update the CAP-006 criteria/design/README, P-001, architecture/harness guidance, and changelog; regenerate indexes and digest the change.
- [x] Run the applicable unit, conformance, wrapper, and team-harness checks.
