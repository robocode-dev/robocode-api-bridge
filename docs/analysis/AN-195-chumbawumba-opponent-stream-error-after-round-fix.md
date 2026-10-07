---
id: AN-195
type: analysis
status: active
links: [P-001, CAP-004, CRIT-004, C-004]
title: ChumbaWumba now records the opponent stream-limit error in Tank Royale
provenance: inferred
reversal-cost: low
---

# AN-195 — ChumbaWumba now records the opponent stream-limit error in Tank Royale

## Risk investigated

Whether ChumbaWumba's historical Classic-only `amk.ChumbaMini.saveData` stream-limit error appears in Tank Royale after the bridge stops releasing unclosed streams at round boundaries.

## Evidence boundary

The read-only subject jar `amk.ChumbaWumba_0.3.jar` has SHA-256 `f7594cdfa02419ae112013ec923387736a89840712162020ecb899e39d6127e9`; its selected opponent pool includes read-only `amk.ChumbaMini_0.2.jar` with SHA-256 `5770270c23f9223e4df93f5455fcf461ecd30309fc318f3d53cbbdf93e133d6b`. The earlier observation `1323d63a4492d748` completed on 2026-09-28 with bridge commit `e663103706b048ae53434b2071f7b8c62b45d757`; the post-fix observation `c2c9ad1070d4caa6` completed on 2026-10-07 with bridge commit `3b649dc1891dd465b320179663e01bfb065bd513` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1, with 10 participants, 35 rounds, and a 1000×1000 arena. The post-fix run used Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API jar SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, runner jar SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, and wrapper jar SHA-256 `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`.

## What was tried

The pre-fix observation scored 114,750 in Classic with 220 errors and 113,151 in Tank Royale with none, a −1.4% delta. Classic recorded `SecurityException` at `amk.ChumbaMini.saveData`, along with other signatures.

The post-fix official M-006 observation scored 114,511 in Classic with 548 errors and 114,095 in Tank Royale with 29 errors, a −0.4% delta. Both sides now record `SecurityException` at `amk.ChumbaMini.saveData`; Tank Royale also reports an unknown-origin signature, while Classic still reports `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare`. Requested skipped-turn capture completed with one event at bot 10, round 1, turn 1. The registry row remains `DISCREPANCY (errors)`.

## Finding

This post-fix opponent-pool check confirms that ChumbaMini's five-stream failure now reaches Tank Royale in a battle where ChumbaWumba is the subject. The score delta is −0.4%, but the row remains unresolved because Classic still has additional error signatures and its reported error total is much higher. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/amk.ShizzleStiX.ShizzleStiX_0.6.jar` (`DISCREPANCY (errors)`), following AN-196's Punbot retest.
