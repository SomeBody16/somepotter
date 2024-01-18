package network.something.somepotter.floo.minecraft;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.floo.network.FlooNetworkManager;
import network.something.somepotter.init.BlockInit;
import network.something.somepotter.init.ItemInit;
import network.something.somepotter.spells.cast.touch.TouchCast;
import network.something.somepotter.util.ColorUtil;

public class FlooPowderItem extends Item {
    public static final String ID = "floo_powder";

    public FlooPowderItem() {
        super(
                new Item.Properties()
                        .tab(ItemInit.CREATIVE_TAB)
                        .stacksTo(64)
        );
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource) {
        var pos = itemEntity.blockPosition();
        if (DamageSource.IN_FIRE.equals(damageSource)
                && itemEntity.level instanceof ServerLevel level
                && !level.getBlockState(pos.below()).isAir()) {

            FlooNetworkManager.addNode(level, pos);
            level.setBlockAndUpdate(pos, BlockInit.FLOO_FIRE.get().defaultBlockState());

            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 1);

            var particle = new ColorUtil(0x00AA00).getParticle();
            TouchCast.playParticles(particle, level, new Vec3(pos.getX(), pos.getY(), pos.getZ()));
        }

        super.onDestroyed(itemEntity, damageSource);
    }
}
