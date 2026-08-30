# Capabilities

CAP-xxx: what the system can do — one folder per capability, and the anchor of the requirements thread.

Each capability folder holds three files, updated together in the same change that alters the capability's behavior:

- `README.md` — what the capability is and why it exists (links its goal).
- `criteria.md` — acceptance criteria as Gherkin scenarios, each tagged `@AC-xxx`. A new or revised machine-proven AC declares `Test-type: Unit`, `Integration`, `E2E`, or `Performance` and has supported Go, JVM, or Cucumber evidence classified by that type and positive/negative direction, unless it records `(single-direction)`; JVM evidence carries its identity, type, and direction on the same supported Java or Kotlin executable. `Test-type: Human` uses the pull request acceptance brief as proof and needs no code reference; `@draft` exempts one genuinely not-yet-proven criterion; an unannotated legacy AC retains one supported reference. `clue validate` enforces declarations and references but does not execute tests. When a requirement changes what an AC *means*, the AC is retired (`@AC-xxx @retired` tombstone) and a new ID is minted — IDs are never redefined.
- `design.md` — how the capability works: the design is documented **per capability**, close to the criteria it realizes, not in one distant document.

A whole capability whose extracted criteria are not ready may stay `status: draft` with the gap stated, but a single unfinished criterion uses `@draft` without deactivating proven siblings — honesty at the narrowest level over artificial green.

<!-- clue:index:start -->
- [CAP-001 — Event dispatch and timing parity](CAP-001-event-dispatch-parity/README.md) · `draft` — Events reach the robot in the order, at the moments, and with the interruptibility classic gives them.
- [CAP-002 — Robot physics and state parity](CAP-002-physics-and-state-parity/README.md) · `draft` — Movement, turn rates, and gun heat behave as classic's arithmetic dictates.
- [CAP-003 — Robocode API surface fidelity](CAP-003-robocode-api-surface-fidelity/README.md) · `active` — The adapter's value conversions are correct; the only capability with machine evidence.
- [CAP-004 — Robot file I/O sandboxing](CAP-004-robot-file-io-sandboxing/README.md) · `draft` — Robot writes are confined to the robot's data directory, as classic confines them. Not yet implemented.
- [CAP-005 — Score parity across the rumble collections](CAP-005-score-parity/README.md) · `draft` — The end-to-end claim, and the only thing that exercises the parts of the surface nobody modelled.
- [CAP-006 — Team robot support](CAP-006-team-robot-support/README.md) · `draft` — Teams, teammate messaging, and droids. Absent from the wrapper entirely.
- [CAP-007 — The compatibility harness](CAP-007-compatibility-harness/README.md) · `draft` — The instrument itself, which has been wrong in ways mistaken for the bridge being wrong.
- [CAP-008-call-routing-fidelity/](CAP-008-call-routing-fidelity/README.md)
<!-- clue:index:end -->
