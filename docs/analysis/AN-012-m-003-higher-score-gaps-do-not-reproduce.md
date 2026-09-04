---
id: AN-012
type: analysis
status: active
links: [P-001, CAP-005, CAP-002, CAP-007, C-003, C-004, AN-001, AN-002, AN-011]
title: M-003 finds one bridge-only lifecycle failure and no reproducible higher-score gap in the other two candidates
provenance: inferred
reversal-cost: low
---

# AN-012 — M-003 finds one bridge-only lifecycle failure and no reproducible higher-score gap in the other two candidates

## What was investigated

Whether the three bots selected by the stale compatibility report as materially higher under Tank Royale — ScalarR, Ar1, and BasicSurfer — still diverge under the current bridge at official `roborumble` parameters, and whether each result can be given a named cause or a within-band disposition.

## Evidence boundary

The spike ran on 2026-09-04 in a prepared Windows environment using PowerShell 7.6.5, Python 3.14.2, OpenJDK 24.0.2 for the surrounding process, the bridge repository at `d1dfcb9a4cd0f0b23f2e7d2bbea2c075427d7a21`, and the Tank Royale repository at `b79f256ea4c6f3bf2f309a5600d44f972dde63da`. The classic worker auto-selected the separately installed JDK 17 required by classic Robocode's SecurityManager.

The classic side used the installation at `C:\robocode` and the Tank Royale side used the local runner and Bot API builds from `C:\Code\tank-royale`. The bridge API jar SHA-256 was `D5C0D65C4F88DF2169E0F5FEE9C4781998B5823653E96906CB4B27DE0B8ECCAF`, the wrapper jar SHA-256 was `A68A12532551C17117D6EEBDD716560C92263EC2A336328D4C6B02CB8EBA1B47`, the runner jar SHA-256 was `2D3838F3BCAC4D73104D9D3C9B1EE98E54E5A0B8BDE7FBD8324FDBBA8B3719BB`, and the Bot API was the matched local 1.2.0 build.

The population was the three higher-score rows named by the prior report and pinned in `compat-test/regression-set.json`: `aaa.r.ScalarR_0.005h.053.jar`, `adt.Ar1_2.1.jar`, and `And.BasicSurfer_FF1.6.jar`. Each pair was robot-versus-itself on classic and Tank Royale. The official setup was 800×600, 35 rounds, and two participants. No `--rounds` override was used.

Scores are stochastic and Tank Royale has no deterministic battle seed, so repeated deltas establish the presence or absence of a gross current divergence for these candidates, not exact cross-engine replay equivalence or population-wide quality. A Tank-Royale-only exception is categorical; the harness stopped that battle rather than inventing a score.

## What was found

| Bot | Comparable samples | Classic mean | Tank Royale mean | Per-pair deltas | Disposition |
|---|---:|---:|---:|---|---|
| `aaa.r.ScalarR_0.005h.053.jar` | 0 of 1 | 4,130 | — | — | Bridge-only lifecycle/tick-boundary failure |
| `adt.Ar1_2.1.jar` | 5 of 5 | 3,888.0 | 3,587.4 | −21.7%, +4.0%, −14.8%, −4.8%, −0.9% | Within ±25% band; mean delta −7.6% |
| `And.BasicSurfer_FF1.6.jar` | 5 of 5 | 4,165.2 | 3,666.2 | −2.0%, −13.4%, −10.1%, −15.5%, −18.0% | Within ±25% band; mean delta −11.8% |

### ScalarR: a reproducible bridge-only lifecycle/tick-boundary failure

The classic pair completed with a combined score of 4,130 and no errors. Tank Royale produced no result and two bridge-side errors before the harness stopped it. The signature was `dev.robocode.tankroyale.botapi.BotException: Game is not running or tick has not occurred yet. Make sure onTick() event handler has been called first`.

The stack ran through `BaseBotInternals.getCurrentTickOrThrow` → `BaseBot.getTurnNumber` → `BotPeer.getTime` → the obfuscated ScalarR custom-event path → `BotPeer.dispatchCustomEvent` → the Bot API event queue's final-turn dispatch. The observation establishes that the legacy robot's custom-event callback can reach `BotPeer.getTime()` when the Bot API has no current tick. It does not yet establish whether the bridge callback boundary or the Bot API's final-turn scheduling owns the mismatch.

This is a bridge-only categorical failure under `C-004`, not a low score that should be averaged. It is a focused lifecycle/tick-boundary repair candidate.

### Ar1: the old higher score does not reproduce as a material gap

Ar1 completed all five official pairs with no errors. Its deltas ranged from −21.7% to −0.9% and averaged −7.6%, inside the current absolute ±25% screening band. The old +64.2% single-run figure is not a current material higher-score observation. The result does not establish exact parity or provide a reason to change physics or state semantics.

### BasicSurfer: the old higher score does not reproduce as a material gap

BasicSurfer completed all five official pairs with no errors. Its deltas ranged from −18.0% to −2.0% and averaged −11.8%, inside the current absolute ±25% screening band. The old +79.2% single-run figure is not a current material higher-score observation. The result does not establish exact parity or isolate the radar and bullet-state behaviours mentioned in the old watch-list note.

## What was tried and rejected

The historical report's single-run higher-score deltas were not reused as baselines. `AN-001` and the watch-list comments state that they were measured at a setup matching no division and before the event-dispatch redesign and Bot API upgrade.

All measurements used the official `roborumble` setup rather than a reduced round count, because an override would produce a number incomparable with the rumble under `C-003`.

ScalarR was not rerun four more times after the first bridge-only exception. Repetition cannot improve a categorical bridge-only failure, and `C-004` requires the battle to stop without a fabricated score.

Ar1 and BasicSurfer were each run for five completed pairs because their first runs completed without errors and score deltas are noisy. No production repair was made for either bot, and no physics or radar conclusion was inferred from the stale higher-score reports.

The measured deltas were not written into `regression-set.json` as baselines. `M-006` has not produced the first official baseline, and the ±15 percentage-point regression movement band is not applicable to these null baselines.

## What it means

`M-003`'s exit criterion is met for the three named higher-score candidates: ScalarR has a named and reproducible bridge-only lifecycle failure, while Ar1 and BasicSurfer are within the current absolute score band after five official repeats. The old higher-score findings for Ar1 and BasicSurfer should be treated as stale noise, not as reasons for speculative bridge changes.

The next plan milestone is `M-004`, robot file-I/O sandboxing. ScalarR should remain a focused repair candidate and should receive targeted lifecycle evidence before any repair is selected. `M-127` remains open because the score-parity criterion is not discharged by this observational spike and the harness's Python evidence remains outside the supported acceptance carriers described by `AN-003`.

## What this does not establish

It does not establish the owner of ScalarR's tick-boundary mismatch, exact parity for Ar1 or BasicSurfer, a regression baseline, population-wide score parity, or parity for any robot outside the three-candidate population. It also does not establish that the old higher-score deltas were caused by one shared engine-level defect.
