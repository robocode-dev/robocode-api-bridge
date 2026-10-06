---
id: AN-055
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-054]
title: MannyPacquiao's terminal loop escaped wrapper stopping and confused error attribution
provenance: inferred
reversal-cost: low
---

# AN-055 — MannyPacquiao's terminal loop escaped wrapper stopping and confused error attribution

## Risk investigated

Whether the unresolved `meleerumble/arthord.MannyPacquiao_Beta.jar` result identifies a bridge loop-stop defect, a harness error-attribution defect, or only the known pinned melee opponent failures.

## Evidence boundary

The read-only subject jar has SHA-256 `4bffcd9f4553a556fb6d136ffcf958755baf2050b0673d4c8c88f119fa45d257`. Its bundled source ends `run()` with `do { turnRadarRightRadians(1); } while (true);`; `javap` shows the compiled loop as an unconditional backward `GOTO`. The official one-pair runs used Classic Robocode 1.11.1, bridge commit `5c379c13f86d33bc675f39452be5b63e2819a9d5`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The fixed pool included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`; the subject and fixture jars remained unchanged.

## What was tried

The pre-fix current-artifact observation `2dc01ff81b991c7a` scored 113,884 in Classic with 493 errors and 112,951 in Tank Royale with zero errors, a −0.8% single-pair delta. The original historical observation `4b092c6fc546a70d` showed the same basic symptom on older artifacts. Classic logged repeated force-stops for MannyPacquiao followed by `robocode.exception.RobotException: You cannot take action in this thread!` at `arthord.MannyPacquiao.run`. Classic stops a robot thread that does not exit after interruption; the following action call is rejected because the thread no longer owns the robot execution context.

`RobotMethodReplacer` had transformed recognized conditional infinite loops but missed this terminal backward `GOTO`. Commit `5c379c1` added a stop-condition branch for that terminal-loop shape. The same commit corrected `parity_registry.py` so an error origin is selected only from that exception's own stack, instead of allowing a frame from a following exception to leak backward into the previous signature.

The first post-fix observation `5d72a40257025954` scored 114,255 in Classic with 901 errors and 112,702 in Tank Royale with zero errors, a −1.4% single-pair delta. The repair-linked observation `3fe413f54fb12c10` scored 114,624 in Classic with 554 errors and 112,721 in Tank Royale with zero errors, a −1.7% single-pair delta. Both score deltas are inside the 25% review threshold; these single pairs do not confirm a score gap. Elapsed-time variation across the runs is also insufficient to establish a performance change.

After parser correction, remaining named Classic signatures point to `amk.ChumbaMini.saveData` and `amk.guns.Aristocles.prepare`, with some stackless errors recorded as `unknown`. The only named MannyPacquiao signature is the expected `RobotException` from the force-stopped run loop. Tank Royale has no error signatures. This matches the pinned opponent-pool failures documented in AN-015 and observed in AN-049 through AN-054. The registry remains `DISCREPANCY (errors)` because Classic still reports those errors; the run is not a pass.

## Finding

The bridge had a terminal-loop transformation gap for this compiled `do-while` shape, and the parity registry parser could misattribute frames across adjacent exceptions. The wrapper and harness fixes are recorded in `5c379c1`, with both corrections listed in `CHANGELOG.md`. The remaining measured error discrepancy belongs to the fixed opponent pool, so keep the subject's registry status and all measurement history intact. Tag the case `melee-opponent-pool-contamination` (owner `harness`), `error-origin-cross-stack-association` (owner `harness`), and `terminal-do-while-run-loop-not-transformed` (owner `wrapper`). Do not edit the read-only rumble jar.

## M-006 handoff

Continue in registry order with `meleerumble/arthord.NanoSatanMelee_Beta.jar` using the current bridge and Tank Royale artifacts.
