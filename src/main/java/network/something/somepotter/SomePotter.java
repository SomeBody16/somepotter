package network.something.somepotter;

import com.mojang.logging.LogUtils;
import iskallia.vault.event.event.VaultGearEquipmentChangeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.hud.ability_power.NotifyClientAbilityPowerPacket;
import network.something.somepotter.util.IntegrationUtil;
import org.slf4j.Logger;

@Mod(SomePotter.MOD_ID)
public class SomePotter {
    public static final String MOD_ID = "somepotter";

    public static final Logger LOGGER = LogUtils.getLogger();

    public SomePotter() {
        IntegrationUtil.theVault(() -> {
            MinecraftForge.EVENT_BUS.addListener((VaultGearEquipmentChangeEvent.Gear event) -> {
                NotifyClientAbilityPowerPacket.notifyPlayer(event.getPlayer());
            });
            MinecraftForge.EVENT_BUS.addListener((VaultGearEquipmentChangeEvent.Curio event) -> {
                NotifyClientAbilityPowerPacket.notifyPlayer(event.getPlayer());
            });
        });
    }
}
