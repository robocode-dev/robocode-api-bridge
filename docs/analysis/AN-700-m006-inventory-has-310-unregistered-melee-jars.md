---
id: AN-700
type: analysis
status: active
links: [P-001, PDR-003, C-007, AN-699]
title: The current M-006 inventory has 310 unregistered MeleeRumble jars
provenance: observed
reversal-cost: low
---

# AN-700 — The current M-006 inventory has 310 unregistered MeleeRumble jars

## Risk investigated

Whether the tracked parity registry covers the local three-division collection, and how much work remains before the M-006 population criterion can be complete.

## Evidence boundary

On 2026-10-19, the direct `*.jar` files under the read-only `C:\Code\LiteRumble robots` collection directories were counted and compared with `compat-test/parity-registry.json`. The rumble jars were not opened for writing or changed. This is a count snapshot, not a checksum manifest; completed registry observations pin each tested jar by hash.

## Findings

The local collection contains 1,124 RoboRumble jars, 382 MeleeRumble jars, and 44 TeamRumble jars, for 1,550 jars total. The registry contains rows for all 1,124 RoboRumble jars and all 44 TeamRumble jars, but only 72 MeleeRumble jars. Therefore, 310 local MeleeRumble jars do not yet have registry rows.

The currently tracked 1,240 subjects have 108 `DISCREPANCY` statuses. That count is not a bridge-defect count: the registry also contains robot-owned failures, known opponent-pool contamination, and an unresolved Java worker initialization failure, among other diagnoses.

This makes M-006 finite for a fixed collection snapshot but substantially incomplete. The collection directory should be recounted before claiming closure because new or removed jars change the population. No plan criterion was changed by this inventory check.

## M-006 handoff

Add official parity cases for the missing MeleeRumble jars in registry order, beginning with `meleerumble/DBall.Anarchy_0.1.jar`.
