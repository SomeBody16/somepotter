package network.something.somemagic.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import network.something.somemagic.entity.SpellEntity;

@OnlyIn(value = Dist.CLIENT)
public class SpellRenderer extends EntityRenderer<SpellEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

    public SpellRenderer(EntityRendererProvider.Context arg) {
        super(arg);
    }

    @Override
    protected int getBlockLightLevel(SpellEntity arg, BlockPos arg2) {
        return 12;
    }

    @Override
    public void render(SpellEntity spellEntity, float f, float g, PoseStack poseStack, MultiBufferSource source, int i) {
    }

    @Override
    public ResourceLocation getTextureLocation(SpellEntity arg) {
        return TEXTURE_LOCATION;
    }
}
