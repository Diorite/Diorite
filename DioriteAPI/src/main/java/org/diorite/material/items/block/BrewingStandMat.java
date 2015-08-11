package org.diorite.material.items.block;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.PlaceableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class BrewingStandMat extends ItemMaterialData implements PlaceableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final BrewingStandMat BREWING_STAND = new BrewingStandMat();

    private static final Map<String, BrewingStandMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<BrewingStandMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected BrewingStandMat()
    {
        super("BREWING_STAND", 379, "minecraft:brewing_stand", "BREWING_STAND", (short) 0x00);
    }

    protected BrewingStandMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected BrewingStandMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public BrewingStandMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public BrewingStandMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of BrewingStand sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BrewingStand or null
     */
    public static BrewingStandMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of BrewingStand sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BrewingStand or null
     */
    public static BrewingStandMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BrewingStandMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BrewingStandMat[] types()
    {
        return BrewingStandMat.brewingStandTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BrewingStandMat[] brewingStandTypes()
    {
        return byID.values(new BrewingStandMat[byID.size()]);
    }

    static
    {
        BrewingStandMat.register(BREWING_STAND);
    }
}

