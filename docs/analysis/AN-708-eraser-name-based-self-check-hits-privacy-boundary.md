---
id: AN-708
type: analysis
status: active
links: [P-001, CAP-003, CAP-005, API-007, IDR-009, AN-045, C-003, C-007, PDR-006]
title: Eraser confirms a score gap at the anonymous-name boundary
provenance: inferred
reversal-cost: low
---

# AN-708 — Eraser confirms a score gap at the anonymous-name boundary

## Risk investigated

Whether the confirmed score difference for `roborumble/traker.Eraser_1.jar` comes from score noise, a bridge defect in the Classic-shaped anonymous alias, or Eraser's dependence on the hidden opponent identity.

## Evidence boundary

The read-only jar SHA-256 is `cb5db64763078480a174f8204bf2f3c7c678118ba5011763d75d63131f7a4f3d`; its robot properties declare Classic Robocode 1.7.4.2. The five-pair confirmation is observation `74c9b538353fdc92`, completed on 2026-10-24 at official RoboRumble parameters: two participants, 35 rounds, and an 800×600 arena. It used Classic Robocode 1.11.1, bridge commit `bc47f805f9a6b37381d8171c3df965cad8737656`, and local Tank Royale commit `02639872ea90b0d8cc7e487eb89607935d341e9a`. The bridge API, wrapper, Bot API, and runner SHA-256 values are pinned in the registry. The exact Classic-side JDK version was not recorded. The jar remained read-only.

## What was tried

Five official self-duel pairs produced score deltas of +85.8%, +79.6%, +88.4%, +57.0%, and +77.4%. Mean Classic score was 1,956.2 and mean Tank Royale score was 3,471.6, for a mean delta of +77.64%. The harness recorded five successful samples and no bridge-only error signatures, so the registry classifies the score gap as `CONFIRMED (score)`.

Read-only bytecode inspection shows that `onScannedRobot` removes the last three characters from both the scanned name and `getName()`, compares the results, and takes a special branch on equality that turns the radar and sets its local firing power to zero. The current Tank Royale log shows the robot name paired with the bridge's anonymous target alias, for example `traker.Eraser 1 ,tankroyale.anonymous.Bot2 .`; the values do not match after the robot's name processing.

The bridge uses the Tank Royale bot-name map when it contains an opponent name and otherwise supplies `tankroyale.anonymous.Bot<id> 1.0`, as specified by `API-007` and `IDR-009`. Tank Royale withholds the real opponent identity from that map; AN-045 records the corresponding upstream information boundary and its effect on robots that select targets by name.

## Finding

The score gap is repeatable and consistent with Eraser's same-name self-check receiving an anonymous target alias in Tank Royale. The bridge provides the parse-safe alias required by the accepted rule, but the alias cannot reproduce the hidden identity that Classic exposes. `IDR-009` already records that name-based selection can differ in this situation and that the bridge cannot reconstruct an opponent's identity from its bot ID.

Changing every anonymous target to the observing robot's own name was rejected because the bridge cannot know which hidden opponent has that identity; it would mislabel ordinary opponents in mixed battles. The remaining discrepancy belongs to Tank Royale's opponent-name visibility boundary under the current contract, rather than a newly identified bridge alias defect. No implementation changed.

## Implications for M-006

Keep the five-pair `CONFIRMED (score)` result and the existing Tank Royale opponent-name diagnosis. This observation adds current score evidence for the limitation documented by AN-045 and does not justify a bridge repair. The read-only robot jar was not modified.

## M-006 handoff

The repair-linked official retest for `meleerumble/divineomega.DivineBot_1.9.4.5.jar` completed as observation `0f53263ec129e22a`: Classic scored 115,372 and Tank Royale scored 111,421 (−3.4%). Both engines reported the robot's caught `EOFException` from `divineomega.FileManager.load`; Tank Royale's remaining `SecurityException` came from the fixed `amk.ChumbaMini` opponent. The version-aware `RobotData.getDataFile` correction therefore removed the bridge-only missing-file behavior, while the registry retains the opponent-pool error discrepancy. Continue with `roborumble/dsekercioglu.mega.WhiteFang_2.8.1.jar` (`score-review`) in AN-554.

## Current disposition under the anonymity policy

The historical score observations and diagnoses remain unchanged. [The subsequent anonymity policy](../decisions/PDR-006-opponent-identity-dependence-is-a-robot-limitation.md) classifies Eraser's demonstrated same-name strategy as a robot compatibility limitation. The registry appends that diagnosis instead of retaining the bridge alias as the current cause. The known score difference does not require a bridge or Tank Royale identity-disclosure repair and does not block a preview; accepted alias consistency and unrelated runtime failures remain in scope.
