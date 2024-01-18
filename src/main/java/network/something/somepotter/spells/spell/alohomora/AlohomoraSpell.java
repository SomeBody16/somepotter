package network.something.somepotter.spells.spell.alohomora;

import iskallia.vault.init.ModBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.integration.Integrations;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.touch.TouchCast;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.spells.requirement.in_block.InBlockRequirement;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.charm.CharmType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Opens locked doors
 * <p>
 * Open doors, trapdoors, fence gates, and iron doors
 */
public class AlohomoraSpell extends Spell {

    public static final String ID = "alohomora";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<AlohomoraSpell> getListener() {
        return new AlohomoraListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new TouchCast()
                .setRange(3);
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.TRIPWIRE_HOOK);
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.bezierCurve(50, 10, 70, 10, 90, 30, 90, 50);
        gesture.removeFromStart(15);

        gesture.draw.bezierCurve(90, 50, 90, 70, 70, 90, 50, 90);
        gesture.draw.bezierCurve(50, 90, 30, 90, 10, 70, 10, 50);
        gesture.draw.bezierCurve(10, 50, 10, 30, 30, 10, 50, 10);
        gesture.draw.line(50, 10, 50, 98);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(CharmType.ID);
    }

    @Override
    public List<Requirement> getRequirements() {
        var result = super.getRequirements();

        var block = Blocks.IRON_DOOR.getRegistryName();
        if (Integrations.THE_VAULT.isLoaded()) {
            block = ModBlocks.TREASURE_DOOR.getRegistryName();
        }

        result.add(InBlockRequirement.of(block));
        return result;
    }

}
