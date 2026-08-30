---
id: C-003
type: constraint
status: active
links: [CAP-005, CAP-007]
title: Each division is measured at its official rumble parameters, unmodified
source: The classic installation's rumble configuration — roborumble.txt, meleerumble.txt, teamrumble.txt
enforcement: agent
provenance: inferred
reversal-cost: high
---

# C-003 — Each division is measured at its official rumble parameters, unmodified

Comparisons between the engines use the parameters the official rumble uses for that division, read from the classic installation's rumble configuration:

| Division | Battlefield | Rounds | Bots per battle |
|---|---|---:|---:|
| One-versus-one | 800x600 | 35 | 2 |
| Melee | 1000x1000 | 35 | 10 |
| Team | 1200x1200 | 10 | teams |

## Why the parameters are not ours to choose

A robot's behaviour is tuned to its division. Melee movement is not one-versus-one movement, and a robot that surfs bullets against a single opponent may do something entirely different against nine. Measuring a melee robot in a one-versus-one setup produces a number that is neither wrong nor meaningful — it describes behaviour the robot was never ranked on.

Round count is the same argument applied to variance rather than to strategy. Thirty-five rounds is the official one-versus-one figure because that is roughly where a result stops being an accident, and the harness's own documentation already advised raising the count for stability without connecting it to the official setting.

Choosing our own parameters would also break the one comparison that costs nothing: a bot's rumble ranking is a large body of existing evidence about how that bot behaves, and it only applies to results measured the way the rumble measures them.

## Residual

Judgment holds the rule that a comparison is *reported* against its own division's parameters. A machine can pin the numbers; it cannot notice that a melee result was quietly discussed as though it were a one-versus-one result.

**Promotion trigger:** the division setup becoming a single named constant per division that every battle path reads, with the report stating the division and its parameters on every row. At that point the machine holds the numbers and the residual shrinks to interpretation.
