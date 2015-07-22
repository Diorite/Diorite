package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.DyeColor;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.ColorableMat;
import org.diorite.material.blocks.FenceMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StainedGlassPane" and all its subtypes.
 */
public class StainedGlassPaneMat extends BlockMaterialData implements ColorableMat, FenceMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final StainedGlassPaneMat STAINED_GLASS_PANE_WHITE      = new StainedGlassPaneMat();
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_ORANGE     = new StainedGlassPaneMat(DyeColor.ORANGE);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_MAGENTA    = new StainedGlassPaneMat(DyeColor.MAGENTA);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_LIGHT_BLUE = new StainedGlassPaneMat(DyeColor.LIGHT_BLUE);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_YELLOW     = new StainedGlassPaneMat(DyeColor.YELLOW);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_LIME       = new StainedGlassPaneMat(DyeColor.LIME);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_PINK       = new StainedGlassPaneMat(DyeColor.PINK);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_GRAY       = new StainedGlassPaneMat(DyeColor.GRAY);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_SILVER     = new StainedGlassPaneMat(DyeColor.LIGHT_GRAY);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_CYAN       = new StainedGlassPaneMat(DyeColor.CYAN);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_PURPLE     = new StainedGlassPaneMat(DyeColor.PURPLE);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_BLUE       = new StainedGlassPaneMat(DyeColor.BLUE);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_BROWN      = new StainedGlassPaneMat(DyeColor.BROWN);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_GREEN      = new StainedGlassPaneMat(DyeColor.GREEN);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_RED        = new StainedGlassPaneMat(DyeColor.RED);
    public static final StainedGlassPaneMat STAINED_GLASS_PANE_BLACK      = new StainedGlassPaneMat(DyeColor.BLACK);

    private static final Map<String, StainedGlassPaneMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StainedGlassPaneMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected StainedGlassPaneMat()
    {
        super("STAINED_GLASS_PANE", 160, "minecraft:stained_glass_pane", "WHITE", (byte) 0x00, 0.3f, 1.5f);
        this.color = DyeColor.WHITE;
    }

    protected StainedGlassPaneMat(final DyeColor color)
    {
        super(STAINED_GLASS_PANE_WHITE.name(), STAINED_GLASS_PANE_WHITE.ordinal(), STAINED_GLASS_PANE_WHITE.getMinecraftId(), color.name(), color.getBlockFlag(), STAINED_GLASS_PANE_WHITE.getHardness(), STAINED_GLASS_PANE_WHITE.getBlastResistance());
        this.color = color;
    }

    protected StainedGlassPaneMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final DyeColor color, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.color = color;
    }

    @Override
    public StainedGlassPaneMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StainedGlassPaneMat getType(final int id)
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
    public StainedGlassPaneMat getColor(final DyeColor color)
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
    public static StainedGlassPaneMat getByID(final int id)
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
    public static StainedGlassPaneMat getByEnumName(final String name)
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
    public static StainedGlassPaneMat getStainedGlassPane(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StainedGlassPaneMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public StainedGlassPaneMat[] types()
    {
        return StainedGlassPaneMat.stainedGlassPaneTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StainedGlassPaneMat[] stainedGlassPaneTypes()
    {
        return byID.values(new StainedGlassPaneMat[byID.size()]);
    }

    static
    {
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_WHITE);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_ORANGE);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_MAGENTA);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_LIGHT_BLUE);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_YELLOW);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_LIME);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_PINK);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_GRAY);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_SILVER);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_CYAN);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_PURPLE);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_BLUE);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_BROWN);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_GREEN);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_RED);
        StainedGlassPaneMat.register(STAINED_GLASS_PANE_BLACK);
    }
}
