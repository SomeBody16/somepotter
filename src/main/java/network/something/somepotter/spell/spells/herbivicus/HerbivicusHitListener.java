package network.something.somepotter.spell.spells.herbivicus;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.spell.api.event.SpellHitBlockEvent;
import network.something.somepotter.spell.api.event.SpellHitEntityEvent;
import network.something.somepotter.spell.api.event.SpellHitListener;

public class HerbivicusHitListener extends SpellHitListener {
    public HerbivicusHitListener() {
        super(HerbivicusSpell.ID);
    }

    @Override
    public void onHitEntity(ServerLevel level, SpellHitEntityEvent event) {
    }

    @Override
    public void onHitBlock(ServerLevel level, SpellHitBlockEvent event) {
        var blockPos = event.getBlockPos();

        var boneMeal = new ItemStack(Items.BONE_MEAL, 64);
        if (event.getCaster() instanceof Player player) {
            if (BoneMealItem.applyBonemeal(boneMeal, level, blockPos, player)) {
                level.levelEvent(1505, blockPos, 0); // particles
            } else {
                var relativePos = blockPos.relative(event.getDirection());
                var flag = level.getBlockState(blockPos).isFaceSturdy(level, blockPos, event.getDirection());
                if (flag && BoneMealItem.growWaterPlant(boneMeal, level, relativePos, event.getDirection())) {
                    level.levelEvent(1505, relativePos, 0); // particles
                }
            }
        }
    }
}
