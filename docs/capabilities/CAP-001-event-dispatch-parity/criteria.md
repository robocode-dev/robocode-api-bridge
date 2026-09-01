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

Most criteria here are `@draft` against `M-001`; `EVT-004`, `EVT-011`, `EVT-012`, `EVT-013`, `EVT-014`, and `EVT-015` are active. Each names the classic test robot or bridge-owned probe that will prove it, because classic's own conformance suite already encodes these expectations and the conformance tier restates them against both engines.

```gherkin
Feature: Event dispatch and timing parity

  @EVT-001 @retired
  Scenario: Events dispatch in classic priority order
    Test-type: Integration
    Given a robot that records the order in which its handlers are entered
    When the same battle runs on classic Robocode and on Tank Royale through the bridge
    Then the recorded order is the same on both engines
    # Retired: the named EventPriorityFilter robot does not record handler order. See IDR-005; successor EVT-015.

  @EVT-002 @draft
  Scenario: A blocking call inside a handler does not discard pending same-priority events
    Test-type: Integration
    Given a robot that calls a blocking method from within onScannedRobot
    When the same battle runs on both engines
    Then every scan event the classic run delivered is also delivered under the bridge
    # The defect this criterion exists for discarded exactly these events. Plan door: M-001.

  @EVT-003 @retired
  Scenario: An interruptible handler is re-entered when a higher-priority event arrives
    Test-type: Integration
    Given a robot that turns its radar from inside onHitWall and marks when it is scanned
    When the same battle runs on both engines
    Then the robot reports being scanned on both engines
    # Retired: no robot in the source tree exercises genuine higher-priority re-entry, and
    # classic's own InteruptibleEvent deliberately uses the SAME priority. See IDR-003.
    # Superseded by EVT-013.

  @EVT-004
  Scenario: A robot's own death reaches its death handler
    Test-type: Integration
    Given a robot that reports from onDeath
    When the robot is destroyed on each engine
    Then the report appears on both engines
    # Proven by the ported BattleWin robot in RoundOutcomeEventsConformanceTest against a locally
    # built matched Tank Royale Bot API and runner pair. AN-009 establishes the original server
    # cause; PDR-002 records why bridge evidence uses this local upstream build. Plan door: M-001.

  @EVT-005 @draft
  Scenario: New-turn events arrive at the classic point in the turn
    Test-type: Integration
    Given a robot that records the turn number at each handler entry
    When the same battle runs on both engines
    Then each handler is entered at the same point in the turn on both engines
    # Draft: no test proves per-turn handler timing yet. The two tests once tagged EVT-005
    # proved round/battle completion instead and are now EVT-011; see G-002. Plan door: M-001.

  @EVT-006 @draft
  Scenario: Custom events fire and can be removed
    Test-type: Integration
    Given a robot that registers a custom condition, reports when it fires, then removes it
    When the same battle runs on both engines
    Then the firing and the silence after removal match on both engines
    # Proven by the ported CustomEvents robot. Plan door: M-001.

  @EVT-007 @retired
  Scenario: The death of another robot reaches the survivors
    Test-type: Integration
    Given a robot that reports each robot death it observes
    When the same battle runs on both engines
    Then the same deaths are reported in the same order on both engines
    # Retired: Tank Royale has no deterministic seed, so cross-engine death ordering cannot be
    # compared honestly. Superseded by EVT-014; see IDR-004.

  @EVT-014
  Scenario: A survivor receives another robot's death event
    Test-type: Integration
    Given two robots that report from onRobotDeath
    When one robot is destroyed on each engine
    Then a surviving robot reports the other robot's death on both engines
    # Proven by the bridge-owned DeathEventProbe in RobotDeathEventsConformanceTest. Successor
    # to EVT-007; see IDR-004. (single-direction): a battle with a death necessarily has a
    # survivor, so the missing marker is the behavior this criterion detects.

  @EVT-015
  Scenario: A lower-priority scan is suppressed while a higher-priority wall handler is blocked
    Test-type: Integration
    Given a priority probe runs against sample.Target, moves to a wall, and turns its radar from onHitWall
    When the same battle runs on classic Robocode and on Tank Royale through the bridge
    Then neither engine's robot output contains the scan marker
    # Proven by EventPriorityConformanceTest with a bridge-owned probe and sample.Target fixture. The probe observes the same handler boundary as classic's EventPriorityFilter test without depending on an unseeded pre-handler scan. Successor to EVT-001; see IDR-005. Plan door: M-001.

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

  @EVT-011
  Scenario: Round and battle completion each reach their handler exactly once
    Test-type: Integration
    Given a robot that reports from onRoundEnded and onBattleEnded
    When the same battle runs on classic Robocode and on Tank Royale through the bridge
    Then both handlers are reported on both engines
    And a round ending is reported exactly once per round the robot saw end
    # Proven by the ported BattleWin robot in RoundOutcomeEventsConformanceTest. Named for what
    # those tests actually assert, after G-002 found them mistagged EVT-005. Plan door: M-001.

  @EVT-012
  Scenario: Winning a round reaches the win handler
    Test-type: Integration
    Given a robot that reports from onWin
    When the robot wins a round on each engine
    Then the report appears on both engines
    # Proven by the ported BattleWin robot in RoundOutcomeEventsConformanceTest. Named for what
    # that test actually asserts, after G-002 found it mistagged EVT-004. Plan door: M-001.

  @EVT-013
  Scenario: An interruptible handler is re-entered for a same-priority event once marked interruptible
    Test-type: Integration
    Given a robot that turns its radar from inside onHitWall, at the same event priority as a scan,
      and has called setInterruptible(true)
    When the same battle runs on both engines
    Then the robot reports being scanned on both engines
    # Proven by the ported InteruptibleEvent robot in InterruptibleEventConformanceTest. Successor
    # to the retired EVT-003; see IDR-003 for why the claim is scoped to same-priority re-entry.
```
