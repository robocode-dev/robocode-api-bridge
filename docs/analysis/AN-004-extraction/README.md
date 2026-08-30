# AN-004 — pinned extraction inputs

The two pinned files [`AN-004`](../AN-004-cliewen-extraction-report.md) was derived from. They live here rather than in the change workspace because the workspace is deleted at the digest and these outlive it: the report's mapping table is rendered from the manifest by `clue report`, and `clue validate` re-renders that region and fails on any difference. Delete the manifest and the report can no longer be checked against anything.

| File | What it holds | Checked by |
|---|---|---|
| `source-manifest.yaml` | One row per criterion: the disposition the extraction resolved, its justification, where in the source it came from, and the plan door that will prove it | `clue parity` |
| `carrier-inventory.yaml` | Every operational carrier the rehearsal found — instructions, workflows, registries, links, diagram assets — mapped to a target or marked blocked with a reason | `clue carriers` |

Both are pinned at source revision `16980d442629e0bd1d3524a65b5407d59aade5fa`. They are historical records: written once by the rehearsal, revised once when the maintainer's answers to the open questions changed what was being built, and not rewritten since.

Neither is edited by hand to make a check pass. When the corpus and the manifest disagree, the disagreement is the finding — `AN-004` records the one place they still do and why it is not suppressed.

<!-- clue:index:start -->
<!-- clue:index:end -->
