package network.something.somepotter.spell.spells.morsmordre.dark_mark;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import network.something.somepotter.SomePotter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@OnlyIn(value = Dist.CLIENT)
public class DarkMarkRenderer extends GeoEntityRenderer<DarkMarkEntity> {
    public static final float SIZE = 8.0f;

    public DarkMarkRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DarkMarkModel());
        this.shadowRadius = SIZE * 2;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(DarkMarkEntity animatable) {
        return new ResourceLocation(SomePotter.MOD_ID, "textures/entity/dark_mark.png");
    }

    @Override
    public RenderType getRenderType(DarkMarkEntity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        poseStack.scale(SIZE, SIZE, SIZE);
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
