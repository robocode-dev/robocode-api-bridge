"""Tracked, append-only parity observations for the compatibility harness."""

from __future__ import annotations

import hashlib
import json
import re
from collections import Counter
from pathlib import Path


SCHEMA_VERSION = 2
EXCEPTION_RE = re.compile(r"\b((?:[a-zA-Z_$][\w$]*\.)+[A-Z][\w$]*(?:Exception|Error))\b")
FRAME_RE = re.compile(r"^\s*at\s+([\w.$]+)\([^)]*\)", re.MULTILINE)
FRAME_NOISE_PREFIXES = (
    "java.", "javax.", "jdk.", "sun.", "robocode.", "dev.robocode.", "net.sf.robocode."
)
UNRESOLVED_STATUSES = frozenset((
    "DISCREPANCY (errors)", "DISCREPANCY (score)", "DISCREPANCY (no score)",
    "DISCREPANCY (outcome)", "CONFIRMED (score)", "FAIL (RC)", "FAIL (TR)", "FAIL (both)",
))


def sha256_file(path: Path) -> str | None:
    if not path.is_file():
        return None
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def error_signatures(errors: list[str], log_text: str = "") -> list[dict[str, str]]:
    """Return exception class plus first legacy application frame for each distinct error."""
    text = "\n".join(errors + ([log_text] if log_text else []))
    signatures: set[tuple[str, str]] = set()
    for match in EXCEPTION_RE.finditer(text):
        origin = "unknown"
        following = text[match.end():]
        for frame in FRAME_RE.finditer(following):
            candidate = frame.group(1)
            if not candidate.startswith(FRAME_NOISE_PREFIXES):
                origin = candidate
                break
        signatures.add((match.group(1), origin))
    return [
        {"exception": exception, "origin": origin}
        for exception, origin in sorted(signatures)
    ]


def signature_keys(signatures: list[dict[str, str]]) -> set[tuple[str, str]]:
    return {(s["exception"], s.get("origin", "unknown")) for s in signatures}


def compare_errors(rc: list[dict[str, str]], tr: list[dict[str, str]]) -> dict[str, list[dict[str, str]]]:
    rc_keys, tr_keys = signature_keys(rc), signature_keys(tr)
    # A robot can catch and print an exception without printing its stack trace. In that
    # case one engine may retain the exception class but lose the legacy application frame.
    # Known-vs-known origin differences remain meaningful; unknown attribution can
    # bridge only the missing frame for the same exception class.
    for exception in {key[0] for key in rc_keys | tr_keys}:
        rc_origins = {origin for current_exception, origin in rc_keys if current_exception == exception}
        tr_origins = {origin for current_exception, origin in tr_keys if current_exception == exception}
        if rc_origins and tr_origins and ("unknown" in rc_origins or "unknown" in tr_origins):
            rc_known = rc_origins - {"unknown"}
            tr_known = tr_origins - {"unknown"}
            if not rc_known or not tr_known:
                rc_keys = {key for key in rc_keys if key[0] != exception}
                tr_keys = {key for key in tr_keys if key[0] != exception}
            else:
                rc_keys.discard((exception, "unknown"))
                tr_keys.discard((exception, "unknown"))
    return {
        "classic_only": [
            {"exception": exception, "origin": origin}
            for exception, origin in sorted(rc_keys - tr_keys)
        ],
        "tank_royale_only": [
            {"exception": exception, "origin": origin}
            for exception, origin in sorted(tr_keys - rc_keys)
        ],
    }


def is_unresolved(status: str) -> bool:
    return status in UNRESOLVED_STATUSES


def registry_status(status: str) -> str:
    return "score-review" if status == "DISCREPANCY (score)" else status


def score_gap_confirmed(deltas: list[float], threshold: float, repeats: int = 5) -> bool:
    return len(deltas) >= repeats and abs(sum(deltas) / len(deltas)) > threshold


def load_registry(path: Path) -> dict:
    if path.exists():
        return json.loads(path.read_text(encoding="utf-8"))
    return {"schema_version": SCHEMA_VERSION, "subjects": {}}


def observation_id(key: str, entry: dict, manifest: dict, source_identity: dict) -> str:
    payload = json.dumps({
        "key": key, "entry": entry, "manifest": manifest, "source_identity": source_identity,
    }, sort_keys=True)
    return hashlib.sha256(payload.encode("utf-8")).hexdigest()[:16]


def subject_identity(key: str, entry: dict, collection_dir: Path) -> dict:
    collection, jar_name = key.split("/", 1)
    jar = collection_dir / collection / jar_name
    return {
        "key": key,
        "division": collection,
        "jar": jar_name,
        "jar_sha256": sha256_file(jar),
        "kind": "team" if entry.get("setup", {}).get("team") else "robot",
        "classic_selected": entry.get("rc", {}).get("selected"),
    }


