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
 * Class representing block "StainedGlassPane" and all its subtypes.
 */
public class StainedGlassPane extends BlockMaterialData implements Colorable
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STAINED_GLASS_PANE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STAINED_GLASS_PANE__HARDNESS;

    public static final StainedGlassPane STAINED_GLASS_PANE_WHITE      = new StainedGlassPane();
    public static final StainedGlassPane STAINED_GLASS_PANE_ORANGE     = new StainedGlassPane(DyeColor.ORANGE);
    public static final StainedGlassPane STAINED_GLASS_PANE_MAGENTA    = new StainedGlassPane(DyeColor.MAGENTA);
    public static final StainedGlassPane STAINED_GLASS_PANE_LIGHT_BLUE = new StainedGlassPane(DyeColor.LIGHT_BLUE);
    public static final StainedGlassPane STAINED_GLASS_PANE_YELLOW     = new StainedGlassPane(DyeColor.YELLOW);
    public static final StainedGlassPane STAINED_GLASS_PANE_LIME       = new StainedGlassPane(DyeColor.LIME);
    public static final StainedGlassPane STAINED_GLASS_PANE_PINK       = new StainedGlassPane(DyeColor.PINK);
    public static final StainedGlassPane STAINED_GLASS_PANE_GRAY       = new StainedGlassPane(DyeColor.GRAY);
    public static final StainedGlassPane STAINED_GLASS_PANE_SILVER     = new StainedGlassPane(DyeColor.SILVER);
    public static final StainedGlassPane STAINED_GLASS_PANE_CYAN       = new StainedGlassPane(DyeColor.CYAN);
    public static final StainedGlassPane STAINED_GLASS_PANE_PURPLE     = new StainedGlassPane(DyeColor.PURPLE);
    public static final StainedGlassPane STAINED_GLASS_PANE_BLUE       = new StainedGlassPane(DyeColor.BLUE);
    public static final StainedGlassPane STAINED_GLASS_PANE_BROWN      = new StainedGlassPane(DyeColor.BROWN);
    public static final StainedGlassPane STAINED_GLASS_PANE_GREEN      = new StainedGlassPane(DyeColor.GREEN);
    public static final StainedGlassPane STAINED_GLASS_PANE_RED        = new StainedGlassPane(DyeColor.RED);
    public static final StainedGlassPane STAINED_GLASS_PANE_BLACK      = new StainedGlassPane(DyeColor.BLACK);

    private static final Map<String, StainedGlassPane>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StainedGlassPane> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected StainedGlassPane()
    {
        super("STAINED_GLASS_PANE", 160, "minecraft:stained_glass_pane", "WHITE", (byte) 0x00);
        this.color = DyeColor.WHITE;
    }

    public StainedGlassPane(final DyeColor color)
    {
        super(STAINED_GLASS_PANE_WHITE.name(), STAINED_GLASS_PANE_WHITE.getId(), STAINED_GLASS_PANE_WHITE.getMinecraftId(), color.name(), color.getBlockFlag());
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
    public StainedGlassPane getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StainedGlassPane getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DyeColor getColor()
    {
        return this.color;
    }

    @Override
    public StainedGlassPane getColor(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Returns one of StainedGlassPane sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StainedGlassPane or null
     */
    public static StainedGlassPane getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StainedGlassPane sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StainedGlassPane or null
     */
    public static StainedGlassPane getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of StainedGlassPane sub-type based on {@link DyeColor}.
     * It will never return null;
     *
     * @param color color of StainedGlassPane
     *
     * @return sub-type of StainedGlassPane
     */
    public static StainedGlassPane getStainedGlassPane(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StainedGlassPane element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StainedGlassPane.register(STAINED_GLASS_PANE_WHITE);
        StainedGlassPane.register(STAINED_GLASS_PANE_ORANGE);
        StainedGlassPane.register(STAINED_GLASS_PANE_MAGENTA);
        StainedGlassPane.register(STAINED_GLASS_PANE_LIGHT_BLUE);
        StainedGlassPane.register(STAINED_GLASS_PANE_YELLOW);
        StainedGlassPane.register(STAINED_GLASS_PANE_LIME);
        StainedGlassPane.register(STAINED_GLASS_PANE_PINK);
        StainedGlassPane.register(STAINED_GLASS_PANE_GRAY);
        StainedGlassPane.register(STAINED_GLASS_PANE_SILVER);
        StainedGlassPane.register(STAINED_GLASS_PANE_CYAN);
        StainedGlassPane.register(STAINED_GLASS_PANE_PURPLE);
        StainedGlassPane.register(STAINED_GLASS_PANE_BLUE);
        StainedGlassPane.register(STAINED_GLASS_PANE_BROWN);
        StainedGlassPane.register(STAINED_GLASS_PANE_GREEN);
        StainedGlassPane.register(STAINED_GLASS_PANE_RED);
        StainedGlassPane.register(STAINED_GLASS_PANE_BLACK);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("color", this.color).toString();
    }
}
