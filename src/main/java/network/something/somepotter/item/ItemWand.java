package network.something.somepotter.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import network.something.somepotter.client.speech.SpeechToSpellThread;
import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.spell.spells.Spells;
import org.jetbrains.annotations.NotNull;

public class ItemWand extends Item {
    public ItemWand() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (player.isCrouching()) {
            if (!level.isClientSide) {
                AbstractSpell.Event.cast(Spells.BASIC_CAST, player);
            }
            return InteractionResultHolder.consume(player.getItemInHand(usedHand));
        }

        player.startUsingItem(usedHand);
        if (level.isClientSide) {
            SpeechToSpellThread.getInstance().resumeRecognition();
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }

        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    @Override
    public void releaseUsing(ItemStack wand, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLevel.isClientSide) {
            SpeechToSpellThread.getInstance().pauseRecognition();
        }
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }
}
