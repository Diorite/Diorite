package org.diorite;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class Difficulty implements SimpleEnum<Difficulty>
{
    public static final  Difficulty                PEACEFUL = new Difficulty("PEACEFUL", 0, "options.difficulty.peaceful");
    public static final  Difficulty                EASY     = new Difficulty("EASY", 1, "options.difficulty.easy");
    public static final  Difficulty                NORMAL   = new Difficulty("NORMAL", 2, "options.difficulty.normal");
    public static final  Difficulty                HARD     = new Difficulty("HARD", 3, "options.difficulty.hard");
    private static final Map<String, Difficulty>   byName   = new SimpleStringHashMap<>(4, .1f);
    @SuppressWarnings("MagicNumber")
    private static final TIntObjectMap<Difficulty> byID     = new TIntObjectHashMap<>(4, .1f);
    private final String enumName;
    private final int    level;
    private final String option;

    public Difficulty(final String enumName, final int level, final String option)
    {
        this.enumName = enumName;
        this.level = level;
        this.option = option;
    }

    @Override
    public String name()
    {
        return this.enumName;
    }

    @Override
    public int getId()
    {
        return this.level;
    }

    @Override
    public Difficulty byId(final int id)
    {
        return byID.get(id);
    }

    @Override
    public Difficulty byName(final String name)
    {
        return byName.get(name);
    }

    public int getLevel()
    {
        return this.level;
    }

    public String getOption()
    {
        return this.option;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("level", this.level).append("option", this.option).toString();
    }

    public static Difficulty getByLevel(final int level)
    {
        return byID.get(level);
    }

    public static Difficulty getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Difficulty element)
    {
        byID.put(element.getLevel(), element);
        byName.put(element.name(), element);
    }

    static
    {
        register(PEACEFUL);
        register(EASY);
        register(NORMAL);
        register(HARD);
    }
}
