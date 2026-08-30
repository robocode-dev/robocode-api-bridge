---
id: AN-001
type: analysis
status: active
links: [CAP-005, CAP-007, C-004, ADR-001]
title: Single-battle score deltas are uninformative for low-scoring bots
provenance: inferred
reversal-cost: low
---

# AN-001 — Single-battle score deltas are uninformative for low-scoring bots

## What was investigated

Whether the score deltas in the compatibility report can be read as evidence about individual bots.

## What was found

For at least one bot, no. Its classic-side score swung between 6 and 274 across runs — a factor of over forty, on the classic engine alone, with no bridge involved. Its reported delta against Tank Royale was over two thousand percent.

A percentage delta computed against a base of six is arithmetic performed on noise. The number is real, reproducible in form, and says nothing about the bridge.

The report's own documentation already stated the general case: a healthy robot can swing by twenty to thirty percent at ten rounds, and the round count should be raised for stable numbers. What had not been connected is that the recommended count is the official rumble figure, so the harness had been measuring below its own advice and below the standard the bots were ranked under. `C-003` closes that.

A second and larger finding sits behind the first. Every score in the current report predates the event-dispatch redesign and the Bot API upgrade, and the queue defect `ADR-001` describes made robots lose scan events wholesale. Those numbers do not describe the current bridge, so the population is not merely noisy — it is stale.

## What it means

**A single battle is not evidence about a bot.** `C-004` requires repeats to be averaged before a delta is judged, and states the gate as movement from a baseline rather than as absolute delta, because a bot that has always differed by a given margin and still does has not regressed.

**Some bots cannot be gated on at any repeat count.** A bot whose score is a handful of points has no stable percentage. The watch list marks these as noise: they are reported and never fail the run, because a gate that fires on the same bot every time is a gate people learn to ignore.

**The existing flagged bots are a reason to look, not a baseline.** `P-001` sequences re-measurement before conclusions for exactly this reason, and `M-006` produces the first baseline that means anything.

## What this does not establish

That the flagged bots are fine. Noise explains why their current numbers cannot be trusted; it does not explain them away. Bots scoring lower and bots scoring higher both remain open, under `M-002` and `M-003`, and the second group is the easier one to dismiss wrongly — a robot that performs better under the bridge is a fidelity defect that flatters itself.
