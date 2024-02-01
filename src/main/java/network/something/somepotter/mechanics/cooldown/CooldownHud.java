package network.something.somepotter.mechanics.cooldown;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.locale.Language;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import network.something.somepotter.SomePotter;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.util.WandUtil;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CooldownHud {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        OverlayRegistry.registerOverlayTop("Spell Cooldown HUD", CooldownHud::render);
    }

    public static void render(ForgeIngameGui gui, PoseStack stack,
                              float partialTicks, int screenWidth, int screenHeight) {

        var player = Minecraft.getInstance().player;
        if (player == null || !WandUtil.isPlayerHoldingWand(player)) return;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        var font = Minecraft.getInstance().font;

        var x = (screenWidth / 2) - 91;
        var y = screenHeight - 60;
        var language = Language.getInstance();

        var cooldowns = CooldownClient.getCooldowns();
//        cooldowns.put("avada_kedavra", new CooldownClient.Cooldown("avada_kedavra", 0.5F));

        for (var entry : cooldowns.entrySet()) {
            var spell = SpellInit.get(entry.getKey());
            var cooldown = entry.getValue();

            var text = language.getLanguageData().get("spell." + spell.getId());

            var remainingCount = (int) Math.floor(text.length() * cooldown.cooldownProgress);
            var coloredCount = text.length() - remainingCount;

            var colored = text.substring(0, coloredCount);
            var remaining = text.substring(coloredCount, coloredCount + remainingCount);

            var remainingX = x + font.width(colored);

            font.draw(stack, colored, x, y, spell.getColor().getRGB());
            font.draw(stack, remaining, remainingX, y, 0x000000);
            y -= font.lineHeight + 2;
        }
    }

}
