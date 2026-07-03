#!/usr/bin/env python3
"""
Automated compatibility test for the Robocode -> Tank Royale API bridge.

For every legacy robot jar in the LiteRumble collection, this script:

  1. Runs a classic Robocode battle (robot vs. itself) via the Robocode Control API
     (driven by RcBattleWorker.java in a separate JVM).
  2. Wraps the same jar with the bridge's robots-wrapper and runs the identical setup
     on Tank Royale via the Battle Runner API (driven by TrBattleWorker.java).
  3. Records scores and errors on both sides, writes per-robot error logs to
     errors/robocode/<robot>.log and errors/tank-royale/<robot>.log, and maintains
     a master markdown report (compatibility_report.md).

Progress is checkpointed to test_progress.json after every robot; re-running the
script resumes from the first untested robot. Use --force to re-test everything,
or --retry-failed to re-test only robots that previously failed/errored.

Team jars (teamrumble) are run on classic Robocode only; the robots-wrapper does not
generate Tank Royale team configs yet, so the TR side is reported as skipped.

Usage examples:
  python compat_test.py                                # test everything, resume-aware
  python compat_test.py --collections roborumble --limit 20
  python compat_test.py --only Waylander --rounds 5
  python compat_test.py --report-only                  # just regenerate the report
"""

import argparse
import json
import os
import re
import shutil
import subprocess
import sys
import time
from datetime import datetime, timezone
from pathlib import Path

# ----------------------------------------------------------------------------------
# Configuration (override with CLI options or environment variables)
# ----------------------------------------------------------------------------------

BASE_DIR = Path(__file__).resolve().parent

DEFAULTS = {
    "collection_dir": os.environ.get("COMPAT_COLLECTION_DIR", r"C:\Code\LiteRumble robots"),
    "robocode_home": os.environ.get("COMPAT_ROBOCODE_HOME", r"C:\robocode"),
    "runner_jar": os.environ.get(
        "COMPAT_RUNNER_JAR",
        r"C:\Code\tank-royale\runner\examples\lib\robocode-tankroyale-runner.jar"),
    "bridge_api_jar": os.environ.get(
        "COMPAT_BRIDGE_API_JAR",
        r"C:\Code\robocode-api-bridge\robocode-api\build\libs\robocode-api-0.5.0.jar"),
    "wrapper_jar": os.environ.get(
        "COMPAT_WRAPPER_JAR",
        r"C:\Code\robocode-api-bridge\robots-wrapper\build\libs\robots-wrapper-0.3.1.jar"),
    # NOTE: must be protocol/API compatible with what the bridge's robocode-api jar was
    # compiled against (0.32/0.33) AND with the server embedded in the runner jar.
    # bot-api 1.0.2 connects but robots stay idle; 0.33.1 works.
    "bot_api_jar": os.environ.get(
        "COMPAT_BOT_API_JAR",
        os.path.expanduser(r"~\.m2\repository\dev\robocode\tankroyale"
                           r"\robocode-tankroyale-bot-api\0.33.1"
                           r"\robocode-tankroyale-bot-api-0.33.1.jar")),
}

STATE_FILE = BASE_DIR / "test_progress.json"
REPORT_FILE = BASE_DIR / "compatibility_report.md"
ERRORS_DIR = BASE_DIR / "errors"
WORK_DIR = BASE_DIR / "work"

RC_WORKER = BASE_DIR / "RcBattleWorker.java"
TR_WORKER = BASE_DIR / "TrBattleWorker.java"

ALL_COLLECTIONS = ["roborumble", "meleerumble", "teamrumble"]

# Matches stack-trace-style error lines, e.g. "java.lang.NullPointerException: ..."
EXCEPTION_RE = re.compile(
    r"\b((?:[a-zA-Z_$][\w$]*\.)+[A-Z][\w$]*(?:Exception|Error))\b")

