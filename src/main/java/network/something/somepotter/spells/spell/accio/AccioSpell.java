package network.something.somepotter.spells.spell.accio;

import iskallia.vault.init.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.integration.Integrations;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.touch.TouchCast;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.spells.requirement.holding_item.HoldingItemRequirement;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import network.something.somepotter.util.ConfigUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

/**
 * Summons an object towards the caster
 * <p>
 * Works like a magnet for items only
 */
public class AccioSpell extends Spell {

    public static final String ID = "accio";
    public static final Supplier<AccioConfig> CONFIG = ConfigUtil.server(AccioConfig.class, ID);

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public float getAreaOfEffect() {
        return 1.0F;
    }

    @Override
    public @NotNull Cast getCast() {
        return new TouchCast();
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(CharmType.ID);
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.FISHING_ROD);
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.bezierCurve(5, 95, 25, 10, 75, 10, 95, 95);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public @NotNull SpellListener<AccioSpell> getListener() {
        return new AccioListener();
    }

    @Override
    public List<Requirement> getRequirements() {
        var result = super.getRequirements();

        var item = Items.ENCHANTED_GOLDEN_APPLE.getRegistryName();
        if (Integrations.THE_VAULT.isLoaded()) {
            item = ModItems.MAGNET.getRegistryName();
        }

        result.add(HoldingItemRequirement.of(item, 1, true));
        return result;
    }
}
