package network.something.somemagic.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import network.something.somemagic.init.SpellInit;
import network.something.somemagic.spell.Spell;

public class ItemWand extends Item {
    public ItemWand() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        Spell spell = getSpell(player, usedHand);
        spell.cast();

        player.swing(usedHand, true);
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }

    public Spell getSpell(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        var itemNbt = itemStack.getTag();
        if (itemNbt == null) {
            return new Spell("", player);
        }
        String spellName = itemNbt.getString("spell");
        return SpellInit.getSpell(spellName, player);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return super.useOn(pContext);
    }
}
