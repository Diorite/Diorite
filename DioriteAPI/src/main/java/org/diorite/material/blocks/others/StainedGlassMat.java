package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.DyeColor;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.ColorableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StainedGlass" and all its subtypes.
 */
public class StainedGlassMat extends BlockMaterialData implements ColorableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final StainedGlassMat STAINED_GLASS_WHITE      = new StainedGlassMat();
    public static final StainedGlassMat STAINED_GLASS_ORANGE     = new StainedGlassMat(DyeColor.ORANGE);
    public static final StainedGlassMat STAINED_GLASS_MAGENTA    = new StainedGlassMat(DyeColor.MAGENTA);
    public static final StainedGlassMat STAINED_GLASS_LIGHT_BLUE = new StainedGlassMat(DyeColor.LIGHT_BLUE);
    public static final StainedGlassMat STAINED_GLASS_YELLOW     = new StainedGlassMat(DyeColor.YELLOW);
    public static final StainedGlassMat STAINED_GLASS_LIME       = new StainedGlassMat(DyeColor.LIME);
    public static final StainedGlassMat STAINED_GLASS_PINK       = new StainedGlassMat(DyeColor.PINK);
    public static final StainedGlassMat STAINED_GLASS_GRAY       = new StainedGlassMat(DyeColor.GRAY);
    public static final StainedGlassMat STAINED_GLASS_SILVER     = new StainedGlassMat(DyeColor.LIGHT_GRAY);
    public static final StainedGlassMat STAINED_GLASS_CYAN       = new StainedGlassMat(DyeColor.CYAN);
    public static final StainedGlassMat STAINED_GLASS_PURPLE     = new StainedGlassMat(DyeColor.PURPLE);
    public static final StainedGlassMat STAINED_GLASS_BLUE       = new StainedGlassMat(DyeColor.BLUE);
    public static final StainedGlassMat STAINED_GLASS_BROWN      = new StainedGlassMat(DyeColor.BROWN);
    public static final StainedGlassMat STAINED_GLASS_GREEN      = new StainedGlassMat(DyeColor.GREEN);
    public static final StainedGlassMat STAINED_GLASS_RED        = new StainedGlassMat(DyeColor.RED);
    public static final StainedGlassMat STAINED_GLASS_BLACK      = new StainedGlassMat(DyeColor.BLACK);

    private static final Map<String, StainedGlassMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StainedGlassMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected StainedGlassMat()
    {
        super("STAINED_GLASS", 95, "minecraft:stained_glass", "WHITE", (byte) 0x00, 0.3f, 1.5f);
        this.color = DyeColor.WHITE;
    }

    protected StainedGlassMat(final DyeColor color)
    {
        super(STAINED_GLASS_WHITE.name(), STAINED_GLASS_WHITE.ordinal(), STAINED_GLASS_WHITE.getMinecraftId(), color.name(), color.getBlockFlag(), STAINED_GLASS_WHITE.getHardness(), STAINED_GLASS_WHITE.getBlastResistance());
        this.color = color;
    }

    protected StainedGlassMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final DyeColor color, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.color = color;
    }

    @Override
    public StainedGlassMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StainedGlassMat getType(final int id)
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
    public StainedGlassMat getColor(final DyeColor color)
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
    public static StainedGlassMat getByID(final int id)
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
    public static StainedGlassMat getByEnumName(final String name)
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
    public static StainedGlassMat getStainedGlass(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StainedGlassMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public StainedGlassMat[] types()
    {
        return StainedGlassMat.stainedGlassTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StainedGlassMat[] stainedGlassTypes()
    {
        return byID.values(new StainedGlassMat[byID.size()]);
    }

    static
    {
        StainedGlassMat.register(STAINED_GLASS_WHITE);
        StainedGlassMat.register(STAINED_GLASS_ORANGE);
        StainedGlassMat.register(STAINED_GLASS_MAGENTA);
        StainedGlassMat.register(STAINED_GLASS_LIGHT_BLUE);
        StainedGlassMat.register(STAINED_GLASS_YELLOW);
        StainedGlassMat.register(STAINED_GLASS_LIME);
        StainedGlassMat.register(STAINED_GLASS_PINK);
        StainedGlassMat.register(STAINED_GLASS_GRAY);
        StainedGlassMat.register(STAINED_GLASS_SILVER);
        StainedGlassMat.register(STAINED_GLASS_CYAN);
        StainedGlassMat.register(STAINED_GLASS_PURPLE);
        StainedGlassMat.register(STAINED_GLASS_BLUE);
        StainedGlassMat.register(STAINED_GLASS_BROWN);
        StainedGlassMat.register(STAINED_GLASS_GREEN);
        StainedGlassMat.register(STAINED_GLASS_RED);
        StainedGlassMat.register(STAINED_GLASS_BLACK);
    }
}
