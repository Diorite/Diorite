package org.diorite.material.blocks.stony;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.DyeColor;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Colorable;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StainedHardenedClay" and all its subtypes.
 */
public class StainedHardenedClay extends BlockMaterialData implements Colorable
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

    public static final StainedHardenedClay STAINED_HARDENED_CLAY_WHITE      = new StainedHardenedClay();
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_ORANGE     = new StainedHardenedClay(DyeColor.ORANGE);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_MAGENTA    = new StainedHardenedClay(DyeColor.MAGENTA);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_LIGHT_BLUE = new StainedHardenedClay(DyeColor.LIGHT_BLUE);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_YELLOW     = new StainedHardenedClay(DyeColor.YELLOW);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_LIME       = new StainedHardenedClay(DyeColor.LIME);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_PINK       = new StainedHardenedClay(DyeColor.PINK);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_GRAY       = new StainedHardenedClay(DyeColor.GRAY);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_SILVER     = new StainedHardenedClay(DyeColor.SILVER);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_CYAN       = new StainedHardenedClay(DyeColor.CYAN);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_PURPLE     = new StainedHardenedClay(DyeColor.PURPLE);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_BLUE       = new StainedHardenedClay(DyeColor.BLUE);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_BROWN      = new StainedHardenedClay(DyeColor.BROWN);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_GREEN      = new StainedHardenedClay(DyeColor.GREEN);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_RED        = new StainedHardenedClay(DyeColor.RED);
    public static final StainedHardenedClay STAINED_HARDENED_CLAY_BLACK      = new StainedHardenedClay(DyeColor.BLACK);

    private static final Map<String, StainedHardenedClay>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StainedHardenedClay> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected StainedHardenedClay()
    {
        super("STAINED_HARDENED_CLAY", 159, "minecraft:stained_hardened_clay", "WHITE", (byte) 0x00);
        this.color = DyeColor.WHITE;
    }

    public StainedHardenedClay(final DyeColor color)
    {
        super(STAINED_HARDENED_CLAY_WHITE.name(), STAINED_HARDENED_CLAY_WHITE.getId(), STAINED_HARDENED_CLAY_WHITE.getMinecraftId(), color.name(), color.getBlockFlag());
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
    public StainedHardenedClay getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StainedHardenedClay getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DyeColor getColor()
    {
        return this.color;
    }

    @Override
    public StainedHardenedClay getColor(final DyeColor color)
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
    public static StainedHardenedClay getByID(final int id)
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
    public static StainedHardenedClay getByEnumName(final String name)
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
    public static StainedHardenedClay getStainedHardenedClay(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StainedHardenedClay element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_WHITE);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_ORANGE);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_MAGENTA);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_LIGHT_BLUE);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_YELLOW);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_LIME);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_PINK);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_GRAY);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_SILVER);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_CYAN);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_PURPLE);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_BLUE);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_BROWN);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_GREEN);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_RED);
        StainedHardenedClay.register(STAINED_HARDENED_CLAY_BLACK);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("color", this.color).toString();
    }
}
