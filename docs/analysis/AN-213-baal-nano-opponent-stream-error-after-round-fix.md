---
id: AN-213
type: analysis
status: active
links: [P-001, CAP-004, CRIT-004, C-004, AN-194]
title: Baal.nano.N now records ChumbaMini's stream-limit failure in Tank Royale
provenance: inferred
reversal-cost: low
---

# AN-213 — Baal.nano.N now records ChumbaMini's stream-limit failure in Tank Royale

## Risk investigated

Whether Baal.nano.N's historical Classic-only ChumbaMini stream-limit failure appears in Tank Royale after the bridge stopped releasing unclosed streams at round boundaries.

## Evidence boundary

The read-only subject jar `meleerumble/baal.nano.N_1.42.jar` has SHA-256 `a653da798755fcbfe9b62af99b7dfaa37bef7495dc2dc19d908e97e95693bee0`; its selected opponent pool includes read-only `amk.ChumbaMini_0.2.jar` with SHA-256 `5770270c23f9223e4df93f5455fcf461ecd30309fc318f3d53cbbdf93e133d6b`. The prior observation `981b7fc2f6749d5c` completed on 2026-10-06 with bridge commit `e9cf194d6f6d3f755b2d5d290f82f95bae004ed2`; the new official M-006 observation `ca2b3b3a8bd537d6` completed on 2026-10-07 with bridge commit `a238d64a320e5ab7c45b7f94d6c0e00ed9c4f851` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1, with 10 participants, 35 rounds, and a 1000×1000 arena. Tank Royale used commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API jar SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, runner jar SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, and wrapper jar SHA-256 `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`.

## What was tried

The prior observation scored 114,280 in Classic with 476 errors and 112,395 in Tank Royale with none, a −1.6% delta. The new observation scored 113,499 in Classic with 292 errors and 113,483 in Tank Royale with 30 errors, a −0.0% delta. Classic recorded `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`, along with unknown-origin signatures. Tank Royale recorded `SecurityException` at `amk.ChumbaMini.saveData` and an unknown-origin `SecurityException`. Requested skipped-turn telemetry was captured with six events for bots 2, 3, 4, 7, 9, and 10, all in round 1 at turn 1. The registry row remains `DISCREPANCY (errors)`.

## Finding

This post-fix opponent-pool check confirms that ChumbaMini's five-stream failure now reaches Tank Royale in a battle where Baal.nano.N is the subject. Classic still reports additional array-bounds failures and a higher error count, so the row remains unresolved despite the nearly equal scores. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/bayen.UbaMicro_1.4.jar` (`DISCREPANCY (errors)`).
