package network.something.somepotter.listener.cooldown;

import ca.lukegrahamlandry.lib.config.Comment;
import network.something.somepotter.spell.alarte_ascendare.AlarteAscendareSpell;
import network.something.somepotter.spell.alohomora.AlohomoraSpell;
import network.something.somepotter.spell.avada_kedavra.AvadaKedavraSpell;
import network.something.somepotter.spell.bombarda.BombardaMaximaSpell;
import network.something.somepotter.spell.bombarda.BombardaSpell;
import network.something.somepotter.util.ConfigUtil;

import java.util.Map;
import java.util.function.Supplier;

public class CooldownConfig {

    protected static final Supplier<CooldownConfig> CONFIG = ConfigUtil.server(CooldownConfig.class, "cooldown");

    public static CooldownConfig get() {
        return CONFIG.get();
    }

    @Comment("Cooldown of spell in ticks")
    public int cooldownDefault = 20 * 2;

    @Comment("Cooldown of spell in ticks")
    public Map<String, Integer> cooldown = Map.of(
            AvadaKedavraSpell.ID, 20 * 60,
            AlarteAscendareSpell.ID, 20 * 10,
            AlohomoraSpell.ID, 20 * 60,
            BombardaSpell.ID, 20 * 20,
            BombardaMaximaSpell.ID, 20 * 40
    );
}
