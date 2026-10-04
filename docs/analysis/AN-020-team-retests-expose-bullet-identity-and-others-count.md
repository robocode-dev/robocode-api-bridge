---
id: AN-020
type: analysis
status: active
links: [P-001, CAP-001, CAP-003, CAP-006, CAP-007, CAP-008, AN-019]
title: Team retests expose inconsistent names, bullet identity, and other-robot counts
provenance: inferred
reversal-cost: low
---

# AN-020 — Team retests expose inconsistent names, bullet identity, and other-robot counts

## Scope

The next M-006 batch after CombatTeam covers the five remaining failure or missing-score subjects diagnosed with `nested-team-jar-discovery`: FirestarterTeam 2.0, MalackaTeam 1.2, Xmen 0.9, TidalWave 0.8, and SuperSittingDuckTeam 1.0.2. The wrapper now discovers their nested robot jars. The retests use official teamrumble parameters, classic Robocode 1.11.1, and matching local Tank Royale 1.4.0 Bot API and runner artifacts containing the projectile-collision repair in AN-019. The collection jars remain read-only. The tracked registry retains each observation and its artifact hashes.

## Harness cleanup

The first retest stopped in Python rather than recording the bot failure: unsuccessful Windows `taskkill` output was bytes, but the cleanup diagnostic joined it as text. Commit `266b1ce` requests decoded text with replacement for undecodable characters, preserving the existing cleanup timeout and fallback behavior. The restarted five-case batch completed and recorded every result.

## Names crossing event boundaries

FirestarterTeam initially failed at `cb.fire.Firestarter.process` while looking up robot data for bullet-hit or hit-by-bullet callbacks. Its bytecode keys that data by robot name. Scans and team messages used known classic names, but bullet owners and victims, collisions, and death callbacks still used numeric identifiers. Commit `7d4438b` applies the existing name resolver to those surfaces, including pending event lists. Opponent identifiers still use the existing numeric fallback when Tank Royale does not provide a name. The subsequent Firestarter observation no longer reported the original lookup signature, but exposed `IndexOutOfBoundsException` at `C.L.B`; the name repair alone did not close its case.

## Bullet identity and blocking fire

Malacka stores firing statistics in a `HashMap` keyed by the returned `Bullet`, then retrieves them with `BulletMissedEvent.getBullet()`. The bridge changed the returned object's hash code when the server assigned its projectile id and created a different object for missed and hit callbacks. Commit `e28f76f` separates the immutable legacy bullet id from the transport id and reuses the returned object for terminal callbacks and event-list mapping. Server ids are assigned only to pending shots and retained in a per-round lookup.

Malacka still failed after that repair. It calls blocking `Robot.fireBullet()`, whose bridge implementation previously called the Bot API's blocking fire before creating the legacy bullet object. The firing event could therefore arrive before registration, and a rejected shot still returned an untracked bullet. Commit `673b300` registers with `setFire()` before advancing the turn and preserves its null result when firing is rejected. Malacka then completed one official pair without errors at −1.0%, followed by five official pairs without asymmetric errors at −3.5%, −5.2%, −4.3%, −4.1%, and −3.1%; the mean was −4.04%, classified `MATCHED (score noise)`. This evidence predates the later other-robot-count repair and should not be described as confirmation of that later build.

The final combined bridge build at `16e8a26`, paired with Tank Royale `107941888`, also completed five official pairs without asymmetric errors: −4.1%, −5.8%, −1.8%, −3.0%, and −3.4%. Their mean is −3.62%, classified `MATCHED (score noise)` in observation `cbb87704154d6859`. This confirms Malacka on the build that includes both the counting and scan-timing corrections.

## Other-robot counts

TidalWave allocated its survival array using `getOthers() + 1`, but subsequently received an index of 8 for an array of length 6. Classic's `Battle.computeActiveRobots()` counts every living non-sentry robot, including teammates; `RobotPeer` subtracts the caller only while it is alive. The bridge instead returned Tank Royale's opponent-only count. Tank Royale also derives a dead bot's team from its living-bot map, so its final tick can count surviving teammates as opponents after that map no longer contains the caller.

