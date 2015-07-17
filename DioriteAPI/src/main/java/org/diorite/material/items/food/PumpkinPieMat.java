package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.material.items.EdibleItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class PumpkinPieMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final PumpkinPieMat PUMPKIN_PIE = new PumpkinPieMat();

    private static final Map<String, PumpkinPieMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<PumpkinPieMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected PumpkinPieMat()
    {
        super("PUMPKIN_PIE", 400, "minecraft:pumpkin_pie", "PUMPKIN_PIE", (short) 0x00, 8, 4.8F);
    }

    protected PumpkinPieMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected PumpkinPieMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public PumpkinPieMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public PumpkinPieMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of PumpkinPie sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PumpkinPie or null
     */
    public static PumpkinPieMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of PumpkinPie sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PumpkinPie or null
     */
    public static PumpkinPieMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PumpkinPieMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PumpkinPieMat[] types()
    {
        return PumpkinPieMat.pumpkinPieTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PumpkinPieMat[] pumpkinPieTypes()
    {
        return byID.values(new PumpkinPieMat[byID.size()]);
    }

    static
    {
        PumpkinPieMat.register(PUMPKIN_PIE);
    }
}

