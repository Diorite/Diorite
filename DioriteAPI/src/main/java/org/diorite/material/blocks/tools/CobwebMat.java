package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Cobweb" and all its subtypes.
 */
public class CobwebMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final CobwebMat COBWEB = new CobwebMat();

    private static final Map<String, CobwebMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CobwebMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected CobwebMat()
    {
        super("COBWEB", 30, "minecraft:web", "COBWEB", (byte) 0x00, 4f, 20f);
    }

    protected CobwebMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public CobwebMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CobwebMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Cobweb sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Cobweb or null
     */
    public static CobwebMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Cobweb sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Cobweb or null
     */
    public static CobwebMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CobwebMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CobwebMat[] types()
    {
        return CobwebMat.cobwebTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CobwebMat[] cobwebTypes()
    {
        return byID.values(new CobwebMat[byID.size()]);
    }

    static
    {
        CobwebMat.register(COBWEB);
    }
}
