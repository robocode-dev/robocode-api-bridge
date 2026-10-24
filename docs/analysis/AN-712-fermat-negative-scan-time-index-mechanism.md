---
id: AN-712
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, C-004, C-007, AN-015, AN-193, AN-319, AN-711]
title: Fermat's negative scan-time index depends on its previous-scan state
provenance: inferred
reversal-cost: low
---

# AN-712 — Fermat's negative scan-time index depends on its previous-scan state

## Risk investigated

Whether the intermittent `Index -1` failure at `ak.RobotBody.getDirection` can be related to an observable startup event in the current official Fermat run, and what the robot's read-only bytecode says about the value that produces the index.

## Evidence boundary

This finding supplements AN-711 using the same read-only jar and official retest observation `eb08cb7a71a0a8a6`. The registry records four skipped-turn events at round 1, turn 1, for bot IDs 4, 5, 6, and 9. The saved Tank Royale runner log names a participant `Fermat 2.0`, and that participant's process output reports `BRIDGE_SKIPPED_TURN_TELEMETRY_READY botId=5` followed by `BRIDGE_SKIPPED_TURN botId=5 round=1 turn=1`; this maps one of the events to Fermat. The original jar contains no bundled Java sources, so the read-only `javap` disassembly was used.

## What was inspected

In `ak.OtherBot`'s constructor, the bytecode initializes `previousScanTime` to zero. In `ak.OtherBot.update`, it copies `scanTime` into `previousScanTime` before advancing the scan time. At `ak.RobotBody.getDirection` source line 115, the code indexes `Fermat.updateTime` using `(other.previousScanTime - 1) % 50`. If this path runs while `previousScanTime` is zero, Java's remainder yields `-1` and the array access throws the recorded `Index -1 out of bounds for length 50` exception.

The current official run proves that Fermat skipped turn 1, but it completed without the index error. The retained logs do not show when Fermat first scanned an opponent or the value of `previousScanTime` at the failing access in earlier runs. The bot-ID mapping therefore corrects AN-711's statement that the saved evidence could not identify Fermat, while leaving the startup trigger unresolved.

## Finding

The negative index is formed from the robot's own `previousScanTime` field; it is not directly formed from `getTime()`. The guard against zero or negative previous scan times is absent at the indexed access. Whether the bridge's first-scan timing leaves that field at zero when Fermat calls `getDirection` remains unproven, and a skipped turn alone does not show that this path ran. This evidence is insufficient to attribute the intermittent failure to the robot or the bridge.

## Implications for M-006

Keep `initial-status-before-run` open. A useful next trace must capture the first scan callback's turn and order relative to the robot's first update; preserve the bot-ID-to-name mapping in that evidence. Do not change the bridge's time or scan mapping based on the current run alone. No rumble jar was modified.
