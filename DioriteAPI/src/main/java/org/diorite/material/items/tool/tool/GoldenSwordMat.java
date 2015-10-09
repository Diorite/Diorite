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
public class GoldenSwordMat extends SwordMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final GoldenSwordMat GOLDEN_SWORD = new GoldenSwordMat();

    private static final Map<String, GoldenSwordMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<GoldenSwordMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<GoldenSwordMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<GoldenSwordMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected GoldenSwordMat()
    {
        super("GOLDEN_SWORD", 283, "minecraft:golden_Sword", "NEW", (short) 0, ToolMaterial.GOLD);
    }

    protected GoldenSwordMat(final int durability)
    {
        super(GOLDEN_SWORD.name(), GOLDEN_SWORD.getId(), GOLDEN_SWORD.getMinecraftId(), Integer.toString(durability), (short) durability, ToolMaterial.GOLD);
    }

    protected GoldenSwordMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected GoldenSwordMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public GoldenSwordMat[] types()
    {
        return new GoldenSwordMat[]{GOLDEN_SWORD};
    }

    @Override
    public GoldenSwordMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public GoldenSwordMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public GoldenSwordMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public GoldenSwordMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public GoldenSwordMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of GoldenSword sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of GoldenSword.
     */
    public static GoldenSwordMat getByID(final int id)
    {
        GoldenSwordMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new GoldenSwordMat(id);
            if ((id > 0) && (id < GOLDEN_SWORD.getBaseDurability()))
            {
                GoldenSwordMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of GoldenSword sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of GoldenSword.
     */
    public static GoldenSwordMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of GoldenSword-type based on name (selected by diorite team).
     * If item contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of GoldenSword.
     */
    public static GoldenSwordMat getByEnumName(final String name)
    {
        final GoldenSwordMat mat = byName.get(name);
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
    public static void register(final GoldenSwordMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static GoldenSwordMat[] goldenSwordTypes()
    {
        return new GoldenSwordMat[]{GOLDEN_SWORD};
    }

    static
    {
        GoldenSwordMat.register(GOLDEN_SWORD);
    }
}
