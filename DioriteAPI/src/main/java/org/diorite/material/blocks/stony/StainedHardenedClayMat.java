package org.diorite.material.blocks.stony;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.DyeColor;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.ColorableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StainedHardenedClay" and all its subtypes.
 */
public class StainedHardenedClayMat extends BlockMaterialData implements ColorableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STAINED_HARDENED_CLAY__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STAINED_HARDENED_CLAY__HARDNESS;

    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_WHITE      = new StainedHardenedClayMat();
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_ORANGE     = new StainedHardenedClayMat(DyeColor.ORANGE);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_MAGENTA    = new StainedHardenedClayMat(DyeColor.MAGENTA);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_LIGHT_BLUE = new StainedHardenedClayMat(DyeColor.LIGHT_BLUE);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_YELLOW     = new StainedHardenedClayMat(DyeColor.YELLOW);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_LIME       = new StainedHardenedClayMat(DyeColor.LIME);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_PINK       = new StainedHardenedClayMat(DyeColor.PINK);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_GRAY       = new StainedHardenedClayMat(DyeColor.GRAY);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_SILVER     = new StainedHardenedClayMat(DyeColor.SILVER);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_CYAN       = new StainedHardenedClayMat(DyeColor.CYAN);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_PURPLE     = new StainedHardenedClayMat(DyeColor.PURPLE);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_BLUE       = new StainedHardenedClayMat(DyeColor.BLUE);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_BROWN      = new StainedHardenedClayMat(DyeColor.BROWN);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_GREEN      = new StainedHardenedClayMat(DyeColor.GREEN);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_RED        = new StainedHardenedClayMat(DyeColor.RED);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_BLACK      = new StainedHardenedClayMat(DyeColor.BLACK);

    private static final Map<String, StainedHardenedClayMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StainedHardenedClayMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected StainedHardenedClayMat()
    {
        super("STAINED_HARDENED_CLAY", 159, "minecraft:stained_hardened_clay", "WHITE", (byte) 0x00);
        this.color = DyeColor.WHITE;
    }

    protected StainedHardenedClayMat(final DyeColor color)
    {
        super(STAINED_HARDENED_CLAY_WHITE.name(), STAINED_HARDENED_CLAY_WHITE.getId(), STAINED_HARDENED_CLAY_WHITE.getMinecraftId(), color.name(), color.getBlockFlag());
        this.color = color;
    }

    protected StainedHardenedClayMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final DyeColor color)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.color = color;
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
    public StainedHardenedClayMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StainedHardenedClayMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("color", this.color).toString();
    }

    @Override
    public DyeColor getColor()
    {
        return this.color;
    }

    @Override
    public StainedHardenedClayMat getColor(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Returns one of StainedHardenedClay sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StainedHardenedClay or null
     */
    public static StainedHardenedClayMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StainedHardenedClay sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StainedHardenedClay or null
     */
    public static StainedHardenedClayMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of StainedHardenedClay sub-type based on {@link DyeColor}.
     * It will never return null;
     *
     * @param color color of StainedHardenedClay
     *
     * @return sub-type of StainedHardenedClay
     */
    public static StainedHardenedClayMat getStainedHardenedClay(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StainedHardenedClayMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_WHITE);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_ORANGE);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_MAGENTA);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_LIGHT_BLUE);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_YELLOW);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_LIME);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_PINK);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_GRAY);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_SILVER);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_CYAN);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_PURPLE);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_BLUE);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_BROWN);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_GREEN);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_RED);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_BLACK);
    }
}
