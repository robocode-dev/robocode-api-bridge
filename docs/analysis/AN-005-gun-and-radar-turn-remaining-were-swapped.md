---
id: AN-005
type: analysis
status: active
links: [CAP-003, CAP-002, ARCH-002, PDR-001]
title: Gun and radar turn-remaining were swapped in the robot status, and the first unit test found it
provenance: inferred
reversal-cost: low
---

# AN-005 — Gun and radar turn-remaining were swapped in the robot status

## What was investigated

Nothing. This was found incidentally, on the first run of the first unit tests this repository has ever had, by an assertion written to check that three consecutive values of the same type had not been interchanged.

## What was found

`IBotToRobotStatusMapper` builds a `RobotStatus` through a seventeen-argument positional constructor. Three of those arguments are remaining turn amounts, and the constructor takes them in the order **body, radar, gun**.

That ordering is genuinely surprising. Everywhere else in the same constructor — and everywhere else in the Robocode API — the order is body, gun, radar. The headings three arguments earlier in the very same call use body, gun, radar.

The mapper passed body, gun, radar. So a robot asking its status for `getGunTurnRemaining()` received the radar's remaining turn, and asking for `getRadarTurnRemaining()` received the gun's.

Classic Robocode's own `RobotStatus` has the identical parameter order, verified in its source. The reproduced signature is faithful; `ARCH-002` fixes it and forbids changing it. The argument order at the call site was what was wrong.

## Why it survived until now

Nothing had ever looked. The bridge had no tests, and this defect is invisible to everything that existed:

It produces no exception, no log line, and no crash. Both values are plausible radian quantities in the same range, so nothing downstream can tell they have been exchanged.

It only affects robots that read these fields from the status object each turn — advanced robots running their own gun and radar control loops, which is a substantial minority of the collection and a disproportionate share of the strong ones. Such a robot mis-steers its gun or radar by exactly the other component's remaining rotation.

And its symptom is a slightly worse robot. Under the score-based instrument that is one more unexplained delta among several, in a report whose own guidance says to re-test before drawing conclusions.

## What it means

**A candidate cause for open score gaps.** `M-002` and `M-003` re-measure bots that score materially differently under the bridge, with no errors on either side. A robot whose radar control loop reads the gun's remaining turn is exactly the profile: it still fights, still scores, and aims worse. This is not a claim that it explains those bots — it is a named candidate to test against, which is more than any of them had.

**The tier ordering in `PDR-001` earned itself immediately.** The defect sits in a value conversion: the cheapest tier, the fastest to run, the one that names its own cause. Found by the sweep it would have been a percentage; found here it is a one-line diff.

**Positional constructors deserve distinct test values.** The assertion that caught this gave each field a different value specifically so a swap could not hide. Had the test used placeholder data — zeroes, or the same number repeated — it would have passed, and this document would not exist.

## What was done

The argument order at the call site was corrected and the comment at that site now states why the order is what it is, so the next reader does not "fix" it back.

This crossed the boundary `CH-001` set for itself, which said no production source under `robocode-api/src/main` would be modified. Crossing it was deliberate and is recorded rather than quietly folded in: the correction is a one-line positional swap restoring behaviour the reproduced API already specified, its evidence is the test that found it, and leaving it in place would have meant either shipping a knowingly red build or writing an assertion that certified the defect.

## What this does not establish

That the fix is complete. `RobotStatus` is one long positional call among several in this adapter, and the same class of error can exist anywhere a mapper fills a wide constructor. `CAP-003`'s criteria now cover the conversions that have tests; nothing yet covers the ones that do not.

Nor does it establish that any particular flagged bot is explained. That is `M-002` and `M-003`, with this as the first thing to check.
