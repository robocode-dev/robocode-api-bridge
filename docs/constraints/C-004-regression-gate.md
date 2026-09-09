---
id: C-004
type: constraint
status: active
links: [CAP-005, CAP-007]
title: A regression verdict averages repeats, and an asymmetric error ends score collection
source: The OQ-001 Q2 resolution, recorded in PDR-001
enforcement: agent
provenance: inferred
reversal-cost: high
---

# C-004 — A regression verdict averages repeats, and an asymmetric error ends score collection

Two rules, because a divergence between the engines arrives in two shapes and only one of them is a quantity.

## Scores are averaged before they are judged

A watched bot is measured over five repeats per side at its division's official parameters, and the averaged deltas are compared. A bot regresses when its averaged delta has moved more than fifteen percentage points from its recorded baseline.

A single battle is not evidence about a bot. One watched bot has swung by a factor of forty between runs on the classic engine alone, with no bridge involved — a percentage delta computed against a base that small is arithmetic rather than measurement. Averaging is what makes the comparison mean anything, and the threshold is stated as movement from a baseline rather than as an absolute delta because a bot that has always differed by twenty points and still differs by twenty points has not regressed.

Bots whose scores are meaningless at any repeat count are marked as such on the watch list. They are reported and never fail the run, because a gate that cries wolf on the same bot every time trains people to ignore it.

## An error that classic did not produce ends Tank Royale score collection immediately

Classic Robocode is the reference. When the Tank Royale side throws an exception whose normalized signature classic did not produce, the battle is stopped and the signature is recorded. A normalized signature contains the exception class and the first legacy callback or application frame; engine frames, paths, and line numbers are not part of it. The stopped battle is not scored or averaged.

The two rules are opposites on purpose. A score difference is a quantity that noise can explain and repetition can resolve. The same bot throwing only under the bridge is a categorical fact about the bridge that no amount of repetition improves, and finishing the battle to produce a score for it spends minutes to learn nothing further. The classic side runs first, so its signatures are already the baseline the Tank Royale side is compared against.

An error or completion mismatch in either direction is an unresolved parity case. The Tank Royale-only case can stop immediately because classic has already run; a classic-only mismatch is recorded after Tank Royale completes. This keeps the comparison objective: the registry compares observable outcomes, not a reviewer's sense of which exception looks normal.

## Residual

Judgment holds two things a machine cannot. Whether a bot belongs on the watch list at all is a decision about what is worth watching. Whether a bot marked as noise is genuinely noise, rather than a defect hiding behind a small score, is a judgment that a gate exempting it can never make for itself.

**Promotion trigger:** the harness implementing both rules, at which point machine enforcement holds the verdict and the residual shrinks to watch-list membership. The harness is Python, which is not a supported evidence carrier, so `AN-003` records why enforcement stays with the agent longer here than the implementation alone would suggest.
