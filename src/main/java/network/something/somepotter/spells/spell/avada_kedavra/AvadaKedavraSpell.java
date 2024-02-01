package network.something.somepotter.spells.spell.avada_kedavra;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import network.something.somepotter.event.SpellHitEvent;
import network.something.somepotter.init.SpellTypeInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.spells.requirement.near_entity.NearEntityRequirement;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.curse.CurseType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

/**
 * Kills the target
 */
public class AvadaKedavraSpell extends Spell {

    public static final String ID = "avada_kedavra";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull() SpellListener<AvadaKedavraSpell> getListener() {
        return new AvadaKedavraListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast();
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.WITHER_SKELETON_SKULL);
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(45, 10, 20, 55);
        gesture.draw.line(20, 55, 80, 45);
        gesture.draw.line(80, 45, 45, 90);

        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return null;
    }

    @Override
    public Color getColor() {
        return new Color(57, 155, 130);
    }

    @Override
    public @NotNull SpellType getType() {
        return SpellTypeInit.get(CurseType.ID);
    }

    @Override
    public void playHitSound(SpellHitEvent.Post<Spell> event) {
        var sound = SoundEvents.WITHER_DEATH;
        var pos = event.hitResult.getLocation();
        event.level.playSound(
                null,
                pos.x, pos.y, pos.z,
                sound,
                SoundSource.PLAYERS,
                0.8F, 1.0F
        );
    }

    @Override
    public List<Requirement> getRequirements() {
        var result = super.getRequirements();
        result.add(NearEntityRequirement.of(WitherBoss.class, 6));
        return result;
    }
}
