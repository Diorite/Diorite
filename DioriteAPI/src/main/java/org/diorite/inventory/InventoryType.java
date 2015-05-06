package org.diorite.inventory;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

@SuppressWarnings("MagicNumber")
public class InventoryType implements SimpleEnum<InventoryType>
{
    public static final InventoryType CHEST           = new InventoryType("CHEST", 0, "Chest", 27);
    public static final InventoryType DISPENSER       = new InventoryType("DISPENSER", 1, "Dispenser", 9);
    public static final InventoryType DROPPER         = new InventoryType("DROPPER", 2, "Dropper", 9);
    public static final InventoryType FURNACE         = new InventoryType("FURNACE", 3, "Furnace", 3);
    public static final InventoryType WORKBENCH       = new InventoryType("WORKBENCH", 4, "Crafting", 10);
    public static final InventoryType PLAYER_CRAFTING = new InventoryType("PLAYER_CRAFTING", 5, "Crafting", 5);
    public static final InventoryType ENCHANTING      = new InventoryType("ENCHANTING", 6, "Enchanting", 2);
    public static final InventoryType BREWING         = new InventoryType("BREWING", 7, "Brewing", 4);
    public static final InventoryType PLAYER          = new InventoryType("PLAYER", 8, "Player", 36);
    public static final InventoryType MERCHANT        = new InventoryType("MERCHANT", 9, "Villager", 3);
    public static final InventoryType ENDER_CHEST     = new InventoryType("ENDER_CHEST", 10, "Ender Chest", 27);
    public static final InventoryType ANVIL           = new InventoryType("ANVIL", 11, "Repairing", 3);
    public static final InventoryType BEACON          = new InventoryType("BEACON", 12, "Beacon", 1);
    public static final InventoryType HOPPER          = new InventoryType("HOPPER", 13, "Item Hopper", 5);

    private static final Map<String, InventoryType>   byName = new SimpleStringHashMap<>(5, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<InventoryType> byID   = new TIntObjectHashMap<>(5, SMALL_LOAD_FACTOR);

    private final String enumName;
    private final int    id;
    private final String defaultTitle;
    private final int    size;

    public InventoryType(final String enumName, final int id, final String defaultTitle, final int size)
    {
        this.enumName = enumName;
        this.id = id;
        this.defaultTitle = defaultTitle;
        this.size = size;
    }

    @Override
    public String name()
    {
        return this.enumName;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    @Override
    public InventoryType byId(final int id)
    {
        return byID.get(id);
    }

    @Override
    public InventoryType byName(final String name)
    {
        return byName.get(name);
    }

    public String getDefaultTitle()
    {
        return this.defaultTitle;
    }

    public int getSize()
    {
        return this.size;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("id", this.id).append("name", this.defaultTitle).toString();
    }

    public static InventoryType getByID(final int id)
    {
        return byID.get(id);
    }

    public static InventoryType getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final InventoryType element)
    {
        byID.put(element.getId(), element);
        byName.put(element.name(), element);
    }

    /**
     * @return all values in array.
     */
    public static InventoryType[] values()
    {
        return byID.values(new InventoryType[byID.size()]);
    }

    static
    {
        register(CHEST);
        register(DISPENSER);
        register(DROPPER);
        register(FURNACE);
        register(WORKBENCH);
        register(PLAYER_CRAFTING);
        register(ENCHANTING);
        register(BREWING);
        register(PLAYER);
        register(MERCHANT);
        register(ENDER_CHEST);
        register(ANVIL);
        register(BEACON);
        register(HOPPER);
    }
}
