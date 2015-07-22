package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.material.blocks.VariantMat;
import org.diorite.material.blocks.VariantableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Cobblestone" and all its subtypes.
 */
public class CobblestoneMat extends StonyMat implements VariantableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final CobblestoneMat COBBLESTONE = new CobblestoneMat();

    private static final Map<String, CobblestoneMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CobblestoneMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected CobblestoneMat()
    {
        super("COBBLESTONE", 4, "minecraft:cobblestone", "COBBLESTONE", (byte) 0x00, 2, 30);
    }

    protected CobblestoneMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public CobblestoneMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CobblestoneMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public VariantMat getVariant()
    {
        return VariantMat.CLASSIC;
    }

    @Override
    public VariantableMat getVariant(final VariantMat variant)
    {
        if (variant == VariantMat.MOSSY)
        {
            return MossyCobblestoneMat.MOSSY_COBBLESTONE;
        }
        return this;
    }

    /**
     * Returns one of Cobblestone sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Cobblestone or null
     */
    public static CobblestoneMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Cobblestone sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Cobblestone or null
     */
    public static CobblestoneMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CobblestoneMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CobblestoneMat[] types()
    {
        return CobblestoneMat.cobblestoneTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CobblestoneMat[] cobblestoneTypes()
    {
        return byID.values(new CobblestoneMat[byID.size()]);
    }

    static
    {
        CobblestoneMat.register(COBBLESTONE);
    }
}
