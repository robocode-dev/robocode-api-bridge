---
id: AN-319
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: Fermat's Tank Royale startup error repeats intermittently in current runs
provenance: inferred
reversal-cost: low
---

# AN-319 — Fermat's Tank Royale startup error repeats intermittently in current runs

## Risk investigated

Whether `ak.Fermat_2.0.jar`'s historical Tank Royale-only `ArrayIndexOutOfBoundsException` persists under the latest matched artifacts, and whether the evidence identifies a bridge defect.

## Evidence boundary

The read-only subject jar has SHA-256 `814688bbe0f93cf5cc6a6be42ab0e69ad0259c54cba8547b20ddb4b1bd7107b3`. Two official runs completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `1c00eeebdab2c4e3f93a2fd115235b6bdd66e4a8`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. Each used 10 participants, 35 rounds, and a 1000×1000 arena with the selected opponent jars recorded in the registry; all jars remained read-only.

## What was tried

The first run, observation `a06ae57aca23bd50`, failed before producing a score. Its bridge-only signature was `java.lang.ArrayIndexOutOfBoundsException` at `ak.RobotBody.getDirection`, and skipped-turn capture was incomplete.

The immediate retry, observation `929ae1ab14c7ed06`, completed two pairs before the third attempt failed with the same signature. The two score deltas were −2.7% and −2.0%, averaging −2.35%; their skipped-turn telemetry was captured with 6 and 15 events. The third attempt's telemetry was incomplete. The robot system logged no detailed stack trace in these records, so the current evidence identifies the class and method but not the triggering event sequence.

Together, the two runs produced two successful pairs and two failures across four attempts. The registry remains `DISCREPANCY (errors)` because both Tank Royale-only failures recur; the partial score sample is not a completed confirmation.

## Finding

The Fermat startup error remains intermittent under the latest matched artifacts: the first run failed immediately, the retry completed two pairs, then failed on its third attempt. The exception is reported inside the read-only bot's `ak.RobotBody.getDirection`, but that alone does not establish whether the bot's state access or bridge event timing caused it. No bridge change is justified by this evidence; the failure needs a trace that captures the triggering status or scan sequence.

## M-006 handoff

Continue in registry order with `meleerumble/amk.ChumbaMini_0.2.jar` (`DISCREPANCY (errors)`).
