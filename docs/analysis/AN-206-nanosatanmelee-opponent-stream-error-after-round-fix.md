---
id: AN-206
type: analysis
status: active
links: [P-001, CAP-004, CRIT-004, C-004, AN-194]
title: NanoSatanMelee now records ChumbaMini's stream-limit failure in Tank Royale
provenance: inferred
reversal-cost: low
---

# AN-206 — NanoSatanMelee now records ChumbaMini's stream-limit failure in Tank Royale

## Risk investigated

Whether NanoSatanMelee's historical Classic-only ChumbaMini stream-limit failure appears in Tank Royale after the bridge stopped releasing unclosed streams at round boundaries.

## Evidence boundary

The read-only subject jar `meleerumble/arthord.NanoSatanMelee_Beta.jar` has SHA-256 `a0737bc6d25f6b89618c119a6df62ae89a44d9a2a7d662056dddefdde04f37fe`; its selected opponent pool includes read-only `amk.ChumbaMini_0.2.jar` with SHA-256 `5770270c23f9223e4df93f5455fcf461ecd30309fc318f3d53cbbdf93e133d6b`. The prior observation `d338a164bbc2e0a1` completed on 2026-10-06 with bridge commit `274031ddd6491bec5d3fb7221f539bc201185bbf`; the new official M-006 observation `d923bb9c0f620792` completed on 2026-10-07 with bridge commit `7c30a6c1549d5db8554c9b4e309963afc97bb013` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1, with 10 participants, 35 rounds, and a 1000×1000 arena. Tank Royale used commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API jar SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, runner jar SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, and wrapper jar SHA-256 `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`.

## What was tried

The prior observation scored 114,440 in Classic with 92 errors and 111,829 in Tank Royale with none, a −2.3% delta. The new observation scored 114,975 in Classic with 374 errors and 112,106 in Tank Royale with 30 errors, a −2.5% delta. Classic recorded `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`, along with unknown-origin signatures. Tank Royale recorded `SecurityException` at `amk.ChumbaMini.saveData` and an unknown-origin `SecurityException`. Requested skipped-turn telemetry was captured with five events for bots 1, 4, 5, 7, and 10, all in round 1 at turn 1. The registry row remains `DISCREPANCY (errors)`.

## Finding

This post-fix opponent-pool check confirms that ChumbaMini's five-stream failure now reaches Tank Royale in a battle where NanoSatanMelee is the subject. Classic still has additional array-bounds failures and a much higher reported error count, so the row remains unresolved. The score delta is inside the 25% review threshold. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/ary.Swarm_1.1.jar` (`DISCREPANCY (errors)`).
