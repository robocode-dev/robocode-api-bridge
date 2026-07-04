# Bridge Compatibility Test Harness

Automated compatibility testing for the Robocode → Tank Royale API bridge.
For every legacy robot jar in the LiteRumble collection it runs the same battle
(robot vs. itself) on **classic Robocode** and on **Tank Royale** (robot wrapped via the
bridge), then compares scores and errors.

## Files

| File | Purpose |
|---|---|
| `compat_test.py` | Orchestrator: staging, checkpointing, error logs, report generation |
| `RcBattleWorker.java` | Single-file worker (run uncompiled) driving the classic Robocode Control API |
| `TrBattleWorker.java` | Single-file worker (run uncompiled) driving the Tank Royale Battle Runner API |

Each robot test spawns fresh worker JVMs, so a hanging or crashing robot can never take
down the harness — the orchestrator kills the whole process tree on timeout and records
the failure.

## Prerequisites

- **JDK 17+** on `PATH` (`java`), **Python 3.9+** (stdlib only).
- Robot collection at `C:\Code\LiteRumble robots` with `roborumble`, `meleerumble`,
  `teamrumble` subdirectories of `.jar` files.
- Classic Robocode installation at `C:\robocode` (1.10.3 tested).
- Built artifacts (all present after building the respective repos):
  - Tank Royale runner fat jar: `C:\Code\tank-royale\runner\examples\lib\robocode-tankroyale-runner.jar`
  - Bridge adapter: `robocode-api\build\libs\robocode-api-0.5.0.jar` (this repo, `gradlew :robocode-api:build`)
  - Robots wrapper: `robots-wrapper\build\libs\robots-wrapper-0.3.1.jar` (this repo, `gradlew :robots-wrapper:build`)
  - Tank Royale Bot API **1.0.2** from `~\.m2\repository\...\robocode-tankroyale-bot-api-1.0.2.jar`
    (publish it with `gradlew :bot-api:java:publishToMavenLocal` in the tank-royale repository)

  > ⚠️ The bot-api version matters: it must be protocol-compatible with the server embedded
  > in the runner jar (an incompatible pairing leaves the robots idle, scoring 0 — newer
  > Bot APIs fail loudly on this). Do not fall back to 0.33.1: its event queue drops
  > deferred same-priority events, e.g. every other scan event for robots that call a
  > blocking method such as `fire()` inside `onScannedRobot`.

All paths are defaults only — override with CLI flags (`--collection-dir`,
`--robocode-home`, `--runner-jar`, `--bridge-api-jar`, `--wrapper-jar`, `--bot-api-jar`)
or the corresponding `COMPAT_*` environment variables.

## Usage

```bash
cd compat-test
python compat_test.py                          # test all collections, resume-aware
python compat_test.py --collections roborumble --limit 50
python compat_test.py --only Waylander         # substring filter on jar name
python compat_test.py --rounds 20              # more rounds = less score variance
python compat_test.py --retry-failed           # re-run only FAIL/ERROR robots
python compat_test.py --force                  # re-run everything
python compat_test.py --report-only            # just regenerate the report
```

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
