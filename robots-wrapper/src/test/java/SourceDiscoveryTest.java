import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class SourceDiscoveryTest {
    @TempDir Path temp;

    @Test
    void testPKG002_UnitPositive_ResolvesRelativeAndAbsoluteRumbleRepositories() throws Exception {
        Path home = Files.createDirectories(temp.resolve("Classic home"));
        Path robots = Files.createDirectories(home.resolve("robots"));
        Path separate = Files.createDirectories(temp.resolve("LiteRumble robots"));
        Path settings = Files.createDirectories(home.resolve("roborumble"));
        Files.writeString(settings.resolve("roborumble.txt"), "BOTSREP=./robots/\n");
        Files.writeString(settings.resolve("meleerumble.txt"), "BOTSREP=" + separate.toString().replace('\\', '/') + "\n");
        Files.writeString(settings.resolve("teamrumble.txt"), "BOTSREP=./robots/../robots\n");
        assertEquals(List.of(robots.toRealPath(), separate.toRealPath()), SourceDiscovery.resolve(home));
        assertEquals(List.of(separate.toRealPath()), SourceDiscovery.resolve(separate));
    }

    @Test
    void testPKG002_UnitPositive_DiscoversConventionalHomesAndRemembersSources() throws Exception {
        Path user = Files.createDirectories(temp.resolve("user"));
        Path classic = Files.createDirectories(user.resolve("robocode/robots"));
        assertEquals(List.of(classic.getParent().toRealPath()), SourceDiscovery.candidates(classic.getParent(), user, null));
        Properties remembered = new Properties();
        remembered.setProperty("source.count", "1");
        remembered.setProperty("source.0", classic.toString());
        assertEquals(List.of(classic.toRealPath()), SourceDiscovery.remembered(remembered));
    }

    @Test
    void testPKG002_UnitNegative_DoesNotHideAmbiguousOrMissingSources() throws Exception {
        Path user = Files.createDirectories(temp.resolve("user/robocode/robots"));
        Path other = Files.createDirectories(temp.resolve("other/robots"));
        assertEquals(2, SourceDiscovery.candidates(other.getParent(), user.getParent().getParent(), null).size());
        assertThrows(java.io.IOException.class, () -> SourceDiscovery.resolve(temp.resolve("missing")));
        Path settings = Files.createDirectories(other.getParent().resolve("roborumble"));
        Files.writeString(settings.resolve("roborumble.txt"), "BOTSREP=./gone\n");
        assertThrows(java.io.IOException.class, () -> SourceDiscovery.resolve(other.getParent()));
        Properties invalid = new Properties();
        invalid.setProperty("source.count", "invalid");
        assertThrows(java.io.IOException.class, () -> SourceDiscovery.remembered(invalid));
    }
}
