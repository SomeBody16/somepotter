package network.something.somepotter.spells.spell.protego_diabolica;

import net.minecraft.world.damagesource.DamageSource;
import network.something.somepotter.init.SpellInit;
import network.something.somepotter.mechanics.gesture.SpellGesture;
import network.something.somepotter.spells.cast.Cast;
import network.something.somepotter.spells.cast.projectile.ProjectileCast;
import network.something.somepotter.spells.requirement.Requirement;
import network.something.somepotter.spells.requirement.spell_learned.SpellLearnedRequirement;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.SpellListener;
import network.something.somepotter.spells.spell.protego_maxima.ProtegoMaximaSpell;
import network.something.somepotter.spells.spell_type.SpellType;
import network.something.somepotter.spells.spell_type.hex.HexType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

/**
 * Creates a protective barrier in specified blocks
 * Players around when casted are considered owners and can pass at any time
 * Casting basic cast at barrier will unlock it and allow players to pass
 */
public class ProtegoDiabolicaSpell extends Spell {

    public static final String ID = "protego_diabolica";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public @NotNull SpellListener<ProtegoDiabolicaSpell> getListener() {
        return new ProtegoDiabolicaListener();
    }

    @Override
    public @NotNull Cast getCast() {
        return new ProjectileCast()
                .setRange(5);
    }

    @Override
    public @NotNull SpellType getType() {
        return new HexType();
    }

    @Override
    public int getSkillPointCost() {
        return 2;
    }

    @Override
    public @NotNull List<SpellGesture> getGestures() {
        var gesture = new SpellGesture(ID);

        gesture.draw.line(20, 80, 80, 80);
        gesture.draw.line(20, 80, 30, 50);
        gesture.draw.line(30, 50, 40, 80);
        gesture.draw.line(40, 80, 50, 40);
        gesture.draw.line(50, 40, 60, 80);
        gesture.draw.line(60, 80, 70, 50);
        gesture.draw.line(70, 50, 80, 80);

        gesture.nextStroke();
        gesture.draw.circle(50, 50, 40);

        gesture.nextStroke();
        gesture.draw.circle(50, 50, 20);

        gesture.nextStroke();
        gesture.draw.bezierCurve(50, 30, 30, 10, 70, 10, 50, 30);


        return List.of(gesture);
    }

    @Override
    public @NotNull List<SpellGesture> getMistakes() {
        return List.of();
    }

    @Override
    public List<Requirement> getRequirements() {
        var result = super.getRequirements();
        result.add(SpellLearnedRequirement.of(ProtegoMaximaSpell.ID));
        return result;
    }

    public DamageSource getDamageSource() {
        return DamageSource.MAGIC
                .setIsFire();
    }

    @Override
    public Color getColor() {
        return SpellInit.get(ProtegoMaximaSpell.ID).getColor();
    }
}
