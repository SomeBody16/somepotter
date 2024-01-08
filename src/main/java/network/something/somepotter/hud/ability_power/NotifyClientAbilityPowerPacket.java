package network.something.somepotter.hud.ability_power;

import ca.lukegrahamlandry.lib.network.ClientSideHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.util.AbilityPowerUtil;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = SomePotter.MOD_ID)
public class NotifyClientAbilityPowerPacket implements ClientSideHandler {

    public int abilityPower;

    NotifyClientAbilityPowerPacket(int abilityPower) {
        this.abilityPower = abilityPower;
    }

    @Override
    public void handle() {
        AbilityPowerUtil.setClientSideAbilityPower(this.abilityPower);
    }

    public static void notifyPlayer(ServerPlayer player) {
        var abilityPower = AbilityPowerUtil.get(player);
        var packet = new NotifyClientAbilityPowerPacket(abilityPower);
        packet.sendToClient(player);
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getEntityLiving() instanceof ServerPlayer player) {
            notifyPlayer(player);
        }
    }
}
