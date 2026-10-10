---
id: AN-579
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: SnippetBot's Tank Royale score advantage grows
provenance: inferred
reversal-cost: low
---

# AN-579 — SnippetBot's Tank Royale score advantage grows

## Risk investigated

Does SnippetBot's earlier Tank Royale score advantage persist under current matched artifacts, and how has its magnitude changed?

## Evidence boundary

The read-only subject jar `nic.SnippetBot_1.0.jar` has SHA-256 `46ff983712f2ef6cae27780563dfcc1a6ec04662e2e728a7c70f7e46519c48ed`. The current five-pair confirmation `07e68693c1d82d07` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `83005f7f8c1c3a11cf808f22935c91941b765558`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists nic.SnippetBot 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `83005f7f8c1c3a11cf808f22935c91941b765558`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 3,997.6 points and Tank Royale averaged 14,203.8 points. Pair deltas were +243.8%, +242.6%, +228.7%, +280.4%, +283.7%, for a +255.84% mean (Tank Royale leads by 255.84%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `0c261ee7291c16e5` recorded a +198.00% delta. The current five-pair mean is +255.84%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms that the Tank Royale score advantage increased but does not explain the change. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

SnippetBot's Tank Royale score advantage increased from +198.0% to +255.84% in the five-pair sample. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/nkn.mini.Jskr0_0.1.jar` (`score-review`) in AN-580.
