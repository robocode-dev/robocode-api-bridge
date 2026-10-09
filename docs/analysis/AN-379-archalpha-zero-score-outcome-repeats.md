---
id: AN-379
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: ArchAlpha's zero-score outcome repeats with current matched artifacts
provenance: inferred
reversal-cost: low
---

# AN-379 — ArchAlpha's zero-score outcome repeats with current matched artifacts

## Risk investigated

Whether `ArchAlpha.ArchimedesAlpha_1.0.jar`'s repeated no-score outcome changes under the latest matched artifacts or identifies an engine discrepancy.

## Evidence boundary

The population is this single M-006 registry row, `roborumble/ArchAlpha.ArchimedesAlpha_1.0.jar`, whose prior status was `DISCREPANCY (no score)`. The read-only subject jar has SHA-256 `dcb4cdb1a904ba42b0747af1b29c660fe5fd01c225f4f079875cc07499b36554`; the run manifest records the selected opponent jar and hash. The confirmation `b516b54363b416df` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `8812b71bd7edb44a5a30d39281c201bc4d051282`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API 1.4.0, and runner jars are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The confirmation requested five attempts; all five produced 0–0 score outcomes and therefore no comparable score samples. No attempt was discarded. This is a repeated outcome for one subject and setup, not a score estimate; no confidence interval or significance test applies.

## What was tried

All five attempts recorded Classic score 0 and Tank Royale score 0, with no runtime errors and no skipped-turn events. The registry status is `DISCREPANCY (outcome)` because neither engine produced a scored battle; there is no score delta to compare.

The preceding observations `3120c221c1dbfb93` and `5db318c40ca0955b` also recorded 0–0 outcomes. The registry carries the diagnosis `robot-keyboard-input-required-for-activity`, owner `robot`; this retest did not inspect the subject's source or independently prove that diagnosis.

## What was not pursued

The 0–0 result was not converted into a score comparison or attributed to bridge behavior. No bridge or rumble-jar change was made from this measurement.

## Finding

ArchAlpha's symmetric no-score outcome persists with the latest matched artifacts. The current run produced no asymmetric error, skipped-turn event, or score evidence that would identify a bridge defect. The existing robot-owned diagnosis remains unverified by this retest.

## M-006 handoff

Continue in registry order with `roborumble/DM.Mijit_.3.jar` (`DISCREPANCY (errors)`).
