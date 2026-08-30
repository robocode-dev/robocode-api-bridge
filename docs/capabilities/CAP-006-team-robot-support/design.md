---
id: DES-006
type: design
status: draft
links: [CAP-006, ARCH-001, ARCH-002]
title: Team robot support — design
provenance: inferred
reversal-cost: high
---

# CAP-006 — design

`status: draft`, and thinner than its siblings on purpose: the central decision has not been made, and writing a confident design around an open choice would misrepresent it.

## What exists

Nothing. No source file in the wrapper mentions teams. The `robocode.*` surface reproduces `TeamRobot` and the messaging types because `ARCH-002` requires the surface to be complete, but nothing behind them is wired up.

## The open decision

A Robocode team is several robots that start together, address each other by name, and are scored as a unit. Tank Royale has its own team concept. There are two ways to bridge that, and they are not close:

**Map onto Tank Royale teams.** The engine handles membership and scoring; the bridge translates addressing and messaging. Less code, and the team semantics become the server's problem — which is the same reasoning that made `CAP-001` delegate event dispatch to the Bot API rather than reimplement it, and that reasoning has already paid once.

**Reconstruct above individual bots.** Each member is an ordinary Tank Royale bot, and the bridge builds messaging and team identity itself. More code and a second implementation of semantics the engine already has, but it does not depend on the two engines' team models agreeing.

The choice turns on how closely those models correspond, which nobody has checked. `M-005` starts by answering that, and the answer earns an ADR because it constrains everything afterwards.

## What is already known to be in the way

`ARCH-001`'s wrapper turns one jar into one bot directory. A team jar contains several robots and a descriptor, so the wrapper needs a shape where one input produces several coordinated outputs — including the case of a jar containing both team and individual robots.

Droids are the sharpest fidelity requirement here. A droid has no radar and receives no scan events, and getting that wrong makes the robot *better*: it gains information it should not have, wins more, and produces a battle in which nothing looks wrong. `TEAM-003` exists because that failure is silent in the direction the score-based instrument is least likely to question.

## Evidence plan

Classic's own test suite is thinner on teams than on events and physics, so the conformance tier will need purpose-written test robots here rather than ported ones. The team collection provides the population once the wrapper can produce bot directories at all.
