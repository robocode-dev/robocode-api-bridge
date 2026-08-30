# Agent routing hub

This repo runs **Cliewen**.

**Before your first tool call — whatever the request is, a question, a review, or a change — run `clue latest --quiet`.**

That is the whole instruction; the rest is why. It prints one line if a newer `clue` release exists and nothing at all when you are current, offline, or unable to tell, so it costs a line only when there is something to say; the answer is cached for a day, so asking again is free. If it says you are behind, route to [`clue-upgrade`](.agents/skills/clue-upgrade/skill.md) — the human decides whether to upgrade now or later, and nothing upgrades without that answer. If `clue` reports `latest` as an unknown command, that *is* the answer: your binary predates the check, so it is behind. The ordinary workflow commands — `clue init`, `scaffold`, `context`, `migrate`, `id`, `refs`, and `report` — also volunteer the same one-line notice on their own, so a session that runs any of them learns this without being asked; the check above is what covers a session that runs none. This is the only reason to run it unprompted; it reaches the network, so it never belongs in a validation verdict or a required check.

Before editing, inspect the smallest relevant context and tell the user `Recommended route: simple` or `Recommended route: full`, why, and what discovery would change that recommendation.

- **Simple** leaves the accepted contract unchanged: observational analysis with a named consumer, a defect correction restoring an unchanged criterion, regression evidence for an unchanged criterion, in-contract configuration, refactoring, maintenance, and editorial work. It uses no CH identity, workspace, plan declaration, digest, acceptance brief, or mandatory agentic review. Run the checks relevant to the changed surfaces.
- **Full** changes the accepted contract: acceptance-criterion, capability, decision, policy, plan-promise, methodology, or uncovered-behavior meaning changes. Uncertainty makes full the honest recommendation. Use [`clue-delta`](.agents/skills/clue-delta/skill.md)'s full loop with `/changes/CH-xxx-slug/`.

Paths and diff size may warn but never decide meaning. Reassess on semantic discovery and against the complete diff before integration. If simple work grows into full work, pause and recommend full. If the user explicitly declines, proceed as simple, keep the repository truthful, and add `Cliewen-Route: simple`, `Cliewen-Recommendation: full`, and `Cliewen-Override: user chose simple; <concise risk>` to the final authored commit.

A route does not authorize a push. Push directly to an integration branch only with explicit user authorization and repository permission; otherwise follow the repository's requested workflow. Humans may integrate by any mechanism their repository permits, and repo-local conventions may be stricter. Release is not a Cliewen route: each adopter defines or omits its own release process.

For a full change, read [`docs/README.md`](docs/README.md) only when the request does not name or resolve to an artifact; use it to identify the closest artifact, then run `clue context <id>`. When an identity is already known, run `clue context` directly and read the bounded slice it prints. The `/docs` corpus remains the system-of-record and working memory.

## Repository conventions

**Markdown prose is never hard-wrapped.** One line per paragraph and per list item; wrapping is the reader's IDE concern. Line breaks are structural only (headings, lists, tables, code fences).

**Guide writing is clear for software practitioners.** When changing prose in `/guide`, use the `humanizer` AI skill if it is installed. If it is not installed, ask the user to install it in the coding agent's local user directory before changing guide prose. The guide is for developers, people who work with coding agents, team leads, and architects. Use plain language, and explain a technical term when it first appears or link to a short glossary definition.

**The core is behind a red line.** Cliewen's core is the verifiable thread (goal → plan → change → capability → criterion → acceptance evidence, including classified executable references and genuine Human proof in the acceptance brief), the full-loop human acceptance boundary, and `clue validate` as deterministic judge. A change that alters what any of these means changes the accepted contract, so the agent recommends full and records an explicit decision; the user retains the authority to choose simple with the required override trailers. Everything else is periphery you may freely extend — including your own artifact types under `docs/` — and periphery never constrains the core.

## Skills

| Skill | When |
|---|---|
| [`clue-analysis`](.agents/skills/clue-analysis/skill.md) | Risks/unknowns first: spikes that end in findings docs |
| [`clue-plan`](.agents/skills/clue-plan/skill.md) | Creating or revising a plan |
| [`clue-upgrade`](.agents/skills/clue-upgrade/skill.md) | Checking for and, with human approval, carrying out a coordinated repository upgrade — simple work, because the release's contract changes were accepted upstream before it was published |
| [`clue-delta`](.agents/skills/clue-delta/skill.md) | The change loop: branch → implement → digest → merge |
| [`clue-verify`](.agents/skills/clue-verify/skill.md) | Pre-merge verification and automatic agentic review before any Cliewen PR |
| [`clue-extract`](.agents/skills/clue-extract/skill.md) | Brownfield adoption: transform an existing corpus into `/docs` |

## Repo-local conventions

Start with [`docs/architecture/README.md`](docs/architecture/README.md). It has the whole system on one page and says where every capability sits in it. Then read [`ARCH-002`](docs/architecture/ARCH-002-frozen-robocode-api-surface.md) before changing anything under `robocode-api/src/main/java/robocode/`.

**What this repository is.** A bridge that runs compiled legacy Robocode robots on Tank Royale. Classic Robocode is the specification: where the two engines disagree, classic is right by definition, because the robots were written against it and can never be recompiled.

**Build and test.**

```bash
./gradlew :robocode-api:test                                  # tier 1: unit, no engine, seconds
./gradlew :conformance-test:test                              # tier 2: both engines, minutes
./gradlew :robocode-api:jar :robots-wrapper:jar               # what the harness needs
cd compat-test && python compat_test.py --regression          # tier 3: the watch list
cd compat-test && python compat_test.py --trace               # per-turn behavioural diff
```

Tier 2 **skips** rather than fails when the environment is absent, so a clean checkout still builds. It needs a classic Robocode installation, the classic source repository's compiled test robots, and the Tank Royale runner jar; point it with `-Probocode.home=` and `-Probocode.source=`.

**The classic side needs its own JDK.** Classic installs a `SecurityManager`, which JDK 24 removed outright, so `-Djava.security.manager=allow` is a fatal VM error there and classic cannot start at all. The harness auto-detects a JDK 23 or older; override with `COMPAT_RC_JAVA`.

**Evidence conventions.** A test carries its criterion, proof type, and direction in its own name — `testAPI001_UnitPositive_...`. Give each field of a wide positional constructor a distinct value in tests: two defects found so far were positional or dispatch faults that placeholder data would have passed. `PDR-001` explains why evidence is layered in three tiers and what each can prove.

**The rumble collection is read-only** (`C-007`). Jars are staged and run, never rewritten; their bytecode and bundled sources may be read for debugging. A modified jar produces results indistinguishable from real ones, which is worse than a missing one.

<!-- A convention that binds every change also registers as a constraint artifact in docs/constraints/ (enforcement: agent until a machine holds it, partial once one holds part of it, human when none ever can) — prose here is the readable carrier, the register is the inventory. Repo-local conventions extend the methodology, never override it — when a rule here would contradict a skill, that conflict is an open question for a human, not a silent choice. -->
