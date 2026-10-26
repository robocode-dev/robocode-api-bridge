---
id: AN-714
type: analysis
status: active
links: [CAP-009, CAP-006, PKG-004, TEAM-001, P-002]
title: Preview team launches expose descriptions used as identities and intermittent zero-valued results
provenance: inferred
reversal-cost: low
---

# Preview team names and unresolved zero-valued results

## Observation

The preview package was exercised on Windows on 2026-10-25 with JDK 11 and the published Tank Royale 1.4.0 runner, whose SHA-256 was checked against the release's `SHA256SUMS`. Original SpinBot, SittingDuck and MyFirstTeam jars were copied from the read-only collection and imported with the packaged command. The solo battle completed with nonzero bullet damage. The team battle booted ten members across two complete five-member teams; the server logged every member joining and becoming ready, but the runner returned two team rows with zero scores and rank zero.

The wrapper had used `team.description` as the team JSON `name`. MyFirstTeam's description contains leading whitespace and several lines of explanatory text. This is a description, not the team entry's identity.

## Focused experiment and repair

Changing only the two generated team JSON `name` fields to simple entry names, with all source jars unchanged, coincided with ranked team results and nonzero scores against the same published runtime. A subsequent run from the repaired package also returned zero-valued rows despite all ten members joining and becoming ready. The name experiment therefore does not establish a cause or repair for the score behavior. [The raw worker results and input hashes](AN-714-team-name-observations.json) retain both outcomes. Scores are single nondeterministic observations, not an averaged regression verdict.

The generator now uses the team entry's basename for `name` and preserves the explanatory text separately as `description`. A filesystem integration regression, `PreviewImporterTest.testPKG004_IntegrationPositive_SeparatesTeamIdentityFromItsMultilineDescription`, checks that separation. This corrects the generated identity and display metadata without claiming to repair the unresolved scores.

## Remaining information boundary

The published 1.4.0 runner converts the observer's result rows directly; its `ServerConnection.toBattleResults` does not invent these zero ranks. All ten team members joined and became ready, with no captured startup exceptions. The source robot jars remain unchanged. No evidence here attributes the intermittent result to a robot, bridge or engine cause. A diagnostic run with a tick subscription also completed with ranked nonzero results, so passing a single run cannot discharge the failing observations.

The next useful experiment captures raw game-ended observer results and per-bot round-ended results from the same game, preserving team IDs, to locate where nonzero scores or ranks disappear. This remains an open compatibility case and a condition to assess under the preview-readiness milestone. Package assembly and user documentation can be delivered without claiming that readiness or comprehensive parity.
