---
id: AN-555
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: GangBang's score gap narrows to noise
provenance: inferred
reversal-cost: low
---

# AN-555 — GangBang's score gap narrows to noise

## Risk investigated

Does GangBang's historical Tank Royale score advantage persist under current matched artifacts, or is the current difference within score noise?

## Evidence boundary

The read-only subject jar `dvogon.GangBang_1.0.jar` has SHA-256 `a40fb98323315743723be8b3153d3fbca76a18889ca3a3a22ec70d18826e730c`. The current retest `40506d20c2be9f51` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `859c7d5edac22146c3acb21f13e89144f6ed9163`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge API, wrapper, Bot API 1.4.0, and runner hashes are respectively `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The prepared Windows PowerShell run used two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The registry identity lists dvogon.GangBang 1.0 for Classic, but the current observation does not retain the selected value; the exact Java executable is not recorded.

The versioned campaign source was `compat-test/parity-registry.json` at starting commit `859c7d5edac22146c3acb21f13e89144f6ed9163`; the population was its `roborumble` robot rows, and this subject was eligible because its starting status was `score-review` and it was next in registry order. All 5 valid pairs were retained; no samples were excluded. Pair deltas show observed spread, but no confidence interval was computed. As described in [PDR-001](../decisions/PDR-001-three-tier-evidence-strategy.md), this sweep is statistical quality evidence rather than deterministic acceptance proof.

The new official retest `f8921cff3e782147` completed on 2026-10-25 with Classic Robocode 1.11.1, bridge commit `7d61d5214b62f9a5df322d591c2d4f8564a378ac`, and local Tank Royale commit `96bdfd7957f0506cd5a84043ec93d3fd7fd1cd4a`. The read-only robot jar SHA-256 is `a40fb98323315743723be8b3153d3fbca76a18889ca3a3a22ec70d18826e730c`; the locally built bridge API, wrapper, Bot API, and runner hashes are respectively `c72d38a2c469d947569035b8058480517ae59eb818665e4f254fdea0b74fe421`, `af06a5274cfbd497edde304f6d576f03a79810a0f6af65249644921df9a8e5bf`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. It used the official two-participant, 35-round, 800×600 setup. The observation does not retain the exact Java executable or Classic selected-name field.

All five pairs in the new retest produced samples; neither engine reported errors and no bridge-only signatures were recorded. Classic averaged 4,622.8 points and Tank Royale averaged 6,055.2 points. The pair deltas were +31.4%, +87.3%, +10.8%, +19.2%, and +21.0%; their mean is +33.94%. The registry therefore changes from `MATCHED (score noise)` to `CONFIRMED (score)`. This retest did not enable skipped-turn or scan-event telemetry.

## What was tried

The earlier observation `8e3f284de332c1dd` recorded +32.80%, and `40506d20c2be9f51` recorded +14.96%. The latest five-pair observation `f8921cff3e782147` records a +33.94% mean and changes the registry status to `CONFIRMED (score)`.

## What was not pursued

The current retest confirms a score gap but does not explain its size or spread. Read-only inspection found that GangBang uses blocking `turnGunRight` in its main loop and calls `execute()` from `onScannedRobot`, `onHitRobot`, and `onHitWall`; this source behavior alone does not identify a bridge mismatch. The five samples include one +87.3% pair and four smaller positive gaps. No controlled trace, bridge code change, or rumble-jar change was made.

## Finding

GangBang's current five-pair Tank Royale advantage averages +33.94%, so it is once again `CONFIRMED (score)` under the registry threshold. The sample had no runtime errors or bridge-only signatures; its score spread remains large and the source review has not isolated a bridge cause.

## M-006 handoff

Continue with `roborumble/ebo.Sparse_0.02.jar` (`score-review`) in AN-556.
