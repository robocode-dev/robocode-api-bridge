---
id: CRIT-004
type: criteria
status: active
links: [CAP-004]
title: Robot file I/O sandboxing — acceptance criteria
ac-prefix: FIO
provenance: inferred
reversal-cost: high
---

# CAP-004 — acceptance criteria

`FIO-001`–`FIO-003` are proven by `M-004`'s resolver work in `RobotData`. `FIO-004` stays `@draft`: `IDR-007` records why classic's own evidence for it depends on a `SecurityManager` this bridge does not have.

```gherkin
Feature: Robot file I/O sandboxing

  @FIO-001
  Scenario: A root-relative path a robot writes to is redirected into its data directory
    Test-type: Integration
    Given a robot that opens a file at a root-relative path outside its data directory
    When it writes and the battle completes
    Then the write succeeded
    And the file exists inside the robot's data directory
    And nothing was written at the path the robot named
    # Classic redirects silently; the robot never learns the difference. A true drive-letter-
    # absolute name (Windows) is a narrower case neither engine redirects; both fail the write
    # instead (CAP-004/design.md). Evidence: FileRedirectionConformanceTest (Integration),
    # RobotDataResolveTest (Unit, resolver-level: strip/traversal-check ordering).

  @FIO-002
  Scenario: The data file and the data directory resolve against the same place
    Test-type: Integration
    Given a robot that asks for its data directory and separately asks for a data file by name
    When it writes through the file and lists the directory
    Then the file it wrote appears in the directory it was given
    # These two calls had disagreed (IDR-002); confinement now makes the agreement checkable.
    # Evidence: FileRedirectionConformanceTest.

  @FIO-003
  Scenario: A robot's data directory is capped at the classic size limit
    Test-type: Integration
    Given a robot that writes past the documented data directory size limit
    When the battle runs
    Then the write is refused at the same point classic refuses it
    # Evidence: FileQuotaConformanceTest, porting classic's FileWriteSize probe.

  @FIO-004 @draft
  Scenario: A robot cannot read or write outside its data directory
    Test-type: Integration
    Given a robot that attempts to reach a file belonging to another robot or to the system
    When the battle runs
    Then the attempt is confined exactly as classic confines it
    # Classic's own evidence (FileAttack, FileOutputStreamAttack) blocks both robots via a
    # java.security.SecurityManager, unconditionally on path, that JDK 24 removed and this
    # bridge cannot reproduce. See IDR-007.
```
