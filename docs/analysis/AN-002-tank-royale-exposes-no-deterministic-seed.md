---
id: AN-002
type: analysis
status: active
links: [CAP-002, ARCH-003, PDR-001]
title: Tank Royale exposes no deterministic random seed, so exact-value comparison cannot cross engines
provenance: inferred
reversal-cost: low
---

# AN-002 — Tank Royale exposes no deterministic random seed

## What was investigated

Whether a battle can be made reproducible on both engines, so that the strongest available fidelity evidence — the same robot at the same coordinates on the same turn — could be compared directly.

## What was found

**Classic can.** It reads a seed from a system property and resets its random number generator deterministically before the battle. Its own conformance suite relies on this: tests assert that a robot is at a specific coordinate to seven decimal places on a specific turn, and that a battle lasts an exact number of turns.

**Tank Royale cannot.** Neither the server nor the runner exposes a seed, and nothing in either accepts one.

The asymmetry is total. There is no partial workaround: a battle that starts from a random placement diverges immediately, and after a few turns two runs of the same robot on the same engine share nothing comparable.

## What it means

**The strongest class of conformance test cannot be ported.** Classic's exact-position, exact-score, and exact-turn-count tests stay classic-only. `PDR-001` records this as a rejection forced by a missing upstream capability rather than by a judgment about the tests.

**Nothing is minted for it.** No criterion asserts exact cross-engine position parity, because a criterion nobody can ever satisfy is a permanent draft dressed as a promise. The gap is recorded here instead.

**The conformance tier is shaped around the constraint.** It compares what a robot *reports* — markers, counts, sequences it prints — rather than where it ends up. That is why most of classic's test robots port and the coordinate assertions do not, and it is a narrower instrument than the alternative.

**The sweep is statistical by necessity, not by choice.** `C-004`'s averaging exists partly because this is unavailable. With seeds on both sides, a regression check could compare exact outcomes and the whole apparatus of repeats and bands would be unnecessary.

## What would change this

A seed in the Tank Royale server. It is server-side and does not touch the Bot APIs, so `C-006`'s four-language obligation does not apply — this is a smaller change than most Tank Royale work.

The payoff would be large and is worth stating plainly, because the cost of the current arrangement is easy to underestimate. Seeded battles would make the whole sweep reproducible rather than statistical, would let classic's exact-value tests port unchanged, and would turn every noisy comparison in `AN-001` into an exact one.

That is a Tank Royale change and not this repository's to make. Recorded here so the option is visible to whoever weighs it.
