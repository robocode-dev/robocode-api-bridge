---
id: AN-485
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Morfeas's Tank Royale concurrent-modification error recurs
provenance: inferred
reversal-cost: low
---

# AN-485 — Morfeas's Tank Royale concurrent-modification error recurs

## Risk investigated

Whether `gre.svman4.Morfeas_1.4.3.jar`'s historical Tank Royale `ConcurrentModificationException` recurs under current matched artifacts, and whether the current run yields a usable paired score.

## Evidence boundary

The read-only subject jar has SHA-256 `eb0b9e69c2cb375ac58906103cf72cb15d5fd8128e6d56a09e145961c935648c`. The official confirmation attempt `fc42d0b71fbdb716` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `6dbe57bf69937995ceddf5a8433fc0af3972ca29`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

The registry records one attempt and zero valid paired samples; its status is `DISCREPANCY (errors)`. The raw current Classic result file reports a completed battle with participant scores of 2,660 and 3,173 and no battle errors, but the Tank Royale worker failed with `java.util.ConcurrentModificationException` in `gre.svman4.Morfeas.updateMineWaves`. The current Tank Royale stack reaches that robot method from the status callback; skipped-turn telemetry is incomplete, and no score delta is available.

## What was tried

The earlier observation `414e70d87bc5aa3d` recorded Classic scores of 0 and 0 and no Tank Royale score. Tank Royale reported two `ConcurrentModificationException` instances from `gre.svman4.Morfeas.updateMineWaves`. The current run records the same exception and origin, with one attempt and zero valid paired samples. The Classic result is not paired with a successful Tank Royale score.

## What was not pursued

The evidence confirms recurrence of the bot's exception signature but does not establish the source-level mutation sequence. No bytecode or source analysis was performed, and the exception was not attributed to the bridge. No code or rumble-jar change was made.

## Finding

Morfeas's Tank Royale `ConcurrentModificationException` recurred under current matched artifacts. The registry classifies the outcome as `DISCREPANCY (errors)` with zero valid paired samples; the available Classic battle score does not establish a score comparison.

## M-006 handoff

Skip `roborumble/gre.svman4.Leonidas_1.3.2.jar` (`MATCHED (score noise)`). Record `roborumble/gre.svman4.Morfeas_1.4.3.jar` as `DISCREPANCY (errors)`. Continue in registry order with `roborumble/grybgoofy.GoofyBot_0.10.jar` (`DISCREPANCY (no score)`).
