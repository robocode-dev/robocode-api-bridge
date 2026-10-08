---
id: AN-275
type: analysis
status: active
links: [P-001, CAP-004, CAP-005, CAP-007]
title: GhostShell scores with the latest artifacts, while its robot error discrepancy remains
provenance: inferred
reversal-cost: low
---

# AN-275 — GhostShell scores with the latest artifacts, while its robot error discrepancy remains

## Risk investigated

Whether `cw.megas.GhostShell_GT.jar`'s prior Tank Royale no-score outcome recurs under the latest matched artifacts and whether its current error imbalance identifies a bridge defect.

## Evidence boundary

The read-only subject jar has SHA-256 `f75b19028ba3d181ae70d87a380370cf53e535b49e87f9060069a8591bc5888a`. The official observation `8d7ad09d673c7d73` completed on 2026-10-08 with Classic Robocode 1.11.1, bridge commit `ad1b8b818512d466fdf419caf51c0887e86405d3`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. It used 2 participants, 35 rounds, and an 800×600 arena. The bridge API, wrapper, Bot API, and runner artifacts were the local builds recorded in the registry; the jar remained read-only.

## What was tried

Classic scored 8,285 with 596 recorded errors; Tank Royale scored 7,505 with 1,036 recorded errors, for a −9.4% delta. Classic signatures include `NullPointerException` in `chooseLocation` and `doMovement`, plus `StringIndexOutOfBoundsException` in `patternGun`. Tank Royale signatures include `NullPointerException` in `chooseLocation`, `run`, and unknown-origin frames. The Tank Royale worker completed a score and captured an empty skipped-turn event list. The registry status is `DISCREPANCY (errors)`.

AN-145's prior latest pair had no Tank Royale score and 450 recorded Tank Royale errors; the current no-score outcome does not recur. AN-145's source inspection identified unchecked robot state in `chooseLocation`, `doMovement`, and `patternGun`, and recorded robot ownership for those causes. The current error frames overlap those robot paths, while the error totals remain asymmetric and the registry leaves this row unresolved.

## Finding

GhostShell now scores in both engines, but its runtime-error discrepancy persists with many errors on each side and a higher Tank Royale count. The prior robot-owned unchecked-state findings remain relevant; this observation does not establish a bridge defect or explain the full count difference. No code or rumble-jar change is indicated.

## M-006 handoff

Continue in registry order with `roborumble/cw.megas.Polar_3.2.jar` (`DISCREPANCY (outcome)`).
