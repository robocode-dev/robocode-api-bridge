---
id: AN-399
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: RougeDC's historical higher score does not reproduce beyond the current band
provenance: inferred
reversal-cost: low
---

# AN-399 — RougeDC's historical higher score does not reproduce beyond the current band

## Risk investigated

Whether `ags.rougedc.RougeDC_willow.jar`'s historical higher Tank Royale score persists under the latest matched artifacts, and whether the retest identifies why the earlier score gap changed.

## Evidence boundary

The read-only subject jar has SHA-256 `2aee27ddd085c75f61943240e090e018c9e69f658cd4afed508430910a41d2ab`. The official five-pair confirmation `0ed9111e59d00bde` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ff03c80914aa57ad48b08ed5ca6df4949db1ef6a`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,658.0 points and Tank Royale averaged 5,658.2 points, for a +0.14% mean delta. The five pair deltas were −6.8%, −2.6%, +8.9%, +0.2%, and +1.0%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status is `MATCHED (score noise)`.

The preceding observation `238cfb36d8d14310` on 2026-09-28 had a +43.2% delta without recorded errors. The first historical observation on 2026-09-08 was `PASS` at −5.2%, followed by three single-pair score discrepancies at +41.2%, +37.9%, and +43.2%. The current five-pair mean is within the confirmation band; the measurements do not identify why the earlier score differences varied.

## What was not pursued

The current within-band score was not treated as exact parity or proof of a specific repair. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

RougeDC's earlier higher Tank Royale score was not reproduced beyond the five-pair mean band under the newer matched artifacts: the current mean delta is +0.14%, compared with +43.2% in the preceding observation. The current run recorded no errors or skipped-turn events; the reason for the historical variation remains unknown.

## M-006 handoff

Continue in registry order with `roborumble/ahf.NanoAndrew_.4.jar` (`DISCREPANCY (score)`).
