---
id: AN-444
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-157, AN-287]
title: DuelistMicroMkII's positive score gap persists across five current pairs
provenance: inferred
reversal-cost: low
---

# AN-444 — DuelistMicroMkII's positive score gap persists across five current pairs

## Risk investigated

Whether `davidalves.net.DuelistMicroMkII_1.1.jar`'s large positive Tank Royale score gap persists under the latest matched artifacts, and whether its magnitude materially changes.

## Evidence boundary

The read-only subject jar has SHA-256 `0d3e784acde7d8156090280241668a002f890ee6d5d7fe6dadc971787f67b15e`. The official five-pair confirmation `938fa352a900198e` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `78039932ddf7bbb1474c68bd4ffe2df72514d329`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,305.0 points and Tank Royale averaged 11,405.6 points, for a +115.56% mean delta. The five pair deltas were +127.4%, +95.5%, +139.5%, +106.9%, and +108.5%. The registry status remains `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-287's preceding five-pair confirmation on 2026-10-08 had a +120.98% mean delta, with every pair positive and no errors or skipped-turn events. The current result reproduces the large Tank Royale advantage at a somewhat smaller mean magnitude.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or DuelistMicroMkII from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

DuelistMicroMkII's higher Tank Royale score remains confirmed at +115.56%, compared with +120.98% in AN-287. All five pairs favored Tank Royale; no errors or skipped-turn events occurred. The retest did not identify the score-gap cause.

## M-006 handoff

Continue in registry order with `roborumble/davidalves.net.DuelistMicro_1.22.jar` (`CONFIRMED (score)`).