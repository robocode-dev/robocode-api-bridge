# Plans

P-xxx: the campaign layer — where goals become sequenced, verifiable work.

A plan names the goals it serves and breaks the campaign into milestones (M-xxx) with **exit criteria**: a milestone is done when its criterion is observably met, never when the work "feels" finished. Milestones live inside the plan file, not as separate artifacts. Status bookkeeping (a milestone flipping to `done` with evidence) may ride along in any merge; changing what a plan *means* is itself a change with a decision record behind it. A completed plan (`status: completed`) is immutable history.

Closing the plan is that same bookkeeping: the change completing the last milestone sets it `completed` in its digest, rather than a separate change afterwards. A campaign is over once its last milestone is evidenced, and leaving it `active` makes this index claim work is in flight that is not. Designate a successor plan there when one is decided; not having decided one is no reason to hold the closure open. Because the closed plan is immutable, every milestone's evidence must be in the table before that digest lands.

Every full change proposal names the plan item it serves, or explicitly declares itself plan-less — silence is not an option.

<!-- clue:index:start -->
<!-- clue:index:end -->
