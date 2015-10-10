package org.diorite.material.blocks.tools;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Barrier' block material in minecraft. <br>
 * ID of block: 166 <br>
 * String ID of block: minecraft:barrier <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000004
 */
@SuppressWarnings("JavaDoc")
public class BarrierMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final BarrierMat BARRIER = new BarrierMat();

    private static final Map<String, BarrierMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BarrierMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected BarrierMat()
    {
        super("BARRIER", 166, "minecraft:barrier", "BARRIER", (byte) 0x00, - 1, 18_000_003);
    }

    protected BarrierMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public BarrierMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BarrierMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Barrier sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Barrier or null
     */
    public static BarrierMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Barrier sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Barrier or null
     */
    public static BarrierMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BarrierMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BarrierMat[] types()
    {
        return BarrierMat.barrierTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BarrierMat[] barrierTypes()
    {
        return byID.values(new BarrierMat[byID.size()]);
    }

    static
    {
        BarrierMat.register(BARRIER);
    }
}
