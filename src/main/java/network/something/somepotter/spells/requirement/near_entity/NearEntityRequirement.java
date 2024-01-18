package network.something.somepotter.spells.requirement.near_entity;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import network.something.somepotter.spells.requirement.Requirement;

public class NearEntityRequirement<T extends LivingEntity> extends Requirement {
    public static final String ID = "near_entity";

    public static <T extends LivingEntity> NearEntityRequirement<T> of(Class<T> entityClass, double range) {
        return new NearEntityRequirement<>(entityClass, range);
    }

    protected Class<T> entityClass;
    protected double range;

    protected NearEntityRequirement(Class<T> entityClass, double range) {
        this.entityClass = entityClass;
        this.range = range;
    }


    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Component getText() {
        return new TranslatableComponent("spell.requirement.near_entity", entityClass.getSimpleName());
    }

    @Override
    public boolean isMet(ServerPlayer player) {
        var near = player.getLevel().getNearbyEntities(
                entityClass,
                TargetingConditions.DEFAULT,
                player,
                player.getBoundingBox().inflate(range)
        );
        return !near.isEmpty();
    }
}
