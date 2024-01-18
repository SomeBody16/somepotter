package network.something.somepotter.util;

import iskallia.vault.util.calc.AbilityPowerHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.ModList;

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
            return (int) AbilityPowerHelper.getAbilityPower(player);
        }

        var howCloseTo100 = player.experienceLevel / 100F;
        return Math.round(getPeakAbilityPower() * howCloseTo100);
    }

    public static int get(LivingEntity entity) {
        if (entity.level.isClientSide) {
            return clientSideAbilityPower;
        }

        if (entity instanceof ServerPlayer player) {
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

    public static float scale(int abilityPower, float value) {
        return value * getMultiplier(abilityPower);
    }

    public static int scale(int abilityPower, int value) {
        return Math.round(scale(abilityPower, (float) value));
    }

    public static float getMultiplier(int abilityPower) {
        return abilityPower / (float) getPeakAbilityPower();
    }

    public static int getPeakAbilityPower() {
        return 500;
    }

}
