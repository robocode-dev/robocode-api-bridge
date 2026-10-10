---
id: AN-656
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Galaxy03's Tank Royale score advantage confirmed just above the threshold
provenance: inferred
reversal-cost: low
---

# AN-656 — Galaxy03's Tank Royale score advantage confirmed just above the threshold

## Risk investigated

Does Galaxy03's earlier score difference persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `suzushin7.nano.Galaxy03_1.01.jar` has SHA-256 `273070235e3054cfc3f7b2796a8295a969c3fe4624952d1cbdc4cb816fb61b9e`. The current five-pair confirmation `42856daabd4d2055` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `28d23c2e5797f1c8ca38b6162ef53366c12dba4d`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists suzushin7.nano.Galaxy03 1.01 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `28d23c2e5797f1c8ca38b6162ef53366c12dba4d`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 10,990.6 points and Tank Royale averaged 13,775.2 points. Pair deltas were +23.2%, +25.2%, +24.5%, +28.0%, +25.8%, for a +25.34% mean (Tank Royale leads by 25.34%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events. The five-pair mean is 25.34%, only 0.34 percentage points above the 25.0% threshold; two individual pair deltas, 23.2% and 24.5%, are below it. The registry status reflects the recorded mean and threshold; this sample does not establish a wider margin.

## What was tried

The earlier observation `2e76da0c6d40e9f0` recorded a +25.40% delta. The current five-pair mean is +25.34%; the registry reports `CONFIRMED (score)`. The retest command was `python compat_test.py --collections roborumble --only suzushin7.nano.Galaxy03_1.01.jar --retry-unresolved --confirm-score --capture-skipped-turns --limit 1`.

## What was not pursued

The retest confirms the recorded score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Galaxy03's five-pair sample confirms a 25.34% Tank Royale score advantage: Classic averaged 10,990.6 points and Tank Royale averaged 13,775.2. Both engines reported no runtime errors; the reason for the score difference remains undetermined.

## M-006 handoff

Continue with `roborumble/synapse.rsim.GeomancyBS_0.11.jar` (`score-review`) in AN-657.
