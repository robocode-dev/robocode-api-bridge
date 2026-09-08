---
id: CH-012-verification
type: verification
status: active
links: []
title: Verification
---

# Verification

`./gradlew :robocode-api:test :robots-wrapper:fatJar` passed after the lifecycle changes.

`./gradlew test` passed with the bridge and conformance test projects green.

The Tank Royale conformance runner completed `cs.Nene` for 35 rounds with no errors after peer attachment moved to the `GameStarted` callback.

The Tank Royale conformance runner completed `davidalves.net.DuelistNano` for 35 rounds with no errors after stale callback tick exceptions were contained at the bridge boundary.

The resumable 50-round sweep was restarted from `compat-test/test_progress.json` and remains in progress; its checkpoint is the durable continuation point.

No permanent architecture document changed: the fix preserves the existing bridge boundary and only changes when the generated wrapper attaches the peer and how a terminated callback's stale tick exception is contained.
