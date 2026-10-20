---
type: evidence-guide
title: Repository-owned acceptance evidence
---

# Repository-owned acceptance evidence

Run `python .clue/evidence/aggregate.py .clue/evidence/producers.json` after adding, changing or deleting a test or build file, then commit `.clue/evidence.yaml` with the tests. `clue validate` reads that snapshot and never runs the exporter.

The producer `junit_producer.py` reads the JUnit tests under `*/src/test/java` and records a reference for each `@Test` method named `test<PREFIX><number>_<Type><Direction>_<Description>`, such as `testFIO003_IntegrationPositive_WritePastQuotaIsRefused`. The name carries the criterion, the proof class (`Unit`, `Integration`, `E2E` or `Performance`) and the direction (`Positive` or `Negative`). A method that looks like a criterion test but breaks the convention, or repeats a subject, fails the export and leaves the previous snapshot intact.
