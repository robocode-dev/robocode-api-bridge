---
id: AN-029
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CAP-007, AN-028]
title: TidalWave passes on a fresh official pair while its previous record needs provenance follow-up
provenance: inferred
reversal-cost: low
---

# AN-029 — TidalWave passes on a fresh official pair while its previous record needs provenance follow-up

## Question

Does `teamrumble/kawigi.sbf.TidalWave_0.8.jar` still show a parity discrepancy on the current matched bridge and Tank Royale artifacts?

## Evidence boundary

The read-only team jar has SHA-256 `1bbdb19a62782ebde0c75eccb304b082b2ec2ba9f6e9727405a43cee41792708`. The fresh official pair used bridge commit `64b355799e18bdfd90aafbe0baadde79dcaa78cc`, Tank Royale commit `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`, the local 1.4.0 runner (SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`) and Bot API (SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`) built from that revision. The bridge API and wrapper hashes were `fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5` and `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`.

The pair used official teamrumble settings: a 1200×1200 field, 10 rounds, and two teams. The collection jar remained read-only.

## Results

The preceding observation `d84e06614899bbdb` was marked `DISCREPANCY (errors)` with scores 23,408 and 21,598, but its stored result shows `ok=false` for both engines and empty error lists. That combination does not match the current evaluator's failure or error classification, so the old status is retained as history and was not used as the current verdict.

The fresh official observation `b5f4b749ab64fd9a` completed on both engines. Classic scored 22,567 and recorded one cleanup message, `Unable to stop thread: kawigi.sbf.FloodHT 0.8 (1)`; Tank Royale scored 21,312 with no errors. Neither side produced an exception signature, the score delta was −5.6%, and the harness classified the pair `PASS`.

Skipped-turn telemetry captured one event for each of the ten bot IDs at round 1, turn 1. This records bridge scheduling behaviour but does not identify a score cause.

## Finding

One fresh official pair passes on the current matched local artifacts, and its score delta is within the 25% threshold. The prior `DISCREPANCY (errors)` result remains anomalous because its saved status conflicts with the saved success flags and error lists; the append-only registry preserves it, while the new observation sets the current subject status to `PASS`. The earlier `bot-assumes-target-exists-before-first-scan` diagnosis remains historical and is not confirmed by this pair.

This single pair does not establish deterministic parity. The classic cleanup message had no exception signature and did not make the battle unsuccessful.

## M-006 handoff

Keep the new `PASS` observation and continue in teamrumble registry order with the next unresolved subject.
