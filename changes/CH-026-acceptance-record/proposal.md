---
id: CH-026
type: change
status: open
links: [P-002, M-149, M-150, G-003]
title: Plan a bridge that runs stand-alone and from the Tank Royale GUI
---

# Plan stand-alone and GUI use of the bridge

Add a milestone to the distribution plan, scheduled after preview readiness, for making the bridge usable from the Tank Royale GUI. The maintainer directed that the bridge must keep a documented stand-alone route, the preview ZIP and the `wrap-robots` command, and must also be installable and usable from the GUI. This change records that future work. It does not start it, and it does not change the current preview scope or M-149's exit criterion.

The milestone keeps the stand-alone package as the unit the GUI consumes rather than introducing a second, GUI-only distribution. Most of the GUI side would live in the Tank Royale repository. The bridge's part is a stable package and a wrapper interface that a program can drive and interpret, with the stand-alone route documented and tested as before.

## Challenge

The assumption most likely to fail is that one package and one command can serve both a person at a terminal and a GUI installer without either becoming awkward. The credible alternative is a GUI-specific bridge distribution or embedding the wrapper as a library inside the GUI. The cheapest useful test, when the milestone starts, is to drive the existing preview ZIP and `wrap-robots` from a small external program, as a GUI would, and see whether its output, exit codes and failure reports can be acted on without parsing human prose. If that fails, the milestone needs a machine-readable interface or a revised approach before any GUI work. An integration could meet every criterion and still fail users if the GUI install hides JDK and version problems that the stand-alone guide explains. The exit criterion therefore requires both routes to report those failures usefully.

The repository has no vision artifact. This change proceeds under G-003, making legacy robots easy to obtain, wrap and run, and does not touch G-001 fidelity work.
