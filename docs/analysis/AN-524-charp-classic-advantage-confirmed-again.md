---
id: AN-524
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-002, C-004]
title: Charp's Classic score advantage is confirmed again
provenance: inferred
reversal-cost: low
---

# AN-524 — Charp's Classic score advantage is confirmed again

## Risk investigated

Whether `kinsen.nano.Charp_1.0.jar`'s historical Classic score advantage persists under current matched artifacts, and whether its magnitude has changed.

## Evidence boundary

The read-only subject jar has SHA-256 `26378bf73f2975afbbe525735b328b63bf566b8d9e71a0afea9dcdbdc50f76b4`. The official five-pair confirmation `c2b1d590dc56f4eb` completed on 2026-10-10 with Classic Robocode 1.11.1, bridge commit `da338c14f624a1ffbb6b41d700617c7c7a113576`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`; the current Bot API is 1.4.0. The earlier 2026-09-12 observation used Bot API 1.2.0 and different bridge and Tank Royale commits. Both records used two participants, 35 rounds, and an 800×600 arena; the current run was prepared in Windows PowerShell and does not demonstrate clean-checkout reproducibility. The current run record does not preserve the exact Java executable selected for Classic.

All five current attempts produced samples; none were excluded. Classic averaged 4,858.2 points and Tank Royale averaged 2,721.6 points, for a −43.98% mean delta. Pair deltas were −43.8%, −43.6%, −44.7%, −42.0%, and −45.8%. Neither engine reported errors, no bridge-only signatures were recorded, and skipped-turn telemetry was captured with zero events in all five Tank Royale attempts. The registry status is `CONFIRMED (score)`.

## What was tried

The earlier observation `16fb9f8a0566af3d` recorded Classic scores of 4,823 and Tank Royale scores of 2,669, for a −44.7% delta. The current five-pair mean confirms the same Classic advantage at −43.98%, nearly unchanged from the earlier observation.

## What was not pursued

The current run confirms that the score difference persists but does not explain its cause. No controlled trace or source comparison was made, and the gap was not attributed to either engine or the bridge. No code or rumble-jar change was made.

## Finding

Charp's Classic score advantage persists under current matched artifacts. The mean delta is −43.98%, close to the earlier −44.7% observation, and the registry classifies the result as `CONFIRMED (score)`. The cause of the score difference remains unknown.

## M-006 handoff

Record `roborumble/kinsen.nano.Charp_1.0.jar` as `CONFIRMED (score)`. Skip `roborumble/kinsen.nano.Hoplomachy_1.6.jar` (`PASS`). Record `roborumble/kinsen.nano.Quarrelet_1.0.jar` as `CONFIRMED (score)`. Skip `roborumble/kinsen.nano.Senticous_1.0.jar`, `roborumble/kjc.Karaykan_1.0.jar`, `roborumble/kjc.MailManX_2.0.jar`, and `roborumble/kjc.etc.Dharok_1.0.jar` (`PASS`). The intervening `roborumble/klein.GottesKrieger_1.1.jar` remains `DISCREPANCY (outcome)` and is outside this score-review sequence. Continue with `roborumble/kms.Golden_0.10.jar` (`score-review`).
