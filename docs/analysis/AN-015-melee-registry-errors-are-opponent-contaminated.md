---
id: AN-015
type: analysis
status: active
links: [CH-016, CAP-005, CAP-007, PDR-003, IDR-007, C-007]
title: The first melee registry cases are contaminated by fixed-opponent failures
provenance: inferred
reversal-cost: low
---

# AN-015 — The first melee registry cases are contaminated by fixed-opponent failures

## Risk investigated

Whether the unresolved errors in the first official melee observations identify defects in the subject robot being measured.

## Evidence boundary

This finding was produced on 2026-09-10 by querying the tracked `compat-test/parity-registry.json`, reading the corresponding classic logs under `compat-test/errors/robocode/`, inspecting the read-only opponent jars, and reading the current bridge implementation. It did not rerun an engine. The observations were prepared-environment evidence: Windows, the local classic installation and read-only collection under `C:\Code\LiteRumble robots`, the local Tank Royale Bot API and runner, and the official 35-round, 1000-by-1000 melee setup. Each observation carries its own artifact and commit manifest; this finding does not replace those manifests.

## What was tried

The latest observation for each of the 17 tracked melee subjects was grouped by normalized exception and first application or legacy frame. The recurring frames were then mapped to the jars in the pinned twelve-jar opponent pool, and representative classic logs were compared with `RobocodeFileOutputStream.java` and `BridgeTeamMessage.java` in the bridge.

## What was found

Every one of the 17 latest melee observations contains `java.lang.ArrayIndexOutOfBoundsException` at `amk.guns.Aristocles.prepare`. That class is bundled in `amk.ChumbaWumba_0.3.jar`, which is in the fixed opponent pool. The corresponding logs show the exception at `Aristocles.java:55`, followed by `amk.ChumbaWumba.onScannedRobot`, so the frame belongs to an opponent and not to the measured subject.

Sixteen of the 17 observations also contain the classic `java.lang.SecurityException` and `java.lang.ArrayIndexOutOfBoundsException` at `net.sf.robocode.host.io.RobotFileSystemManager.addStream`. The representative logs identify `amk.ChumbaMini.saveData` during `onDeath`; `amk.ChumbaMini_0.2.jar` is also in the fixed opponent pool. These are repeated consequences of the selected fixture, not independent evidence that each subject has a file-I/O defect.

The missing classic stream-limit behaviour is a bridge gap worth a focused repair investigation. Classic rejects a sixth open robot file stream with “You may only have 5 streams open at a time”. The bridge's `RobocodeFileOutputStream` opens a `java.io.FileOutputStream` and checks the 200000-byte quota, but has no corresponding per-robot open-stream count. This is an inference from the current source plus the classic stack trace; it does not yet establish whether any subject's score changes once the stream limit is reproduced.

`abud.ThirdRobo_1.0.jar` is a separate case. Classic reports `NotSerializableException: abud.EnemyInfo` from the robot's broadcast, while the bridge reports `BotException: Could not serialize team message: abud.EnemyInfo` through `BridgeTeamMessage`. The robot supplies a non-serializable payload in both engines; the remaining question is whether the bridge should preserve the legacy exception shape or whether the registry should classify this as equivalent failure semantics.

The remaining tracked roborumble and team failures do not form one common signature in this snapshot. Their normalized frames are mostly individual application methods, with a smaller set of harness no-result and data-file cases. They need per-family triage; bulk attribution to the bridge would overstate what the registry proves.

## What this means for CH-016

The current melee observations cannot be used as clean subject-level parity evidence until the fixed opponent pool is either revised through an explicit decision or the harness isolates opponent failures from the subject result. The rumble jars remain read-only; removing, rewriting, or recompiling the failing opponent jars would violate `C-007` and would destroy the very evidence being diagnosed.

The registry should retain the observations and append a harness-owned cause for the opponent-contamination cluster. A later focused retest may use a repaired or explicitly revised opponent setup, and must link that repair or decision rather than silently replacing the original observations. The stream-limit mismatch and the non-serializable team-message mismatch remain separate bridge investigations.

## Rejected interpretations

The analysis rejects treating every classic-only error in these melee runs as a defect in the measured subject, treating the pinned pool's prior roborumble `PASS` results as proof that its jars are clean in melee, and modifying the rumble jars to make the comparison complete.

