package org.diorite.material.items.tool.basic;

import java.util.Map;

import org.diorite.material.BasicToolData;
import org.diorite.material.items.tool.BasicToolMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class FlintAndSteelMat extends BasicToolMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final FlintAndSteelMat FLINT_AND_STEEL = new FlintAndSteelMat();

    private static final Map<String, FlintAndSteelMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<FlintAndSteelMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<FlintAndSteelMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<FlintAndSteelMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected FlintAndSteelMat()
    {
        super("FLINT_AND_STEEL", 259, "minecraft:flint_and_steel", "NEW", (short) 0x00, new BasicToolData(65));
    }

    protected FlintAndSteelMat(final int durability)
    {
        super(FLINT_AND_STEEL.name(), FLINT_AND_STEEL.getId(), FLINT_AND_STEEL.getMinecraftId(), Integer.toString(durability), (short) durability, new BasicToolData(FLINT_AND_STEEL.getToolData()));
    }

    public FlintAndSteelMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final BasicToolData toolData)
    {
        super(enumName, id, minecraftId, typeName, type, toolData);
    }

    public FlintAndSteelMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final BasicToolData toolData)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolData);
    }

    @Override
    public FlintAndSteelMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public FlintAndSteelMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public FlintAndSteelMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public FlintAndSteelMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public FlintAndSteelMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    @Override
    public FlintAndSteelMat[] types()
    {
        return flintAndSteelTypes();
    }

    /**
     * Returns one of FlintAndSteel sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of FlintAndSteel.
     */
    public static FlintAndSteelMat getByID(final int id)
    {
        FlintAndSteelMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new FlintAndSteelMat(id);
            if ((id > 0) && (id < FLINT_AND_STEEL.getBaseDurability()))
            {
                FlintAndSteelMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of FlintAndSteel sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of FlintAndSteel.
     */
    public static FlintAndSteelMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of FlintAndSteel-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of FlintAndSteel.
     */
    public static FlintAndSteelMat getByEnumName(final String name)
    {
        final FlintAndSteelMat mat = byName.get(name);
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
    public static void register(final FlintAndSteelMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static FlintAndSteelMat[] flintAndSteelTypes()
    {
        return new FlintAndSteelMat[]{FLINT_AND_STEEL};
    }

    static
    {
        FlintAndSteelMat.register(FLINT_AND_STEEL);
    }
}

