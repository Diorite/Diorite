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
public class DiamondHoeMat extends HoeMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DiamondHoeMat DIAMOND_HOE = new DiamondHoeMat();

    private static final Map<String, DiamondHoeMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<DiamondHoeMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<DiamondHoeMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<DiamondHoeMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected DiamondHoeMat()
    {
        super("DIAMOND_HOE", 293, "minecraft:diamond_Hoe", "NEW", (short) 0, ToolMaterial.DIAMOND);
    }

    protected DiamondHoeMat(final int durability)
    {
        super(DIAMOND_HOE.name(), DIAMOND_HOE.getId(), DIAMOND_HOE.getMinecraftId(), Integer.toString(durability), (short) durability, ToolMaterial.DIAMOND);
    }

    protected DiamondHoeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected DiamondHoeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public DiamondHoeMat[] types()
    {
        return new DiamondHoeMat[]{DIAMOND_HOE};
    }

    @Override
    public DiamondHoeMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public DiamondHoeMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public DiamondHoeMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public DiamondHoeMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public DiamondHoeMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of DiamondHoe sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of DiamondHoe.
     */
    public static DiamondHoeMat getByID(final int id)
    {
        DiamondHoeMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new DiamondHoeMat(id);
            if ((id > 0) && (id < DIAMOND_HOE.getBaseDurability()))
            {
                DiamondHoeMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of DiamondHoe sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of DiamondHoe.
     */
    public static DiamondHoeMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DiamondHoe-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DiamondHoe.
     */
    public static DiamondHoeMat getByEnumName(final String name)
    {
        final DiamondHoeMat mat = byName.get(name);
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
    public static void register(final DiamondHoeMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static DiamondHoeMat[] diamondHoeTypes()
    {
        return new DiamondHoeMat[]{DIAMOND_HOE};
    }

    static
    {
        DiamondHoeMat.register(DIAMOND_HOE);
    }
}
