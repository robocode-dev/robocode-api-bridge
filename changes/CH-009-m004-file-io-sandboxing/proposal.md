---
id: CH-009
type: change
status: proposed
links: [P-001, M-004, CAP-004, C-005, FIO-001, FIO-002, FIO-003, FIO-004, IDR-002]
title: Confine robot file I/O to the robot's data directory
---

# CH-009 — Confine robot file I/O to the robot's data directory

## What

Give `RobotData.getDataFile`/`getDataDirectory` — the one resolution point `robocode.RobocodeFileOutputStream` and `robocode.RobocodeFileWriter` already route through — the confinement classic Robocode has and the bridge does not: an absolute or traversing path a robot names is re-rooted inside the robot's data directory rather than passed through, and the directory's total size is capped at the documented 200000-byte quota. Move `FIO-001`, `FIO-002`, `FIO-003` out of `@draft` with integration evidence, and give `C-005` machine enforcement in place of the agent-judgment note it currently carries.

`FIO-004`'s scope is a blocking open question (see `open-questions.md`) and is not committed to by this proposal until it is answered.

## Why

`C-005` already states the defect: `RobotData.getDataFile` uses `java.nio.file.Path#resolve`, which returns an absolute argument unchanged rather than re-rooting it — the opposite of classic's silent redirection. A rumble bot that writes to a root-level path (`AN-012`'s note on Windows permission-failure floods) produces thousands of access-denied errors under the bridge where classic produced a normal write inside the robot's directory. `IDR-002` aligned `getDataDirectory` and `getDataFile` to the same lookup but explicitly left confinement undone; `FIO-002`'s `@draft` status is the record of that gap, and this change is what closes it.

The quota (`FIO-003`) is documented in three Javadoc comments (`RobocodeFileOutputStream`, `RobocodeFileWriter`, `IAdvancedRobotPeer#getDataFile`) and enforced nowhere; a robot that reads its own Javadoc and expects a `SYSTEM:`-prefixed quota message and an `IOException` at 200000 bytes currently gets neither.

## Route

Full. This closes acceptance criteria that are currently `@draft` (`FIO-001`, `FIO-002`, `FIO-003`) and promotes `C-005` from `enforcement: agent` to machine-enforced — both are contract changes, not refactors of unchanged behaviour.
