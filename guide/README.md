# Run existing Robocode robots in Tank Royale

The bridge runs compiled Java robot jars written for Classic Robocode on Robocode Tank Royale. The robot calls the Classic API, and the bridge translates those calls to Tank Royale. Keep your existing LiteRumble collection or robot directory: the wrapper reads it and creates a separate Tank Royale bot directory.

Classic Robocode does not need to be installed or running. A Classic installation is one place the wrapper can find existing robots. You do not need Git, Gradle, Maven or the compatibility test harness to use a packaged preview.

## What you need

- A full Java Development Kit (JDK), version 11 or newer. Generated bots start `Wrapper.java` directly as Java source, which needs the compiler supplied by a JDK. A Java Runtime Environment (JRE) alone is insufficient. [Eclipse Temurin](https://adoptium.net/temurin/releases/) is one available JDK distribution.
- The bridge preview ZIP and its accompanying `.zip.sha256` checksum file, supplied by the maintainer or obtained from the [bridge releases page](https://github.com/robocode-dev/robocode-api-bridge/releases). A candidate package may be available from the maintainer before a public preview exists.
- [Robocode Tank Royale](https://github.com/robocode-dev/tank-royale/releases), minimum 1.4.0 for this package. Newer stable 1.x versions are expected compatible; a new major version needs a fresh compatibility assessment. Use the GUI for the first battle. The [1.4.0 release](https://github.com/robocode-dev/tank-royale/releases/tag/v1.4.0) provides a reproducible starting point.
- Existing Java robot jars, such as your LiteRumble collection. This package does not download or redistribute other authors' robots.

This candidate documents Windows installation. Package setup and Java-source launching have been exercised with Oracle JDK 11.0.27 and Eclipse Temurin JDK 25.0.1 on Windows, using the published Tank Royale 1.4.0 runtime for focused battles. The package includes a shell launcher for Linux and macOS, but those platforms need their own installation evidence before preview support is advertised. Compatibility within 1.x is an expectation, not a claim that every future release has been tested.

Open PowerShell and check:

```powershell
java -version
java --list-modules
```

The version must be at least 11, and the module list must contain `jdk.compiler`. If the commands are missing or select an older Java, install a JDK and set `JAVA_HOME` to its installation directory, or add its `bin` directory to `PATH`. The launchers prefer `JAVA_HOME`. Restart Tank Royale after changing these environment variables so its bot processes inherit the same Java selection.

## Unpack and identify the bridge

Use a directory you can write to. Keep the extracted bridge package separate from your robot collection and generated bots. In these examples, `<version>` means the version in the ZIP filename; replace it with that actual value.

```powershell
Get-FileHash -Algorithm SHA256 '.\robocode-api-bridge-<version>.zip'
Get-Content '.\robocode-api-bridge-<version>.zip.sha256'
Expand-Archive -LiteralPath '.\robocode-api-bridge-<version>.zip' -DestinationPath 'C:\RobocodeBridge'
Set-Location 'C:\RobocodeBridge\robocode-api-bridge-<version>'
.\bin\wrap-robots.cmd --version
.\bin\wrap-robots.cmd --help
```

Compare the hash with the supplied checksum before unpacking. `--version` prints the bridge version, exact source revision and Tank Royale runtime requirements. `preview.properties` identifies the packaged libraries and their hashes; `SHA256SUMS` lists hashes for the unpacked payload. Keep the whole extracted directory together, including `tools`, `runtime`, `licenses` and `guide`.

## Find and wrap your robots

If you know the collection path, supply it directly. A path containing spaces must be quoted:

```powershell
.\bin\wrap-robots.cmd 'C:\My LiteRumble robots'
```

The command searches jars in that directory and its subdirectories, including `roborumble`, `meleerumble` and `teamrumble`. It skips library, data and previously generated bot directories. Byte-identical jars in more than one division are imported once.

You can instead supply a Classic installation:

```powershell
.\bin\wrap-robots.cmd 'C:\robocode'
```

The command reads `BOTSREP` from the installation's rumble configuration files. For example, `BOTSREP=./robots/` means the `robots` directory under that installation. A custom absolute `BOTSREP` path is also supported. If there are no configured repositories, the installation's `robots` directory is used.

For discovery, run the command from an interactive terminal without a source argument:

```powershell
.\bin\wrap-robots.cmd
```

It reuses a remembered source or looks at the current directory, `%SystemDrive%\robocode` on Windows and `~/robocode`. With several candidates it asks you to select one; with none it asks for the robot directory or Classic installation. A separate LiteRumble collection may not appear in Classic's configuration, so supply its path when necessary. The command does not search the entire disk. Scripts without an interactive terminal must supply a source when discovery is ambiguous.

By default, generated bots live under `RobocodeBridge\bots` in your user home directory. To choose another location:

```powershell
.\bin\wrap-robots.cmd 'C:\My LiteRumble robots' --output 'D:\Tank Royale bots'
```

Sources and output must be separate, non-overlapping directories. The output must also be outside the extracted package. Source jars remain unchanged. Each imported jar is copied into the output, and the command installs its own runtime libraries automatically. Learned data belongs to each generated bot's `.data` directory. Data embedded in a robot jar is seeded when absent; existing external Classic learned-data files are not automatically migrated.

The final summary reports imported, reused and failed jars. A failure produces a nonzero exit code and names the failing jar. A conflicted or incomplete team is not a successful import. Keep the error when reporting a problem; another jar's success does not clear it.

## First battle in Tank Royale

1. Start the Tank Royale GUI using the [upstream installation instructions](https://robocode.dev/articles/installing-robocode.html).
2. Open **Config > Bot Root Directories** and add the exact bot root printed by `wrap-robots`. Add the generated output directory, not your original collection or the bridge package. Generated bot directories are immediate children of this root.
3. Create a battle, select two imported solo robots, boot them and start the battle. Confirm that both join and act. Start with a small battle before trying a large collection.
4. For a team battle, select complete team entries. A team entry refers to its generated member directories, including independent copies when a member appears repeatedly. Selecting individual members as solo bots does not create a team.

Leave the generated member directories, JSON configuration, scripts, copied jars and `lib` directory together. Renaming or moving only part of this tree can break classpaths and team references.

## Upgrade and repeat an import

Unpack a newer bridge package into a new package directory and run its `wrap-robots` against the same bot output. The command remembers the source for that output. Reimport updates managed runtime files and preserves existing learned data and unrelated files. Do not delete the output's `.bridge-state.properties`: it records ownership and the remembered source. Back up the entire output before moving or manually editing it.

If the source moved, supply its new location explicitly. To move the output, copy the entire bot root, select it with `--output` and update Tank Royale's configured root. Removing a jar from the source does not automatically delete an already imported bot. Use a fresh output root when you want a newly selected collection.

The command refuses an existing unmanaged bot directory or an unmanaged conflicting bridge library. Use a separate output root to keep an existing setup intact. Two different jars declaring the same robot identity are reported as a conflict; choose the intended source collection rather than merging those robots under one name.

## Troubleshooting and reports

| Symptom | What to check |
|---|---|
| JDK requirement error, or `java` is not found | Check `JAVA_HOME`, `PATH`, `java -version` and `jdk.compiler` in `java --list-modules`. Use a full JDK 11 or newer and restart the terminal and Tank Royale after changing the environment. |
| Missing or changed packaged library | Unpack the complete ZIP again. Keep `runtime`, `tools` and `preview.properties` together; no Maven download is needed during normal use. |
| No source found or a remembered source disappeared | Supply the current collection path or Classic installation explicitly. Check custom `BOTSREP` settings. |
| Access denied, overlapping directories or a redirected destination | Choose a writable, separate output outside the collection and package. Symbolic-link or redirected output destinations are refused. |
| No robot metadata, a class missing or a team member missing | Check that you have the original complete robot or team jar. Supply the reported jar and error with your issue; do not alter the jar to suppress the failure. |
| An imported bot is missing from Tank Royale | Add the exact output root printed by the command. Check that the import summary recorded success and refresh Tank Royale's bot selection. |
| A bot fails to launch or remains idle | Check the selected Java and Tank Royale versions, the bot console and booter/server logs. Use the documented minimum runtime or a newer compatible stable version. |
| Output is being updated by another process | Wait for the other wrapper process to finish. An OS file lock prevents concurrent updates; a leftover lock filename alone is harmless. |

Capture wrapper output in PowerShell:

```powershell
.\bin\wrap-robots.cmd 'C:\My LiteRumble robots' 2>&1 | Tee-Object -FilePath '.\wrap-robots.log'
```

For a [bridge issue](https://github.com/robocode-dev/robocode-api-bridge/issues), include `wrap-robots --version`, `java -version`, the Tank Royale version, your OS, the exact command and import summary, and the relevant bot console and booter/server messages. Identify the original robot jar and version; include its SHA-256 hash when possible. Redact secrets and private paths before sharing logs. You do not need to install Classic or run the development harness to report a failure.

## Compatibility limits

A completed battle does not establish identical Classic behavior or scores. Compatibility is still being improved. Tank Royale hides opponents' real identities; a robot that depends on an opponent's real class, package or version can behave differently even when anonymous tracking names work correctly. Teammate identities and messaging are supported separately.

Focused Windows checks with the public Tank Royale 1.4.0 runner booted every member of two five-member MyFirstTeam entries, but some completed runs returned zero-valued team results and rank zero. Other runs returned ranked results. The cause remains under investigation; do not use those zero rows as a compatibility or ranking verdict. The full preview-readiness assessment remains open.

The bridge implements the Classic file APIs reached through `getDataFile` and `getDataDirectory`. It does not reproduce the entire Classic sandbox for raw Java file access, threads, reflection or sockets. Run jars you trust. Legacy internal-JDK dependencies and .NET robots from the old Classic plugin are outside the supported Java-jar path.

## Building and preparing a release (maintainers)

End users can use the ZIP directly. Maintainers need Git, a JDK suitable for the checked-in Gradle wrapper, and the build's Java 11 toolchain. From a clean committed checkout:

```powershell
.\gradlew.bat :robocode-api:test :robots-wrapper:test
.\gradlew.bat :robots-wrapper:previewDist
```

The ZIP and sibling `.zip.sha256` appear in `robots-wrapper/build/distributions`. The default preview version includes the source revision; `-PpreviewVersion=<version>` selects an explicit distribution version. The package records the full source commit either way. Local Bot API overrides and uncommitted inputs are refused. Build from public dependencies, and update the embedded-dependency notices when changing the Bot API version.

Exercise the archive and guide, retain the checksums and record the observed Java, Tank Royale and OS combination. The preview-readiness milestone additionally requires a disposable-environment walkthrough and a maintainer or user observation record. Publication requires separate maintainer authorization. Once authorized, upload the already verified ZIP and checksum to the named release rather than silently rebuilding different bytes. `previewDist` prepares files and never creates a release, tag or upload.
