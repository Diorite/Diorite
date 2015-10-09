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
public class WoodenPickaxeMat extends PickaxeMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final WoodenPickaxeMat WOODEN_PICKAXE = new WoodenPickaxeMat();

    private static final Map<String, WoodenPickaxeMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<WoodenPickaxeMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<WoodenPickaxeMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<WoodenPickaxeMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected WoodenPickaxeMat()
    {
        super("WOODEN_PICKAXE", 270, "minecraft:wooden_Pickaxe", "NEW", (short) 0, ToolMaterial.WOODEN);
    }

    protected WoodenPickaxeMat(final int durability)
    {
        super(WOODEN_PICKAXE.name(), WOODEN_PICKAXE.getId(), WOODEN_PICKAXE.getMinecraftId(), Integer.toString(durability), (short) durability, ToolMaterial.WOODEN);
    }

    protected WoodenPickaxeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected WoodenPickaxeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public WoodenPickaxeMat[] types()
    {
        return new WoodenPickaxeMat[]{WOODEN_PICKAXE};
    }

    @Override
    public WoodenPickaxeMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public WoodenPickaxeMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public WoodenPickaxeMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public WoodenPickaxeMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public WoodenPickaxeMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of WoodenPickaxe sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of WoodenPickaxe.
     */
    public static WoodenPickaxeMat getByID(final int id)
    {
        WoodenPickaxeMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new WoodenPickaxeMat(id);
            if ((id > 0) && (id < WOODEN_PICKAXE.getBaseDurability()))
            {
                WoodenPickaxeMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of WoodenPickaxe sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of WoodenPickaxe.
     */
    public static WoodenPickaxeMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of WoodenPickaxe-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WoodenPickaxe.
     */
    public static WoodenPickaxeMat getByEnumName(final String name)
    {
        final WoodenPickaxeMat mat = byName.get(name);
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
    public static void register(final WoodenPickaxeMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static WoodenPickaxeMat[] woodenPickaxeTypes()
    {
        return new WoodenPickaxeMat[]{WOODEN_PICKAXE};
    }

    static
    {
        WoodenPickaxeMat.register(WOODEN_PICKAXE);
    }
}
