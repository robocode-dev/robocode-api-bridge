---
id: CRIT-001
type: criteria
status: draft
links: [CAP-001]
title: Event dispatch and timing parity — acceptance criteria
ac-prefix: EVT
provenance: inferred
reversal-cost: low
---

# CAP-001 — acceptance criteria

Most criteria here are `@draft` against `M-001`. The behaviour is implemented; the evidence mostly is not. Each names the classic test robot that will prove it, because classic's own conformance suite already encodes these expectations and the conformance tier restates them against both engines.

```gherkin
Feature: Event dispatch and timing parity

  @EVT-001 @draft
  Scenario: Events dispatch in classic priority order
    Test-type: Integration
    Given a robot that records the order in which its handlers are entered
    When the same battle runs on classic Robocode and on Tank Royale through the bridge
    Then the recorded order is the same on both engines
    # Proven by the ported EventPriorityFilter robot. Plan door: M-001.

  @EVT-002 @draft
  Scenario: A blocking call inside a handler does not discard pending same-priority events
    Test-type: Integration
    Given a robot that calls a blocking method from within onScannedRobot
    When the same battle runs on both engines
    Then every scan event the classic run delivered is also delivered under the bridge
    # The defect this criterion exists for discarded exactly these events. Plan door: M-001.

  @EVT-003
  Scenario: An interruptible handler is re-entered when a higher-priority event arrives
    Test-type: Integration
    Given a robot that turns its radar from inside onHitWall and marks when it is scanned
    When the same battle runs on both engines
    Then the robot reports being scanned on both engines
    # Proven by the ported InteruptibleEvent robot, in InterruptibleEventConformanceTest.

  @EVT-004 @draft
  Scenario: A robot's own death reaches its death handler
    Test-type: Integration
    Given a robot that reports from onDeath
    When the robot is destroyed on each engine
    Then the report appears on both engines
    # Draft because the behaviour is missing, not because nothing tests it: the test exists and
    # is disabled. AN-006 establishes the cause -- the Tank Royale server emits a death before the
    # turn's bot snapshot exists, so it reaches no bot at all -- and the repair is committed
    # upstream and unreleased. Promote when a release carries it. Plan door: M-001.

  @EVT-005 @draft
  Scenario: New-turn events arrive at the classic point in the turn
    Test-type: Integration
    Given a robot that records the turn number at each handler entry
    When the same battle runs on both engines
    Then each handler is entered at the same point in the turn on both engines
    # Draft, and not proven by the tests currently tagged EVT-005: those assert round and battle
    # completion, which is different behaviour and which no criterion here covers. See G-002.
    # Plan door: M-001.

  @EVT-006 @draft
  Scenario: Custom events fire and can be removed
    Test-type: Integration
    Given a robot that registers a custom condition, reports when it fires, then removes it
    When the same battle runs on both engines
    Then the firing and the silence after removal match on both engines
    # Proven by the ported CustomEvents robot. Plan door: M-001.

  @EVT-007 @draft
  Scenario: The death of another robot reaches the survivors
    Test-type: Integration
    Given a robot that reports each robot death it observes
    When the same battle runs on both engines
    Then the same deaths are reported in the same order on both engines
    # Proven by the ported RobotDeathEvents robot. Blocked by the same cause as EVT-004: no death
    # event reaches any bot, so the survivors are not told either. See AN-006. Plan door: M-001.

  @EVT-008 @draft
  Scenario: Skipped turns are reported to the robot
    Test-type: Integration
    Given a robot that deliberately overruns its turn and reports each skipped turn
    When the same battle runs on both engines
    Then skipped turns are reported on both engines
    # Proven by the ported SkipTurns robot. Plan door: M-001.

  @EVT-009 @draft
  Scenario: An exception thrown out of a handler is handled as classic handles it
    Test-type: Integration
    Given a robot that throws from inside an event handler
    When the same battle runs on both engines
    Then the robot survives or dies identically, and the exception is reported on both engines
    # Proven by the ported Throwing robot. Plan door: M-001.

  @EVT-010 @draft
  Scenario: A melee turn delivers every scan event it carries
    Test-type: Integration
    Given a robot that counts the scan events it receives per turn
    And a melee battle at the official melee parameters
    When the same battle runs on both engines
    Then the per-turn scan counts match on both engines
    # The division the harness has never run, and the one that carries the most
    # same-priority events per turn. Plan door: M-001.
```
