## Resolved

### Does `FIO-004` cover raw `java.io` access, or only access reached through `getDataFile`/`getDataDirectory`?

Decided, then corrected once evidence came in. See `IDR-007` for the final finding: neither reading closes `FIO-004` in this change. The proposal originally guessed that `FileOutputStreamAttack` (which calls `getDataFile` before opening a raw stream) was a legitimate case the resolver-surface design would satisfy. Building and running that probe on both engines showed otherwise — classic's own test for it (`TestFileOutputStreamAttack`) expects an `AccessControlException`, because classic's `SecurityManager` blocks the raw stream unconditionally on path, confined or not. `FIO-004` stays `@draft`; `FIO-001`–`FIO-003` close as originally planned.
