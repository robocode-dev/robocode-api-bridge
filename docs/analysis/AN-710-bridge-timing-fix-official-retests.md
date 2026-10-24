---
id: AN-710
type: analysis
status: active
links: [P-001, CAP-004, C-003, C-004, C-007, AN-015, AN-193, AN-319, AN-465, AN-703]
title: Official retests confirm callback and zero-based timing fixes
provenance: inferred
reversal-cost: low
---

# AN-710 — Official retests confirm callback and zero-based timing fixes

## Risk investigated

Whether the bridge's early bullet-hit callback ordering and Classic zero-based start-time repairs remove their diagnosed discrepancies in official MeleeRumble battles, and whether the remaining errors belong to the bridge or the fixed opponent pool.

## Evidence boundary

The read-only jars were `meleerumble/nic.Nicator_2.4.jar` (SHA-256 `b95c3c76451969efe65a8c874e6bf45afc0bb5d6ed3de50493db9d74103ccc6f`), `meleerumble/pedersen.Grishnakh_1.0.jar` (SHA-256 `8af32bffea553b8e686232871f456c0a968d16401821659302b98addb47c6798`), `meleerumble/pedersen.Moron_2.0.jar` (SHA-256 `45d4ada2bb9b3d55ce8a7502e881cebbd403acaa2cd25507666c1b7a5b9d0d03`), and `meleerumble/pedersen.Ugluk_1.1.1.jar` (SHA-256 `e2d8c096a36a602ff208169debefe4628fe0ebd6767c5c6e06f36e7b6a3e4d26`). Each complete retest used Classic Robocode 1.11.1 and the official MeleeRumble setup: ten participants, 35 rounds, and a 1000×1000 arena. The bridge commit was `8c9f40ec2e6df97b8864f66a47249546928861b5`, Tank Royale commit `02639872ea90b0d8cc7e487eb89607935d341e9a`, Bot API jar SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`, bridge API jar SHA-256 `0ca98a89022b0e08dbcfcf6f28fe23380dbd131ec32df75b5f5d3f732c752c39`, wrapper jar SHA-256 `9caa34b387c91cdede543809ef3dedc157aadb0051e1e094a18715098ee8c640`, and runner jar SHA-256 `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`. The Classic JDK version was not recorded. The jars were run without modification.

## What was tried

Nicator was retested for `bridge-firebullet-callback-ordering` after repair `3b1e21a`; observation `26a73e31f4f61b11` scored 114,741 on Classic and 111,427 on Tank Royale, a −2.9% delta. The 234 Classic errors included `ArrayIndexOutOfBoundsException` from `amk.guns.Aristocles.prepare` and `SecurityException` from `amk.ChumbaMini.saveData`; Tank Royale's 30 errors came from `ChumbaMini.saveData` and the same fixed melee opponent pool.

Grishnakh and Moron were retested for `classic-time-zero-based-start` after repair `4454e7d`; observations `099d59c959aa0dab` and `316e16a1b540ab2b` scored 113,787 versus 111,006 (−2.4%) and 116,108 versus 113,055 (−2.6%), respectively. Their Classic errors identify `Aristocles.prepare` and `ChumbaMini.saveData`; Tank Royale's 30 errors identify `ChumbaMini.saveData`.

Ugluk's first attempt, observation `9f1886b53efe172d`, ended without a Tank Royale score and showed two Tank Royale-only null-pointer signatures in Ugluk's own snapshot and targeting code. The harness also reported incomplete process-tree cleanup. A complete official repeat, observation `9623a9bef744a6ca`, scored 113,931 on Classic and 112,149 on Tank Royale (−1.6%); its 30 Tank Royale errors came from `ChumbaMini.saveData`, and the two Ugluk null-pointer signatures did not recur. Both Ugluk observations remain in the registry to preserve the unstable first result and its successful reproduction check.

## Finding

The complete repair-linked retests contain no Tank Royale-only robot or bridge error signature. Their remaining classified errors come from the pinned melee opponent pool described in AN-015 and AN-703. The four score deltas are all below the harness's 25% flag threshold, but none is a clean pass because asymmetric opponent errors remain. Ugluk's no-score failure was not reproduced in its next official run, so these observations do not justify a new bridge repair; retain it as a transient failure signal for future runs.

## Implications for M-006

Keep all four subjects unresolved as `DISCREPANCY (errors)` until the fixed melee pool is isolated or its errors are repaired. The repair-linked observations verify the callback-ordering and zero-based-time fixes against official robots. Continue with `meleerumble/ak.Fermat_2.0.jar`'s intermittent post-repair startup error: the existing runs in AN-193 and AN-319 leave its trigger unresolved, so capture skipped-turn telemetry on the next official retest before choosing another bridge change. No rumble jar was modified.
