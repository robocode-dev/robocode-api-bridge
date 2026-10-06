---
id: AN-128
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: Domogled's historical Tank Royale exception does not recur
provenance: inferred
reversal-cost: low
---

# AN-128 — Domogled's historical Tank Royale exception does not recur

## Risk investigated

Whether cb.Domogled's historical Tank Royale-only exception and no-score result recur with the current matched artifacts.

## Evidence boundary

The read-only subject jar `cb.Domogled_1.2.jar` has SHA-256 `75c374c61a80c5606d1ecd1220d80d663497eef1618b9a80f112c41e5b81e70a`. The current official observation `81b684f912cb6069` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `184415e6cdb4455dfdc32e0b412041f0111c906f`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The current official pair scored 4,548 in Classic and 4,866 in Tank Royale, a +7.0% delta. Tank Royale reported no errors and captured an empty skipped-turn event list. Classic reported one `Unable to stop thread: cb.Domogled 1.2 (1)` message. The registry status is `PASS`.

The earlier observation `5447e9339db7eccd` from 2026-09-11 had no Tank Royale score and three Tank Royale exceptions, including a `NullPointerException` originating at `cb.Domogled.movement`; Classic scored 4,347 and reported one error. The earlier observation `ab4a97816c447d60` from 2026-09-08 also had no Tank Royale score, three Tank Royale errors, and one Classic error. The current Tank Royale run did not reproduce the prior exception or no-score outcome.

## Finding

Domogled's historical Tank Royale failure does not recur with the current matched artifacts, and its current score delta is within the registry threshold. Classic did emit a thread-stop message, so the current pair was not error-free on both sides; that message is recorded as observed and is not attributed here. Tank Royale reported no errors in the current observation; no jar change is indicated.

## M-006 handoff

Continue in registry order with the next unresolved subject, `roborumble/cb.fire.Firestarter_2.0f.jar` (`DISCREPANCY (outcome)`).