# Known universal noise that would otherwise flag every robot. These still appear in the
# per-robot error log files, but are not counted as errors.
# - "class redefinition failed": RobotMethodReplacer's while(true) transform fails under
#   bytebuddy class-reloading on modern JVMs for virtually every robot; the wrapper then
#   falls back to the untransformed robot class.
NOISE_PATTERNS = (
    "class redefinition failed",
    "Failed to transform robot class",
)
MAX_LOG_BYTES = 512 * 1024  # cap per captured bot output file


# ----------------------------------------------------------------------------------
# Small helpers
# ----------------------------------------------------------------------------------

def now_iso():
    return datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")


def split_jar_name(jar_name):
    """'pkg.Robot_1.2b.jar' -> ('pkg.Robot', '1.2b'). Version may be None."""
    base = jar_name[:-4] if jar_name.lower().endswith(".jar") else jar_name
    if "_" in base:
        classname, version = base.rsplit("_", 1)
        return classname, version
    return base, None


def sanitize(name):
    return re.sub(r'[<>:"/\\|?*]', "_", name)


def clean_dir(path: Path):
    if path.exists():
        shutil.rmtree(path, ignore_errors=True)
    path.mkdir(parents=True, exist_ok=True)


def read_capped(path: Path):
    try:
        size = path.stat().st_size
        with open(path, "rb") as f:
            if size > MAX_LOG_BYTES:
                f.seek(size - MAX_LOG_BYTES)
            return f.read().decode("utf-8", errors="replace")
    except OSError:
        return ""


def extract_errors(text):
    """Returns a list of exception/error signatures found in console/log text."""
    found = []
    for m in EXCEPTION_RE.finditer(text):
        name = m.group(1)
        # Grab the rest of the line for context
        line_start = text.rfind("\n", 0, m.start()) + 1
        line_end = text.find("\n", m.end())
        line = text[line_start:line_end if line_end != -1 else len(text)].strip()
        # Skip pure stack frame lines ("at foo.Bar...") so one trace counts once
        if line.startswith("at ") or line.startswith("Caused by:"):
            continue
        if any(noise in line for noise in NOISE_PATTERNS):
            continue
        found.append(line[:300] if line else name)
    return found


def kill_process_tree(proc: subprocess.Popen):
    """Kills a process and all of its children (bot JVMs, embedded server, booter)."""
    if proc.poll() is not None:
        return
    if sys.platform == "win32":
        subprocess.run(["taskkill", "/PID", str(proc.pid), "/T", "/F"],
                       capture_output=True)
    else:
        proc.kill()
    try:
        proc.wait(timeout=15)
    except subprocess.TimeoutExpired:
        proc.kill()


def run_java(cmd, cwd, timeout):
    """Runs a java command; returns (returncode, stdout+stderr, timed_out)."""
    proc = subprocess.Popen(
        cmd, cwd=str(cwd), stdout=subprocess.PIPE, stderr=subprocess.STDOUT,
        text=True, encoding="utf-8", errors="replace")
    try:
        output, _ = proc.communicate(timeout=timeout)
        return proc.returncode, output or "", False
    except subprocess.TimeoutExpired:
        kill_process_tree(proc)
        try:
            output, _ = proc.communicate(timeout=10)
        except Exception:
            output = ""
        return -1, (output or "") + "\n<killed: orchestrator timeout>", True


# ----------------------------------------------------------------------------------
# State (checkpointing)
# ----------------------------------------------------------------------------------

def load_state():
    if STATE_FILE.exists():
        try:
            with open(STATE_FILE, encoding="utf-8") as f:
                return json.load(f)
        except (json.JSONDecodeError, OSError) as e:
            backup = STATE_FILE.with_suffix(".json.corrupt")
            shutil.copyfile(STATE_FILE, backup)
            print(f"WARNING: could not read state file ({e}); backed up to {backup}",
                  file=sys.stderr)
    return {"version": 1, "created": now_iso(), "settings": {}, "robots": {}}


def save_state(state):
    tmp = STATE_FILE.with_suffix(".json.tmp")
    with open(tmp, "w", encoding="utf-8") as f:
        json.dump(state, f, indent=1)
    os.replace(tmp, STATE_FILE)


