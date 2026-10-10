---
id: AN-591
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: MojRobot's Classic score gap narrows to noise
provenance: inferred
reversal-cost: low
---

# AN-591 — MojRobot's Classic score gap narrows to noise

## Risk investigated

Does MojRobot's earlier Classic score advantage persist under current matched artifacts, or is the current difference within score noise?

## Evidence boundary

The read-only subject jar `paket.MojRobot_1.0.jar` has SHA-256 `2ca64645fee46fb94e083b8cac7f7c0aa1c9ed65c57f4dbd5df118cfa1cd7f88`. The current five-pair confirmation `8de8152264b74656` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `9d8c04d240e231da6175156328b434fdc8a0e3ac`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists paket.MojRobot 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `9d8c04d240e231da6175156328b434fdc8a0e3ac`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,839.8 points and Tank Royale averaged 5,019.2 points. Pair deltas were -12.8%, -24.3%, -18.6%, -5.6%, -9.2%, for a -14.10% mean (Classic leads by 14.1%). The confirmation manifest's threshold is 25.0%; the registry status is `MATCHED (score noise)`, recorded without inferring the classifier's reason. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `b8e28e1790c20bc0` recorded a -28.20% delta. The current five-pair mean is -14.10%; the registry reports `MATCHED (score noise)`.

## What was not pursued

The retest shows that the earlier score difference is now within the registry noise band but does not explain the change. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

MojRobot's Classic score lead narrowed from 28.2% to 14.1%, and the registry now classifies it as `MATCHED (score noise)`. The five-pair sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Panzer is recorded in [AN-592](AN-592-panzer-classic-score-advantage-confirmed-again.md) with a -25.68% Classic score advantage. Continue with `roborumble/paolord.TheHulk_1.0.jar` (`score-review`) in AN-593.
