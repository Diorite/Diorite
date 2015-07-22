package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class MelonMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final MelonMat MELON = new MelonMat();

    private static final Map<String, MelonMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<MelonMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected MelonMat()
    {
        super("MELON", 360, "minecraft:melon", "MELON", (short) 0x00, 2, 1.2F);
    }

    protected MelonMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected MelonMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public MelonMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public MelonMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Melon sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Melon or null
     */
    public static MelonMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Melon sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Melon or null
     */
    public static MelonMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MelonMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public MelonMat[] types()
    {
        return MelonMat.melonTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static MelonMat[] melonTypes()
    {
        return byID.values(new MelonMat[byID.size()]);
    }

    static
    {
        MelonMat.register(MELON);
    }
}

