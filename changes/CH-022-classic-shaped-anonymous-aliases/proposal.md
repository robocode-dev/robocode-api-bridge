---
id: CH-022
type: change
status: open
links: [M-006]
title: Classic-shaped anonymous names for hidden Tank Royale opponents
---

# Classic-shaped anonymous names for hidden Tank Royale opponents

M-006 measures legacy robots against Classic Robocode and repairs bridge or Tank Royale discrepancies that the measurements identify. Tank Royale intentionally omits opponent names from a bot's name map, so the bridge currently puts a bare numeric ID into Classic event names. Several legacy robots parse those names as package, class, and version strings and crash before they can fight.

This change adds a stable, non-identifying Classic-shaped alias for an ID whose real name is unavailable. The form is `tankroyale.anonymous.Bot<id> 1.0`, where `<id>` is the decimal Tank Royale bot ID. Names supplied by Tank Royale remain unchanged. The alias makes no claim about the opponent's real robot class or version and cannot reproduce behavior that depends on recognizing a particular opponent.

## Commitment challenge

The main assumption is that these failures depend on the Classic name shape rather than on the real identity contained in it. `AN-045` showed that Tank Royale withholds opponent names and that at least one legacy robot changes its firing behavior when it does not see the original name; the current registry also records robots that fail while parsing the numeric fallback. A synthetic name can remove parser failures without restoring identity-dependent behavior.

The alternative is to preserve numeric IDs and keep diagnosing each affected robot until Tank Royale exposes an authoritative opponent-name source. That preserves the current privacy boundary but leaves robots that parse Classic names unable to run. The cheapest useful test is to prove the alias format and real-name preservation with unit evidence, then rerun the already diagnosed name-parsing robots in their recorded parity setup.

Revise or stop this approach if those robots still fail on the alias shape, or if the alias causes a new bridge-only failure. Even a passing shape test can meet every criterion and still fail a robot owner: a robot that selects targets or changes tactics by actual opponent name will receive an anonymous alias and may behave differently from Classic. The bridge cannot test or recover that withheld identity, so the registry must retain any resulting behavior discrepancy.

## Scope

- Add a CAP-003 acceptance criterion for preserving available names and supplying a stable Classic-shaped alias when a mapped name is absent.
- Use the alias wherever the bridge converts an unmapped bot ID to a name for the Classic API.
- Retest the name-privacy cases already diagnosed during M-006, update their registry observations, and record whether the alias removes their errors.
- Update the CAP-003 design, record the implementation decision, update `CHANGELOG.md`, and refresh executable evidence.

## Durable documentation

The capability criteria and design will describe the observable name behavior. An implementation decision will explain why an anonymous alias is used while the Bot API withholds opponent identity. No architecture overview change is expected because the adapter boundary and runtime topology stay the same.
