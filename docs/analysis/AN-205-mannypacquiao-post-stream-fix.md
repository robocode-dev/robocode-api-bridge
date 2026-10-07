---
id: AN-205
type: analysis
status: active
links: [P-001, CAP-004, CRIT-004, C-004, AN-055, AN-194]
title: MannyPacquiao retains Classic loop errors while the stream fix reaches Tank Royale
provenance: inferred
reversal-cost: low
---

# AN-205 — MannyPacquiao retains Classic loop errors while the stream fix reaches Tank Royale

## Risk investigated

Whether MannyPacquiao's Classic-only terminal-loop errors recur after the wrapper repair and whether ChumbaMini's five-stream failure now appears in Tank Royale after the round-boundary cleanup fix.

## Evidence boundary

The read-only subject jar `meleerumble/arthord.MannyPacquiao_Beta.jar` has SHA-256 `4bffcd9f4553a556fb6d136ffcf958755baf2050b0673d4c8c88f119fa45d257`. Its selected opponent pool includes read-only `amk.ChumbaMini_0.2.jar` with SHA-256 `5770270c23f9223e4df93f5455fcf461ecd30309fc318f3d53cbbdf93e133d6b`. The prior observation `3fe413f54fb12c10` completed on 2026-10-06; the new official M-006 observation `f4ffedbce4a6d5d5` completed on 2026-10-07 with bridge commit `7c30a6c1549d5db8554c9b4e309963afc97bb013` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1, with 10 participants, 35 rounds, and a 1000×1000 arena. Tank Royale used commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API jar SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, runner jar SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, and wrapper jar SHA-256 `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`.

## What was tried

The prior observation scored 114,624 in Classic with 554 errors and 112,721 in Tank Royale with none, a −1.7% delta. The new observation scored 114,031 in Classic with 455 errors and 112,316 in Tank Royale with 31 errors, a −1.5% delta. Classic still recorded the force-stop warning and `robocode.exception.RobotException` at `arthord.MannyPacquiao.run`; Tank Royale recorded `SecurityException` at `amk.ChumbaMini.saveData` and an unknown-origin `SecurityException`. Requested skipped-turn telemetry was captured with 821 events for bot 7 across rounds 2–31 and turns 1–130. The registry row remains `DISCREPANCY (errors)`.

## Finding

The Classic-only MannyPacquiao loop exception matches the force-stop behavior already documented in AN-055; this run does not identify a new bridge defect. The Tank Royale ChumbaMini stream-limit signature confirms that the round-boundary cleanup repair also takes effect in this opponent-pool battle. The score delta remains inside the 25% review threshold, while the error discrepancy remains unresolved. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/arthord.NanoSatanMelee_Beta.jar` (`DISCREPANCY (errors)`), using observation `f4ffedbce4a6d5d5` as the preceding MannyPacquiao retest.
