package org.diorite.scoreboard;

import org.diorite.utils.SimpleEnum.ASimpleEnum;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class ScoreboardPosition extends ASimpleEnum<ScoreboardPosition>
{
    static
    {
        init(ScoreboardPosition.class, 3);
    }

    public static final ScoreboardPosition LIST = new ScoreboardPosition("LIST", 0);
    public static final ScoreboardPosition SIDEBAR = new ScoreboardPosition("SIDEBAR", 1);
    public static final ScoreboardPosition BELOW_NAME = new ScoreboardPosition("BELOW_NAME", 2);

    public ScoreboardPosition(final String enumName, final int ordinal)
    {
        super(enumName, ordinal);
    }

    /**
     * Register new {@link ScoreboardPosition} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ScoreboardPosition element)
    {
        ASimpleEnum.register(ScoreboardPosition.class, element);
    }

    /**
     * Get one of {@link ScoreboardPosition} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ScoreboardPosition getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ScoreboardPosition.class, ordinal);
    }

    /**
     * Get one of {@link ScoreboardPosition} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ScoreboardPosition getByEnumName(final String name)
    {
        return getByEnumName(ScoreboardPosition.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ScoreboardPosition[] values()
    {
        final Int2ObjectMap<ScoreboardPosition> map = getByEnumOrdinal(ScoreboardPosition.class);
        return map.values().toArray(new ScoreboardPosition[map.size()]);
    }

    static
    {
        ScoreboardPosition.register(LIST);
        ScoreboardPosition.register(SIDEBAR);
        ScoreboardPosition.register(BELOW_NAME);
    }
}
