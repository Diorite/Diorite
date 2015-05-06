package org.diorite.material.blocks.earth;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.VariantMat;
import org.diorite.material.blocks.VariantableMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Dirt" and all its subtypes.
 */
public class DirtMat extends EarthMat implements VariantableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 3;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DIRT__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DIRT__HARDNESS;

    public static final DirtMat DIRT        = new DirtMat();
    public static final DirtMat DIRT_COARSE = new DirtMat(0x1, VariantMat.COARSE);
    public static final DirtMat DIRT_PODZOL = new DirtMat(0x2, VariantMat.PODZOL);

    private static final Map<String, DirtMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DirtMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final VariantMat variant;

    protected DirtMat()
    {
        super("DIRT", 3, "minecraft:dirt", "CLASSIC", (byte) 0x00);
        this.variant = VariantMat.CLASSIC;
    }

    protected DirtMat(final int type, final VariantMat variant)
    {
        super(DIRT.name(), DIRT.getId(), DIRT.getMinecraftId(), variant.name(), (byte) type);
        this.variant = variant;
    }

    protected DirtMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final VariantMat variant)
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
    public DirtMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DirtMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public VariantMat getVariant()
    {
        return this.variant;
    }

    @Override
    public DirtMat getVariant(final VariantMat variant)
    {
        for (final DirtMat mat : byName.values())
        {
            if (mat.variant == variant)
            {
                return mat;
            }
        }
        return DIRT;
    }

    /**
     * Returns one of Dirt sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Dirt or null
     */
    public static DirtMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Dirt sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Dirt or null
     */
    public static DirtMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DirtMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DirtMat.register(DIRT);
        DirtMat.register(DIRT_COARSE);
        DirtMat.register(DIRT_PODZOL);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("variant", this.variant).toString();
    }
}
