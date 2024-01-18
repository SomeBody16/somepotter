package network.something.somepotter.spells.spell.accio;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import network.something.somepotter.spells.tickable.Tickable;

import java.util.List;

public class AccioTickable extends Tickable {

    public static void moveToEntity(Entity target, List<? extends Entity> entities, float speed) {

        for (var entity : entities) {
            Vec3 vec3 = new Vec3(target.getX() - entity.getX(), target.getY() + (double) target.getEyeHeight() / 2.0D - entity.getY(), target.getZ() - entity.getZ());

            entity.setDeltaMovement(entity.getDeltaMovement().add(vec3.normalize().scale(speed)));
            entity.hasImpulse = true;
        }
    }

    protected Entity target;
    protected List<? extends Entity> entities;
    protected float speed;


    public AccioTickable(int duration, Entity target, List<? extends Entity> entities, float speed) {
        super(duration);
        this.speed = speed;
        this.target = target;
        this.entities = entities;
    }

    @Override
    public void tick() {
        super.tick();
        moveToEntity(target, entities, speed);
    }
}
