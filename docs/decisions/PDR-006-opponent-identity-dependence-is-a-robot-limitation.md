---
id: PDR-006
type: decision
status: inferred
author: agent
accepted-by: []
links: [G-001, P-001, P-002, CAP-003, CAP-005, API-007, IDR-009, AN-045, AN-708, PDR-003]
title: Treat real-opponent-identity dependence as a robot limitation
---

# PDR-006 — Treat real-opponent-identity dependence as a robot limitation

## Decision

Preserve Tank Royale's opponent anonymity. A robot strategy that requires an opponent's real package, class, version, or identity is a documented robot compatibility limitation, not a requirement for the bridge or Tank Royale to disclose that identity. A demonstrated score difference caused by that dependence does not block preview delivery or require an engine repair.

Using an event name as an opaque key to track a robot remains supported. The bridge still owes the stable, distinct, Classic-shaped anonymous aliases required by API-007, consistent event identity, available mapped names, and the accepted teammate and messaging behavior. The anonymous alias does not claim to recover the real opponent's identity.

Attribute a case to this limitation only when source, bytecode, or a focused experiment demonstrates the dependency. A runtime exception or missing score is not excused merely because the robot uses names or is old; unexplained symptoms remain unresolved. Preserve all historical observations, append the new diagnosis and responsible component, and describe the limitation in user guidance. Never change a robot jar or substitute invented identities to hide the difference.

## Rationale and evidence

Classic already provides an opponent-anonymity setting. In the inspected [Classic event-name implementation](https://github.com/robo-code/robocode/blob/c981c51aecd819d4d09e7dcbf4280cfc9fb61e69/robocode.battle/src/main/java/net/sf/robocode/battle/peer/RobotPeer.java#L944-L949), `hideEnemyNames` selects anonymous opponent names while preserving teammate names. Its [default battle constructor](https://github.com/robo-code/robocode/blob/c981c51aecd819d4d09e7dcbf4280cfc9fb61e69/robocode.api/src/main/java/robocode/control/BattleSpecification.java#L35-L48) leaves that setting false. The bridge's Classic worker uses that default constructor; historical comparisons therefore need not have the same name visibility as Tank Royale.

Eraser's same-name self-check and GeneBot's name-dependent firing branch are documented in AN-708 and AN-045. Their score evidence does not imply a defect in the accepted anonymous alias. If a future experiment needs to isolate name effects, explicitly record and align the Classic name-visibility setting; retain earlier observations as measurements of their original configuration.
