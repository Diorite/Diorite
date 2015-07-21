package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "MelonBlock" and all its subtypes.
 */
public class MelonBlockMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final MelonBlockMat MELON_BLOCK = new MelonBlockMat();

    private static final Map<String, MelonBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<MelonBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected MelonBlockMat()
    {
        super("MELON_BLOCK", 103, "minecraft:melon_block", "MELON_BLOCK", (byte) 0x00, 1, 5);
    }

    protected MelonBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public MelonBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public MelonBlockMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of MelonBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of MelonBlock or null
     */
    public static MelonBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of MelonBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of MelonBlock or null
     */
    public static MelonBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MelonBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public MelonBlockMat[] types()
    {
        return MelonBlockMat.melonBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static MelonBlockMat[] melonBlockTypes()
    {
        return byID.values(new MelonBlockMat[byID.size()]);
    }

    static
    {
        MelonBlockMat.register(MELON_BLOCK);
    }
}
