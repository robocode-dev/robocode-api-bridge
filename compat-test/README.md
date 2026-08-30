# Bridge Compatibility Test Harness

Automated compatibility testing for the Robocode → Tank Royale API bridge.
For every legacy robot jar in the LiteRumble collection it runs the same battle
(robot vs. itself) on **classic Robocode** and on **Tank Royale** (robot wrapped via the
bridge), then compares scores and errors.

## Files

| File | Purpose |
|---|---|
| `compat_test.py` | Orchestrator: staging, checkpointing, error logs, report generation |
| `regression-set.json` | The pinned watch list the regression gate measures against |
| `trace-robot/` | A robot that reports its own state each turn, for `--trace` |
| `RcBattleWorker.java` | Single-file worker (run uncompiled) driving the classic Robocode Control API |
| `TrBattleWorker.java` | Single-file worker (run uncompiled) driving the Tank Royale Battle Runner API |

Each robot test spawns fresh worker JVMs, so a hanging or crashing robot can never take
down the harness — the orchestrator kills the whole process tree on timeout and records
the failure.

## Prerequisites

- **JDK 17+** on `PATH` (`java`), **Python 3.9+** (stdlib only).
- **A JDK 23 or older for the classic side.** Classic Robocode installs a `SecurityManager`
  to sandbox robots, and JDK 24 removed `SecurityManager` support outright -- so
  `-Djava.security.manager=allow` is no longer a deprecation warning but a fatal VM error,
  and classic cannot start at all on a JDK 24+ default. The harness auto-detects a suitable
  JDK; override with `--rc-java` or `COMPAT_RC_JAVA`. The Tank Royale side is unaffected.
- Robot collection at `C:\Code\LiteRumble robots` with `roborumble`, `meleerumble`,
  `teamrumble` subdirectories of `.jar` files.
- Classic Robocode installation at `C:\robocode` (1.10.3 tested).
- Built artifacts (all present after building the respective repos):
  - Tank Royale runner fat jar: `C:\Code\tank-royale\runner\examples\lib\robocode-tankroyale-runner.jar`
  - Bridge adapter: `robocode-api\build\libs\robocode-api-0.5.0.jar` (this repo, `gradlew :robocode-api:build`)
  - Robots wrapper: `robots-wrapper\build\libs\robots-wrapper-0.3.1.jar` (this repo, `gradlew :robots-wrapper:build`)
  - Tank Royale Bot API **1.0.2**, resolved from Maven Central by the Gradle build. The
    harness reads the jar from `~\.m2\repository\...\robocode-tankroyale-bot-api-1.0.2.jar`,
    where building the bridge once puts it. To try an unreleased Bot API, publish it with
    `gradlew :bot-api:java:publishToMavenLocal` in the tank-royale repository.

  > ⚠️ The bot-api version matters: it must be protocol-compatible with the server embedded
  > in the runner jar (an incompatible pairing leaves the robots idle, scoring 0 — newer
  > Bot APIs fail loudly on this). Do not fall back to 0.33.1: its event queue drops
  > deferred same-priority events, e.g. every other scan event for robots that call a
  > blocking method such as `fire()` inside `onScannedRobot`.

All paths are defaults only — override with CLI flags (`--collection-dir`,
`--robocode-home`, `--runner-jar`, `--bridge-api-jar`, `--wrapper-jar`, `--bot-api-jar`)
or the corresponding `COMPAT_*` environment variables.

## Division setups

Each division runs at its **official rumble parameters**, read from the classic
installation's `roborumble/` configuration. Constraint `C-003` in the corpus: these are not
ours to choose. A robot is tuned to its division and its ranking was earned at these
settings, so measuring it at anything else produces a number describing behaviour the
robot was never ranked on.

| Division | Battlefield | Rounds | Bots per battle |
|---|---|---:|---:|
| `roborumble` | 800x600 | 35 | 2 |
| `meleerumble` | 1000x1000 | 35 | 10 |
| `teamrumble` | 1200x1200 | 10 | teams |

`--rounds` overrides the round count for quick local runs and makes the result
incomparable with the rumble, so the report records the setup each row was measured at.

## Usage

```bash
cd compat-test
python compat_test.py                          # test all collections, resume-aware
python compat_test.py --collections roborumble --limit 50
python compat_test.py --only Waylander         # substring filter on jar name
python compat_test.py --rounds 5               # override the official round count
python compat_test.py --retry-failed           # re-run only FAIL/ERROR robots
python compat_test.py --force                  # re-run everything
python compat_test.py --report-only            # just regenerate the report
```

### Regression gate

```bash
python compat_test.py --regression             # re-measure the pinned watch list
python compat_test.py --regression --repeats 3 --band 20
```

Re-measures every bot in `regression-set.json` over averaged repeats and reports movement
from its recorded baseline. A single battle is not evidence about a bot -- one watched bot
swings by a factor of forty between runs on classic alone -- so scores are averaged before
they are judged, and the verdict is stated as movement from a baseline rather than as an
absolute delta.

Every baseline in the shipped watch list is `null`, deliberately. The deltas recorded there
were measured before the event-dispatch redesign and the Bot API upgrade, at ten rounds, in
a setup matching no division exactly. They say why a bot is watched; they are not something
to measure against. The gate reports `NO BASELINE` until a sweep at official parameters
records real ones.

