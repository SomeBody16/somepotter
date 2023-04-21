package network.something.somemagic.magic.spell;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import network.something.somemagic.entity.SpellEntity;
import network.something.somemagic.magic.spell.core.ProjectileSpell;
import network.something.somemagic.util.SpellColor;

public class MeloforsSpell extends ProjectileSpell {
    public static final String ID = "melofors";

    public MeloforsSpell(LivingEntity caster) {
        super(ID, caster);
    }

    @Override
    public int getCooldownTicks() {
        return 20 * 5;
    }

    @Override
    public void onHitEntity(SpellEntity spellEntity, EntityHitResult hitResult) {
        if (hitResult.getEntity() instanceof LivingEntity livingEntity) {
            var helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
            var pumpkinStack = Items.CARVED_PUMPKIN.getDefaultInstance();

            if (helmet.isEmpty()) {
                pumpkinStack.enchant(Enchantments.BINDING_CURSE, 1);
            } else {
                livingEntity.spawnAtLocation(helmet);
            }

            livingEntity.setItemSlot(EquipmentSlot.HEAD, pumpkinStack);
        }
    }

    @Override
    public void onHitBlock(SpellEntity spellEntity, BlockHitResult hitResult) {
    }

    @Override
    public SpellColor getColor() {
        return SpellColor.UTILITY;
    }

}

