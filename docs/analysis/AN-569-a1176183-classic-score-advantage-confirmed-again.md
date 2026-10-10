---
id: AN-569
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: A1176183's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-569 — A1176183's Classic score advantage is confirmed again

## Risk investigated

Does A1176183's earlier Classic score advantage persist under current matched artifacts, and has its magnitude changed?

## Evidence boundary

The read-only subject jar `mwj.A1176183_1.0.jar` has SHA-256 `f8ce952d18336b068f10ebb1eeed74639937a2fedfdcb95467c610542c885415`. The current five-pair confirmation `62db8d0d584e5c57` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `de043930e0bf34d4b576923ab49afc91078e4e14`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists mwj.A1176183 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `de043930e0bf34d4b576923ab49afc91078e4e14`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 7,320.6 points and Tank Royale averaged 4,040.6 points. Pair deltas were -51.3%, -34.3%, -45.8%, -48.5%, -42.3%, for a -44.44% mean (Classic leads by 44.44%). The confirmation manifest's threshold is 25.0%; the registry status is `CONFIRMED (score)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `4e51a38a4bc47576` recorded a -52.50% delta. The current five-pair mean is -44.44%; the registry reports `CONFIRMED (score)`.

## What was not pursued

The retest confirms a smaller Classic score advantage but does not explain the change in magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

A1176183's five-pair sample confirms a 44.44% Classic score advantage, down from 52.5% in the earlier observation. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/myl.micro.Troodon_1.10.jar` (`score-review`) in AN-570.
