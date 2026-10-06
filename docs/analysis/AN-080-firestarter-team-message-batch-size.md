---
id: AN-080
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CAP-007, AN-015, AN-079]
title: Firestarter's oversized team-message batch is fixed; a melee fixture error remains
provenance: inferred
reversal-cost: low
---

# AN-080 — Firestarter's oversized team-message batch is fixed; a melee fixture error remains

## Risk investigated

Whether Firestarter's current no-score is caused by the robot, bridge message batching, a mismatched runner artifact, or the pinned melee opponent pool.

## Evidence boundary

The read-only subject jar `cb.fire.Firestarter_2.0f.jar` has SHA-256 `de45410b59fea6b8fad9463ece161e1e1656960129f1b9bc84791137d788325f`. Measurements used Classic Robocode 1.11.1, Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. This was a prepared Windows environment with the read-only LiteRumble collection, local bridge and Tank Royale builds, PowerShell 7.6.6, and OpenJDK 25.0.1 on the runner path; the harness auto-selects a Classic-compatible JDK no newer than 23, but the selected path is not saved in the observation manifest. The subject and pinned opponent jars were not changed.

## What was tried

The first current run used the default `examples/lib` runner, SHA-256 `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, and failed with `IndexOutOfBoundsException` in the subject's obfuscated `C.L.B` method (`b65ab209c61e4c9f`). Repeating with the locally built runner, SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, reproduced that exception (`d775196e4a55df06`), so the runner-path difference did not explain it. A further pre-fix repeat (`231f4ab6cd3586f3`) also recorded two `IllegalArgumentException`s from `broadcastTeamMessageBatch`: the encoded batch exceeded Tank Royale's 49,152-byte per-message limit ([pinned validator source](https://github.com/robocode-dev/tank-royale/blob/ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9/bot-api/java/src/main/java/dev/robocode/tankroyale/botapi/internal/IntentValidator.java#L131-L143)). The stack passed through `BotPeer.flushTeamMessages`; the bridge had combined the queued same-turn messages into one API batch. The wrapper's staged robot jar contained no changed class bytes, ruling out the new terminal-loop transform as the source of these errors.

The bridge now splits only an encoded-packet size failure, preserving order and directed-recipient routing, and uses the existing single-message route when a one-item batch still exceeds the packet limit. The post-fix official retest (`003a8921f174ee81`, bridge commit `86bd405e279bae95c3c1d093db2186252535f4d8`) no longer reported either the oversized-batch error or `C.L.B`. Classic scored 114,365 with 204 errors; Tank Royale produced no score and two errors from `amk.ShizzleStiX.Navigator.run`, a pinned opponent, before the worker completed. The current bridge artifact SHA-256 is `2fe0045694b3636d99e2718216d082d32354472535de449028daf098fbf10945`; the Bot API, runner, and wrapper hashes are recorded in the observation.

## Finding

The bridge's single-packet batching caused the 49,152-byte validation failure and was corrected without changing the Classic team-message criterion. The post-fix observation supports that diagnosis: neither the packet-size exception nor the earlier empty-list exception recurred. That does not prove the empty-list exception's root cause, because the post-fix worker stopped on a separate pinned-pool `ConcurrentModificationException` before producing a score. The current `DISCREPANCY (outcome)` is attributable to the fixed melee fixture pool already tracked for this subject; score parity remains unmeasured in the interrupted retest. Keep `melee-opponent-pool-contamination` as the current cause and do not alter any rumble jar.

## M-006 handoff

Continue in registry order with `meleerumble/cb.nano.Insomnia_1.0.jar`.
