---
id: AN-145
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: GhostShell GT's repeated no-score run has robot-owned unchecked state
provenance: inferred
reversal-cost: low
---

# AN-145 — GhostShell GT's repeated no-score run has robot-owned unchecked state

## Risk investigated

Whether GhostShell GT's historical Tank Royale no-score outcome recurs with current matched artifacts, and whether its repeated exceptions identify the owning code.

## Evidence boundary

The read-only subject jar `cw.megas.GhostShell_GT.jar` has SHA-256 `f75b19028ba3d181ae70d87a380370cf53e535b49e87f9060069a8591bc5888a`. The current official observation `c279e9c8a178d0c0` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `461197b544a745c035b1dc8ca40195369f12c535`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

Classic scored 8,783 with 588 recorded errors. Tank Royale produced no score and 450 recorded errors; telemetry was incomplete because its worker ended without a result. Both engines reported `NullPointerException` origins in `cw.megas.GhostShell.chooseLocation` and `cw.megas.GhostShell.doMovement`. Tank Royale also reported `StringIndexOutOfBoundsException` from `cw.megas.GhostShell.patternGun`. The registry status is `DISCREPANCY (outcome)`.

Earlier observation `cebc69d8bb954e71` also had no Tank Royale score, with 560 Classic errors and 582 Tank Royale errors; its Tank Royale origins included the same `chooseLocation` and `doMovement` methods. The first observation `624c2e5957e9c806` scored in both engines but recorded hundreds of errors on each.

## Finding

The bundled source leaves three relevant accesses unchecked: `chooseLocation()` dereferences the wave returned by `getClosestSurfableWave()` without confirming it exists; `doMovement()` passes `precisePredictionDestionation` to `Rectangle2D.contains()` before ensuring that destination has been set; and `patternGun()` reads backward from a pattern match with `eLog.charAt(indX--)` without checking the string boundary. These source paths match the current exception methods and messages. The registry diagnoses all three as robot-owned: `robot-unguarded-empty-wave-candidates`, `robot-null-predicted-destination-in-do-movement`, and `robot-enemy-pattern-reads-past-final-sample`. The current Classic run also reaches the two NPE paths and completes with a score; the evidence identifies unchecked robot state but does not isolate why the Tank Royale worker ends without a result. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cw.megas.Polar_3.2.jar` (`DISCREPANCY (outcome)`).
