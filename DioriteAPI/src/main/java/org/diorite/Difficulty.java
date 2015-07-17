package org.diorite;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class Difficulty extends ASimpleEnum<Difficulty>
{
    static
    {
        init(Difficulty.class, 4);
    }

    public static final Difficulty PEACEFUL = new Difficulty("PEACEFUL", 0, "options.difficulty.peaceful");
    public static final Difficulty EASY     = new Difficulty("EASY", 1, "options.difficulty.easy");
    public static final Difficulty NORMAL   = new Difficulty("NORMAL", 2, "options.difficulty.normal");
    public static final Difficulty HARD     = new Difficulty("HARD", 3, "options.difficulty.hard");

    private static final TIntObjectMap<Difficulty> byLevel = new TIntObjectHashMap<>(4, SMALL_LOAD_FACTOR);

    private final int    level;
    private final String option;

    public Difficulty(final String enumName, final int enumId, final int level, final String option)
    {
        super(enumName, enumId);
        this.level = level;
        this.option = option;
    }

    public Difficulty(final String enumName, final int level, final String option)
    {
        super(enumName);
        this.level = level;
        this.option = option;
    }

    public int getLevel()
    {
        return this.level;
    }

    public String getOption()
    {
        return this.option;
    }

    public static Difficulty getByLevel(final int level)
    {
        return byLevel.get(level);
    }

    /**
     * Register new {@link Difficulty} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final Difficulty element)
    {
        ASimpleEnum.register(Difficulty.class, element);
        byLevel.put(element.getLevel(), element);
    }

    /**
     * Get one of {@link Difficulty} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static Difficulty getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(Difficulty.class, ordinal);
    }

    /**
     * Get one of Difficulty entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static Difficulty getByEnumName(final String name)
    {
        return getByEnumName(Difficulty.class, name);
    }

    /**
     * @return all values in array.
     */
    public static Difficulty[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(Difficulty.class);
        return (Difficulty[]) map.values(new Difficulty[map.size()]);
    }

    static
    {
        Difficulty.register(PEACEFUL);
        Difficulty.register(EASY);
        Difficulty.register(NORMAL);
        Difficulty.register(HARD);
    }
}
