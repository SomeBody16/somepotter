package network.something.somepotter.spell.spells;

import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.api.particle.SpellParticle;
import network.something.somepotter.spell.spells.accio.AccioSpell;
import network.something.somepotter.spell.spells.aguamenti.AguamentiSpell;
import network.something.somepotter.spell.spells.alohomora.AlohomoraSpell;
import network.something.somepotter.spell.spells.ascendio.AscendioSpell;
import network.something.somepotter.spell.spells.avada_kedavra.AvadaKedavraSpell;
import network.something.somepotter.spell.spells.bombarda.BombardaSpell;
import network.something.somepotter.spell.spells.cistem_aperio.CistemAperioSpell;
import network.something.somepotter.spell.spells.colloportus.ColloportusSpell;
import network.something.somepotter.spell.spells.confringo.ConfringoSpell;
import network.something.somepotter.spell.spells.crucio.CrucioSpell;
import network.something.somepotter.spell.spells.depulso.DepulsoSpell;
import network.something.somepotter.spell.spells.disillusio.DisillusioSpell;
import network.something.somepotter.spell.spells.episkey.EpiskeySpell;
import network.something.somepotter.spell.spells.expelliarmus.ExpelliarmusSpell;
import network.something.somepotter.spell.spells.fumos.FumosSpell;
import network.something.somepotter.spell.spells.herbivicus.HerbivicusSpell;
import network.something.somepotter.spell.spells.incendio.IncendioSpell;
import network.something.somepotter.spell.spells.levioso.LeviosoSpell;
import network.something.somepotter.spell.spells.melofors.MeloforsSpell;
import network.something.somepotter.spell.spells.obscuro.ObscuroSpell;
import network.something.somepotter.spell.spells.reparo.ReparoSpell;
import network.something.somepotter.spell.spells.revelio.RevelioSpell;
import network.something.somepotter.spell.spells.stupefy.StupefySpell;
import network.something.somepotter.spell.spells.tempest.TempestSpell;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Spells {

    private static final Map<String, AbstractSpell> SPELLS = new HashMap<>();

    public static AccioSpell ACCIO = register(new AccioSpell());
    public static AguamentiSpell AGUAMENTI = register(new AguamentiSpell());
    public static AlohomoraSpell ALOHOMORA = register(new AlohomoraSpell());
    public static AscendioSpell ASCENDIO = register(new AscendioSpell());
    public static AvadaKedavraSpell AVADA_KEDAVRA = register(new AvadaKedavraSpell());
    public static BombardaSpell BOMBARDA = register(BombardaSpell.BOMBARDA);
    public static BombardaSpell BOMBARDA_MAXIMA = register(BombardaSpell.BOMBARDA_MAXIMA);
    public static CistemAperioSpell CISTEM_APERIO = register(new CistemAperioSpell());
    public static ColloportusSpell COLLOPORTUS = register(new ColloportusSpell());
    public static ConfringoSpell CONFRINGO = register(new ConfringoSpell());
    public static CrucioSpell CRUCIO = register(new CrucioSpell());
    public static DepulsoSpell DEPULSO = register(new DepulsoSpell());
    public static DisillusioSpell DISILLUSIO = register(new DisillusioSpell());
    public static EpiskeySpell EPISKEY = register(new EpiskeySpell());
    public static ExpelliarmusSpell EXPELLIARMUS = register(new ExpelliarmusSpell());
    public static FumosSpell FUMOS = register(new FumosSpell());
    public static HerbivicusSpell HERBIVICUS = register(new HerbivicusSpell());
    public static IncendioSpell INCENDIO = register(new IncendioSpell());
    public static LeviosoSpell LEVIOSO = register(new LeviosoSpell());
    public static MeloforsSpell MELOFORS = register(new MeloforsSpell());
    public static ObscuroSpell OBSCURO = register(new ObscuroSpell());
    public static ReparoSpell REPARO = register(new ReparoSpell());
    public static RevelioSpell REVELIO = register(new RevelioSpell());
    public static StupefySpell STUPEFY = register(new StupefySpell());
    public static TempestSpell TEMPEST = register(new TempestSpell());

    public static AbstractSpell get(String spellId) {
        return SPELLS.getOrDefault(spellId, UnknownSpell.instance);
    }

    private static <TSpell extends AbstractSpell> TSpell register(TSpell spell) {
        SPELLS.put(spell.getId(), spell);
        SpellParticle.register(spell.getId(), spell.getParticle());
        SomePotter.LOGGER.info("Registered spell: {}", spell.getId());
        return spell;
    }

    public static void forEach(Consumer<AbstractSpell> consumer) {
        SPELLS.forEach((id, spell) -> consumer.accept(spell));
    }

}
