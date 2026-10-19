---
id: AN-702
type: analysis
status: active
links: [P-001, PDR-001, AN-015, C-007]
title: Phoenix's null geometry argument is visible in bytecode, but its cause remains unresolved
provenance: inferred
reversal-cost: low
---

# AN-702 — Phoenix's null geometry argument is visible in bytecode, but its cause remains unresolved

## Risk investigated

Whether `meleerumble/davidalves.Phoenix_0.54.jar`'s current Tank Royale no-score result is a robot defect or a bridge discrepancy, and whether the exception identifies its cause.

## Evidence boundary

The read-only jar SHA-256 is `949e85553e857da7b1e1640f1723d97f1b45ec74d1072e7135033e315eb39cd9`. Its official MeleeRumble observation is `8886c50b55a9ad6b`, pinned to bridge commit `1c5d51d27c5f194e3ea0837e3eafc07339b4876e`, Tank Royale commit `ee7df6fef3b59383cc5783879441016854f7f2ce`, and Classic Robocode `1.11.1`; the battle used 35 rounds, ten participants, and a 1000×1000 arena. The local inspection used Temurin JDK 25.0.1 and `javap`; no jar contents were changed.

## What was tried

Classic scored 114,044 and recorded 172 errors attributed to the pinned melee opponent pool. Tank Royale produced no score and recorded two `NullPointerException` occurrences with origin `davidalves.A.A.B.A`; the message says parameter 1 is null while reading field `K`.

The jar metadata says source is not included. Bytecode for `davidalves.A.A.B.A(davidalves.A.A.B)` begins by dereferencing its argument's `K` and `I` fields, which matches the exception. The TeamRobot utility `davidalves.A.B.driveTo(davidalves.A.A.B)` calls that method with its target argument and contains no null guard. The captured registry observation does not include the complete caller stack, so it does not establish that `driveTo` was on the failing path or show where the null value originated.

## Finding

The exception is thrown inside Phoenix's own geometry code after it receives a null argument. That identifies the immediate failure site, not the producer of the null value or why the path differs between engines. I did not assign a robot or bridge cause: the Tank Royale-only no-score result could still be an engine-facing behavior mismatch that exposed this unchecked robot path.

The tempting alternative was to tag the error as robot-owned because the dereference is in the robot jar. That was rejected because the observation lacks the caller stack and does not show whether the bridge delivered a different event or value that led to the null argument.

## Implications for M-006

This official case remains unresolved. A follow-up needs a captured full caller stack and enough event/value evidence to identify where the null geometry argument is introduced; until then the registry cause stays unset. No bridge code changed, and the rumble jar remains read-only.

## M-006 handoff

Continue the next unregistered MeleeRumble cases. Return to Phoenix for targeted tracing before closing the unresolved discrepancy.
