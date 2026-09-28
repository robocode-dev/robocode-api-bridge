---
id: AN-015
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, PDR-003, IDR-007, C-007]
title: The melee registry contains opponent-contaminated failures
provenance: inferred
reversal-cost: low
---

# AN-015 — The melee registry contains opponent-contaminated failures

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

## Implications for M-006

The current melee observations cannot be used as clean subject-level parity evidence until the fixed opponent pool is either revised through an explicit decision or the harness isolates opponent failures from the subject result. The rumble jars remain read-only; removing, rewriting, or recompiling the failing opponent jars would violate `C-007` and would destroy the very evidence being diagnosed.

The registry should retain the observations and append a harness-owned cause for the opponent-contamination cluster. A later focused retest may use a repaired or explicitly revised opponent setup, and must link that repair or decision rather than silently replacing the original observations. The stream-limit mismatch and the non-serializable team-message mismatch remain separate bridge investigations under M-006.

## Follow-up evidence from the next official checkpoint

The second official melee checkpoint completed on 2026-09-28 with 25 observations: 20 error discrepancies, 4 outcome discrepancies, and 1 pass. Classic again reported opponent-origin ArrayIndexOutOfBoundsException signatures from amk.ChumbaMini.saveData and amk.guns.Aristocles.prepare in 24 observations; both jars are in the pinned opponent pool. The 25 subjects were tagged with the existing melee-opponent-pool-contamination diagnosis.

The stream-limit gap described above referred to the source snapshot examined on 2026-09-10. The bridge now enforces the five-open-stream limit in RobotData; the newer observations were produced with that implementation. The opponent-origin exceptions and the three measured-subject exceptions remain separate findings.

Three outcome discrepancies carried Tank-Royale-only exceptions from measured-subject methods: cb.fire.Firestarter, cf.RiO.RiOxM_OP, and darkcanuck.B26354. Those remain separate cases for focused diagnosis.

The fourth outcome discrepancy was com.syncleus.robocode.Dreadnaught. Its first Tank Royale run produced no result and recorded 8,139 seconds against the 600-second per-side timeout. Source review found that the fail-fast watcher ran synchronously inside the timeout loop and Windows taskkill had no timeout. Both paths are now bounded and covered by regression tests. The timeout did not reproduce: a same-setup official retest completed in 36.1 seconds and returned to the opponent-error discrepancy pattern. The original elapsed-time record remains in the append-only registry; that run did not capture which blocking step caused the overrun.

## Focused retests after timeout supervision repair

The repair-linked official Dreadnaught retest completed all 35 rounds in 60.6 seconds on Tank Royale, below the 600-second per-side timeout. Its observation (`e60cf1d52f296445`) remains `DISCREPANCY (errors)` because the fixed pool still produces the opponent-origin `SecurityException` and `ArrayIndexOutOfBoundsException`. The registry links this retest to cause `harness-timeout-overrun` and repair commit `4d0fa88`; the earlier 36.1-second observation is unchanged and remains unlinked.

The three subject-only exceptions were retaken under the same official 35-round, 1000-by-1000, ten-participant setup and exact nine-opponent pool. Firestarter again threw a `NullPointerException` in its obfuscated `C.I.I` callback (`0b2c1f3d52ecd23f`); RiOx again threw `ArrayIndexOutOfBoundsException: Index 9 out of bounds for length 9` at `cf.OPs.RiOxM_OP.onScannedRobot` (`491778d42b27fa0a`); and B26354 again threw `NullPointerException` from `darkcanuck.m.a` (`b336f45184cf831d`). The classic runs contained the known opponent-pool errors, not these subject signatures. Tank Royale fail-fast stopped each run at the first subject-only exception, so these observations prove reproducibility rather than full-battle score parity.

The RiOx bytecode shows a nine-entry enemy array sized from `getOthers()`. A trace-enabled diagnostic rerun captured the first Tank Royale failure at turn 239: before then, RiOx had scanned all nine opponent IDs (`1, 2, 3, 4, 6, 7, 8, 9, 10`). At turn 239 it received `RobotDeathEvent(victim=3)`, followed by `ScannedRobotEvent(target=3)` with `others=8`; the array exception followed that scan callback. The target ID and exposed name remained `3`, so the captured failure does not indicate a tenth distinct identity. It shows a scan for the just-declared-dead target after the death callback, which re-enters RiOx's new-target path after its death handler removed that record. This identifies the immediate trigger, but does not establish that the bridge violates classic behavior: classic and Tank Royale both prioritize robot-death events above scan events for events with the same time/turn, and the official classic run had no RiOx callback exception. A controlled comparison of this exact death/scan sequence is still needed before changing bridge behavior. The diagnostic build changed only a detached scratch worktree and is not a registry observation. No rumble jar was changed.

The first focused Firestarter retake recorded uncertain process-tree cleanup after its fail-fast stop. A second retake reproduced the same bot exception with cleanup confirmed. The harness now records taskkill's timeout or error text when Windows cleanup cannot be confirmed, and its timeout supervision tests pass.

