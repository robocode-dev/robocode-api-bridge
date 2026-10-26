# Third-party components

The bridge is distributed under the Apache License 2.0 in `LICENSE`. Classic API-derived source files retain their original Eclipse Public License 1.0 terms; the license is included in `licenses/classic-EPL-1.0.txt`. The corresponding bridge and API source is included in `sources/bridge-sources.zip`. The exact repository revision is recorded in `preview.properties`.

The wrapper includes Apache BCEL and its Commons Lang and Commons IO dependencies. Their resolved versions, Maven coordinates and SHA-256 hashes are recorded in `preview.properties`. Their original `META-INF/LICENSE` and `META-INF/NOTICE` files are copied into component-specific directories under `licenses`.

Tank Royale Bot API 1.4.0 is Apache-2.0, copyright Flemming N. Larsen and Tank Royale contributors. It incorporates the following Apache-2.0 components. The inventory comes from the [published 1.4.0 build](https://github.com/robocode-dev/tank-royale/blob/v1.4.0/bot-api/java/build.gradle.kts) and its [version catalog](https://github.com/robocode-dev/tank-royale/blob/v1.4.0/gradle/libs.versions.toml), and must be refreshed when that artifact changes.

| Component | Version | Source |
|---|---|---|
| Gson | 2.14.0 | https://github.com/google/gson |
| Gson extras | 3.3.0 | https://github.com/DanySK/gson-extras |
| Error Prone annotations | 2.48.0 | https://github.com/google/error-prone |
| nv-i18n | 1.34.1 | https://github.com/foundationsedge/nv-i18n |

Gson and Gson extras include work copyright Google Inc. Error Prone annotations are copyright The Error Prone Authors. nv-i18n includes work copyright Takahiko Kawasaki and Foundations Edge contributors. Full upstream license texts accompany this file under `licenses`; dependency binaries are not modified by archive assembly. The wrapper's combined jar and Tank Royale's published combined jar contain their bundled classes.
