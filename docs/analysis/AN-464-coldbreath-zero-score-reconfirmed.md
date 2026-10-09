---
id: AN-464
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: ColdBreath's zero-score outcome persists with current artifacts
provenance: inferred
reversal-cost: low
---

# AN-464 — ColdBreath's zero-score outcome persists with current artifacts

## Risk investigated

Whether `dsekercioglu.shield.ColdBreath_1.0.jar`'s historical zero-score outcome persists under the current matched artifacts.

## Evidence boundary

The read-only subject jar has SHA-256 `336a299174282db068cff77960bed112771207a9523659a63a070f72dce44067`. The official five-attempt confirmation `7769b1cda91deff2` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `89f1aa92da35b79287488a5a9ce63be021d752d7`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and runner artifacts. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

Each of the five current attempts completed with a score of 0 for both engines. No attempt supplied a positive score sample, so no pair delta or five-pair mean could be calculated. Neither engine reported runtime errors. Tank Royale skipped-turn telemetry was captured as an empty event list in all five attempts. The jar remained read-only.

## What was tried

The registry status is `DISCREPANCY (outcome)`, with five attempts and zero score samples. The earlier observation `854a73382e180a56` also recorded 0 points from both engines but used older artifacts. The zero-score outcome therefore persists across these artifact sets; it does not establish why ColdBreath scores zero.

## What was not pursued

No gameplay trace or source attribution was made for the zero-score outcome. The absence of runtime errors and skipped-turn events does not explain why both engines award no points. No code or rumble-jar change was made.

## Finding

ColdBreath again scored 0–0 in all five attempts using the current matched artifacts. The result is classified `DISCREPANCY (outcome)` because it provides no usable score comparison. The cause remains unresolved.

## M-006 handoff

Continue in registry order with `roborumble/e32.Omni_0.04.jar` (`DISCREPANCY (outcome)`).
