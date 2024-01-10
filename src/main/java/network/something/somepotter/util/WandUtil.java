package network.something.somepotter.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import network.something.somepotter.SomePotter;

public class WandUtil {

    public static boolean isPlayerHoldingWand(Player player) {
        var mainHand = player.getMainHandItem();
        var offHand = player.getOffhandItem();
        return isItemWand(mainHand) || isItemWand(offHand);
    }

    public static boolean isItemWand(ItemStack item) {
        var itemId = item.getItem().getRegistryName().toString();
        return SomePotter.CONFIG.get().wandItems.contains(itemId);
    }
}
