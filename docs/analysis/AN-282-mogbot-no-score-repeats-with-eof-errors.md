---
id: AN-282
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: MogBot's Tank Royale no-score outcome recurs with EOF errors of unknown origin
provenance: inferred
reversal-cost: low
---

# AN-282 — MogBot's Tank Royale no-score outcome recurs with EOF errors of unknown origin

## Risk investigated

Whether `dam.MogBot_2.9.jar`'s historical Tank Royale no-score outcome recurs under the latest matched artifacts and whether the current errors match the prior persistence-writer diagnosis.

## Evidence boundary

The read-only subject jar has SHA-256 `7afc716ca13e8918c6f19d5d04fb4119a1f8ce825cc44f873e48329233edc646`. The official observation `25135646822b48a4` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `c0687e317ddc8775f40a7d8b4a88226cfedb2f6c`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic scored 6,413 with 44 recorded serialization and stream errors. Tank Royale produced no score after 3 recorded errors: two `java.io.EOFException` entries and a harness worker-without-result entry. The registry lists the EOF signature with origin `unknown`; skipped-turn telemetry was incomplete. The registry status is `DISCREPANCY (outcome)`.

AN-153's prior observation also had no Tank Royale score, with five errors including `IOException: Stream Closed` from `dam.MogBot.saveSettings`, `EOFException`, and a worker-without-result entry. Its source inspection found an unjoined robot persistence thread. The current observation does not record `Stream Closed`, and the EOF origin remains unknown, so the earlier diagnosis does not establish the cause of this run's failure.

## Finding

MogBot's Tank Royale no-score outcome recurs, but the current error evidence does not establish whether the earlier robot-owned persistence behavior caused this failure. The Classic run also records many errors. Keep the current Tank Royale failure unresolved; no bridge defect is established by this observation. No code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/dans.Cinnamon_1.2.jar` (`score-review`).
