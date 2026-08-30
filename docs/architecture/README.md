# Architecture

The shape of the whole: structure, actors, module boundaries, technology commitments — the things that are expensive to change.

Architecture documents describe the system, not individual features (feature-level design lives in each capability's `design.md`). A change updates these documents when it alters the system's structure or public surface; the decision behind such a change is recorded as an ADR in `decisions/`. Documents follow the default lifecycle `draft` → `active`; retiring one means deleting its file and naming its ID in a successor's `supersedes:` field. An agent-extracted document is born `provenance: inferred` with `reversal-cost: low|high` and is promoted to `provenance: verified` by human review.

<!-- clue:index:start -->
<!-- clue:index:end -->
