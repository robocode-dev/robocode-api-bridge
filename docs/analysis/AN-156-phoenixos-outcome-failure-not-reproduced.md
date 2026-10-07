---
id: AN-156
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: PhoenixOS's historical Tank Royale outcome failure does not recur
provenance: inferred
reversal-cost: low
---

# AN-156 — PhoenixOS's historical Tank Royale outcome failure does not recur

## Risk investigated

Whether PhoenixOS's historical Tank Royale no-score outcome recurs with current matched artifacts, and whether its logged null-gun errors recur.

## Evidence boundary

The read-only subject jar `davidalves.PhoenixOS_1.1.jar` has SHA-256 `fb4b5b7b628d818780a81818b30de9ae2cf10f54e66f6fbe6111dbb62b38a4c8`. The current official observation `f47d4c3f053cdf86` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `9b1f7c504888dd9a00adaf486f15b7576b9c113e`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

The current official pair scored 4,818 in Classic and 4,253 in Tank Royale, a −11.7% delta. Neither engine reported runtime errors, and the registry status is `PASS`. Tank Royale captured one skipped-turn event for bot 2 at round 1, turn 1; the event does not identify why the robot missed that turn.

Earlier observations `8f13036b4836dcbf` and `d37fc2d466f2354b` had no Tank Royale score and 3 and 6 errors, respectively. The logs included null `GuessFactorGun` receiver errors from `davidalves.net.gun` methods. Classic completed without errors in both observations. The current run did not reproduce those outcome failures or errors.

## Finding

PhoenixOS completes both current engine runs with no runtime errors, and its historical Tank Royale no-score outcome does not recur with current matched artifacts. One skipped-turn event was captured; its cause remains unassigned. The older null-gun errors also remain unexplained by this observation. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/davidalves.net.DuelistMicroMkII_1.1.jar` (`score-review`).