Commit `9fd1be2` initializes a team's classic count with opponents plus teammates, tracks other robots' death events, and exposes the same count in `getOthers()` and `RobotStatus`. This restores the classic counting model without changing Tank Royale's opponent-count API. The count resets each round. The current registry must be consulted for the subsequent four-case retest.

Commit `16e8a26` confines count updates to delivered status ticks and callbacks, rather than inspecting future events from a later network tick during an earlier robot callback. The next TidalWave run no longer reported the earlier survival-array or statistical-gun indexing signatures, but failed with `NullPointerException` at `kawigi.sbf.FloodHT.run` because its target was null. This is a remaining lifecycle investigation, not proof that the entire team now matches.

## Scan timing in Tank Royale

Classic updates projectiles before robot scans, and `RobotPeer.performScan` excludes dead scanners and targets. Tank Royale scanned before projectile damage and inactivity damage, then emitted death events from the final turn state. That can deliver both a scan and a death for the same target on one tick, and scan energy can precede the damage reported by that tick's other events. The local Tank Royale commit `107941888` moves scanning after turn damage and excludes dead scanners and targets. Its `CHANGELOG.md` entry records the observable fix. This is a separate branch awaiting integration approval; the earlier approval of the duplicate-projectile repair does not authorize this change's push.

The four-case retest with this runner still found Firestarter's `C.L.B` empty-history failure, Xmen's scan-wave failure, and TidalWave's target failure. The source correction is not evidence that those intermittent robot failures are eliminated. Both the Bot API and runner were rebuilt locally from this checkout before the retest.

## Previously flagged team scores

Valkiries 1.0 previously had a single-pair score difference of +34.7%. On bridge `16e8a26` with Tank Royale `107941888`, five official pairs produced −4.1%, −3.9%, −6.2%, −1.8%, and −4.5%, without asymmetric errors. The mean is −4.10%, classified `MATCHED (score noise)` in observation `ec40d37777c4769c`. The combined repairs and repeated evidence close this score review; this does not isolate which individual repair changed its earlier result.

DemoniacNimrods 0.50 previously had a single-pair difference of +46.6%. On the same combined build, its five official pairs produced +17.7%, +14.9%, +12.1%, +20.1%, and +12.7%, without asymmetric errors. The mean is +15.50%, classified `CONFIRMED (score)` in observation `f9da598653e8d726` by the existing repeated-score rule. This is an unresolved systematic score difference; neither its smaller magnitude nor clean exception signatures establish parity. Its diagnosis owner remains unknown.

## Registry handoff

The batch appends observations for exactly seven subjects and preserves all previous observations unchanged. Diagnosis events distinguish the repaired Malacka bullet identity and blocking-fire fault from unresolved Firestarter wave history, Xmen comparator behavior, TidalWave target lifecycle, DemoniacNimrods score behavior, and SuperSittingDuck's symmetric runtime failures. The unresolved labels describe observed failure surfaces rather than asserting a bridge or engine root cause.

## Remaining boundaries

Xmen reports `NullPointerException` at `florent.XSeries.team.Xmen.removeBullet` on classic as well as Tank Royale. Its latest Tank Royale observation additionally reports `IllegalArgumentException` at that method and at `florent.XSeries.gun.patternrecognition.ScanStore.mostSimilarScans`; those asymmetric signatures remain open. The earlier end-round failure is retained in history but was not reproduced in this latest pair. SuperSittingDuck throws `RuntimeException` at `mn.SuperSittingDuck.run` 100 times on both engines and scores zero on both. Its current `DISCREPANCY (no score)` classification is not evidence of an asymmetric robot exception; changing how zero-score failures are classified would require a separate methodology decision. A successful single score comparison is not five-pair score confirmation, and none of these observations closes M-006 for the whole collection.
