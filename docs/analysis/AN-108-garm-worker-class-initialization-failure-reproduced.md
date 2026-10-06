---
id: AN-108
type: analysis
status: active
links: [P-001, CAP-005, CAP-007]
title: Garm's Tank Royale worker failure recurs, but its originating error is unavailable
provenance: inferred
reversal-cost: low
---

# AN-108 — Garm's Tank Royale worker failure recurs, but its originating error is unavailable

## Risk investigated

Whether Krabb.sliNk.Garm's historical Tank Royale worker failure recurs with current matched artifacts and whether the available exception identifies its owner.

## Evidence boundary

The read-only subject jar `Krabb.sliNk.Garm_0.9u.jar` has SHA-256 `c895d29c6c2b83c9441a643deca71f43405cb9907a2d4393832a84305967fe2d`. The current official observation `2e935b12cefc0443` completed on 2026-10-06 with Classic Robocode 1.11.1, bridge commit `93ef077d8b13635f8bcd5693d8b9770fba5a31e9`, and local Tank Royale commit `ac4c4cc8e1ebe5be25e6527addfbdbfe2de557b9`. It used 2 participants, 35 rounds, an 800×600 arena, and the current matched local Bot API and runner artifacts recorded in the registry. The Tank Royale process used the default Temurin JDK 25.0.1. The jar remained read-only.

## What was tried

The current official pair scored 4,820 in Classic with zero errors; the Tank Royale worker produced no result and reported four `NoClassDefFoundError` messages, leaving the current registry status `DISCREPANCY (outcome)`. Its stderr says `Could not initialize class java.lang.StackTraceElement$HashedModules` and then reports failure in the uncaught-exception handler. The associated bot stdout ends during the battle, but no underlying initializer exception or stack trace was captured.

The historical observation `a51bebcb193de7f9` also completed Classic successfully and produced no Tank Royale score with the same `StackTraceElement$HashedModules` signature. It used bridge commit `07f66a1a2edceb2ac5f9350254c039775e045ebd`, Tank Royale commit `875f517f7d3a7a49b458c2164088d44058496f55`, Bot API 1.2.0, and the older examples runner. That observation does not record the Java runtime version, so it cannot establish whether the current JDK is related to the failure.

## Finding

The Tank Royale worker failure is reproduced under current matched artifacts, so the historical no-result is not an obsolete-artifact-only observation. The recorded exception names a `java.lang` class, but the worker log omits the original initialization failure; this evidence cannot assign the cause to the bridge, robot, or runtime. Keep the outcome discrepancy open and do not record a diagnosis yet. No code or rumble jar change is indicated.

## M-006 handoff

Continue with the next unresolved registry subject, `roborumble/ap.Frederick_1.1.jar`.
