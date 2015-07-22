package org.diorite.material.items.tool.simple;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class ClockMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final ClockMat CLOCK = new ClockMat();

    private static final Map<String, ClockMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<ClockMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected ClockMat()
    {
        super("CLOCK", 347, "minecraft:clock", "CLOCK", (short) 0x00);
    }

    protected ClockMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected ClockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public ClockMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public ClockMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Clock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Clock or null
     */
    public static ClockMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Clock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Clock or null
     */
    public static ClockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final ClockMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public ClockMat[] types()
    {
        return ClockMat.clockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static ClockMat[] clockTypes()
    {
        return byID.values(new ClockMat[byID.size()]);
    }

    static
    {
        ClockMat.register(CLOCK);
    }
}

