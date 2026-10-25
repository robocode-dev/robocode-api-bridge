---
id: AN-714
type: analysis
status: active
links: [CAP-009, CAP-006, PKG-004, TEAM-001, P-002]
title: A multiline team description produced zero-valued runner team results
provenance: inferred
reversal-cost: low
---

# A multiline team description produced zero-valued runner team results

## Observation

The preview package was exercised on Windows on 2026-10-25 with JDK 11 and the published Tank Royale 1.4.0 runner, whose SHA-256 was checked against the release's `SHA256SUMS`. Original SpinBot, SittingDuck and MyFirstTeam jars were copied from the read-only collection and imported with the packaged command. The solo battle completed with nonzero bullet damage. The team battle booted ten members across two complete five-member teams; the server logged every member joining and becoming ready, but the runner returned two team rows with zero scores and rank zero.

The wrapper had used `team.description` as the team JSON `name`. MyFirstTeam's description contains leading whitespace and several lines of explanatory text. This is a description, not the team entry's identity.

## Focused experiment and repair

Changing only the two generated team JSON `name` fields to simple entry names, with all source jars unchanged, produced ranked team results and nonzero scores against the same published runtime. This isolates the generated name representation as sufficient to recover the results; it does not establish an internal engine cause or behavioral parity. [The raw before/after worker results and input hashes](AN-714-team-name-observations.json) retain the original observations. Scores are single nondeterministic observations, not an averaged regression verdict.

The generator now uses the team entry's basename for `name` and preserves the explanatory text separately as `description`. A filesystem integration regression, `PreviewImporterTest.testPKG004_IntegrationPositive_SeparatesTeamIdentityFromItsMultilineDescription`, checks that separation. The package's full team launch is rerun after the repair rather than treating a completed zero-valued result as sufficient evidence.
