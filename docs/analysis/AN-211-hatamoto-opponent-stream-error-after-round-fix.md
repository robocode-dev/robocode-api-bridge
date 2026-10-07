---
id: AN-211
type: analysis
status: active
links: [P-001, CAP-004, CRIT-004, C-004, AN-194]
title: HataMoto now records ChumbaMini's stream-limit failure in Tank Royale
provenance: inferred
reversal-cost: low
---

# AN-211 — HataMoto now records ChumbaMini's stream-limit failure in Tank Royale

## Risk investigated

Whether HataMoto's historical Classic-only ChumbaMini stream-limit failure appears in Tank Royale after the bridge stopped releasing unclosed streams at round boundaries.

## Evidence boundary

The read-only subject jar `meleerumble/axeBots.HataMoto_3.09.jar` has SHA-256 `293a92846b40195a5de25c7379f72e0dcb08297f79d2b6fffd7d3a95b50afb2c`; its selected opponent pool includes read-only `amk.ChumbaMini_0.2.jar` with SHA-256 `5770270c23f9223e4df93f5455fcf461ecd30309fc318f3d53cbbdf93e133d6b`. The prior observation `30002d21cb0e30ae` completed on 2026-10-06 with bridge commit `06be2efa9742916e870916b4920d86243530a14d`; the new official M-006 observation `97b04c576f4c4caa` completed on 2026-10-07 with bridge commit `2de272a1b379354ba97d85e76d91c9409b45c39e` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1, with 10 participants, 35 rounds, and a 1000×1000 arena. Tank Royale used commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API jar SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, runner jar SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, and wrapper jar SHA-256 `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`.

## What was tried

The prior observation scored 114,495 in Classic with 796 errors and 110,962 in Tank Royale with none, a −3.1% delta. The new observation scored 114,356 in Classic with 536 errors and 112,139 in Tank Royale with 30 errors, a −1.9% delta. Classic recorded `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`, along with unknown-origin signatures. Tank Royale recorded `SecurityException` at `amk.ChumbaMini.saveData` and an unknown-origin `SecurityException`. Requested skipped-turn telemetry was marked `unavailable` with `events: null`, so this run provides no telemetry event count. The registry row remains `DISCREPANCY (errors)`.

## Finding

This post-fix opponent-pool check confirms that ChumbaMini's five-stream failure now reaches Tank Royale in a battle where HataMoto is the subject. Classic retains additional array-bounds failures and a much higher error count, so the row remains unresolved. The score delta is inside the 25% review threshold. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/az.Ololobot_0.2.4.jar` (`DISCREPANCY (errors)`).
