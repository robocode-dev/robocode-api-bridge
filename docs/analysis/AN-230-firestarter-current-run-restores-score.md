---
id: AN-230
type: analysis
status: active
links: [P-001, CAP-004, CRIT-004, C-004, AN-015, AN-080, AN-129, AN-194]
title: Firestarter's no-score outcome changes to a score with a remaining error discrepancy
provenance: inferred
reversal-cost: low
---

# AN-230 — Firestarter's no-score outcome changes to a score with a remaining error discrepancy

## Risk investigated

Whether the current `meleerumble/cb.fire.Firestarter_2.0f.jar` no-score outcome persists on the current bridge and whether the current errors identify a new bridge defect.

## Evidence boundary

The read-only subject jar has SHA-256 `de45410b59fea6b8fad9463ece161e1e1656960129f1b9bc84791137d788325f`. The prior current-artifact M-006 observation `003a8921f174ee81` completed on 2026-10-06 with bridge commit `86bd405e279bae95c3c1d093db2186252535f4d8` and produced no Tank Royale score; the new official observation `cd98c79f3f81b699` completed on 2026-10-07 with bridge commit `9dc61cad40e61fb168116dcc8dc7fd18c7942794` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1, with 10 participants, 35 rounds, and a 1000×1000 arena. Tank Royale used commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API jar SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, runner jar SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, and wrapper jar SHA-256 `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`.

## What was tried

The prior M-006 observation scored 114,365 in Classic with 204 errors and produced no Tank Royale score after two `ConcurrentModificationException`s at `amk.ShizzleStiX.Navigator.run`. The new observation scored 114,915 in Classic with 299 errors and 111,671 in Tank Royale with 30 errors, a −2.8% delta; the registry status changed from `DISCREPANCY (outcome)` to `DISCREPANCY (errors)`. Classic recorded `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare`, `SecurityException` at `amk.ChumbaMini.saveData`, and `ConcurrentModificationException` at `amk.ShizzleStiX.Navigator.run`. Tank Royale recorded `SecurityException` at `amk.ChumbaMini.saveData` and an unknown-origin `SecurityException`. Requested skipped-turn telemetry was captured with six events for bots 2, 3, 7, 8, 9, and 10, all in round 1 at turn 1.

## Finding

The latest M-006 no-score outcome does not recur. The score delta is inside the 25% review threshold, but the row remains `DISCREPANCY (errors)` because Tank Royale reports the ChumbaMini stream-limit error and Classic has additional signatures and a higher error total. The previous Tank Royale ShizzleStiX failure is now recorded on Classic; this run does not identify a new bridge defect. No further code change is indicated.

## M-006 handoff

Continue in registry order with `meleerumble/cb.nano.Insomnia_1.0.jar` (`DISCREPANCY (errors)`).
