package org.diorite.material;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

/**
 * Represent material of tool item.
 */
@SuppressWarnings({"ClassHasNoToStringMethod", "MagicNumber"})
public class ToolMaterial extends ASimpleEnum<ToolMaterial>
{
    static
    {
        init(ToolMaterial.class, 5);
    }

    public static final ToolMaterial WOODEN  = new ToolMaterial("WOODEN", Material.PLANKS, 1000, 60, 15);
    public static final ToolMaterial STONE   = new ToolMaterial("STONE", Material.COBBLESTONE, 2000, 132, 5);
    public static final ToolMaterial IRON    = new ToolMaterial("IRON", Material.IRON_INGOT, 3000, 251, 14);
    public static final ToolMaterial GOLD    = new ToolMaterial("GOLD", Material.GOLD_INGOT, 4000, 33, 22);
    public static final ToolMaterial DIAMOND = new ToolMaterial("DIAMOND", Material.DIAMOND, 5000, 1562, 10);

    protected final Material material;
    protected final int      rank; // tool with higher rank do everything that can do tools with lower rank.
    protected final int      baseDurability;
    protected final int      enchantability;

    protected ToolMaterial(final String enumName, final int enumId, final Material material, final int rank, final int baseDurability, final int enchantability)
    {
        super(enumName, enumId);
        this.material = material;
        this.rank = rank;
        this.baseDurability = baseDurability;
        this.enchantability = enchantability;
    }

    protected ToolMaterial(final String enumName, final Material material, final int rank, final int baseDurability, final int enchantability)
    {
        super(enumName);
        this.material = material;
        this.rank = rank;
        this.baseDurability = baseDurability;
        this.enchantability = enchantability;
    }

    /**
     * Returns base material item of tool, like iron ingot for iron tool. <br>
     * Crafting don't needs to match this material.
     *
     * @return base material item of tool.
     */
    public Material getMaterial()
    {
        return this.material;
    }

    /**
     * Returns rank of tool material, tools with higher rank of this same
     * {@link ToolType} can be used to break all blocks as lower rank tools.
     *
     * @return rank of tool material.
     */
    public int getRank()
    {
        return this.rank;
    }

    /**
     * Returns base durability of this tool.
     *
     * @return base durability of this tool.
     */
    public int getBaseDurability()
    {
        return this.baseDurability;
    }

    /**
     * Returns enchantability level of tool, used to get possible enchantemnts using enchanting table.
     *
     * @return enchantability level of tool.
     */
    public int getEnchantability()
    {
        return this.enchantability;
    }

    /**
     * Register new {@link ToolMaterial} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ToolMaterial element)
    {
        ASimpleEnum.register(ToolMaterial.class, element);
    }

    /**
     * Get one of {@link ToolMaterial} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ToolMaterial getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ToolMaterial.class, ordinal);
    }

    /**
     * Get one of ToolMaterial entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ToolMaterial getByEnumName(final String name)
    {
        return getByEnumName(ToolMaterial.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ToolMaterial[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ToolMaterial.class);
        return (ToolMaterial[]) map.values(new ToolMaterial[map.size()]);
    }

    static
    {
        ToolMaterial.register(WOODEN);
        ToolMaterial.register(STONE);
        ToolMaterial.register(IRON);
        ToolMaterial.register(GOLD);
        ToolMaterial.register(DIAMOND);
    }
}