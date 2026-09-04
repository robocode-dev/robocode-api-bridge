---
id: AN-011
type: analysis
status: active
links: [P-001, CAP-005, CAP-002, CAP-007, C-003, C-004, AN-001, AN-002, AN-005, AN-006, AN-007]
title: M-002 leaves one lower-score bot within the band and names causes for the other two
provenance: inferred
reversal-cost: low
---

# AN-011 — M-002 leaves one lower-score bot within the band and names causes for the other two

## What was investigated

Whether the three bots selected by the stale compatibility report as materially lower under Tank Royale — CodaFirst, Bl4ck, and AetosFirstBot — still diverge under the current bridge at official `roborumble` parameters, and whether each result can be given a named cause or a within-band disposition.

## Evidence boundary

The spike ran on 2026-09-04 in a prepared Windows 11 Pro environment using PowerShell 7.6.5, Python 3.14.2, the bridge repository at `299bbcafe7fa3b374f6b4f19bc02f8fe20a02592`, and the Tank Royale repository at `b79f256ea4c6f3bf2f309a5600d44f972dde63da`.

The classic side used the installation at `C:\robocode` and the harness-selected JDK 17.0.17; the surrounding JVM was OpenJDK 24.0.2. The bridge used `robocode-api-0.5.0.jar` (SHA-256 `D5C0D65C4F88DF2169E0F5FEE9C4781998B5823653E96906CB4B27DE0B8ECCAF`), `robots-wrapper-0.3.1.jar` (SHA-256 `A68A12532551C17117D6EEBDD716560C92263EC2A336328D4C6B02CB8EBA1B47`), the locally built Tank Royale runner (SHA-256 `2D3838F3BCAC4D73104D9D3C9B1EE98E54E5A0B8BDE7FBD8324FDBBA8B3719BB`), and the matched locally built Bot API 1.2.0 jar (SHA-256 `C854E1C9FD3316C948F4083AB17A854236E4744E8F2208023DA4B3EEEC9CF191`). The three rumble jars were staged read-only from `C:\Code\LiteRumble robots\roborumble`.

The population is the three lower-score rows named by the prior report and pinned in `compat-test/regression-set.json`; the six higher-score or separately classified watch-list rows belong to `M-003` or later work. Each pair was robot-versus-itself on classic and Tank Royale. The official setup was 800×600, 35 rounds, and two participants. The harness's ±25% evaluation threshold is the absolute score band used below; `C-004`'s ±15 percentage-point band is for movement from a future recorded baseline and is not yet applicable because `M-006` has not produced one.

Scores are stochastic and Tank Royale has no deterministic battle seed, so these results establish the presence or absence of a gross current divergence for these three bots, not exact cross-engine replay equivalence or population-wide quality.

## What was found

| Bot | Comparable samples | Classic mean | Tank Royale mean | Mean delta | Disposition |
|---|---:|---:|---:|---:|---|
| `AD.CodaFirst_1.1.jar` | 7 of 15 attempts | — | — | −44.1% on completed pairs | Lower; named lifecycle/tick-boundary cause |
| `acid.Bl4ck_1.0.jar` | 5 of 5 | 6,409.2 | 3,336.0 | −48.0% | Lower; named startup/timing and physics-sensitive cause |
| `aetos.AetosFirstBot_1.0.jar` | 5 of 5 | 13,923.6 | 13,701.0 | −1.6% | Within ±25% band |

The CodaFirst means are omitted from the table because the seven successful pairs were collected across retries used to characterize the fail-fast condition rather than one uninterrupted five-repeat gate run. Their successful deltas were −45.3%, −44.7%, −46.2%, −44.3%, −41.7%, −45.5%, and −41.0%, averaging −44.1%. Eight attempts stopped before a comparable score was produced.

Bl4ck's five deltas were −53.1%, −52.0%, −44.8%, −43.4%, and −46.6%, averaging −48.0%. Neither engine reported a robot exception in those pairs.

AetosFirstBot's five deltas were −3.2%, +0.8%, −0.6%, −3.9%, and −0.9%, averaging −1.6%. Neither engine reported a robot exception in those pairs.

### CodaFirst: a reproducible lifecycle/tick-boundary failure

