"""Export classified acceptance-criterion JUnit methods from Java test sources.

A JUnit method proves a criterion when its name follows
`test<PREFIX><number>_<Type><Direction>_<Description>`, for example
`testFIO003_IntegrationPositive_WritePastQuotaIsRefused`.
"""

import json
from pathlib import Path
import re
import sys


CLASS_NAME = re.compile(r"\b(?:class|interface|enum)\s+([A-Za-z_][A-Za-z0-9_]*)")
JAVA_METHOD = re.compile(r"\bvoid\s+([A-Za-z_$][A-Za-z0-9_$]*)\s*\(")
CRITERION_LIKE = re.compile(r"test[A-Z]+[0-9]+[a-z]*_")
CLASSIFIED_NAME = re.compile(
    r"test(?P<prefix>[A-Z]+)(?P<number>[0-9]+[a-z]*)_"
    r"(?P<type>Unit|Integration|E2E|Performance)"
    r"(?P<direction>Positive|Negative)_(?P<description>[A-Za-z0-9_$]+)"
)
TEST_ANNOTATIONS = ("@Test", "@ParameterizedTest", "@RepeatedTest", "@TestFactory")


def collect(root, files):
    references, diagnostics, seen = [], [], set()

    def report(path, subject, message):
        diagnostics.append({"path": path, "subject": subject, "message": message})

    for name in sorted(files):
        if not name.endswith(".java") or "/src/test/" not in name:
            continue
        source = (root / name).read_text(encoding="utf-8").replace("\r\n", "\n")
        owner, pending = Path(name).stem, []
        for line in source.splitlines():
            stripped = line.strip()
            if not stripped or stripped.startswith(("//", "/*", "*")):
                continue
            if stripped.startswith("@"):
                pending.append(stripped)
                continue
            found = JAVA_METHOD.search(stripped)
            if found is None:
                declared = CLASS_NAME.search(stripped)
                if declared:
                    owner = declared.group(1)
                pending = []
                continue
            method = found.group(1)
            is_test = any(a in " ".join(pending) for a in TEST_ANNOTATIONS)
            pending = []
            if not is_test or not CRITERION_LIKE.match(method):
                continue
            subject = f"{owner}.{method}"
            classified = CLASSIFIED_NAME.fullmatch(method)
            if classified is None:
                report(name, subject, "JUnit method does not follow the classified JVM naming convention")
            elif (name, subject) in seen:
                report(name, subject, "duplicate executable subject")
            else:
                seen.add((name, subject))
                references.append({
                    "id": f"{classified.group('prefix')}-{classified.group('number')}",
                    "path": name,
                    "subject": subject,
                    "type": classified.group("type"),
                    "direction": classified.group("direction").lower(),
                })

    return {"references": references, "diagnostics": diagnostics}


if __name__ == "__main__":
    request = json.load(sys.stdin)
    print(json.dumps(collect(Path(request["root"]), request["files"])))
