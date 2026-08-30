---
id: AN-008
type: analysis
status: active
links: [CAP-001, AN-006, PDR-001]
title: Tank Royale declares more round winners than the battle has rounds
provenance: inferred
reversal-cost: low
---

# AN-008 — Tank Royale declares more round winners than the battle has rounds

## What was investigated

Round outcomes, noticed while measuring `AN-006`. Classic's `BattleWin` robot prints a marker from `onWin` and from `onDeath`, so a battle's win and death totals can be counted from the two robot consoles. Once deaths began arriving under the bridge, the win totals became readable against them for the first time.

## What was found

A robot can win a round only once, so the wins in a battle can never exceed its rounds. Classic obeys that. Tank Royale does not.

Measured over five-round battles of `BattleWin` against a copy of itself, at the roborumble division, on 2026-08-30:

| Engine | Wins per battle | Deaths per battle |
|---|---|---|
| Classic Robocode | 5, 5, 5, 5, 5, 4, 5, 5, 4 | 5, 7, 6, 5, 5, 6, 6, 6, 6 |
| Tank Royale, current server | 5, 5, 5, 6, 7, 7 | none delivered — `AN-006` |
| Tank Royale, server with the `AN-006` repair | 4, 4, 5, 5, 6, 7 | equal to the wins, in every battle |

Classic never exceeded its round count. Tank Royale reached seven wins in a five-round battle, on both the repaired and the unrepaired server, so this is independent of `AN-006` and is not caused by its repair.

The likely shape: a round that ends in mutual destruction. Classic's deaths exceed its wins, which is what a mutual kill looks like when one robot is still declared the winner. Under the bridge the two totals are equal in every battle measured, which is what it looks like when both are declared winners.

## Why it was invisible until now

There was nothing to read the win totals against. With no deaths arriving, a battle's markers were wins and round ends only, and a win total of six in a five-round battle is not obviously wrong unless you are already counting. `AN-006` hid this one.

## What this does not establish

The cause. Nothing in the server has been traced; the shape above is inferred from the totals and from how classic's totals differ, not demonstrated. Whether the extra winner also receives the round's score, and therefore whether this moves rumble results, is unmeasured — and that is the question that decides how much it matters.

It is also not established which engine is right about the scoring rule, only that they differ. Classic is the specification, so classic is right by definition about what a robot observes; whether Tank Royale intends a different rule for its own bots is a question for that project.

## Where it goes

`EVT-005` covers round and battle completion reaching their handlers and is proven. No criterion covers how many robots may be declared the winner of a round, so nothing currently fails because of this. A criterion for it belongs to `CAP-001` and needs the cause established first, because a criterion written against inferred behaviour would encode the guess.
