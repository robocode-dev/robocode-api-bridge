package dev.robocode.tankroyale.bridge;

import dev.robocode.tankroyale.botapi.BotResults;
import robocode.BattleResults;

final class ResultsMapper {

    public static BattleResults map(BotResults botResults, String teamLeadName) {
        return new BattleResults(
                teamLeadName,
                botResults.getRank(),
                botResults.getTotalScore(),
                botResults.getSurvival(),
                botResults.getLastSurvivorBonus(),
                botResults.getBulletDamage(),
                botResults.getBulletKillBonus(),
                botResults.getRamDamage(),
                botResults.getRamKillBonus(),
                botResults.getFirstPlaces(),
                botResults.getSecondPlaces(),
                botResults.getThirdPlaces()
        );
    }
}
