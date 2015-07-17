package org.diorite.material.items.others;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class GoldNuggetMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final GoldNuggetMat GOLD_NUGGET = new GoldNuggetMat();

    private static final Map<String, GoldNuggetMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<GoldNuggetMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected GoldNuggetMat()
    {
        super("GOLD_NUGGET", 371, "minecraft:gold_nugget", "GOLD_NUGGET", (short) 0x00);
    }

    protected GoldNuggetMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected GoldNuggetMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public GoldNuggetMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public GoldNuggetMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of GoldNugget sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of GoldNugget or null
     */
    public static GoldNuggetMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of GoldNugget sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of GoldNugget or null
     */
    public static GoldNuggetMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final GoldNuggetMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public GoldNuggetMat[] types()
    {
        return GoldNuggetMat.goldNuggetTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static GoldNuggetMat[] goldNuggetTypes()
    {
        return byID.values(new GoldNuggetMat[byID.size()]);
    }

    static
    {
        GoldNuggetMat.register(GOLD_NUGGET);
    }
}

