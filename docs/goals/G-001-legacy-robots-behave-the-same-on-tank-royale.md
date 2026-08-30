---
id: G-001
type: goal
status: accepted
links: []
title: Legacy Robocode robots run unmodified on Tank Royale and behave as they do on classic Robocode
provenance: verified
reversal-cost: high
---

# G-001 — Legacy Robocode robots run unmodified on Tank Royale and behave as they do on classic Robocode

## Who wants it

The people who wrote the robots, and the people who still compete with them. Two decades of Robocode robots exist as compiled jars in the rumble collections, most of them abandoned by their authors, none of them recompilable in practice — the sources are often gone, and the ones that survive were written against a JDK that no longer ships.

Behind them, the Robocode project itself. Tank Royale is the successor engine; classic Robocode is the corpus of everything anyone ever built for Robocode. A successor that cannot run its predecessor's work starts from an empty field.

## What they want

To point Tank Royale at an existing robot jar and have it play the game it was written for. Not approximately, and not after being ported: the same robot, the same jar, the same behaviour.

## Why it matters

The value of a robot is entirely relative. A robot is good because it beats other robots, and its ranking means something because thousands of battles produced it. Move that robot to an engine where it turns a fraction slower, or misses every second scan, and the robot has not been preserved — a different robot with the same name has been created, and the ranking it carried is now a lie about it.

This makes behavioural fidelity the whole of the goal rather than a quality attribute of it. A bridge that runs every robot without crashing and gets the physics subtly wrong has failed at the thing it exists to do, while failing invisibly: the battles complete, the scores look plausible, and only a comparison against the original engine shows the difference.

That is why this repository's promises are stated as parity against classic Robocode rather than as correctness in the abstract. Classic is the specification. Where the two engines disagree, classic is right by definition, because classic is what the robots were written against.

## What this does not commit to

Preserving classic Robocode's implementation. The bridge reproduces observable behaviour and the API surface robots compiled against; it does not reproduce internals, and it is free to reach the same behaviour by different means.

Running robots the classic engine could not run. A robot that crashed under classic is permitted to crash under the bridge, and a robot that classic sandboxed is expected to be sandboxed here too. Parity cuts both ways.
