---
id: DES-009
type: design
status: active
links: [CAP-009, CRIT-009, ARCH-001, IDR-010]
title: Preview packaging and source-to-bot setup
provenance: inferred
reversal-cost: low
---

# Preview packaging and source-to-bot setup

The package adds a Java entry point beside the existing developer wrapper. Source discovery reads conventional Classic locations and Java-properties rumble settings without starting Classic or importing its libraries. Explicit sources and remembered choices take precedence over discovery. An interactive console resolves missing or ambiguous candidates; unattended calls must supply an unambiguous source.

Each unique input jar is copied into a private staging directory and wrapped there using the existing generator. Strict validation checks robot classes, team completeness and filesystem-safe metadata. Only a complete, nonconflicting generation is merged into the output. SHA-256 input identities and a versioned state file track generated runtime files; learned data is seeded only when absent. Ownership is checked before replacement or removal, and symlink destinations are refused. A failed generation leaves the installed generation intact. Filesystem or process failure during a merge is reported; a subsequent import repairs owned runtime files while preserving learned data.

The output is flat because Tank Royale lists direct children of configured bot roots. Distinct input jars declaring the same generated identity are conflicts; byte-identical copies across rumble divisions are deduplicated. Within one archive tree, duplicate robot classes are rejected, including different versions, because the team's class-to-member mapping would otherwise be ambiguous. The command prints the one output root to configure in Tank Royale.

Archive assembly uses resolved published dependency jars and a checked source revision. It includes separate adapter and Bot API runtime jars, the existing self-contained wrapper, source and dependency provenance, license material and a copy of the canonical guide. Dependency and archive checksums identify exact bytes; deterministic ordering and timestamps avoid incidental ZIP differences. Build tasks reject local Bot API overrides and dirty tracked inputs for candidate creation. Public release publication is a separate human-authorized action.

## Observed package checks

On Windows on 2026-10-25, package integration tests exercised JDK 11 and JDK 25, quoted paths, Java source launching, missing libraries and Java, unchanged source inputs, duplicate inputs, repeated team members, learned data preservation and redirected output rejection. Original SpinBot and SittingDuck jars completed a focused battle through the published Tank Royale 1.4.0 runtime. Complete MyFirstTeam entries booted all ten member processes across two teams. [The team result investigation](../../analysis/AN-714-preview-team-result-observations.md) retains intermittent zero-valued results as unresolved; these checks do not close the clean-environment readiness gate or establish behavioral parity.