# ----------------------------------------------------------------------------------
# Classic Robocode side
# ----------------------------------------------------------------------------------

def run_rc_battle(jar_path: Path, classname, version, opts):
    """Runs one classic Robocode battle for the jar; returns a result summary dict."""
    robots_dir = WORK_DIR / "rc-robots"
    home_dir = WORK_DIR / "rc-home"
    clean_dir(robots_dir)
    (home_dir / "config").mkdir(parents=True, exist_ok=True)
    (home_dir / "battles").mkdir(parents=True, exist_ok=True)
    shutil.copyfile(jar_path, robots_dir / jar_path.name)

    out_file = WORK_DIR / "rc-result.json"
    out_file.unlink(missing_ok=True)

    select = classname if version is None else f"{classname} {version}"
    cmd = [
        "java",
        "-Xmx1024M",
        "-XX:+IgnoreUnrecognizedVMOptions",
        "-Djava.security.manager=allow",  # required by classic Robocode on Java 12-23
        "--add-opens=java.base/sun.net.www.protocol.jar=ALL-UNNAMED",
        "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
        "--add-opens=java.desktop/javax.swing.text=ALL-UNNAMED",
        "--add-opens=java.desktop/sun.awt=ALL-UNNAMED",
        f"-DROBOTPATH={robots_dir}",
        "-cp", str(Path(opts.robocode_home) / "libs" / "*"),
        str(RC_WORKER),
        "--home", str(home_dir),
        "--select", select,
        "--rounds", str(opts.rounds),
        "--out", str(out_file),
        "--timeout", str(max(30, opts.timeout - 15)),
    ]
    started = time.time()
    returncode, output, timed_out = run_java(cmd, cwd=home_dir, timeout=opts.timeout)
    elapsed = time.time() - started

    return summarize_worker_result(
        out_file, returncode, output, timed_out, elapsed, engine="robocode")


def summarize_worker_result(out_file, returncode, output, timed_out, elapsed, engine):
    result = {
        "ok": False, "score": None, "scores": [], "errors": [],
        "error_count": 0, "elapsed": round(elapsed, 1), "log_text": "",
    }
    data = None
    if out_file.exists():
        try:
            with open(out_file, encoding="utf-8") as f:
                data = json.load(f)
        except (json.JSONDecodeError, OSError):
            pass

    if data is None:
        reason = "orchestrator timeout" if timed_out else \
            f"worker produced no result (exit code {returncode})"
        result["errors"] = [f"HARNESS: {reason}"]
        result["error_count"] = 1
        result["log_text"] = f"=== {engine} worker failed: {reason} ===\n\n{output}"
        return result

    result["ok"] = bool(data.get("ok"))
    result["selected"] = data.get("selected")
    scores = [p.get("score", 0) for p in data.get("participants", [])]
    result["scores"] = [round(float(s), 1) for s in scores]
    result["score"] = round(sum(float(s) for s in scores), 1) if scores else None

    errors = list(data.get("battleErrors", []))
    if data.get("fatal"):
        errors.insert(0, "FATAL: " + str(data["fatal"]).splitlines()[0])

    log_parts = []
    if data.get("fatal"):
        log_parts.append("=== Fatal harness error ===\n" + data["fatal"])
    for i, err in enumerate(data.get("battleErrors", [])):
        log_parts.append(f"=== Battle error {i + 1} ===\n{err}")
    for i, console in enumerate(data.get("consoles", [])):
        console_errors = extract_errors(console)
        errors.extend(console_errors)
        if console_errors:
            log_parts.append(f"=== Robot {i + 1} console ===\n{console}")
    for line in data.get("runnerLog", []):
        for sig in extract_errors(line):
            errors.append(sig)
    if data.get("runnerLog") and any(extract_errors(l) for l in data["runnerLog"]):
        log_parts.append("=== Runner log (tail) ===\n" + "\n".join(data["runnerLog"]))

    result["errors"] = errors[:100]
    result["error_count"] = len(errors)
    result["log_text"] = "\n\n".join(log_parts)
    return result


