package org.diorite.material.blocks.others;

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
 * Class representing block "Wool" and all its subtypes.
 */
public class WoolMat extends BlockMaterialData implements ColorableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WOOL__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__WOOL__HARDNESS;

    public static final WoolMat WOOL_WHITE      = new WoolMat();
    public static final WoolMat WOOL_ORANGE     = new WoolMat(DyeColor.ORANGE);
    public static final WoolMat WOOL_MAGENTA    = new WoolMat(DyeColor.MAGENTA);
    public static final WoolMat WOOL_LIGHT_BLUE = new WoolMat(DyeColor.LIGHT_BLUE);
    public static final WoolMat WOOL_YELLOW     = new WoolMat(DyeColor.YELLOW);
    public static final WoolMat WOOL_LIME       = new WoolMat(DyeColor.LIME);
    public static final WoolMat WOOL_PINK       = new WoolMat(DyeColor.PINK);
    public static final WoolMat WOOL_GRAY       = new WoolMat(DyeColor.GRAY);
    public static final WoolMat WOOL_SILVER     = new WoolMat(DyeColor.SILVER);
    public static final WoolMat WOOL_CYAN       = new WoolMat(DyeColor.CYAN);
    public static final WoolMat WOOL_PURPLE     = new WoolMat(DyeColor.PURPLE);
    public static final WoolMat WOOL_BLUE       = new WoolMat(DyeColor.BLUE);
    public static final WoolMat WOOL_BROWN      = new WoolMat(DyeColor.BROWN);
    public static final WoolMat WOOL_GREEN      = new WoolMat(DyeColor.GREEN);
    public static final WoolMat WOOL_RED        = new WoolMat(DyeColor.RED);
    public static final WoolMat WOOL_BLACK      = new WoolMat(DyeColor.BLACK);

    private static final Map<String, WoolMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WoolMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected WoolMat()
    {
        super("WOOL", 35, "minecraft:wool", "WHITE", (byte) 0x00);
        this.color = DyeColor.WHITE;
    }

    protected WoolMat(final DyeColor color)
    {
        super(WOOL_WHITE.name(), WOOL_WHITE.getId(), WOOL_WHITE.getMinecraftId(), color.name(), color.getBlockFlag());
        this.color = color;
    }

    protected WoolMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final DyeColor color)
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
    public WoolMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WoolMat getType(final int id)
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
    public WoolMat getColor(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Returns one of Wool sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Wool or null
     */
    public static WoolMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Wool sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Wool or null
     */
    public static WoolMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Wool sub-type based on {@link DyeColor}.
     * It will never return null;
     *
     * @param color color of Wool
     *
     * @return sub-type of Wool
     */
    public static WoolMat getWool(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WoolMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public WoolMat[] types()
    {
        return WoolMat.woolTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WoolMat[] woolTypes()
    {
        return byID.values(new WoolMat[byID.size()]);
    }

    static
    {
        WoolMat.register(WOOL_WHITE);
        WoolMat.register(WOOL_ORANGE);
        WoolMat.register(WOOL_MAGENTA);
        WoolMat.register(WOOL_LIGHT_BLUE);
        WoolMat.register(WOOL_YELLOW);
        WoolMat.register(WOOL_LIME);
        WoolMat.register(WOOL_PINK);
        WoolMat.register(WOOL_GRAY);
        WoolMat.register(WOOL_SILVER);
        WoolMat.register(WOOL_CYAN);
        WoolMat.register(WOOL_PURPLE);
        WoolMat.register(WOOL_BLUE);
        WoolMat.register(WOOL_BROWN);
        WoolMat.register(WOOL_GREEN);
        WoolMat.register(WOOL_RED);
        WoolMat.register(WOOL_BLACK);
    }
}
