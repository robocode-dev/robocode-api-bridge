# Tasks

- [x] Check `clue validate --intent` for a stated corpus vision before proposing new goal/plan content
- [x] Allocate `G-003` and draft the goal artifact (`docs/goals/G-003-legacy-robots-are-easy-to-obtain-wrap-and-run.md`), `status: proposed`
- [x] Allocate `P-002` and draft the plan artifact (`docs/plans/P-002-build-distribution-and-bot-acquisition.md`), `status: draft`, linking `G-003`, with four milestones (build/catalog cleanup, artifact distribution, usage guide, bot-acquisition tooling)
- [x] Regenerate `docs/goals/README.md` and `docs/plans/README.md` indexes with `clue scaffold`
- [x] Mark `G-003` and `P-002` live in the identity ledger and confirm `clue validate` passes
- [ ] Record the stray off-ledger milestone-ID numbering convention (`M-144`..`M-147` chosen by continuing P-001's sequence rather than via `clue id next M`, since that prefix has no prior ledger entries despite P-001 already using `M-001`..`M-143` in prose) as an open question for the human to confirm or correct
