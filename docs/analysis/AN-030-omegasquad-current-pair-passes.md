---
id: AN-030
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CAP-007, AN-029]
title: OmegaSquad passes on matched current teamrumble artifacts
provenance: inferred
reversal-cost: low
---

# AN-030 — OmegaSquad passes on matched current teamrumble artifacts

## Question

Does `teamrumble/kid.team.OmegaSquad_.0.2.jar` still fail under Tank Royale when tested with the current bridge, Bot API, and runner builds?

## Evidence boundary

The read-only team jar has SHA-256 `6c95e40d3f595ceaff6d469464ffb7d28a3589490569aa434f72d36971020437`. The fresh official pair used bridge commit `0d7e647fa07b85ad9047fdbae99f0b3f64a8c67d`, Tank Royale commit `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`, the local 1.4.0 runner (SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`) and Bot API (SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`) built from that revision. The bridge API and wrapper hashes were `fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5` and `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`.

The pair used official teamrumble settings: a 1200×1200 field, 10 rounds, and two teams. The collection jar remained read-only.

## Results

The prior observation `527d1334b6cbaafe` used bridge commit `d64c2fbad098a8f001662f9aad542e4352e3cb63`, Tank Royale commit `0b2d2beb27387235c24a22f8d8fbd10bf88ef835`, and Bot API 1.2.0. Classic completed with 15,367 points; Tank Royale produced no result and recorded repeated `ArrayIndexOutOfBoundsException` messages for indices beyond an array of length one.

The fresh official observation `1c265b098fb762c5` completed on both engines without runtime errors. The selected classic team was `kid.team.Niner [.0.2]`. Classic scored 14,920 and Tank Royale scored 13,896, a −6.9% delta; the harness classified the pair `PASS`.

Skipped-turn telemetry captured one event for each of eight bot IDs at round 1, turn 1. This records bridge scheduling behaviour but does not identify the cause of the earlier class-array errors or the score delta.

## Finding

One fresh official pair passes on the current matched local artifacts, with a score delta within the 25% threshold and no runtime errors on either side. The old Tank Royale no-result and array-bounds errors were not reproduced. This pair does not establish which earlier artifact or runtime behavior caused that failure, and it is not deterministic parity proof.

## M-006 handoff

Keep the current `PASS` observation and continue in teamrumble registry order with the next unresolved subject.
