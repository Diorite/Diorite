package org.diorite.material;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;
import org.diorite.utils.collections.maps.SimpleEnumMap;

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

    public static final ToolMaterial WOODEN;
    public static final ToolMaterial STONE;
    public static final ToolMaterial IRON;
    public static final ToolMaterial GOLD;
    public static final ToolMaterial DIAMOND;

    static
    {
        init(ToolMaterial.class, 5);
        {
            final SimpleEnumMap<ToolType, ToolData> properties = new SimpleEnumMap<>(4);
            WOODEN = new ToolMaterial("WOODEN", Material.PLANKS, 1000, 60, 15, properties);
            properties.put(ToolType.PICKAXE, new ToolData(WOODEN, ToolType.PICKAXE, 2, 1.2 /*-4.0*/));
            properties.put(ToolType.SHOVEL, new ToolData(WOODEN, ToolType.SHOVEL, 2.5, 1.0 /*-4.0*/));
            properties.put(ToolType.AXE, new ToolData(WOODEN, ToolType.AXE, 7, 0.8 /*-4.0*/));
            properties.put(ToolType.HOE, new ToolData(WOODEN, ToolType.HOE, 1, 1.0 /*-4.0*/));
            properties.put(ToolType.SWORD, new ToolData(WOODEN, ToolType.SWORD, 4, 1.6 /*-4.0*/));
        }
        {
            final SimpleEnumMap<ToolType, ToolData> properties = new SimpleEnumMap<>(4);
            STONE = new ToolMaterial("STONE", Material.COBBLESTONE, 2000, 132, 5, properties);
            properties.put(ToolType.PICKAXE, new ToolData(STONE, ToolType.PICKAXE, 3, 1.2 /*-4.0*/));
            properties.put(ToolType.SHOVEL, new ToolData(STONE, ToolType.SHOVEL, 3.5, 1.0 /*-4.0*/));
            properties.put(ToolType.AXE, new ToolData(STONE, ToolType.AXE, 9, 0.8 /*-4.0*/));
            properties.put(ToolType.HOE, new ToolData(STONE, ToolType.HOE, 1, 2.0 /*-4.0*/));
            properties.put(ToolType.SWORD, new ToolData(STONE, ToolType.SWORD, 5, 1.6 /*-4.0*/));
        }
        {
            final SimpleEnumMap<ToolType, ToolData> properties = new SimpleEnumMap<>(4);
            IRON = new ToolMaterial("IRON", Material.IRON_INGOT, 3000, 251, 14, properties);
            properties.put(ToolType.PICKAXE, new ToolData(IRON, ToolType.PICKAXE, 4, 1.2 /*-4.0*/));
            properties.put(ToolType.SHOVEL, new ToolData(IRON, ToolType.SHOVEL, 4.5, 1.0 /*-4.0*/));
            properties.put(ToolType.AXE, new ToolData(IRON, ToolType.AXE, 9, 0.9 /*-4.0*/));
            properties.put(ToolType.HOE, new ToolData(IRON, ToolType.HOE, 1, 3.0 /*-4.0*/));
            properties.put(ToolType.SWORD, new ToolData(IRON, ToolType.SWORD, 6, 1.6 /*-4.0*/));
        }
        {
            final SimpleEnumMap<ToolType, ToolData> properties = new SimpleEnumMap<>(4);
            GOLD = new ToolMaterial("GOLD", Material.GOLD_INGOT, 4000, 33, 22, properties);
            properties.put(ToolType.PICKAXE, new ToolData(GOLD, ToolType.PICKAXE, 2, 1.2 /*-4.0*/));
            properties.put(ToolType.SHOVEL, new ToolData(GOLD, ToolType.SHOVEL, 2.5, 1.0 /*-4.0*/));
            properties.put(ToolType.AXE, new ToolData(GOLD, ToolType.AXE, 7, 1.0 /*-4.0*/));
            properties.put(ToolType.HOE, new ToolData(GOLD, ToolType.HOE, 1, 1.0 /*-4.0*/));
            properties.put(ToolType.SWORD, new ToolData(GOLD, ToolType.SWORD, 4, 1.6 /*-4.0*/));
        }
        {
            final SimpleEnumMap<ToolType, ToolData> properties = new SimpleEnumMap<>(4);
            DIAMOND = new ToolMaterial("DIAMOND", Material.DIAMOND, 5000, 1562, 10, properties);
            properties.put(ToolType.PICKAXE, new ToolData(DIAMOND, ToolType.PICKAXE, 5, 1.2 /*-4.0*/));
            properties.put(ToolType.SHOVEL, new ToolData(DIAMOND, ToolType.SHOVEL, 5.5, 1.0 /*-4.0*/));
            properties.put(ToolType.AXE, new ToolData(DIAMOND, ToolType.AXE, 9, 1.0 /*-4.0*/));
            properties.put(ToolType.HOE, new ToolData(DIAMOND, ToolType.HOE, 1, 4.0 /*-4.0*/));
            properties.put(ToolType.SWORD, new ToolData(DIAMOND, ToolType.SWORD, 7, 1.6 /*-4.0*/));
        }
    }

    protected final Material                          material;
    protected final int                               rank; // tool with higher rank do everything that can do tools with lower rank.
    protected final int                               baseDurability;
    protected final int                               enchantability;
    protected final SimpleEnumMap<ToolType, ToolData> properties;

    protected ToolMaterial(final String enumName, final int enumId, final Material material, final int rank, final int baseDurability, final int enchantability, final SimpleEnumMap<ToolType, ToolData> properties)
    {
        super(enumName, enumId);
        this.material = material;
        this.rank = rank;
        this.baseDurability = baseDurability;
        this.enchantability = enchantability;
        this.properties = properties;
    }

    protected ToolMaterial(final String enumName, final Material material, final int rank, final int baseDurability, final int enchantability, final SimpleEnumMap<ToolType, ToolData> properties)
    {
        super(enumName);
        this.material = material;
        this.rank = rank;
        this.baseDurability = baseDurability;
        this.enchantability = enchantability;
        this.properties = properties;
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
     * Returns properties of tool for given tool element type.
     *
     * @param toolType type of tool element.
     *
     * @return properties of tool.
     */
    public ToolData getProperties(final ToolType toolType)
    {
        return this.properties.get(toolType);
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