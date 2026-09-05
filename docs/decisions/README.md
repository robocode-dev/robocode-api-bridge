# Decisions

Why future changes are constrained — each future-shaping choice is routed by subject to exactly one concise record.

- **ADR-xxx** — Architectural Decision Records: software structure and corpus-format choices.
- **PDR-xxx** — Project/Process Decision Records: choices about how the project or its methodology works.
- **IDR-xxx** — Implementation Decision Records: implementation choices that constrain future implementation.

Subject alone selects the type. Reversal cost does not route a record, and routine facts, chronology, and implementation history are not decision records. A rejected future-shaping choice routes by the same subject test.

A new or modified record keeps enduring context and the decision. Add considered alternatives only when they materially explain the choice, and consequences only when they help a future reader act on it. Triggering incidents, review history, carrier inventories, and implementation walkthroughs belong in analysis, the change workspace, the pull request, or Git history.

Decision records carry provenance in their status: a record an agent writes during a change starts `inferred`; explicit human approval (review approval or a stated "approved") promotes it to `verified`, with each approver signed in `accepted-by:`. Merging a change makes its decisions binding either way — approval changes their standing, not their force. Rejected records stay in the corpus as history, and a rejection that would be expensive to revisit is itself a decision that earns its own record rather than a paragraph in a findings document.

`accepted-by:` records only approval given under this repository's own merge boundary, never acceptance a record already carried before it entered the corpus. A record extracted from a source with its own acceptance history preserves that history as body prose with its original names, roles, and dates, and keeps `accepted-by: []`, exactly the shape any unsigned record already has.

A decision that adopts a well-established practice cites it by name and records only the local why.

Decision records are timeless: state what is decided and only the enduring context and rationale needed to understand it; include a historical fact only when removing it would make the decision unintelligible.

A decision that changes a methodology contract inventories every live carrier that states the affected contract and updates that complete inventory in the same change. Live carriers include current corpus truth, canonical and generated skills, templates, public or contributor guidance, implementation explanations, CLI text, and distribution metadata. Historical analyses, completed plans, and changelog entries remain pinned history. Focused guards hold stable repaired claims, but no current mechanism derives an arbitrary contract's complete carrier set, so the general obligation remains agent-enforced.

<!-- clue:index:start -->
- [ADR-001 — Link a Bot API whose event queue preserves deferred same-priority events](ADR-001-upgrade-to-bot-api-1-0-2.md) · `verified` — Why 0.33.1 is forbidden: it discarded deferred events, and produced plausible scores for battles that were not Robocode.
- [IDR-001 — Route robot events through the Bot API's event queue](IDR-001-route-events-through-the-bot-api-event-queue.md) · `verified` — Why the bridge delegates dispatch instead of holding a second implementation of the engine's event semantics.
- [IDR-002 — getDataDirectory resolves through the same robot data lookup getDataFile uses](IDR-002-data-directory-resolves-through-robot-data.md) · `verified` — One question about where a robot's data lives should have one answer. Alignment, not confinement.
- [PDR-001 — Evidence comes in three tiers, separated by cost and by what each can prove](PDR-001-three-tier-evidence-strategy.md) · `verified` — Unit, conformance, and sweep: what each tier can establish, and what is rejected because Tank Royale has no seed.
- [IDR-003 — EVT-003's higher-priority claim is retired; interruptible re-entry evidence is scoped to what classic's own robot proves](IDR-003-evt-003-scoped-to-what-classic-actually-proves.md) · `inferred` — `EVT-003` retires.
- [PDR-002 — Conformance uses locally built Tank Royale artifacts rather than waiting for releases](PDR-002-locally-built-tank-royale-artifacts-for-conformance.md) · `verified` — When bridge conformance needs a Tank Royale repair that is not released, build the Tank Royale Bot API and runner locally from the same upstream revision and use that pair for the comparison.
- [IDR-004 — EVT-007's cross-engine death-order claim is retired; survivor delivery is measured directly](IDR-004-evt-007-scoped-to-observable-survivor-delivery.md) · `inferred` — Retire `EVT-007` and mint `EVT-014`: a surviving robot receives another robot's death event on each engine.
- [IDR-005 — EVT-001's priority-order claim is retired; the filter boundary is measured directly](IDR-005-evt-001-scoped-to-classic-filter-behavior.md) · `inferred` — `EVT-001` retires and `EVT-015` measures the observable filter behavior.
- [IDR-006 — Report legacy handler exceptions at the bridge callback boundary](IDR-006-report-legacy-handler-exceptions-at-the-bridge-boundary.md) · `inferred` — `BotPeer` invokes every legacy robot event callback inside a bridge-owned boundary.
- [IDR-007 — FIO-004 stays @draft; classic's own evidence for it depends on a SecurityManager this bridge cannot have](IDR-007-fio-004-stays-draft-no-securitymanager.md) · `inferred` — `CH-009` closes `FIO-001`, `FIO-002`, and `FIO-003` but leaves `FIO-004` `@draft`.
<!-- clue:index:end -->
