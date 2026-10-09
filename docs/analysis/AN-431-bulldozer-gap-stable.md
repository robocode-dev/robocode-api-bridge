---
id: AN-431
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-138, AN-270]
title: Bulldozer's positive score gap remains stable under newer artifacts
provenance: inferred
reversal-cost: low
---

# AN-431 — Bulldozer's positive score gap remains stable under newer artifacts

## Risk investigated

Whether `conscience.Bulldozer_1.0a.jar`'s confirmed positive Tank Royale score gap persists under the latest matched artifacts, and whether its magnitude materially changes.

## Evidence boundary

The read-only subject jar has SHA-256 `f822506ece11632ca21436cfede847408f696140c474ae3b35e25187fbe27875`. The official five-pair confirmation `13a9433e843618a4` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1505150c660236e2d868aa090c7c6746c815f37c`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 10,992.8 points and Tank Royale averaged 16,895.2 points, for a +53.68% mean delta. The five pair deltas were +52.0%, +53.2%, +56.1%, +53.5%, and +53.6%. The registry status remains `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-270's preceding five-pair confirmation on 2026-10-08 had a +53.3% mean delta, also with all five pairs positive and closely grouped. The current result reproduces that score advantage at nearly the same magnitude under newer matched artifacts.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Bulldozer from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Bulldozer's higher Tank Royale score remains confirmed at +53.68%, almost unchanged from AN-270's +53.3%. All five pairs favored Tank Royale, with no errors or skipped-turn events. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/conscience.Idem_1.0a.jar` (`CONFIRMED (score)`).