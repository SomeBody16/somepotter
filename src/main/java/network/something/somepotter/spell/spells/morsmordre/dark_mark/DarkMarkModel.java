package network.something.somepotter.spell.spells.morsmordre.dark_mark;

import net.minecraft.resources.ResourceLocation;
import network.something.somepotter.SomePotter;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DarkMarkModel extends AnimatedGeoModel<DarkMarkEntity> {
    @Override
    public ResourceLocation getModelLocation(DarkMarkEntity object) {
        return new ResourceLocation(SomePotter.MOD_ID, "geo/dark_mark.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DarkMarkEntity object) {
        return new ResourceLocation(SomePotter.MOD_ID, "textures/entity/dark_mark.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(DarkMarkEntity animatable) {
        return null;
    }
}
