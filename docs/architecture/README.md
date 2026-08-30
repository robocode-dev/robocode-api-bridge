# Architecture

The shape of the whole: structure, actors, module boundaries, technology commitments — the things that are expensive to change.

Architecture documents describe the system, not individual features (feature-level design lives in each capability's `design.md`). A change updates these documents when it alters the system's structure or public surface; the decision behind such a change is recorded as an ADR in `decisions/`. Documents follow the default lifecycle `draft` → `active`; retiring one means deleting its file and naming its ID in a successor's `supersedes:` field. An agent-extracted document is born `provenance: inferred` with `reversal-cost: low|high` and is promoted to `provenance: verified` by human review.

<!-- clue:index:start -->
- [ARCH-001 — Bridge runtime topology](ARCH-001-bridge-runtime-topology.md) · `active` — The hand-off chain from robot jar to server, and the process boundaries that make a compatibility defect hard to localise.
- [ARCH-002 — The frozen Robocode API surface](ARCH-002-frozen-robocode-api-surface.md) · `active` — Why no signature in `robocode.*` may ever change, and where the implementation behind it is free.
- [ARCH-003 — The two-engine evidence topology](ARCH-003-two-engine-evidence-topology.md) · `active` — Where each test tier runs, which installations it needs, and the isolation model that survives untrusted robots.
<!-- clue:index:end -->
