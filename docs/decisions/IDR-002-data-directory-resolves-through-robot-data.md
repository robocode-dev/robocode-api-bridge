---
id: IDR-002
type: decision
status: verified
author: agent
accepted-by: [Flemming N. Larsen]
links: [CAP-004, C-005]
title: getDataDirectory resolves through the same robot data lookup getDataFile uses
---

# IDR-002 — getDataDirectory resolves through the same robot data lookup getDataFile uses

## Decision

`getDataDirectory` returns the directory the robot data lookup resolves, which is the directory `getDataFile` resolves a named file against. The two agree by construction rather than by coincidence.

## Context

They had not agreed. A robot could ask for its data directory, ask for a data file by name, and get a file that was not in the directory it had been given. Classic Robocode has no such gap — a robot that lists its directory sees the files it wrote — and a robot relying on that behaves differently under the bridge in a way that produces no error.

The disagreement surfaced through a bot that also exposed the missing sandbox, which is how a resolution bug and a confinement bug arrived looking like one problem.

## Why this way

There is one question — where is this robot's data — and it should have one answer. Two resolution paths for the same fact is a defect waiting to reappear whenever one path changes.

## Consequences

The alignment is unverified. It was made in response to the bot that surfaced it and has never been re-tested against that bot, so this record describes a fix that is believed correct rather than one that is known correct. `FIO-002` is the criterion, `M-004` is where it gets evidence.

Alignment is not confinement, and the distinction matters. Both calls now agree about where the robot's directory is, and neither prevents a robot from writing somewhere else entirely. `C-005` is the constraint that still has nothing holding it, and `CAP-004` is the work.
