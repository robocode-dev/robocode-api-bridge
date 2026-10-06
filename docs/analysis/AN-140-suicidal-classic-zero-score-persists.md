---
id: AN-140
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Suicidal's Classic zero-score result persists with no runtime errors
provenance: inferred
reversal-cost: low
---

# AN-140 — Suicidal's Classic zero-score result persists with no runtime errors

## Risk investigated

Whether conscience.Suicidal's historical Classic no-score result recurs with current matched artifacts, and what the bundled robot source reveals about its behavior.

## Evidence boundary

The read-only subject jar `conscience.Suicidal_1.1.jar` has SHA-256 `4650b8e8b5705e0fafb87fb4ffdcda77597f168c7c1c9272abae80691ad4f3d6`. The current official observation `35837038671a2a83` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `3f8554886073bebdb01169c621e2affd371ce073`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Classic scored 0 and Tank Royale scored 2,100. Neither engine reported errors, and Tank Royale captured an empty skipped-turn event list. The registry status is `DISCREPANCY (no score)`.

Earlier observations `126c32b41d68f5f3` and `a392eb662de14448` also scored 0 in Classic and 2,100 and 2,080 in Tank Royale, respectively, with no errors. The jar bundles Java source. `run()` sets body, gun, and radar turns to positive infinity and then repeatedly calls `fire(3)` without calling `execute()`. This unusual command pattern is established by the source; its connection to the Classic zero score is not established by these observations.

## Finding

Suicidal's Classic zero-score outcome is stable across the current and historical measurements, while Tank Royale scores around 2,100. The bundled source shows a robot-specific command pattern that may contribute, but does not prove the reason the engines score it differently. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next unresolved subject, `roborumble/cre.Karolos_0.32.jar` (`DISCREPANCY (outcome)`).
