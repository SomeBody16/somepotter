package network.something.somepotter.spells.cast.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import network.something.somepotter.SomePotter;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ProjectileCastEntityRenderer extends EntityRenderer<ProjectileCastEntity> {

    @SubscribeEvent
    public static void register(FMLClientSetupEvent event) {
        EntityRenderers.register(ProjectileCastEntity.TYPE, ProjectileCastEntityRenderer::new);
    }

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");

    protected ProjectileCastEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    protected int getBlockLightLevel(ProjectileCastEntity pEntity, BlockPos pPos) {
        return 12;
    }

    @Override
    public void render(ProjectileCastEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
    }

    @Override
    public ResourceLocation getTextureLocation(ProjectileCastEntity pEntity) {
        return TEXTURE;
    }
}
