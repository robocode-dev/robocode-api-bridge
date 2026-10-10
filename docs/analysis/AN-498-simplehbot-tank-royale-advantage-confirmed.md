---
id: AN-498
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: SimpleHBot's Tank Royale score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-498 — SimpleHBot's Tank Royale score advantage is confirmed again

## Risk investigated

Whether `hs.SimpleHBot_1.3.jar`'s historical Tank Royale score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `5ad20a1354f20e2964e33e95c2d5157f1ee97d6889d1c4fc2a8dc400306e78ca`. The official five-pair confirmation `714b7b122eb07e1d` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `0e4ae538692b96d2afe7a8480a00193496c7c0c1`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 4,106.4 points and Tank Royale averaged 4,961.0 points, for a +21.62% mean delta. Pair deltas were +17.1%, +16.3%, +39.8%, +24.2%, and +10.7%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `c9d49e297432faa4` recorded Classic scores of 3,590 and Tank Royale scores of 5,594, for a +55.8% delta. The current five-pair mean confirms the Tank Royale advantage at +21.62%, smaller than the earlier single-pair difference.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

SimpleHBot's Tank Royale score advantage persists under current matched artifacts. The mean delta is +21.62%, smaller than the earlier +55.8% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/hs.SimpleHBot_1.3.jar` as `CONFIRMED (score)`. Record `roborumble/hvilela.HVilela_0.9.jar` as `CONFIRMED (score)`. Skip `roborumble/infovk.s_schwarzm16.silverbird_1.0.jar` (`PASS`). Continue in registry order with `roborumble/ins.MobyNano_0.8.jar` (`score-review`).
