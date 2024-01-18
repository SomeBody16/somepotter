package network.something.somepotter.mechanics.cooldown;

import java.util.HashMap;
import java.util.Map;

public class CooldownClient {

    public static class Cooldown {
        public String spellId;
        public float cooldownProgress;

        public Cooldown(String spellId, float cooldownProgress) {
            this.spellId = spellId;
            this.cooldownProgress = cooldownProgress;
        }
    }

    protected static Map<String, Cooldown> clientCache = new HashMap<>();

    public static void updateCache(Map<String, Cooldown> cooldowns) {
        clientCache = cooldowns;
    }

    public static Map<String, Cooldown> getCooldowns() {
        return clientCache;
    }
}
