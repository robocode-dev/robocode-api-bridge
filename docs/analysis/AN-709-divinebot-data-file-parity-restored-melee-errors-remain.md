---
id: AN-709
type: analysis
status: active
links: [P-001, CAP-004, C-003, C-004, C-007, AN-015, AN-704, AN-707]
title: DivineBot's data-file behavior matches Classic while melee errors remain
provenance: inferred
reversal-cost: low
---

# AN-709 — DivineBot's data-file behavior matches Classic while melee errors remain

## Risk investigated

Whether the version-aware `RobotData.getDataFile` correction preserves Classic's empty-file behavior for `meleerumble/divineomega.DivineBot_1.9.4.5.jar`, and whether its remaining error discrepancy belongs to the subject or to the fixed melee opponent pool.

## Evidence boundary

The read-only jar SHA-256 is `9c0bd9c31537d9e22c18f16a6a29a073dea8d480eee6b5c7303646271b7ea376`; its bundled properties declare `robocode.version=1.8.3.0`. Official retest observation `0f53263ec129e22a` completed on 2026-10-24 at MeleeRumble parameters: ten participants, 35 rounds, and a 1000×1000 arena. It used Classic Robocode 1.11.1, bridge commit `f36c932685537808615d66b4791853ca52de30aa`, local Tank Royale commit `02639872ea90b0d8cc7e487eb89607935d341e9a`, and repair `ac95ec8` for diagnosis `classic-getdatafile-initializes-missing-file`. Artifact hashes are pinned in the registry. The exact Classic-side JDK version was not recorded. The jar remained read-only.

## What was tried

Classic scored 115,372 with 427 errors; Tank Royale scored 111,421 with 31 errors, a −3.4% score delta. Both engines reported `EOFException` from `divineomega.FileManager.load`, so the data-file read now fails the same way on both engines, as Classic does for this robot. This matches the version declared in the jar: 1.8.3.0 is later than Classic's 1.8.2.0 cutoff for creating an empty file when no packaged data resource exists.

Classic also reported `ArrayIndexOutOfBoundsException` from `amk.guns.Aristocles.prepare`; Tank Royale did not. Both sides reported `SecurityException` from `amk.ChumbaMini.saveData`. AN-015 identifies Aristocles and ChumbaMini as members of the fixed melee opponent pool. The resulting status is `DISCREPANCY (errors)` and the observation is linked to the bridge repair.

## Finding

The version-aware file correction preserves the newer-robot behavior required by DivineBot while also restoring the earlier missing-file behavior required by SandboxDT (AN-707). The data-file signature is now common to both engines. The remaining status comes from asymmetric opponent-pool errors, not from a new DivineBot file-I/O mismatch.

Treating the −3.4% score delta as a `PASS` was rejected because the official run still contains asymmetric errors, which remain unresolved under `C-004`. Treating all 427 Classic errors as DivineBot errors was also rejected because the recorded stack origins identify the fixed opponents.

## Implications for M-006

Keep the repair-linked result as `DISCREPANCY (errors)` until the fixed opponent pool is isolated or repaired. It confirms both sides of the version-aware data-file behavior across a pre-1.8.2 robot (SandboxDT) and a 1.8.3 robot (DivineBot). No rumble jar was modified.

## M-006 handoff

Continue with the next bridge-owned watch-list case whose cause can be exercised independently of the fixed melee opponent pool; retain the existing harness diagnosis on this DivineBot observation.
