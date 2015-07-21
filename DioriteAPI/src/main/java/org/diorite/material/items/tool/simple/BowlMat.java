package org.diorite.material.items.tool.simple;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class BowlMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final BowlMat BOWL = new BowlMat();

    private static final Map<String, BowlMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<BowlMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected BowlMat()
    {
        super("BOWL", 281, "minecraft:bowl", "BOWL", (short) 0x00);
    }

    protected BowlMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected BowlMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public BowlMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public BowlMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Bowl sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Bowl or null
     */
    public static BowlMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Bowl sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Bowl or null
     */
    public static BowlMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BowlMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BowlMat[] types()
    {
        return BowlMat.bowlTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BowlMat[] bowlTypes()
    {
        return byID.values(new BowlMat[byID.size()]);
    }

    static
    {
        BowlMat.register(BOWL);
    }
}

