# Capabilities

CAP-xxx: what the system can do — one folder per capability, and the anchor of the requirements thread.

Each capability folder holds three files, updated together in the same change that alters the capability's behavior:

- `README.md` — what the capability is and why it exists (links its goal).
- `criteria.md` — acceptance criteria as Gherkin scenarios, each tagged `@AC-xxx`. A new or revised machine-proven AC declares `Test-type: Unit`, `Integration`, `E2E`, or `Performance` and has supported Go, JVM, or Cucumber evidence classified by that type and positive/negative direction, unless it records `(single-direction)`; JVM evidence carries its identity, type, and direction on the same supported Java or Kotlin executable. `Test-type: Human` uses the pull request acceptance brief as proof and needs no code reference; `@draft` exempts one genuinely not-yet-proven criterion; an unannotated legacy AC retains one supported reference. `clue validate` enforces declarations and references but does not execute tests. When a requirement changes what an AC *means*, the AC is retired (`@AC-xxx @retired` tombstone) and a new ID is minted — IDs are never redefined.
- `design.md` — how the capability works: the design is documented **per capability**, close to the criteria it realizes, not in one distant document.

A whole capability whose extracted criteria are not ready may stay `status: draft` with the gap stated, but a single unfinished criterion uses `@draft` without deactivating proven siblings — honesty at the narrowest level over artificial green.

<!-- clue:index:start -->
<!-- clue:index:end -->
