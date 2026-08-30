---
id: C-001
type: constraint
status: active
links: []
title: Markdown prose is never hard-wrapped
source: Cliewen methodology (scaffolded by clue init)
enforcement: machine
---

# C-001 — Markdown prose is never hard-wrapped

One line per paragraph and per list item; wrapping is the reader's IDE concern. Line breaks are structural only: headings, lists, tables, code fences.

The generated AGENTS.md declares this rule as its rule 6; a repo that kept its own AGENTS.md holds it through this artifact — the register entry is the rule's authoritative declaration either way.

**Checked by:** `clue validate`'s prose-layout lint — every markdown file under `docs/` and `changes/`, which is the corpus the judge reads; two running-text lines in a row are one paragraph someone broke. Fenced and indented code, tables written with or without outer pipes, frontmatter, blockquotes, HTML blocks, and comments are structure and are read as such.
