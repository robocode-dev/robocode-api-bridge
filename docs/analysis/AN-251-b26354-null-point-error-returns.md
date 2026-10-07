---
id: AN-251
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, AN-015, AN-101]
title: B26354's earlier robot NullPointerException returns in the latest official pair
provenance: inferred
reversal-cost: low
---

# AN-251 — B26354's earlier robot NullPointerException returns in the latest official pair

## Risk investigated

Whether B26354's historical Tank Royale-only `NullPointerException` returns in the current official M-006 pair, and whether the recurrence establishes a bridge defect.

## Evidence boundary

The read-only subject jar `meleerumble/darkcanuck.B26354_1.06.jar` has SHA-256 `9dec0de94deb2d91527d98bfdfc56b76eb65734778e6096b23724d7480f8e58d`. The prior current-pair observation `e2187be09c14425c` completed on 2026-10-06; the new official observation `326a1db9fec4d3c7` completed on 2026-10-07 with bridge commit `86cd96970cca5e437b78f5aa34965942e33add7d` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 and Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`, with 10 participants, 35 rounds, and a 1000×1000 arena. The Bot API jar SHA-256 was `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, the runner jar SHA-256 was `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and the wrapper jar SHA-256 was `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`. Requested skipped-turn telemetry was incomplete because Tank Royale stopped before returning a score.

## What was tried

The previous current-pair observation scored 114,090 in Classic with 324 errors and 111,505 in Tank Royale with no errors, a −2.3% delta. The new observation scored 113,596 in Classic with 320 errors, but Tank Royale produced no score after four errors. Its first subject-side failure was `NullPointerException: Cannot invoke "java.awt.geom.Point2D.getX()" because "pt" is null` at `darkcanuck.m.a`. The Classic signatures are the known `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`, which AN-015 maps to the pinned opponent pool.

The new failure repeats the subject-side signature from observation `b336f45184cf831d` and AN-015, after it did not recur in the preceding current-pair run. This confirms recurrence of the robot-origin exception surface. It does not identify why the exception occurs in this Tank Royale run but not in the paired Classic run, and does not establish a bridge violation.

## Finding

Retain `DISCREPANCY (outcome)`: Tank Royale again stops on a B26354-owned null-point failure and has no score. The registry keeps its earlier harness-contamination diagnosis as history and appends the current observed cause `robot-null-point-in-darkcanuck-m-a` with owner `robot`. The fixture errors in Classic remain separate. No bridge code change is indicated by this observation alone.

## M-006 handoff

This is the last `meleerumble` subject in registry order. Check the M-006 scope for its next collection before continuing.
