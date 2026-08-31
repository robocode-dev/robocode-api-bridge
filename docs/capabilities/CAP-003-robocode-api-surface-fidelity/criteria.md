---
id: CRIT-003
type: criteria
status: active
links: [CAP-003]
title: Robocode API surface fidelity — acceptance criteria
ac-prefix: API
provenance: inferred
reversal-cost: low
---

# CAP-003 — acceptance criteria

These are the corpus's only machine-proven criteria. Each is proven by unit tests carrying its identity, proof type, and direction in the test method name, running with no engine present.

```gherkin
Feature: Robocode API surface fidelity

  @API-001
  Scenario: Angles convert from the Tank Royale convention to the Robocode convention (single-direction)
    Test-type: Unit
    Given an angle expressed in Tank Royale's convention
    When it is converted to Robocode's convention
    Then the result is the same direction expressed in Robocode's convention
    And the discontinuity where the circle wraps is handled rather than producing a reflected angle
    # The adapter converts only this direction: outbound turn commands are relative rotations
    # in the same winding sense on both engines and need no conversion, so nothing in the
    # bridge ever converts a Robocode-convention angle back to Tank Royale's. AN-010 found the
    # round-trip clause this scenario once had was untestable because no such method exists,
    # and the asymmetry is architectural rather than a gap.

  @API-002
  Scenario: Colours convert between Robocode's Color and Tank Royale's representation
    Test-type: Unit
    Given a robot colour
    When it is converted for Tank Royale and read back
    Then the colour is preserved
    And an absent colour stays absent rather than becoming a default

  @API-003
  Scenario: Battle results map to Robocode's BattleResults with classic field semantics
    Test-type: Unit
    Given a Tank Royale battle result
    When it is mapped for a robot
    Then each score component lands in the field a classic robot reads it from
    And a participant with no score maps to zeroes rather than to absent fields

  @API-004
  Scenario: Bullets map to Robocode's Bullet with owner, power, and heading preserved
    Test-type: Unit
    Given a Tank Royale bullet whose owner id does not match any bot currently in the battle
    When it is mapped for a robot
    Then its power, heading, and owning robot are preserved
    And a bullet whose owner is no longer in the battle still maps rather than failing

  @API-005
  Scenario: Tank Royale bot state maps to the Robocode robot status fields
    Test-type: Unit
    Given a Tank Royale bot state for a turn
    When it is mapped to the status a robot reads
    Then energy, position, headings, velocity, and gun heat carry across
    And a state field the classic API has no equivalent for is dropped rather than approximated

  @API-006
  Scenario: Each Tank Royale event maps to its Robocode event class
    Test-type: Unit
    Given a Tank Royale event
    When it is mapped for a robot
    Then it becomes the Robocode event class a classic robot would have received
    And an event with no Robocode counterpart does not become an unrelated class
```
