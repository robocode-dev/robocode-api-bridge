---
id: CRIT-005
type: criteria
status: draft
links: [CAP-005]
title: Score parity across the rumble collections — acceptance criteria
ac-prefix: SCORE
provenance: inferred
reversal-cost: low
---

# CAP-005 — acceptance criteria

`SCORE-001` is `Test-type: Human` and is proven by a maintainer naming the sweep report in the acceptance brief. The rest are `@draft`.

The distinction is deliberate and worth stating: `SCORE-001` is a judgment about a population that no threshold can make for us, while everything else here has a definite answer once the instrument exists.

```gherkin
Feature: Score parity across the rumble collections

  @SCORE-001
  Scenario: The collection's scores are comparable between the engines
    Test-type: Human
    Given a sweep run at each division's official parameters
    When a maintainer reads the resulting report
    Then the population's parity is judged acceptable, or the outliers are named and carried into the plan
    # Human because the verdict is interpretation over a statistical instrument: which
    # divergences matter, and which are the collection being the collection. Not a
    # placeholder for a test — no threshold decides this one.

  @SCORE-002 @draft
  Scenario: A watched bot has not moved from its baseline
    Test-type: Integration
    Given the regression watch list with a recorded baseline per bot
    When each watched bot is measured over the required repeats at its division's parameters
    Then no bot's averaged delta has moved beyond the band
    And a bot marked as noise is reported without failing the run
    # The gate C-004 defines. The watch list does not exist yet. Plan door: M-001.

  @SCORE-003 @draft
  Scenario: A bot that throws only under the bridge stops its battle
    Test-type: Integration
    Given a bot whose classic run produced a known set of exception signatures
    When the Tank Royale run produces a signature the classic run did not
    Then the battle is stopped at that point and the signature is recorded
    And no score is reported for that pairing
    # Decided against the classic baseline, so this is a verdict rather than a judgment.
    # Draft only because the harness is Python — see AN-003. Plan door: M-001.

  @SCORE-004 @draft
  Scenario: Bots that scored lower under the bridge are within the band or explained
    Test-type: Integration
    Given the bots the report flags as scoring materially lower
    When each is re-measured under the current bridge at official parameters
    Then each is within the band, or has a named cause recorded in analysis
    # Their present numbers predate the redesign and the upgrade. Plan door: M-002.

  @SCORE-005 @draft
  Scenario: Bots that scored higher under the bridge are within the band or explained
    Test-type: Integration
    Given the bots the report flags as scoring materially higher
    When each is re-measured under the current bridge at official parameters
    Then each is within the band, or has a named cause recorded in analysis
    # Scoring better under the bridge is as much a fidelity defect as scoring worse,
    # and is easier to leave unexamined. Plan door: M-003.

  @SCORE-006 @draft
  Scenario: The melee division is measured at all
    Test-type: Integration
    Given the melee collection and the official melee parameters
    When a sweep runs on both engines
    Then a report exists for the division
    # The division has never been run. Its parity is unmeasured rather than unmet,
    # and it is the division most likely to expose event-delivery defects. Plan door: M-006.
```
