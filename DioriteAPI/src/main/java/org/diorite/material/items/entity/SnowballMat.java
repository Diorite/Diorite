package org.diorite.material.items.entity;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.items.PlaceableEntityMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class SnowballMat extends ItemMaterialData implements PlaceableEntityMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final SnowballMat SNOWBALL = new SnowballMat();

    private static final Map<String, SnowballMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<SnowballMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected SnowballMat()
    {
        super("SNOWBALL", 332, "minecraft:snowball", "SNOWBALL", (short) 0x00);
    }

    protected SnowballMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected SnowballMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public SnowballMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public SnowballMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Snowball sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Snowball or null
     */
    public static SnowballMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Snowball sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Snowball or null
     */
    public static SnowballMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SnowballMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SnowballMat[] types()
    {
        return SnowballMat.snowballTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SnowballMat[] snowballTypes()
    {
        return byID.values(new SnowballMat[byID.size()]);
    }

    static
    {
        SnowballMat.register(SNOWBALL);
    }
}

