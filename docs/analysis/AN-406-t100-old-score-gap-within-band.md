---
id: AN-406
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: T100's historical lower score falls within the current confirmation band
provenance: inferred
reversal-cost: low
---

# AN-406 — T100's historical lower score falls within the current confirmation band

## Risk investigated

Whether `ao.T100_0.9.jar`'s historical lower Tank Royale score persists under the latest matched artifacts, and whether the retest identifies why the score changed.

## Evidence boundary

The read-only subject jar has SHA-256 `05273a2b787354f59695eeb17a4a8de330ed34d75d805aa17d91a7b756efaae0`. The official five-pair confirmation `c59c37aeb3ab4bdf` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `960b55b85e93eb90fcfebddf55eca4c38b618977`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 6,759.6 points and Tank Royale averaged 7,703.0 points, for a +14.36% mean delta. The five pair deltas were +1.6%, +24.4%, +12.5%, +20.2%, and +13.1%. Two individual pairs exceeded 15%, but the five-pair mean is within the confirmation band and the registry status is `MATCHED (score noise)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

The preceding observation `dde2c0b78956ab41` on 2026-09-28 had a −26.6% delta without recorded errors; earlier score observations were −51.0% and −38.0%. The current five-pair mean does not reproduce that lower score gap beyond the band. The measurement does not identify why the direction and size changed.

## What was not pursued

The within-band mean was not treated as exact parity or as proof of a specific repair. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

T100's historical lower Tank Royale score was not reproduced beyond the current five-pair mean band: the current mean delta is +14.36%, compared with −26.6% in the preceding observation. The current report records no errors or skipped-turn events; the historical score variation remains unexplained.

## M-006 handoff

Continue in registry order with `roborumble/ap.Frederick_1.1.jar` (`DISCREPANCY (outcome)`).
