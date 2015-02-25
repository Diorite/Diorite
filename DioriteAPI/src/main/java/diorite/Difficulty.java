package diorite;

import diorite.utils.DioriteMathUtils;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public enum Difficulty
{
    PEACEFUL(0, "options.difficulty.peaceful"),
    EASY(1, "options.difficulty.easy"),
    NORMAL(2, "options.difficulty.normal"),
    HARD(3, "options.difficulty.hard");

    private final int    level;
    private final String option;
    private static final TByteObjectMap<Difficulty> elements = new TByteObjectHashMap<>();

    Difficulty(final int level, final String option)
    {
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

    public static Difficulty getByID(final int id)
    {
        if (! DioriteMathUtils.canBeByte(id))
        {
            return null;
        }
        return elements.get((byte) id);
    }

    static
    {
        for (final Difficulty diff : Difficulty.values())
        {
            elements.put((byte) diff.level, diff);
        }
    }
}
