---
id: CH-023
type: change
status: open
links: [M-006]
title: Keep bridged event times on Classic's turn scale
---

# Keep bridged event times on Classic's turn scale

M-006 compares legacy robots on Classic Robocode and Tank Royale to find observable bridge differences. The bridge already maps Tank Royale turn 1 to Classic robot time 0 for status, but event mappers currently expose raw Tank Royale turn numbers. A team robot has now failed after combining a scan recorded at turn 1 with teammate stats recorded at Classic time 0; its history code treats the older message as a later update and throws a NullPointerException.

This change establishes that bridge-produced Classic events report the same zero-based time as the robot status for the corresponding Tank Royale turn. It applies that conversion consistently wherever the bridge exposes a Tank Royale event as a Classic event, and adds focused evidence for the rule.

## Commitment challenge

The main assumption is that Tank Royale event turn numbers use the same one-based battle clock as its bot status and that Classic event time is zero-based. The existing status mapper already encodes that offset, and the Krabb replay's stack trace reaches the failing robot code from both scanned-event and teammate-message callbacks. A credible alternative is that only scan timestamps need rebasing because some event types use a different turn convention. The cheapest useful test is a unit matrix over every mapped event type at turns 1 and 4, followed by an official-parameter Krabb replay. Narrow or stop the change if the local Tank Royale event definitions show a different convention for any mapped type, or if the replay shows that the corrected timestamps make callback history less consistent with Classic.

An implementation could satisfy the timestamp assertions and still fail a robot owner if events arrive in a different order from Classic. This change preserves event delivery order and only corrects the event time exposed through the frozen API; the Krabb replay checks whether that is sufficient for its observed failure, while other ordering differences remain diagnosable M-006 cases.

## Scope

- Add API-008 to CAP-003 for translating Tank Royale event turn numbers to Classic time.
- Apply the existing one-based-to-zero-based conversion to Classic event objects created by mapper and callback paths, including skipped-turn and custom events.
- Add positive and negative unit evidence covering the event-time conversion and export it in the executable evidence manifest.
- Retest the official Krabb Slartibartfass team case and record its diagnosis and result without modifying the rumble jar.
- Update `CHANGELOG.md` and the CAP-003 criteria documentation.

## Durable documentation

CAP-003 will state the event-time behavior explicitly. No architecture overview change is expected because the adapter boundary and runtime topology stay the same.
