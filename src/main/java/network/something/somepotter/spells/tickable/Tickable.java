package network.something.somepotter.spells.tickable;

public abstract class Tickable {

    /**
     * The duration of this tickable in ticks.
     */
    protected int duration;

    public Tickable(int duration) {
        this.duration = duration;
    }


    public void tick() {
        this.duration--;
    }

    public void onExpired() {
    }

    public boolean isExpired() {
        return this.duration <= 0;
    }

}
