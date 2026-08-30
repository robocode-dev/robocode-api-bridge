# clue-extract mapping: ad-hoc markdown

Source mapping for [clue-extract](../skill.md) — the target contract in `skill.md` governs; this file only says what maps where for a repository whose specification corpus is ordinary project markdown rather than a structured format.

This is the common brownfield case and the hardest one to extract honestly, because an ad-hoc corpus has no IDs, no scenarios, no declared proof types, and no boundary between a promise and a note-to-self. Nothing here licenses inventing structure the source does not have. Where the source states no promise, the extraction mints no criterion.

Layout: there is none. The source is whatever markdown the repository already keeps — typically a project `README.md`, a prose `TODO.md` or `ROADMAP.md`, per-tool READMEs beside the tools they document, and generated reports that some script writes.

## Recognizing the source

Read every markdown file outside the corpus and classify it, because in this format a file's name tells you very little:

- **Descriptive prose** — what the system is and does. Usually the project README. Maps to goals and capability READMEs.
- **Intent prose** — what is wrong, what is next, what is deliberately not done. Usually a TODO. Maps to a plan, to capabilities, and to criteria.
- **Operational prose** — how to run something. Usually a tool README. Maps to a capability's design, and its documented invariants map to constraints.
- **Generated output** — a report some script writes. Never an artifact. It is *evidence*, and its generator is a capability.

The fourth class is the one that gets mis-extracted. A generated report looks authoritative because it is full of numbers, but it is a measurement taken at a moment, so it maps to evidence and to analysis findings, never to a durable claim.

| Ad-hoc markdown | Cliewen |
|---|---|
| Project README's purpose statement — what the repository is for | `G-xxx` goal, `status: accepted`; the repository's existence is the acceptance |
| Project README's component list or architecture diagram | `docs/architecture/` artifact, or capability `design.md` where it is capability-local; a Mermaid diagram is carried across unchanged |
| A TODO's ordered remaining work | one `P-xxx` plan; each item becomes a milestone, and the source's own ordering becomes the milestone order, because a priority list is a campaign someone already sequenced |
| A TODO item describing behavior the system should have | a capability, or a criterion under an existing one; the item's own words seed the Gherkin, and what it does not say stays unsaid |
| A TODO item describing work already finished | a decision record with `status: inferred`, routed by subject, plus its criteria under the capability it changed; a finished item is the strongest source in an ad-hoc corpus because it states both what changed and why |
| A TODO's diagnosis or measurement — "the numbers are noisy", "this predates the fix" | `docs/analysis/` finding, written once and never rewritten |
| A tool README's documented invariant — a version that must match, a threshold, a path that must hold | `C-xxx` constraint naming its `source` and its `enforcement` level |
| A tool README's usage and methodology sections | that tool's capability `design.md` |
| A generated report's rows | evidence, and its generator is a capability with its own criteria; the report file itself is never converted into an artifact |
| A generated report's flagged rows — failures, discrepancies, outliers | the pinned watch list the regression capability tests against, plus a criterion per class of failure |
| A test suite in a neighboring repository that already exercises the same semantics | recorded in the rehearsal as an evidence source with its revision pinned; it is not a source corpus, and extraction never edits it |

## Minting IDs

An ad-hoc source has no IDs to preserve, so every criterion is minted. Declare each capability's `ac-prefix:` from the capability's own subject rather than from anything in the source, take the requirements in the source's stated order, and start each namespace at one. Record the full minted mapping in the extraction report; there is no preserved column to record, and saying so explicitly is what keeps the report honest.

## The dispositions this format produces

Expect most criteria to be born `@draft`. That is the correct outcome, not a failure of the extraction: a repository that documented its intent in prose and verified it by running something and looking at the result has real promises and no attributable evidence, and the extraction's job is to say so per criterion rather than to average it away at the capability level.

Every `@draft` criterion names the plan milestone that will prove it. A source with no tests therefore produces a plan whose early milestones are about building evidence, and that shape is diagnostic rather than incidental.

Reserve `Test-type: Human` for a promise a person genuinely judges. A long-running local sweep whose report a maintainer reads before merging is Human evidence; a test someone has not written yet is `@draft`. Collapsing the second into the first is the characteristic failure of this format's extraction, because both feel like "no automated test exists".

## Carrier inventory

| Ad-hoc carrier | `kind` | Typical target |
|---|---|---|
| A pre-existing `AGENTS.md`, `CLAUDE.md`, or `.cursor/rules` | `instruction` | the repository's `AGENTS.md` routing hub, with compatible rules absorbed as repo-local conventions |
| An existing CI workflow | `workflow` | retained alongside the `clue` workflow; a workflow that checked something the corpus now owns is `blocked` until the corpus check replaces it |
| A version pinned in prose that must match a build artifact | `freshness-input` | the constraint that carries it, or `blocked` when nothing checks it |
| A TODO or roadmap acting as the repository's index of work | `registry` | the plan that replaces it; the source file is reduced to a pointer rather than deleted when it is also a reader's entry point |
| Every local or external markdown link the source carries | `link` | its rewritten target, or `blocked` when the mapping gap is still open |
| A Mermaid, ASCII, or SVG diagram | `diagram-asset` | its retained or converted location |

## Watch for

A TODO that has been edited in place over a long period, where a "done" section and a numbered priority list disagree about the same work — extract both and let the disagreement become a finding, rather than picking the one that reads more recent.

Prose that states a threshold without stating what enforces it. That is a constraint with `enforcement: human` at best, and writing it as `machine` because a script mentions the same number is how an unchecked rule acquires a false badge.

A README documenting a path or version as a default while the code resolves it differently. Record what the code does; the README is the thing that drifted.
