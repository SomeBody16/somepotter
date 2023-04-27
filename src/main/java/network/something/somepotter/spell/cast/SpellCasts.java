package network.something.somepotter.spell.cast;

import network.something.somepotter.SomePotter;
import network.something.somepotter.spell.cast.missile.MissileCast;
import network.something.somepotter.spell.cast.projectile.ProjectileCast;
import network.something.somepotter.spell.cast.projectile_or_self.ProjectileOrSelfCast;
import network.something.somepotter.spell.cast.self.SelfCast;
import network.something.somepotter.spell.cast.touch.TouchCast;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpellCasts {
    protected static final List<AbstractCast> CASTS = new ArrayList<>();

    public static ProjectileCast PROJECTILE = register(new ProjectileCast());
    public static ProjectileOrSelfCast PROJECTILE_OR_SELF = register(new ProjectileOrSelfCast());
    public static SelfCast SELF = register(new SelfCast());
    public static TouchCast TOUCH = register(new TouchCast());
    public static MissileCast MISSILE = register(new MissileCast());

    public static @Nullable AbstractCast get(String typeId) {
        return CASTS.stream()
                .filter(t -> Objects.equals(t.getId(), typeId))
                .findFirst()
                .orElse(null);
    }

    private static <TSpell extends AbstractCast> TSpell register(TSpell cast) {
        CASTS.add(cast);
        SomePotter.LOGGER.info("Registered cast: {}", cast.getId());
        return cast;
    }

    public static List<AbstractCast> asList() {
        return CASTS;
    }
}
