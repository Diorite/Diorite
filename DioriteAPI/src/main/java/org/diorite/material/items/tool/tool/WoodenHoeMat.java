package org.diorite.material.items.tool.tool;

import java.util.Map;

import org.diorite.material.ToolMaterial;
import org.diorite.material.ToolType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class WoodenHoeMat extends HoeMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final WoodenHoeMat WOODEN_HOE = new WoodenHoeMat();

    private static final Map<String, WoodenHoeMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<WoodenHoeMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<WoodenHoeMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<WoodenHoeMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected WoodenHoeMat()
    {
        super("WOODEN_HOE", 290, "minecraft:wooden_Hoe", "NEW", (short) 0, ToolMaterial.WOODEN);
    }

    protected WoodenHoeMat(final int durability)
    {
        super(WOODEN_HOE.name(), WOODEN_HOE.getId(), WOODEN_HOE.getMinecraftId(), Integer.toString(durability), (short) durability, ToolMaterial.WOODEN);
    }

    protected WoodenHoeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected WoodenHoeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public WoodenHoeMat[] types()
    {
        return new WoodenHoeMat[]{WOODEN_HOE};
    }

    @Override
    public WoodenHoeMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public WoodenHoeMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public WoodenHoeMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public WoodenHoeMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public WoodenHoeMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of WoodenHoe sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of WoodenHoe.
     */
    public static WoodenHoeMat getByID(final int id)
    {
        WoodenHoeMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new WoodenHoeMat(id);
            if ((id > 0) && (id < WOODEN_HOE.getBaseDurability()))
            {
                WoodenHoeMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of WoodenHoe sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of WoodenHoe.
     */
    public static WoodenHoeMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of WoodenHoe-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WoodenHoe.
     */
    public static WoodenHoeMat getByEnumName(final String name)
    {
        final WoodenHoeMat mat = byName.get(name);
        if (mat == null)
        {
            final Integer idType = DioriteMathUtils.asInt(name);
            if (idType == null)
            {
                return null;
            }
            return getByID(idType);
        }
        return mat;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WoodenHoeMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WoodenHoeMat[] woodenHoeTypes()
    {
        return new WoodenHoeMat[]{WOODEN_HOE};
    }

    static
    {
        WoodenHoeMat.register(WOODEN_HOE);
    }
}