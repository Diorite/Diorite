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
 * Class representing block "Carpet" and all its subtypes.
 */
public class Carpet extends BlockMaterialData implements Colorable
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CARPET__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CARPET__HARDNESS;

    public static final Carpet CARPET_WHITE      = new Carpet();
    public static final Carpet CARPET_ORANGE     = new Carpet(DyeColor.ORANGE);
    public static final Carpet CARPET_MAGENTA    = new Carpet(DyeColor.MAGENTA);
    public static final Carpet CARPET_LIGHT_BLUE = new Carpet(DyeColor.LIGHT_BLUE);
    public static final Carpet CARPET_YELLOW     = new Carpet(DyeColor.YELLOW);
    public static final Carpet CARPET_LIME       = new Carpet(DyeColor.LIME);
    public static final Carpet CARPET_PINK       = new Carpet(DyeColor.PINK);
    public static final Carpet CARPET_GRAY       = new Carpet(DyeColor.GRAY);
    public static final Carpet CARPET_SILVER     = new Carpet(DyeColor.SILVER);
    public static final Carpet CARPET_CYAN       = new Carpet(DyeColor.CYAN);
    public static final Carpet CARPET_PURPLE     = new Carpet(DyeColor.PURPLE);
    public static final Carpet CARPET_BLUE       = new Carpet(DyeColor.BLUE);
    public static final Carpet CARPET_BROWN      = new Carpet(DyeColor.BROWN);
    public static final Carpet CARPET_GREEN      = new Carpet(DyeColor.GREEN);
    public static final Carpet CARPET_RED        = new Carpet(DyeColor.RED);
    public static final Carpet CARPET_BLACK      = new Carpet(DyeColor.BLACK);

    private static final Map<String, Carpet>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Carpet> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    private final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected Carpet()
    {
        super("CARPET", 171, "minecraft:carpet", "WHITE", DyeColor.WHITE.getBlockFlag());
        this.color = DyeColor.WHITE;
    }

    public Carpet(final DyeColor color)
    {
        super(CARPET_WHITE.name(), CARPET_WHITE.getId(), CARPET_WHITE.getMinecraftId(), color.name(), color.getBlockFlag());
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
    public Carpet getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Carpet getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DyeColor getColor()
    {
        return this.color;
    }

    @Override
    public Carpet getColor(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Returns one of Carpet sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Carpet or null
     */
    public static Carpet getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Carpet sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Carpet or null
     */
    public static Carpet getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Carpet sub-type based on {@link DyeColor}.
     * It will never return null;
     *
     * @param color color of Carpet
     *
     * @return sub-type of Carpet
     */
    public static Carpet getCarpet(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Carpet element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Carpet.register(CARPET_WHITE);
        Carpet.register(CARPET_ORANGE);
        Carpet.register(CARPET_MAGENTA);
        Carpet.register(CARPET_LIGHT_BLUE);
        Carpet.register(CARPET_YELLOW);
        Carpet.register(CARPET_LIME);
        Carpet.register(CARPET_PINK);
        Carpet.register(CARPET_GRAY);
        Carpet.register(CARPET_SILVER);
        Carpet.register(CARPET_CYAN);
        Carpet.register(CARPET_PURPLE);
        Carpet.register(CARPET_BLUE);
        Carpet.register(CARPET_BROWN);
        Carpet.register(CARPET_GREEN);
        Carpet.register(CARPET_RED);
        Carpet.register(CARPET_BLACK);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("color", this.color).toString();
    }
}
