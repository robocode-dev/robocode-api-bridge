---
id: CH-014
type: change
status: proposed
links: [P-001#M-006]
title: Turn M-006 into a versioned legacy-robot parity campaign
---

# Proposal

M-006 currently promises one sweep report. That report is ignored by Git, records only the latest observation, and cannot tell a reviewer whether a failure on Tank Royale is equivalent to classic Robocode or whether a later repair fixed the affected robots.

This change makes classic Robocode the explicit behavioural reference and adds a tracked parity registry for every rumble jar and team. The registry records pinned engine artifacts, official division setup, outcome, normalized errors, score evidence, diagnosis, and repair retests. It retains healthy cases without rerunning them after unrelated repairs, while batches of unresolved cases are retested by named cause.

The change revises M-006, C-004, CAP-005, and CAP-007 because it changes the campaign and measurement methodology. It records the decision in a PDR, upgrades the generated Cliewen carriers needed for coordinated identity allocation, and updates the harness and its tests. Participant jars remain read-only.
