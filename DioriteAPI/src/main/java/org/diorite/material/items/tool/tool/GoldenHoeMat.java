package org.diorite.material.items.tool.tool;

import java.util.Map;

import org.diorite.material.ToolMaterial;
import org.diorite.material.ToolType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Golden Hoe' item material in minecraft. <br>
 * ID of material: 294 <br>
 * String ID of material: minecraft:golden_Hoe <br>
 * Max item stack size: 1
 */
@SuppressWarnings({"JavaDoc", "ClassHasNoToStringMethod"})
public class GoldenHoeMat extends HoeMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final GoldenHoeMat GOLDEN_HOE = new GoldenHoeMat();

    private static final Map<String, GoldenHoeMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<GoldenHoeMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<GoldenHoeMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<GoldenHoeMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected GoldenHoeMat()
    {
        super("GOLDEN_HOE", 294, "minecraft:golden_Hoe", "NEW", (short) 0, ToolMaterial.GOLD);
    }

    protected GoldenHoeMat(final int durability)
    {
        super(GOLDEN_HOE.name(), GOLDEN_HOE.getId(), GOLDEN_HOE.getMinecraftId(), Integer.toString(durability), (short) durability, ToolMaterial.GOLD);
    }

    protected GoldenHoeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected GoldenHoeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public GoldenHoeMat[] types()
    {
        return new GoldenHoeMat[]{GOLDEN_HOE};
    }

    @Override
    public GoldenHoeMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public GoldenHoeMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public GoldenHoeMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public GoldenHoeMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public GoldenHoeMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of GoldenHoe sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of GoldenHoe.
     */
    public static GoldenHoeMat getByID(final int id)
    {
        GoldenHoeMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new GoldenHoeMat(id);
            if ((id > 0) && (id < GOLDEN_HOE.getBaseDurability()))
            {
                GoldenHoeMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of GoldenHoe sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of GoldenHoe.
     */
    public static GoldenHoeMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of GoldenHoe-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of GoldenHoe.
     */
    public static GoldenHoeMat getByEnumName(final String name)
    {
        final GoldenHoeMat mat = byName.get(name);
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
    public static void register(final GoldenHoeMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static GoldenHoeMat[] goldenHoeTypes()
    {
        return new GoldenHoeMat[]{GOLDEN_HOE};
    }

    static
    {
        GoldenHoeMat.register(GOLDEN_HOE);
    }
}
