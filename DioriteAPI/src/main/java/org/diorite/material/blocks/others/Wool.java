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
 * Class representing block "Wool" and all its subtypes.
 */
public class Wool extends BlockMaterialData implements Colorable
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

    public static final Wool WOOL_WHITE      = new Wool();
    public static final Wool WOOL_ORANGE     = new Wool(DyeColor.ORANGE);
    public static final Wool WOOL_MAGENTA    = new Wool(DyeColor.MAGENTA);
    public static final Wool WOOL_LIGHT_BLUE = new Wool(DyeColor.LIGHT_BLUE);
    public static final Wool WOOL_YELLOW     = new Wool(DyeColor.YELLOW);
    public static final Wool WOOL_LIME       = new Wool(DyeColor.LIME);
    public static final Wool WOOL_PINK       = new Wool(DyeColor.PINK);
    public static final Wool WOOL_GRAY       = new Wool(DyeColor.GRAY);
    public static final Wool WOOL_SILVER     = new Wool(DyeColor.SILVER);
    public static final Wool WOOL_CYAN       = new Wool(DyeColor.CYAN);
    public static final Wool WOOL_PURPLE     = new Wool(DyeColor.PURPLE);
    public static final Wool WOOL_BLUE       = new Wool(DyeColor.BLUE);
    public static final Wool WOOL_BROWN      = new Wool(DyeColor.BROWN);
    public static final Wool WOOL_GREEN      = new Wool(DyeColor.GREEN);
    public static final Wool WOOL_RED        = new Wool(DyeColor.RED);
    public static final Wool WOOL_BLACK      = new Wool(DyeColor.BLACK);

    private static final Map<String, Wool>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Wool> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected Wool()
    {
        super("WOOL", 35, "minecraft:wool", "WHITE", (byte) 0x00);
        this.color = DyeColor.WHITE;
    }

    public Wool(final DyeColor color)
    {
        super(WOOL_WHITE.name(), WOOL_WHITE.getId(), WOOL_WHITE.getMinecraftId(), color.name(), color.getBlockFlag());
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
    public Wool getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Wool getType(final int id)
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
    public Wool getColor(final DyeColor color)
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
    public static Wool getByID(final int id)
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
    public static Wool getByEnumName(final String name)
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
    public static Wool getWool(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Wool element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Wool.register(WOOL_WHITE);
        Wool.register(WOOL_ORANGE);
        Wool.register(WOOL_MAGENTA);
        Wool.register(WOOL_LIGHT_BLUE);
        Wool.register(WOOL_YELLOW);
        Wool.register(WOOL_LIME);
        Wool.register(WOOL_PINK);
        Wool.register(WOOL_GRAY);
        Wool.register(WOOL_SILVER);
        Wool.register(WOOL_CYAN);
        Wool.register(WOOL_PURPLE);
        Wool.register(WOOL_BLUE);
        Wool.register(WOOL_BROWN);
        Wool.register(WOOL_GREEN);
        Wool.register(WOOL_RED);
        Wool.register(WOOL_BLACK);
    }
}
