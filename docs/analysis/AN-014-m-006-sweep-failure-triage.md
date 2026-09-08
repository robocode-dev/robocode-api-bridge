---
id: AN-014
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, CAP-002, AN-012, C-004]
title: M-006 partial sweep reproduces state-access failures while robot code is running
provenance: inferred
reversal-cost: low
---

# AN-014 — M-006 partial sweep failure triage

## Question and consumer

Do the partial sweep's Tank Royale failures come from shared runtime or harness faults that need investigation before M-006 can establish a useful regression baseline? The consumer is the M-006 sweep and the focused repair work its results identify. This is observational analysis under the simple route; it changes no accepted criterion or plan promise.

## Evidence boundary

The sweep started on 2026-09-05 and was paused on 2026-09-06 in a prepared Windows/PowerShell environment. The bridge revision was `7ae925af0e131fabc8cc411bacc8f1f65fbec5cc`; the matched local Tank Royale Bot API and runner were built from [309183ba377b2d7951545dd00ae15800b0b1413d](https://github.com/robocode-dev/tank-royale/tree/309183ba377b2d7951545dd00ae15800b0b1413d). Prerequisites were the local rumble collection, classic installation at `C:/robocode`, JDK 17.0.17 for classic, JDK 24.0.2 for Tank Royale, Python 3.14, and locally built bridge/wrapper artifacts. This is not evidence of clean-checkout reproducibility.

The local evidence directory is `compat-test/work/m006-20260905/`: `run-manifest.json` pins artifact hashes, `checkpoint-paused-20260906.json` preserves the observed results, `errors/` holds original logs, and `diagnostic-20260906/` holds the isolated rerun. These generated files are ignored by Git and must be retained locally to inspect the full evidence.

The paused checkpoint contains 89 completed pairs: 41 PASS, 25 FAIL (TR), 21 score discrepancies, and two no-score discrepancies. These are measurements of the checkpoint on 2026-09-06, not maintained corpus totals. All completed entries are the initial filename-ordered portion of roborumble, at 800×600, 35 rounds and two participants, with one pair per jar. This is not a random sample or a population parity estimate. Melee and team have not been measured by this run. Scores are stochastic; no single-pair discrepancy establishes a reproducible score regression.

## Observations

Eleven failed entries contain null `InitialPosition.getX()` or `getDirection()` unboxing errors; seven contain the Bot API's no-current-tick exception. The groups overlap. Representative position stacks reach `BaseBot.getX` through `BotPeer.getX` from robot run or scan-handler code. ScalarR's time stack reaches `getCurrentTickOrThrow` through `BotPeer.getTime` during final-turn custom-event dispatch, matching the earlier observation in AN-012.

An isolated rerun of `acid.Null_1.0.jar` on 2026-09-06 used a fresh staging directory and the same snapshotted harness and jars, with no round-count override. Classic completed with score 2,214 and no errors. Tank Royale was aborted after the same null `InitialPosition.getX()` exception, reached from `acid.Null.onScannedRobot` through `BotPeer.getX`. This reproduces a state-access failure independently of the original staging directory. The categorical failure, not the classic score, is the finding.

Most failed logs say `<stopped: exception with no classic counterpart>`. `run_java` deliberately returns -1 after its fail-fast watcher detects a bridge-only exception. The subsequent `worker produced no result` message is therefore often a consequence of the intended abort, not evidence that Java failed to launch.

Other signatures include team-message serialization (`abud.ThirdRobo`), robot-code null or array accesses, file I/O errors, one timeout, and a connection reset. They have not all been reproduced or assigned root causes; the state-access finding does not explain every failure.

The original sweep terminated on a Windows sharing violation while deleting `work/tr-bots/lib/bot-api.jar`. No matching sweep process remained when the run was resumed. The interrupted sweep was later explicitly stopped with its process tree, preserving the last completed checkpoint. No surviving sweep process was found immediately after that pause. Historical logs do not identify the process that owned the earlier file lock.

## Source-supported hypothesis

At the pinned Tank Royale revision, [BaseBot.java](https://github.com/robocode-dev/tank-royale/blob/309183ba377b2d7951545dd00ae15800b0b1413d/bot-api/java/src/main/java/dev/robocode/tankroyale/botapi/BaseBot.java) falls back to initial-position fields when no current tick exists, and unboxes those nullable fields. This explains the immediate null-pointer failure in the recorded stacks.

[BaseBotInternals.java](https://github.com/robocode-dev/tank-royale/blob/309183ba377b2d7951545dd00ae15800b0b1413d/bot-api/java/src/main/java/dev/robocode/tankroyale/botapi/internal/BaseBotInternals.java) clears `tickEvent` in `onRoundStarted`. Its `stopThread` interrupts the robot thread and drops its reference without joining it; the runnable also dispatches final events after leaving the main loop. A previous-round thread or callback overlapping round reset is therefore a concrete race candidate. The investigation did not instrument thread ordering, so the exact interleaving and repair ownership remain unproven.

The harness's `kill_process_tree` returns immediately when its direct process has already exited. That leaves a possible cleanup gap for surviving descendants. It is a source-supported candidate for the sharing violation, not a demonstrated explanation of the historical lock.

## Tried, rejected, and next evidence

The full sweep was paused rather than continued while these failures were investigated. Original checkpoints and logs were preserved. The isolated rerun did not replace a sweep result, modify a collection jar, change runtime behavior, or suppress exception detection. Treating all failures as launch failures was rejected because the logs show deliberate exception-triggered aborts. Treating the original staging directory as the sole cause was rejected by the isolated reproduction.

The next useful evidence is a targeted round-boundary reproduction covering state reads from robot code and final callbacks, paired with process-cleanup evidence for normal exit, timeout and fail-fast abort. Those should precede selecting a runtime repair. Secondary signatures need separate triage. No baseline values, criterion statuses, or milestone completion claims were changed; at the end of this investigation on 2026-09-06, the sweep was paused at the user's request.
