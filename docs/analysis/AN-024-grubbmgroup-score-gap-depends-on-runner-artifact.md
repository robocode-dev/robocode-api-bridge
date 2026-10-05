---
id: AN-024
type: analysis
status: active
links: [P-001, CAP-005, CAP-006, CAP-007, AN-002, AN-016, AN-020]
title: GrubbmGroup's score result changes with the Tank Royale runner artifact
provenance: inferred
reversal-cost: low
---

# AN-024 — GrubbmGroup's score result changes with the Tank Royale runner artifact

## Question

Does the earlier `teamrumble/gh.mini.GrubbmGroup_0.4.jar` score gap persist with the wrapper repair and the local runner and Bot API built from the same current Tank Royale revision?

## Evidence boundary

The read-only team jar has SHA-256 `d4c8bd894ef6324d97ac984ff8ece51e4683b53b45ef4a4629155d9ddc2a5721`. The current official confirmation used classic Robocode 1.11.1, bridge commit `8f7b0f50f17d0d5ddc214abb720b41db41bbab2d`, Tank Royale commit `5000c678b7fffc8a6147adb5dc4b42b3c6b4d4bb`, the local 1.4.0 runner (SHA-256 `53f9aae74f00d4380f7f6c100ad9d9a0c4d06d727a73e447ff7d1f88ed4dd2af`), and Bot API (SHA-256 `ab65c4d5cec1808adeb71375adae6d15341ae250def89a6c10fc9da879de0752`). The bridge API and wrapper hashes were `fa3d5909bae372d30f7653d37db7aa53c5fd493bd8710ddaaa22928b602d5ed5` and `1c98c6167c553747d6179b92f1e854f8520fe1a1d92e8c270afab38dfc3e2e52`.

The five pairs used official teamrumble settings: 1200×1200, 10 rounds, and two teams. The harness captured skipped-turn telemetry. The collection jar remained read-only.

## Results

The current matched local runner and Bot API produced deltas of −1.2%, −3.7%, −6.5%, −6.0%, and −4.4%, for a −4.36% mean. Classic averaged 22,467.8 and Tank Royale 21,486.6. All five pairs completed without runtime errors. The registry records `MATCHED (score noise)` in observation `664f2cb7d8684621`.

The earlier five-pair confirmation `6753c7240ebb4c18` averaged +28.82% (classic 22,449.8; Tank Royale 28,917.4). That run used the runner at `runner/examples/lib/robocode-tankroyale-runner.jar`, SHA-256 `4f208f8047d0f0d49d119255fc713b042fb9c34fb1d01c585fcfa8730cb940fc`, with the same Bot API hash. The tracked registry retains both observations; their scores are not pooled because the runner artifacts differ.

Both jars report runner version 1.4.0. Comparing their contents found three differing top-level entries; the bundled server jar differs in 14 entries, including `TurnProcessor`, `CollisionDetector`, and score-tracking classes. Tank Royale commit `5000c678` moves scans until after turn damage and skips robots that died during that turn. These changes can alter what a robot observes during combat and are a plausible source of the score shift, but the independent battle seeds and differing packaged classes do not isolate a single cause.

Skipped-turn telemetry in the current five pairs recorded 4, 5, 6, 6, and 7 events; every event was at round 1, turn 1. The events affected different bot IDs between runs and do not establish a connection to the scores.

## Finding

GrubbmGroup matches the score noise band with the local runner and Bot API from Tank Royale commit `5000c678`, with no asymmetric runtime error. The +28.82% result belongs to a different runner artifact and does not reproduce on this matched pair. The measurements show runner-artifact sensitivity; they do not establish whether the scan-order change or another difference in the packaged runner caused the earlier score advantage. No bridge or robot defect is identified by this batch.

The registry's `nested-team-jar-discovery` diagnosis remains historical evidence for the wrapper repair. The current observation closes this score review for the recorded local artifact pair without deleting either earlier observation.

## M-006 handoff

Use the local runner and Bot API built from Tank Royale commit `5000c678` for the next team score-review cases so their artifact pair matches the current campaign baseline. Keep the published `examples/lib` runner result as a separate artifact-specific observation; do not pool it with local-build measurements.
