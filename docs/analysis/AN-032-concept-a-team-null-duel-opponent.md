---
id: AN-032
type: analysis
status: active
links: [P-001, CAP-005, CAP-006]
title: ConceptATeam's current score is in band; its Classic null dereference is in the robot jar
provenance: inferred
reversal-cost: low
---

# AN-032 — ConceptATeam's current score is in band; its Classic null dereference is in the robot jar

## Question

Does the earlier `teamrumble/lxx.ConceptATeam_0.8.jar` score gap persist on current matched artifacts, and is the current Classic-only null-pointer error bridge-owned?

## Evidence boundary

The read-only team jar has SHA-256 `8ed86d05ddbe64e77cc93352da90d53afed21a832a43f40f1525f1b67f1264aa` and contains the bundled Java sources used for the source-level trace below. The fresh official pair used a 1200×1200 field, 10 rounds, and two teams. Its registry observation is `02701f091eea7032`, measured with bridge commit `b4be3bf4ceb3f3acf9f261888cee190fdcace7bc`, Classic installation 1.11.1, Runner 1.4.0 (SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`), and Bot API 1.4.0 (SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`). At measurement time the Tank Royale checkout was at `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`; that commit only records analysis documentation, and the tested artifacts were built from the unchanged source at `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`. The bridge API and wrapper hashes were `fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5` and `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`.

This was a prepared Windows run using the local Classic installation and local Tank Royale and bridge artifacts. The rumble jar was not modified. The one current pair is a diagnostic retest, not a five-sample score confirmation.

## Results

The current pair scored 21,481 in Classic and 22,815 in Tank Royale, a +6.2% delta within the 25% score band. Classic recorded eight normalized error records and Tank Royale recorded none, so the harness status is `DISCREPANCY (errors)`, not a clean score pass. The Classic log contains a `NullPointerException` from `lxx.util.CaUtils.getNonZeroLateralDirection()` while dispatching `StatusEvent`; `CaRobot.getSpeed()` is called on a null `robot`.

The historical observation `6d7c8a3305582f2a` recorded 21,045 Classic points versus 33,927 Tank Royale points, a +61.4% delta, with 10 Classic and 6 Tank Royale error records. Both sides carried the same `CaUtils.getNonZeroLateralDirection` signature. The large old score delta is not reproduced on the current pair, and the earlier Tank Royale errors show that this robot exception is not a bridge-only failure.

## Source trace

The bundled `BattleModel` sets `duelOpponent` only when exactly one enemy is alive. `GuessFactorGun.battleModelUpdated()` launches a wave when the new state has one duel opponent but stores `newState.prevState` as the wave's `fireTimeState`. That previous state may still have zero or multiple live enemies, so its `duelOpponent` is null. Later, `GuessFactorGun.wavePassed()` dereferences `w.fireTimeState.duelOpponent` and passes it to `CaUtils.getNonZeroLateralDirection()`, whose first operation calls `robot.getSpeed()` without a null check. The recorded stack follows that bundled bot path through `WavesService` and `ConceptA.onStatus`.

## Finding

The null dereference is a defect in the read-only ConceptATeam jar's wave-model transition: it retains a prior model with no unique opponent and later assumes that model has one. This accounts for the Classic stack trace and matches the same historical Tank Royale error signature. Its absence from the current Tank Royale sample does not establish a bridge repair; the trigger depends on the battle state reached by the robot. The current score delta is inside the accepted band, and this measurement does not identify a bridge or Tank Royale score defect.

The diagnosed cause is recorded in the parity registry as `robot-null-previous-duel-opponent-on-wave-callback`, owner `robot`. No bridge code was changed, and the collection jar remains read-only.

## M-006 handoff

Retain the current observation and diagnosis as a robot-owned error path with a current score inside the band. Do not treat the historical +61.4% result as a current score gap. Continue with the next unresolved teamrumble registry entry.
