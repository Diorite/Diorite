package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class MuttonMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final MuttonMat MUTTON = new MuttonMat();

    private static final Map<String, MuttonMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<MuttonMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected MuttonMat()
    {
        super("MUTTON", 423, "minecraft:mutton", "MUTTON", (short) 0x00, 2, 1.2F);
    }

    protected MuttonMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected MuttonMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public MuttonMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public MuttonMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Mutton sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Mutton or null
     */
    public static MuttonMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Mutton sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Mutton or null
     */
    public static MuttonMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MuttonMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public MuttonMat[] types()
    {
        return MuttonMat.muttonTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static MuttonMat[] muttonTypes()
    {
        return byID.values(new MuttonMat[byID.size()]);
    }

    static
    {
        MuttonMat.register(MUTTON);
    }
}

