---
id: AN-044
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CAP-007, AN-002, AN-020, AN-036]
title: HOFSwarm's higher score came from the older Tank Royale team survival scorer
provenance: inferred
reversal-cost: low
---

# AN-044 — HOFSwarm's higher score came from the older Tank Royale team survival scorer

## Question

Does the historical `teamrumble/rz.HOFSwarm_1.1.jar` score advantage persist on the current local Tank Royale build, and what explains the difference between the published and locally built Runner jars?

## Evidence boundary

The read-only jar has SHA-256 `7d3e197920ed6789ae6897127724a12310e7e087b3a6ac0753bd985ef3e6083f`; its descriptor runs five copies of `rz.HOFMember [1.1]`, and it bundles no Java source. The older registry observation `7114988a3b5ce8ee` recorded +33.1% using Bot API 1.2.0 and Runner SHA-256 `3b3212aded7edf2eca481e2e536075f59e074993a85f87ba7f5bd0760892c151`.

On 2026-10-05, an official five-pair confirmation using the harness's published `examples/lib` Runner jar, SHA-256 `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, produced deltas +28.5%, +28.6%, +33.6%, +32.8%, and +34.7% (mean +31.64%; observation `7ff28febc28cc78b`, `CONFIRMED (score)`). That jar identifies itself as Runner 1.4.0 but embeds `CURRENT_BEHAVIOR_VERSION=1`.

The same day, with Classic 1.11.1, bridge commit `4cd9e162ab460c27395b7d84ceab7f8d4066f092`, bridge API SHA-256 `3f09f55c412b024e5aa7fc202cde9cdbbd3fdd0d61a636370d340361e572aa3a`, Bot API 1.4.0 SHA-256 `ab65c4d5cec1808adeb71375ada6e15341ae250def89a6c10fc9da879de0752`, and wrapper SHA-256 `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`, a five-pair confirmation with the local Runner build produced −6.3%, −2.6%, −1.4%, −6.8%, and −4.4% (mean −4.3%; observation `5edb03c136b94cda`, `MATCHED (score noise)`). The local Runner jar SHA-256 is `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`; it is also Runner 1.4.0, embeds behavior version 2, and was built from Tank Royale checkout `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9` on Windows. Both observations used the same bot jar, bridge, Bot API, wrapper, Classic install, and official team parameters (1200×1200, 10 rounds, two teams of five). The current observation contains no bridge-only exceptions; captured skipped-turn events, when present, were all at round 1 turn 1. The harness observation does not pin the Java executable version.

The two Runner jars contain different embedded server jars. The build includes Tank Royale commit `9ce6a9312a3017a28b1289d5f0098cbf168e8b42`, titled `fix(server): match classic team survival scoring`. That change rewrites `ScoreTracker.registerDeaths` to account for each newly defeated bot survived by each living opponent ([pinned source](https://github.com/robocode-dev/tank-royale/blob/9ce6a9312a3017a28b1289d5f0098cbf168e8b42/server/src/main/kotlin/dev/robocode/tankroyale/server/score/ScoreTracker.kt#L96-L119)). The harness README intentionally defaults to the published Runner and documents `--runner-jar` for a local build; the two observations therefore pin distinct, reviewable artifacts rather than interchangeable files.

## Score components

The final pair from each five-pair run shows the scoring difference directly; these are single-pair figures, not the five-pair means.

| Runner artifact | Classic total | Tank Royale total | Classic survival | Tank Royale survival |
|---|---:|---:|---:|---:|
| Published 1.4.0, behavior version 1 | 19,421 | 26,168 | 12,450 | 20,950 |
| Local 1.4.0 build, behavior version 2 | 20,000 | 19,114 | 12,500 | 12,500 |

Under the published artifact, Tank Royale's extra 8,500 survival points dominate the fifth pair's score gap. With the local build, survival points match exactly in that pair, and the five-pair mean is inside the band. This aligns the score change with Tank Royale's team survival scoring repair rather than a bridge event or identity path.

## Source review

The bytecode's movement planner uses `getOthers()` and teammate flags, so those paths were checked. The bridge clears its dead-bot set and resets its initial other count on each round; scanned names and teammate checks resolve through the current bot ID/name map. Neither path explains a difference that follows the Runner scoring artifact while every bridge and robot artifact stays fixed. The robot's bytecode also initializes its estimated own position once and does not refresh it in the movement loop, but that robot-side weakness is present in both runs and does not explain the score reversal. No bridge repair is indicated, and the team jar remains unchanged.

## Finding

The +33.1% historical result and the +31.64% published-Runner confirmation reflect Tank Royale's pre-fix team survival scoring. The current local Runner includes upstream commit `9ce6a9312a3017a28b1289d5f0098cbf168e8b42`; with that build the official result is `MATCHED (score noise)` at −4.3%. The cause is `tank-royale-team-survival-scoring`, owner `tank-royale`. Retain both Runner observations in the registry history, with the local build's matched result as latest.

## M-006 handoff

Keep `teamrumble/rz.HOFSwarm_1.1.jar` at `MATCHED (score noise)` with the Tank Royale scoring diagnosis. Continue in registry order with the next unresolved score-review case, `teamrumble/sp.Minis.GeneBotUpgrade_1.1.jar`.
