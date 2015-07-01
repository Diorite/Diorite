package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.VariantMat;
import org.diorite.material.blocks.VariantableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Stone" and all its subtypes.
 */
public class StoneMat extends StonyMat implements VariantableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 7;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE__HARDNESS;

    public static final StoneMat STONE                   = new StoneMat();
    public static final StoneMat STONE_GRANITE           = new StoneMat("GRANITE", 0x01, VariantMat.CLASSIC);
    public static final StoneMat STONE_POLISHED_GRANITE  = new StoneMat("POLISHED_GRANITE", 0x02, VariantMat.SMOOTH);
    public static final StoneMat STONE_DIORITE           = new StoneMat("DIORITE", 0x03, VariantMat.CLASSIC); // <3
    public static final StoneMat STONE_POLISHED_DIORITE  = new StoneMat("POLISHED_DIORITE", 0x04, VariantMat.SMOOTH); // <3
    public static final StoneMat STONE_ANDESITE          = new StoneMat("ANDESITE", 0x05, VariantMat.CLASSIC);
    public static final StoneMat STONE_POLISHED_ANDESITE = new StoneMat("POLISHED_ANDESITE", 0x06, VariantMat.SMOOTH);

    private static final Map<String, StoneMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StoneMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final VariantMat variant;

    protected StoneMat()
    {
        super("STONE", 1, "minecraft:stone", "STONE", (byte) 0x00);
        this.variant = VariantMat.CLASSIC;
    }

    protected StoneMat(final String enumName, final int type, final VariantMat variant)
    {
        super(STONE.name(), STONE.ordinal(), STONE.getMinecraftId(), enumName, (byte) type);
        this.variant = variant;
    }

    protected StoneMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final VariantMat variant)
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
    public StoneMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StoneMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public VariantMat getVariant()
    {
        return this.variant;
    }

    @Override
    public StoneMat getVariant(final VariantMat variant)
    {
        switch (variant)
        {
            case SMOOTH:
                if (this.variant == VariantMat.SMOOTH)
                {
                    return this;
                }
                if (this.equals(STONE_DIORITE))
                {
                    return STONE_POLISHED_DIORITE;
                }
                if (this.equals(STONE_GRANITE))
                {
                    return STONE_POLISHED_GRANITE;
                }
                if (this.equals(STONE_ANDESITE))
                {
                    return STONE_POLISHED_ANDESITE;
                }
                return STONE;
            case CLASSIC:
            default:
                if (this.variant == VariantMat.CLASSIC)
                {
                    return STONE;
                }
                if (this.equals(STONE_POLISHED_DIORITE))
                {
                    return STONE_DIORITE;
                }
                if (this.equals(STONE_POLISHED_GRANITE))
                {
                    return STONE_GRANITE;
                }
                if (this.equals(STONE_POLISHED_ANDESITE))
                {
                    return STONE_ANDESITE;
                }
                return STONE;
        }
    }

    /**
     * Returns one of Stone sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Stone or null
     */
    public static StoneMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Stone sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Stone or null
     */
    public static StoneMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StoneMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public StoneMat[] types()
    {
        return StoneMat.stoneTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StoneMat[] stoneTypes()
    {
        return byID.values(new StoneMat[byID.size()]);
    }

    static
    {
        StoneMat.register(STONE);
        StoneMat.register(STONE_GRANITE);
        StoneMat.register(STONE_POLISHED_GRANITE);
        StoneMat.register(STONE_DIORITE);
        StoneMat.register(STONE_POLISHED_DIORITE);
        StoneMat.register(STONE_ANDESITE);
        StoneMat.register(STONE_POLISHED_ANDESITE);
    }
}
