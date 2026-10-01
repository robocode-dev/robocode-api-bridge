---
id: AN-018
type: analysis
status: active
links: [P-001, CAP-003, CAP-007, CAP-008, AN-015]
title: Gir exposes a bridge clock that can advance before status delivery
provenance: inferred
reversal-cost: low
---

# AN-018 — Gir exposes a bridge clock that can advance before status delivery

## Risk investigated

Whether the Tank Royale-only failure of read-only `ag.Gir_0.99.jar` after the published 1.4.0 upgrade can be avoided by restoring classic time delivery in the bridge.

## Evidence boundary

The investigation ran on Windows on 2026-10-01 with classic Robocode 1.11.1, the published Tank Royale 1.4.0 Bot API and runner, bridge commit `20ad128`, and the official Roborumble setup (35 rounds, 800 by 600, two Gir copies). The Bot API jar had SHA-256 `AB65C4D5CEC1808ADEB71375ADAE6D15341AE250DEF89A6C10FC9DA879DE0752`; the runner had SHA-256 `02F6D1D8E9346A4AEAE1B91E7ABB278C98AC1F1D30C605F867758566F5F327CF`; the read-only Gir jar had SHA-256 `35A4CBABB1C433B6A4E54977741E90195FA95332D7AFCCE0C7FFE9C8AF1CE32D`. The captured failure log is local under `%TEMP%\robocode-bridge-tr140\gir-repro-work\gir-bridge-only-error.log`; it is not committed evidence. The source inspection used the published 1.4.0 Bot API source jar and the Java sources bundled in Gir's jar.

## What was tried

The official regression gate reported one bridge-only Gir error without its signature. A focused rerun captured the exception and stack. Gir's bundled source was read without changing its jar. Twenty further official Gir runs with skipped-turn telemetry enabled completed without this exception. The bridge's time mapping was compared with published Bot API source, then a focused unit test and one official 35-round Gir run exercised a candidate bridge repair.

## What was found

The captured Tank Royale failure was `NullPointerException` at `ag.movement.Movement.createNeuralDataInput` line 498. Gir had consecutive enemy snapshots and detected an energy drop, but its own snapshot for the previous tick was absent. The corresponding classic run had no matching exception. The failure happened once in the original gate and once in the focused rerun; it did not recur in the twenty telemetry-enabled repeats, so the exact trigger remains unproven.

Published Bot API 1.4.0 updates its current tick from the WebSocket handler before the managed robot thread dispatches the tick's events. The bridge's `BotPeer.getTime()` read that live tick directly. Thus a network tick could advance `getTime()` while Gir's `run()` was still working on the preceding iteration, before its next `execute()` and status callback. This is a concrete bridge clock mismatch with classic's delivered-turn model. It is a plausible cause of Gir's missing previous-tick snapshot, but the captured exception alone does not prove that this race occurred in the failing run. Skipped turns alone are insufficient as an explanation: the twenty subsequent runs included skipped-turn events without the exception.

The candidate repair holds robot-visible time at the last status tick delivered by the bridge, with the existing time-zero initial status callback retained. A new `ROUTE-012` unit test advances the supplied Bot API tick before delivering status and verifies that `getTime()` remains at the delivered tick. One official 35-round Gir run after the repair completed without error; because the original error is intermittent, that single pass does not establish its elimination. The full two-engine conformance run completed 45 of 46 tests; `EVT-005` failed once because one start status remained pending at `run()` and passed on an isolated rerun. This observation does not establish whether that one-off start-status result is related to the clock repair.

## Implications for M-006

The bridge should keep the delivered-time repair and expose the exact exception signature in future regression-gate output. A later Gir failure must be compared by normalized signature, round, turn, and any available skipped-turn data before the case can be closed. The official registry's earlier repair-linked Gir pass remains historical evidence for the local matched 1.4.0 pair, not proof that the published pair never fails. The Gir jar remains read-only.
