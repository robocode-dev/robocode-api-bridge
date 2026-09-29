# Changelog

## Unreleased

- Deliver each round's initial status callback before the legacy robot's `run()` method, matching Classic Robocode and preventing robots from reading uninitialized callback state.
- Preserve classic scan target names and stable string identity so legacy robots continue to recognize repeated scans of the same bot.
- Close legacy data streams abandoned by worker threads between rounds after the previous bot thread has stopped, and track active stream instances so leaked handles cannot consume later rounds' five-stream quota.
- Match classic data-quota accounting when a robot replaces an existing file, so repeated rewrites do not consume quota cumulatively.
- Bounded compatibility-sweep timeout supervision so blocked error watching or process-tree cleanup cannot stall a checkpoint indefinitely, and retain Windows taskkill diagnostics when cleanup is incomplete.
- Kept cause-targeted compatibility retests limited to unresolved, diagnosed subjects even when local checkpoint state is missing.
- Fixed legacy bot startup and round-transition lifecycle races by attaching peers after game setup and containing stale callback tick errors during shutdown.
- Added two-engine conformance evidence for pending scan delivery, turn-boundary timing, custom-event removal, skipped turns, handler exceptions, and official melee-participant-count scans.
- Preserved classic handler-exception reporting by surfacing legacy callback failures at the bridge boundary while retaining Tank Royale's event queue.
- Added grouped team-robot staging and two-engine conformance evidence for team membership, teammate messages, directed-recipient isolation, droid no-scan behavior, and arbitrary serializable message payloads.
- Restored classic team names, including battle-wide duplicate suffixes, through the Tank Royale name map for team identity, teammate lookup, directed messaging, and message-event senders when paired with the matching Tank Royale Bot API and runner.
