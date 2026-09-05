# Design

How the bridge works, capability by capability.

This folder holds no design documents of its own, and that is deliberate. Cliewen keeps feature-level design **inside each capability folder**, next to the criteria it realises, so that a change altering a capability's behaviour updates its criteria and its design in the same commit and `clue validate` can see that it did. A design document that lived here instead would drift from the criteria it describes, and nothing would notice.

What this page is, then, is the map: the shape the designs share, where each one lives, and the decisions that constrain all of them.

## The shape every design shares

The bridge is a translator sitting between two engines that disagree about almost everything superficial and agree about the game underneath. Every capability's design is a variation on the same three-part answer:

**What the bridge does not implement.** More of the system than a first reading suggests. Physics is simulated by the Tank Royale server; event priority and interruptibility belong to the Bot API's queue. The bridge computes no acceleration curve and holds no event queue of its own, and where it once did, `IDR-001` records why that ended: a second implementation of the engine's semantics is a second thing that can be wrong, and it made a real defect harder to find by giving every symptom two candidate explanations.

**What the bridge does implement.** Conversion and routing. Angles change frame, colours change representation, a Tank Royale event becomes the Robocode event class a robot expects, and a robot's call is routed to the Bot API method that performs it. This is a narrow layer with a wide surface, which is why it is where the two defects found so far both live.

**What the frozen surface forces.** `ARCH-002` fixes every signature in `robocode.*`, so when the classic API is awkward the design bends rather than the signature. `RobotStatus` taking its remaining-turn amounts in body/radar/gun order while its headings run body/gun/radar is exactly that, and `AN-005` is what happened when a call site quietly assumed otherwise.

## Where each design lives

| Capability | Design | What it is mostly about |
|---|---|---|
| `CAP-001` event dispatch parity | [design](../capabilities/CAP-001-event-dispatch-parity/design.md) | Delegating to the Bot API's queue instead of dispatching, and what that makes the bridge depend on |
| `CAP-002` physics and state parity | [design](../capabilities/CAP-002-physics-and-state-parity/design.md) | Why the criteria compare the engines rather than assert rates, and where a unit or frame error enters |
| `CAP-003` API surface fidelity | [design](../capabilities/CAP-003-robocode-api-surface-fidelity/design.md) | The mappers, why they stay dependency-free, and why that is the natural test seam |
| `CAP-008` call-routing fidelity | [design](../capabilities/CAP-008-call-routing-fidelity/design.md) | The test seam and recording fake, and why completeness is enforced by reflection rather than reviewed |
| `CAP-004` file I/O sandboxing | [design](../capabilities/CAP-004-robot-file-io-sandboxing/design.md) | One resolution point used by every wrapper, and why the robot is never told it was redirected |
| `CAP-005` score parity | [design](../capabilities/CAP-005-score-parity/design.md) | Self-play, the three shapes a finding takes, and what makes a baseline honest |
| `CAP-006` team robot support | [design](../capabilities/CAP-006-team-robot-support/design.md) | Native Tank Royale team mapping, roster staging, teammate messaging, and droid semantics |
| `CAP-007` the compatibility harness | [design](../capabilities/CAP-007-compatibility-harness/design.md) | Python orchestration over Java workers, per-instance logs, and why that split costs what it does |

## The decisions that cut across them

- `IDR-001` — events route through the Bot API's queue rather than a dispatcher of our own.
- `ADR-001` — the linked Bot API must preserve deferred same-priority events; the version that dropped them produced plausible scores for battles that were not Robocode.
- `IDR-002` — one question about where a robot's data lives should have one answer.
- `PDR-001` — evidence comes in three tiers, separated by what each can prove.

## Reading order

If you are new to the repository: [the architecture overview](../architecture/README.md) for the whole picture, then `ARCH-002` for the rule that constrains every change, then the design for the capability you are about to touch.

If you are about to change behaviour: the capability's `criteria.md` first, then its `design.md`. The criteria say what must stay true; the design says how it currently stays true, and only one of those is negotiable.

<!-- clue:index:start -->
<!-- clue:index:end -->
