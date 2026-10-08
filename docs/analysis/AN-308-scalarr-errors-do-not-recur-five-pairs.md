---
id: AN-308
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ScalarR's historical error imbalance does not recur in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-308 — ScalarR's historical error imbalance does not recur in five current pairs

## Risk investigated

Whether the historical bridge file-stream error and Classic-only error imbalance for `aaa.r.ScalarR_0.005g.047.jar` recur under the latest matched artifacts, and whether its score remains outside the parity band.

## Evidence boundary

The read-only subject jar has SHA-256 `deb332f422c39ac0ff3c0996df2396834bca50533f2a414c09aa3a9fb14c1c79`. The official five-pair confirmation `908d6e21c1367208` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `49248a488cd62fcce5233fd0b0e3a32d4c218182`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena with the nine selected opponent jars recorded in the registry. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; all jars remained read-only.

## What was tried

Across five pairs, Classic averaged 113,183.0 points and Tank Royale averaged 111,593.8, for a −1.42% mean delta. Pair deltas were −0.9%, −1.4%, −1.7%, −1.3%, and −1.8%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

AN-182's earlier one-pair run recorded 230 Classic errors and no Tank Royale errors, including the known Classic five-stream-limit signature, while the historical Tank Royale stream-limit error did not recur in that run. Neither error imbalance recurred in these five current pairs. Unlike AN-182, skipped-turn telemetry was captured completely; event counts were 2, 3, 7, 5, and 7 across the five runs. These records contain bot IDs but do not identify which robot owns each event here.

## Finding

The current five-pair result is within the score-noise band, and neither the historical bridge stream error nor the prior Classic-only error imbalance was observed. The captured skipped-turn events are retained in the registry; this measurement does not attribute them to ScalarR or establish their effect. No bridge code or rumble-jar change is indicated by the current result.

## M-006 handoff

Continue in registry order with `meleerumble/abud.ThirdRobo_1.0.jar` (`DISCREPANCY (errors)`).
