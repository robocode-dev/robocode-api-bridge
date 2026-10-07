---
id: AN-231
type: analysis
status: active
links: [P-001, CAP-004, CRIT-004, C-004, AN-194]
title: Insomnia now records ChumbaMini's stream-limit failure in Tank Royale
provenance: inferred
reversal-cost: low
---

# AN-231 — Insomnia now records ChumbaMini's stream-limit failure in Tank Royale

## Risk investigated

Whether Insomnia's historical Classic-only ChumbaMini stream-limit failure appears in Tank Royale after the bridge stopped releasing unclosed streams at round boundaries.

## Evidence boundary

The read-only subject jar `meleerumble/cb.nano.Insomnia_1.0.jar` has SHA-256 `dfaa0b7522185985faaaad2c8c8761b56ed6b2e31f9d256de0abbcf466863d30`; its selected opponent pool includes read-only `amk.ChumbaMini_0.2.jar` with SHA-256 `5770270c23f9223e4df93f5455fcf461ecd30309fc318f3d53cbbdf93e133d6b`. The prior observation `dc104bbcc0f75283` completed on 2026-10-06 with bridge commit `0f38b609c0151022835bfe672d58ba112a9fc10e`; the new official M-006 observation `84e586ddb595dfd0` completed on 2026-10-07 with bridge commit `9dc61cad40e61fb168116dcc8dc7fd18c7942794` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1, with 10 participants, 35 rounds, and a 1000×1000 arena. Tank Royale used commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API jar SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, runner jar SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, and wrapper jar SHA-256 `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`.

## What was tried

The prior observation scored 114,160 in Classic with 738 errors and 112,265 in Tank Royale with none, a −1.7% delta. The new observation scored 114,474 in Classic with 376 errors and 111,762 in Tank Royale with 30 errors, a −2.4% delta. Classic recorded `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and `SecurityException` at `amk.ChumbaMini.saveData`, along with unknown-origin signatures. Tank Royale recorded `SecurityException` at `amk.ChumbaMini.saveData` and an unknown-origin `SecurityException`. Requested skipped-turn telemetry was captured with three events for bots 1, 8, and 9, all in round 1 at turn 1. The registry row remains `DISCREPANCY (errors)`.

## Finding

This post-fix opponent-pool check confirms that ChumbaMini's five-stream failure now reaches Tank Royale in a battle where Insomnia is the subject. Classic still reports additional array-bounds failures and a much higher error count, so the row remains unresolved. The score delta is inside the 25% review threshold. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/cf.RiO.RiOx_4.2.1.jar` (`DISCREPANCY (errors)`).
