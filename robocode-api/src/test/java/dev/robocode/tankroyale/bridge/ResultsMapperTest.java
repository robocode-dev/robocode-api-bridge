package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BotResults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import robocode.BattleResults;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Acceptance evidence for API-003 — battle results map to Robocode's {@link BattleResults}
 * with classic field semantics.
 * <p>
 * This mapper is a long positional constructor call, which is the shape where two adjacent
 * fields of the same type can be swapped and nothing complains. Every field therefore gets a
 * distinct value: a swap between ram damage and its bonus would be invisible against
 * placeholder data and is a one-line assertion failure against this.
 */
class ResultsMapperTest {

    /** Distinct per field, so a positional swap cannot hide. */
    private static BotResults results() {
        return new BotResults(
                3,      // rank
                11.0,   // survival
                22.0,   // last survivor bonus
                33.0,   // bullet damage
                44.0,   // bullet kill bonus
                55.0,   // ram damage
                66.0,   // ram kill bonus
                77.0,   // total score
                4,      // firsts
                5,      // seconds
                6       // thirds
        );
    }

    @Test
    @DisplayName("API-003 positive: every score component lands in the field a classic robot reads")
    void testAPI003_UnitPositive_MapsEachScoreComponentToItsClassicField() {
        BattleResults mapped = ResultsMapper.map(results(), "Walls");

        assertEquals("Walls", mapped.getTeamLeaderName());
        assertEquals(3, mapped.getRank());
        assertEquals(77, mapped.getScore(), "total score");
        assertEquals(11, mapped.getSurvival());
        assertEquals(22, mapped.getLastSurvivorBonus());
        assertEquals(33, mapped.getBulletDamage());
        assertEquals(44, mapped.getBulletDamageBonus(), "bullet kill bonus");
        assertEquals(55, mapped.getRamDamage());
        assertEquals(66, mapped.getRamDamageBonus(), "ram kill bonus");
        assertEquals(4, mapped.getFirsts());
        assertEquals(5, mapped.getSeconds());
        assertEquals(6, mapped.getThirds());
    }

    @Test
    @DisplayName("API-003 positive: the supplied name is used rather than anything from the results")
    void testAPI003_UnitPositive_UsesTheSuppliedTeamLeadName() {
        assertEquals("Crazy", ResultsMapper.map(results(), "Crazy").getTeamLeaderName());
    }

    @Test
    @DisplayName("API-003 negative: a participant with no score maps to zeroes, not to absent fields")
    void testAPI003_UnitNegative_MapsAScorelessParticipantToZeroes() {
        BotResults nothing = new BotResults(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        BattleResults mapped = ResultsMapper.map(nothing, "SittingDuck");

        assertEquals(0, mapped.getScore());
        assertEquals(0, mapped.getSurvival());
        assertEquals(0, mapped.getFirsts());
        assertEquals("SittingDuck", mapped.getTeamLeaderName(),
                "a scoreless participant is still a named participant");
    }

    @Test
    @DisplayName("API-003 negative: a fractional score is not silently truncated")
    void testAPI003_UnitNegative_DoesNotTruncateFractionalScores() {
        BotResults fractional = new BotResults(1, 0.9, 0, 0, 0, 0, 0, 99.9, 0, 0, 0);

        BattleResults mapped = ResultsMapper.map(fractional, "Fractional");

        // Tank Royale carries these as doubles and the classic API exposes them as ints, so
        // something has to give. Classic rounds half-up -- `(int) (score + 0.5)` -- and the
        // reproduced BattleResults does the same, which is what makes a robot's reported
        // score agree between the engines. Truncating instead would cost a point on most
        // battles: small, systematic, and in one direction.
        assertEquals(100, mapped.getScore(), "99.9 rounds up, as classic rounds it");
        assertEquals(1, mapped.getSurvival(), "0.9 rounds up rather than vanishing");
    }
}
