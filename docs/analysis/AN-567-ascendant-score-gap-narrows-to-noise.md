---
id: AN-567
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Ascendant's score gap narrows to noise
provenance: inferred
reversal-cost: low
---

# AN-567 — Ascendant's score gap narrows to noise

## Risk investigated

Does Ascendant's earlier score difference persist under current matched artifacts, or is the current difference within score noise?

## Evidence boundary

The read-only subject jar `mue.Ascendant_1.2.27.jar` has SHA-256 `66705bfbd6daa2b1f7470e9e17846a238b752fc3829227ddc7e67612c33117ff`. The current five-pair confirmation `126ed3c66b9a2a23` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `de043930e0bf34d4b576923ab49afc91078e4e14`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists mue.Ascendant 1.2.27 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `de043930e0bf34d4b576923ab49afc91078e4e14`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 5,588.6 points and Tank Royale averaged 5,226.0 points. Pair deltas were +0.5%, -9.5%, -10.3%, -8.1%, -4.3%, for a -6.34% mean (Classic leads by 6.34%). The confirmation manifest's threshold is 25.0%; the registry status is `MATCHED (score noise)`, recorded without inferring the classifier's reason. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `5707fc085104a700` recorded a +28.00% delta. The current five-pair mean is -6.34%; the registry reports `MATCHED (score noise)`.

## What was not pursued

The retest shows that the earlier score difference is now within the registry noise band but does not explain the change in direction or magnitude. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

The earlier +28.0% score difference narrowed to -6.34%, and the registry now classifies the result as `MATCHED (score noise)`. The sample had no runtime errors or bridge-only signatures.

## M-006 handoff

Continue with `roborumble/mue.Hyperion_0.8.jar` (`score-review`) in AN-568.
