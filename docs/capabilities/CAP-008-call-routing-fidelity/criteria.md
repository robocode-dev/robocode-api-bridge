---
id: CRIT-008
type: criteria
status: active
links: [CAP-008]
title: Bot API call-routing fidelity — acceptance criteria
ac-prefix: ROUTE
provenance: inferred
reversal-cost: low
---

# CAP-008 — acceptance criteria

All proven by unit tests driving the peer against a recording fake, with no engine present. Each test carries its criterion, proof type, and direction in its own name.

```gherkin
Feature: Bot API call-routing fidelity

  @ROUTE-001
  Scenario: Movement and turning reach their own Bot API calls
    Test-type: Unit
    Given a robot that moves or turns a component
    When the call is routed
    Then it reaches the Bot API method for that component, in degrees, with the direction preserved
    And it does not reach any other component's call

  @ROUTE-002
  Scenario: Firing routes with its power unchanged
    Test-type: Unit
    Given a robot that fires
    When the call is routed
    Then the Bot API receives the same power
    And no movement or turning call is made

  @ROUTE-003
  Scenario: Each colour reaches its own Bot API setter
    Test-type: Unit
    Given a robot that sets one of its colours
    When the call is routed
    Then that colour's Bot API setter receives it, including where the two engines name the part differently
    And no other colour is set

  @ROUTE-004
  Scenario: State getters read the matching Bot API value in the Robocode frame
    Test-type: Unit
    Given a Bot API state
    When a robot reads its own position, energy, headings, or remaining amounts
    Then each value comes from the corresponding Bot API value, converted into Robocode's frame
    And no getter returns a neighbouring component's value

  @ROUTE-005
  Scenario: Stopping, resuming, and the independence flags route individually
    Test-type: Unit
    Given a robot that stops, resumes, or sets one independence flag
    When the call is routed
    Then it reaches that call alone, carrying its argument rather than an assumed value
    And an unsupported variant is refused rather than silently downgraded

  @ROUTE-006
  Scenario: A queued command is queued, not executed
    Test-type: Unit
    Given a robot that queues a movement or turn
    When the call is routed
    Then the Bot API's queued setter receives it
    And the turn is not completed, and the blocking form is not used
    And a non-numeric amount becomes zero, as classic treats it

  @ROUTE-007
  Scenario: Conditions, priorities and interruptibility route by identity
    Test-type: Unit
    Given a robot that waits on, adds, or removes a condition, or changes an event priority
    When the call is routed
    Then the Bot API receives the same condition instance that was registered
    And an event priority is addressed by its mapped Tank Royale event class
    And an event type with no counterpart is ignored rather than mapped to a neighbour

  @ROUTE-008
  Scenario: Event lists, round bookkeeping and data access answer correctly
    Test-type: Unit
    Given a robot reading its events, round numbers, or data locations
    When each is routed
    Then every per-type event list answers without consuming the queue
    And round numbering is rebased to classic's while the round total is not
    And a data file resolves inside the directory the robot was given

  @ROUTE-009
  Scenario: Team messaging routes by translated identity
    Test-type: Unit
    Given a robot addressing its team
    When a broadcast or directed message is routed
    Then a broadcast reaches every teammate and a directed message only its addressee
    And a name with no Tank Royale identity is refused rather than silently dropped

  @ROUTE-010
  Scenario: The junior combined move converts only its angle
    Test-type: Unit
    Given a junior robot turning and moving in one call
    When the call is routed
    Then the angle is converted to degrees and the distance is passed through unchanged

  @ROUTE-011
  Scenario: Every method on the peer surface is tested or exempt for a stated reason
    Test-type: Unit
    Given the peer interfaces a robot is handed, enumerated reflectively
    When the routing tests are checked against them
    Then every method is exercised by a test, or carries an exemption naming why it routes nothing
    And an exemption for a method that no longer exists is itself a failure

  @ROUTE-012
  Scenario: The peer and the status report the same state
    Test-type: Unit
    Given one Bot API state
    When a robot reads it through the peer and through the status it is handed
    Then both report the same value for every field, including sign
```
