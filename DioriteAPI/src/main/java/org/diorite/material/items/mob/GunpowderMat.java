package org.diorite.material.items.mob;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class GunpowderMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final GunpowderMat GUNPOWDER = new GunpowderMat();

    private static final Map<String, GunpowderMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<GunpowderMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected GunpowderMat()
    {
        super("GUNPOWDER", 289, "minecraft:gunpowder", "GUNPOWDER", (short) 0x00);
    }

    protected GunpowderMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected GunpowderMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public GunpowderMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public GunpowderMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Gunpowder sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Gunpowder or null
     */
    public static GunpowderMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Gunpowder sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Gunpowder or null
     */
    public static GunpowderMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final GunpowderMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public GunpowderMat[] types()
    {
        return GunpowderMat.gunpowderTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static GunpowderMat[] gunpowderTypes()
    {
        return byID.values(new GunpowderMat[byID.size()]);
    }

    static
    {
        GunpowderMat.register(GUNPOWDER);
    }
}

