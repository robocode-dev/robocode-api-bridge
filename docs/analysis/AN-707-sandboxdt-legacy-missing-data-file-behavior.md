---
id: AN-707
type: analysis
status: active
links: [P-001, CAP-004, CRIT-004, AN-704, C-007]
title: SandboxDT requires Classic's pre-1.8.2 missing data-file behavior
provenance: inferred
reversal-cost: low
---

# AN-707 — SandboxDT requires Classic's pre-1.8.2 missing data-file behavior

## Risk investigated

Whether `roborumble/pe.SandboxDT_3.02.jar`'s Tank Royale-only data-file error came from the bridge creating a file that Classic leaves absent, and whether the correction can preserve the behavior of newer robots.

## Evidence boundary

The read-only jar SHA-256 is `3635d6856c1a754c82bb24fcc9a2863244766f3dfec34f40a6fff34fee615feb`, and its bundled robot properties declare `robocode.version=1.0.6`. The focused pre-fix observation is `db2510f18997bb21`; the post-fix official observation is `e8496a11fd3f6c86`. Both used Classic Robocode 1.11.1, two participants, 35 rounds, and an 800×600 arena; the post-fix run used bridge commit `ac95ec8ab4f9deea2eac8fa692c54fb0a45e1dc7` and local Tank Royale commit `02639872ea90b0d8cc7e487eb89607935d341e9a`. The post-fix bridge API, wrapper, Bot API, and runner SHA-256 values are pinned in the registry. The exact Classic-side JDK version for these two runs was not recorded. The jar remained read-only.

## What was tried

Before the correction, Classic scored 5,032 and logged two `FileNotFoundException` signatures for `enemystats.dat`; Tank Royale produced no score and logged two `EOFException` signatures after opening an empty file. The failure was caught by SandboxDT's GZIP reader, which reset its stats file.

The Classic source at `9ea397b08fe0e3c9010b96c7f19bd65ef5e84976`, specifically `RobotFileSystemManager.getDataFile`, preserves a missing file for robot versions before 1.8.2.0. For later versions, Classic creates an empty writable file when no packaged data resource exists. SandboxDT's declared version is 1.0.6, while the two DivineBot jars discussed in AN-704 declare 1.8.3.0 and use the later behavior.

The bridge now reads the robot class's `robocode.version` metadata and preserves the earlier missing-file behavior only for versions before 1.8.2.0. Missing or unreadable metadata keeps the existing newer-robot behavior. The official post-fix run scored 4,912 in Classic and 4,774 in Tank Royale, a −2.8% delta; both engines logged the same two `FileNotFoundException` signatures, Tank Royale captured zero skipped turns, and the registry classified the result as `PASS`.

## Finding

The asymmetry was a bridge defect in `RobotData.getDataFile`: it created an empty file for SandboxDT even though Classic 1.11.1 preserves the missing-file result for robots compiled before 1.8.2.0. A version-aware correction restores that behavior without undoing the empty-file behavior required by newer robots. This is a direct implementation repair under the existing `CAP-004` Classic-parity contract; no accepted criterion changed.

Treating missing-file behavior as uniform across all robot versions was rejected because Classic itself changed it, and applying the older rule globally would regress the 1.8.3 DivineBot cases. The inverse, creating empty files for every version, was the behavior that caused SandboxDT's mismatch.

## Implications for M-006

The SandboxDT case now has a repair-linked official `PASS` observation. AN-704 remains a historical finding for the newer DivineBot cases; this analysis records the version boundary that limits its conclusion. No rumble jar was modified.

## M-006 handoff

Confirm the remaining score-only discrepancy for `roborumble/traker.Eraser_1.jar` with five official repeats, then inspect any repeated asymmetric error before considering another bridge change.
