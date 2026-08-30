# Claude Code entry point

This repository's agent instructions live in [`AGENTS.md`](AGENTS.md), which is the cross-agent standard and the file to edit. This file exists only because Claude Code loads `CLAUDE.md` and not `AGENTS.md`, so without it the routing hub never reaches the session.

@AGENTS.md

## What belongs here, and what does not

Nothing about the methodology belongs in this file. A rule written here is invisible to every other assistant working in this repository, and it fails quietly: Claude Code follows it while the others carry on without it, and nothing reports the disagreement. Rules go in `AGENTS.md`.

Instructions that are genuinely specific to Claude Code — and only those — can be added below the import. This file is yours: `clue init` never overwrites it and `clue migrate` never rewrites it.

## Where the lifecycle rules live

`AGENTS.md` routes; it does not restate the method. The rules an agent must follow are in the `clue-*` skills under `.agents/skills/`, mirrored to `.claude/skills/` so Claude Code lists them. **Read the relevant skill before deciding how a change is shaped** — the skill is what binds, and a rule that is only in your own `/docs` is this repository's local bookkeeping rather than something the method requires.

The skills are generated and version-stamped. Do not hand-edit them; `clue migrate` upgrades them when a new pair is released.
