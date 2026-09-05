---
id: IDR-008
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-006, ARCH-002]
title: Preserve arbitrary classic Serializable team messages through a bridge envelope
---

# IDR-008 — Preserve arbitrary classic Serializable team messages through a bridge envelope

Tank Royale transports team-message payloads as JSON and its Gson serializer cannot reflect into every class a classic robot may put inside a `Serializable` message, including JDK-owned `java.awt` values on a strongly encapsulated JDK. Classic team messaging is Java-serialization-shaped, and the `robocode.*` surface is frozen by `ARCH-002`.

The bridge therefore leaves simple JSON-safe values unchanged and wraps every other `Serializable` message in a public bridge type whose payload is Java-serialized and Base64-encoded. The receiving mapper restores the original object before constructing the classic `MessageEvent`; native Tank Royale team semantics still own delivery and recipient routing.

This preserves the legacy message's type and value across the bridge without requiring JVM module-opening flags or a second message protocol. The envelope adds payload overhead and requires all members of a bridged team to use the bridge, which is already true for a legacy team jar. Name-to-id addressing remains the separate boundary recorded by `ADR-002`.
