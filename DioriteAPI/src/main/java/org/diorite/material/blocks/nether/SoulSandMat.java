package org.diorite.material.blocks.nether;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SoulSand" and all its subtypes.
 */
public class SoulSandMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final SoulSandMat SOUL_SAND = new SoulSandMat();

    private static final Map<String, SoulSandMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SoulSandMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SoulSandMat()
    {
        super("SOUL_SAND", 88, "minecraft:soul_sand", "SOUL_SAND", (byte) 0x00, 0.5f, 2.5f);
    }

    protected SoulSandMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public SoulSandMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SoulSandMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of SoulSand sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SoulSand or null
     */
    public static SoulSandMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SoulSand sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SoulSand or null
     */
    public static SoulSandMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SoulSandMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SoulSandMat[] types()
    {
        return SoulSandMat.soulSandTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SoulSandMat[] soulSandTypes()
    {
        return byID.values(new SoulSandMat[byID.size()]);
    }

    static
    {
        SoulSandMat.register(SOUL_SAND);
    }
}
