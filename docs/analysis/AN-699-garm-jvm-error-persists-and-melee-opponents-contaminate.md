---
id: AN-699
type: analysis
status: active
links: [P-001, PDR-001, PDR-003, PDR-015, AN-015, AN-108, AN-385, C-007]
title: Garm's worker error persists across JDKs while its melee result is opponent-contaminated
provenance: inferred
reversal-cost: low
---

# AN-699 — Garm's worker error persists across JDKs while its melee result is opponent-contaminated

## Risk investigated

Whether Garm's Tank Royale no-result identifies a bridge defect or depends on the Java runtime, and whether its separate MeleeRumble errors belong to Garm or the fixed opponent pool.

## Evidence boundary

The read-only jar `Krabb.sliNk.Garm_0.9u.jar` has SHA-256 `c895d29c6c2b83c9441a643deca71f43405cb9907a2d4393832a84305967fe2d`. The current RoboRumble observations are `c07f675dfd9a4d69` on Temurin 25.0.1 and `dcf671ba1d3d6188` on Temurin 17.0.17; both used the official 800×600, 35-round, two-participant setup. Each used the matched artifacts recorded in the registry and completed Classic without errors, scoring 4,779 and 4,825 respectively.

Both Tank Royale runs produced no score and four errors, including `NoClassDefFoundError: Could not initialize class java.lang.StackTraceElement$HashedModules` and failure in the uncaught-exception handler. The JDK 17 diagnostic used a process-local `JAVA_HOME` and `PATH` override; it did not change machine-wide settings or the tracked repository.

The separate official MeleeRumble row is `meleerumble/Krabb.sliNk.Garm_0.9u.jar`, observation `0935de29b327fe56`, with ten participants, 35 rounds, and a 1000×1000 arena. It recorded 436 Classic errors and 30 Tank Royale errors; their normalized origins include `amk.guns.Aristocles.prepare` and `amk.ChumbaMini.saveData`, both from the pinned opponent pool described in AN-015.

## What was tried

The same RoboRumble case was run once with the default Temurin 25.0.1 and again with Temurin 17.0.17. The worker failure persisted with the same `StackTraceElement$HashedModules` message. The second runtime did not expose the underlying class-initializer exception.

The MeleeRumble case completed with Classic at 113,544 points and Tank Royale at 110,194, a −3.0% score delta. The known opponent errors remained on both sides, so the score comparison is not clean subject-level evidence.

## Findings

The Garm worker failure is reproduced with two Java runtimes, but both logs still omit the original initializer failure. This rules out a JDK-25-only trigger for this observation; it does not assign the failure to the bridge, robot, or runtime. As in AN-108 and AN-385, the no-score case remains undiagnosed and no code change is justified.

The MeleeRumble row reproduces the fixed opponent-pool contamination from AN-015. Its registry cause is recorded as `melee-opponent-pool-contamination`, owner `harness`.

## Implications for M-006

Keep the RoboRumble Garm outcome open until the worker exposes its first initializer error or another controlled setup isolates the source. The MeleeRumble error records are attributed to the known opponent pool and do not establish a Garm bridge defect.

## M-006 handoff

Continue M-006 collection coverage with the first unregistered MeleeRumble jar, `meleerumble/DBall.Anarchy_0.1.jar`; AN-700 records the current inventory gap.
