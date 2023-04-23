package network.something.somepotter.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import network.something.somepotter.SomePotter;
import network.something.somepotter.entity.SpellProjectileEntity;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, SomePotter.MOD_ID);

    public static final RegistryObject<EntityType<SpellProjectileEntity>> SPELL_PROJECTILE = ENTITIES.register(
            "spell_projectile",
            () -> EntityType.Builder.of((EntityType.EntityFactory<SpellProjectileEntity>) SpellProjectileEntity::new, MobCategory.MISC)
                    .sized(0.15F, 0.15F)
                    .build("spell_projectile")
    );

    public static final RegistryObject<EntityType<SpellProjectileEntity>> SPELL_TOUCH = ENTITIES.register(
            "spell_touch",
            () -> EntityType.Builder.of((EntityType.EntityFactory<SpellProjectileEntity>) SpellProjectileEntity::new, MobCategory.MISC)
                    .sized(0.15F, 0.15F)
                    .build("spell_touch")
    );

}
