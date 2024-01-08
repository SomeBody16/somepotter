package network.something.somepotter.hud.ability_power;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import network.something.somepotter.SomePotter;
import network.something.somepotter.util.AbilityPowerUtil;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AbilityPowerHud {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        OverlayRegistry.registerOverlayTop("Ability Power HUD", AbilityPowerHud::render);
    }

    public static void render(ForgeIngameGui gui, PoseStack stack,
                              float partialTicks, int screenWidth, int screenHeight) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        var font = Minecraft.getInstance().font;

        var x = screenWidth / 2 - 100;
        var y = screenHeight - 32;

        var abilityPower = AbilityPowerUtil.get(Minecraft.getInstance().player);
        var percent = (int) (AbilityPowerUtil.getMultiplier(abilityPower) * 100.0F);
        var text = percent + "%";

        GuiComponent.drawCenteredString(stack, font, text, x, y, 0xFFFFFFFF);
    }

}
