---
id: AN-713
type: analysis
status: active
links: [P-001, CAP-005, CAP-007, C-007]
title: PTeam's external member jars are incomplete in the pinned collection
provenance: inferred
reversal-cost: low
---

# AN-713 — PTeam's external member jars are incomplete in the pinned collection

## Risk investigated

Does `teamrumble/Polkwane.PTeam_1.0.jar` fail because the wrapper points external members at the team archive, because the pinned collection lacks their bytecode, or both?

## Evidence boundary

The read-only team jar SHA-256 is `81ae620cf63a24bf4604bc9e7c12977733f9732fcb8ba9937af5b6bb83d126a1`. Its official observation `153efe939f86d788` completed on 2026-10-24 with Classic Robocode 1.11.1, bridge commit `7434e0b623a03379424f56a41e8f1a03247c0144`, local Tank Royale commit `02639872ea90b0d8cc7e487eb89607935d341e9a`, bridge API hash `f0a25c61534ee55b0deb657c7e37b801783bb379de3f3a261efae8aa461da32f`, and wrapper hash `5cd3fe473ef46410d177243507f581ad00cc26fcd16c4a2e8e58fbed7e0ae9a3`. The Tank Royale Bot API and runner hashes were `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752` and `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`; the battle used two teams, ten rounds, and a 1200×1200 arena.

On 2026-10-25, a read-only scan inspected all 44 team jars in the prepared `C:\Code\LiteRumble robots` collection and recursively inspected embedded jars. Across 219 declared member entries, 215 had bytecode embedded in their team archive or a nested jar. This scan is a point-in-time inventory of that local collection, not a claim about other RoboRumble mirrors.

## What was tried

The `.team` descriptor lists `Polkwane.Piyane 0.7b*`, `Polkwane.Intensive 1.0`, `voidious.Diamond 1.8.28`, `simonton.micro.WeeklongObsession 3.4.1`, and `jk.mega.DrussGT 2.9.0`. The team archive contains Piyane's classes and four companion `.properties` files, but no bytecode or embedded jars for the other four members.

The local collection has exact-version bytecode for Diamond 1.8.28 in `meleerumble/voidious.Diamond_1.8.28.jar` (SHA-256 `1ab39d66041eaf4edf5c073d8caa7c61ff5e783961adfad03914f14017d048ea`) and WeeklongObsession 3.4.1 in `roborumble/simonton.micro.WeeklongObsession_3.4.1.jar` (SHA-256 `0afc22545c605389574fa5c473fba60efbcf3e560d4b13f5d71ddb0dc1e5f16c`). `Polkwane.Intensive 1.0` bytecode is absent from the scanned collection. DrussGT 2.9.0 is also absent; the collection contains DrussGT 3.1.7 (SHA-256 `c5dca7d207087ab97ab1d76755cc7e3eb33f90b4ccb1ce64a616d307beaa2baa`), which is a different robot version.

The recorded Tank Royale run failed with `ClassNotFoundException` for all four external classes. The wrapper reads their metadata from the team jar and currently generates their bot launch scripts against that same jar; the compatibility harness stages only the team jar for each engine. Classic completed with 2,417 points and reported repeated Piyane thread-stop warnings, so that score does not establish that the declared five-member team ran.

## What was rejected

Substituting DrussGT 3.1.7 for the declared 2.9.0 member or editing the team jar would measure a different team and violate the rule that rumble jars remain unchanged. Staging the two exact external jars alone could not make this five-member roster complete while Intensive is absent and the requested DrussGT version is unavailable.

## Finding

The failure has two causes: the wrapper does not stage external team-member jars that are present at the exact declared versions, and the pinned collection lacks bytecode for two other declared members. A wrapper improvement could resolve Diamond and WeeklongObsession, but it cannot produce a complete PTeam run from the current collection. The case remains unresolved for the parity campaign; revisit it if the original missing artifacts become available.

## M-006 handoff

Return to the ordered score-review sweep with `roborumble/mld.Wisdom_1.0.jar` in [the parity campaign](../plans/P-001-bridge-parity-campaign.md).
