package diorite;

import diorite.utils.DioriteMathUtils;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public enum GameMode
{
    NOT_SET(- 1, ""),
    SURVIVAL(0, "survival"),
    CREATIVE(1, "creative"),
    ADVENTURE(2, "adventure"),
    SPECTATOR(3, "spectator");

    private final int    id;
    private final String name;
    private static final TByteObjectMap<GameMode> gameModes = new TByteObjectHashMap<>();

    GameMode(final int id, final String name)
    {
        this.id = id;
        this.name = name;
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
        if (! DioriteMathUtils.canBeByte(id))
        {
            return NOT_SET;
        }
        final GameMode gameMode = gameModes.get((byte) id);
        return (gameMode == null) ? NOT_SET : gameMode;
    }

    static
    {
        for (final GameMode gameMode : GameMode.values())
        {
            gameModes.put((byte) gameMode.id, gameMode);
        }
    }
}
