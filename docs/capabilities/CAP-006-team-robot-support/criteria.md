---
id: CRIT-006
type: criteria
status: draft
links: [CAP-006]
title: Team robot support — acceptance criteria
ac-prefix: TEAM
provenance: inferred
reversal-cost: high
---

# CAP-006 — acceptance criteria

Every criterion is `@draft` against `M-005`. All describe behaviour that does not exist.

```gherkin
Feature: Team robot support

  @TEAM-001 @draft
  Scenario: A team jar produces a runnable Tank Royale bot directory
    Test-type: Integration
    Given a team jar with its descriptor and member robots
    When the wrapper processes it
    Then a bot directory is produced that boots and takes part in a battle
    And the team is no longer recorded as skipped
    # Plan door: M-005.

  @TEAM-002 @draft
  Scenario: A message sent to teammates arrives as classic delivers it
    Test-type: Integration
    Given a team whose members exchange messages
    When the same battle runs on classic Robocode and on Tank Royale through the bridge
    Then each member reports receiving the same messages from the same senders
    And a message addressed to one teammate does not reach the others
    # Plan door: M-005.

  @TEAM-003 @draft
  Scenario: A droid relies on its teammates rather than on its own radar
    Test-type: Integration
    Given a team containing a droid
    When the same battle runs on both engines
    Then the droid receives no scan events of its own on either engine
    And it acts on teammate information identically
    # A droid that receives scans it should not is a fidelity defect that makes the
    # robot stronger, so nothing about the battle looks wrong. Plan door: M-005.
```
