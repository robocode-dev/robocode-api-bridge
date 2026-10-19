---
id: CH-020
type: change
status: open
links: [P-001, CAP-008, CRIT-008]
title: Route classic stop overwrite through Tank Royale
---

# CH-020 — Route classic stop overwrite through Tank Royale

## What and why

M-006 found that the legacy `jaybot.bots.Oddball` calls `Robot.stop(true)`, and `BotPeer.stop(boolean)` currently rejects that call even though Tank Royale's `IBot` exposes `setStop(boolean)`. This prevents the robot from producing a Tank Royale result and leaves a bridge-owned discrepancy in the parity registry.

Route the Classic overwrite flag to `IBot.stop(overwrite)`. Retire `ROUTE-005` and replace it with `ROUTE-013`, which preserves stop overwrite while retaining the existing stop, resume, and independence-flag coverage.

## Commitment challenge

The key assumption is that Tank Royale `IBot.stop(boolean)` preserves Classic's overwrite semantics for an already-stopped robot. Its interface documents that `true` overrides a previous saved movement and that `false` behaves like `setStop()`, matching the Classic contract.

The alternative is to keep `stop(true)` unsupported and record this as a known bridge limitation. That conflicts with M-006's exit criterion, which requires bridge-owned discrepancies to be repaired and retested.

The cheapest useful check is a peer-routing regression test for both boolean values, followed by the official-parameter retest of `jaybot.bots.Oddball`. Revise or stop the change if Tank Royale's resulting stop/resume behavior differs from Classic in the focused retest.

An implementation could pass the routing test and still fail the user if the saved command state differs after an earlier stop. The official robot retest is included to detect that failure beyond the unit criterion.

## Scope

This change updates the CAP-008 stop-routing criterion, the peer implementation and focused unit evidence, the capability design, the parity registry after retest, and `CHANGELOG.md`. It does not change the frozen `robocode.*` signatures or the M-006 plan promise.