# ----------------------------------------------------------------------------------
# Tank Royale side
# ----------------------------------------------------------------------------------

def ensure_tr_lib(opts):
    lib_dir = WORK_DIR / "tr-bots" / "lib"
    lib_dir.mkdir(parents=True, exist_ok=True)
    for jar in (opts.bridge_api_jar, opts.bot_api_jar, opts.wrapper_jar):
        src = Path(jar)
        dst = lib_dir / src.name
        if not dst.exists() or dst.stat().st_mtime < src.stat().st_mtime:
            shutil.copyfile(src, dst)


def patch_boot_scripts(bot_dir: Path):
    """Redirects the wrapped bot's stdout/stderr into files inside the bot dir."""
    for script in list(bot_dir.glob("*.cmd")) + list(bot_dir.glob("*.sh")):
        content = script.read_text(encoding="utf-8", errors="replace")
        content = content.replace(">nul", "").rstrip()
        if "stdout.log" not in content:
            content += " >stdout.log 2>stderr.log"
        script.write_text(content, encoding="utf-8")


def duplicate_bot_dir(bot_dir: Path):
    """Creates a sibling copy of the bot dir so two independent processes never share
    stdout/stderr log files. The copy keeps the original .json (read by Wrapper.java)
    and gains <copyname>.json/.cmd/.sh (read by the booter)."""
    name = bot_dir.name
    copy_dir = bot_dir.parent / (name + "-2")
    if copy_dir.exists():
        shutil.rmtree(copy_dir, ignore_errors=True)
    shutil.copytree(bot_dir, copy_dir)
    for ext in (".json", ".cmd", ".sh"):
        src = copy_dir / (name + ext)
        if src.exists():
            if ext == ".json":
                shutil.copyfile(src, copy_dir / (copy_dir.name + ext))  # keep original
            else:
                src.rename(copy_dir / (copy_dir.name + ext))
    return copy_dir


def wrap_jar_for_tr(jar_path: Path, classname, version, opts):
    """Stages the jar, runs the robots-wrapper, returns (bot_dir, error_message)."""
    staging = WORK_DIR / "tr-bots"
    staging.mkdir(parents=True, exist_ok=True)
    ensure_tr_lib(opts)

    # Remove artifacts from the previous robot (keep lib/)
    for entry in staging.iterdir():
        if entry.name == "lib":
            continue
        if entry.is_dir():
            shutil.rmtree(entry, ignore_errors=True)
        else:
            entry.unlink(missing_ok=True)

    shutil.copyfile(jar_path, staging / jar_path.name)

    returncode, output, timed_out = run_java(
        ["java", "-jar", opts.wrapper_jar, str(staging)], cwd=staging, timeout=120)
    if returncode != 0:
        return None, f"robots-wrapper failed (exit {returncode}):\n{output}"

    bot_dirs = [d for d in staging.iterdir() if d.is_dir() and d.name != "lib"]
    if not bot_dirs:
        return None, ("robots-wrapper produced no bot directory "
                      "(no robot .properties in jar?):\n" + output)

    expected = f"{classname}_{version}" if version else classname
    chosen = next((d for d in bot_dirs if d.name == expected), None)
    if chosen is None:
        chosen = next((d for d in bot_dirs if d.name.startswith(classname)), bot_dirs[0])

    patch_boot_scripts(chosen)
    return chosen, None


