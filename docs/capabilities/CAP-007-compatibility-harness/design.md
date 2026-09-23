---
id: DES-007
type: design
status: draft
links: [CAP-007, ARCH-003, C-003, C-004, C-007, AN-003]
title: The compatibility harness — design
provenance: inferred
reversal-cost: low
---

# CAP-007 — design

`status: draft`: the orchestration and isolation described here exist; the division setups, averaging, fail-fast, and tracing do not.

## Shape

A Python orchestrator drives two single-file Java workers, one per engine, each run uncompiled in source mode. The classic worker drives the Control API against a real Robocode installation; the Tank Royale worker drives the Battle Runner API with an embedded server. Each writes its outcome as JSON to a file the orchestrator reads.

Python for orchestration and Java for the engines is not incidental. Both engines are JVM libraries and must be driven in-process; everything around them — staging, process supervision, checkpointing, reporting — is glue that benefits from being quick to change. The cost of the split is `AN-003`: the orchestration half sits in a language this methodology cannot take evidence from.

## Per robot

Stage for classic: the jar alone in a robots directory, run against a copy of itself, capturing scores, battle errors, and each robot's console output — which is where classic prints robot exceptions.

Stage for Tank Royale: the jar with the bridge, Bot API, and wrapper jars alongside; run the wrapper over it; patch the generated boot script to redirect the bot process's output into log files; duplicate the bot directory so two instances of the same robot cannot interleave into one log.

That duplication is load-bearing rather than tidy. `C-004`'s fail-fast rule needs an exception attributed to a participant, and two instances writing to one log makes attribution impossible.

Compare: scores as the sum of all staged participants' totals, errors as normalized exception class plus the first legacy callback or application frame scraped from consoles and logs. A completion mismatch, an unmatched normalized error, a timeout, or a confirmed score gap is a parity case rather than a pass.

## Durable parity registry

`compat-test/parity-registry.json` is append-only per subject. An observation carries the official setup, source jar digest, selected classic identity, team classification, both engine outcomes, normalized errors, and the classic Robocode version plus bridge, Tank Royale, Bot API, wrapper, and runner fingerprints that produced it. The harness refuses to create LiteRumble evidence when the classic version is unknown or outside LiteRumble's allowed client list. `parity-registry.md` is generated from that JSON and is the review table for every subject.

The harness imports an interrupted checkpoint with `--sync-registry`, runs new first-pass work in bounded `--limit` batches, and selects retests with `--retry-unresolved` or `--retest-cause`. Matched subjects stay recorded and are not selected after an unrelated repair.

## What changes for the milestone

**Division setups.** One named constant per division carrying the official battlefield, rounds, and participant count, and one constant for the collection root — `C-003` and `C-007`. The single hard-coded setup goes away.

**Repeats and averaging.** A regression verdict runs the required repeats and compares averaged deltas against recorded baselines. `C-004` defines the band.

**Fail-fast.** The classic side already runs first, so its signatures are available before the Tank Royale side starts. Watching the bot log files during the run and aborting on an unmatched signature is the change; the ordering that makes it possible is already there.

**Tracing.** Per-turn position, headings, and energy from both engines in a comparable form. This is the one genuinely new capability rather than a correction, and it is what `M-002` and `M-003` need: those milestones ask for behavioural comparison, and nothing today can produce one.

## Known false positives

The bytecode transform that rewrites `while(true)` loops fails on modern JVMs for virtually every robot. Its message is kept in the log and excluded from the error count.

This is the kind of exclusion that needs justifying rather than accumulating. Each one narrows what the instrument can see, and a growing list of ignored signatures is how an instrument stops reporting the thing it was built for. `HARN-003` covers the classification including exclusions, so a new one has to be stated rather than added quietly.

## Why the registry is tracked

The generated compatibility report remains a local checkpoint view. The parity registry is the reviewable evidence carrier for `SCORE-001`; it survives an interrupted sweep, names the artifacts that produced each result, and shows whether a later repair resolved the same case.
