package network.something.somepotter.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import network.something.somepotter.SomePotter;

public class DamageSourceUtil {

    public static DamageSource create(String name) {
        return new DamageSource("death.attack." + SomePotter.MOD_ID + "." + name);
    }

    public static DamageSource indirect(String name, Entity source) {
        return new IndirectEntityDamageSource(
                "death.attack." + SomePotter.MOD_ID + "." + name,
                source, source
        );
    }

}
