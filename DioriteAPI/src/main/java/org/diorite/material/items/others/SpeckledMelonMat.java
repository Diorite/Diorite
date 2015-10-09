package org.diorite.material.items.others;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class SpeckledMelonMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final SpeckledMelonMat SPECKLED_MELON = new SpeckledMelonMat();

    private static final Map<String, SpeckledMelonMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<SpeckledMelonMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected SpeckledMelonMat()
    {
        super("SPECKLED_MELON", 382, "minecraft:speckled_melon", "SPECKLED_MELON", (short) 0x00);
    }

    protected SpeckledMelonMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected SpeckledMelonMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public SpeckledMelonMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public SpeckledMelonMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of SpeckledMelon sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SpeckledMelon or null
     */
    public static SpeckledMelonMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of SpeckledMelon sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SpeckledMelon or null
     */
    public static SpeckledMelonMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SpeckledMelonMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SpeckledMelonMat[] types()
    {
        return SpeckledMelonMat.speckledMelonTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static SpeckledMelonMat[] speckledMelonTypes()
    {
        return byID.values(new SpeckledMelonMat[byID.size()]);
    }

    static
    {
        SpeckledMelonMat.register(SPECKLED_MELON);
    }
}

