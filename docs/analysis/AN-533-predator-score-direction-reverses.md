---
id: AN-533
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Predator's score direction reverses on current artifacts
provenance: inferred
reversal-cost: low
---

# AN-533 — Predator's score direction reverses on current artifacts

## Risk investigated

Whether `lorneswork.Predator_1.0.jar`'s historical Classic score advantage persists under current matched artifacts, or whether the measured direction has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `af75898eac7a0de312f3d96b701b8c1ae3c34152986b76fc4cd575c5a08ae07d`. The official five-pair confirmation `22f412f7bb9b7d89` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `64bf710b3b8c5dadbbe8742a213d5f3fdc5f4ef3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The current record does not preserve the exact Java executable selected for Classic.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 7,189.4 points and Tank Royale averaged 8,874.2 points. Pair deltas were +28.0%, +32.9%, +14.1%, +25.6%, and +18.5%, for a +23.82% mean. Skipped-turn telemetry was captured in all five Tank Royale attempts; attempt 3 recorded bot 1 at round 8, turn 151, and the other attempts recorded none. The registry status is `CONFIRMED (score)` even though the mean delta is below the manifest's 25.0% threshold; this note records the current classification without inferring why it was assigned.

## Population and sampling boundary

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `64bf710b3b8c5dadbbe8742a213d5f3fdc5f4ef3`. The considered population was its `roborumble` robot rows; this subject was eligible because its starting status was `score-review` and it was selected in registry order. The official five-pair method is the sample; all valid pairs were retained, while failed or incomplete attempts are reported separately for error-stopped subjects. Pair deltas show observed spread, but no confidence interval was computed. Under [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this is statistical sweep quality evidence rather than deterministic acceptance proof.

## What was tried

The earlier observation `93c5ace4d4087b33` recorded a −57.2% delta, with Classic ahead. The current confirmation reverses direction and measures a +23.82% mean in Tank Royale's favor. The registry reports `CONFIRMED (score)` for this observation.

## What was not pursued

The retest establishes the direction and measured magnitude under the current artifacts but does not explain the reversal or the registry classification relative to the recorded threshold. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

Predator's score direction reversed: current matched runs favor Tank Royale by a +23.82% mean pair delta, compared with the earlier Classic-favoring −57.2%. The registry status is `CONFIRMED (score)`; the cause of the reversal and the sub-threshold classification remain unknown.

## M-006 handoff

Continue with `roborumble/lrem.quickhack.QuickHack_1.0.jar` (`score-review`) in AN-534.
