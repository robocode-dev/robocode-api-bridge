# Robocode robots wrapper

The wrapper reads Classic robot jars and generates Tank Royale JSON metadata, boot scripts and Java-source entry points. Team jars produce a grouped entry plus the member directories it references.

For normal use, follow the [installation and first-battle guide](../guide/README.md). The preview's `wrap-robots` command discovers existing robot collections, stages unchanged jars into a separate bot root and installs the packaged runtime libraries. It requires a JDK; Classic is not a runtime prerequisite.

## Developer entry point

The original entry point remains available for callers that manage their own staging directory:

```text
java -jar robots-wrapper-0.3.1.jar <robots-directory>
```

This entry point generates beside the staged jars. Its caller supplies a sibling `lib` directory containing the adapter and its compatible Tank Royale Bot API. The packaged command handles that setup for end users.

## Build

```powershell
.\gradlew.bat :robots-wrapper:build
.\gradlew.bat :robots-wrapper:test
.\gradlew.bat :robots-wrapper:previewDist
```

The combined developer jar appears under `robots-wrapper/build/libs`. The complete preview ZIP and checksum appear under `robots-wrapper/build/distributions`. Packaging requires committed sources and public dependencies; it performs no publishing. The guide covers the release preparation procedure.
