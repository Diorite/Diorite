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
public class StoneHoeMat extends HoeMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final StoneHoeMat STONE_HOE = new StoneHoeMat();

    private static final Map<String, StoneHoeMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<StoneHoeMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<StoneHoeMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<StoneHoeMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected StoneHoeMat()
    {
        super("STONE_HOE", 291, "minecraft:stone_Hoe", "NEW", (short) 0, ToolMaterial.STONE);
    }

    protected StoneHoeMat(final int durability)
    {
        super(STONE_HOE.name(), STONE_HOE.getId(), STONE_HOE.getMinecraftId(), Integer.toString(durability), (short) durability, ToolMaterial.STONE);
    }

    protected StoneHoeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected StoneHoeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public StoneHoeMat[] types()
    {
        return new StoneHoeMat[]{STONE_HOE};
    }

    @Override
    public StoneHoeMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public StoneHoeMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public StoneHoeMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public StoneHoeMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public StoneHoeMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of StoneHoe sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of StoneHoe.
     */
    public static StoneHoeMat getByID(final int id)
    {
        StoneHoeMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new StoneHoeMat(id);
            if ((id > 0) && (id < STONE_HOE.getBaseDurability()))
            {
                StoneHoeMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of StoneHoe sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of StoneHoe.
     */
    public static StoneHoeMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of StoneHoe-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StoneHoe.
     */
    public static StoneHoeMat getByEnumName(final String name)
    {
        final StoneHoeMat mat = byName.get(name);
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
    public static void register(final StoneHoeMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StoneHoeMat[] stoneHoeTypes()
    {
        return new StoneHoeMat[]{STONE_HOE};
    }

    static
    {
        StoneHoeMat.register(STONE_HOE);
    }
}