---
id: AN-437
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-148, AN-277]
title: Spark's large positive score gap persists without the earlier skipped-turn burst
provenance: inferred
reversal-cost: low
---

# AN-437 — Spark's large positive score gap persists without the earlier skipped-turn burst

## Risk investigated

Whether `cx.micro.Spark_0.6.jar`'s large positive Tank Royale score gap persists under the latest matched artifacts, and whether the earlier skipped-turn burst recurs.

## Evidence boundary

The read-only subject jar has SHA-256 `dfcbbb2bb08ae6028e68178c3d9f80c0b1d0058ced7ab90bab2cade92f615d18`. The official five-pair confirmation `9165f23f42418645` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `70f381da5890c929600cc50696b66d5a4a0d590b`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 4,653.0 points and Tank Royale averaged 12,748.2 points, for a +174.6% mean delta. The five pair deltas were +154.2%, +200.7%, +173.5%, +154.9%, and +189.7%. The registry status remains `CONFIRMED (score)`; excluding the largest pair, the other four average +168.08%.

Neither engine reported errors and no bridge-only signatures were recorded. Skipped-turn telemetry was captured with zero events in all five attempts. AN-277's preceding confirmation had a +164.68% mean delta; its first pair recorded a 112-event skipped-turn burst, while the remaining four pairs were empty. The current score advantage persists at a somewhat larger mean, and the earlier burst did not recur.

## What was not pursued

The score difference and prior skipped-turn burst were not attributed to the bridge, Tank Royale, or Spark from aggregate evidence alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Spark's higher Tank Royale score remains confirmed at +174.6%, up from +164.68% in AN-277. The previous 112-event skipped-turn burst did not recur; all five current telemetry captures were empty. The cause of the score difference remains open.

## M-006 handoff

Continue in registry order with `roborumble/cx.mini.Nimrod_0.55.jar` (`CONFIRMED (score)`).