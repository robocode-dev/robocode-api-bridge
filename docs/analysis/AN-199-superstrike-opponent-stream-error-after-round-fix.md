---
id: AN-199
type: analysis
status: active
links: [P-001, CAP-004, CRIT-004, C-004]
title: SuperStrike now records the opponent stream-limit error in Tank Royale
provenance: inferred
reversal-cost: low
---

# AN-199 — SuperStrike now records the opponent stream-limit error in Tank Royale

## Risk investigated

Whether SuperStrike's historical Classic-only `amk.ChumbaMini.saveData` stream-limit error appears in Tank Royale after the bridge stops releasing unclosed streams at round boundaries.

## Evidence boundary

The read-only subject jar `amk.superstrike.SuperStrike_0.3.jar` has SHA-256 `ef146a488f92e25b5f8f957831fd715bb6a6b2b1f8cf72ffa05bad505c34c79b`; its selected opponent pool includes read-only `amk.ChumbaMini_0.2.jar` with SHA-256 `5770270c23f9223e4df93f5455fcf461ecd30309fc318f3d53cbbdf93e133d6b`. The earlier observation `6d59b712b5397f0e` completed on 2026-10-06 with bridge commit `807f5c9dacaaac72535eb23225d7020e0fa9e35b`; the post-fix observation `c1efcec566203c27` completed on 2026-10-07 with bridge commit `ffb7bff7bbc6023ab34daa407944fd91947ebef4` and bridge API jar SHA-256 `9c138de51e4da4c31d1319086add068c178277d2433923e22ecc458b4eafd8ba`.

The prepared Windows environment used Classic Robocode 1.11.1 on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1, with 10 participants, 35 rounds, and a 1000×1000 arena. The post-fix run used Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Bot API jar SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, runner jar SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`, and wrapper jar SHA-256 `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`.

## What was tried

The pre-fix observation scored 115,524 in Classic with 386 errors and 112,178 in Tank Royale with none, a −2.9% delta. Classic recorded `SecurityException` at `amk.ChumbaMini.saveData`, along with other signatures.

The post-fix official M-006 observation scored 115,807 in Classic with 1,028 errors and 112,433 in Tank Royale with 31 errors, a −2.9% delta. Both sides now record `SecurityException` at `amk.ChumbaMini.saveData`; Tank Royale also reports an unknown-origin signature, while Classic still reports `ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare`. Requested skipped-turn capture completed with eight events, all at round 1, turn 1. The registry row remains `DISCREPANCY (errors)`.

## Finding

This post-fix opponent-pool check confirms that ChumbaMini's five-stream failure now reaches Tank Royale in a battle where SuperStrike is the subject. The score delta remains −2.9%, but the row remains unresolved because Classic still has additional error signatures and its reported error total is much higher. No further code change is indicated by this observation.

## M-006 handoff

Continue in registry order with `meleerumble/ap.Frederick_1.1.jar` (`DISCREPANCY (errors)`), following AN-200's NanoClone retest.
