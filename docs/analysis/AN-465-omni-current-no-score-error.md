---
id: AN-465
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004]
title: Omni's wrapper run-loop error is repaired and retested
provenance: inferred
reversal-cost: low
---

# AN-465 — Omni's wrapper run-loop error is repaired and retested

## Risk investigated

Whether `e32.Omni_0.04.jar`'s no-score outcome persisted under current matched artifacts, and whether its recorded `NoSuchMethodError` came from the bridge wrapper.

## Evidence boundary

The read-only subject jar has SHA-256 `08f753a1256addbc2c54399fdfeee4e29ba61c04aba969886b38cdb842bf3afb`. The repair retest `01b23359332d5475` completed on 2026-10-24 with Classic Robocode 1.11.1, bridge commit `07fb7a886d36e4fe5f1063369aaad7d3e3f7de68`, and local Tank Royale commit `02639872ea90b0d8cc7e487eb89607935d341e9a`. The locally built bridge, wrapper, Bot API, and runner artifacts and their hashes are recorded in `compat-test/parity-registry.json`; the Bot API is 1.4.0. The run used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The current run record does not retain the exact Java executable selected for Classic.

The repair retest produced scores of 4,323 on Classic and 4,369 on Tank Royale, a 1.1% delta, with zero runtime errors on either engine. Skipped-turn telemetry was disabled. The jar remained read-only.

## What was tried

The earlier official retry `2aaa137503d409dc` on 2026-10-09 had no score samples and recorded a bridge-only `NoSuchMethodError` in `e32.Omni.run`. Bytecode inspection confirmed that `e32.Omni` implements the Robocode robot interfaces directly, extends `Object`, and declares no `getEnergy()` method; the wrapper had injected a call to that missing method at the terminal backward branch in `run()`.

Commit `07fb7a8` makes the wrapper use the active `BotPeer` energy for transformed loops in direct `Object` subclasses without their own `getEnergy()` method. The peer is bound to the robot thread while its runnable executes, so the existing bridge energy and round-stop behavior remain available to interface-based robots. The transformed class was inspected and calls `BotPeer.getCurrentRobotEnergy()`; the original jar was not changed.

The official repair retest completed all 35 rounds on both engines. Tank Royale recorded no missing-method error, and the score difference stayed below the harness's 25% discrepancy threshold.

## What was not pursued

The older 2026-09-12 `NullPointerException` trace from `execution.EventAdaptor.onStatus` used Bot API 1.2.0 and older engine artifacts; this repair retest does not explain that separate historical observation. The current result is one official score pair rather than a repeated score study, so it does not establish statistical score parity. No new skipped-turn claim follows because telemetry was disabled.

## Finding

The recorded `NoSuchMethodError` was caused by the wrapper's run-loop rewrite calling a method absent from a valid interface-based robot. After the repair, Omni completed the official Classic and Tank Royale battles without runtime errors and scored 4,323 versus 4,369 (`PASS`, 1.1% delta). The wrapper-owned error is resolved for this robot; the earlier no-score result no longer reproduces.

## M-006 handoff

Continue prioritizing bridge- and wrapper-owned cases, starting with `meleerumble/ak.Fermat_2.0.jar` (`DISCREPANCY (errors)`, `initial-status-before-run`), then the remaining bridge- and wrapper-owned subjects in `compat-test/parity-registry.md`.
