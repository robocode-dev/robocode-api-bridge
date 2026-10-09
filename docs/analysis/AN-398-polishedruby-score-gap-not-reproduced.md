---
id: AN-398
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: PolishedRuby's historical lower score does not reproduce beyond the current band
provenance: inferred
reversal-cost: low
---

# AN-398 — PolishedRuby's historical lower score does not reproduce beyond the current band

## Risk investigated

Whether `ags.polished.PolishedRuby_1.jar`'s historical lower Tank Royale score persists under the latest matched artifacts, and whether the retest identifies a cause for the change.

## Evidence boundary

The read-only subject jar has SHA-256 `66674145d1a973e3d1b5079f97a991485a890c843f47ee5c0caff9fb9b0fbfcf`. The official five-pair confirmation `2616514817a66037` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ff03c80914aa57ad48b08ed5ca6df4949db1ef6a`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 11,792.2 points and Tank Royale averaged 12,689.0 points, for a +8.0% mean delta. The five pair deltas were +14.8%, +7.0%, +15.5%, +6.1%, and −3.4%. The mean is inside the 15.0-point confirmation band, although one pair was +15.5%; the registry status is `MATCHED (score noise)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

The preceding observation `3e2eef0ec2101e8b` on 2026-09-28 had a −81.3% delta without recorded errors; the three older observations were −80.9%, −80.7%, and −81.6%. The current score difference is dramatically smaller and within the five-pair mean band. The measurement does not identify why the score changed.

## What was not pursued

The within-band result was not treated as exact parity or as proof of a specific repair. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

PolishedRuby's historical lower Tank Royale score was not reproduced beyond the five-pair mean band under the newer matched artifacts: the current mean delta is +8.0%, compared with −81.3% in the preceding observation. The report records no runtime errors or skipped-turn events; the cause of the score change remains unknown.

## M-006 handoff

Continue in registry order with `roborumble/ags.rougedc.RougeDC_willow.jar` (`DISCREPANCY (score)`).
