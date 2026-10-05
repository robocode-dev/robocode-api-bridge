---
id: AN-035
type: analysis
status: active
links: [P-001, CAP-006, AN-020]
title: SuperSittingDuckTeam repeats its symmetric zero-score runtime failure on current artifacts
provenance: inferred
reversal-cost: low
---

# AN-035 — SuperSittingDuckTeam repeats its symmetric zero-score runtime failure on current artifacts

## Question

Does the `teamrumble/mn.SuperSittingDuckTeam_1.0.2.jar` runtime failure still occur with the current matched bridge and Tank Royale artifacts, and does the retest expose a new asymmetric cause?

## Evidence boundary

The collection jar has SHA-256 `54725c8839f7074aac3a9f30d6adfd6bca82e7f0756ed792cff928e3ca5f457e` and the selected robot is `mn.SuperSittingDuck 1.0.2`. The fresh official pair used a 1200×1200 field, 10 rounds, two teams, and Classic Robocode 1.11.1. Registry observation `15d61e66e8f76f48` was measured with bridge commit `e2850fde704191b934529120ac4630b36eea234a`, Runner 1.4.0 (SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`), and Bot API 1.4.0 (SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`). The Tank Royale artifacts were built from unchanged production source commit `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`; its checkout had since advanced to a documentation-only commit. The collection jar was not modified.

This is one diagnostic retest, not a score confirmation. The tracked registry retains the artifacts, observation, error signatures, and result.

## Result

Classic and Tank Royale each scored zero and recorded 100 errors. Both normalized signatures are `java.lang.RuntimeException` from `mn.SuperSittingDuck.run`, so the harness retains `DISCREPANCY (no score)`. The current observation reproduces the symmetric failure already described in AN-020.

## Finding

This current-artifact pair confirms that the runtime failure still occurs symmetrically in both engines. It provides no new evidence of a bridge-only or Tank Royale-only failure and does not locate the exception's internal cause. The registry's existing diagnosis events remain `nested-team-jar-discovery` (owner `wrapper`) and `symmetric-robot-runtime-failure-zero-score` (owner `robot`); the latter records the observed robot-owned failure surface, not a deeper source-level explanation. No new diagnosis event is warranted from this retest.

## M-006 handoff

Keep the current `DISCREPANCY (no score)` observation and existing diagnosis. Continue to the next unresolved `teamrumble` registry entry, `mn.nano.perceptual.ImpactTeam_1.3.0.jar`. This subject remains open for the M-006 requirement to diagnose every discrepancy; no bridge source changed.
