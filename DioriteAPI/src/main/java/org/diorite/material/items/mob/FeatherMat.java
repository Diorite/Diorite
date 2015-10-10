package org.diorite.material.items.mob;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Feather' item material in minecraft. <br>
 * ID of material: 288 <br>
 * String ID of material: minecraft:feather <br>
 * Max item stack size: 64
 */
@SuppressWarnings("JavaDoc")
public class FeatherMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final FeatherMat FEATHER = new FeatherMat();

    private static final Map<String, FeatherMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<FeatherMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected FeatherMat()
    {
        super("FEATHER", 288, "minecraft:feather", "FEATHER", (short) 0x00);
    }

    protected FeatherMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected FeatherMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public FeatherMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public FeatherMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Feather sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Feather or null
     */
    public static FeatherMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Feather sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Feather or null
     */
    public static FeatherMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FeatherMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public FeatherMat[] types()
    {
        return FeatherMat.featherTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static FeatherMat[] featherTypes()
    {
        return byID.values(new FeatherMat[byID.size()]);
    }

    static
    {
        FeatherMat.register(FEATHER);
    }
}

