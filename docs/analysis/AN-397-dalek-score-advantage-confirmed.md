---
id: AN-397
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-314]
title: Dalek's higher Tank Royale score remains confirmed under current artifacts
provenance: inferred
reversal-cost: low
---

# AN-397 — Dalek's higher Tank Royale score remains confirmed under current artifacts

## Risk investigated

Whether `agrach.Dalek_1.0.jar`'s historical higher Tank Royale score in the `roborumble` registry row persists under the latest matched artifacts, and whether the repeated measurement identifies a cause.

## Evidence boundary

The read-only subject jar has SHA-256 `1b594bd828661ec7fbeb11aba73bccf7567d16e78a9ff21114a1f430c7152836`. The official five-pair confirmation `52df99cd293c33fa` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `ff03c80914aa57ad48b08ed5ca6df4949db1ef6a`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is a repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 3,552.6 points and Tank Royale averaged 6,530.6 points, for a +86.12% mean delta. The five pair deltas were +50.1%, +86.6%, +71.7%, +99.4%, and +122.8%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts. The registry status is `CONFIRMED (score)`.

The preceding observation `5bdc9d1e03ddbbfd` on 2026-09-28 had a +136.1% delta and no recorded errors. Earlier score observations were also positive at +65.3%, +109.8%, and +48.2%. AN-314 concerns this same jar in the separate melee setup; it does not explain the current RoboRumble score difference.

## What was not pursued

Aggregate scores do not locate the divergent behavior, so no cause was assigned to the bridge, Tank Royale, or Dalek. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

Dalek's higher Tank Royale score remains confirmed at +86.12%, compared with +136.1% in the preceding observation. The current five-pair run reported no errors or skipped-turn events and did not identify the cause of the score gap.

## M-006 handoff

Continue in registry order with `roborumble/ags.polished.PolishedRuby_1.jar` (`DISCREPANCY (score)`).
