# Constraints

C-xxx: rules that bind Cliewen changes — laws, licenses, security policies, organizational mandates, and the **convention register**: every repo rule that would otherwise live only in prose.

External constraints come from outside the project: you do not decide them, you comply with them (the decision *how* to comply is an ADR or PDR in `decisions/`). The register holds the rest: a convention that binds every change but would otherwise live only in prose (an AGENTS.md rule, a README convention) registers here as a constraint artifact, so the rules have an inventory a validator can count instead of prose that drifts silently. Rules the versioned skills carry need no registration — the register starts with exactly the methodology conventions no skill carries; their declaration lives here, mirrored by the generated AGENTS.md.

Each constraint names its `source` (the document, law, or catalog that states the rule) and an `enforcement` class saying who holds it: `machine` (a lint or CI check holds the whole rule, named under **Checked by**), `partial` (a machine holds a stated subset under **Checked by** and a stated **Residual** stays with judgment — the shape most real rules have), `agent` (nothing holds it yet: the constraint states its promotion trigger, and the count of agent-enforced constraints on `clue validate`'s OK line is the visible promotion backlog, silent once it reaches zero), or `human` (no machine can hold it, and the constraint declares its **Residual**: what stays with judgment and what it costs). `clue validate` lints the fields, the vocabulary, and the declarations.

A rule leaves `agent` by gaining a real check or by being declared — never by relabelling, and never by weakening a check to make the count fall. `clue validate` holds rules about the corpus as it stands and only those: it reads a repository state, never a transition, so a rule about what a *change* did belongs to a machine that has a base, such as your CI workflow. Every full change is assessed against the active constraints before its PR; simple work follows repository conventions and checks relevant to its changed surfaces. This index is the register table:

<!-- clue:index:start -->
- [C-001 — Markdown prose is never hard-wrapped](C-001-no-hard-wrapped-markdown.md) · `machine` — One line per paragraph and per list item; wrapping is the reader's IDE concern.
<!-- clue:index:end -->
