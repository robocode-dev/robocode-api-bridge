---
id: AN-425
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-129, AN-264, AN-356]
title: Firestarter's known robot errors recur with no current score
provenance: inferred
reversal-cost: low
---

# AN-425 — Firestarter's known robot errors recur with no current score

## Risk investigated

Whether `cb.fire.Firestarter_2.0f.jar`'s previously diagnosed Tank Royale errors recur under the latest matched artifacts, and whether the current failure adds evidence of a bridge defect.

## Evidence boundary

The read-only subject jar has SHA-256 `de45410b59fea6b8fad9463ece161e1e1656960129f1b9bc84791137d788325f`. The official observation `98d48bf0c0834536` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `1d37a31f57f1ce5576a1fdc5e24accb4f37c3a97`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The attempt produced no score from either engine and zero completed confirmation samples. Tank Royale skipped-turn telemetry was incomplete. Its log contains an `IndexOutOfBoundsException` from `C.L.B` while `cb.fire.Firestarter.runTick` was executing, and a `NullPointerException` from `C.I.I` because the robot's `atan2` field was null. These signatures match the robot-owned failures investigated in AN-129 and repeated in AN-264. Although the registry's mechanical bridge-only signature field records `C.L.B` because Classic reported no matching exception, the prior bytecode investigation tied this frame to the robot's unchecked index-zero read. The registry status remains `DISCREPANCY (errors)`.

## What was tried

The score confirmation stopped after one attempt; both engines returned `ok: false` with no scores, and no mean delta could be calculated. The registry's earlier bridge diagnosis concerns the separate team-message batch-size issue. The current signatures match the known empty-history index and uninitialized wall-rectangle failures; they do not add evidence that the bridge caused the current failure.

## Finding

Firestarter's retest again stops without a score, with the previously diagnosed `C.L.B` and `C.I.I` robot error signatures. The incomplete telemetry and single failed attempt provide no parity result. This observation does not identify a new bridge defect, and no code or rumble-jar change is indicated.

## M-006 handoff

Skip `roborumble/cb.mega.RandomBot_1.0.jar` (`PASS`) and `roborumble/cb.nano.Insomnia_1.0.jar` (`MATCHED (score noise)`). Continue with `roborumble/cbot.agile.Nibbler_0.2.jar` (`DISCREPANCY (errors)`).