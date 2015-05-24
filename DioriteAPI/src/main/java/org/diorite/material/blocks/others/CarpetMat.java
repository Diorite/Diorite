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
 * Class representing block "Carpet" and all its subtypes.
 */
public class CarpetMat extends BlockMaterialData implements ColorableMat
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

    public static final CarpetMat CARPET_WHITE      = new CarpetMat();
    public static final CarpetMat CARPET_ORANGE     = new CarpetMat(DyeColor.ORANGE);
    public static final CarpetMat CARPET_MAGENTA    = new CarpetMat(DyeColor.MAGENTA);
    public static final CarpetMat CARPET_LIGHT_BLUE = new CarpetMat(DyeColor.LIGHT_BLUE);
    public static final CarpetMat CARPET_YELLOW     = new CarpetMat(DyeColor.YELLOW);
    public static final CarpetMat CARPET_LIME       = new CarpetMat(DyeColor.LIME);
    public static final CarpetMat CARPET_PINK       = new CarpetMat(DyeColor.PINK);
    public static final CarpetMat CARPET_GRAY       = new CarpetMat(DyeColor.GRAY);
    public static final CarpetMat CARPET_SILVER     = new CarpetMat(DyeColor.SILVER);
    public static final CarpetMat CARPET_CYAN       = new CarpetMat(DyeColor.CYAN);
    public static final CarpetMat CARPET_PURPLE     = new CarpetMat(DyeColor.PURPLE);
    public static final CarpetMat CARPET_BLUE       = new CarpetMat(DyeColor.BLUE);
    public static final CarpetMat CARPET_BROWN      = new CarpetMat(DyeColor.BROWN);
    public static final CarpetMat CARPET_GREEN      = new CarpetMat(DyeColor.GREEN);
    public static final CarpetMat CARPET_RED        = new CarpetMat(DyeColor.RED);
    public static final CarpetMat CARPET_BLACK      = new CarpetMat(DyeColor.BLACK);

    private static final Map<String, CarpetMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CarpetMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected CarpetMat()
    {
        super("CARPET", 171, "minecraft:carpet", "WHITE", DyeColor.WHITE.getBlockFlag());
        this.color = DyeColor.WHITE;
    }

    protected CarpetMat(final DyeColor color)
    {
        super(CARPET_WHITE.name(), CARPET_WHITE.getId(), CARPET_WHITE.getMinecraftId(), color.name(), color.getBlockFlag());
        this.color = color;
    }

    protected CarpetMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final DyeColor color)
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
    public CarpetMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CarpetMat getType(final int id)
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
    public CarpetMat getColor(final DyeColor color)
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
    public static CarpetMat getByID(final int id)
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
    public static CarpetMat getByEnumName(final String name)
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
    public static CarpetMat getCarpet(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CarpetMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public CarpetMat[] types()
    {
        return CarpetMat.carpetTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CarpetMat[] carpetTypes()
    {
        return byID.values(new CarpetMat[byID.size()]);
    }

    static
    {
        CarpetMat.register(CARPET_WHITE);
        CarpetMat.register(CARPET_ORANGE);
        CarpetMat.register(CARPET_MAGENTA);
        CarpetMat.register(CARPET_LIGHT_BLUE);
        CarpetMat.register(CARPET_YELLOW);
        CarpetMat.register(CARPET_LIME);
        CarpetMat.register(CARPET_PINK);
        CarpetMat.register(CARPET_GRAY);
        CarpetMat.register(CARPET_SILVER);
        CarpetMat.register(CARPET_CYAN);
        CarpetMat.register(CARPET_PURPLE);
        CarpetMat.register(CARPET_BLUE);
        CarpetMat.register(CARPET_BROWN);
        CarpetMat.register(CARPET_GREEN);
        CarpetMat.register(CARPET_RED);
        CarpetMat.register(CARPET_BLACK);
    }
}
