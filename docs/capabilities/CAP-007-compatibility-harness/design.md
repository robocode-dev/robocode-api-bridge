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

Compare: scores as the sum of both participants' totals, errors as stack-trace-shaped signatures scraped from consoles and logs.

## What changes for the milestone

**Division setups.** One named constant per division carrying the official battlefield, rounds, and participant count, and one constant for the collection root — `C-003` and `C-007`. The single hard-coded setup goes away.

**Repeats and averaging.** A regression verdict runs the required repeats and compares averaged deltas against recorded baselines. `C-004` defines the band.

**Fail-fast.** The classic side already runs first, so its signatures are available before the Tank Royale side starts. Watching the bot log files during the run and aborting on an unmatched signature is the change; the ordering that makes it possible is already there.

**Tracing.** Per-turn position, headings, and energy from both engines in a comparable form. This is the one genuinely new capability rather than a correction, and it is what `M-002` and `M-003` need: those milestones ask for behavioural comparison, and nothing today can produce one.

## Known false positives

The bytecode transform that rewrites `while(true)` loops fails on modern JVMs for virtually every robot. Its message is kept in the log and excluded from the error count.

This is the kind of exclusion that needs justifying rather than accumulating. Each one narrows what the instrument can see, and a growing list of ignored signatures is how an instrument stops reporting the thing it was built for. `HARN-003` covers the classification including exclusions, so a new one has to be stated rather than added quietly.

## Why the report is untracked, and why that is a problem

The generated report is excluded from version control along with the work directories and progress files. It is also the evidence `SCORE-001` is judged from, which means the corpus names evidence that exists only in whoever last ran the sweep's working tree, and a reviewer reading the acceptance brief cannot open it. Recorded as a blocked carrier during extraction; `M-006` has to resolve it when it produces the first real baseline.