The bridge-only signature was `dev.robocode.tankroyale.botapi.BotException: Game is not running or tick has not occurred yet. Make sure onTick() event handler has been called first`. The stack ran through `BaseBotInternals.getCurrentTickOrThrow` → `BaseBot.getEnergy` → `BaseBotInternals.setFire` → `BotPeer.setFire` → `robocode.AdvancedRobot.setFire` → `AD.CodaFirst.run`.

This is consistent with the bridge invoking the legacy run thread while a state-dependent fire operation can occur without a current Tank Royale tick. It is not a classic-side exception, and the harness stopped the Tank Royale battle without inventing a score as required by `C-004`. The same signature was observed in 8 of 15 attempts; the other 7 completed and all showed a large lower score. The exact ownership of the lifecycle mismatch — bridge scheduling versus Bot API tick availability — remains unverified, but the failure mode and call path are named and reproducible.

The read-only bytecode shows that CodaFirst's run loop reaches `setFire(3)` after its scan-driven `spinnyTime` state becomes true, so this is not evidence that the jar is malformed or that classic produced an equivalent exception.

### Bl4ck: lower score with no exception; startup timing and physics/state remain the named cause

The read-only bytecode shows a radar-lock robot whose run path starts with `turnRadarRightRadians(Double.POSITIVE_INFINITY)` and `scan()`. Its scan handler depends on radar and gun headings, body heading, position, battlefield dimensions, energy, and movement/turn state while performing predictive firing. It therefore exercises the unverified `CAP-002` physics/state surface rather than a single isolated mapper call.

The existing fixed-command trace was rerun during this spike. Its first classic report was at `turn=0` with gun heat `3.000`; the first bridge report was at `turn=1` with gun heat `2.900`. The trace tool and `AN-002` also establish that the two battles cannot be aligned by exact random state because Tank Royale has no seed. The observation names a one-turn startup/tick-boundary skew; Bl4ck's immediate infinite radar sweep and per-turn state-dependent targeting make that skew a credible cause of its persistent lower score. The measurement does not yet prove whether the skew, radar/physics semantics, or both account for the full −48.0% delta, so no narrower physics claim is made.

### AetosFirstBot: the old lower score does not reproduce as a material gap

AetosFirstBot completed all five official pairs with no errors and an average delta of −1.6%, inside the harness's ±25% absolute score band. This retires the old −37.5% single-run figure as a current material lower-score observation; it does not establish exact parity or remove the jar from the watch list before `M-006` supplies a baseline.

## What was tried and rejected

The historical report's ten-round deltas were not reused as baselines. `AN-001` and the watch-list comments state that they were measured at a setup matching no division and before the event-dispatch redesign and Bot API upgrade.

The three candidates were measured at official parameters rather than with `--rounds`, because an override would produce a number incomparable with the rumble under `C-003`.

The CodaFirst failed attempts were not averaged as zeroes or treated as score samples. They are categorical bridge-only failures under `C-004`; only completed pairs contributed to the score summary.

The AetosFirstBot result was not called proof of parity merely because it is within the score band. The result is a disposition for M-002, while exact behavior remains subject to the cross-engine randomness boundary in `AN-002`.

No production repair was made in this spike. The CodaFirst lifecycle issue and the Bl4ck startup/physics candidate need focused conformance or trace work before a correction can be chosen without guessing.

## What it means

`M-002`'s exit criterion is met for the three named lower-score bots: AetosFirstBot is within the current absolute score band, while CodaFirst and Bl4ck remain lower with named causes recorded here. `M-126` remains open because `SCORE-004` is still a Python-harness criterion and `AN-003` says that implementation is not a supported acceptance-evidence carrier.

The next plan consumer is `M-003`, which should apply the same official setup and evidence boundary to the three higher-score candidates. The CodaFirst lifecycle issue should be a focused repair candidate; the Bl4ck result should feed the later physics/state evidence work rather than be treated as a complete root-cause isolation.

## What this does not establish

It does not establish a complete root cause for Bl4ck, a final owner for CodaFirst's tick-boundary mismatch, a baseline for the regression gate, or parity for any robot outside the three-candidate population. It also does not establish that the current bridge's one-turn startup skew is the only cause of any score gap.
