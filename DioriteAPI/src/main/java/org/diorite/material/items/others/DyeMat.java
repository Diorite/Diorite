package org.diorite.material.items.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.DyeColor;
import org.diorite.material.ColorableMat;
import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class DyeMat extends ItemMaterialData implements ColorableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final DyeMat DYE_INK_SAC      = new DyeMat();
    public static final DyeMat DYE_RED          = new DyeMat("RED", DyeColor.RED);
    public static final DyeMat DYE_GREEN        = new DyeMat("GREEN", DyeColor.GREEN);
    public static final DyeMat DYE_COCOA_BEANS  = new DyeMat("COCOA_BEANS", DyeColor.BROWN);
    public static final DyeMat DYE_LAPIS_LAZULI = new DyeMat("LAPIS_LAZULI", DyeColor.BLUE);
    public static final DyeMat DYE_PURPLE       = new DyeMat("PURPLE", DyeColor.PURPLE);
    public static final DyeMat DYE_CYAN         = new DyeMat("CYAN", DyeColor.CYAN);
    public static final DyeMat DYE_LIGHT_GRAY   = new DyeMat("LIGHT_GRAY", DyeColor.LIGHT_GRAY);
    public static final DyeMat DYE_GRAY         = new DyeMat("GRAY", DyeColor.GRAY);
    public static final DyeMat DYE_PINK         = new DyeMat("PINK", DyeColor.PINK);
    public static final DyeMat DYE_LIME         = new DyeMat("LIME", DyeColor.LIME);
    public static final DyeMat DYE_YELLOW       = new DyeMat("YELLOW", DyeColor.YELLOW);
    public static final DyeMat DYE_LIGHT_BLUE   = new DyeMat("LIGHT_BLUE", DyeColor.LIGHT_BLUE);
    public static final DyeMat DYE_MAGENTA      = new DyeMat("MAGENTA", DyeColor.MAGENTA);
    public static final DyeMat DYE_ORANGE       = new DyeMat("ORANGE", DyeColor.ORANGE);
    public static final DyeMat DYE_BONE_MEAL    = new DyeMat("BONE_MEAL", DyeColor.WHITE);

    private static final Map<String, DyeMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<DyeMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final DyeColor color;

    protected DyeMat()
    {
        super("DYE", 351, "minecraft:dye", "INK_SAC", (short) 0x00);
        this.color = DyeColor.BLACK;
    }

    protected DyeMat(final String typeName, final DyeColor color)
    {
        super(DYE_INK_SAC.name(), DYE_INK_SAC.getId(), DYE_INK_SAC.getMinecraftId(), typeName, (short) color.getItemFlag());
        this.color = color;
    }

    protected DyeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final DyeColor color)
    {
        super(enumName, id, minecraftId, typeName, type);
        this.color = color;
    }

    protected DyeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final DyeColor color)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.color = color;
    }

    @Override
    public DyeMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public DyeMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public DyeColor getColor()
    {
        return this.color;
    }

    @Override
    public DyeMat getColor(final DyeColor color)
    {
        return getByID(color.getItemFlag());
    }

    /**
     * Returns one of Dye sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Dye or null
     */
    public static DyeMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Dye sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Dye or null
     */
    public static DyeMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DyeMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DyeMat[] types()
    {
        return DyeMat.dyeTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DyeMat[] dyeTypes()
    {
        return byID.values(new DyeMat[byID.size()]);
    }

    static
    {
        DyeMat.register(DYE_INK_SAC);
        DyeMat.register(DYE_RED);
        DyeMat.register(DYE_GREEN);
        DyeMat.register(DYE_COCOA_BEANS);
        DyeMat.register(DYE_LAPIS_LAZULI);
        DyeMat.register(DYE_PURPLE);
        DyeMat.register(DYE_CYAN);
        DyeMat.register(DYE_LIGHT_GRAY);
        DyeMat.register(DYE_GRAY);
        DyeMat.register(DYE_PINK);
        DyeMat.register(DYE_LIME);
        DyeMat.register(DYE_YELLOW);
        DyeMat.register(DYE_LIGHT_BLUE);
        DyeMat.register(DYE_MAGENTA);
        DyeMat.register(DYE_ORANGE);
        DyeMat.register(DYE_BONE_MEAL);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("color", this.color).toString();
    }
}

