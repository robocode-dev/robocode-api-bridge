# Changelog

## Unreleased

- Bounded compatibility-sweep timeout supervision so blocked error watching or process-tree cleanup cannot stall a checkpoint indefinitely.
- Fixed legacy bot startup and round-transition lifecycle races by attaching peers after game setup and containing stale callback tick errors during shutdown.
- Added two-engine conformance evidence for pending scan delivery, turn-boundary timing, custom-event removal, skipped turns, handler exceptions, and official melee-participant-count scans.
- Preserved classic handler-exception reporting by surfacing legacy callback failures at the bridge boundary while retaining Tank Royale's event queue.
- Added grouped team-robot staging and two-engine conformance evidence for team membership, teammate messages, directed-recipient isolation, droid no-scan behavior, and arbitrary serializable message payloads.
- Restored classic team names, including battle-wide duplicate suffixes, through the Tank Royale name map for team identity, teammate lookup, directed messaging, and message-event senders when paired with the matching Tank Royale Bot API and runner.
