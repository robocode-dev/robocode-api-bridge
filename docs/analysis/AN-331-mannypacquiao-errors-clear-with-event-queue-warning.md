---
id: AN-331
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004, AN-055, AN-205]
title: MannyPacquiao's prior errors clear, with Tank Royale event-queue warnings
provenance: inferred
reversal-cost: low
---

# AN-331 — MannyPacquiao's prior errors clear, with Tank Royale event-queue warnings

## Risk investigated

Whether `arthord.MannyPacquiao_Beta.jar`'s prior loop and opponent-pool errors persist under the latest matched artifacts, and whether the long run exposes another bridge error.

## Evidence boundary

The read-only subject jar SHA-256 and the nine selected opponent jar hashes are recorded in the registry. The official five-pair confirmation `831f6a2a042ba252` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `773d1c97e17d3018b4f6816af475e6ba403bd232`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 10 participants, 35 rounds, and a 1000×1000 arena; the bridge API, wrapper, Bot API, and runner artifact hashes are recorded in the registry. The subject and opponent jars remained read-only.

## What was tried

The five pairs took 16.8 minutes. Classic averaged 114,379.4 points and Tank Royale averaged 112,448.0, for a −1.70% mean delta. Pair deltas were −1.1%, −2.3%, −1.6%, −2.1%, and −1.4%. Neither engine reported errors, and the registry status is `MATCHED (score noise)`.

The preceding observation `f4ffedbce4a6d5d5` recorded 455 Classic errors and 31 Tank Royale errors, including MannyPacquiao's Classic force-stop exception and the selected opponent `amk.ChumbaMini_0.2.jar`'s Tank Royale stream-limit error. Neither error pattern recurred in the current five pairs. The generated subject class contains the wrapper's `getEnergy()` stop-condition branch in `run()`.

The final Tank Royale attempt's subject stderr contained 5,815 repetitions of `Maximum event queue size has been reached: 256`. The Java Bot API event queue emits this warning when its ordinary-event queue reaches 256 and drops subsequent ordinary events. Skipped-turn telemetry was captured for only the first two pairs, with 1,528 and 1,151 events; the remaining three captures were incomplete. These queue warnings are distinct from the empty engine error lists, but show event loss during the long radar-loop run. The available evidence does not establish whether the high event volume, wrapper scheduling, or another factor caused the queue to fill.

## Finding

The prior Classic loop exception and Tank Royale opponent error did not recur, and the five-pair score result remains within the registry's `MATCHED (score noise)` classification. The Tank Royale event queue did overflow during the final attempt, and telemetry was incomplete in three pairs; this leaves event delivery unconfirmed for those portions of the run. No new bridge code defect is established by this evidence. Preserve the warning and telemetry gaps for follow-up rather than treating this row as proof that all events were delivered.

## M-006 handoff

Continue in registry order with `meleerumble/arthord.NanoSatanMelee_Beta.jar` (`DISCREPANCY (errors)`).
