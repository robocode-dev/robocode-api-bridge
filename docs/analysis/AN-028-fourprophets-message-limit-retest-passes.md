---
id: AN-028
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CAP-007, AN-016, AN-019, AN-027]
title: FourProphets passes after the team-message packet-limit retest
provenance: inferred
reversal-cost: low
---

# AN-028 — FourProphets passes after the team-message packet-limit retest

## Question

Does the old Tank Royale ten-message packet failure for `teamrumble/jeremyreeder.collective.FourProphetsAndADiscliple_5.jar` persist after the bridge batching repair and current local Bot API and runner build?

## Evidence boundary

The read-only team jar has SHA-256 `5e9dbc6aeaee918dfc910fd3bd84f6c4249a80cfd95b069404fc2515de3769ad`. The repair-linked official retest used classic Robocode 1.11.1, bridge commit `0f16f66777905a375b4d41250920dfcc0b261d74`, Tank Royale commit `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`, the local 1.4.0 runner (SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`) and Bot API (SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`) built from that revision. The bridge API and wrapper hashes were `fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5` and `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`.

The pair used official teamrumble settings: a 1200×1200 field, 10 rounds, and two teams. The collection jar remained read-only.

## Results

The old observation `958bfa1999f49a8` had no classic errors but stopped on Tank Royale with repeated `BotException` messages stating that the ten-message limit had been reached. It used bridge `d64c2fbad098a8f001662f9aad542e4352e3cb63` and Bot API 1.2.0, and produced no Tank Royale score.

The current repair-linked observation `abbff68a384e39b8` completed on both engines without runtime errors. Classic scored 24,157 and Tank Royale scored 23,317, a −3.5% delta; the harness classified the case `PASS`. The registry links the retest to `tank-royale-team-message-limit` and repair commit `68ed6be3ac3be160c302827217d51b4885383fde`.

The current bridge batches legacy team messages at turn submission, and the local Bot API supports 64 message packets per turn with batch payloads. The prior ten-packet failure did not recur. Skipped-turn telemetry captured 26 events during round 1 at turns 1, 3, 5, 6, and 17; the telemetry does not identify a score or completion cause.

## Finding

FourProphets passes on the current matched local artifacts, with no runtime errors and a score delta within the harness threshold. The old Tank Royale packet-limit error is not reproduced after the linked message-batching repair. This one official pair is not a deterministic parity proof, but it closes the current error discrepancy for this artifact pair.

## M-006 handoff

Keep the current `PASS` observation and the linked message-limit diagnosis in the registry. Continue the team registry using the same local runner and Bot API hashes.
