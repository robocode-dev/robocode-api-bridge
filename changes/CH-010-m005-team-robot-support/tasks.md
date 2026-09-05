- [x] Inspect a real `teamrumble` collection jar and the Tank Royale booter's team-launch mechanism to confirm how team identity reaches a launched bot process. Resolved: the booter (not the wrapper) assigns `TEAM_ID`/`TEAM_NAME`/`TEAM_VERSION` from a `teamMembers` field in the boot entry's own JSON; see `open-questions.md`.
- [x] Extend `robots-wrapper/src/main/java/Main.java` to detect a `.team` descriptor and parse `team.members`. Done: `processTeam`/`createTeamJsonFile` in `Main.java`, verified against a real `teamrumble` jar (`amz.TeamDeathTeam_1.jar`) — produces the member bot directory plus a team boot-entry directory with `teamMembers` naming it once per duplicate.
- [x] Produce the team boot-entry directory per the booter's actual contract. Done as part of the prior task.
- [x] Droid detection: `BotPeer.createBotImpl` selects `DroidBotImpl` (implements `dev.robocode.tankroyale.botapi.Droid`) when the wrapped robot implements `robocode.Droid`.
- [x] Resolve the blocking open question on droid/team name resolution. Resolved (after a correction — see `open-questions.md`): no robot in this bridge, team or not, ever learns another robot's real classic name; every `getName()`-shaped value is already the stringified Tank Royale id. Team messaging keeps `ROUTE-009`'s existing numeric-id addressing unchanged.

## Scoped out of this change (follow-up)

`TEAM-001`, `TEAM-002`, `TEAM-003` all require the team division to actually run — `TEAM-001`'s own criterion text says "the team is no longer recorded as skipped" — and un-skipping it needs real team-battle-staging plumbing in `compat_test.py` (new CLI surface, participant construction on both the classic and Tank Royale sides for a team roster), not the one-line flip the original task list assumed. That is materially bigger than the rest of this change and harder to verify without deeper harness work, so this change stops at the groundwork (wrapper + droid detection) and leaves the following for a follow-up change against `M-005`:

- [ ] Add team-battle-staging support to `compat_test.py` (classic and Tank Royale sides) and un-skip `teamrumble` (`tr_skipped = (collection == "teamrumble")`).
- [ ] Author purpose-written conformance test robots for `TEAM-002` (message delivery: broadcast reaches every member, a directed message reaches only its addressee, addressed by numeric id) and `TEAM-003` (a droid receives no scan events on either engine while acting correctly on teammate-reported information).
- [ ] Remove `@draft` from `TEAM-001`, `TEAM-002`, `TEAM-003` once the above gives them integration evidence.

## This change's remaining tasks

- [ ] Update `docs/capabilities/CAP-006-team-robot-support/design.md` and `README.md` to describe the implemented groundwork (native Tank Royale team mapping via a `teamMembers` boot entry, droid detection via `Bot` subclass selection, numeric-id team messaging) and name the follow-up work above as what still closes `TEAM-001`–`TEAM-003`; update `docs/architecture/README.md`'s `CAP-006` row and `ARCH-001`'s note that the one-jar-to-one-bot-directory assumption "has to give."
- [ ] Record `ADR-002` (map onto Tank Royale's native team model, per `AN-013`), linking `CAP-006`, `AN-013`, and the affected architecture overview.
- [ ] Update `P-001`: `M-005` stays `todo` (its exit criterion is unmet until the follow-up lands), but note in the milestone row or a nearby note that its foundational wrapper/peer groundwork landed in `CH-010`.
- [ ] Run the tier-1 unit suite; retain any environment limitation in the handoff.
- [ ] Digest: regenerate indexes, run `clue validate`, commit the final state, and delete this change workspace.
