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

`TEAM-001` and `TEAM-003` are active against `M-005` with integration evidence. `TEAM-002` remains `@draft`: the focused evidence proves delivery and directed-recipient isolation, but the two engines expose different sender identities and the protocol provides no classic name mapping (`ADR-002`).

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

  @TEAM-002 @draft
  Scenario: A message sent to teammates arrives as classic delivers it
    Test-type: Integration
    Given a team whose members exchange messages
    When the same battle runs on classic Robocode and on Tank Royale through the bridge
    Then each member reports receiving the same messages from the same senders
    And a message addressed to one teammate does not reach the others
    # Evidence: TeamSupportConformanceTest.testTEAM002_IntegrationPositive_TeammateMessagesReachIntendedMembers and
    # TeamSupportConformanceTest.testTEAM002_IntegrationNegative_DirectedMessageIsNotBroadcast prove delivery,
    # sender presence, and recipient isolation. Literal same-name sender parity remains unproven because the
    # Tank Royale protocol exposes numeric ids; see ADR-002. Plan door: M-005.

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
