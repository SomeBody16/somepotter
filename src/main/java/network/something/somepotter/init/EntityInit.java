package network.something.somepotter.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import network.something.somepotter.SomePotter;
import network.something.somepotter.entity.SpellProjectileEntity;
import network.something.somepotter.spell.cast.missile.SpellMissileEntity;
import network.something.somepotter.spell.spells.morsmordre.dark_mark.DarkMarkEntity;
import network.something.somepotter.spell.spells.morsmordre.dark_mark.DarkMarkRenderer;

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

    public static final RegistryObject<EntityType<SpellMissileEntity>> SPELL_MISSILE = ENTITIES.register(
            "spell_missile",
            () -> EntityType.Builder.of((EntityType.EntityFactory<SpellMissileEntity>) SpellMissileEntity::new, MobCategory.MISC)
                    .sized(0.15F, 0.15F)
                    .build("spell_missile")
    );

    public static final RegistryObject<EntityType<DarkMarkEntity>> DARK_MARK = ENTITIES.register(
            "dark_mark",
            () -> EntityType.Builder.of(DarkMarkEntity::new, MobCategory.MONSTER)
                    .sized(DarkMarkRenderer.SIZE, DarkMarkRenderer.SIZE)
                    .fireImmune()
                    .build("dark_mark")
    );

}
