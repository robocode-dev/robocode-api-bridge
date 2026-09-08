---
id: CH-012-proposal
type: change
status: open
links: [M-006]
title: Close actionable lifecycle failures found by the M-006 sweep
---

# Proposal

The M-006 sweep still exposes legacy bots that fail because the bridge and Tank Royale lifecycle do not present classic-compatible state at startup or while a callback is running. This change will reproduce those failures with focused probes, repair the lifecycle boundary, and add regression evidence without modifying participant jars or hiding bot-owned and staging-data failures.

The change serves `P-001` milestone `M-006`: the sweep must become a baseline that can be acted on and rerun.

The implementation will first establish the exact ordering at `setPeer`, `GameStarted`, `RoundStarted`, first tick, and nested event callbacks. It will then update the smallest owning layer, add focused tests, and rerun the affected conformance probes and sweep checkpoint.

No permanent architecture or criterion meaning is intended to change; the goal is to restore the existing classic-parity behavior under the bridge.

This change closes the actionable lifecycle repairs found in the partial sweep. The full M-006 baseline remains open because the user-directed stop left the sweep resumable rather than complete.
