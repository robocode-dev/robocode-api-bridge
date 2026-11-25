---
id: AN-715
type: analysis
status: active
links: [AN-714, CAP-006, CAP-009, P-002, M-149]
title: Team zero results are present at round end and zero-only ranks follow the server ranking algorithm
provenance: inferred
reversal-cost: low
---

# Team zero results and rank zero

## Question and evidence boundary

For the preview-readiness milestone, identify whether the intermittent zero-valued team results previously observed in AN-714 originate before or after the public Tank Royale runner converts the game-end event, and whether the rank-zero value has an explanation. The consumer is the M-149 readiness assessment, not a claim of Classic parity or a publication decision.

On Windows, using the prepared generated team directories from the earlier preview smoke and Eclipse Temurin JDK 25.0.1, a disposable Java-source diagnostic subscribed to the public 1.4.0 runner's observer round-end and game-end events. The runner jar had SHA-256 `02f6d1d8e9346a4aeae1b91e7abb278c98ac1f1d30c605f867758566f5f327cf`; the bridge candidate and robot jar hashes are recorded in AN-714. This is a prepared environment with previously generated bot directories, not a clean-install or user-walkthrough result. Each game used one round and two five-member copies of MyFirstTeam. No robot jar was modified.

## Observations

[The captured events](AN-715-preview-team-round-observations.txt) show four runs. Run 2 ended at turn 1451 with zero-valued rows and rank zero for both teams at both round end and game end; its runner log reported all ten bots ready. Runs 3–5 ended at turn 1651 with nonzero survival scores and ranked team rows, also consistently between the two observer events. The first diagnostic run likewise produced zero-valued game-end rows, but its initial round-event logging omitted fields, so it is excluded from the four-run round-event comparison. These opportunistic runs do not establish failure frequency or identify the reason for the zero scores.

The observer already receives the zeros at round end, so the runner's final result conversion cannot explain this observation. The source of the lack of points remains open. Observing all bots ready establishes startup, not that their actions or score contributions were correct. An explanation based on the earlier description-as-name defect is rejected because unchanged generated names produced both outcomes.

## Ranking mechanism and next investigation

The [Tank Royale 1.4.0 rank decorator at revision c8ad3a8](https://github.com/robocode-dev/tank-royale/blob/c8ad3a8d19a843f6258d6f6f9db7f29229963903/server/src/main/kotlin/dev/robocode/tankroyale/server/score/RankDecorator.kt) initializes its previous score and rank to `(0.0, 0)` and carries the previous rank forward for equal scores. Therefore, if every underlying score is exactly zero, the algorithm assigns rank zero to every row. This explains how a zero-only ranking can arise in the server, although the observer's integer scores alone do not prove that the underlying floating-point values were exactly zero. The [results builder at the same revision](https://github.com/robocode-dev/tank-royale/blob/c8ad3a8d19a843f6258d6f6f9db7f29229963903/server/src/main/kotlin/dev/robocode/tankroyale/server/core/ResultsBuilder.kt) supplies observer results from the server's aggregated score view.

The remaining useful experiment compares per-bot score contributions and actions during a zero-valued game with a ranked game under the same configuration. Do not infer a bridge defect, a confirmed upstream cause of the missing scores, or an unsupported-runtime verdict from this comparison. The separate clean Windows walkthrough and the documented team-result limitation remain necessary for M-149.
