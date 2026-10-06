---
id: AN-109
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Frederick's very low randomized scores leave the engine outcome difference open
provenance: inferred
reversal-cost: low
---

# AN-109 — Frederick's very low randomized scores leave the engine outcome difference open

## Risk investigated

Whether ap.Frederick's historical no-score results persist with current matched artifacts and whether the current score difference is stable enough to confirm.

## Evidence boundary

The read-only subject jar `ap.Frederick_1.1.jar` has SHA-256 `d7f1709e4ea064f0662f383c339f57390977292fdd921933adc36e305a375899`. Current observations `bbbb7dda58f5e183` and `51e3a70a7b199fd9` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `4f11d7e9c13303bbc15d8df5634d8cff28abb16b`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. Both used 2 participants, 35 rounds, an 800×600 arena, and the current matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The first current official pair scored 7 in Classic and 120 in Tank Royale, with zero errors on either engine and no skipped turns. The official five-attempt confirmation scored 5, 0, 23, 0, and 1 in Classic, and 81, 190, 180, 195, and 146 in Tank Royale. Its all-attempt means were 5.8 and 158.4. Only three pairs had a nonzero Classic denominator, yielding deltas of +1,520.0%, +682.6%, and +14,500.0%; the other two pairs had no calculable percentage. The registry therefore records `DISCREPANCY (outcome)` with 5 attempts but only 3 valid score samples, not a five-sample confirmed score delta. No runtime errors or skipped turns were reported in the five attempts.

Earlier observations under different artifact pairs scored 120 versus 125, 1 versus 132, and 0 versus 61 in Classic and Tank Royale respectively. The bundled `ap/Frederick.java` uses `Math.random()` for movement choices and velocity. It calculates a `firePower` value but never calls a firing method, so its current behavior relies on movement and survival rather than shooting. This source supports high outcome variability and small absolute scores; it does not identify why the two engines' current averages differ.

## Finding

The historical Classic zero score recurs in two of five current attempts. The five-attempt series has no runtime errors or skipped turns, but only three valid percentage samples because two Classic scores were zero. The all-attempt mean scores differ substantially, while the per-attempt percentage mean is defined for only three runs. Keep the case open as `DISCREPANCY (outcome)` and leave its cause unassigned; do not treat the percentage mean as a complete five-run confirmation. No code or rumble jar change is indicated.

## M-006 handoff

Continue with the next unresolved registry subject, `roborumble/ary.FourWD_1.3d.jar`.
