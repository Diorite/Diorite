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
public class GoldenShovelMat extends ShovelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final GoldenShovelMat GOLDEN_SHOVEL = new GoldenShovelMat();

    private static final Map<String, GoldenShovelMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<GoldenShovelMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<GoldenShovelMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<GoldenShovelMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected GoldenShovelMat()
    {
        super("GOLDEN_SHOVEL", 284, "minecraft:golden_Shovel", "NEW", (short) 0, ToolMaterial.GOLD);
    }

    protected GoldenShovelMat(final int durability)
    {
        super(GOLDEN_SHOVEL.name(), GOLDEN_SHOVEL.getId(), GOLDEN_SHOVEL.getMinecraftId(), Integer.toString(durability), (short) durability, ToolMaterial.GOLD);
    }

    protected GoldenShovelMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected GoldenShovelMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public GoldenShovelMat[] types()
    {
        return new GoldenShovelMat[]{GOLDEN_SHOVEL};
    }

    @Override
    public GoldenShovelMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public GoldenShovelMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public GoldenShovelMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public GoldenShovelMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public GoldenShovelMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of GoldenShovel sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of GoldenShovel.
     */
    public static GoldenShovelMat getByID(final int id)
    {
        GoldenShovelMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new GoldenShovelMat(id);
            if ((id > 0) && (id < GOLDEN_SHOVEL.getBaseDurability()))
            {
                GoldenShovelMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of GoldenShovel sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of GoldenShovel.
     */
    public static GoldenShovelMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of GoldenShovel-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of GoldenShovel.
     */
    public static GoldenShovelMat getByEnumName(final String name)
    {
        final GoldenShovelMat mat = byName.get(name);
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
    public static void register(final GoldenShovelMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static GoldenShovelMat[] goldenShovelTypes()
    {
        return new GoldenShovelMat[]{GOLDEN_SHOVEL};
    }

    static
    {
        GoldenShovelMat.register(GOLDEN_SHOVEL);
    }
}