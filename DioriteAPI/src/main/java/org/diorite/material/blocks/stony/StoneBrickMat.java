package org.diorite.material.blocks.stony;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.VariantMat;
import org.diorite.material.blocks.VariantableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StoneBrick" and all its subtypes.
 */
public class StoneBrickMat extends StonyMat implements VariantableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 4;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE_BRICK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE_BRICK__HARDNESS;

    public static final StoneBrickMat STONE_BRICK          = new StoneBrickMat();
    public static final StoneBrickMat STONE_BRICK_MOSSY    = new StoneBrickMat(0x1, VariantMat.MOSSY);
    public static final StoneBrickMat STONE_BRICK_CRACKED  = new StoneBrickMat(0x2, VariantMat.CRACKED);
    public static final StoneBrickMat STONE_BRICK_CHISELED = new StoneBrickMat(0x3, VariantMat.CHISELED);

    private static final Map<String, StoneBrickMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StoneBrickMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final VariantMat variant;

    @SuppressWarnings("MagicNumber")
    protected StoneBrickMat()
    {
        super("STONE_BRICK", 98, "minecraft:stonebrick", "CLASSIC", (byte) 0x00);
        this.variant = VariantMat.CLASSIC;
    }

    protected StoneBrickMat(final int type, final VariantMat varaint)
    {
        super(STONE_BRICK.name(), STONE_BRICK.ordinal(), STONE_BRICK.getMinecraftId(), varaint.name(), (byte) type);
        this.variant = varaint;
    }

    protected StoneBrickMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final VariantMat variant)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.variant = variant;
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
    public StoneBrickMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StoneBrickMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public VariantMat getVariant()
    {
        return this.variant;
    }

    @Override
    public StoneBrickMat getVariant(final VariantMat variant)
    {
        for (final StoneBrickMat mat : byName.values())
        {
            if (mat.variant == variant)
            {
                return mat;
            }
        }
        return STONE_BRICK;
    }

    /**
     * Returns one of StoneBrick sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StoneBrick or null
     */
    public static StoneBrickMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StoneBrick sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StoneBrick or null
     */
    public static StoneBrickMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StoneBrickMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public StoneBrickMat[] types()
    {
        return StoneBrickMat.stoneBrickTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StoneBrickMat[] stoneBrickTypes()
    {
        return byID.values(new StoneBrickMat[byID.size()]);
    }

    static
    {
        StoneBrickMat.register(STONE_BRICK);
        StoneBrickMat.register(STONE_BRICK_MOSSY);
        StoneBrickMat.register(STONE_BRICK_CRACKED);
        StoneBrickMat.register(STONE_BRICK_CHISELED);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("variant", this.variant).toString();
    }
}
