package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SlimeBlock" and all its subtypes.
 */
public class SlimeBlockMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final SlimeBlockMat SLIME_BLOCK = new SlimeBlockMat();

    private static final Map<String, SlimeBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SlimeBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected SlimeBlockMat()
    {
        super("SLIME_BLOCK", 165, "minecraft:slime", "SLIME_BLOCK", (byte) 0x00, 0, 0);
    }

    protected SlimeBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public SlimeBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SlimeBlockMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of SlimeBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SlimeBlock or null
     */
    public static SlimeBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SlimeBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SlimeBlock or null
     */
    public static SlimeBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SlimeBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SlimeBlockMat[] types()
    {
        return SlimeBlockMat.slimeBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SlimeBlockMat[] slimeBlockTypes()
    {
        return byID.values(new SlimeBlockMat[byID.size()]);
    }

    static
    {
        SlimeBlockMat.register(SLIME_BLOCK);
    }
}
