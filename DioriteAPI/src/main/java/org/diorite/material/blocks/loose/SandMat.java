package org.diorite.material.blocks.loose;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Sand" and all its subtypes.
 */
public class SandMat extends LooseMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 2;

    public static final SandMat SAND     = new SandMat();
    public static final SandMat SAND_RED = new SandMat("RED", 0x01);

    private static final Map<String, SandMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SandMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected SandMat()
    {
        super("SAND", 12, "minecraft:sand", "SAND", (byte) 0x00, 0.5f, 2.5f);
    }

    protected SandMat(final String enumName, final int type)
    {
        super(SAND.name(), SAND.ordinal(), SAND.getMinecraftId(), enumName, (byte) type, SAND.getHardness(), SAND.getBlastResistance());
    }

    protected SandMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public SandMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SandMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Sand sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Sand or null
     */
    public static SandMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Sand sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Sand or null
     */
    public static SandMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SandMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SandMat[] types()
    {
        return SandMat.sandTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SandMat[] sandTypes()
    {
        return byID.values(new SandMat[byID.size()]);
    }

    static
    {
        SandMat.register(SAND);
        SandMat.register(SAND_RED);
    }
}