def add_diagnosis(subject: dict, cause: str, owner: str, recorded_at: str) -> dict:
    """Append an immutable diagnosis event; prior triage remains reviewable."""
    events = subject.setdefault("diagnosis_events", [])
    event = {
        "id": f"diagnosis-{len(events) + 1}",
        "cause": cause,
        "owner": owner,
        "recorded_at": recorded_at,
    }
    events.append(event)
    return event


def diagnosis_for_cause(subject: dict, cause: str) -> dict | None:
    for event in reversed(subject.get("diagnosis_events", [])):
        if event.get("cause") == cause:
            return event
    return None


def latest_diagnosis(subject: dict) -> dict:
    events = subject.get("diagnosis_events", [])
    return events[-1] if events else {}


def _migrate_subject(subject: dict) -> None:
    """Make pre-event registry entries readable without losing their former triage."""
    subject.setdefault("diagnosis_events", [])
    legacy = subject.pop("diagnosis", None)
    if legacy and legacy.get("cause"):
        add_diagnosis(subject, legacy["cause"], legacy.get("owner"), "migrated")


def _same_observation(observation: dict, entry: dict, manifest: dict,
                      source_identity: dict) -> bool:
    """Recognize schema-1 observations whose ID predates per-observation identity."""
    return (
        observation.get("completed_at") == entry.get("completed_at")
        and observation.get("status") == entry.get("status")
        and observation.get("delta_pct") == entry.get("delta_pct")
        and observation.get("setup") == entry.get("setup")
        and observation.get("classic") == entry.get("rc", {})
        and observation.get("tank_royale") == entry.get("tr", {})
        and observation.get("confirmation") == entry.get("confirmation")
        and observation.get("retest") == entry.get("retest")
        and observation.get("manifest") == manifest
        and observation.get("source_identity") == source_identity
    )


def sync_state(registry: dict, state: dict, collection_dir: Path, manifest: dict) -> int:
    """Append state observations without replacing earlier evidence."""
    added = 0
    registry["schema_version"] = SCHEMA_VERSION
    subjects = registry.setdefault("subjects", {})
    for key, entry in sorted(state.get("robots", {}).items()):
        identity = subject_identity(key, entry, collection_dir)
        subject = subjects.setdefault(key, {
            "identity": identity,
            "diagnosis_events": [],
            "observations": [],
        })
        _migrate_subject(subject)
        for observation in subject["observations"]:
            observation.setdefault("source_identity", subject["identity"])
        oid = observation_id(key, entry, manifest, identity)
        if any(observation["id"] == oid or _same_observation(
                   observation, entry, manifest, identity)
               for observation in subject["observations"]):
            continue
        subject["observations"].append({
            "id": oid,
            "completed_at": entry.get("completed_at"),
            "status": entry.get("status"),
            "delta_pct": entry.get("delta_pct"),
            "setup": entry.get("setup"),
            "classic": entry.get("rc", {}),
            "tank_royale": entry.get("tr", {}),
            "confirmation": entry.get("confirmation"),
            "retest": entry.get("retest"),
            "source_identity": identity,
            "manifest": manifest,
        })
        subject["latest_observation"] = oid
        subject["status"] = registry_status(entry.get("status", "unknown"))
        added += 1
    for subject in subjects.values():
        _migrate_subject(subject)
        observations = subject.get("observations", [])
        if observations:
            subject["status"] = registry_status(observations[-1].get("status", "unknown"))
    return added


def render_markdown(registry: dict) -> str:
    subjects = registry.get("subjects", {})
    counts = Counter(subject.get("status", "unknown") for subject in subjects.values())
    lines = [
        "# Legacy robot parity registry",
        "",
        "This generated table is the reviewed index of the append-only observations in `parity-registry.json`. Classic Robocode is the reference; an unresolved row requires diagnosis and a focused retest after its repair.",
        "",
        "## Current status",
        "",
        "| Status | Subjects |",
        "|---|---:|",
    ]
    lines.extend(f"| {status} | {count} |" for status, count in sorted(counts.items()))
    lines.extend([
        "",
        "## Subjects",
        "",
        "| Subject | Division | Kind | Status | Latest observation | Cause | Owner |",
        "|---|---|---|---|---|---|---|",
    ])
    for key, subject in sorted(subjects.items()):
        identity = subject["identity"]
        diagnosis = latest_diagnosis(subject)
        lines.append(
            f"| {key} | {identity['division']} | {identity['kind']} | {subject.get('status', 'unknown')} | "
            f"{subject.get('latest_observation', '-')} | {diagnosis.get('cause') or '-'} | {diagnosis.get('owner') or '-'} |"
        )
    return "\n".join(lines) + "\n"


def save_registry(registry: dict, json_path: Path, markdown_path: Path) -> None:
    json_path.write_text(json.dumps(registry, indent=2, sort_keys=True) + "\n", encoding="utf-8")
    markdown_path.write_text(render_markdown(registry), encoding="utf-8")
