package org.diorite.material.blocks.earth;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "ClayBlock" and all its subtypes.
 */
public class ClayBlockMat extends EarthMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;

    public static final ClayBlockMat CLAY_BLOCK = new ClayBlockMat();

    private static final Map<String, ClayBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<ClayBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected ClayBlockMat()
    {
        super("CLAY_BLOCK", 82, "minecraft:clay", "CLAY_BLOCK", (byte) 0x00, 0.6f, 3);
    }

    protected ClayBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public ClayBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public ClayBlockMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of ClayBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of ClayBlock or null
     */
    public static ClayBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of ClayBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of ClayBlock or null
     */
    public static ClayBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final ClayBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public ClayBlockMat[] types()
    {
        return ClayBlockMat.clayBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static ClayBlockMat[] clayBlockTypes()
    {
        return byID.values(new ClayBlockMat[byID.size()]);
    }

    static
    {
        ClayBlockMat.register(CLAY_BLOCK);
    }
}
