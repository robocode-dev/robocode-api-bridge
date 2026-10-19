---
id: AN-703
type: analysis
status: active
links: [P-001, PDR-001, AN-015, C-007]
title: Five new MeleeRumble cases reproduce known opponent-pool errors
provenance: inferred
reversal-cost: low
---

# AN-703 — Five new MeleeRumble cases reproduce known opponent-pool errors

## Risk investigated

Whether five newly measured official MeleeRumble cases contain subject-owned bridge discrepancies or only the already identified failures in the fixed opponent pool.

## Evidence boundary

The read-only cases were run once each with official parameters on 2026-10-19: 35 rounds, ten participants, and a 1000×1000 arena. The observations pin bridge commit `864cb7f095d0d2d3c342da154ec6f808aba8a4bf`, Tank Royale commit `860aa075445ccf0a257310001aa6d4983053e56e`, and Classic Robocode `1.11.1`; each registry entry also pins its jar SHA-256 and complete artifact manifest. The local environment is Windows with Temurin JDK 25.0.1 and the prepared Classic, Tank Royale, and LiteRumble installations. No rumble jar was changed.

## What was tried

| Subject | Observation | Classic score / errors | Tank Royale score / errors | Score delta |
|---|---|---:|---:|---:|
| `dcs.Eater_of_Worlds_1.1.3-A.jar` | `ea5b525ec25bdc13` | 114,954 / 424 | 114,117 / 30 | −0.7% |
| `de.simpleworks.robocode.bots.swiBot_1.0.jar` | `b565fd3d5b47dab0` | 115,943 / 874 | 113,872 / 30 | −1.8% |
| `de.simpleworks.robocode.bots.swiBot_1.1.jar` | `3822e80155fbc0f2` | 115,582 / 238 | 112,562 / 30 | −2.6% |
| `deewiant.Anomaly_0.2.jar` | `37e73588fd8db1d3` | 113,942 / 474 | 113,937 / 30 | −0.0% |
| `dft.Calliope_5.6.jar` | `20692844329cb18c` | 115,582 / 374 | 111,500 / 30 | −3.5% |

All five Classic observations include `ArrayIndexOutOfBoundsException` from `amk.guns.Aristocles.prepare` and `SecurityException` from `amk.ChumbaMini.saveData`; both origins map to jars in the pinned opponent pool, as documented in AN-015. Tank Royale records 30 `SecurityException` events from `amk.ChumbaMini.saveData` in each case. No measured-subject error signature appears in either engine.

## Finding

The five cases reproduce the known opponent-pool contamination. The one-run score deltas are all within the registry's 25% threshold, and the runs contain asymmetric opponent errors, so these values do not confirm subject score gaps or clean parity. The registry records `melee-opponent-pool-contamination`, owner `harness`, for all five observations.

Two interpretations were rejected: treating each measured subject as the source of the errors despite their frames belonging to pinned opponents, and treating near-equal scores as `PASS` while the official battles still contain asymmetric errors. The cases remain `DISCREPANCY (errors)` until the fixture contamination is addressed or an explicitly revised measurement can isolate the subjects.

## Implications for M-006

These rows add five pinned cases to collection coverage and diagnose their current error signatures, but they do not close subject-level parity. No bridge behavior changed, so there is no CHANGELOG entry.

## M-006 handoff

Continue with the next unregistered MeleeRumble jars; retain these observations and the harness-owned diagnosis rather than treating the score deltas as bridge regressions.
