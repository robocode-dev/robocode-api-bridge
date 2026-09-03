---
id: CH-007
type: change
status: open
links: [P-001, CAP-001]
title: Establish the remaining event conformance foundation
---

# CH-007 — Establish the remaining event conformance foundation

## What

Continue M-001 with focused conformance evidence for the event behaviors that remain draft after CH-006: pending same-priority delivery across a blocking handler, turn-boundary timing, custom-event registration and removal, skipped turns, handler exceptions, and melee scan delivery. Use classic's compiled test robots where they state the behavior and bridge-owned probes only where the classic suite has no stable machine-readable marker.

Extend the conformance test adapter only as needed to run the same expectation against both engines, including the official melee participant count. Activate only the criteria whose positive and negative evidence is present and attributable; do not change the meaning of any criterion.

## Why

M-001 is still unmeasurable for most event behavior even though classic's test suite contains the relevant robots. A score sweep cannot show whether a scan was discarded, whether a skipped turn was reported, or whether an exception changed the robot's lifecycle. Named two-engine expectations make those differences fail close to their cause and provide the evidence needed before the campaign's score-gap milestones.

## Route

Full. Activating the remaining CAP-001 criteria and extending the conformance methodology changes the accepted evidence contract for the bridge's event-parity claims.

## Documentation impact

Update CAP-001's criteria and design overview for the evidence that becomes active, update P-001's M-001 evidence doors, and record any implementation decision that constrains future event evidence. No architecture change is expected because the work stays within the existing adapter/conformance boundary.
