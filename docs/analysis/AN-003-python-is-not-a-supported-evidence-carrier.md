---
id: AN-003
type: analysis
status: active
links: [CAP-007, PDR-001, C-004]
title: The harness is Python, which is not a supported evidence carrier
provenance: inferred
reversal-cost: low
---

# AN-003 — The harness is Python, which is not a supported evidence carrier

## What was investigated

How the compatibility harness's own promises can carry acceptance evidence, given that `clue validate` accepts Go, JVM, and Cucumber test references.

## What was found

It cannot, today. The orchestrator is Python — staging, process supervision, checkpointing, result classification, and reporting — and a Python test cannot be named as evidence for a criterion.

This is a gap in what the corpus can express about this repository, not a defect in the harness. Several of the affected promises describe behaviour that exists and works: checkpoint-and-resume, process-tree termination on timeout, result classification.

## What it means

**Those criteria are `@draft`, not `Test-type: Human`.**

This was the decision worth recording. `Human` was available, would have validated, and would have made `CAP-007` look complete. It would also have been false. `Human` is for a promise a person genuinely judges — `SCORE-001`, where a maintainer reads a sweep and decides whether the population's parity is acceptable, is one. Checkpoint-and-resume is not a judgment; it is an untested function with a definite right answer.

Using `Human` as the label for "no test exists in a language the tool accepts" would convert a tooling limitation into a claim about the work, and it would do so invisibly — nothing downstream distinguishes a real Human criterion from a laundered one. The whole value of the disposition is lost the first time it is used that way.

So the criteria stay draft, and this record is why.

**`C-004` stays agent-enforced longer than its implementation suggests.** Once the harness implements averaging and fail-fast, the rule is mechanically held — but the mechanism cannot be cited as evidence, so the constraint's enforcement class cannot advance on implementation alone.

## What would change this

Three routes, roughly in order of cost:

**Move the checkable logic to the JVM.** Result classification, division setup resolution, and the regression verdict are pure functions over values. They already sit beside two Java workers, and moving them would let the unit tier cover them on the same terms as the adapter's mappers. This is the narrow, likely-correct option: it targets exactly the criteria whose `Test-type` is `Unit`, and leaves orchestration where it belongs.

**Express the harness's behaviour as Cucumber scenarios.** Supported, and a poor fit — the promises are about process supervision and file state, not about scenario-shaped behaviour.

**Wait for Python support upstream.** Not this repository's to schedule.

The first route is the one `M-001` should take, and it does not require rewriting the harness — only relocating the parts that were always more testable than the rest.
