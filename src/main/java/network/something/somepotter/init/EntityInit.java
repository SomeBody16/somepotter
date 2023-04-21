package network.something.somepotter.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import network.something.somepotter.SomePotter;
import network.something.somepotter.entity.SpellEntity;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, SomePotter.MOD_ID);

    public static final RegistryObject<EntityType<SpellEntity>> SPELL = ENTITIES.register(
            "spell",
            () -> EntityType.Builder.of((EntityType.EntityFactory<SpellEntity>) SpellEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .build("spell")
    );

}
