---
id: AN-193
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Fermat's Tank Royale startup bounds error recurs intermittently
provenance: inferred
reversal-cost: low
---

# AN-193 — Fermat's Tank Royale startup bounds error recurs intermittently

## Risk investigated

Whether the historical Tank Royale-only `ArrayIndexOutOfBoundsException` for `ak.Fermat_2.0.jar` recurs after the registered `initial-status-before-run` repair under current matched artifacts.

## Evidence boundary

The read-only subject jar `ak.Fermat_2.0.jar` has SHA-256 `814688bbe0f93cf5cc6a6be42ab0e69ad0259c54cba8547b20ddb4b1bd7107b3`. Two official one-pair observations completed on 2026-10-07 with the same bridge commit `c1b522de2baa945bde62ee17fc475ff577dd36e0`, Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, Classic Robocode 1.11.1, 10 participants, 35 rounds, and a 1000×1000 arena. The prepared Windows environment used Classic on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge API, wrapper, Bot API, and runner artifact SHA-256 values were `2fe0045694b3636d99e2718216d082d32354472535de449028daf098fbf10945`, `e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a`, `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, and `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af` respectively. The registry records the selected opponent jars and their hashes; all subject and opponent jars remained read-only.

## What was tried

Observation `176aab56fa5cd2ea` produced no Tank Royale score and 2 Tank Royale errors; the log classified the worker exit and `ArrayIndexOutOfBoundsException` as bridge-only. The exception was `Index -1 out of bounds for length 50` at `ak.RobotBody.getDirection(RobotBody.java:115)`, called by `ak.Fermat.doUpdates(Fermat.java:147)` and `ak.Fermat.run(Fermat.java:82)`. Classic scored 114,479 and logged 1,096 errors, including the known `amk.ChumbaMini.saveData` stream-limit and `amk.guns.Aristocles.prepare` array-bounds signatures. Requested skipped-turn capture was incomplete for this failed run.

The immediate repeat, observation `2607328840a47861`, scored 113,591 in Classic with 588 errors and 112,183 in Tank Royale with no errors, a −1.2% delta. Skipped-turn capture completed with an empty event list. Classic's current error signatures again include ChumbaMini's stream-limit error and Aristocles' array-bounds error. The registry row remains `DISCREPANCY (errors)`.

Earlier observations on the same post-repair bridge commit `8e50c0b` also completed Tank Royale without errors: `bc0742d34282e1cf` scored 110,693 against Classic's 114,690 (872 Classic errors), and `dd82d6b34b151588` scored 111,993 against Classic's 113,858 (204 Classic errors). Across the four post-repair observations summarized here, the Fermat-specific Tank Royale exception appeared once and did not recur in the immediate repeat or the two earlier observations.

## Finding

The prior `initial-status-before-run` repair did not make the Fermat-specific Tank Royale error impossible: one current run reproduced it, while three other post-repair runs completed without it. The failed run's missing telemetry prevents checking whether a scan or status callback preceded Fermat's first update, and the legacy robot stack alone does not establish whether bridge event timing caused the invalid index. The recurrence is therefore recorded as intermittent in these observations, with its cause unresolved. The row remains `DISCREPANCY (errors)` because the successful repeat still has 588 Classic-side errors and a measurable score gap; no code change is justified by this evidence alone.

## M-006 handoff

Continue in registry order with `meleerumble/amk.ChumbaWumba_0.3.jar` (`DISCREPANCY (errors)`), following AN-194's ChumbaMini stream-limit retest.
