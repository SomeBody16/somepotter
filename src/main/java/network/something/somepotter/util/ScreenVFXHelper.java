package network.something.somepotter.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;
import team.lodestar.lodestone.setup.LodestoneShaderRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;

import java.awt.*;
import java.util.function.Supplier;

public class ScreenVFXHelper {

    /**
     * Stolen from ArcanaCodexHelper
     * <a href="https://github.dev/SammySemicolon/Malum-Mod/tree/1.6-1.20.1/src/main/java/com/sammy/malum/visual_effects">...</a>
     */
    public static void point(PoseStack stack, ResourceLocation texture, boolean corrupted, int x, int y, Color color) {
        var size = 4;
        var shaderInstance = (ExtendedShaderInstance) LodestoneShaderRegistry.DISTORTED_TEXTURE.getInstance().get();
        shaderInstance.safeGetUniform("YFrequency").set(corrupted ? 5f : 11f);
        shaderInstance.safeGetUniform("XFrequency").set(corrupted ? 12f : 17f);
        shaderInstance.safeGetUniform("Speed").set(2000f * (corrupted ? -0.75f : 1));
        shaderInstance.safeGetUniform("Intensity").set(corrupted ? 14f : 50f);
        Supplier<ShaderInstance> shaderInstanceSupplier = () -> shaderInstance;

        VFXBuilders.ScreenVFXBuilder builder = VFXBuilders.createScreen()
                .setPosColorTexLightmapDefaultFormat()
                .setShader(shaderInstanceSupplier)
                .setColor(color)
                .setAlpha(0.9f)
                .setZLevel(200)
                .setShader(() -> shaderInstance);

        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        renderTexture(texture, stack, builder, x, y, 0, 0, size, size, 16, 16);
        builder.setAlpha(0.8F);
        renderTexture(texture, stack, builder, x - 1, y, 0, 0, size, size, 16, 16);
        renderTexture(texture, stack, builder, x + 1, y, 0, 0, size, size, 16, 16);
        renderTexture(texture, stack, builder, x, y - 1, 0, 0, size, size, 16, 16);
        if (corrupted) {
            builder.setColor(color.darker().darker().darker());
        }
        renderTexture(texture, stack, builder, x, y + 1, 0, 0, size, size, 16, 16);
        shaderInstance.setUniformDefaults();
        RenderSystem.defaultBlendFunc();
    }

    protected static void renderTexture(ResourceLocation texture, PoseStack poseStack, VFXBuilders.ScreenVFXBuilder builder, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        renderTexture(texture, poseStack, builder, x, y, 0, u, v, width, height, textureWidth, textureHeight);
    }

    protected static void renderTexture(ResourceLocation texture, PoseStack poseStack, VFXBuilders.ScreenVFXBuilder builder, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        builder.setPositionWithWidth(x, y, width, height)
                .setZLevel(z)
                .setShaderTexture(texture)
                .setUVWithWidth(u, v, width, height, textureWidth, textureHeight)
                .draw(poseStack);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
    }

}
