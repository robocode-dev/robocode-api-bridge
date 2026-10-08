---
id: AN-309
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ThirdRobo's historical serialization errors do not recur in five current pairs
provenance: inferred
reversal-cost: low
---

# AN-309 — ThirdRobo's historical serialization errors do not recur in five current pairs

## Risk investigated

Whether `abud.ThirdRobo_1.0.jar`'s historical non-serializable team-message errors and error imbalance persist under the latest matched artifacts, and whether its score remains outside the parity band.

## Evidence boundary

The read-only subject jar has SHA-256 `124b7067cca1b407845176fd26ad2388aab1ad20085b34bd5561539515272a16`. The official five-pair confirmation `2b91198a0feb5be7` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `49248a488cd62fcce5233fd0b0e3a32d4c218182`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena with the nine selected opponent jars recorded in the registry. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; all jars remained read-only.

## What was tried

Across five pairs, Classic averaged 115,698.4 points and Tank Royale averaged 112,327.6, for a −2.9% mean delta. Pair deltas were −2.1%, −3.0%, −3.2%, −2.7%, and −3.5%. Neither engine reported errors in any pair, and the registry status is `MATCHED (score noise)`.

AN-183's earlier one-pair run recorded 920 Classic errors and 10,483 Tank Royale errors, including repeated `NotSerializableException: abud.EnemyInfo`. Classic attributed the send to ThirdRobo, and Tank Royale rejected the same robot payload. The current five-pair run did not reproduce those errors. Skipped-turn telemetry was captured completely; event counts were 4, 5, 5, 0, and 0 across the runs. These records contain bot IDs but do not identify which robot owns each event here.

## Finding

ThirdRobo's score is within the score-noise band, and its historical serialization errors did not recur under the latest matched artifacts. The prior invalid message payload came from the read-only robot; the bridge's rejection matches Classic's serialization failure. The captured skipped-turn events are retained in the registry and are not attributed to ThirdRobo by this measurement. No bridge code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/adt.Ar1_2.1.jar` (`DISCREPANCY (errors)`).
