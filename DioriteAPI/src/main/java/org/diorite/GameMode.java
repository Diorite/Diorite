package org.diorite;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class GameMode extends ASimpleEnum<GameMode>
{
    static
    {
        init(GameMode.class, 4);
    }

    public static final GameMode SURVIVAL  = new GameMode("SURVIVAL", "survival");
    public static final GameMode CREATIVE  = new GameMode("CREATIVE", "creative");
    public static final GameMode ADVENTURE = new GameMode("ADVENTURE", "adventure");
    public static final GameMode SPECTATOR = new GameMode("SPECTATOR", "spectator");

    private final String name;

    public GameMode(final String enumName, final int enumId, final String name)
    {
        super(enumName, enumId);
        this.name = name;
    }

    public GameMode(final String enumName, final String name)
    {
        super(enumName);
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    /**
     * Register new {@link GameMode} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final GameMode element)
    {
        ASimpleEnum.register(GameMode.class, element);
    }

    /**
     * Get one of {@link GameMode} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static GameMode getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(GameMode.class, ordinal);
    }

    /**
     * Get one of GameMode entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static GameMode getByEnumName(final String name)
    {
        return getByEnumName(GameMode.class, name);
    }

    /**
     * @return all values in array.
     */
    public static GameMode[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(GameMode.class);
        return (GameMode[]) map.values(new GameMode[map.size()]);
    }

    static
    {
        GameMode.register(SURVIVAL);
        GameMode.register(CREATIVE);
        GameMode.register(ADVENTURE);
        GameMode.register(SPECTATOR);
    }
}
