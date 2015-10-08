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
public class DiamondShovelMat extends ShovelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DiamondShovelMat DIAMOND_SHOVEL = new DiamondShovelMat();

    private static final Map<String, DiamondShovelMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<DiamondShovelMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected final LazyValue<DiamondShovelMat> next = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() + 1) : null);
    protected final LazyValue<DiamondShovelMat> prev = new LazyValue<>(() -> (this.haveValidDurability()) ? getByDurability(this.getDurability() - 1) : null);

    @SuppressWarnings("MagicNumber")
    protected DiamondShovelMat()
    {
        super("DIAMOND_SHOVEL", 277, "minecraft:diamond_Shovel", "NEW", (short) 0, ToolMaterial.DIAMOND);
    }

    protected DiamondShovelMat(final int durability)
    {
        super(DIAMOND_SHOVEL.name(), DIAMOND_SHOVEL.getId(), DIAMOND_SHOVEL.getMinecraftId(), Integer.toString(durability), (short) durability, ToolMaterial.DIAMOND);
    }

    protected DiamondShovelMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected DiamondShovelMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public DiamondShovelMat[] types()
    {
        return new DiamondShovelMat[]{DIAMOND_SHOVEL};
    }

    @Override
    public DiamondShovelMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public DiamondShovelMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public DiamondShovelMat increaseDurability()
    {
        return this.next.get();
    }

    @Override
    public DiamondShovelMat decreaseDurability()
    {
        return this.prev.get();
    }

    @Override
    public DiamondShovelMat setDurability(final int durability)
    {
        return this.getType(durability);
    }

    /**
     * Returns one of DiamondShovel sub-type based on sub-id.
     *
     * @param id sub-type id
     *
     * @return sub-type of DiamondShovel.
     */
    public static DiamondShovelMat getByID(final int id)
    {
        DiamondShovelMat mat = byID.get((short) id);
        if (mat == null)
        {
            mat = new DiamondShovelMat(id);
            if ((id > 0) && (id < DIAMOND_SHOVEL.getBaseDurability()))
            {
                DiamondShovelMat.register(mat);
            }
        }
        return mat;
    }

    /**
     * Returns one of DiamondShovel sub-type based on durability.
     *
     * @param id durability of type.
     *
     * @return sub-type of DiamondShovel.
     */
    public static DiamondShovelMat getByDurability(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DiamondShovel-type based on name (selected by diorite team).
     * If block contains only one type, sub-name of it will be this same as name of material.<br>
     * Returns null if name can't be parsed to int and it isn't "NEW" one.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DiamondShovel.
     */
    public static DiamondShovelMat getByEnumName(final String name)
    {
        final DiamondShovelMat mat = byName.get(name);
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
    public static void register(final DiamondShovelMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DiamondShovelMat[] diamondShovelTypes()
    {
        return new DiamondShovelMat[]{DIAMOND_SHOVEL};
    }

    static
    {
        DiamondShovelMat.register(DIAMOND_SHOVEL);
    }
}