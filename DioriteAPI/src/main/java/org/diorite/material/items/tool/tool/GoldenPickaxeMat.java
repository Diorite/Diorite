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
 * Class representing 'Golden Pickaxe' item material in minecraft. <br>
 * ID of material: 285 <br>
 * String ID of material: minecraft:golden_Pickaxe <br>
 * Max item stack size: 1
 */
@SuppressWarnings({"JavaDoc", "ClassHasNoToStringMethod"})
public class GoldenPickaxeMat extends PickaxeMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final GoldenPickaxeMat GOLDEN_PICKAXE = new GoldenPickaxeMat();

    private static final Map<String, GoldenPickaxeMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<GoldenPickaxeMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<GoldenPickaxeMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<GoldenPickaxeMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected GoldenPickaxeMat()
    {
        super("GOLDEN_PICKAXE", 285, "minecraft:golden_Pickaxe", "NEW", (short) 0, ToolMaterial.GOLD);
    }

    protected GoldenPickaxeMat(final int durability)
    {
        super(GOLDEN_PICKAXE.name(), GOLDEN_PICKAXE.getId(), GOLDEN_PICKAXE.getMinecraftId(), Integer.toString(durability), (short) durability, ToolMaterial.GOLD);
    }

    protected GoldenPickaxeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected GoldenPickaxeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public GoldenPickaxeMat[] types()
    {
        return new GoldenPickaxeMat[]{GOLDEN_PICKAXE};
    }

    @Override
    public GoldenPickaxeMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public GoldenPickaxeMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public GoldenPickaxeMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public GoldenPickaxeMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public GoldenPickaxeMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of GoldenPickaxe sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of GoldenPickaxe.
     */
    public static GoldenPickaxeMat getByID(final int id)
    {
        GoldenPickaxeMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new GoldenPickaxeMat(id);
            if ((id > 0) && (id < GOLDEN_PICKAXE.getBaseDurability()))
            {
                GoldenPickaxeMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of GoldenPickaxe sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of GoldenPickaxe.
     */
    public static GoldenPickaxeMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of GoldenPickaxe-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of GoldenPickaxe.
     */
    public static GoldenPickaxeMat getByEnumName(final String name)
    {
        final GoldenPickaxeMat mat = byName.get(name);
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
    public static void register(final GoldenPickaxeMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static GoldenPickaxeMat[] goldenPickaxeTypes()
    {
        return new GoldenPickaxeMat[]{GOLDEN_PICKAXE};
    }

    static
    {
        GoldenPickaxeMat.register(GOLDEN_PICKAXE);
    }
}
