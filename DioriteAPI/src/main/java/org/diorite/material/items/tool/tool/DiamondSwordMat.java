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
public class DiamondSwordMat extends SwordMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DiamondSwordMat DIAMOND_SWORD = new DiamondSwordMat();

    private static final Map<String, DiamondSwordMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<DiamondSwordMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<DiamondSwordMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<DiamondSwordMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected DiamondSwordMat()
    {
        super("DIAMOND_SWORD", 276, "minecraft:diamond_Sword", "NEW", (short) 0, ToolMaterial.DIAMOND);
    }

    protected DiamondSwordMat(final int durability)
    {
        super(DIAMOND_SWORD.name(), DIAMOND_SWORD.getId(), DIAMOND_SWORD.getMinecraftId(), Integer.toString(durability), (short) durability, ToolMaterial.DIAMOND);
    }

    protected DiamondSwordMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected DiamondSwordMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public DiamondSwordMat[] types()
    {
        return new DiamondSwordMat[]{DIAMOND_SWORD};
    }

    @Override
    public DiamondSwordMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public DiamondSwordMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public DiamondSwordMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public DiamondSwordMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public DiamondSwordMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of DiamondSword sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of DiamondSword.
     */
    public static DiamondSwordMat getByID(final int id)
    {
        DiamondSwordMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new DiamondSwordMat(id);
            if ((id > 0) && (id < DIAMOND_SWORD.getBaseDurability()))
            {
                DiamondSwordMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of DiamondSword sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of DiamondSword.
     */
    public static DiamondSwordMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DiamondSword-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DiamondSword.
     */
    public static DiamondSwordMat getByEnumName(final String name)
    {
        final DiamondSwordMat mat = byName.get(name);
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
    public static void register(final DiamondSwordMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DiamondSwordMat[] diamondSwordTypes()
    {
        return new DiamondSwordMat[]{DIAMOND_SWORD};
    }

    static
    {
        DiamondSwordMat.register(DIAMOND_SWORD);
    }
}