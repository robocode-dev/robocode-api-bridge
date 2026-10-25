---
id: IDR-010
type: decision
status: inferred
author: agent
accepted-by: []
links: [CAP-009, P-002, ARCH-001, C-007, C-002]
title: Stage existing collections in a separate managed bot root
---

# Stage existing collections in a separate managed bot root

The packaged Java command reads existing LiteRumble collections or Classic installations and stages unchanged jars into a separate managed bot root. Conventional location discovery and Classic's `BOTSREP` configuration help find a source; a missing or ambiguous result asks the user rather than selecting an arbitrary path. Noninteractive calls supply an unambiguous source. [The runtime topology](../architecture/README.md) places this command before the battle, with no dependency on starting Classic.

Keep generated bot directories directly below one output root because Tank Royale discovers direct children. Identify source jars by content, preserve learned data across package upgrades and track generated runtime files before replacing them. Generating directly into the original collection would mix transformed classes and launcher files into Classic's repository and tie upgrades to the user's measurement inputs, so the packaged command uses a separate root. The developer/harness entry point continues to operate on caller-owned staging directories.

Pin the libraries inside a package for reproducibility. State a minimum Tank Royale version and compatible major separately from the exact versions tested; users can run newer compatible stable releases without replacing the package's libraries themselves. A change to the minimum or compatible major requires refreshed startup and team evidence. The full JDK is an end-user requirement while generated bots use Java source launching.
