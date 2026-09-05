---
id: CH-011
type: change
status: proposed
links: [P-001, CAP-006, CRIT-006, ADR-002]
title: Run team robots through the compatibility harness
provenance: agent
---

# CH-011 — Run team robots through the compatibility harness

## What changes

Complete the harness half of M-005. Team jars will be staged as team entries rather than individual member bots, their member trees will be duplicated without sharing process logs or configuration, and the classic and Tank Royale workers will run the same number of teams at the official teamrumble setup.

The change will add focused integration evidence for `TEAM-001`, `TEAM-002`, and `TEAM-003`: a team jar boots in both engines, teammate messages have the expected delivery, and a droid does not receive its own scan events. The existing wrapper and peer implementation from CH-010 remains the production path under test.

## Why

CH-010 deliberately stopped at wrapper and peer groundwork because `compat-test` still treated every team jar as `SKIPPED-TR`. M-005 cannot close while the harness has no team-roster path, and the draft CAP-006 criteria have no integration evidence until that path exists.

## Scope

- Detect the generated team boot entry and preserve its member roster when staging a team jar.
- Create independent copies of every member directory for each team instance, rewrite copied team manifests, and collect per-member logs for fail-fast error attribution.
- Run team jars on classic Robocode and Tank Royale instead of recording `SKIPPED-TR`; keep ordinary robot and melee behavior unchanged.
- Adjust participant-limit and result handling for team entries, whose runner identity count is larger than their entry count.
- Add purpose-written team probes and executable integration checks for team startup, messaging, and droid scan behavior.
- Update CAP-006 evidence/status, P-001 bookkeeping, harness guidance, and the user-facing changelog after the evidence is complete.

## Out of scope

TwinDuel remains outside CAP-006, and the bridge's accepted numeric-id teammate addressing remains the decision recorded in ADR-002. No `robocode.*` signature changes are expected.

## Documentation impact

The capability, harness guidance, architecture overview, plan bookkeeping, and changelog will need updates because the team division moves from an explicit skip to an exercised path. No new architecture decision is expected unless implementation discovers a boundary that contradicts ADR-002.