A bot marked `noise` is always reported and never fails the run. A gate that fires on the
same bot every time is a gate people learn to ignore.

**Fail-fast on a bridge-only exception.** The classic side runs first, so its exception
signatures are the baseline. When the Tank Royale side throws a signature classic did not
produce, the battle is stopped there and the signature recorded -- no score, no averaging.
A score difference is a quantity that repetition can resolve; the same bot throwing only
under the bridge is a categorical fact that repetition cannot improve.

### Trace mode

```bash
python compat_test.py --trace                  # per-turn state from both engines
python compat_test.py --trace --trace-turns 80
```

Compiles `trace-robot/tracing/TraceRobot.java` against the classic API, runs it on both
engines, and prints the two per-turn streams side by side with the differing lines marked.
This is the behavioural comparison the score-gap work needs; until now nothing could show
behaviour at all, only the score at the end.

The robot reports from *inside*: neither engine will hand a per-turn view to the harness
from outside, and instrumenting an engine would mean measuring a build no robot will ever
run against. Reporting from inside measures what the robot perceives, which is the parity
question exactly, and one class compiled against the classic API runs on both engines
because reproducing that API is what the bridge is for.

Its movement is a fixed command sequence rather than a strategy, so that what differs
between the two traces is the engine rather than the robot.

Expect divergence to grow once the robots interact -- Tank Royale has no seed, so the two
battles are not the same battle. **The early turns, before anything is scanned, are where a
real mapping fault shows.**

> First observation from this tool, recorded and not yet explained: classic's first traced
> turn is `turn=0` and the bridge's is `turn=1`, and the bridge's state at its turn N
> matches classic's at turn N. So the robot's first execution appears to happen a turn later
> under the bridge rather than the turn number being offset. Worth noting alongside it that
> `BotPeer.getTime()` returns the Tank Royale turn number unchanged while the status mapper
> rebases the round number by one -- the codebase already treats the two engines' counting
> conventions as differing, in one place and not the other. Needs investigation before
> anything is concluded; it belongs to the score-gap milestones.

### Checkpointing / resume

Progress is written to `test_progress.json` after **every** robot (atomic replace).
Interrupt at any time (Ctrl+C, crash, reboot) — re-running resumes from the first
untested robot. Completed robots are never re-run unless `--force` (everything) or
`--retry-failed` (failures only) is given.

## Test methodology (per jar)

1. **Classic Robocode** — the jar is copied alone into a staging robots dir;
   `RcBattleWorker` runs a battle of the robot against itself (10 rounds default,
   800×600) via the Control API and records scores, battle errors, and each robot's
   console output (where classic Robocode prints robot exceptions).
2. **Tank Royale** — the jar is staged with `lib\` (bridge + bot-api + wrapper jars) and
   processed by the robots-wrapper; the generated boot script is patched to redirect the
   bot process's stdout/stderr into log files; the bot dir is duplicated so the two
   instances don't share log files; `TrBattleWorker` runs the identical setup via the
   Battle Runner API (embedded server, max TPS).
3. **Comparison** — scores are the *sum of both participants' total scores*. Errors are
   exception signatures scraped from consoles/log files (stack-trace shaped lines).

Full error details land in `errors/robocode/<robot>.log` and
`errors/tank-royale/<robot>.log`; the master table is `compatibility_report.md`.

**Every row states the setup it was measured at.** The report is regenerated from the state
file long after the battles ran, so a single header describing the current configuration
would restate every stored row as though it had been measured under today's settings --
which is how a stale result stops looking stale. A row measured at its division's official
parameters reads `official`; one measured at anything else says what it was measured at; one
stored before the harness recorded per-row setups reads `unrecorded`. `--report-only` is
therefore safe to run over a state file holding results from several eras.

### Status values

| Status | Meaning |
|---|---|
| `PASS` | Both ran; \|score delta\| ≤ threshold (default 25%); no TR-only errors |
| `DISCREPANCY (score)` | Both ran, but scores diverge beyond the threshold |
| `DISCREPANCY (errors)` | TR side threw errors the RC side didn't |
| `FAIL (TR)` / `FAIL (RC)` / `FAIL (both)` | The battle did not complete on that side |
| `SKIPPED-TR` | Team jar: classic result recorded, TR skipped (wrapper has no team support yet) |

## Caveats

- **Score variance**: robot-vs-itself battles are stochastic; at 10 rounds a healthy
  robot can still swing ±20–30%. Treat `DISCREPANCY (score)` as "worth a look", not
  proof of a bug; increase `--rounds` (e.g. 35) for more stable numbers.
- **Known noise filtered**: `RobotMethodReplacer`'s `while(true)` transform fails on
  modern JVMs ("class redefinition failed…") for virtually every robot. That message is
  kept in the log files but not counted as an error.
- **Runtime**: RC side ≈ 2–10 s per robot; TR side ≈ 10–60 s (boots server + two bot
  JVMs). A full roborumble sweep (~1100 jars) is an overnight job — use `--limit` to
  chunk it; checkpointing makes chunking free.
- Per-side timeout (`--timeout`, default 600 s) kills the entire worker process tree
  (server/booter/bot JVMs included) and records the robot as failed.
