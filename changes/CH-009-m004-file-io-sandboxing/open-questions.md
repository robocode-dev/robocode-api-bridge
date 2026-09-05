## Blocking

### Does `FIO-004` cover raw `java.io` access, or only access reached through `getDataFile`/`getDataDirectory`?

`CAP-004`'s criteria name the ported classic test robots `FileAttack` and `FileOutputStreamAttack` as `FIO-004`'s evidence plan. Reading them in `C:/Code/robocode/robocode.tests.robots/src/main/java/tested/robots/`:

- `FileOutputStreamAttack` calls `getDataFile("test")`, then opens a plain `java.io.FileOutputStream` on the returned `File`. This is exactly the path this change's resolver covers: `getDataFile` re-roots the name, and whatever opens the resulting `File` sees an already-confined path. Porting this robot needs no scope decision.
- `FileAttack` never calls `getDataFile` at all. It opens `new FileInputStream("C:\\MSDOS.SYS")` and `new FileOutputStream("C:\\Robocode.attack")` directly. In classic, this is blocked by the JVM `SecurityManager`'s `checkRead`/`checkWrite` — a mechanism this repository's own architecture record says is out of scope: `docs/architecture/README.md` lists "threads, reflection, and sockets" as sandbox gaps "scoped out rather than forgotten," and `AGENTS.md` records that JDK 24 removed `SecurityManager` outright, which is why classic needs its own older JDK to run at all. A resolver inside `getDataFile` cannot see or redirect a call that never goes through it.

Two ways to resolve this, neither of which this change should choose on its own:

1. **Narrow `FIO-004` to the `getDataFile`-reached surface.** Port `FileOutputStreamAttack` as `FIO-004`'s evidence; drop `FileAttack` or retarget it at a different criterion (or a documented, deliberately out-of-scope gap alongside threads/reflection/sockets). This keeps `CAP-004` inside the boundary the architecture record already drew.
2. **Widen scope to intercept raw `java.io` calls from robot code.** This would need a mechanism the bridge does not have today — a security-manager successor (Java's replacement APIs), a custom `FileSystemProvider`, or bytecode instrumentation of robot jars at wrap time — and is a materially larger and separately-plannable piece of work, arguably its own capability rather than a corner of `CAP-004`.

This change proceeds on option 1's assumption (resolver-surface confinement only, `FileAttack` excluded or retargeted) unless directed otherwise, because option 2 is a different-shaped change that this plan door's own scope note (`docs/architecture/README.md`'s "scoped out rather than forgotten") argues against undertaking implicitly. Flagging here rather than deciding silently, per the scope-change rule in `AGENTS.md`.
