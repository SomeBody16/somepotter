package network.something.somepotter.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import network.something.somepotter.entity.SpellProjectileEntity;

@OnlyIn(value = Dist.CLIENT)
public class SpellRenderer extends EntityRenderer<SpellProjectileEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");

    public SpellRenderer(EntityRendererProvider.Context arg) {
        super(arg);
    }

    @Override
    protected int getBlockLightLevel(SpellProjectileEntity arg, BlockPos arg2) {
        return 12;
    }

    @Override
    public void render(SpellProjectileEntity spellEntity, float f, float g, PoseStack poseStack, MultiBufferSource source, int i) {
    }

    @Override
    public ResourceLocation getTextureLocation(SpellProjectileEntity arg) {
        return TEXTURE_LOCATION;
    }
}
