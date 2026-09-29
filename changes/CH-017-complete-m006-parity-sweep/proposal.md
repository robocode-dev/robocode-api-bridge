---
id: CH-017
type: change
status: open
links: [P-001]
title: Merge an evidence-backed M-006 checkpoint
---

# Proposal

This change packages a bounded, evidence-backed checkpoint from the all-division parity campaign. It includes the bridge repairs, conformance evidence, official retests, registry observations, and documentation already recorded on this branch. It does not claim that `P-001#M-006` is complete; the plan milestone remains open for the unresolved collection cases and repairs.

The checkpoint includes the completed `TEAM-002` classic-name mapping using the authoritative map delivered by [Tank Royale PR 277](https://github.com/robocode-dev/tank-royale/pull/277), the tested startup, file-stream, quota, scan-name, and harness repairs, and the corresponding observations in the append-only registry. The permanent plan and changelog state only what this evidence supports.

## Challenge

The main assumption is that merging independently tested checkpoint work while `M-006` remains open gives the bridge a useful, truthful baseline without suggesting that overall parity is complete. The alternative is to keep this accumulated work in draft until every collection discrepancy is resolved; the PR's history shows that this makes the checkpoint wait on a much larger campaign.

The cheapest useful test is to inspect the complete merge diff and strict digest gate, verify that each claim in this checkpoint has a test or registry observation, and confirm the plan still labels `M-006` unfinished. Stop and narrow the checkpoint if any claim relies on full-campaign completion, or if a retest is represented as a clean parity result when its recorded status remains discrepant.

A change could pass its focused tests and still fail the people preserving legacy robots if unresolved cases are hidden by the merge or missing skipped-turn telemetry is treated as proof that no turns were skipped. The registry and analysis retain those cases as unresolved, and observations with `skipped: null` remain explicitly unmeasured.

## Follow-up boundary

After this checkpoint is accepted, the remaining official sweep, diagnosis of unresolved cases, evidence-backed repairs, final capability bookkeeping, and per-turn skipped-turn instrumentation continue in a separate change from the updated `main` branch. This checkpoint leaves `P-001#M-006` open and does not stand in for that follow-up.
