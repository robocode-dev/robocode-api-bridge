---
id: AN-577
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Nexus.Two's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-577 — Nexus.Two's Classic score advantage is confirmed again

## Risk investigated

Does Nexus.Two's earlier Classic score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `nexus.Two_0.2.jar` has SHA-256 `156aa2641cfd499cb2b8c80e4684d25d556424db64596776828ecd4ab28cbafb`. The current five-pair confirmation `c509dddd7b2fb06a` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `83005f7f8c1c3a11cf808f22935c91941b765558`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists nexus.Two 0.2 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `83005f7f8c1c3a11cf808f22935c91941b765558`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 3,368.2 points and Tank Royale averaged 2,189.8 points. Pair deltas were -40.0%, -36.2%, -29.5%, -35.6%, -32.8%, for a -34.82% mean (Classic leads by 34.82%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `faf8e5dab6252c46` recorded a -28.70% delta. The current five-pair mean is -34.82%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms the score difference but does not explain its cause or change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Nexus.Two's five-pair sample confirms a 34.82% Classic score advantage, compared with 28.7% in the earlier observation. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/ngf.nano.Sparky_0.1.5.jar` (`score-review`) in AN-578.
