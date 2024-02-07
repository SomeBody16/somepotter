package network.something.somepotter.init;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import network.something.somepotter.SomePotter;
import network.something.somepotter.floo.minecraft.FlooFireBlock;
import network.something.somepotter.spells.spell.protego_diabolica.ProtegoDiabolicaBlock;
import network.something.somepotter.spells.spell.protego_diabolica.ProtegoDiabolicaSpell;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit {

    protected static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SomePotter.MOD_ID);

    public static final RegistryObject<FlooFireBlock> FLOO_FIRE = BLOCKS.register(FlooFireBlock.ID, FlooFireBlock::new);

    public static final RegistryObject<ProtegoDiabolicaBlock> PROTEGO_DIABOLICA = BLOCKS.register(ProtegoDiabolicaSpell.ID, ProtegoDiabolicaBlock::new);

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

}
