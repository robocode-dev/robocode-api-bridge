---
id: AN-010
type: analysis
status: active
links: [CAP-003, CAP-008, G-002]
title: The unit tier's evidence tags mostly hold — two small gaps, and why the failure mode CH-003 found is concentrated elsewhere
provenance: inferred
reversal-cost: low
---

# AN-010 — The unit tier's tags mostly hold

## What was investigated

`G-002`'s extension from the conformance tier, which it audited, to the unit tier: every test under `robocode-api/src/test/java/dev/robocode/tankroyale/bridge/` against the `CAP-003` (`API-001`–`API-006`) and `CAP-008` (`ROUTE-001`–`ROUTE-012`) criteria those tests carry a tag for.

## What was found

**The tier is materially tighter than the conformance tier.** Every criterion ID has at least one test; no test carries an ID that does not exist in either criteria file; no criterion is silently uncovered. Assertions consistently name the specific Bot API method and argument value — `RecordingBot`'s routing tests assert `bot.onlyCall("forward").doubleArg(0)`, not merely that some call happened. That is the opposite shape from the conformance tier's `anyConsoleContains`, which is what let `EVT-003` and `EVT-005` pass while proving something other than their names. `ROUTE-011` (the reflective coverage check) and `ROUTE-012` (peer/status agreement) are both criteria with tests that do exactly what their scenarios say, and both carry a documented history — `AN-005`, `AN-007` — of the defect class they exist to catch.

Two gaps, both narrow.

**`API-001`'s round trip is unwritten because there is nothing to write it against.** The scenario says a converted angle, converted back, yields the original. `AngleConverter` has no reverse conversion method — only `toRobocodeHeadingRad` and `toRobocodeBearingRad`, both one direction, Tank Royale into Robocode. The outbound calls that send a robot's turn commands (`BotPeer.java:412,595`) convert only radians to degrees; they need no frame conversion because Robocode's turn commands and Tank Royale's are relative rotations in the same winding sense, unlike heading and bearing, which are absolute and wind oppositely. So the scenario's "converting it back" clause describes a symmetry that may not need to exist in this adapter at all — the asymmetry is architectural, not an oversight — but the criterion as written claims it, and nothing tests it either way.

**`API-004`'s stated negative case has no test; a different one runs under its name.** The scenario says a bullet whose *owner* is no longer in the battle still maps. `testAPI004_UnitNegative_MapsABulletThatHasNotHitAnything` tests a null *victim* — a bullet in flight that has not hit anyone yet — which is a different field. Reading `BulletMapper.map`, the owner is always `String.valueOf(bullet.getOwnerId())`; there is no lookup that could fail for a departed owner, so the scenario's claim holds by construction. But that is a reason the test is easy to skip, not a reason it exists — nothing currently proves it, and the test that carries the tag proves an adjacent thing instead.

## Why the tier held where the conformance tier did not

The conformance tier proves behaviour through a robot's own console output, because a robot's source is fixed by classic and cannot be extended with an assertion of choice — `anyConsoleContains` is close to the only vocabulary available. The unit tier drives a recording fake directly and can assert on the exact call and argument. The weaker vocabulary is where the tag-name mismatch surfaces; the stronger one mostly prevented it.

## What this does not establish

Whether every `ROUTE` and `API` scenario's Given/When/Then was compared word for word against its test's setup — the two gaps above were found by reading test bodies against scenario text for the criteria most likely to hide a mismatch (a two-way conversion, and a negative case with no obvious failure mode). A full line-by-line audit of all eighteen scenarios against all thirteen test files was not performed.

## What it means

`G-002`'s repair should not treat the unit and conformance tiers alike. The conformance tier needs new criteria for round/battle completion, a retag, and a decision on `EVT-003`'s name. The unit tier needs two small, independent fixes: either add the round-trip test (or narrow `API-001` to the one direction the adapter actually implements) and either add the owner-departed case (or narrow `API-004` to what `testAPI004_UnitNegative_MapsABulletThatHasNotHitAnything` actually proves and rename it). Neither changes a criterion's meaning in a way that needs `@retired`; both are the same class of repair as the `EVT-005` retag, just smaller.