def run_tr_battle(jar_path: Path, classname, version, opts):
    """Wraps the jar and runs one Tank Royale battle; returns a result summary dict."""
    bot_dir, wrap_error = wrap_jar_for_tr(jar_path, classname, version, opts)
    if wrap_error:
        return {
            "ok": False, "score": None, "scores": [], "elapsed": 0.0,
            "errors": ["HARNESS: " + wrap_error.splitlines()[0]],
            "error_count": 1, "log_text": "=== Wrapping failed ===\n" + wrap_error,
        }

    bot_dir2 = duplicate_bot_dir(bot_dir)
    out_file = WORK_DIR / "tr-result.json"
    out_file.unlink(missing_ok=True)

    cmd = [
        "java",
        "-cp", opts.runner_jar,
        str(TR_WORKER),
        "--bot", str(bot_dir),
        "--bot2", str(bot_dir2),
        "--rounds", str(opts.rounds),
        "--out", str(out_file),
        "--timeout", str(max(30, opts.timeout - 15)),
    ]
    started = time.time()
    returncode, output, timed_out = run_java(cmd, cwd=WORK_DIR, timeout=opts.timeout)
    elapsed = time.time() - started

    result = summarize_worker_result(
        out_file, returncode, output, timed_out, elapsed, engine="tank-royale")

    # Bot processes write stdout/stderr into their bot dirs; scan them for errors.
    bot_logs = []
    for d in (bot_dir, bot_dir2):
        for log_name in ("stderr.log", "stdout.log"):
            text = read_capped(d / log_name)
            if not text.strip():
                continue
            errors = extract_errors(text)
            result["errors"].extend(errors)
            result["error_count"] += len(errors)
            if errors or log_name == "stderr.log":
                bot_logs.append(f"=== {d.name}/{log_name} ===\n{text}")
    if bot_logs:
        result["log_text"] = (result["log_text"] + "\n\n" if result["log_text"] else "") \
            + "\n\n".join(bot_logs)
    result["errors"] = result["errors"][:100]
    return result


# ----------------------------------------------------------------------------------
# Evaluation & reporting
# ----------------------------------------------------------------------------------

def evaluate(rc, tr, threshold, tr_skipped):
    if tr_skipped:
        return "SKIPPED-TR", None
    rc_ok, tr_ok = rc["ok"], tr["ok"]
    if not rc_ok and not tr_ok:
        return "FAIL (both)", None
    if not rc_ok:
        return "FAIL (RC)", None
    if not tr_ok:
        return "FAIL (TR)", None

    delta = None
    if rc["score"] and rc["score"] > 0 and tr["score"] is not None:
        delta = round((tr["score"] - rc["score"]) / rc["score"] * 100.0, 1)

    if tr["error_count"] > 0 and rc["error_count"] == 0:
        return "DISCREPANCY (errors)", delta
    if delta is not None and abs(delta) > threshold:
        return "DISCREPANCY (score)", delta
    if delta is None:
        return "DISCREPANCY (no score)", None
    return "PASS", delta


def write_error_log(engine_dir_name, robot_name, log_text):
    log_dir = ERRORS_DIR / engine_dir_name
    log_dir.mkdir(parents=True, exist_ok=True)
    log_file = log_dir / (sanitize(robot_name) + ".log")
    if log_text.strip():
        log_file.write_text(log_text, encoding="utf-8")
        return True
    log_file.unlink(missing_ok=True)
    return False


def fmt_score(value):
    return "-" if value is None else f"{value:,.0f}"


def fmt_errors(count, engine_dir_name, robot_name, has_log):
    if count == 0:
        return "0"
    if has_log:
        rel = f"errors/{engine_dir_name}/{sanitize(robot_name)}.log"
        return f"[{count}]({rel.replace(' ', '%20')})"
    return str(count)


