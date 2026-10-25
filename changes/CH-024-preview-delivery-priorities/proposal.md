---
id: CH-024-proposal
type: change-proposal
status: open
links: [P-001, P-002, G-001, G-003]
title: Prioritize a usable bridge preview before comprehensive parity closure
---

# A usable bridge preview is the next delivery

The maintainer has directed work toward people installing the bridge and running their existing Java robot jars in Tank Royale. Activate the existing distribution plan and put packaging, a first-battle guide, troubleshooting, and a verified preview installation ahead of repeated broad score confirmations. Keep the Classic fidelity goal and the full parity campaign's exit criterion intact.

This is a plan revision, not a release or implementation of a new installation mechanism. The proposal serves the distribution plan (P-002), especially artifact distribution (M-145) and the usage guide (M-146), and changes the execution priority of the still-open parity campaign (M-006).

## Observed starting point

The repository already has a draft distribution plan and a proposed adoption goal. The wrapper builds a fat jar, while the adapter jar has a runtime Bot API dependency. Generated bot scripts rely on libraries beside the robot directories. The wrapper README describes manual library placement, uses an old example version, and does not take a new user through their first Tank Royale battle. The root README also makes an unqualified claim that a robot cannot tell the difference, which the unresolved parity evidence does not establish.

The existing compatibility registry distinguishes completed runs, score gaps, and errors. Completing a battle does not prove Classic fidelity. Preview communication must preserve that distinction and link to current evidence instead of copying a changing support percentage into permanent prose.

## Challenge to this commitment

The main assumption is that the current wrapper and a public, compatible Tank Royale runtime can support a repeatable installation outside the maintainer's prepared machine. A credible alternative is to continue comprehensive compatibility work before preparing a preview. The cheapest useful test is a disposable installation using only the candidate archive, the documented Java version, a declared Tank Royale runtime, and an unchanged example robot jar. It must require neither a Classic installation nor the maintainer's Maven cache or locally built Tank Royale binaries. If this fails, packaging work repairs or documents the concrete dependency before a preview is offered; another broad score sweep does not resolve that failure.

A package could pass all mechanical checks and still fail its intended user if the first robot cannot be found in the GUI or a failed launch gives no usable explanation. The plan therefore includes a first-time-user walkthrough and an issue-reporting path, with observation conditions recorded before preview publication.

## Scope and acceptance

Revise the two active-work plans, record the delivery policy in a process decision, correct obsolete adoption prose, and remove the README's unsupported compatibility claim. No executable acceptance criterion changes, robot jars, runtime behavior, release versions, or publication workflows are part of this plan-only change. No new use-case artifact is needed for this planning revision; the actual installation flow will be established and verified during the distribution work.

The repository has no stated vision. This change proceeds under the existing Classic fidelity goal (G-001), the proposed adoption goal (G-003), and the maintainer's installation-and-documentation direction. The acceptance mechanism is local on main. No branch publication or hosted pull request is requested for this change.
