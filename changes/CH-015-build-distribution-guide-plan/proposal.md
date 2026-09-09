---
links: [G-003]
---

# CH-015 — Record the build/distribution/guide/downloader idea as a plan

## What

Adds `G-003` (a new, `proposed` goal: legacy robots should be easy to obtain, wrap, and run) and `P-002` (a `draft` plan serving it, with four milestones: Gradle version-catalog cleanup, artifact build & distribution, a usage guide pointing to https://rumble.robowiki.net/, and bot-acquisition tooling whose mechanism is left open pending feasibility investigation). Regenerates the `docs/goals/README.md` and `docs/plans/README.md` indexes.

## Why

The user asked, in a prior conversation, to capture a future improvement idea — better Gradle/version-catalog hygiene for `robocode-api` and `robots-wrapper`, publishable artifacts, a usage guide linking to the rumble bot archive, and eventually a tool to download bots and run `robots-wrapper` on them — as a durable corpus artifact rather than a private note. This change creates that plan. No implementation work against the plan's own milestones is in scope here; the plan is deliberately `draft`/`todo` throughout, recording intent for later pickup.

This change is not plan-less: it exists to create `P-002`, so it links the goal (`G-003`) that plan serves rather than a pre-existing plan item.
