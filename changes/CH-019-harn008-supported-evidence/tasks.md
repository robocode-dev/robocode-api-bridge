---
id: TASKS-012
type: tasks
status: open
links: [CH-019]
title: Tasks for CH-019
---

# Tasks

- [ ] Add a JVM Integration path that invokes the compatibility harness with skipped-turn capture enabled and exposes its structured observation to JUnit.
- [ ] Add `HARN-008` Integration-positive evidence matching captured `(bot ID, round, turn)` tuples to callback output, including early and warm-up turns.
- [ ] Add `HARN-008` Integration-positive evidence for a completed capture with an empty event list, without making the result depend on a timing-sensitive no-skip battle.
- [ ] Add `HARN-008` Integration-negative evidence that disabled, unavailable, and incomplete capture remain distinct and never claim zero events.
- [ ] Verify the JUnit assertions cover the durable observation path used by compatibility measurements rather than only a transient conformance response.
- [ ] Activate `HARN-008` and mark `M-148` done only if every clause has supported positive and negative evidence; otherwise keep both states honest and record the exact gap.
- [ ] Update the capability design, telemetry analysis evidence, and `P-001` bookkeeping without changing M-006's open campaign scope.
- [ ] Run the supported local conformance and relevant unit checks, validate the digest, and publish the ready candidate under the full review workflow.
