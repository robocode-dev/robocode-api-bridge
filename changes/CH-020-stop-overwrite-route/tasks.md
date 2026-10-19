# Tasks

- [x] Retire `ROUTE-005` and add `ROUTE-013` to CAP-008 for stop overwrite, preserving existing stop, resume, and independence-flag coverage.
- [x] Route `BotPeer.stop(boolean)` through `IBot.stop(overwrite)` while preserving the boolean argument.
- [x] Add focused positive and negative unit evidence for overwrite-stop routing and prove no neighboring peer call is used.
- [x] Update the CAP-008 design and `CHANGELOG.md` to describe the restored Classic behavior.
- [x] Run `./gradlew :robocode-api:test` and build the bridge and wrapper jars.
- [x] Retest `jaybot.bots.Oddball` with official Melee parameters, record repair-linked evidence in the parity registry, and diagnose the remaining opponent-pool difference.
- [ ] Run the required Cliewen validation and review loop on the committed candidate.
- [ ] Digest the accepted capability and registry changes, then remove this change workspace.
