package org.diorite.material.blocks.others;

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
 * Class representing block "StainedGlass" and all its subtypes.
 */
public class StainedGlass extends BlockMaterialData implements Colorable
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STAINED_GLASS__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STAINED_GLASS__HARDNESS;

    public static final StainedGlass STAINED_GLASS_WHITE      = new StainedGlass();
    public static final StainedGlass STAINED_GLASS_ORANGE     = new StainedGlass(DyeColor.ORANGE);
    public static final StainedGlass STAINED_GLASS_MAGENTA    = new StainedGlass(DyeColor.MAGENTA);
    public static final StainedGlass STAINED_GLASS_LIGHT_BLUE = new StainedGlass(DyeColor.LIGHT_BLUE);
    public static final StainedGlass STAINED_GLASS_YELLOW     = new StainedGlass(DyeColor.YELLOW);
    public static final StainedGlass STAINED_GLASS_LIME       = new StainedGlass(DyeColor.LIME);
    public static final StainedGlass STAINED_GLASS_PINK       = new StainedGlass(DyeColor.PINK);
    public static final StainedGlass STAINED_GLASS_GRAY       = new StainedGlass(DyeColor.GRAY);
    public static final StainedGlass STAINED_GLASS_SILVER     = new StainedGlass(DyeColor.SILVER);
    public static final StainedGlass STAINED_GLASS_CYAN       = new StainedGlass(DyeColor.CYAN);
    public static final StainedGlass STAINED_GLASS_PURPLE     = new StainedGlass(DyeColor.PURPLE);
    public static final StainedGlass STAINED_GLASS_BLUE       = new StainedGlass(DyeColor.BLUE);
    public static final StainedGlass STAINED_GLASS_BROWN      = new StainedGlass(DyeColor.BROWN);
    public static final StainedGlass STAINED_GLASS_GREEN      = new StainedGlass(DyeColor.GREEN);
    public static final StainedGlass STAINED_GLASS_RED        = new StainedGlass(DyeColor.RED);
    public static final StainedGlass STAINED_GLASS_BLACK      = new StainedGlass(DyeColor.BLACK);

    private static final Map<String, StainedGlass>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StainedGlass> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected StainedGlass()
    {
        super("STAINED_GLASS", 95, "minecraft:stained_glass", "WHITE", (byte) 0x00);
        this.color = DyeColor.WHITE;
    }

    public StainedGlass(final DyeColor color)
    {
        super(STAINED_GLASS_WHITE.name(), STAINED_GLASS_WHITE.getId(), STAINED_GLASS_WHITE.getMinecraftId(), color.name(), color.getBlockFlag());
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
    public StainedGlass getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StainedGlass getType(final int id)
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
    public StainedGlass getColor(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Returns one of StainedGlass sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StainedGlass or null
     */
    public static StainedGlass getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StainedGlass sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StainedGlass or null
     */
    public static StainedGlass getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of StainedGlass sub-type based on {@link DyeColor}.
     * It will never return null;
     *
     * @param color color of StainedGlass
     *
     * @return sub-type of StainedGlass
     */
    public static StainedGlass getStainedGlass(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StainedGlass element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StainedGlass.register(STAINED_GLASS_WHITE);
        StainedGlass.register(STAINED_GLASS_ORANGE);
        StainedGlass.register(STAINED_GLASS_MAGENTA);
        StainedGlass.register(STAINED_GLASS_LIGHT_BLUE);
        StainedGlass.register(STAINED_GLASS_YELLOW);
        StainedGlass.register(STAINED_GLASS_LIME);
        StainedGlass.register(STAINED_GLASS_PINK);
        StainedGlass.register(STAINED_GLASS_GRAY);
        StainedGlass.register(STAINED_GLASS_SILVER);
        StainedGlass.register(STAINED_GLASS_CYAN);
        StainedGlass.register(STAINED_GLASS_PURPLE);
        StainedGlass.register(STAINED_GLASS_BLUE);
        StainedGlass.register(STAINED_GLASS_BROWN);
        StainedGlass.register(STAINED_GLASS_GREEN);
        StainedGlass.register(STAINED_GLASS_RED);
        StainedGlass.register(STAINED_GLASS_BLACK);
    }
}
