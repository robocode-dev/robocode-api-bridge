<!-- The form for a tracked Cliewen change. For direct work, which leaves the accepted contract unchanged, delete the Cliewen-specific sections. -->

## Summary

<!-- What changes, and why is it needed? -->

## Verification

<!-- List the checks relevant to the changed surface and their results. -->

## Cliewen proposal

<!-- Full loop only. If the agent recommended full but the user chose simple, delete this section and retain the three PDR-042 override trailers in Git history. -->

- Change ID: `CH-xxx`
- Plan item served: <!-- P-xxx / M-xxx, or explicitly plan-less -->
- Proposal location: <!-- /changes/CH-xxx-slug/proposal.md -->
- Agentic review mode, reviewed commit, and pass count: <!-- context-isolated or in-context fallback; SHA; number of passes -->
- Outstanding advisory findings: <!-- links or concise descriptions, or none -->
- Hosted head reviewed before this update: <!-- SHA, or new PR -->
- Outstanding actionable findings: <!-- unresolved review-conversation links, or none -->

## Traceability and Decisions

- Acceptance criteria or capability meaning changed: <!-- links, or none -->
- Decision records added or changed: <!-- links, or none -->
- Constraints assessed (including verifiable quality bars): <!-- links and effects, or none -->

## Cliewen checklist

- [ ] This is the initiating author's only initiated Cliewen change; review or update help on an existing PR does not consume another slot.
- [ ] The full proposal was committed before implementation.
- [ ] The plan item or plan-less declaration is truthful, and all artifact links resolve.
- [ ] Consequential decisions are recorded, and active constraints were assessed.
- [ ] Changed active acceptance criteria satisfy the evidence contract, each by one of these routes:
  - classified positive/negative Go, JVM, or Cucumber evidence at the declared machine proof type, with JVM identity/type/direction attached to the same executable, or an explicit `(single-direction)`;
  - Human proof recorded in the proposal's `## Human evidence` section (observer, conditions, observation, result, and what was not observed);
  - per-criterion `@draft` for a genuine gap;
  - the legacy one-supported-reference rule.
- [ ] User-visible impact is described under `[Unreleased]` in `CHANGELOG.md`, or the change has no user-visible impact.
- [ ] Full-change tasks are complete, plan bookkeeping is current, and no transient `/changes/` workspace remains.
- [ ] Generated artifacts were regenerated from their canonical sources where applicable.
- [ ] The current commit received a clean agentic review pass, every blocking repair after an earlier pass triggered a new review, and advisories first reported by the clean pass remain open rather than changing its reviewed commit.
- [ ] Reviews of an existing PR name its hosted head, and actionable findings are unresolved hosted conversations until their reviewed fixes are published.
- [ ] `go build ./...`, coverage-gated `go test ./...`, `go run ./cmd/clue validate --forbid-changes`, and `git diff --check <base> HEAD` pass.

## Review boundary

- [ ] The branch started from the current tip of `main` and does not build on unmerged work, or its committed authorized-dependency record names the base, authorization, and binding meaning.
- [ ] Every intended edit is committed, the worktree is clean, and the reported verification ran against the current commit.
- [ ] The branch was updated without force, and this ready pull request's head branch and SHA equal the locally verified branch and `HEAD`.
- [ ] This pull request existed as a draft from first publication, every working turn that changed anything ended by pushing the branch, and marking it ready was the explicit act binding verification and a clean review pass to the current head.
- [ ] Satisfied review conversations were resolved only after their reviewed fixes reached this hosted head.
- [ ] The full change is ready for human review and merge; no agent will accept its own full change.
