---
id: OQ-006
type: open-questions
status: open
links: [CH-015]
title: Open questions for CH-015 — Record the build/distribution/guide/downloader idea as a plan
---

# Open questions

## Milestone-ID (`M-xxx`) ledger gap

`clue id next M` returned `M-001` through `M-004` as the next free milestone identities, but `docs/plans/P-001-bridge-parity-campaign.md` already declares `M-001` through `M-007` and `M-101` through `M-143` in its milestone tables — confirmed live by `clue context M-001` resolving to `P-001`. The `.clue/id-ledger.yaml` had zero prior entries for the `M` prefix, so the allocator has no record of `P-001`'s existing milestone IDs and offered ones that would collide if used.

This change did not use the allocator's output for milestones. Instead, `P-002`'s four milestones are numbered `M-144`–`M-147`, continuing `P-001`'s existing sequence directly in the plan file, matching how `P-001`'s own milestones appear to have been authored (not through `clue id next M`, since the ledger had no trace of them). The four colliding reservations the allocator produced (`M-001`–`M-004`) were not released — the ledger is append-only and hand-editing it is out of scope here — so they sit unused in `.clue/id-ledger.yaml`.

This is not blocking for this change (`clue validate` passes cleanly with `M-144`–`M-147` used directly in prose), but it means the `M` prefix in this repository's identity ledger is out of sync with the corpus and `clue id next M` cannot currently be trusted for milestone allocation. Flagging for a human decision: whether to backfill the ledger with `P-001`'s existing milestone IDs (so future `next M` calls resume correctly at `M-148`), or whether milestone IDs are intentionally kept outside ledger tracking and numbered by direct inspection as done here.
