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
    private static final TByteObjectMap<Dimension> dims = new TByteObjectHashMap<>();

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
        return dims.get((byte) id);
    }

    static
    {
        for (final Dimension type : Dimension.values())
        {
            dims.put((byte) type.value, type);
        }
    }
}
