package network.something.somepotter.floo.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.floo.network.FlooNetworkManager;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class DisableFlooNetworkScreen {

    protected static List<BlockPos> positions = new ArrayList<>();

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        var player = Minecraft.getInstance().player;
        if (event.phase == TickEvent.Phase.END || player == null) return;
        if (positions.isEmpty()) return;
        positions.removeIf(pos -> FlooNetworkManager.playerDistanceTo(player, pos) > 1.5);
    }

    public static void disableForPosition(BlockPos pos) {
        positions.add(pos);
    }

    public static boolean isDisabled() {
        return !positions.isEmpty();
    }

}
