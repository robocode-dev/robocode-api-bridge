---
id: G-003
type: goal
status: proposed
links: []
title: Legacy robots are easy to obtain, wrap, and run on Tank Royale
provenance: inferred
reversal-cost: low
---

# G-003 — Legacy robots are easy to obtain, wrap, and run on Tank Royale

## Who wants it

Everyone `G-001` promises fidelity to, once they try to actually reach it: newcomers trying the bridge for the first time, anyone running a personal or club rumble, and the project itself. The parity campaign (`P-001`, milestone `M-006`) already depends on a local rumble jar collection existing on disk; there is no documented or automated way to build one today.

## What they want

To go from "here is a directory of classic Robocode robot jars" — or from nothing at all — to "these robots are running as Tank Royale bots." That means a build that produces artifacts someone can download rather than compile from source, and a guide that says where bots come from (https://rumble.robowiki.net/) and how to wrap them, replacing the current undocumented manual steps.

## Why it matters

Fidelity work has no audience if nobody can practically reach it. Today, using `robocode-api` and `robots-wrapper` means building from source with per-module hardcoded dependency versions and no shared catalog, no published jars, and a module README that stops at "copy this jar into a `lib` folder you create yourself." A user who cannot get the bridge running cannot benefit from anything `G-001` or `G-002` prove about it.

## What this does not commit to

Redistributing rumble bot jars itself, or guaranteeing that any particular automation against rumble.robowiki.net (scraping, or otherwise) is feasible or welcome there — that feasibility is explicitly open and belongs to whichever plan work picks it up. Nor does it commit to a specific artifact-publishing mechanism (Maven Central vs. GitHub Releases); that is an implementation choice, not a goal-level one.
