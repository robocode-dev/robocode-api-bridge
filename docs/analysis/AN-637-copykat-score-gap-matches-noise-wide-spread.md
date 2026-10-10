---
id: AN-637
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: CopyKat's score gap matches observed noise
provenance: inferred
reversal-cost: low
---

# AN-637 — CopyKat's score gap matches observed noise

## Risk investigated

Does CopyKat's earlier score discrepancy persist under current matched artifacts, or does it fall within the recorded threshold?

## Evidence boundary

The read-only subject jar `spinnercat.CopyKat_1.2.3.jar` has SHA-256 `412e3d30011a1491592f7532480e2e2ec55828add4121fefc7acd7409c525f3f`. The current five-pair confirmation `91ea166d899e1b2d` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `3f631a2269075741c511271640e96c7351a8adb1`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used 2 participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists spinnercat.CopyKat 1.2.3 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `3f631a2269075741c511271640e96c7351a8adb1`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All five valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

All five pairs produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 7,346.2 points and Tank Royale averaged 6,342.8 points. Pair deltas were -21.3%, -55.2%, -7.1%, +78.7%, -19.2%, for a -4.82% mean (Classic leads by 4.82%). The confirmation manifest's threshold is 25.0%; the registry status is `MATCHED (score noise)`. Skipped-turn telemetry was captured for all five Tank Royale attempts; every attempt recorded no skipped-turn events.

## What was tried

The earlier observation `9e93aa13f6c201c0` recorded a -56.90% delta. The current five-pair mean is -4.82%; the registry reports `MATCHED (score noise)`.

## What was not pursued

The retest places the score difference within the recorded noise threshold but does not explain the earlier larger discrepancy. No controlled trace or source comparison was made, and no code or rumble-jar change was made.

## Finding

CopyKat's five-pair sample shows a 4.82% Classic score lead, within the recorded 25.0% threshold; the registry status is `MATCHED (score noise)`. This retest supports score consistency within observed noise, not exact parity.

## M-006 handoff

The CopyKat retest is recorded here. Continue with `roborumble/sqTank.waveSurfing.LionWWSVMvoid_0.01.jar` (`score-review`) in AN-638.
