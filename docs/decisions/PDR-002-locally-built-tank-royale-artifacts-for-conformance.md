---
id: PDR-002
type: decision
status: verified
author: agent
accepted-by: [Flemming N. Larsen]
links: [PDR-001, C-002, CAP-001]
title: Conformance uses locally built Tank Royale artifacts rather than waiting for releases
---

# PDR-002 — Conformance uses locally built Tank Royale artifacts rather than waiting for releases

## Decision

When bridge conformance needs a Tank Royale repair that is not released, build the Tank Royale Bot API and runner locally from the same upstream revision and use that pair for the comparison. Do not wait for, request, or create a Tank Royale release solely to establish bridge evidence.

## Context

Tank Royale releases are a separate, consequential product operation. They are not a prerequisite for checking whether an upstream repair restores classic Robocode behaviour through this bridge. The bridge needs a compatible Bot API and runner server, not published coordinates.

## Consequences

The conformance setup identifies the Tank Royale source revision and builds both artifacts from it. The bridge is rebuilt with that local Bot API version before the comparison, while the CI-only unit tier retains its published dependency default. `C-002` remains in force: a local pair must still be protocol-compatible and a battle must show that bots act before its results are trusted. A Tank Royale release may later consume the same repair, but that release is outside the bridge change.
