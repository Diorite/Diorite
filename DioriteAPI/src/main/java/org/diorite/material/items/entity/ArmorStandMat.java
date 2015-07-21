package org.diorite.material.items.entity;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.PlaceableEntityMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class ArmorStandMat extends ItemMaterialData implements PlaceableEntityMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final ArmorStandMat ARMOR_STAND = new ArmorStandMat();

    private static final Map<String, ArmorStandMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<ArmorStandMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected ArmorStandMat()
    {
        super("ARMOR_STAND", 416, "minecraft:armor_stand", "ARMOR_STAND", (short) 0x00);
    }

    protected ArmorStandMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected ArmorStandMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public ArmorStandMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public ArmorStandMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of ArmorStand sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of ArmorStand or null
     */
    public static ArmorStandMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of ArmorStand sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of ArmorStand or null
     */
    public static ArmorStandMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final ArmorStandMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public ArmorStandMat[] types()
    {
        return ArmorStandMat.armorStandTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static ArmorStandMat[] armorStandTypes()
    {
        return byID.values(new ArmorStandMat[byID.size()]);
    }

    static
    {
        ArmorStandMat.register(ARMOR_STAND);
    }
}

