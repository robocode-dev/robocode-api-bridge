---
id: AN-123
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: RipCurl's historical Tank Royale failure does not recur, and its old frame is unattributed
provenance: inferred
reversal-cost: low
---

# AN-123 — RipCurl's historical Tank Royale failure does not recur, and its old frame is unattributed

## Risk investigated

Whether bts.wiki.RipCurl's historical Tank Royale no-score outcome recurs with current matched artifacts, and whether the old error signature identifies RipCurl or the bridge.

## Evidence boundary

The read-only subject jar `bts.wiki.RipCurl_0.9b.jar` has SHA-256 `1f9a52c3ea49c22e5755defaaf43f355801169537f7a9636d1708bd00064e4c9`. The current official observation `dc44d1fa8d4bbef2` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `fed14402bed8169f27e25aa8586f69c67602a5e8`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the matched local Bot API and runner artifacts recorded in the registry. The jar remained read-only.

## What was tried

The current official pair scored 5,085 in Classic and 4,624 in Tank Royale, a −9.1% delta. Neither engine reported errors, and Tank Royale captured an empty skipped-turn event list. The registry status is `PASS`.

The earlier observation `1a37177249ebafea` from 2026-09-11 had no Tank Royale score and three `ArrayIndexOutOfBoundsException` errors whose recorded origin was `us.bluetorch.robocode.movement.MinimumRisk.onScannedRobot`; Classic scored 5,112 without errors. The earlier observation `0b71b2a50f905e18` also had no Tank Royale score and two errors, but no error signature. The historical setup recorded two participants but did not preserve the opponent identity, so the `MinimumRisk` frame cannot be assigned to RipCurl or its opponent from the registry evidence. Neither the no-score outcome nor the exception recurred in the current run.

## Finding

RipCurl passes the current score and error checks, and its historical Tank Royale failure does not recur with the current matched artifacts. The old exception's robot identity remains uncertain, and the evidence does not implicate the bridge. No code or rumble jar change is indicated.

## M-006 handoff

Continue in registry order with the next subject requiring review, `roborumble/bvh.hdr.Hodur_0.4.jar` (`score-review`).
