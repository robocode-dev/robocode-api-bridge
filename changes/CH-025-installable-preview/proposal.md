---
id: CH-025
type: change
status: open
links: [P-002, M-145, M-146, G-003, G-001]
title: Ship an installable bridge preview and its first-battle guide
---

# Installable bridge preview and first-battle guide

Deliver the accepted package and documentation milestones together. A Java command finds an existing robot collection or Classic installation, stages unchanged jars into a separate Tank Royale bot root, installs the packaged libraries, and preserves learned data when rerun. Windows is the first supported walkthrough. Shell launchers are included for subsequent platform evidence.

The package pins its own Bot API dependency and identifies its source revision. Its documentation states a minimum compatible Tank Royale runtime and the exact versions tested, allowing newer stable versions within the compatible major version. Classic is a source of existing jars, not an end-user runtime prerequisite. The generated Java source launchers require a JDK.

## Challenge

The largest risk is that a package built on the maintainer's machine relies on libraries or runtime state absent from the archive. The credible alternative is retaining the developer-only installation until that gap is resolved. The cheapest useful test unpacks the candidate into a path containing spaces, wraps an unchanged fixture using only its bundled libraries, and completes focused solo and team battles against the published compatible runtime. Missing dependencies or incomplete teams stop readiness. A user can still fail despite correct libraries if the guide does not explain the JDK requirement or which bot root to select; those steps are exercised against the concrete archive.

The repository has no vision artifact. This work proceeds under the existing goals of preserving compiled robots and making them practical to wrap and run. It does not publish, tag, integrate itself, or complete the separate clean-environment preview-readiness milestone.
