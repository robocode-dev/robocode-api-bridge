---
id: AN-442
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007, C-004, AN-153, AN-282]
title: MogBot's no-score failure recurs while the EOF source remains unknown
provenance: inferred
reversal-cost: low
---

# AN-442 — MogBot's no-score failure recurs while the EOF source remains unknown

## Risk investigated

Whether `dam.MogBot_2.9.jar`'s recurring no-score outcome persists under the latest matched artifacts, and whether the current EOF evidence establishes the previously suspected persistence-writer cause.

## Evidence boundary

The read-only subject jar has SHA-256 `7afc716ca13e8918c6f19d5d04fb4119a1f8ce825cc44f873e48329233edc646`. The official observation `980544819abcf29d` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `78039932ddf7bbb1474c68bd4ffe2df72514d329`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API, and runner artifacts are identified by hashes in `compat-test/parity-registry.json`. It used two participants, 35 rounds, and an 800×600 arena in a prepared Windows PowerShell environment; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The run stopped after one attempt with zero completed score samples. This is not a five-pair score confirmation. No confidence interval or score-gap estimate was calculated. The registry status is `DISCREPANCY (errors)` because the run failed before either engine produced a score; the manifest's `threshold: 25.0` does not classify this outcome.

## What was tried

Classic returned no score and listed no engine errors. Tank Royale returned no score; its log contains two `java.io.EOFException` messages with unknown origin and a worker-without-result report. Skipped-turn telemetry was incomplete. The registry records a mechanically bridge-only EOF signature because Classic reported no matching exception, but the current evidence does not establish that the bridge caused it.

AN-282's preceding observation also ended without a Tank Royale score and recorded two EOF errors plus a worker-without-result report. AN-153 had additionally recorded `IOException: Stream Closed` from `dam.MogBot.saveSettings`; its bytecode review found an unjoined persistence thread. The current run does not include that `Stream Closed` signature and does not show which component produced the EOFs, so the earlier robot-owned diagnosis remains a possible explanation rather than a demonstrated cause.

## Finding

MogBot's no-score failure recurs, with two EOF messages of unknown origin and incomplete skipped-turn telemetry. No valid score comparison was produced. The evidence does not establish whether the previously identified persistence behavior caused this run or whether a bridge defect is involved.

## M-006 handoff

Continue in registry order with `roborumble/dans.Cinnamon_1.2.jar` (`CONFIRMED (score)`).