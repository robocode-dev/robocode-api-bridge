# The corpus

This directory is the **system-of-record**: the permanent, durable truth about the system. Full Cliewen changes are transient deltas on branches that get **digested** into this corpus at merge — `git log docs/` is the audit trail. Entry point for humans and agents alike; agents treat this tree as working memory when a change affects product or methodology meaning. Simple work uses no Cliewen workspace but still leaves any corpus surface it touches truthful.

## How the corpus is wired

Every artifact carries YAML frontmatter with a common core — `id`, `type`, `status`, `links`, `title` — plus small type-specific extensions. **Identity is the ID, the path is only the current address**: tooling discovers artifacts by scanning frontmatter, and external systems reference IDs, never paths.

An extracted non-decision carries `provenance: inferred` and `reversal-cost: low|high`; high-cost inferred meaning blocks an active capability joined to it by one `links:` edge, while low-cost findings may remain deferred. Decisions carry provenance in `status`. An incident analysis where the corpus was green but reality disproved a claim carries `reality: contradicted` and links the failed capability or acceptance criterion; `clue validate --reality-gaps` derives the affected-capability view.

The red thread `clue validate` walks:

```
G-xxx (goal) → P-xxx/M-xxx (plan/milestone) → CH-xxx (change)
  → CAP-xxx (capability) → AC-xxx (acceptance criterion) → acceptance evidence
    → classified Go/JVM/Cucumber test reference, or Human acceptance brief
```

Cross-cutting, checked against every proposal: C-xxx (constraints, including verifiable quality bars).

When a released `clue` adds or narrows a corpus obligation, preview `clue migrate` and apply its complete plan only after resolving any semantic or local-edit findings. `clue init` remains a non-destructive materializer, not an updater.

## What lives where — and when a change updates it

Each folder below holds one kind of record. A full change (the `clue-delta` loop) updates every record its work touches in the same integration; simple work remains responsible for any durable record it touches even though it uses no workspace or digest:

- **Goals** (`goals/`) — who wants the system and why. A new wish enters here as `status: proposed`; a change rarely touches goals.
- **Plans** (`plans/`) — campaigns with verifiable milestones. Every full change names the plan item it serves (or declares itself plan-less); the digest updates plan bookkeeping, including closing a plan whose last milestone the change completes.
- **Capabilities** (`capabilities/`) — one folder per capability: `README.md` (what and why), `criteria.md` (acceptance criteria as Gherkin, each tied to its declared acceptance evidence), `design.md` (how it works). **Design is documented per capability** — a change that alters a capability's behavior updates its criteria and design in the same PR.
- **Architecture** (`architecture/`) — the shape of the whole: the expensive-to-change. Updated when a change alters the system's structure or public surface, not for local detail.
- **Decisions** (`decisions/`) — why future work is constrained. A future-shaping choice routes by subject: **ADR** for software or corpus architecture, **PDR** for project/process or methodology, **IDR** for implementation. Routine facts and chronology stay in their natural carriers.
- **Constraints** (`constraints/`) — rules the system must not break: laws, licenses, policies, and verifiable quality bars (a coverage floor, a response-time bound). Each names its `source` and how it is `enforcement`-checked. Updated when the outside world imposes something or a quality bar moves.
- **Analysis** (`analysis/`) — findings from spikes and extractions. Historical records: written once, never rewritten.

## Status vocabularies

**The default lifecycle is `draft` → `active`.** It applies to every artifact type, including your own — a type `clue validate` does not recognize is validated against this default, so you can add your own artifact types under `docs/` without changing the tool. Only the types below differ, each for a stated reason. There is no `retired`: retiring a default-lifecycle artifact means deleting its file and naming it in a successor's optional `supersedes: [ID, …]` frontmatter field, whose named ID must no longer exist in the corpus — retirement is an event, not a status a file rests in.

| Type | Statuses | Why not the default |
|---|---|---|
| goal | `proposed` → `accepted` | proposed goals are the inbox |
| plan | `draft` → `active` → `completed` | `completed` is immutable |
| decision | `inferred` → `verified` | provenance lives in status; human acceptance promotes |
| change, tasks | `open` | transient workspace artifacts |
| open-questions | `open` → `resolved` | transient workspace artifacts |

## Folders

<!-- clue:index:start -->
- [goals/](goals/README.md) — G-xxx: who wants it, why
- [plans/](plans/README.md) — P-xxx: campaigns and milestones
- [capabilities/](capabilities/README.md) — CAP-xxx: one folder per capability (README / criteria / design)
- [architecture/](architecture/README.md) — the whole, the expensive-to-change
- [decisions/](decisions/README.md) — ADR-xxx, PDR-xxx, and IDR-xxx future-shaping choices
- [constraints/](constraints/README.md) — C-xxx: laws, licenses, policies, and verifiable quality bars you must not break
- [analysis/](analysis/README.md) — spike findings, extraction reports
<!-- clue:index:end -->
