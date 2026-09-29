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

`TEAM-001`, `TEAM-002`, and `TEAM-003` are active against `M-005` with two-engine integration evidence. Name-map conformance uses the matched local Tank Royale 1.4.0 Bot API and runner.

```gherkin
Feature: Team robot support

  @TEAM-001
  Scenario: A team jar produces a runnable Tank Royale bot directory
    Test-type: Integration
    Given a team jar with its descriptor and member robots
    When the wrapper processes it
    Then a bot directory is produced that boots and takes part in a battle
    And the team is no longer recorded as skipped
    # Evidence: TeamSupportConformanceTest.testTEAM001_IntegrationPositive_TeamEntryBootsEveryMember and
    # TeamSupportConformanceTest.testTEAM001_IntegrationNegative_TeamEntryDoesNotCollapseMembers.
    # Plan door: M-005.

  @TEAM-002
  Scenario: A message sent to teammates arrives as classic delivers it
    Test-type: Integration
    Given a team whose members exchange messages by classic name
    When the same battle runs on classic Robocode and on Tank Royale through the bridge
    Then each member reports receiving the same messages from the same senders
    And a message addressed to one teammate does not reach the others
    And getName(), getTeammates(), isTeammate(), and MessageEvent.getSender() expose classic names
    # Evidence: TeamSupportConformanceTest.testTEAM002_IntegrationPositive_TeammateMessagesReachIntendedMembers,
    # testTEAM002_IntegrationPositive_ExposesClassicNamesAndResolvesThem, and
    # testTEAM002_IntegrationNegative_DirectedMessageIsNotBroadcast prove delivery, full names including
    # duplicate suffixes, name resolution, sender identity, and recipient isolation. Plan door: M-005.

  @TEAM-003
  Scenario: A droid relies on its teammates rather than on its own radar
    Test-type: Integration
    Given a team containing a droid
    When the same battle runs on both engines
    Then the droid receives no scan events of its own on either engine
    And it acts on teammate information identically
    # A droid that receives scans it should not is a fidelity defect that makes the
    # robot stronger, so nothing about the battle looks wrong. Evidence: TeamSupportConformanceTest.testTEAM003_IntegrationPositive_DroidReceivesTeammateInformationWithoutScans and
    # TeamSupportConformanceTest.testTEAM003_IntegrationNegative_DroidNeverReceivesOwnScan. Plan door: M-005.
```
