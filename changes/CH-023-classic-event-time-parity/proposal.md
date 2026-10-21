---
id: CH-023
type: change
status: open
links: [M-006]
title: Keep bridged event times on Classic's turn scale
---

# Keep bridged event times on Classic's turn scale

M-006 compares legacy robots on Classic Robocode and Tank Royale to find observable bridge differences. The bridge already maps Tank Royale turn 1 to Classic robot time 0 for status, but event mappers exposed raw Tank Royale turn numbers. Classic's `EventManager` stamps queued events with the robot's current round time, so a scan and teammate message from the same turn must use the same zero-based time as status.

This change establishes that bridge-produced Classic events report the same zero-based time as the robot status for the corresponding Tank Royale turn. It applies that conversion consistently wherever the bridge exposes a Tank Royale event as a Classic event, and adds focused evidence for the rule.

## Commitment challenge

The main assumption is that Tank Royale event turn numbers use the same one-based clock as bot status and that Classic event time is zero-based. The status mapper already encodes that offset, and Classic source confirms that events receive the current robot time when queued. A credible alternative is that some Tank Royale event types use a different turn convention. Unit evidence checks scan and teammate-message times against status at the first and a later turn; the code audit covers the remaining event paths. The official Krabb retest still produced the same `LStats.newChangeStats` exception, so this change does not claim to repair that case; M-006 keeps it open for separate diagnosis.

An implementation could satisfy the timestamp assertions and still fail a robot owner if events arrive in a different order from Classic. This change preserves event delivery order and corrects the event time exposed through the frozen API. The Krabb replay is retained as an M-006 observation, and its unresolved message-history exception remains a separate investigation.

## Scope

- Add API-008 to CAP-003 for translating Tank Royale event turn numbers to Classic time.
- Apply the existing one-based-to-zero-based conversion to Classic event objects created by mapper and callback paths, including skipped-turn and custom events.
- Add positive and negative unit evidence covering the event-time conversion and export it in the executable evidence manifest.
- Retest the official Krabb Slartibartfass team case, record the unsuccessful hypothesis and result, and keep the original rumble jar read-only.
- Update `CHANGELOG.md` and the CAP-003 criteria documentation.

## Durable documentation

CAP-003 will state the event-time behavior explicitly. No architecture overview change is expected because the adapter boundary and runtime topology stay the same.
