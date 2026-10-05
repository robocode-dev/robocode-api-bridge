---
id: AN-022
type: analysis
status: active
links: [P-001, CAP-001, CAP-003, CAP-006, CAP-007, CAP-008, AN-020]
title: PhoenixTeam's Tank Royale melee crash reaches a null position-history point
provenance: inferred
reversal-cost: low
---

# AN-022 — PhoenixTeam's Tank Royale melee crash reaches a null position-history point

## Question

Does the current official PhoenixTeam discrepancy identify a bridge defect, a robot defect, or only the operation that crashes?

## Evidence boundary

The current official pair used the read-only collection jar \`teamrumble/davidalves.PhoenixTeam_0.54.jar\` (SHA-256 \`949e85553e857da7b1e1640f1723d97f1b45ec74d1072e7135033e315eb39cd9\`), classic Robocode 1.11.1, bridge \`fde515b9ed80d23221e4d62a7a2c0cede7671e70\`, Tank Royale \`5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb\`, and matched local 1.4.0 runner and Bot API jars with SHA-256 values \`53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af\` and \`ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752\`. The bridge API and wrapper artifacts had SHA-256 values \`fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5\` and \`1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52\`.

The pair used a 1200×1200 field and 10 official teamrumble rounds. The team descriptor names Phoenix five times, and the Tank Royale work directory contains ten member processes across the two team instances. The collection jar remained read-only; only its bytecode and team descriptor were inspected.

## Observation

Classic completed with score 20,977 and no logged runtime errors. Tank Royale produced no score: each of the ten team-member processes logged the same NullPointerException once, and the harness recorded no worker result. The exception says parameter 1 is null while reading point field \`K\`; its stack is \`davidalves.A.A.B.A\`, two \`davidalves.A.C.E.A\` frames, \`davidalves.Phoenix.B\`, and \`davidalves.Phoenix.run\`. The current registry observation is \`903836b97b9628b9\`; skipped-turn telemetry is incomplete because the bots terminate.

## Bytecode finding

Phoenix selects melee mode when \`getOthers() > 1\`, then calls its melee movement routine from \`Phoenix.B\`. The movement candidate scorer looks up a historical position from Phoenix's own indexed position track, using \`max(getTime() - 32, 6)\`, and passes that result to a point-distance method without a null check. The thrown null-parameter exception is consistent with this lookup returning no point; the stripped stack does not identify which call site within the overloaded scorer received null.

This pinpoints the bot operation that fails, but it does not explain why classic reaches the same path without an error while Tank Royale does not. \`AN-020\` established from classic's battle implementation that living teammates count in classic \`getOthers()\`; the bridge's teammate-inclusive count is therefore not a supported cause and was left unchanged.

## Finding and handoff

The official result remains an asymmetric team-runtime discrepancy with unknown ownership. The bot has an unchecked use of nullable position history, but the available evidence does not show whether Tank Royale supplies a different early clock, scan sequence, or other input that exposes it. No bridge correction is justified yet.

Keep the registry diagnosis as \`unresolved-phoenix-melee-position-history\`, owner \`unknown\`. A focused next investigation should record the first self-position samples, \`getTime()\`, scan event timestamps, and the initial radar-turn completion on both engines before the failing melee score call. Do not treat the null-check gap alone as proof that the bridge matches classic or that the robot owns the discrepancy.
