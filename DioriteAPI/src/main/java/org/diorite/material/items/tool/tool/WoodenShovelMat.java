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
public class WoodenShovelMat extends ShovelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final WoodenShovelMat WOODEN_SHOVEL = new WoodenShovelMat();

    private static final Map<String, WoodenShovelMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<WoodenShovelMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<WoodenShovelMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<WoodenShovelMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected WoodenShovelMat()
    {
        super("WOODEN_SHOVEL", 269, "minecraft:wooden_Shovel", "NEW", (short) 0, ToolMaterial.WOODEN);
    }

    protected WoodenShovelMat(final int durability)
    {
        super(WOODEN_SHOVEL.name(), WOODEN_SHOVEL.getId(), WOODEN_SHOVEL.getMinecraftId(), Integer.toString(durability), (short) durability, ToolMaterial.WOODEN);
    }

    protected WoodenShovelMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected WoodenShovelMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public WoodenShovelMat[] types()
    {
        return new WoodenShovelMat[]{WOODEN_SHOVEL};
    }

    @Override
    public WoodenShovelMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public WoodenShovelMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public WoodenShovelMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public WoodenShovelMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public WoodenShovelMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of WoodenShovel sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of WoodenShovel.
     */
    public static WoodenShovelMat getByID(final int id)
    {
        WoodenShovelMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new WoodenShovelMat(id);
            if ((id > 0) && (id < WOODEN_SHOVEL.getBaseDurability()))
            {
                WoodenShovelMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of WoodenShovel sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of WoodenShovel.
     */
    public static WoodenShovelMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of WoodenShovel-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WoodenShovel.
     */
    public static WoodenShovelMat getByEnumName(final String name)
    {
        final WoodenShovelMat mat = byName.get(name);
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
    public static void register(final WoodenShovelMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WoodenShovelMat[] woodenShovelTypes()
    {
        return new WoodenShovelMat[]{WOODEN_SHOVEL};
    }

    static
    {
        WoodenShovelMat.register(WOODEN_SHOVEL);
    }
}