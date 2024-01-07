package network.something.somepotter.wand;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WandListener {

    @SubscribeEvent
    public static void onWandUse(PlayerInteractEvent.RightClickItem event) {
        if (event.getSide().isClient()) return;

        var item = event.getItemStack();
        if (!isItemWand(item)) return;

        var player = (ServerPlayer) event.getPlayer();
        ChosenSpells.castChosenSpell(player);
    }

    public static boolean isPlayerHoldingWand(Player player) {
        var mainHand = player.getMainHandItem();
        var offHand = player.getOffhandItem();
        return isItemWand(mainHand) || isItemWand(offHand);
    }

    public static boolean isItemWand(ItemStack item) {
        return true;
    }
}
