---
id: TASKS-003
type: tasks
status: open
links: [CH-003]
title: CH-003 task checklist
---

# CH-003 — tasks

Ordered. The measurement comes first because everything after it depends on which of the three outcomes is true, and the repair cannot be scoped before then.

## Establish the cause

- [ ] Confirm the conformance tier actually runs here before trusting any result from it: a skipped tier is not evidence
- [ ] Instrument the bridge's own death path so the measurement can tell whether the Bot API ever calls into it
- [ ] Instrument the Bot API's event queue and instant-handler path in a local build, published to `mavenLocal`, and run `tested.robots.BattleWin` through the conformance tier on both engines
- [ ] Record which of the three outcomes holds: the death event never reaches the queue, it reaches the queue and is discarded, or it is dispatched onto a thread already stopped
- [ ] Remove all instrumentation before the repair is written; none of it is committed

## Correct the record

- [ ] Rewrite `AN-006`'s suspect section with the established cause and with what the reading of the Bot API refuted, naming the evidence for each
- [ ] Decide the round-split divergence: resolved here, or carried to its own analysis record with what is known about it
- [ ] Update `docs/analysis/README.md` if a new record is minted

## Repair (serves EVT-004)

- [ ] If the cause is in the bridge, fix it in `BotPeer` and prove it with the re-enabled conformance test
- [ ] If the cause is upstream, record the decision that the repair belongs in the Tank Royale Bot API across all four languages under `C-006`, and say what this repository does in the meantime
- [ ] Re-enable `testEVT004_IntegrationPositive_OwnDeathReachesTheDeathHandler`, or mark this task `[-]` with the reason if no released Bot API carries the repair
- [ ] Confirm the bridge depends on a released Bot API version, not a locally published one

## Criteria (serve EVT-004 and EVT-005)

- [ ] Promote `EVT-004` out of `@draft` once its test passes; if it does not, restate its `@draft` reason to name the established cause
- [ ] Promote `EVT-005` out of `@draft` — it is already proven by passing tests and needs no new evidence
- [ ] Check whether `CAP-001` and `CRIT-001` statuses should change now that some of their criteria are active

## Verify and integrate

- [ ] `gradlew :robocode-api:test` green
- [ ] `gradlew :conformance-test:test` green with the environment present, and confirmed to have run rather than skipped
- [ ] `compat_test.py --regression` green — `C-004`
- [ ] `clue validate --forbid-changes` after the digest
- [ ] Run `clue-verify` including its review loop
