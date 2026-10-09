---
id: AN-443
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-154, AN-286]
title: Cinnamon's lower Tank Royale score persists in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-443 — Cinnamon's lower Tank Royale score persists in five current pairs

## Risk investigated

Whether `dans.Cinnamon_1.2.jar`'s negative score gap persists under the latest matched artifacts, and whether its magnitude changes materially.

## Evidence boundary

The read-only subject jar has SHA-256 `cb352fa2412994d4160c5161124036464a48cc73b1725178a7ee048609c8b7da`. The official five-pair confirmation `eb1f5a99b8ce416a` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `78039932ddf7bbb1474c68bd4ffe2df72514d329`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 8,466.0 points and Tank Royale averaged 4,320.2 points, for a −48.8% mean delta. The five pair deltas were −38.2%, −52.0%, −54.2%, −47.2%, and −52.4%. The registry status remains `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-286's preceding five-pair confirmation on 2026-10-08 had a −48.0% mean delta, with all five pairs negative and no errors or skipped-turn events. The current result reproduces that score gap at a very similar magnitude.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or Cinnamon from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Cinnamon's lower Tank Royale score remains confirmed at −48.8%, close to AN-286's −48.0%. All five pairs favored Classic, with no errors or skipped-turn events. The retest did not identify the score-gap cause.

## M-006 handoff

Skip `roborumble/davidalves.Firebird_0.25.jar` (`MATCHED (score noise)`) and the intervening `PASS` subjects. Continue with `roborumble/davidalves.net.DuelistMicroMkII_1.1.jar` (`CONFIRMED (score)`).