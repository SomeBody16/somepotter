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

        var x = getStartX(screenWidth);
        var y = getStartY(screenHeight);
        var language = Language.getInstance();

        var cooldowns = CooldownClient.getCooldowns();
//        cooldowns.put("avada_kedavra", new CooldownClient.Cooldown("avada_kedavra", 0.5F));

        var config = CooldownClientConfig.get();
        for (var entry : cooldowns.entrySet()) {
            var spell = SpellInit.get(entry.getKey());
            var cooldown = entry.getValue();

            var text = language.getLanguageData().get("spell." + spell.getId());

            var remainingCount = (int) Math.floor(text.length() * cooldown.cooldownProgress);
            var coloredCount = text.length() - remainingCount;

            var colored = text.substring(0, coloredCount);
            var remaining = text.substring(coloredCount, coloredCount + remainingCount);

            var targetX = x;
            if (config.rightToLeft) {
                targetX -= font.width(text);
            }

            var remainingX = targetX + font.width(colored);

            font.draw(stack, colored, targetX, y, spell.getColor().getRGB());
            font.draw(stack, remaining, remainingX, y, 0x000000);

            if (config.topToBottom) {
                y += font.lineHeight + 2;
            } else {
                y -= font.lineHeight + 2;
            }
        }
    }

    protected static int getStartX(int screenWidth) {
        int x = (screenWidth / 2) - 90;

        var config = CooldownClientConfig.get();
        if (config.hudX > -1) {
            x = CooldownClientConfig.get().hudX;
        }
        x += config.hudOffsetX;
        return x;
    }

    protected static int getStartY(int screenHeight) {
        int y = screenHeight - 50;

        var config = CooldownClientConfig.get();
        if (config.hudY > -1) {
            y = CooldownClientConfig.get().hudY;
        } else {
            var player = Minecraft.getInstance().player;
            if (player != null) {
                // Handle armor
                if (player.getArmorValue() > 0) {
                    y -= 10;
                }

                // Handle multiple bars of health
                var maxHealth = player.getMaxHealth();
                var rows = (int) Math.ceil(maxHealth / 20);
                if (rows == 2) {
                    y -= 10;
                }
                if (rows == 3) {
                    y -= 16;
                }
                if (rows == 4) {
                    y -= 22;
                }
            }
        }
        y += config.hudOffsetY;
        return y;
    }

}
