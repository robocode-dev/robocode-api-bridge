---
id: CRIT-002
type: criteria
status: draft
links: [CAP-002]
title: Robot physics and state parity — acceptance criteria
ac-prefix: PHY
provenance: inferred
reversal-cost: low
---

# CAP-002 — acceptance criteria

Every criterion is `@draft` against `M-001`. Each names the classic test robot that encodes the same expectation; the conformance tier restates it against both engines rather than re-deriving the numbers.

The criteria are written as agreement between the engines rather than as literal rates. A rate written here would be a third copy of a number that already exists in two engines, and the one that drifts silently.

```gherkin
Feature: Robot physics and state parity

  @PHY-001 @draft
  Scenario: Gun heat and its cooling rate match classic
    Test-type: Integration
    Given a robot that reports its gun heat each turn after firing
    When the same battle runs on classic Robocode and on Tank Royale through the bridge
    Then the reported heat sequences agree
    # Proven by the ported GunHeat robot. Plan door: M-001.

  @PHY-002 @draft
  Scenario: Velocity is capped and acceleration follows the classic curve
    Test-type: Integration
    Given a robot that reports its velocity each turn while accelerating from rest and then braking
    When the same battle runs on both engines
    Then the reported velocity sequences agree, including the deceleration corner cases
    # Proven by the ported MaxVelocity, Ahead, and DecelerationCaveat robots. Plan door: M-001.

  @PHY-003 @draft
  Scenario: Body turn rate falls as velocity rises, as it does in classic
    Test-type: Integration
    Given a robot that reports its heading each turn while turning at several velocities
    When the same battle runs on both engines
    Then the reported heading sequences agree
    # Proven by the ported BodyTurnRate and MaxTurnRate robots. Plan door: M-001.

  @PHY-004 @draft
  Scenario: Gun turn rate and gun-turn independence match classic
    Test-type: Integration
    Given a robot that turns its gun with the body-turn adjustment both set and unset
    When the same battle runs on both engines
    Then the reported gun headings agree in both settings
    # Proven by the ported GunTurnRate robot. Plan door: M-001.

  @PHY-005 @draft
  Scenario: Radar turn rate and radar-turn independence match classic
    Test-type: Integration
    Given a robot that turns its radar with the gun-turn adjustment both set and unset
    When the same battle runs on both engines
    Then the reported radar headings agree in both settings
    # Radar behaviour is where the earlier event-queue defect surfaced as broken locks,
    # so this criterion has a failure history and no test. Plan door: M-001.

  @PHY-006 @draft
  Scenario: Reversing direction follows the classic deceleration path
    Test-type: Integration
    Given a robot that reverses while at speed and reports its velocity each turn
    When the same battle runs on both engines
    Then the reported velocity sequences agree through the reversal
    # Proven by the ported ReverseDirection robot. Plan door: M-001.

  @PHY-007 @draft
  Scenario: Rate-controlled movement matches the classic rates
    Test-type: Integration
    Given a rate-controlling robot that sets velocity and turn rates directly
    When the same battle runs on both engines
    Then the resulting motion agrees
    # Proven by the ported RateControl robot. Plan door: M-001.

  @PHY-008 @draft
  Scenario: A fired bullet's readable state matches classic
    Test-type: Integration
    Given a robot that fires and then reports its bullet's power, heading, and position each turn
    When the same battle runs on both engines
    Then the reported bullet state agrees until the bullet is resolved
    # Proven by the ported WatchBullets robot. Plan door: M-001.
```
