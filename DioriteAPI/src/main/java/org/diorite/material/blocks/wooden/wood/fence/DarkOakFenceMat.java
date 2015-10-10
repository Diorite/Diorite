package org.diorite.material.blocks.wooden.wood.fence;

import java.util.Map;

import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Dark Oak Fence' block material in minecraft. <br>
 * ID of block: 191 <br>
 * String ID of block: minecraft:dark_oak_fence <br>
 * Hardness: 2 <br>
 * Blast Resistance 15
 */
@SuppressWarnings("JavaDoc")
public class DarkOakFenceMat extends WoodenFenceMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DarkOakFenceMat DARK_OAK_FENCE = new DarkOakFenceMat();

    private static final Map<String, DarkOakFenceMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DarkOakFenceMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DarkOakFenceMat()
    {
        super("DARK_OAK_FENCE", 191, "minecraft:dark_oak_fence", "DARK_OAK_FENCE", WoodType.DARK_OAK, 2, 15);
    }

    protected DarkOakFenceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
    }

    @Override
    public DarkOakFenceMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DarkOakFenceMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DarkOakFence sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DarkOakFence or null
     */
    public static DarkOakFenceMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DarkOakFence sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DarkOakFence or null
     */
    public static DarkOakFenceMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DarkOakFenceMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DarkOakFenceMat[] types()
    {
        return DarkOakFenceMat.darkOakFenceTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DarkOakFenceMat[] darkOakFenceTypes()
    {
        return byID.values(new DarkOakFenceMat[byID.size()]);
    }

    static
    {
        DarkOakFenceMat.register(DARK_OAK_FENCE);
    }
}
