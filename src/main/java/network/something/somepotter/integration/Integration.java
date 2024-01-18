package network.something.somepotter.integration;

import net.minecraftforge.fml.ModList;

public abstract class Integration {

    protected void ifLoaded() {
    }

    protected String modId;

    public Integration(String modId) {
        this.modId = modId;
    }

    public void init() {
        if (isLoaded()) {
            ifLoaded();
        }
    }

    public boolean isLoaded() {
        return ModList.get().isLoaded(this.modId);
    }

    public boolean isNotLoaded() {
        return !isLoaded();
    }

}
