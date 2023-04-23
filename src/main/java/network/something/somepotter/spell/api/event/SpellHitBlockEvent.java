package network.something.somepotter.spell.api.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Event;
import network.something.somepotter.spell.spells.AbstractSpell;
import network.something.somepotter.spell.spells.Spells;

public class SpellHitBlockEvent extends Event {

    private final String spellId;
    private final LivingEntity caster;
    private final BlockHitResult hitResult;

    public SpellHitBlockEvent(String spellId, LivingEntity caster, BlockHitResult hitResult) {
        this.spellId = spellId;
        this.caster = caster;
        this.hitResult = hitResult;
    }

    public String getSpellId() {
        return spellId;
    }

    public LivingEntity getCaster() {
        return caster;
    }

    public BlockHitResult getHitResult() {
        return hitResult;
    }

    public BlockPos getBlockPos() {
        return hitResult.getBlockPos();
    }

    public Vec3 getLocation() {
        return hitResult.getLocation();
    }

    public Direction getDirection() {
        return hitResult.getDirection();
    }

    public ServerLevel getLevel() {
        return (ServerLevel) caster.getLevel();
    }

    public AbstractSpell getSpell() {
        return Spells.get(spellId);
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
