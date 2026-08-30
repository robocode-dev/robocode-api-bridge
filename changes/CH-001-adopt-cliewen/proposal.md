---
id: CH-001
type: change
status: open
links: []
title: Adopt Cliewen and give the bridge's compatibility promises real evidence
plan-less: true
---

# CH-001 — Adopt Cliewen and give the bridge's compatibility promises real evidence

## What

Bring `robocode-api-bridge` into Cliewen: extract the intent currently held in `TODO.md`, `README.md`, and `compat-test/` into a `/docs` corpus, and stand up the test layer that lets those promises carry acceptance evidence instead of prose.

This is the repository's first full change loop and its brownfield extraction, so it is plan-less by construction: the plan it would otherwise serve (`P-001`) is one of its own outputs.

## Why

The bridge lets legacy Robocode robots run on Tank Royale. Whether that works is currently answered by one instrument: `compat-test/compat_test.py`, an overnight sweep that plays every rumble jar against itself on both engines and diffs the scores. That instrument is valuable and stays, but on its own it is a poor judge.

It is statistical, not semantic. A score delta says a robot behaved differently; it never says which promise broke. `compat-test/compatibility_report.md` currently flags eight bots as discrepancies with no error logged on either side, and the TODO's own guidance for all eight is "re-test first" — because the previous numbers predate a fix and nobody can tell which of them were symptoms of it.

It is noisy. `ad.last.Bottom` swung RC=6 ↔ 274 between runs on the classic side alone, which makes a single-battle percentage delta for that bot meaningless. The report's own caveat says a healthy robot can swing ±20–30% at ten rounds.

It is slow. A full roborumble sweep is an overnight job, so it cannot answer "did this commit break something" during the work that would benefit most from the answer.

And there are no JVM tests in the repository at all. The event-dispatch redesign recorded in `TODO.md` was validated by re-running battles and reading scores. It found a real bug — the 0.33.1 event queue popped and dropped deferred same-priority events — but only after that bug had been misattributed to `prepareRobotForRound()` and had sat behind a blind-stalemate regression.

Cliewen is the fit for this because the gap is precisely a traceability gap: the repository holds intent (`TODO.md`), it holds evidence (`compatibility_report.md`), and nothing connects them or notices when the connection breaks.

## Scope

In scope:

- The `/docs` corpus extracted from `TODO.md`, `README.md`, `compat-test/README.md`, and `compat-test/compatibility_report.md`: one goal, one plan whose milestones are the TODO's remaining priorities, capabilities for the behaviours the bridge promises, constraints, retroactive decision records for work already merged, and analysis findings.
- A unit test tier over the bridge's pure mapper classes — the first JVM tests in the repository.
- A semantic conformance tier that runs classic Robocode's own test robots on both engines and compares what they report, giving the event and physics capabilities evidence that is deterministic and fast.
- A regression tier layered on the existing sweep: a pinned watch list of bots that have diverged, multi-run averaging, and a per-turn trace mode for diagnosis.
- The generated `clue validate` CI workflow.
- `TODO.md` reduced to a pointer at the plan that replaces it.

Out of scope, and deliberately so:

- Fixing any of the open score gaps. This change builds the instrument; using it is the plan's later milestones.
- The file I/O sandbox (TODO item 4) and team support (TODO item 5). Both become capabilities with criteria; neither is implemented here.
- Branch protection on `robocode-dev/robocode-api-bridge`. The workflow is committed; arming it is the maintainer's call in repository settings.

## Decision boundaries

Choices this change is authorized to make: how the corpus is partitioned into capabilities, which criteria are minted and in what namespaces, and how the three test tiers are structured.

Choices it is not authorized to make: anything that changes bridge behaviour. No production source under `robocode-api/src/main` or `robots-wrapper/src/main` is modified.

## Route

Full. The change creates capabilities, acceptance criteria, and decision records, which is accepted-contract meaning by definition, and brownfield extraction is a full loop by the skill's own boundary.
