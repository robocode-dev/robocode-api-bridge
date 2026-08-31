# Open questions

## Q-001 — May bridge conformance evidence consume the unreleased Tank Royale death-event repair?

`AN-009` recorded that the Tank Royale server emitted death events before its turn snapshot existed, so no bot received either its own death or another bot's death. The repair is now in Tank Royale `main` as `824028f9d` (`fix(server): deliver bot death events to the bots`), but it is not contained by a release tag.

The bridge's `robocode-api` module declares Bot API `1.0.2`; its conformance tier launches a separate runner jar. `C-002` requires the Bot API and runner server to be protocol compatible, but the bridge does not yet mechanically verify that compatibility for locally built upstream artifacts.

Should this change consume a locally built, pinned Tank Royale `main` server and Bot API pair to establish conformance evidence, wait for a compatible released pair, or defer these two criteria and take the next unblocked M-001 door instead?

This blocks implementation. A human answer will be recorded as a decision before work resumes.
