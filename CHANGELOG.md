# Changelog

## Unreleased

- Include living teammates in `getOthers()` and robot status, and keep that count from increasing when the robot dies.
- Preserve the bullet object and hash code returned by firing when delivering later bullet events, so legacy robots can retrieve statistics stored under that bullet.
- Register blocking fire commands before advancing the turn, and return null when the gun cannot fire instead of returning an untracked bullet.
- Keep known classic bot names consistent across scans, team messages, bullets, collisions, and robot-death events so legacy robots can match events to their recorded teammates.
- Fixed a Windows compatibility-sweep crash when process cleanup failed, so the harness retains cleanup diagnostics and records the battle result.
- Preserve projectile callback timestamps so legacy robots can match bullet outcomes to the turn when they occurred.
- Batch multiple team messages sent in one turn while preserving their order and directed recipients, and retain the existing transport for a single message.
- Default to the published Tank Royale Bot API 1.4.0 and resolve it from Maven Central, so team names work with the matching 1.4.0 runner even when a different local API build uses the same version.
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
- Added supported integration evidence that checks skipped-turn telemetry, including warm-up events and completed empty captures, in the persisted parity registry.
