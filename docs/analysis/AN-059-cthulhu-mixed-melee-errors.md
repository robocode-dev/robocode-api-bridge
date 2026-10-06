---
id: AN-059
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, AN-015, AN-058]
title: Cthulhu's melee errors mix its own data-file quota output with opponent failures
provenance: inferred
reversal-cost: low
---

# AN-059 — Cthulhu's melee errors mix its own data-file quota output with opponent failures

## Risk investigated

Whether the current `meleerumble/asd.Cthulhu_1.3.jar` errors identify the subject robot, the bridge, or the fixed melee opponent pool.

## Evidence boundary

The read-only subject jar has SHA-256 `208de4c048a8b6f619c676f0cc8098b758a6fd42ce06689f5549459d0d542051`. The current official one-pair observation `0679565125f26395` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `4580799690e1806b9ea7afb33a29320968947e44`, local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`, 10 participants, 35 rounds, and a 1000×1000 arena. The selected nine opponents included `amk.ChumbaMini_0.2.jar` and `amk.ChumbaWumba_0.3.jar`. The subject and fixture jars remained unchanged.

## What was tried

The harness force-ran this registry subject at official parameters using the current bridge and local Tank Royale builds. Classic scored 114,277 with 405 errors; Tank Royale scored 111,106 with 35 errors. The single-pair score delta was −2.8%, within the 25% review threshold, and does not confirm a score gap.

The Tank Royale error text includes `errore scrittura su file: java.io.IOException: You have reached your filesystem quota of: 200000 bytes.` Its parser signature has no stack origin. Read-only `javap` inspection of the subject jar identifies this exact message in `asd.movement.Movement.toFile(String)`: the method appends movement statistics with `RobocodeFileWriter`, catches `IOException`, and prints the error. `asd.movement.SelectMovement.onWin()` and `onDeath()` build a robot data-file path and call `toFile()`. The same quota message appears in both engines, so this output is attributable to Cthulhu's own repeated append writes; it is not evidence of a bridge exception. This append behavior is distinct from the overwrite-accounting bridge defect documented in AN-015.

The current Classic signatures also name `amk.ChumbaMini.saveData` for a five-open-stream `SecurityException` and `amk.guns.Aristocles.prepare` for an `ArrayIndexOutOfBoundsException`. Those classes belong to the selected ChumbaMini and ChumbaWumba opponent jars. Some Classic origins remain `unknown`. These fixture errors match AN-015 and the other current melee findings. The historical observation `1233d28410286af5` reported an IOException with a ChumbaMini stack origin; retain that historical record as captured. The current bytecode establishes that Cthulhu itself also emits the same quota-error text.

## Finding

The current result has mixed sources: Cthulhu's own data-file appends reach the Robocode 200,000-byte quota on both engines, while Classic also reports errors from the fixed opponent pool. Tag the subject-owned output `subject-owned-data-file-append-quota`, owner `robot`, and the fixture errors `melee-opponent-pool-contamination`, owner `harness`. Retain `DISCREPANCY (errors)` while the run reports errors. The −2.8% delta from one pair is not a confirmed score divergence. Do not edit the read-only subject or opponent jars.

## M-006 handoff

Continue in registry order with `meleerumble/awesomeness.Elite_1.0.jar`.
