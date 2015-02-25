package diorite;

import diorite.utils.DioriteMathUtils;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public enum Dimension
{
    NETHER(- 1),
    OVERWORLD(0),
    END(1);

    private final int value;
    private static final TByteObjectMap<Dimension> elements = new TByteObjectHashMap<>();

    Dimension(final int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    public static Dimension getByID(final int id)
    {
        if (! DioriteMathUtils.canBeByte(id))
        {
            return null;
        }
        return elements.get((byte) id);
    }

    static
    {
        for (final Dimension type : Dimension.values())
        {
            elements.put((byte) type.value, type);
        }
    }
}
