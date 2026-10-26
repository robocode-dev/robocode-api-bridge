---
id: P-002
type: plan
status: active
links: [G-003, G-001, PDR-005, PDR-006]
title: Make the bridge buildable, distributable, and easy to point at real robots
provenance: inferred
reversal-cost: low
---

# P-002 â€” Make the bridge buildable, distributable, and easy to point at real robots

## The campaign

Make the existing bridge usable by someone who has a compiled Java robot jar and wants to run it in Tank Royale. The next delivery is a preview package, its first-battle guide, and evidence that the documented installation works outside the maintainer's prepared development environment. Comprehensive Classic parity continues in [the compatibility campaign](P-001-bridge-parity-campaign.md), but completing that campaign is not a prerequisite for a clearly described preview.

This is a declared revision of the former draft distribution plan, following the maintainer's direction to prioritize installation, use, and documentation. It activates the existing packaging and guide milestones, adds a preview-readiness milestone, and places collection-acquisition work after the preview. It does not approve a tag, public release, or publication date. [The delivery decision](../decisions/PDR-005-preview-before-comprehensive-parity-closure.md) governs this order.

## Milestones

| ID | Milestone | Exit criterion | Status |
|---|---|---|---|
| M-144 | build cleanup + version catalog | `robocode-api`, `robots-wrapper`, and `conformance-test` share a Gradle version catalog (`gradle/libs.versions.toml`) for their dependency and plugin versions, and use a consistent Java target declaration style. `./gradlew build` succeeds across all modules from the catalog-sourced versions. | done |
| M-145 | preview package and distribution preparation | A revision-pinned build produces a versioned preview archive containing the wrapper, adapter, required runtime libraries, dependency notices, checksums, and documented Java and Tank Royale runtime requirements. A user can wrap an existing robot jar from the archive without Git, Gradle, Maven, a Classic installation, or manual dependency hunting. The build and publication procedure can produce the same archive for an explicitly authorized release; preparing the candidate does not publish it. Evidence: [package criteria and classified tests](../capabilities/CAP-009-installable-preview/criteria.md), revision-pinned ZIP and checksums from `:robots-wrapper:previewDist`, Windows package checks on JDK 11 and 25, and [focused runtime observations](../analysis/AN-714-preview-team-result-observations.md). | done |
| M-146 | installation, first battle, and troubleshooting guide | The root and module READMEs lead to one guide covering prerequisites, obtaining the bridge package and its compatible Tank Royale runtime, preparing a directory, wrapping an unchanged jar, finding the generated bots in Tank Royale, and completing the first battle. It covers team entries, logs, missing Java and dependencies, incompatible runtime versions, incomplete team jars, upgrades that preserve robot data, and reporting a failure with versions and useful logs. Known anonymity and sandbox limitations are explained. Commands and download references correspond to the candidate package; normal use requires no Classic installation or compatibility-harness setup. Evidence: [the canonical user guide](../../guide/README.md), linked from root and module READMEs and copied into the tested ZIP; its commands are exercised by `PreviewCommandTest`. The clean-environment human walkthrough remains in M-149. | done |
| M-149 | preview installation and compatibility readiness | Using a disposable environment with only the documented prerequisites, the candidate archive, and unchanged example jars, record a successful Windows installation, wrapper invocation from a path containing spaces, a solo battle, and a complete grouped-team battle on the declared Tank Royale runtime. Every other advertised operating system has equivalent recorded evidence. Relevant startup and behavior regression checks pass, and the maintainer or a new user completes the documented walkthrough with observer, conditions, observations, and limitations recorded. Outstanding failures and supported runtime versions are documented for the publication decision. This is a preview gate, not proof of complete Classic parity, and it requires no outside volunteers. | todo |
| M-147 | bot acquisition tooling, after the preview | A documented, feasible acquisition path exists for building a local bot collection â€” either scripted downloading or a documented pointer to an existing bundle, whichever investigation at implementation time finds workable â€” and running it against `robots-wrapper` produces correctly wrapped bot directories for a sample set of bots. This milestone does not gate the preview for people who already have robot jars. | todo |

## Execution order and scope

Start with M-145. Draft and exercise M-146 against that concrete package, then complete M-149 before proposing public preview publication. Choose minimum Java and supported Tank Royale versions from the actual packaged dependencies and observed runs; a Java compilation target or a successful maintainer-machine battle alone does not prove an installable runtime combination. Windows is the first required walkthrough; additional platforms are advertised only after their own evidence exists.

The preview must provide a publicly obtainable or reproducibly documented compatible runtime route. Local Tank Royale builds remain valid for development conformance under [the local-build policy](../decisions/PDR-002-locally-built-tank-royale-artifacts-for-conformance.md), but a preview must not silently depend on the maintainer's unpublished binaries or Maven cache. If an unreleased upstream repair is essential, record that dependency and resolve a usable distribution route with the maintainer before calling the preview ready; do not request an upstream release merely to obtain test evidence.

Run targeted compatibility investigations alongside these milestones when they concern actual startup, crashes, hangs, team operation, or regressions. Preserve raw observations and distinguish a completed battle from behavioral parity. Real-name-dependent strategy is a known robot limitation under [the anonymity policy](../decisions/PDR-006-opponent-identity-dependence-is-a-robot-limitation.md); malformed or inconsistent anonymous identities remain bridge defects. Do not remove difficult failures from the register or promise universal compatibility to make a preview appear ready.

The first release path starts with the user's existing jar. Bulk download automation and redistribution of other authors' robot collections are outside the preview. M-147 retains its later investigation of feasible acquisition sources, including https://rumble.robowiki.net/, without assuming a bulk-download API or permission to redistribute a collection.

## Challenge to this commitment

The assumption most likely to fail is that the current bridge and an obtainable Tank Royale runtime can be packaged into a repeatable first-run experience. The alternative is to continue comprehensive parity work before offering a preview. The cheapest useful test is to unpack the candidate archive into a disposable directory and follow only the proposed guide with a known robot jar. Failure to resolve dependencies, wrap the jar, find the robot in Tank Royale, or complete a battle sends work back to M-145 or M-146; another score sweep does not discharge those failures.

A package can pass automated checks and still leave a user unable to find a generated bot or understand a failed launch. M-149 therefore includes a recorded walkthrough, usable troubleshooting, and an actionable reporting path. The maintainer can perform that walkthrough; recruiting another contributor is not a dependency.

## What would change this plan

A startup or runtime regression that breaks the documented path takes priority over preview publication. Evidence that the proposed runtime combination is unavailable to users revises the packaging approach before publication. Evidence that a collection source cannot be acquired appropriately changes the later acquisition milestone through a declared plan revision; it does not delay the existing-jar preview.
