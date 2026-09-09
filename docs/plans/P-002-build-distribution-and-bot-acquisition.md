---
id: P-002
type: plan
status: draft
links: [G-003]
title: Make the bridge buildable, distributable, and easy to point at real robots
provenance: inferred
reversal-cost: low
---

# P-002 — Make the bridge buildable, distributable, and easy to point at real robots

## The campaign

`G-003` asks for an easy path from "I want to run legacy robots on Tank Royale" to "I have them running." This campaign is that path, sequenced so each milestone gives the next one something to build on: a fat jar isn't worth publishing until its dependency versions are deliberate rather than accidental, a guide isn't worth writing until there's something a reader can download, and a downloader isn't worth building until the wrapper it drives has a stable, documented way to run.

`M-147`'s exit criterion is deliberately open on mechanism. Rumble.robowiki.net has no documented bulk-download API, and whether a scraper against its live pages is feasible or welcome there is unknown; forcing that choice at plan time would commit the campaign to an approach nobody has yet checked is viable.

## Milestones

| ID | Milestone | Exit criterion | Status |
|---|---|---|---|
| M-144 | build cleanup + version catalog | `robocode-api`, `robots-wrapper`, and `conformance-test` share a Gradle version catalog (`gradle/libs.versions.toml`) for their dependency and plugin versions, and use a consistent Java target declaration style. `./gradlew build` succeeds across all modules from the catalog-sourced versions. | todo |
| M-145 | artifact build & distribution | A tagged build produces downloadable `robocode-api` and `robots-wrapper` jars (via `maven-publish`, GitHub Releases, or an equivalent mechanism) that a fresh clone can fetch and run without building from source. | todo |
| M-146 | usage guide | A guide documents the end-to-end flow from an existing robot jar to a running Tank Royale bot, replaces or removes the current undocumented manual `lib`-folder step, and points readers to https://rumble.robowiki.net/ as a source of robot jars. | todo |
| M-147 | bot acquisition tooling | A documented, feasible acquisition path exists for building a local bot collection — either scripted downloading or a documented pointer to an existing bundle, whichever investigation at implementation time finds workable — and running it against `robots-wrapper` produces correctly wrapped bot directories for a sample set of bots. | todo |

## Why this order

`M-144` comes first because `M-145`'s published artifacts are only worth having if their dependency versions were chosen deliberately; publishing first would just make the current accidental version drift (`robocode-api` and `conformance-test` at `0.5.0`, `robots-wrapper` independently at `0.3.1`, every dependency version hardcoded per module) a public commitment instead of an internal one.

`M-146` follows `M-145` because a guide that tells someone to download an artifact that doesn't exist yet is worse than no guide — it would need to be rewritten once publishing lands anyway.

`M-147` is last because it is the only milestone whose mechanism is not yet known to be feasible, and because it depends on `robots-wrapper` already having the stable, documented invocation that `M-146` establishes. Automating a step that isn't yet reliably documented for a human would just automate the wrong thing.

## What would change this plan

If investigation for `M-147` finds that rumble.robowiki.net cannot be scraped (technically or by its own terms) and no equivalent bundle exists to point to instead, that milestone's exit criterion would need to be revised to something narrower than "acquisition tooling" — for example, a curated instructions page rather than a tool. That is a semantic change to the milestone's promise and needs the same human-accepted revision any other plan-promise change does.
