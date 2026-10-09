---
id: AN-428
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-135, AN-267]
title: NewTest's prior Classic bullet-index errors do not recur in five pairs
provenance: inferred
reversal-cost: low
---

# AN-428 — NewTest's prior Classic bullet-index errors do not recur in five pairs

## Risk investigated

Whether `com.arsenic.NewTest_1.0.jar`'s previously observed Classic-only bullet-index errors recur under the latest matched artifacts, and whether the score remains within the noise band.

## Evidence boundary

The read-only subject jar has SHA-256 `0a5a9d382489ace685399cf9c5d709daa70f7e545ac85d54a1b0e81bf7d3a412`. The official five-pair confirmation `c23d42ee4b04fd93` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1d37a31f57f1ce5576a1fdc5e24accb4f37c3a97`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 6,529.6 points and Tank Royale averaged 6,317.6 points, for a −3.24% mean delta. The five pair deltas were −3.6%, −2.4%, +1.6%, −5.7%, and −6.1%. The registry status is `MATCHED (score noise)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-267's preceding single-pair observation on 2026-10-08 had a −8.2% delta, two Classic `ArrayIndexOutOfBoundsException` events in `onBulletHit`, no Tank Royale errors, and one skipped-turn event. The current five-pair retest did not reproduce those errors or skipped-turn events and places the score difference comfortably within the 15-point band. This does not establish that the historical robot-owned unchecked-index path is permanently fixed.

## What was not pursued

The historical bullet-index errors were not attributed to the bridge; AN-135's bytecode analysis tied them to the robot's unchecked use of extracted bullet metadata. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

NewTest's earlier Classic-only bullet-index errors did not recur in the current five pairs, and its mean score difference is −3.24%, inside the noise band. No runtime errors or skipped-turn events occurred. The observation does not show whether the historical robot defect is permanently fixed.

## M-006 handoff

Continue in registry order with `roborumble/com.blogspot.malinkody.DestrobotMalin_1.0.jar` (`CONFIRMED (score)`).