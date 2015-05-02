package org.diorite.material.blocks.stony.oreblocks;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.Material;
import org.diorite.material.blocks.VariantMat;
import org.diorite.material.blocks.VariantableMat;
import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "QuartzBlock" and all its subtypes.
 */
public class QuartzBlockMat extends OreBlockMat implements VariantableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 5;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__QUARTZ_BLOCK__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__QUARTZ_BLOCK__HARDNESS;

    public static final QuartzBlockMat QUARTZ_BLOCK                    = new QuartzBlockMat();
    public static final QuartzBlockMat QUARTZ_BLOCK_CHISELED           = new QuartzBlockMat(0x1, VariantMat.CHISELED);
    public static final QuartzBlockMat QUARTZ_BLOCK_PILLAR_VERTICAL    = new QuartzBlockMat(0x2, VariantMat.PILLAR_VERTICAL);
    public static final QuartzBlockMat QUARTZ_BLOCK_PILLAR_NORTH_SOUTH = new QuartzBlockMat(0x3, VariantMat.PILLAR_NORTH_SOUTH);
    public static final QuartzBlockMat QUARTZ_BLOCK_PILLAR_EAST_WEST   = new QuartzBlockMat(0x4, VariantMat.PILLAR_EAST_WEST);

    private static final Map<String, QuartzBlockMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<QuartzBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final VariantMat variant;

    @SuppressWarnings("MagicNumber")
    protected QuartzBlockMat()
    {
        super("QUARTZ_BLOCK", 155, "minecraft:quartz_block", "QUARTZ_BLOCK", (byte) 0x00, Material.QUARTZ_ORE);
        this.variant = VariantMat.CLASSIC;
    }

    protected QuartzBlockMat(final int type, final VariantMat variant)
    {
        super(QUARTZ_BLOCK.name(), QUARTZ_BLOCK.getId(), QUARTZ_BLOCK.getMinecraftId(), variant.name(), (byte) type, QUARTZ_BLOCK.getOre());
        this.variant = variant;
    }

    protected QuartzBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreMat ore, final VariantMat variant)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, ore);
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
    public QuartzBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public QuartzBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public VariantMat getVariant()
    {
        return this.variant;
    }

    @Override
    public QuartzBlockMat getVariant(final VariantMat variant)
    {
        for (final QuartzBlockMat mat : byName.values())
        {
            if (mat.variant == variant)
            {
                return mat;
            }
        }
        return QUARTZ_BLOCK;
    }

    /**
     * Returns one of QuartzBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of QuartzBlock or null
     */
    public static QuartzBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of QuartzBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of QuartzBlock or null
     */
    public static QuartzBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final QuartzBlockMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        QuartzBlockMat.register(QUARTZ_BLOCK);
        QuartzBlockMat.register(QUARTZ_BLOCK_CHISELED);
        QuartzBlockMat.register(QUARTZ_BLOCK_PILLAR_VERTICAL);
        QuartzBlockMat.register(QUARTZ_BLOCK_PILLAR_NORTH_SOUTH);
        QuartzBlockMat.register(QUARTZ_BLOCK_PILLAR_EAST_WEST);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("variant", this.variant).toString();
    }
}
