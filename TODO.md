# TODO

The work this file used to track now lives in the corpus, where each item is a promise with
acceptance criteria and a milestone rather than a paragraph.

- **What is planned, and in what order** — [`docs/plans/P-001`](docs/plans/P-001-bridge-parity-campaign.md). Its milestones are the items this file used to list, in the same priority order.
- **What the bridge promises** — [`docs/capabilities/`](docs/capabilities/README.md). One folder per capability, each with its criteria and design.
- **How the system fits together** — [`docs/architecture/README.md`](docs/architecture/README.md).
- **Why things are the way they are** — [`docs/decisions/`](docs/decisions/README.md), including the event-dispatch redesign and the Bot API upgrade this file used to describe.
- **What has been investigated** — [`docs/analysis/`](docs/analysis/README.md), including why the old score numbers cannot be used as a baseline.

New work starts by reading [`AGENTS.md`](AGENTS.md).

## Why this file is a pointer rather than a list

Two systems of record is zero systems of record. A TODO that restates the plan drifts from
it silently, and the drift is invisible precisely because both look authoritative.

This file survives as a pointer rather than being deleted because it is where a contributor
looks first.
