---
id: AN-144
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Blade's repeated pattern-gun index error is robot-owned
provenance: inferred
reversal-cost: low
---

# AN-144 — Blade's repeated pattern-gun index error is robot-owned

## Risk investigated

Whether Blade's historical errors recur with current matched artifacts, and whether the no-score outcome is caused by the bridge or the read-only robot.

## Evidence boundary

The read-only subject jar `cw.megas.Blade_0.8.jar` has SHA-256 `4a50333c16fd0bdc02aca8c152e7017ede5c8e763ceaba42319867a5b256f830`. The current official observation `c83f4cf3396ce737` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `1ffc8781588fad8dc0e905a9217f34c9fd492a7e`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The current Classic run scored 6,041 with no errors. Tank Royale produced no score and recorded 10 errors, including nine `StringIndexOutOfBoundsException` messages and a worker-without-result entry. The exception signatures include origin `cw.megas.Blade.gun`; skipped-turn telemetry was incomplete because the worker ended without a result. The registry diagnosis is `robot-enemy-pattern-reads-past-final-sample`, owner `robot`.

The earlier observation `8fd749571b62d83f` had no Classic errors and 30 Tank Royale errors, including the same exception type with indices exceeding the reported string length; its Tank Royale stack origin was unavailable. The later observation `4aaeb14f0d7fe7cd` had 46 Classic `StringIndexOutOfBoundsException` errors from `cw.megas.Blade.gun`, while Tank Royale scored 5,647 without errors.

## Finding

The bundled `gun()` source builds a string containing enemy movement samples, searches it for a pattern, then reads backward with `charAt(indX--)` without checking the string boundary. The current Tank Royale exception names this method, and the older Classic log names the same method. This establishes an unchecked robot-owned pattern index path; the current Classic run did not trigger it, while the current Tank Royale run did. The available evidence does not show a bridge defect. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cw.megas.GhostShell_GT.jar` (`DISCREPANCY (outcome)`).
