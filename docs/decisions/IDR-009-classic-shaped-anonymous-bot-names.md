---
id: IDR-009
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-003, AN-045]
title: Give unmapped bot IDs Classic-shaped anonymous names
---

# IDR-009 — Give unmapped bot IDs Classic-shaped anonymous names

Tank Royale's bot-name map omits opponents, while legacy Robocode event names use a package, class, and optional version form. Passing a bare numeric ID into those events can make a robot fail when it parses the name, but the bridge cannot reconstruct an opponent's hidden identity.

The bridge preserves every mapped name and assigns an unmapped bot ID the deterministic alias `tankroyale.anonymous.Bot<id> 1.0` wherever a bot ID becomes a Classic name. This gives legacy parsers a familiar name shape without claiming that the alias identifies the real robot.

The alias can prevent failures caused by parsing a numeric name, but name-based target selection still cannot match Classic when the robot depends on the opponent's actual identity. `AN-045` records the evidence for the hidden-name boundary.
