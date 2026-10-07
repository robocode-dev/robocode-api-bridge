---
id: AN-178
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Jezza's Classic score advantage is confirmed below the single-pair review threshold
provenance: inferred
reversal-cost: low
---

# AN-178 — Jezza's Classic score advantage is confirmed below the single-pair review threshold

## Risk investigated

Whether Jezza's historical Classic score advantage persists across five official pairs, and how its mean relates to the single-pair review threshold and five-pair confirmation band.

## Evidence boundary

The read-only subject jar `donjezza.Jezza_1.0.jar` has SHA-256 `1188605236143d82517db214ae55e4881181a3c88245d6f498dfd9a1f5fd9de5`. The official confirmation `fe5b481d0d854565` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `c123c14bcf3584aef74836d29e303beec1801fbe`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

Across five official pairs, the score deltas were −27.0%, −24.2%, −22.8%, −25.2%, and −23.8%. Classic averaged 7,844.2 points and Tank Royale averaged 5,914.2, for a −24.6% mean delta. Both engines completed all five pairs without errors, and Tank Royale captured an empty skipped-turn event list in every run. The registry status is `CONFIRMED (score)`.

The workflow uses two score thresholds. A single-pair delta beyond the 25% review threshold places a subject in `score-review`; the five-pair `--confirm-score` result uses the 15-point mean-gap band. Jezza's mean is below the single-pair screen but exceeds the five-pair band, so the confirmed status follows the five-pair rule. The registry manifest records the 25% single-pair threshold.

This measures one selected robot from the local RoboRumble corpus, pinned by the jar hash above, rather than the collection as a whole. The five paired deltas show the observed spread; no confidence interval was calculated. The score gap is behavioral quality evidence, not a deterministic acceptance criterion.

The earlier observation `ea54e870cda66d48` recorded a single-pair Classic score of 8,082 and Tank Royale score of 5,956, a −26.3% delta. The current five-pair mean confirms a smaller Classic advantage.

## Finding

Jezza's Classic score advantage is confirmed with current matched artifacts. Its −24.6% five-pair mean is below the 25% single-pair review threshold and above the 15-point five-pair confirmation band. No runtime errors or skipped turns were observed. The measurements do not establish the behavioral cause of the score difference. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/donjezza.Muncho_1.0.jar` (`score-review`).
