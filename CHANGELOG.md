# Changelog

## Unreleased

- Fixed legacy bot startup and round-transition lifecycle races by attaching peers after game setup and containing stale callback tick errors during shutdown.
- Added two-engine conformance evidence for pending scan delivery, turn-boundary timing, custom-event removal, skipped turns, handler exceptions, and official melee-participant-count scans.
- Preserved classic handler-exception reporting by surfacing legacy callback failures at the bridge boundary while retaining Tank Royale's event queue.
- Added grouped team-robot staging and two-engine conformance evidence for team membership, teammate messages, directed-recipient isolation, droid no-scan behavior, and arbitrary serializable message payloads.
