package network.something.somepotter.util;

import iskallia.vault.util.calc.AbilityPowerHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.ModList;
import network.something.somepotter.SomePotter;

public class AbilityPowerUtil {

    protected static int clientSideAbilityPower = 0;

    public static void setClientSideAbilityPower(int abilityPower) {
        clientSideAbilityPower = abilityPower;
    }


    public static int get(ServerPlayer player) {
        if (player.level.isClientSide) {
            return clientSideAbilityPower;
        }

        if (ModList.get().isLoaded("the_vault")) {
            SomePotter.LOGGER.info("Mod is loaded");
            var result = AbilityPowerHelper.getAbilityPower(player);
            SomePotter.LOGGER.info("Ability Power: {}", result);
            return (int) result;
        }

        SomePotter.LOGGER.info("Mod is not loaded");
        return 600;
    }

    public static int get(LivingEntity entity) {
        if (entity.level.isClientSide) {
            return clientSideAbilityPower;
        }

        if (entity instanceof ServerPlayer player) {
            SomePotter.LOGGER.info("Entity is player");
            return get(player);
        }
        return 0;
    }

    public static float scale(int abilityPower, float minValue, float maxValue) {
        return minValue + ((maxValue - minValue) * getMultiplier(abilityPower));
    }

    public static int scale(int abilityPower, int minValue, int maxValue) {
        return Math.round(scale(abilityPower, (float) minValue, (float) maxValue));
    }

    public static float getMultiplier(int abilityPower) {
        return abilityPower / (float) getPeakAbilityPower();
    }

    public static int getPeakAbilityPower() {
        return 500;
    }

}
