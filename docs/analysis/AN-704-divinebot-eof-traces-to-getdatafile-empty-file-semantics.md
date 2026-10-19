---
id: AN-704
type: analysis
status: active
links: [P-001, CAP-004, CRIT-004, AN-298, AN-454, C-007]
title: DivineBot's Classic EOF errors come from missing getDataFile initialization parity
provenance: inferred
reversal-cost: low
---

# AN-704 — DivineBot's Classic EOF errors come from missing getDataFile initialization parity

## Risk investigated

Why two DivineBot versions repeatedly log `EOFException` from `FileManager.load` on Classic while Tank Royale reports no matching error, and whether the bridge's data-file behavior is responsible.

## Evidence boundary

The read-only RoboRumble jar SHA-256 is `8d127b2400ccfddf39d0666729cc749b95084f1550c76d604f8b1089ca688b8a`; the read-only MeleeRumble jar SHA-256 is `9c0bd9c31537d9e22c18f16a6a29a073dea8d480eee6b5c7303646271b7ea376`. Their latest pre-repair official observations are `d5067ec49924631a` and `c6fcf50c923b6795`, pinned to bridge commit `7a18a7c35bc8c82add4b87e4a9f516b261c158e3`, Tank Royale commit `860aa075445ccf0a257310001aa6d4983053e56e`, and Classic Robocode `1.11.1`. The Classic source repository was at `9ea397b08fe0e3c9010b96c7f19bd65ef5e84976`; inspection used Windows PowerShell and Temurin JDK 25.0.1. The MeleeRumble jar contains no bundled `.data` file for the path DivineBot loads.

## What was tried

The RoboRumble run scored 7,136 on Classic with two `EOFException` errors from `divineomega.FileManager.load`; Tank Royale scored 7,789 with no errors. The MeleeRumble run scored 114,787 on Classic with 159 errors, including the same `FileManager.load` exception, and 112,832 on Tank Royale with 30 errors from the known `amk.ChumbaMini.saveData` opponent-pool issue. The EOF trace recurred in earlier official observations documented by AN-298 and AN-454.

The read-only bytecode shows `FileManager.load` checks `File.exists()` before opening a `FileInputStream` and `ObjectInputStream`; it catches the resulting exception and continues. Classic's `RobotFileSystemManager.getDataFile` creates the writable file with `FileOutputStream` when no bundled resource exists, leaving an empty file for the robot to read. The bridge's `RobotData.getDataFile` only resolved and returned a path, so `exists()` was false on Tank Royale and DivineBot skipped the read that fails on Classic. The Classic source and the two current bridge implementations were inspected without modifying either rumble jar.

## Finding

The asymmetric EOF signature is a bridge behavior gap, not evidence that DivineBot's state file is corrupt. `CAP-004`'s design already says `RobotData.getDataFile` matches Classic's file manager; the missing-file state was an unimplemented detail of that existing behavior. The correction must copy a robot-class `.data` resource when present and create an empty file only when there is no bundled resource, while keeping newly created paths inside the robot's data directory.

The interpretation that the subject jar itself was malformed was rejected: the MeleeRumble jar has no resource at the loaded path, Classic creates an empty writable file for that case, and the Classic EOF is caught by the robot. Creating an empty file unconditionally was also rejected because Classic copies a bundled default before falling back to an empty file.

## Implications for M-006

The RoboRumble and MeleeRumble rows are diagnosed as `classic-getdatafile-initializes-missing-file`, owner `bridge`. Retest both official cases after the correction; the MeleeRumble row may still retain a separate harness-owned opponent-pool diagnosis. No rumble jar is modified.

## M-006 handoff

Use the repair-linked official retest to confirm the Classic and Tank Royale file-load behavior, then continue with the next unregistered MeleeRumble jars.
