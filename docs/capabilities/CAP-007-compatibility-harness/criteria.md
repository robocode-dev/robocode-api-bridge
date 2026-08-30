---
id: CRIT-007
type: criteria
status: draft
links: [CAP-007]
title: The compatibility harness — acceptance criteria
ac-prefix: HARN
provenance: inferred
reversal-cost: low
---

# CAP-007 — acceptance criteria

Every criterion is `@draft` against `M-001`. The first three describe behaviour that already works; they are draft because Python is not a supported evidence carrier, not because the behaviour is missing. `AN-003` records the distinction.

```gherkin
Feature: The compatibility harness

  @HARN-001 @draft
  Scenario: An interrupted sweep resumes without repeating completed work
    Test-type: Unit
    Given a sweep that has completed some robots and recorded its progress
    When the run is interrupted and started again
    Then it continues from the first untested robot
    And a completed robot is not re-run unless a retry is explicitly requested
    # Works today. Untested. Plan door: M-001.

  @HARN-002 @draft
  Scenario: A hanging robot is killed with its process tree and recorded as failed
    Test-type: Integration
    Given a robot that does not terminate
    When its per-side timeout expires
    Then the worker and every server, booter, and bot process beneath it are terminated
    And the robot is recorded as failed rather than stalling the sweep
    # Works today. Untested. Plan door: M-001.

  @HARN-003 @draft
  Scenario: Each result is classified by the documented vocabulary
    Test-type: Unit
    Given scores and error counts from both engines
    When a result is classified
    Then it carries the documented status for that combination
    And a known false positive is excluded from the error count without being hidden from the log
    # A pure function of its inputs, and therefore exactly what a unit test is for.
    # Works today. Untested. Plan door: M-001.

  @HARN-004 @draft
  Scenario: A regression verdict averages repeats before comparing
    Test-type: Unit
    Given repeated measurements for a watched bot
    When the regression verdict is computed
    Then the averaged delta is compared against the baseline band
    And a bot marked as noise never fails the run
    # Does not exist. Plan door: M-001.

  @HARN-005 @draft
  Scenario: A trace emits per-turn state from both engines for one robot
    Test-type: Integration
    Given a robot and a turn count
    When a trace is requested
    Then per-turn position, headings, and energy are emitted for both engines in a comparable form
    # The diagnostic the score gaps need and nothing currently provides. Plan door: M-001.

  @HARN-006 @draft
  Scenario: A Tank-Royale-only exception aborts the battle at once
    Test-type: Integration
    Given a classic run that produced a known set of exception signatures
    When the Tank Royale run emits a signature not in that set
    Then the battle is stopped at that point and the signature is recorded
    # The rule C-004 states. Does not exist. Plan door: M-001.

  @HARN-007 @draft
  Scenario: Each division runs at its official parameters
    Test-type: Unit
    Given a division
    When its battle setup is resolved
    Then the battlefield, round count, and participant count are that division's official values
    # The harness currently uses one setup for every division. Plan door: M-001.
```
