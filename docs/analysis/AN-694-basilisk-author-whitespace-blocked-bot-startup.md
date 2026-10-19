---
id: AN-694
type: analysis
status: active
links: [P-001, PDR-001, CAP-005, CAP-007, C-002, C-004]
title: Basilisk's author whitespace blocked Tank Royale bot startup
provenance: inferred
reversal-cost: low
---

# AN-694 — Basilisk's author whitespace blocked Tank Royale bot startup

## Risk investigated

Whether the Tank Royale connection timeout for kronenthaler.Basilisk_1.0.jar persisted after the recorded wrapper JSON repair, and which layer caused it.

## Evidence boundary

The read-only rumble jar kronenthaler.Basilisk_1.0.jar has SHA-256 303ca7f685da16603f72cdf3e4905b949d16f37fc8b599777336148c92e5d750. Both measurements used the prepared Windows PowerShell environment with the local LiteRumble collection at C:\Code\LiteRumble robots, Classic Robocode 1.11.1 at C:\robocode, official 2-participant, 35-round, 800×600 Roborumble parameters, and the matched local Tank Royale pair at commit 8bb5ba1f0bbc11cf007150ac0f4f966238d7a734.

The pre-repair observation 1be2d96c88506949 used bridge commit ae5e588553ef3281c9b13e5fcc5469273fb1b9f6 and wrapper SHA-256 e5d8fb31d88fc9c9db71763e591f1182afcdb0ad3cb2ba9d35ae655ba91d696a. Classic completed with 4,088 points and no errors; Tank Royale timed out after 30 seconds with no score, although its server log said both bots had joined. The generated bot JSON contained authors ["kronenthaler\t"].

At Tank Royale commit 8bb5ba1f0bbc11cf007150ac0f4f966238d7a734, BotInfo.processAuthors trims author strings before sending the handshake, BooterManager reads the JSON author without trimming, and BotMatcher compares the expected author string exactly ([BotInfo.java](https://github.com/robocode-dev/tank-royale/blob/8bb5ba1f0bbc11cf007150ac0f4f966238d7a734/bot-api/java/src/main/java/dev/robocode/tankroyale/botapi/BotInfo.java#L432-L455), [BooterManager.kt](https://github.com/robocode-dev/tank-royale/blob/8bb5ba1f0bbc11cf007150ac0f4f966238d7a734/runner/src/main/kotlin/dev/robocode/tankroyale/runner/internal/BooterManager.kt#L289-L304), [BotMatcher.kt](https://github.com/robocode-dev/tank-royale/blob/8bb5ba1f0bbc11cf007150ac0f4f966238d7a734/runner/src/main/kotlin/dev/robocode/tankroyale/runner/internal/BotMatcher.kt#L99-L102)). The runner's actual BotInfo snapshot was not captured; this normalization mismatch is inferred from the pinned source and is supported by the successful retest.

## What was tried

Two earlier official observations after wrapper repairs 8135a43 and 48871c9 still timed out with the same pending author identity. On the current observation, the JSON escape itself was valid: the tab was represented as \t, but the Bot API later removed it while the runner retained it in its expected identity.

The wrapper now trims authors when generating both individual-bot and team-bot JSON. The wrapper fat jar was rebuilt with SHA-256 30672d56c45d70b3ba3189681522a6a7cfd30626fa580d042b5847ad3da90dd0 and the change was committed as 4db840040c5fe11b0d1f9a6ee3b94d49e285b8e0. The post-repair official observation efd34ca162c83b99 completed with Classic at 4,490 points and Tank Royale at 4,730, a +5.3% delta within the 25% band. Both engines reported no errors; skipped-turn telemetry was captured as an empty event list. The registry status is PASS.

Changing Tank Royale's runner matching was not needed for this bridge case: emitting the Bot API's already-canonical author string made the expected and reported identities agree. No rumble jar was modified.

## Finding

The timeout was caused by trailing author whitespace in generated bot metadata: the Bot API trimmed it, while the runner matched against the untrimmed configuration value. Trimming author metadata in the wrapper resolved the current Basilisk discrepancy under the matched local artifacts. The evidence is one official pair and does not establish behavior for every legacy metadata edge case; the team JSON path received the same fix but was not separately retested here.

## M-006 handoff

Continue in registry order with roborumble/logiblocs.SittingDroid_1.0.jar (DISCREPANCY (no score)).
