# Analysis

Findings from spikes, investigations, and extractions — risks and unknowns retired *before* they are built on.

An analysis document (AN-xxx) records what was investigated, what was found, and what it means for plans and changes. Findings are **historical records**: written once at the end of a spike, then never rewritten — a later spike that learns more writes a new document. Plans and proposals cite findings instead of re-arguing them.

An incident where the corpus was green but later evidence contradicted it carries `reality: contradicted` and links every capability or acceptance criterion whose claim failed, in addition to the carriers that failed to prevent the incident. `clue validate --reality-gaps` derives the affected-capability view from those edges; it is not a production telemetry channel.

<!-- clue:index:start -->
- [AN-001 — Single-battle score deltas are uninformative for low-scoring bots](AN-001-score-noise-makes-single-battles-uninformative.md) · `active` — Why the current flagged bots are a reason to look rather than a baseline to measure against.
- [AN-002 — Tank Royale exposes no deterministic random seed](AN-002-tank-royale-exposes-no-deterministic-seed.md) · `active` — Classic can make a battle reproducible and Tank Royale cannot, which rules out the strongest class of conformance test.
- [AN-003 — The harness is Python, which is not a supported evidence carrier](AN-003-python-is-not-a-supported-evidence-carrier.md) · `active` — Why the harness's working behaviour stays draft rather than being badged Human, and the three routes out.
- [AN-005 — Gun and radar turn-remaining were swapped in the robot status](AN-005-gun-and-radar-turn-remaining-were-swapped.md) · `active` — A silent defect in a positional constructor call, found by the first unit test this repository ever ran.
- [AN-006 — A robot's own death still does not reach onDeath under the bridge](AN-006-own-death-still-does-not-reach-on-death.md) · `active` — Whether `EVT-004` holds: a robot's own death reaches its death handler.
<!-- clue:index:end -->
