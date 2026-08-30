---
id: ARCH-002
type: architecture
status: active
links: [G-001, ARCH-001, CAP-003]
title: The frozen Robocode API surface — robocode.* is reproduced, not designed
provenance: inferred
reversal-cost: high
---

# ARCH-002 — The frozen Robocode API surface

The `robocode-api` module reproduces the classic Robocode public API: `robocode.Robot`, `AdvancedRobot`, `TeamRobot`, `JuniorRobot`, the event classes, `Bullet`, `Condition`, `BattleRules`, the file wrappers, the `robocode.robotinterfaces` hierarchy, and the odd historical corner such as the `gl4java` and `robocodeGL` shims some robots still reference.

None of it is designed. All of it is dictated.

## The rule

**No signature in the reproduced surface may change.** Not a parameter type, not a return type, not a method name, not a class hierarchy, not the package a class lives in, not whether a method is `final`.

The reason is the whole premise of `G-001`. Rumble robots are compiled jars whose authors are largely gone and whose sources are largely lost. They were compiled against classic Robocode's API and they will never be compiled again. The bridge does not get to ask them to adapt; the surface they were built against is a fixed target, and reproducing it exactly is the only way their bytecode links at runtime.

The practical shape of this: the surface is append-only, and only where classic itself appended. Something classic never had should not appear here merely because it would be convenient, because a robot cannot use it and a future reader cannot tell it apart from the parts that must be preserved.

## What is behind the surface, and is free

Everything. The `dev.robocode.tankroyale.bridge` package — the peers, the mappers, the converters — is ordinary code with no external obligation. `BotPeer` may be restructured, mappers may be merged or split, the event translation may be rewritten. `CAP-003` holds the criteria for what those translations must produce, and it is stated in terms of results rather than of structure for exactly this reason.

The boundary between the two is the single most important thing to know before changing this module: `robocode.*` is a contract with the past, and `dev.robocode.tankroyale.bridge.*` is where the work happens.

## The uncomfortable consequence

Classic Robocode's API includes behaviour that is wrong, surprising, or historical accident, and the bridge must reproduce that too. A method whose angle convention is inconsistent with its neighbours is reproduced with its inconsistency, because robots were written against the behaviour rather than against the intent.

`G-001` states this as a principle — classic is right by definition. This is where it costs something, and where the temptation to fix a small thing is strongest.

## Where the surface is genuinely uncertain

Reproduction has so far been driven by what robots turned out to need. Parts of the surface that no tested robot exercises have never been confirmed against classic's behaviour at all, and their correctness is currently an assumption rather than a finding. `CAP-003`'s criteria cover the translations the adapter performs; they do not yet establish that the surface is complete.
