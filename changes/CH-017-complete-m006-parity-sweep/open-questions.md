---
id: OQ-008
type: open-question
status: active
links: [CH-017]
title: CH-017 open questions
---

# Open questions

## OQ-008-1 — Classic team identity reconstruction

`ethdsy.MalackaTeam_1.2` proves that legacy team robots can depend on the classic `getName()` format, including its per-instance ` (n)` suffix. The bridge currently returns the simple Java class name, while the Tank Royale Bot API exposes only an opaque numeric bot ID and teammate IDs after the game starts. Reconstructing the classic name would require a wrapper-to-runtime identity mapping that also governs `getTeammates()`, `isTeammate()`, and directed `sendMessage()`.

Should the bridge introduce and preserve that canonical classic-name mapping for every generated team member, or should this protocol mismatch remain an explicit Tank Royale limitation?
