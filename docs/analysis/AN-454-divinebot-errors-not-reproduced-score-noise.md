---
id: AN-454
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-171, AN-298]
title: DivineBot's earlier Classic errors do not recur and scores remain matched
provenance: inferred
reversal-cost: low
---

# AN-454 — DivineBot's earlier Classic errors do not recur and scores remain matched

## Risk investigated

Whether `divineomega.DivineBot_1.9.5.jar`'s earlier Classic-only file-load errors recur under the latest matched artifacts, and whether five current score pairs show a material difference.

## Evidence boundary

The read-only subject jar has SHA-256 `8d127b2400ccfddf39d0666729cc749b95084f1550c76d604f8b1089ca688b8a`. The official five-pair confirmation `48195cd5a303998c` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `80b8f661c9f6785bfe8f26a9dbe9987dc72d2e82`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 8,050.2 points and Tank Royale averaged 8,992.0 points, for a +12.64% mean delta. The five pair deltas were +80.2%, −0.1%, −9.6%, −2.5%, and −4.8%. Both engines completed all attempts without errors; skipped-turn telemetry was captured with zero events for all five Tank Royale attempts. The registry status is `MATCHED (score noise)`.

AN-298's preceding observation recorded two Classic `java.io.EOFException` errors and a +0.7% Tank Royale score delta. No Classic or Tank Royale runtime errors occurred in the current five-pair run. The current mean is within the 15-point five-pair classification gate, although its first pair is a large positive outlier and the other four are near or below parity.

## What was not pursued

The first-pair outlier was not attributed to DivineBot, either engine, or the bridge. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

The previous Classic file-load errors did not recur. The five-pair mean delta is +12.64%, so the registry classifies the scores as `MATCHED (score noise)` under its 15-point gate. One pair is a large outlier; the measurements do not establish its cause.

## M-006 handoff

Continue in registry order with `roborumble/dmh.robocode.robot.BlackDeath_9.2.jar` (`DISCREPANCY (outcome)`).
