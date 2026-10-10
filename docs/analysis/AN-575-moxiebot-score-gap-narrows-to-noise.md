---
id: AN-575
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: MoxieBot's score gap narrows to noise
provenance: inferred
reversal-cost: low
---

# AN-575 — MoxieBot's score gap narrows to noise

## Risk investigated

Does MoxieBot's earlier score difference persist under current matched artifacts, or is the current mean within score noise?

## Evidence boundary

The read-only subject jar `ncj.MoxieBot_1.0.jar` has SHA-256 `b8f6b2bcd2039b665d29a300a30d7c405bc90a332463af5350113b5d4545fa4d`. The current five-pair confirmation `8a74b8a8188a0820` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `e644efe51b66d681e8e56725b0aac3f80efed55e`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists ncj.MoxieBot 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `e644efe51b66d681e8e56725b0aac3f80efed55e`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 1,304.6 points and Tank Royale averaged 1,248.8 points. Pair deltas were -4.6%, -16.6%, -9.5%, -33.6%, +61.4%, for a -0.58% mean (Classic leads by 0.58%). The confirmation manifest's threshold is 25.0%; the registry status is `MATCHED (score noise)`, recorded without inferring the classifier's reason. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `1a1bd5dc5e9ba681` recorded a +30.90% delta. The current five-pair mean is -0.58%; the registry reports `MATCHED (score noise)`.

## What was not pursued

The retest shows the average score difference narrowed into the registry noise band but does not explain the wide spread among pairs. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

MoxieBot's earlier +30.9% Tank Royale score advantage is not reproduced in the current sample: the mean is -0.58% and the registry classifies it as `MATCHED (score noise)`. Four pair deltas were negative, while the fifth was +61.4%, so the mean hides substantial variation.

## M-006 handoff

Continue with `roborumble/nexus.Prototype_1.0.jar` (`score-review`) in AN-576.