## Fermat file-stream leak investigation

Three focused official 35-round Roborumble replays of `roborumble/ak.Fermat_2.0.jar` produced both outcomes: the replay with round-end stream cleanup completed without stream errors (`a00859152f1287d2`), while two replays without cleanup hit the five-stream exception (`7b65988ff7e71a41`, 2 errors; `49b5fb75c478cf4b`, 7 errors). Classic completed without errors in each replay. These runs used the matched local Tank Royale 1.4.0 Bot API and runner.

Trace output shows the active set growing from repeated opens of `Fermat.data/*.txt` files. Fermat's `DataWriter.run()` opens a stream, writes a copied statistics list, and closes the stream only on the successful path; its broad `catch (Exception)` prints only `Could not write Data` and does not close the current stream. The exact exception caught by that legacy worker is therefore unavailable. Its leaked streams remain registered until Fermat's later `writeOneOnOneData()` reaches the five-stream limit.

The bridge closes still-registered output streams at the start of the next round, after Tank Royale's internal round-ended handler has stopped and joined the previous main bot thread. This contains streams abandoned by legacy helper threads so they cannot accumulate into a later round's quota, while avoiding a close race with the finishing robot callback. The five-stream limit remains enforced within each round.

Three repair-linked 35-round official retests (`d5d2e35d49eabd9a`, `11ec421d416c630c`, and `ff31ace0ad193807`, all linked to repair `796a170`) completed with zero Tank Royale errors; classic also reported zero errors in each. No skipped-turn report was recorded. The results remain `DISCREPANCY (score)` at -43.2%, -41.2%, and -32.6%, so these runs verify the stream-error repair but do not resolve Fermat's score gap.

## Colossus2 scan-name identity

The official Roborumble observation for `apc.Colossus2_0.12.jar` had 180 Tank Royale `ArrayIndexOutOfBoundsException`s from `onScannedRobot`, while classic reported none (`aafa949ac07b6f62`). Bytecode inspection shows the callback compares `ScannedRobotEvent.getName()` to its 20 saved names using reference equality (`if_acmpne`). It appends each name it does not recognize to a 20-slot array, so repeated scans must carry the same `String` object for the same target.

Classic constructs scans with `RobotPeer.getNameForEvent(otherRobot)`, which returns the target's stable name held by `RobotStatics`. The bridge previously created `String.valueOf(scannedBotId)` for every event, producing a new object on each scan and causing Colossus2 to treat a known target as new. The mapper now resolves the classic name through Tank Royale's name map and interns the result; older Bot APIs without that map use an interned numeric fallback. Unit tests assert the mapped value and stable reference for both paths.

The repair-linked official 35-round retest (`9c81fee190364e22`, repair `255fcb3`) completed with zero errors on both engines and `PASS` status. Classic scored 11,561 and Tank Royale 10,070, a -12.9% delta within the registry's 25% threshold. The observation contains no turn-by-turn skipped-turn data, so it does not establish whether either engine skipped turns.

## Gir file-quota accounting

The official Roborumble observation for `ag.Gir_0.99.jar` completed on classic with no errors, while Tank Royale recorded 18 errors (`e3c870e44a2cc066`). Its log shows failed writes at the 200,000-byte quota followed by truncated `*_move.fct` and `*_robotstats.stt` files; Gir then reports EOF while loading movement factors and throws `ArrayIndexOutOfBoundsException` in `ag.movement.Movement.readMoveFactors`.

Classic's `ThreadManager.createRobotFileStream()` subtracts an existing file's length from quota usage before opening it for replacement (`append == false`), and checks the current quota before creating a new file. The bridge counted every write but never credited the old length of an overwritten file. Gir rewrites learned data files repeatedly, so its quota usage grew with historical bytes written instead of current file sizes. The bridge now performs classic's quota adjustment before opening the stream.

The FIO-003 two-engine regression rewrites the same 100,000-byte file twice and passes on classic and Tank Royale using the matched local bridge and Tank Royale 1.4.0 Bot API/runner. The first repair-linked official retest (`d61ab1a5cabc06c8`, repair `3878254`) removed the quota errors but remained `DISCREPANCY (outcome)` with 12 Tank Royale errors. Its first error was `IOException: Stream Closed` while writing `1_gun.net`, followed by EOF reads and null-network errors.

The second diagnosis, `round-end-stream-close-race`, identifies the remaining trigger. Tank Royale publishes the bridge's `RoundEnded` callback before its internal handler stops and joins the previous bot thread; the bridge closed open streams inside that earlier callback. Cleanup now runs on the next `RoundStarted`, after the previous main bot thread has stopped. The repair-linked official retest for this lifecycle-order correction is pending.

## Rejected interpretations

The analysis rejects treating every classic-only error in these melee runs as a defect in the measured subject, treating the pinned pool's prior roborumble `PASS` results as proof that its jars are clean in melee, and modifying the rumble jars to make the comparison complete.
