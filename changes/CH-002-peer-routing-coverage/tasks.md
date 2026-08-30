---
id: TASKS-002
type: tasks
status: open
links: [CH-002]
title: CH-002 task checklist
---

# CH-002 — tasks

Ordered; the seam and the fake come first because every test depends on them.

## Foundation

- [ ] Add a package-private `BotPeer` constructor taking an `IBasicRobot` and an `IBot`, so the peer can be driven without a server
- [ ] Write `RecordingBot`: a dynamic-proxy `IBot` that records every call and its arguments, and answers canned state for the getters
- [ ] Write a minimal `IBasicRobot` stub for construction, covering the basic, advanced, and team listener shapes

## Corpus (serves M-007)

- [ ] Write `CAP-008` call-routing fidelity — README, criteria, design; declare its `ac-prefix`
- [ ] Write criteria grouped by peer interface, plus one for the completeness check itself
- [ ] Update `docs/design/README.md`'s map and the architecture overview's capability table for the new capability
- [ ] Mark `M-007` `doing`, and `done` in the digest if its exit criterion is met

## Tests (serve the CAP-008 criteria)

- [ ] `IBasicRobotPeer` — movement, firing, the body/gun/radar turn calls, and the state getters
- [ ] `IStandardRobotPeer` — stop, resume, scan, and the adjust flags
- [ ] `IAdvancedRobotPeer` — the queued `set*` calls, custom events, event priorities, and interruptibility
- [ ] `ITeamRobotPeer` — messaging and teammate queries
- [ ] `IJuniorRobotPeer` — the single junior call
- [ ] Write the reflective coverage test: enumerate the peer hierarchy, fail on any method with no test, and state its exemptions explicitly

## Findings

- [ ] Record any routing defect the tests uncover as an analysis finding, with the measured evidence
- [ ] Fix only defects that are unambiguous routing errors; anything needing a decision becomes an open question and stops

## Verify and integrate

- [ ] `gradlew :robocode-api:test` green, and the coverage check failing when a test is removed
- [ ] `clue validate --forbid-changes` after the digest
- [ ] Run `clue-verify` including its review loop
