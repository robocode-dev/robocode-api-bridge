---
id: AN-414
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-112, AN-252]
title: Okami's prior Classic-only errors clear while its score stays within band
provenance: inferred
reversal-cost: low
---

# AN-414 — Okami's prior Classic-only errors clear while its score stays within band

## Risk investigated

Whether `axeBots.Okami_1.04.jar`'s historical Classic-only missing-history-file errors and earlier score discrepancy persist under the latest matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `608f42ae73765a352e9034e41f36af721cd745835a19a20f8fe7e8bbe3be30fa`. The official five-pair retest `69238d48f87da978` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `26f82bcb83360debf53b9fc0310daef363359600`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,551.4 points and Tank Royale averaged 5,214.2 points, for a −6.04% mean delta. The five pair deltas were −9.4%, −5.4%, −5.9%, −7.1%, and −2.4%. The mean is within the confirmation band, the current report records no errors on either engine, and the registry status is `MATCHED (score noise)`.

Skipped-turn telemetry was captured in all five attempts, with 10, 2, 2, 6, and 6 events respectively. The events include bot IDs 1 and 2, but this record does not attribute them to Okami. The preceding observation `eebba33f96db75a8` on 2026-10-07 had a +3.3% score delta, two Classic file-read errors, and no Tank Royale errors. AN-112 and AN-252 explain how Okami's unchecked missing-history-file path can produce the Classic message and retain the cause `robot-unchecked-null-history-file`; they leave the engine-specific file availability unresolved. Those file-read errors did not recur in the current five-pair report, while the skipped-turn events remain unattributed.

## What was not pursued

The within-band result was not treated as exact parity or proof that the history-file behavior is fixed in every setup. The skipped-turn events were not used to assign a cause or status. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Okami's score is within the five-pair band at −6.04%, and its historical Classic-only file-read errors did not recur under the newer matched artifacts. The current report records 26 skipped-turn events across the five pairs, but does not identify which participant owns them. The row now has status `MATCHED (score noise)`.

## M-006 handoff

Continue in registry order with `roborumble/bayen.UbaRamLT_1.0.jar` (`CONFIRMED (score)`), skipping `roborumble/az.Ololobot_0.2.4.jar` (`PASS`).
