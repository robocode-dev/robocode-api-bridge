---
id: AN-252
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Okami's Classic-only missing-history-file errors persist with current artifacts
provenance: inferred
reversal-cost: low
---

# AN-252 — Okami's Classic-only missing-history-file errors persist with current artifacts

## Risk investigated

Whether `axeBots.Okami_1.04.jar`'s Classic-only file-read errors recur with the latest matched artifacts and whether the current pair identifies a bridge defect.

## Evidence boundary

The read-only subject jar has SHA-256 `608f42ae73765a352e9034e41f36af721cd745835a19a20f8fe7e8bbe3be30fa`. The official observation `eebba33f96db75a8` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `c01ce6d81756fa01f381215440f3fba0ea51b2e3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic scored 5,150 and reported two `IOException trying to read: java.lang.NullPointerException: name can't be null` messages. Tank Royale scored 5,322 with zero errors, for a +3.3% delta. The Tank Royale skipped-turn telemetry was unavailable. The registry status remains `DISCREPANCY (errors)`.

This reproduces the two Classic-only messages from observation `abb124f1b2d515b0` under the newer artifact pair. AN-112's source inspection found that `AxeFiles.findFile()` returns null when no history file matches and that both `SegmentedGFs.load()` and `FlatPilot.load()` pass that result to `FileInputStream` without a null check. The current log does not identify which reader produced either message. AN-112 also found that post-run directory contents could not establish why the lookup differed between engines.

## Finding

Okami's score remains within the review threshold, and its Classic-only file-read errors persist with the latest matched artifacts. The bundled robot source explains how a missing history file becomes the caught null-name message, but the available evidence still does not explain the engine-specific file availability. Keep the recorded `robot-unchecked-null-history-file` cause and the error discrepancy open; this observation does not identify a bridge defect or indicate a code or rumble-jar change.

## M-006 handoff

Skip the next registry row, `roborumble/az.Ololobot_0.2.4.jar`, because it remains `PASS`. Continue with `roborumble/bayen.UbaRamLT_1.0.jar` (`score-review`).
