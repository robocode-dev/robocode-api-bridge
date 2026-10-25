---
id: CRIT-009
type: criteria
status: active
links: [CAP-009]
title: Installable preview acceptance criteria
ac-prefix: PKG
provenance: inferred
reversal-cost: low
---

# Installable preview acceptance criteria

```gherkin
Feature: Install and prepare existing robots from a complete bridge archive

  @PKG-001
  Scenario: Assemble a complete revision-pinned preview
    Test-type: Integration
    Given published runtime dependencies and a clean revision-pinned source tree
    When the preview archive is built
    Then it includes launchers, wrapper, adapter, Bot API, guide and dependency notices
    And its manifest and SHA-256 checksums identify its revision and every payload
    And local dependency overrides or dirty tracked input are rejected

  @PKG-002
  Scenario: Resolve an existing robot source without guessing
    Test-type: Unit
    Given an explicit source, remembered source or conventional Classic installation
    When the source is resolved
    Then Classic BOTSREP paths resolve relative to the installation
    And equivalent paths are deduplicated
    And ambiguous or missing sources require a choice rather than an arbitrary selection

  @PKG-003
  Scenario: Stage unchanged jars and preserve learned data
    Test-type: Integration
    Given an existing robot collection and a separate managed output root
    When robots are imported or reimported with a newer package
    Then source files remain unchanged and duplicate jar content is imported once
    And generated bots are direct children of the output root with packaged libraries installed
    And existing learned data and unowned files are preserved
    And unsafe overlapping roots or symbolic-link destinations are rejected

  @PKG-004
  Scenario: Report unusable robots and teams as failures
    Test-type: Integration
    Given robot jars that may contain conflicting identities or incomplete teams
    When the packaged command wraps them
    Then successful imports and failures are reported separately with a nonzero result for failures
    And conflicting or incomplete results do not replace an installed robot

  @PKG-005
  Scenario: Use the packaged command independently of the development checkout
    Test-type: Integration
    Given an unpacked preview and a JDK with no Classic installation or development classpath
    When its documented command is used from a path containing spaces
    Then help and version describe the package and wrapping produces launchable Java-source bots
    And invalid arguments or unavailable libraries give actionable errors and nonzero results
```
