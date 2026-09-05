## Resolved

### Does `FIO-004` cover raw `java.io` access, or only access reached through `getDataFile`/`getDataDirectory`?

Decided: the resolver-surface reading. See `IDR-007`. `FileOutputStreamAttack` ports as `FIO-004` evidence; `FileAttack` (raw `java.io`, no `getDataFile` call) is not ported under `FIO-004`.
