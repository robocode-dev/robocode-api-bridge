---
id: AN-551
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: LittleBlackBook's Tank Royale score advantage grows
provenance: inferred
reversal-cost: low
---

# AN-551 — LittleBlackBook's Tank Royale score advantage grows

## Risk investigated

Does LittleBlackBook's earlier Tank Royale score advantage persist under current matched artifacts, and how has its magnitude changed?

## Evidence boundary

The read-only subject jar `mld.LittleBlackBook_1.69e.jar` has SHA-256 `d0ed49835588ab6b2a4162870fb805e0a3a7e611928e54b7f8e20a71a161d183`. The official five-pair confirmation `5941c490fca5a7bb` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `596a4914d9684b64a70123941a165b8883c03c3f`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry does not retain the exact selected Classic robot string or the Java executable used.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `596a4914d9684b64a70123941a165b8883c03c3f`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 1,307.6 points and Tank Royale averaged 6,256.6 points. Pair deltas were +487.2%, +353.5%, +367.0%, +415.3%, +307.6%, for a +386.12% mean (Tank Royale leads by 386.12%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `2974701ea33707c7` recorded a +241.80% delta. The current five-pair mean is +386.12%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a large Tank Royale score advantage but does not explain its cause or why its magnitude increased. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

LittleBlackBook's Tank Royale score advantage persists and grows in the five-pair sample to 386.12%, from 241.8% in the earlier observation. The sample had no runtime errors or bridge-only error signatures.

## M-006 handoff

Continue with `roborumble/drm.Magazine_0.39.jar` (`score-review`) in AN-552.
