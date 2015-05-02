package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.VariantMat;
import org.diorite.material.blocks.VariantableMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "MossyCobblestone" and all its subtypes.
 */
public class MossyCobblestoneMat extends StonyMat implements VariantableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__MOSSY_COBBLESTONE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__MOSSY_COBBLESTONE__HARDNESS;

    public static final MossyCobblestoneMat MOSSY_COBBLESTONE = new MossyCobblestoneMat();

    private static final Map<String, MossyCobblestoneMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<MossyCobblestoneMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected MossyCobblestoneMat()
    {
        super("MOSSY_COBBLESTONE", 48, "minecraft:mossy_cobblestone", "MOSSY_COBBLESTONE", (byte) 0x00);
    }

    protected MossyCobblestoneMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public float getBlastResistance()
    {
        return BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return HARDNESS;
    }

    @Override
    public MossyCobblestoneMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public MossyCobblestoneMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public VariantMat getVariant()
    {
        return VariantMat.MOSSY;
    }

    @Override
    public VariantableMat getVariant(final VariantMat variant)
    {
        if (variant == VariantMat.MOSSY)
        {
            return this;
        }
        return CobblestoneMat.COBBLESTONE;
    }

    /**
     * Returns one of MossyCobblestone sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of MossyCobblestone or null
     */
    public static MossyCobblestoneMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of MossyCobblestone sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of MossyCobblestone or null
     */
    public static MossyCobblestoneMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MossyCobblestoneMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        MossyCobblestoneMat.register(MOSSY_COBBLESTONE);
    }
}