def regenerate_report(state):
    robots = state.get("robots", {})
    counts = {}
    for entry in robots.values():
        status = entry.get("status", "?").split(" ")[0]
        counts[status] = counts.get(status, 0) + 1

    lines = []
    lines.append("# Robocode API Bridge — Compatibility Report")
    lines.append("")
    lines.append(f"Generated: {now_iso()}  ")
    settings = state.get("settings", {})
    lines.append(f"Battle: robot vs. itself, {settings.get('rounds', '?')} rounds, "
                 f"800×600 arena. Score delta threshold: "
                 f"±{settings.get('threshold', '?')}%.")
    lines.append("")
    lines.append(f"**Tested: {len(robots)}** — " + ", ".join(
        f"{k}: {v}" for k, v in sorted(counts.items())))
    lines.append("")
    lines.append("| Robot (JAR) | RC Score | TR Score | Score Delta (%) "
                 "| RC Errors | TR Errors | Status |")
    lines.append("|---|---:|---:|---:|---:|---:|---|")

    for key in sorted(robots):
        entry = robots[key]
        rc = entry.get("rc", {})
        tr = entry.get("tr", {})
        robot_name = Path(key).name[:-4] if key.lower().endswith(".jar") else key
        delta = entry.get("delta_pct")
        delta_str = "-" if delta is None else f"{delta:+.1f}"
        rc_err = fmt_errors(rc.get("error_count", 0), "robocode", robot_name,
                            rc.get("has_log", False))
        tr_err = "-" if entry.get("status") == "SKIPPED-TR" else \
            fmt_errors(tr.get("error_count", 0), "tank-royale", robot_name,
                       tr.get("has_log", False))
        tr_score = "-" if entry.get("status") == "SKIPPED-TR" else fmt_score(tr.get("score"))
        lines.append(f"| {key} | {fmt_score(rc.get('score'))} | {tr_score} "
                     f"| {delta_str} | {rc_err} | {tr_err} | {entry.get('status', '?')} |")

    lines.append("")
    lines.append("Legend: **RC** = classic Robocode, **TR** = Tank Royale (via bridge). "
                 "Scores are the sum of both participants' total scores. "
                 "SKIPPED-TR = team jars; the robots-wrapper does not support "
                 "Tank Royale teams yet.")
    lines.append("")
    REPORT_FILE.write_text("\n".join(lines), encoding="utf-8")


# ----------------------------------------------------------------------------------
# Main driver
# ----------------------------------------------------------------------------------

def discover_jars(opts):
    jars = []
    collection_root = Path(opts.collection_dir)
    for collection in opts.collections:
        col_dir = collection_root / collection
        if not col_dir.is_dir():
            print(f"WARNING: collection dir not found: {col_dir}", file=sys.stderr)
            continue
        for jar in sorted(col_dir.glob("*.jar")):
            if opts.only and opts.only.lower() not in jar.name.lower():
                continue
            jars.append((collection, jar))
    return jars


def should_run(entry, opts):
    if entry is None or opts.force:
        return True
    if opts.retry_failed:
        return entry.get("status", "").startswith(("FAIL", "ERROR", "HARNESS"))
    return False


def check_prerequisites(opts):
    problems = []
    for label, path in [
        ("robot collection", opts.collection_dir),
        ("classic Robocode home", opts.robocode_home),
        ("Tank Royale runner jar", opts.runner_jar),
        ("bridge robocode-api jar", opts.bridge_api_jar),
        ("robots-wrapper jar", opts.wrapper_jar),
        ("Tank Royale bot-api jar", opts.bot_api_jar),
    ]:
        if not Path(path).exists():
            problems.append(f"  - {label} not found: {path}")
    if shutil.which("java") is None:
        problems.append("  - 'java' not found on PATH (JDK 17+ required)")
    if problems:
        print("Missing prerequisites:\n" + "\n".join(problems), file=sys.stderr)
        sys.exit(2)


def parse_args():
    p = argparse.ArgumentParser(
        description="Robocode vs. Tank Royale bridge compatibility test",
        formatter_class=argparse.ArgumentDefaultsHelpFormatter)
    p.add_argument("--collections", default=",".join(ALL_COLLECTIONS),
                   help="comma-separated collection subdirectories to test")
    p.add_argument("--rounds", type=int, default=10, help="rounds per battle")
    p.add_argument("--timeout", type=int, default=600,
                   help="max seconds per engine run (battle + JVM startup)")
    p.add_argument("--threshold", type=float, default=25.0,
                   help="score delta %% beyond which a robot is flagged as discrepancy")
    p.add_argument("--only", help="only test jars whose name contains this substring")
    p.add_argument("--limit", type=int, help="stop after testing N robots this session")
    p.add_argument("--force", action="store_true",
                   help="re-run robots that already have results")
    p.add_argument("--retry-failed", action="store_true",
                   help="re-run only robots whose previous status was FAIL/ERROR")
    p.add_argument("--report-only", action="store_true",
                   help="regenerate compatibility_report.md from the state file and exit")
    p.add_argument("--collection-dir", default=DEFAULTS["collection_dir"])
    p.add_argument("--robocode-home", default=DEFAULTS["robocode_home"])
    p.add_argument("--runner-jar", default=DEFAULTS["runner_jar"])
    p.add_argument("--bridge-api-jar", default=DEFAULTS["bridge_api_jar"])
    p.add_argument("--wrapper-jar", default=DEFAULTS["wrapper_jar"])
    p.add_argument("--bot-api-jar", default=DEFAULTS["bot_api_jar"])
    opts = p.parse_args()
    opts.collections = [c.strip() for c in opts.collections.split(",") if c.strip()]
    return opts


