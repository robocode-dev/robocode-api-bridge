---
id: AN-141
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Karolos's historical Tank Royale failure does not recur
provenance: inferred
reversal-cost: low
---

# AN-141 — Karolos's historical Tank Royale failure does not recur

## Risk investigated

Whether cre.Karolos's historical Tank Royale no-score result and repeated exception recur with current matched artifacts.

## Evidence boundary

The read-only subject jar `cre.Karolos_0.32.jar` has SHA-256 `9d35fbbe7c3c3a2d325d49028ea3c4be40906f0f5e919a49dd46f66ca734e7ca`. The current official observation `17dab4a805d7a49e` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `b26d5a1806c36be20537717a97949d9f82fb5bc4`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The current official pair scored 6,457 in Classic and 6,217 in Tank Royale, a −3.7% delta. Neither engine reported errors, and Tank Royale captured an empty skipped-turn event list. The registry status is `PASS`.

The earlier observation `7bb15adf1502a23c` from 2026-09-11 had no Tank Royale score and 204 errors, including a `NullPointerException` with origin `cre.b.d.b`; Classic scored 6,430 without errors. The earlier observation `0c5b559772cf4ee1` also had no Tank Royale score and 370 errors but no signature. The current run did not reproduce either historical outcome or error.

## Finding

Karolos passes the current score and error checks, and its historical Tank Royale no-score failure does not recur with current matched artifacts. The cause of the older obfuscated-frame exception remains unassigned. No bridge code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/cs.Nene_1.0.5.jar` (`score-review`).
