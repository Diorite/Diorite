package org.diorite.material.items.entity;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.PlaceableEntityMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class BoatMat extends ItemMaterialData implements PlaceableEntityMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final BoatMat BOAT = new BoatMat();

    private static final Map<String, BoatMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<BoatMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected BoatMat()
    {
        super("BOAT", 333, "minecraft:boat", "BOAT", (short) 0x00);
    }

    protected BoatMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected BoatMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public BoatMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public BoatMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Boat sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Boat or null
     */
    public static BoatMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Boat sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Boat or null
     */
    public static BoatMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BoatMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BoatMat[] types()
    {
        return BoatMat.boatTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BoatMat[] boatTypes()
    {
        return byID.values(new BoatMat[byID.size()]);
    }

    static
    {
        BoatMat.register(BOAT);
    }
}

