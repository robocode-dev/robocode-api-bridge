---
id: OQ-008
type: open-question
status: active
links: [CH-017]
title: CH-017 open questions
---

# Open questions

## OQ-008-1 — Classic team identity reconstruction

When this question was opened, `ethdsy.MalackaTeam_1.2` had shown that legacy team robots depend on the classic `getName()` format, including its per-instance ` (n)` suffix. The bridge returned the simple Java class name, while the Tank Royale Bot API exposed only numeric bot IDs and teammate IDs after the game started.

Answer (Flemming N. Larsen, 2026-09-27): Recreate classic team names in the bridge API. The required behavior includes `getName()`, `getTeammates()`, `isTeammate()`, directed `sendMessage()`, and message sender names, with classic version and duplicate-instance formatting.

The bridge must use the authoritative server mapping rather than derive duplicate suffixes from teammate IDs. OQ-008-2 records that source and its approval.

## OQ-008-2 — Authoritative classic-name mapping source (resolved)

Before the name-map change in [Tank Royale PR 277](https://github.com/robocode-dev/tank-royale/pull/277), `GameStartedEventForBot` supplied the bot's numeric ID and teammate IDs, and team-message events identified their sender by numeric ID. Exact classic duplicate suffixes depend on the battle roster, including bots outside the team, so the bridge could not reconstruct them from a `.team` descriptor or a teammate-only exchange.

Should this change include a separate Tank Royale protocol change that provides the authoritative mapping and equivalent Java, .NET, Python, and TypeScript Bot API surfaces, or should the name-restoration decision remain recorded while implementation waits for a separately authorized upstream change?

Answer (Flemming N. Larsen, 2026-09-27): Include the separate Tank Royale protocol change with equivalent Bot API support. [Tank Royale PR 277](https://github.com/robocode-dev/tank-royale/pull/277) was merged on 2026-09-27, adding the authoritative bot-ID-to-name map; CH-017 now consumes that map in the bridge.
