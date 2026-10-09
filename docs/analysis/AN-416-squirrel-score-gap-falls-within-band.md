---
id: AN-416
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-115, AN-254]
title: Squirrel's historical lower score falls within the current mean band
provenance: inferred
reversal-cost: low
---

# AN-416 — Squirrel's historical lower score falls within the current mean band

## Risk investigated

Whether `bayen.nut.Squirrel_1.621.jar`'s previously confirmed lower Tank Royale score persists beyond the current five-pair mean band, and whether the recent score variation identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `a5a1b82535752ee271c91cdec18f433e21dcfe1efc46ed4001bed116eeca1f76`. The official five-pair confirmation `3804988fab768e63` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `d64e76ce2a011143165c5f032d81e221f656b580`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 4,988.2 points and Tank Royale averaged 4,367.6 points, for a −12.44% mean delta. The five pair deltas were −10.2%, −12.1%, −10.4%, −11.8%, and −17.7%. One pair was below −15%, but the five-pair mean is within the confirmation band and the registry status is `MATCHED (score noise)`. Neither engine reported errors and no bridge-only signatures were recorded.

Skipped-turn telemetry was captured with empty event lists in attempts 1, 3, and 5; it was unavailable in attempts 2 and 4, so the run cannot establish zero skipped turns across all pairs. The preceding five-pair confirmation `913055fbe1b40f66` on 2026-10-07 had a −25.9% mean delta with substantial pair variation; AN-115's 2026-10-06 confirmation had −27.48%. The current mean is closer to parity, while the reason for the changing score magnitude remains unknown.

## What was not pursued

The within-band mean was not treated as exact parity or as proof of a specific repair. The incomplete telemetry was not treated as zero skips, and no cause was assigned from aggregate scores. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Squirrel's historical lower Tank Royale score was not reproduced beyond the current five-pair mean band: the mean delta is −12.44%, compared with −25.9% in the preceding confirmation. The current report records no runtime errors, but skipped-turn telemetry was unavailable in two pairs and the score variation remains unexplained.

## M-006 handoff

Continue in registry order with `roborumble/bbo.RamboT_0.3.jar` (`CONFIRMED (score)`).
