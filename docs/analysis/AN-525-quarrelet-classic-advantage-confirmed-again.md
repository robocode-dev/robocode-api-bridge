---
id: AN-525
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Quarrelet's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-525 — Quarrelet's Classic score advantage is confirmed again

## Risk investigated

Whether `kinsen.nano.Quarrelet_1.0.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `240a6fbec20c1ef3aed660a9afd4d053be3b6be70292dfda9149150bd5acda1a`. The official five-pair confirmation `d2497a7692e897a8` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `da338c14f624a1ffbb6b41d700617c7c7a113576`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 5,480.2 points and Tank Royale averaged 2,921.8 points, for a −46.58% mean delta. Pair deltas were −49.3%, −46.1%, −47.6%, −48.0%, and −41.9%. Neither engine reported errors and no bridge-only signatures were recorded. Skipped-turn telemetry was captured in all five Tank Royale attempts; attempt 2 recorded bot 1 at round 15, turn 276 and round 31, turn 112, while attempt 3 recorded bot 2 at round 31, turn 56. Attempts 1, 4, and 5 recorded none. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `392cc7b5ec68cfab` recorded Classic scores of 5,280 and Tank Royale scores of 2,818, for a −46.6% delta. The current five-pair mean confirms the same Classic advantage at −46.58%, essentially unchanged from the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Quarrelet's Classic score advantage persists under current matched artifacts. The mean delta is −46.58%, effectively unchanged from the earlier −46.6% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/kinsen.nano.Quarrelet_1.0.jar` as `CONFIRMED (score)`. Skip `roborumble/kinsen.nano.Senticous_1.0.jar`, `roborumble/kjc.Karaykan_1.0.jar`, `roborumble/kjc.MailManX_2.0.jar`, and `roborumble/kjc.etc.Dharok_1.0.jar` (`PASS`). The intervening `roborumble/klein.GottesKrieger_1.1.jar` remains `DISCREPANCY (outcome)` and is outside this score-review sequence. Continue with `roborumble/kms.Golden_0.10.jar` (`score-review`).
