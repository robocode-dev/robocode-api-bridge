---
id: CRIT-006
type: criteria
status: active
links: [CAP-006]
title: Team robot support — acceptance criteria
ac-prefix: TEAM
provenance: inferred
reversal-cost: high
---

# CAP-006 — acceptance criteria

Every criterion is active against `M-005`, with two-engine integration evidence in `TeamRobotConformanceTest` and a read-only collection-jar smoke check in `compat-test`.

```gherkin
Feature: Team robot support

  @TEAM-001
  Scenario: A team jar produces a runnable Tank Royale bot directory (single-direction)
    Test-type: Integration
    Given a team jar with its descriptor and member robots
    When the wrapper processes it
    Then a bot directory is produced that boots and takes part in a battle
    And the team is no longer recorded as skipped
    # (single-direction): a valid generated team either takes part in the battle or does not;
    # malformed roster rejection is covered by the focused staging unit test.
    # Plan door: M-005, closed by the team conformance and collection smoke checks.

  @TEAM-002
  Scenario: A message sent to teammates arrives as classic delivers it
    Test-type: Integration
    Given a team whose members exchange messages
    When the same battle runs on classic Robocode and on Tank Royale through the bridge
    Then each member reports receiving the same messages from the same senders
    And a message addressed to one teammate does not reach the others
    # Plan door: M-005, closed by the two-engine message probes.

  @TEAM-003
  Scenario: A droid relies on its teammates rather than on its own radar
    Test-type: Integration
    Given a team containing a droid
    When the same battle runs on both engines
    Then the droid receives no scan events of its own on either engine
    And it acts on teammate information identically
    # A droid that receives scans it should not is a fidelity defect that makes the
    # robot stronger, so nothing about the battle looks wrong. Plan door: M-005,
    # closed by the two-engine droid probes.
```
