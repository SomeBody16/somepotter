package network.something.somepotter.integration.the_vault.listener;

import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.objective.*;
import iskallia.vault.core.vault.player.Completion;
import iskallia.vault.core.vault.stat.StatsCollector;
import iskallia.vault.event.event.VaultLeaveEvent;
import network.something.somepotter.mechanics.spell_point.SpellPointData;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class VaultCompletedListener {

    public static void onVaultLeave(VaultLeaveEvent event) {
        if (!event.getVault().has(Vault.STATS)) return;
        var statCollector = (StatsCollector) event.getVault().get(Vault.STATS);
        var stat = statCollector.get(event.getPlayer().getUUID());
        if (stat.getCompletion() != Completion.COMPLETED) return;

        var objectives = (Objectives) event.getVault().get(Vault.OBJECTIVES);
        var objectiveExperienceMap = getObjectiveExperienceMap();
        var spellExperience = new AtomicInteger(0);

        for (var objectiveClass : objectiveExperienceMap.keySet()) {
            objectives.forEach(objectiveClass, o -> {
                spellExperience.addAndGet(objectiveExperienceMap.get(objectiveClass));
                return false;
            });
        }

        SpellPointData.add(event.getPlayer(), spellExperience.get());
    }

    protected static Map<Class<? extends Objective>, Integer> getObjectiveExperienceMap() {
        return Map.of(
                // Difficulty: Easy
                MonolithObjective.class, 2,
                CakeObjective.class, 2,

                // Difficulty: Medium
                KillBossObjective.class, 3,
                ElixirObjective.class, 3,
                ParadoxObjective.class, 3,

                // Difficulty: Hard
                ScavengerObjective.class, 4,

                // End game
                HeraldObjective.class, 20
        );
    }

}
