package org.diorite.material.blocks.stony;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.VariantMat;
import org.diorite.material.blocks.VariantableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedSandstone" and all its subtypes.
 */
public class RedSandstoneMat extends BlockMaterialData implements VariantableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 3;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__RED_SANDSTONE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__RED_SANDSTONE__HARDNESS;

    public static final RedSandstoneMat RED_SANDSTONE          = new RedSandstoneMat();
    public static final RedSandstoneMat RED_SANDSTONE_CHISELED = new RedSandstoneMat(0x1, VariantMat.CHISELED);
    public static final RedSandstoneMat RED_SANDSTONE_SMOOTH   = new RedSandstoneMat(0x2, VariantMat.SMOOTH);

    private static final Map<String, RedSandstoneMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedSandstoneMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final VariantMat variant;

    @SuppressWarnings("MagicNumber")
    protected RedSandstoneMat()
    {
        super("RED_SANDSTONE", 179, "minecraft:red_sandstone", "CLASSIC", (byte) 0x00);
        this.variant = VariantMat.CLASSIC;
    }

    protected RedSandstoneMat(final int type, final VariantMat variant)
    {
        super(RED_SANDSTONE.name(), RED_SANDSTONE.ordinal(), RED_SANDSTONE.getMinecraftId(), variant.name(), (byte) type);
        this.variant = variant;
    }

    protected RedSandstoneMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final VariantMat variant)
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
    public RedSandstoneMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedSandstoneMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public VariantMat getVariant()
    {
        return this.variant;
    }

    @Override
    public RedSandstoneMat getVariant(final VariantMat variant)
    {
        for (final RedSandstoneMat mat : byName.values())
        {
            if (mat.variant == variant)
            {
                return mat;
            }
        }
        return RED_SANDSTONE;
    }

    /**
     * Returns one of RedSandstone sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedSandstone or null
     */
    public static RedSandstoneMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedSandstone sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedSandstone or null
     */
    public static RedSandstoneMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedSandstoneMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedSandstoneMat[] types()
    {
        return RedSandstoneMat.redSandstoneTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedSandstoneMat[] redSandstoneTypes()
    {
        return byID.values(new RedSandstoneMat[byID.size()]);
    }

    static
    {
        RedSandstoneMat.register(RED_SANDSTONE);
        RedSandstoneMat.register(RED_SANDSTONE_CHISELED);
        RedSandstoneMat.register(RED_SANDSTONE_SMOOTH);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("variant", this.variant).toString();
    }
}
