package network.something.somepotter.init;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import network.something.somepotter.SomePotter;
import network.something.somepotter.spells.spell.Spell;
import network.something.somepotter.spells.spell.accio.AccioSpell;
import network.something.somepotter.spells.spell.aguamenti.AguamentiSpell;
import network.something.somepotter.spells.spell.alarte_ascendare.AlarteAscendareSpell;
import network.something.somepotter.spells.spell.alohomora.AlohomoraSpell;
import network.something.somepotter.spells.spell.apparition.ApparitionSpell;
import network.something.somepotter.spells.spell.arresto_momentum.ArrestoMomentumSpell;
import network.something.somepotter.spells.spell.ascendio.AscendioSpell;
import network.something.somepotter.spells.spell.avada_kedavra.AvadaKedavraSpell;
import network.something.somepotter.spells.spell.basic_cast.BasicCastSpell;
import network.something.somepotter.spells.spell.bombarda.BombardaSpell;
import network.something.somepotter.spells.spell.bombarda_maxima.BombardaMaximaSpell;
import network.something.somepotter.spells.spell.circumrota.CircumrotaSpell;
import network.something.somepotter.spells.spell.confringo.ConfringoSpell;
import network.something.somepotter.spells.spell.depulso.DepulsoSpell;
import network.something.somepotter.spells.spell.disillusio.DisillusioSpell;
import network.something.somepotter.spells.spell.engorgio.EngorgioSpell;
import network.something.somepotter.spells.spell.incarcerous_captura.IncarcerousCapturaSpell;
import network.something.somepotter.spells.spell.protego.ProtegoSpell;
import network.something.somepotter.spells.spell.protego_maxima.ProtegoMaximaSpell;
import network.something.somepotter.spells.spell.reducio.ReducioSpell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = SomePotter.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SpellInit {

    protected static Map<String, Map<String, String>> SPELLS_FOR_DOCS = new HashMap<>();
    protected static Map<String, Spell> SPELLS = new HashMap<>();

    static {
        register(new AccioSpell());
        register(new AguamentiSpell());
        register(new AlarteAscendareSpell());
        register(new AlohomoraSpell());
        register(new ApparitionSpell());
        register(new ArrestoMomentumSpell());
        register(new AscendioSpell());
        register(new AvadaKedavraSpell());
        register(new BasicCastSpell());
        register(new BombardaSpell());
        register(new BombardaMaximaSpell());
        register(new CircumrotaSpell());
//        register(new CistemAperioSpell());
//        register(new ColloportusSpell());
        register(new ConfringoSpell());
//        register(new CrucioSpell());
        register(new DepulsoSpell());
//        register(new DescendoSpell());
        register(new DisillusioSpell());
        registerIfLoaded("pehkui", new EngorgioSpell());
//        register(new EpiskeySpell());
//        register(new ExpelliarmusSpell());
//        register(new FumosSpell());
//        register(new GlaciusSpell());
//        register(new HerbivicusSpell());
        registerIfLoaded("the_vault", new IncarcerousCapturaSpell());
//        register(new IncendioSpell());
//        register(new LeviosoSpell());
//        register(new LumosSpell());
//        register(new MeloforsSpell());
//        register(new MorsmordreSpell());
//        register(new NebulusSpell());
//        register(new NoxSpell());
//        register(new ObscuroSpell());
//        register(new PetrificusTotalusSpell());
        register(new ProtegoSpell());
//        register(new ProtegoDiabolicaSpell());
        register(new ProtegoMaximaSpell());
        registerIfLoaded("pehkui", new ReducioSpell());
//        register(new RevelioSpell());
//        register(new SectumsempraSpell());
//        register(new StupefySpell());
//        register(new TempestSpell());
//        register(new WingardiumLeviosaSpell());
    }

    protected static void register(Spell spell) {
        SPELLS.put(spell.getId(), spell);
        SPELLS_FOR_DOCS.put(spell.getId(), new HashMap<>());
    }

    protected static void registerIfLoaded(String modId, Spell spell) {
        if (ModList.get().isLoaded(modId)) {
            register(spell);
        } else {
            SPELLS_FOR_DOCS.put(spell.getId(), new HashMap<>());
            SPELLS_FOR_DOCS.get(spell.getId()).put("flag", "mod:" + modId);
        }
    }

    public static Spell get(String id) {
        if (SPELLS.containsKey(id)) {
            return SPELLS.get(id);
        } else {
            return SPELLS.get(BasicCastSpell.ID);
        }
    }

    public static boolean has(String id) {
        return SPELLS.containsKey(id);
    }

    public static List<Spell> all() {
        return new ArrayList<>(SPELLS.values());
    }

    public static Map<String, Map<String, String>> allForDocs() {
        return SPELLS_FOR_DOCS;
    }

    public static void init() {
        all().forEach(Spell::register);
    }

}
