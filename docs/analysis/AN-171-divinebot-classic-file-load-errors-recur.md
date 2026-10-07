---
id: AN-171
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: DivineBot's Classic file-load errors recur
provenance: inferred
reversal-cost: low
---

# AN-171 — DivineBot's Classic file-load errors recur

## Risk investigated

Whether DivineBot's historical Classic-only file-load errors recur with current matched artifacts, and whether the current score gap is confirmed.

## Evidence boundary

The read-only subject jar `divineomega.DivineBot_1.9.5.jar` has SHA-256 `8d127b2400ccfddf39d0666729cc749b95084f1550c76d604f8b1089ca688b8a`. The official observation `bc9133dc96057591` completed on 2026-10-07 with Classic Robocode 1.11.1, bridge commit `d8f9e0b313f54fa2105ac485da918cdedb967a6f`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, and an 800×600 arena in a prepared Windows environment; Classic ran on JDK 17.0.17.10 and Tank Royale on Temurin 25.0.1. The bridge, wrapper, Bot API, and runner artifacts were locally built or staged as recorded in the registry. The jar remained read-only.

## What was tried

The official single pair scored 7,586 in Classic and 10,012 in Tank Royale, a +32.0% Tank Royale delta. Both engines completed. Classic recorded two `java.io.EOFException` errors from `divineomega.FileManager.load`; its console then reported that a new file would be created at the end of the round. Tank Royale recorded no runtime errors and captured an empty skipped-turn event list. The Classic console recorded skipped turns 2951, 2663, 2664, and 2665. The registry status remains `DISCREPANCY (errors)`.

The earlier official observation `4fd33ccfdaf0dab3` on 2026-09-11 recorded the same two Classic `EOFException` signatures and no Tank Royale errors, with scores of 7,632 and 7,654 respectively (+0.3%). The error signature therefore recurred, while the one-pair score delta varied substantially.

The population here is this one selected robot from the local RoboRumble corpus, pinned by the jar hash above; this is not a collection-wide estimate. The current score delta comes from one pair, so it does not confirm a stable score gap and no confidence interval was calculated. The 25% threshold is a score-review marker; score quality remains environment-sensitive evidence rather than a deterministic acceptance criterion.

I did not run the five-pair score confirmer for this row. It is currently classified as an error discrepancy, and that confirmer emits a score-only status without retaining this Classic error comparison. The +32.0% single-pair result remains unconfirmed.

## Finding

The two Classic `EOFException` errors at the robot's `FileManager.load` frame recurred, while Tank Royale completed without runtime errors. The available evidence locates the exceptions in the robot frame but does not establish why the serialized data could not be read or whether the bridge caused the difference. No bridge-only error or bridge code change is indicated by this observation. The score difference requires separate confirmation before it can be treated as a persistent quality gap.

## M-006 handoff

Continue in registry order with `roborumble/djdjdj.NanoSkunk10_1.0.jar` (`score-review`).
