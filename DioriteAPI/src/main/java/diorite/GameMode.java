package diorite;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.utils.collections.SimpleStringHashMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class GameMode
{
    public static final GameMode NOT_SET   = new GameMode("NOT_SET", - 1, "");
    public static final GameMode SURVIVAL  = new GameMode("SURVIVAL", 0, "survival");
    public static final GameMode CREATIVE  = new GameMode("CREATIVE", 1, "creative");
    public static final GameMode ADVENTURE = new GameMode("ADVENTURE", 2, "adventure");
    public static final GameMode SPECTATOR = new GameMode("SPECTATOR", 3, "spectator");

    private final String enumName;
    private final int    id;
    private final String name;
    private static final Map<String, GameMode>   byName = new SimpleStringHashMap<>(5, .1f);
    private static final TIntObjectMap<GameMode> byID   = new TIntObjectHashMap<>(5, .1f);

    public GameMode(final String enumName, final int id, final String name)
    {
        this.enumName = enumName;
        this.id = id;
        this.name = name;
    }

    public String name()
    {
        return this.enumName;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public static GameMode getByID(final int id)
    {
        final GameMode gameMode = byID.get(id);
        return (gameMode == null) ? NOT_SET : gameMode;
    }

    public static GameMode getByEnumName(final String name)
    {
        final GameMode gameMode = byName.get(name);
        return (gameMode == null) ? NOT_SET : gameMode;
    }

    public static void register(final GameMode element)
    {
        byID.put(element.getId(), element);
        byName.put(element.name(), element);
    }

    static
    {
        register(NOT_SET);
        register(SURVIVAL);
        register(CREATIVE);
        register(ADVENTURE);
        register(SPECTATOR);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("id", this.id).append("name", this.name).toString();
    }
}
