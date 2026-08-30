---
id: CRIT-004
type: criteria
status: draft
links: [CAP-004]
title: Robot file I/O sandboxing — acceptance criteria
ac-prefix: FIO
provenance: inferred
reversal-cost: high
---

# CAP-004 — acceptance criteria

Every criterion is `@draft` against `M-004`, and every one describes behaviour the bridge does not have. These are a specification, not a description.

```gherkin
Feature: Robot file I/O sandboxing

  @FIO-001 @draft
  Scenario: An absolute path a robot writes to is redirected into its data directory
    Test-type: Integration
    Given a robot that opens a file at an absolute path outside its data directory
    When it writes and the battle completes
    Then the write succeeded
    And the file exists inside the robot's data directory
    And nothing was written at the path the robot named
    # Classic redirects silently; the robot never learns the difference. Plan door: M-004.

  @FIO-002 @draft
  Scenario: The data file and the data directory resolve against the same place
    Test-type: Integration
    Given a robot that asks for its data directory and separately asks for a data file by name
    When it writes through the file and lists the directory
    Then the file it wrote appears in the directory it was given
    # These two calls have disagreed. The resolution was aligned but never re-tested
    # against the bot that surfaced it. Plan door: M-004.

  @FIO-003 @draft
  Scenario: A robot's data directory is capped at the classic size limit
    Test-type: Integration
    Given a robot that writes past the documented data directory size limit
    When the battle runs
    Then the write is refused at the same point classic refuses it
    # The documented cap is stated in the API and enforced nowhere. Plan door: M-004.

  @FIO-004 @draft
  Scenario: A robot cannot read or write outside its data directory
    Test-type: Integration
    Given a robot that attempts to reach a file belonging to another robot or to the system
    When the battle runs
    Then the attempt is confined exactly as classic confines it
    # Proven by the ported FileAttack and FileOutputStreamAttack robots, which exist
    # precisely to try to escape. Plan door: M-004.
```
