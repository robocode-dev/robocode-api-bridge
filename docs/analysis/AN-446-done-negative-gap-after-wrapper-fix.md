---
id: AN-446
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-159, AN-289]
title: DOne's lower Tank Royale score persists after the wrapper fix
provenance: inferred
reversal-cost: low
---

# AN-446 — DOne's lower Tank Royale score persists after the wrapper fix

## Risk investigated

Whether `davv.DOne_b002.jar`'s confirmed Classic score advantage persists under the latest matched artifacts after the `JuniorRobot` wrapper correction, and whether the earlier wrapper failure recurs.

## Evidence boundary

The read-only subject jar has SHA-256 `c4247ac7dade8278e7bdcb028af8c14d4850654318798c694a558df07d8b6a2e`. The official five-pair confirmation `61935a5be5641c6a` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `de3a59833f71e25c07e3fd9fe614747caffa36a7`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

All five attempts produced samples; none were excluded. This is repeated measurement of one subject and setup, not a population-wide estimate. No confidence interval or significance test was calculated. The five-pair classification compares absolute mean delta to `REGRESSION_BAND_POINTS = 15.0`; the manifest's `threshold: 25.0` is the regular sweep setting and is not this confirmation gate. The measured score is a quality observation, not a deterministic acceptance proof.

## What was tried

Classic averaged 5,455.8 points and Tank Royale averaged 2,522.4 points, for a −53.64% mean delta. The five pair deltas were −62.1%, −63.7%, −47.8%, −58.4%, and −36.2%. The registry status remains `CONFIRMED (score)`. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five attempts.

AN-289's preceding confirmation after the wrapper correction had a −57.26% mean delta, with all five pairs negative and no errors or skipped turns. The current result reproduces the Classic advantage with a smaller mean gap. The earlier `NoSuchMethodError` caused by the incorrect wrapper artifact did not recur.

## What was not pursued

The repeated score difference was not attributed to the bridge, Tank Royale, or DOne from aggregate scores alone. No controlled trace or code change was made during this registry retest, and the read-only subject jar was not modified.

## Finding

DOne's Classic score advantage remains confirmed at −53.64%, compared with −57.26% in AN-289. All five pairs favored Classic, with no errors or skipped-turn events. The earlier wrapper-generated failure did not recur, and the score-gap cause remains open.

## M-006 handoff

Skip `roborumble/dcs.PM.Eater_of_Worlds_PM_1.2.jar` (`MATCHED (score noise)`) and the intervening `PASS` subjects. Continue with `roborumble/de.erdega.robocode.Polyphemos_0.4.jar` (`CONFIRMED (score)`).
