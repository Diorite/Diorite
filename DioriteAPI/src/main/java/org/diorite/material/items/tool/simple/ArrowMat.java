package org.diorite.material.items.tool.simple;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Arrow' item material in minecraft. <br>
 * ID of material: 262 <br>
 * String ID of material: minecraft:arrow <br>
 * Max item stack size: 64
 */
@SuppressWarnings("JavaDoc")
public class ArrowMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final ArrowMat ARROW = new ArrowMat();

    private static final Map<String, ArrowMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<ArrowMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected ArrowMat()
    {
        super("ARROW", 262, "minecraft:arrow", "ARROW", (short) 0x00);
    }

    protected ArrowMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected ArrowMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public ArrowMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public ArrowMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Arrow sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Arrow or null
     */
    public static ArrowMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Arrow sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Arrow or null
     */
    public static ArrowMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final ArrowMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public ArrowMat[] types()
    {
        return ArrowMat.arrowTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static ArrowMat[] arrowTypes()
    {
        return byID.values(new ArrowMat[byID.size()]);
    }

    static
    {
        ArrowMat.register(ARROW);
    }
}

