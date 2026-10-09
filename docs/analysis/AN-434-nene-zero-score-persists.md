---
id: AN-434
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-142, AN-273]
title: Nene's Tank Royale zero score persists across five current pairs
provenance: inferred
reversal-cost: low
---

# AN-434 — Nene's Tank Royale zero score persists across five current pairs

## Risk investigated

Whether `cs.Nene_1.0.5.jar`'s previously confirmed Tank Royale zero-score result persists across five pairs under the latest matched artifacts, and whether the retest identifies its cause.

## Evidence boundary

The read-only subject jar has SHA-256 `9615be8f8f2bdf4f42a38f74b4b81d0944e86f06311582915a78af06d529e856`. The official five-pair confirmation `dd56443772ac0c6e` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1505150c660236e2d868aa090c7c6746c815f37c`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 4,751.8 points and Tank Royale scored 0 in every attempt, for a −100.0% mean delta. All five pair deltas were −100.0%. The registry status remains `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-273's preceding five-pair confirmation on 2026-10-08 had a −100.0% mean delta, with Classic averaging 4,721.2 and Tank Royale scoring 0 in every pair. The current result reproduces the same all-five zero-score discrepancy under newer matched artifacts. AN-142's bundled-source leads remain unconfirmed explanations; this retest does not locate the cause.

## What was not pursued

The score difference was not attributed to the bridge, Tank Royale, or Nene from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Nene's Tank Royale zero-score discrepancy remains confirmed across all five current pairs. Classic averaged 4,751.8, and no runtime errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Skip `roborumble/csp.Eagle_3.30.jar` (`MATCHED (score noise)`), `roborumble/cw.megas.Blade_0.8.jar` (`PASS`), and the intervening `PASS` subjects. Continue with `roborumble/cw.megas.GhostShell_GT.jar` (`DISCREPANCY (errors)`).