def main():
    opts = parse_args()
    state = load_state()
    state["settings"] = {"rounds": opts.rounds, "threshold": opts.threshold}

    if opts.report_only:
        regenerate_report(state)
        print(f"Report regenerated: {REPORT_FILE}")
        return 0

    check_prerequisites(opts)
    WORK_DIR.mkdir(parents=True, exist_ok=True)

    jars = discover_jars(opts)
    todo = [(c, j) for c, j in jars
            if should_run(state["robots"].get(f"{c}/{j.name}"), opts)]
    print(f"Found {len(jars)} jars; {len(jars) - len(todo)} already tested, "
          f"{len(todo)} to test.")

    tested = 0
    session_started = time.time()
    try:
        for collection, jar in todo:
            if opts.limit is not None and tested >= opts.limit:
                break
            key = f"{collection}/{jar.name}"
            robot_name = jar.name[:-4]
            classname, version = split_jar_name(jar.name)
            index = f"[{tested + 1}/{min(len(todo), opts.limit or len(todo))}]"
            print(f"{index} {key} ...", flush=True)

            rc = run_rc_battle(jar, classname, version, opts)
            rc["has_log"] = write_error_log("robocode", robot_name, rc.pop("log_text", ""))

            tr_skipped = (collection == "teamrumble")
            if tr_skipped:
                tr = {"ok": False, "score": None, "scores": [], "errors": [],
                      "error_count": 0, "elapsed": 0.0, "skipped": True,
                      "has_log": False}
            else:
                tr = run_tr_battle(jar, classname, version, opts)
                tr["has_log"] = write_error_log("tank-royale", robot_name,
                                                tr.pop("log_text", ""))

            status, delta = evaluate(rc, tr, opts.threshold, tr_skipped)
            state["robots"][key] = {
                "status": status,
                "delta_pct": delta,
                "rc": {k: rc.get(k) for k in
                       ("ok", "score", "scores", "error_count", "elapsed",
                        "errors", "has_log", "selected")},
                "tr": {k: tr.get(k) for k in
                       ("ok", "score", "scores", "error_count", "elapsed",
                        "errors", "has_log", "skipped")},
                "completed_at": now_iso(),
            }
            save_state(state)
            regenerate_report(state)
            tested += 1

            delta_str = "-" if delta is None else f"{delta:+.1f}%"
            tr_str = "skipped" if tr_skipped else (
                f"{fmt_score(tr['score'])} ({tr['error_count']} err, "
                f"{tr['elapsed']:.0f}s)")
            print(f"    RC={fmt_score(rc['score'])} ({rc['error_count']} err, "
                  f"{rc['elapsed']:.0f}s)  TR={tr_str}  delta={delta_str}  -> {status}",
                  flush=True)
    except KeyboardInterrupt:
        print("\nInterrupted — progress saved. Re-run to resume.", file=sys.stderr)
        save_state(state)
        regenerate_report(state)
        return 130

    save_state(state)
    regenerate_report(state)
    elapsed_min = (time.time() - session_started) / 60
    print(f"\nDone. Tested {tested} robots in {elapsed_min:.1f} min. "
          f"Report: {REPORT_FILE}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
