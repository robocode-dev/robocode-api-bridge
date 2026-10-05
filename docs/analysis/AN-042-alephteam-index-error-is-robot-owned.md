---
id: AN-042
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CAP-007, AN-002, AN-020]
title: AlephTeam's earlier asymmetric index error is robot-owned and appears on both engines
provenance: inferred
reversal-cost: low
---

# AN-042 — AlephTeam's earlier asymmetric index error is robot-owned and appears on both engines

## Question

Does the previous Tank Royale-only `IndexOutOfBoundsException` for `teamrumble/rz.AlephTeam_0.34.jar` persist on current artifacts, and what operation produces it?

## Evidence boundary

The read-only team jar has SHA-256 `bdd8d3fa9492e41a8a35e8f67347ebc36c74ee850740587ace45497348bff697`; the selected robot is `rz.Aleph [0.34]`. Three earlier official observations used Bot API 1.2.0: Classic scored 20,781 without logged errors, while Tank Royale aborted with `java.lang.IndexOutOfBoundsException` and no score.

The current official observation `318881e18dc34010` used Classic Robocode 1.11.1, a 1200×1200 field, 10 rounds, and two teams. Both sides completed: Classic scored 21,047 and Tank Royale scored 20,740 (−1.5%). Each logged one `IndexOutOfBoundsException`, with no bridge-only signature, and the harness classifies the result as `PASS`. The bridge commit was `c415dc1254d6cbf49d3f93cf326b781ed2dcbb83`; Runner 1.4.0 SHA-256 was `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, Bot API 1.4.0 SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, bridge API SHA-256 was `3f09f55c412b024e5aa7fc202cde9cdbbd3fdd0d61a636370d340361e572aa3a`, and wrapper SHA-256 was `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`.

The outer jar has no bundled Java sources, so the read-only `rz.a.Enemy` class was inspected with `javap`. Its `getAimPos()` method selects pattern indices, reads `pattern.get(index)`, increments the index, then reads `pattern.get(index)` again with the incremented value at source line 198, without checking the list boundary. The method catches `Exception` at line 213 and prints the turn and exception before continuing, which matches the current reported bounds errors: Classic logged `Index 3363 out of bounds for length 3363` at turn 513, and Tank Royale logged `Index 1095 out of bounds for length 1095` at turn 1104. The output contains no stack trace, so the bytecode path is source-level evidence rather than a captured runtime frame.

## Finding

The previous asymmetric outcome does not reproduce on current artifacts. The bundled robot contains an unguarded one-past-end pattern lookup inside a handler that catches and logs the resulting exception; the current run reaches that robot-owned path in both engines and completes within the score band. The historical cause is `robot-enemy-pattern-reads-past-final-sample`, owner `robot`. No bridge or Tank Royale repair is indicated, and the read-only jar remains unchanged.

## M-006 handoff

Keep `teamrumble/rz.AlephTeam_0.34.jar` at `PASS` with the historical error observation and source-level diagnosis retained. Continue in registry order with `teamrumble/rz.GlowingHawks_0.2.jar`.
