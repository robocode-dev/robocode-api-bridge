---
id: DES-005
type: design
status: draft
links: [CAP-005, CAP-007, ARCH-003, C-003, C-004, AN-001]
title: Score parity across the rumble collections — design
provenance: inferred
reversal-cost: low
---

# CAP-005 — design

`status: draft`: the measurement described here is partly implemented and partly intended.

## What is compared

Per robot, per division: the sum of both participants' total scores from a robot-versus-itself battle on each engine, and the delta between the two engines expressed as a percentage.

The self-play arrangement is worth explaining because it looks odd. Pitting a robot against a copy of itself removes the opponent as a variable — both engines run the identical matchup — and it means a divergence in the robot's behaviour shows up symmetrically rather than being absorbed by an opponent that also behaves differently. It also scales: no pairing matrix, one battle per robot per engine.

Melee changes this. The official melee setup is ten participants, and ten copies of one robot is a legitimate battle but a peculiar one. Whether the melee sweep uses self-play or a fixed opponent set is an open design question `M-006` must settle; `SCORE-006` is written to require that the division is measured, not how.

## The three shapes of finding

The design separates them because they need different handling, and conflating them is what makes the current report hard to act on.

**A score delta** is a quantity subject to noise. `C-004` handles it by averaging repeats and comparing movement from a baseline. `AN-001` records the bot that demonstrates why a single battle is not evidence.

**A Tank-Royale-only exception** is a categorical fact. It ends the battle immediately, as `C-004` requires. The asymmetry is the point: averaging improves a noisy quantity and does nothing for a fact.

**A failure to complete** — a hang, a crash, a timeout — is a third thing again, attributable to a side but not to a cause. `ARCH-003`'s isolation model exists so that this case is recorded rather than taking the sweep down.

## Baselines, and what makes them honest

The regression gate compares against recorded baselines, so the baselines carry the weight. Each watch-list entry records its division, its measured baseline, and a state: `open` where the divergence is unexplained, `fixed` where a cause was found and closed — kept on the list so it stays closed — and `noise` where the bot's score is meaningless at any repeat count.

The `fixed` state is the one that earns its keep. A defect that was found, fixed, and then removed from the watch list is a defect free to return unnoticed, and this project has already had one engine-level defect produce divergence across many unrelated bots.

## Why the current numbers cannot be a baseline

Every flagged bot in the existing report was measured before the event-dispatch redesign and the Bot API upgrade, at ten rounds rather than the official thirty-five, in a setup matching no division exactly. Those numbers are the reason to look, not something to measure against. `M-006` produces the first real baseline, which is why it closes the campaign rather than opening it.
