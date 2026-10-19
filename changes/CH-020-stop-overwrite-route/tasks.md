# Tasks

- [ ] Retire `ROUTE-005` and add `ROUTE-013` to CAP-008 for stop overwrite, preserving existing stop, resume, and independence-flag coverage.
- [ ] Route `BotPeer.stop(boolean)` through `IBot.stop(overwrite)` while preserving the boolean argument.
- [ ] Add focused positive and negative unit evidence for overwrite-stop routing and prove no neighboring peer call is used.
- [ ] Update the CAP-008 design and `CHANGELOG.md` to describe the restored Classic behavior.
- [ ] Run the relevant bridge unit checks and build the bridge and wrapper jars.
- [ ] Retest `jaybot.bots.Oddball` with official Melee parameters, record repair-linked evidence in the parity registry, and diagnose any remaining difference.
- [ ] Run the required Cliewen validation and review loop on the committed candidate.
- [ ] Digest the accepted capability and registry changes, then remove this change workspace.
