# Changelog

## Unreleased

- Clarify the bridge's current compatibility limits and distinguish anonymous opponent tracking from strategies that require real opponent identities.

- Preserve Classic's missing-data-file behavior for robots compiled before Robocode 1.8.2, so older bots do not read an empty file where Classic reports the file missing.
- Use the active bridge peer's energy when transforming terminal run loops in interface-based robots, so classes without `getEnergy()` can finish cleanly at round boundaries.
- Return `null` from `getTeammates()` when a legacy solo robot has no teammate IDs, matching Classic and preventing bots from indexing an empty array.
- Keep `getOthers()` aligned with the delivered turn and drop scans after their target dies, matching Classic event delivery and preventing legacy bots from indexing an empty opponent list.
- Map Tank Royale's one-based turns to Classic's zero-based robot time, so startup logic and status snapshots observe the same turn as Classic.
- Map Tank Royale event timestamps onto Classic's zero-based round time, so event callbacks and robot status report the same time for a turn.
- Defer a bullet-hit callback until `fireBullet()` callers can register the returned bullet, matching Classic's callback ordering and preventing legacy robots from failing on same-turn hits.
- Route legacy `Robot.stop(boolean)` overwrite calls to the matching Tank Royale API, preserving saved movement when the robot calls `stop(true)`.
- Truncate legacy robot and team author metadata to the Bot API's 50-character limit so long author names do not prevent wrapped bots from starting.
- Trim legacy robot and team author metadata in generated bot JSON so it matches the Bot API's canonical author identity and does not block bot startup.
- Copy a robot's bundled data file when `getDataFile()` resolves it, and create an empty file when no bundled resource exists, matching Classic's file initialization behavior.
- Transform infinite loops in direct `JuniorRobot` subclasses using their `energy` field, so wrapped robots stop at round boundaries without calling a missing `getEnergy()` method.
- Transform terminal `do-while(true)` robot run loops so the bridge can stop them cleanly at round boundaries, matching the existing stop behavior for other infinite-loop bytecode shapes.
- Keep each compatibility error signature attached to its own stack trace so a later robot frame cannot be misattributed to an earlier exception.
- Include living teammates in `getOthers()` and robot status, and keep that count from increasing when the robot dies.
- Preserve the bullet object and hash code returned by firing when delivering later bullet events, so legacy robots can retrieve statistics stored under that bullet.
- Register blocking fire commands before advancing the turn, and return null when the gun cannot fire instead of returning an untracked bullet.
- Keep known classic bot names consistent across scans, team messages, bullets, collisions, and robot-death events so legacy robots can match events to their recorded teammates.
- Give unmapped Tank Royale bots stable Classic-shaped anonymous names in legacy events while preserving names the Bot API provides, so robots that parse package, class, or version fields can keep running when opponent names are hidden.
- Fixed a Windows compatibility-sweep crash when process cleanup failed, so the harness retains cleanup diagnostics and records the battle result.
- Preserve projectile callback timestamps so legacy robots can match bullet outcomes to the turn when they occurred.
- Batch multiple team messages sent in one turn while preserving their order and directed recipients, and retain the existing transport for a single message.
- Split batches that exceed Tank Royale's per-message byte limit while preserving message order and recipients, so a large same-turn queue does not terminate the robot turn.
- Keep oversized legacy team-message queues within Tank Royale's per-turn logical-payload limit and carry remaining messages into later turns instead of failing the robot turn.
- Default to the published Tank Royale Bot API 1.4.0 and resolve it from Maven Central, so team names work with the matching 1.4.0 runner even when a different local API build uses the same version.
- Deliver each round's initial status callback before the legacy robot's `run()` method, matching Classic Robocode and preventing robots from reading uninitialized callback state.
- Preserve Classic's five-open-stream limit across rounds when legacy robots leave streams open, so the bridge reports the same stream-limit failures.
- Preserve classic scan target names and stable string identity so legacy robots continue to recognize repeated scans of the same bot.
- Match classic data-quota accounting when a robot replaces an existing file, so repeated rewrites do not consume quota cumulatively.
- Bounded compatibility-sweep timeout supervision so blocked error watching or process-tree cleanup cannot stall a checkpoint indefinitely, and retain Windows taskkill diagnostics when cleanup is incomplete.
- Kept cause-targeted compatibility retests limited to unresolved, diagnosed subjects even when local checkpoint state is missing.
- Fixed legacy bot startup and round-transition lifecycle races by attaching peers after game setup and containing stale callback tick errors during shutdown.
- Added two-engine conformance evidence for pending scan delivery, turn-boundary timing, custom-event removal, skipped turns, handler exceptions, and official melee-participant-count scans.
- Preserved classic handler-exception reporting by surfacing legacy callback failures at the bridge boundary while retaining Tank Royale's event queue.
- Added grouped team-robot staging and two-engine conformance evidence for team membership, teammate messages, directed-recipient isolation, droid no-scan behavior, and arbitrary serializable message payloads.
- Restored classic team names, including battle-wide duplicate suffixes, through the Tank Royale name map for team identity, teammate lookup, directed messaging, and message-event senders when paired with the matching Tank Royale Bot API and runner.
- Echo direct team messages addressed to the sending robot back to its message callback, matching Classic Robocode.
- Resolve a team member's class from its matching archive entry when duplicated robot properties metadata would otherwise stop team staging.
- Added supported integration evidence that checks skipped-turn telemetry, including warm-up events and completed empty captures, in the persisted parity registry.
- Include each staged bot's display name with skipped-turn telemetry, so captured turn events can be attributed without reconstructing bot IDs from process logs.
- Add class-filtered opt-in scan-event telemetry with turn, event time, peer time, and scanned bot ID to diagnose intermittent startup ordering failures without instrumenting every bot.
