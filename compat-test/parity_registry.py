"""Tracked, append-only parity observations for the compatibility harness."""

from __future__ import annotations

import hashlib
import json
import re
from collections import Counter
from pathlib import Path


SCHEMA_VERSION = 1
EXCEPTION_RE = re.compile(r"\b((?:[a-zA-Z_$][\w$]*\.)+[A-Z][\w$]*(?:Exception|Error))\b")
FRAME_RE = re.compile(r"^\s*at\s+([\w.$]+)\([^)]*\)", re.MULTILINE)
FRAME_NOISE_PREFIXES = ("java.", "javax.", "jdk.", "sun.", "robocode.", "dev.robocode.")
UNRESOLVED_STATUSES = frozenset((
    "DISCREPANCY (errors)", "DISCREPANCY (score)", "DISCREPANCY (no score)",
    "DISCREPANCY (outcome)", "FAIL (RC)", "FAIL (TR)", "FAIL (both)",
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


def load_registry(path: Path) -> dict:
    if path.exists():
        return json.loads(path.read_text(encoding="utf-8"))
    return {"schema_version": SCHEMA_VERSION, "subjects": {}}


def observation_id(key: str, entry: dict, manifest: dict) -> str:
    payload = json.dumps({"key": key, "entry": entry, "manifest": manifest}, sort_keys=True)
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


def sync_state(registry: dict, state: dict, collection_dir: Path, manifest: dict) -> int:
    """Append state observations without replacing earlier evidence."""
    added = 0
    subjects = registry.setdefault("subjects", {})
    for key, entry in sorted(state.get("robots", {}).items()):
        subject = subjects.setdefault(key, {
            "identity": subject_identity(key, entry, collection_dir),
            "diagnosis": {"state": "untriaged", "cause": None, "owner": None},
            "observations": [],
        })
        oid = observation_id(key, entry, manifest)
        if any(observation["id"] == oid for observation in subject["observations"]):
            continue
        subject["observations"].append({
            "id": oid,
            "completed_at": entry.get("completed_at"),
            "status": entry.get("status"),
            "delta_pct": entry.get("delta_pct"),
            "setup": entry.get("setup"),
            "classic": entry.get("rc", {}),
            "tank_royale": entry.get("tr", {}),
            "manifest": manifest,
        })
        subject["latest_observation"] = oid
        subject["status"] = entry.get("status")
        added += 1
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
        diagnosis = subject.get("diagnosis", {})
        lines.append(
            f"| {key} | {identity['division']} | {identity['kind']} | {subject.get('status', 'unknown')} | "
            f"{subject.get('latest_observation', '-')} | {diagnosis.get('cause') or '-'} | {diagnosis.get('owner') or '-'} |"
        )
    return "\n".join(lines) + "\n"


def save_registry(registry: dict, json_path: Path, markdown_path: Path) -> None:
    json_path.write_text(json.dumps(registry, indent=2, sort_keys=True) + "\n", encoding="utf-8")
    markdown_path.write_text(render_markdown(registry), encoding="utf-8")
