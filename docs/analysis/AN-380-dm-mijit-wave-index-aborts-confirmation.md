---
id: AN-380
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-004]
title: DM.Mijit's unchecked wave index aborts current score confirmation
provenance: inferred
reversal-cost: low
---

# AN-380 — DM.Mijit's unchecked wave index aborts current score confirmation

## Risk investigated

Whether `DM.Mijit_.3.jar`'s confirmed lower Tank Royale score persists under newer matched artifacts, and whether the current failed attempt identifies a bridge defect.

## Evidence boundary

The population is this single M-006 registry row, `roborumble/DM.Mijit_.3.jar`, whose prior status was `DISCREPANCY (errors)`. The read-only subject jar has SHA-256 `7340c3a1b751cc33f2323f13cfb1e5c499a25e23d682e7aa77f5b99538146f9d`; the run manifest records the selected opponent jar and hash. The partial confirmation `8c54544f3807ac30` completed on 2026-10-09 with Classic Robocode 1.11.1, bridge commit `8812b71bd7edb44a5a30d39281c201bc4d051282`, and local Tank Royale commit `8bb5ba1f0bbc11cf007150ac0f4f966238d7a734`. The locally built bridge, wrapper, Bot API 1.4.0, and runner jars are identified by hashes in `compat-test/parity-registry.json`. It ran in a prepared Windows PowerShell environment with two participants, 35 rounds, and an 800×600 arena; it does not demonstrate clean-checkout reproducibility. The run record does not preserve the exact Java executable selected for Classic.

The confirmation requested five attempts; it reached two attempts and produced one score sample before the second attempt stopped. No completed sample was discarded. This is a partial measurement of one subject and setup, not a five-pair estimate; no confidence interval or significance test applies.

## What was tried

The one completed pair scored 6,354 in Classic and 3,493 in Tank Royale, a −45.0% delta. The next attempt stopped on `ArrayIndexOutOfBoundsException` at `DM.GFTWave.setSegmentations`. The registry lists this under `bridge_only_signatures`, but the origin is the subject's `DM.GFTWave` class; the label alone does not establish bridge ownership. No skipped-turn events were captured.

The preceding observation `bc9081091ba6807a` on 2026-10-06 had three samples across four attempts, a −38.83% mean score delta, and the same `ArrayIndexOutOfBoundsException` origin. The registry diagnosis `robot-unchecked-wave-distance-index` assigns ownership to the robot. An earlier score-only observation `ed69371a4fe64176` had a −41.5% delta without errors.

## What was not pursued

The error was not treated as a bridge defect based only on its registry classification field; the recorded origin and existing diagnosis identify a robot-owned index failure. One completed pair was not treated as a new five-pair score confirmation, and no bridge or rumble-jar change was made.

## Finding

DM.Mijit's lower Tank Royale score remains large in the one completed pair, but current score confirmation is incomplete because the subject's unchecked wave index failed again. The existing robot-owned diagnosis is consistent with the observed origin; this run does not establish a bridge defect or provide a complete current score estimate.

## M-006 handoff

Continue in registry order with `roborumble/EBBU.Sim2_1.02.jar` (`DISCREPANCY (score)`).
