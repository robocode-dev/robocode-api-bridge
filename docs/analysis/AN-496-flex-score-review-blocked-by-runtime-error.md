---
id: AN-496
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Flex's current runtime error blocks a score review
provenance: inferred
reversal-cost: low
---

# AN-496 — Flex's current runtime error blocks a score review

## Risk investigated

Whether `hlavko.micro.Flex_1.5.jar`'s earlier Classic score advantage persists under current matched artifacts, and whether the score-review result can be confirmed.

## Evidence boundary

The read-only subject jar has SHA-256 `c0483b78ec051e00d18a60f85c937aa7661498dd2872fb0078d9f6e3abb54ff5`. The official confirmation attempt `eabcc96b5dbe2f34` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `0e4ae538692b96d2afe7a8480a00193496c7c0c1`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits, and did not record a Robocode version. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

The registry records one attempt and zero valid paired samples; its status is `DISCREPANCY (errors)`. Both current Tank Royale bot logs show `java.lang.StringIndexOutOfBoundsException` at index −1 in `hlavko.micro.Flex.getPatternMatchingGun` (line 86), called from `Flex.onScannedRobot`. The raw current Classic result file reports a completed battle with participant scores of 2,811 and 2,870 and no battle errors, but it is not paired with a successful Tank Royale score. Skipped-turn telemetry is incomplete, and no score delta is available.

## What was tried

The earlier observation `c7bbcab70575bea1` recorded Classic scores of 5,883 and Tank Royale scores of 3,324, for a −43.5% delta, with no runtime errors. The current run could not confirm that score gap because Tank Royale failed in `getPatternMatchingGun` and the registry retained zero valid paired samples.

## What was not pursued

The stack identifies the failing robot method but does not establish the source-level cause. No bytecode or source analysis was performed, and the exception was not attributed to the bridge. No code or rumble-jar change was made.

## Finding

Flex's prior score-review outcome cannot be confirmed under the current run because both Tank Royale bots fail with `StringIndexOutOfBoundsException` in the robot's pattern-matching gun method. The registry classifies the new outcome as `DISCREPANCY (errors)` with one attempt and zero valid paired samples.

## M-006 handoff

Record `roborumble/hlavko.micro.Flex_1.5.jar` as `DISCREPANCY (errors)`. Skip `roborumble/hlavko.nano.Phoenix_1.0.jar` and `roborumble/hlavko.nano.Ringo_1.0d.jar` (`PASS`). Record `roborumble/hlavko.nano.Ringo_2.0.jar` as `CONFIRMED (score)`. Skip `roborumble/homerbots.h1_1.0.jar` and `roborumble/hp.Athena_0.1.jar` (`PASS`). Continue in registry order with `roborumble/hs.SimpleHBot_1.3.jar` (`score-review`).
