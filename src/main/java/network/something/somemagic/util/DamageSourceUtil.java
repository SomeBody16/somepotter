package network.something.somemagic.util;

import net.minecraft.world.damagesource.DamageSource;
import network.something.somemagic.SomeMagic;

public class DamageSourceUtil {

    public static DamageSource create(String name) {
        return new DamageSource("death.attack." + SomeMagic.MOD_ID + "." + name);
    }

}
