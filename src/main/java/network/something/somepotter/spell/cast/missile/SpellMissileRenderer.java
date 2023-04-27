package network.something.somepotter.spell.cast.missile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT)
public class SpellMissileRenderer extends EntityRenderer<SpellMissileEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");

    public SpellMissileRenderer(EntityRendererProvider.Context arg) {
        super(arg);
    }

    @Override
    protected int getBlockLightLevel(SpellMissileEntity arg, BlockPos arg2) {
        return 12;
    }

    @Override
    public void render(SpellMissileEntity spellEntity, float f, float g, PoseStack poseStack, MultiBufferSource source, int i) {
    }

    @Override
    public ResourceLocation getTextureLocation(SpellMissileEntity arg) {
        return TEXTURE_LOCATION;
    }
}
