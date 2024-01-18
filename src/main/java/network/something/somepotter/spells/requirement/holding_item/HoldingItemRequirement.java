package network.something.somepotter.spells.requirement.holding_item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.util.TranslatableComponentUtil;

public class HoldingItemRequirement extends Requirement {
    public static final String ID = "holding_item";


    public static HoldingItemRequirement of(ResourceLocation itemId, int count, boolean consume) {
        return new HoldingItemRequirement(itemId, count, consume);
    }

    public static HoldingItemRequirement of(Item item, int count, boolean consume) {
        return of(item.getRegistryName(), count, consume);
    }

    protected ResourceLocation itemId;
    protected int count;
    protected boolean consume;

    protected HoldingItemRequirement(ResourceLocation itemId, int count, boolean consume) {
        this.itemId = itemId;
        this.count = count;
        this.consume = consume;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Component getText() {
        return TranslatableComponentUtil.item(itemId);
    }

    @Override
    public boolean isMet(ServerPlayer player) {
        return getItem(player) != null;
    }

    @Override
    public void perform(ServerPlayer player) {
        if (!consume) return;
        getItem(player).shrink(count);
    }

    protected ItemStack getItem(ServerPlayer player) {
        var mainHand = player.getMainHandItem();
        var offHand = player.getOffhandItem();

        if (mainHand.getItem().getRegistryName() == itemId) return mainHand;
        if (offHand.getItem().getRegistryName() == itemId) return offHand;
        return null;
    }
}
