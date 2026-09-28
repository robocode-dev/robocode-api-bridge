# Make the Cliewen check block merging

`clue init` wrote `.github/workflows/clue.yml`, which runs a check named `validate` on every pull request. A check only reports. Nothing stops a pull request with a red check from being merged until your Git host is told to require it, and that setting lives at the host, not in this repository: no file records it, so `clue validate` can never tell you whether it is on. The `validate` job asks GitHub on every run and says what it found: a notice when the rules GitHub can report to it are in place, and a warning naming what is missing when they are not.

Configure the branch you merge into, usually `main`, so that:

- Branch deletion is blocked.
- Force pushes are blocked.
- Changes arrive only through a pull request.
- Merge commits are the only allowed merge method. Squash and rebase merging are off, so the reviewed commits stay reachable from the branch.
- Review conversations must be resolved before merging.
- The `validate` check must pass, with the branch up to date, before merging. Let the workflow run once first so the host can offer the check by name; GitHub may show it as `validate / validate`.
- Nobody is on the bypass list, including administrators and automation. An agent working with your credentials can bypass anything you can.

Then prove it once: open a throwaway pull request whose `validate` check fails, and confirm the host refuses to merge it. A red check next to a working merge button means the check runs but is not required yet.

On GitHub, `gh ruleset check --default` lists what the default branch enforces. The workflow's token can read rulesets but not classic branch protection or a ruleset's bypass list, so check those two yourself.

If your host or plan cannot enforce these settings, Cliewen still catches mistakes locally and in CI, but the check is evidence rather than a wall.

The full walkthrough, with GitHub's screens and the commands for the throwaway pull request, is at https://cliewen.dev/ci-wall.
