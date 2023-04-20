package network.something.somemagic.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import network.something.somemagic.SomeMagic;
import network.something.somemagic.client.speech.SpeechToSpellThread;

public class ItemWand extends Item {
    public ItemWand() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        SomeMagic.LOGGER.info("Started using wand");
        player.startUsingItem(usedHand);

        if (level.isClientSide) {
            SpeechToSpellThread.getInstance().resumeRecognition();
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }

        return InteractionResultHolder.consume(player.getItemInHand(usedHand));


//        Spell spell = getSpell(player, usedHand);
//        spell.cast();
//
//        player.swing(usedHand, true);
//        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }

    @Override
    public void releaseUsing(ItemStack wand, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        SomeMagic.LOGGER.info("Stopped using wand");
        if (pLevel.isClientSide) {
            SpeechToSpellThread.getInstance().pauseRecognition();
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }
}
