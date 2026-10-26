# Robocode API bridge for Tank Royale

Run existing compiled Classic Robocode Java robots in Robocode Tank Royale. The bridge presents the Classic API to the unchanged robot jar and translates calls and events through Tank Royale's Java Bot API.

Start with the [installation, first battle and troubleshooting guide](guide/README.md). It covers the preview ZIP, the JDK requirement, finding an existing LiteRumble collection or Classic robot directory, generating a separate Tank Royale bot root, teams, upgrades and failure reports. Classic Robocode does not need to be installed or running.

## The two tools

- [Robots wrapper](robots-wrapper/README.md): analyses existing jars and generates Tank Royale bot directories. The packaged `wrap-robots` command finds sources and installs the bundled runtime libraries automatically.
- [API adapter](robocode-api/README.md): runs inside each generated bot process and translates the Classic API to Tank Royale.

Behavioral fidelity is measured against Classic Robocode. Compatibility work is ongoing: completing a battle does not prove identical behavior or scores. Opponent names may be anonymous, and legacy strategies depending on real identities can behave differently. The user guide describes these and the sandbox limitations.

## Development

The [architecture overview](docs/architecture/README.md) maps the runtime and evidence tiers. Follow the repository's agent instructions before changing code. The reproduced `robocode.*` API is a frozen binary contract.

```powershell
.\gradlew.bat :robocode-api:test :robots-wrapper:test
.\gradlew.bat :robots-wrapper:previewDist
```

`previewDist` requires a clean committed checkout, produces a revision-pinned ZIP and checksum under `robots-wrapper/build/distributions`, and never publishes. See the guide's maintainer section for preparing a candidate and its separate publication decision.
