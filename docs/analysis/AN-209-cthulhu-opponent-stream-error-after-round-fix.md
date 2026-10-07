---
id: AN-209
type: analysis
status: active
links: [P-001, CAP-004, CRIT-004, C-004, AN-194]
title: Cthulhu retains file-quota errors and now records ChumbaMini's stream limit in Tank Royale
provenance: inferred
reversal-cost: low
---

# AN-209 — Cthulhu retains file-quota errors and now records ChumbaMini's stream limit in Tank Royale

## Risk investigated

Whether Cthulhu's Tank Royale file-quota errors recur and whether the ChumbaMini stream-limit failure now appears after the bridge stopped releasing unclosed streams at round boundaries.

## Evidence boundary

The read-only subject jar `meleerumble/asd.Cthulhu_1.3.jar` has SHA-256 `208de4c048a8b6f619c676f0cc8098b758a6fd42ce06689f5549459d0d542051`; its selected opponent pool includes read-only `amk.ChumbaMini_0.2.jar` with SHA-256 `5770270c23f9223e4df93f5455fcf461ecd30309fc318f3d53cbbdf93e133d6b`. The prior observation `0679565125f26395` completed on 2026-10-06 with bridge commit `4580799690e1806b9ea7afb33a29320968947e44`; the new official M-006 observation `da0129bb6e373bf6` completed on 2026-10-07 with bridge commit `08d66041230305f91fcbadd51d7ef816ccb51d7b` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1, with 10 participants, 35 rounds, and a 1000×1000 arena. Tank Royale used commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API jar SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, runner jar SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, and wrapper jar SHA-256 `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`.

## What was tried

The prior observation scored 114,277 in Classic with 405 errors and 111,106 in Tank Royale with 35 errors, a −2.8% delta; both sides reported `IOException` from the 200,000-byte file quota. The new observation scored 115,128 in Classic with 499 errors and 111,660 in Tank Royale with 66 errors, a −3.0% delta. Tank Royale recorded 36 file-quota `IOException`s and 30 five-open-stream `SecurityException`s; Classic also recorded `SecurityException` at `amk.ChumbaMini.saveData`. Classic additionally reported `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare` and unknown origin. Requested skipped-turn telemetry was captured with an empty event list. The registry row remains `DISCREPANCY (errors)`.

## Finding

The file-quota error is not new: it appeared in the previous observation on both engines. This post-fix run also confirms that ChumbaMini's five-stream failure reaches Tank Royale in a battle where Cthulhu is the subject. Classic retains additional array-bounds failures and a much higher error count, so the row remains unresolved. The score delta is inside the 25% review threshold. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/awesomeness.Elite_1.0.jar` (`DISCREPANCY (errors)`).
