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
    public static final InventoryType PLAYER            = new InventoryType("PLAYER", 0, "Player", 45);
    public static final InventoryType PLAYER_CRAFTING   = new InventoryType("PLAYER_CRAFTING", 1, "Crafting", 5, PLAYER, true);
    public static final InventoryType PLAYER_ARMOR      = new InventoryType("PLAYER_ARMOR", 2, "Armor", 5, PLAYER, true);
    public static final InventoryType PLAYER_HOTBAR     = new InventoryType("PLAYER_HOTBAR", 3, "Hotbar", 9, PLAYER, true);
    public static final InventoryType PLAYER_EQ         = new InventoryType("PLAYER_EQ", 4, "Inventory", 27, PLAYER, true);
    public static final InventoryType PLAYER_FULL_EQ    = new InventoryType("PLAYER_FULL_EQ", 5, "Inventory", 36, PLAYER, true);
    public static final InventoryType CHEST             = new InventoryType("CHEST", 6, "Chest", 27);
    public static final InventoryType LARGE_CHEST       = new InventoryType("LARGE_CHEST", 7, "Large Chest", 54);
    public static final InventoryType DISPENSER         = new InventoryType("DISPENSER", 8, "Dispenser", 9);
    public static final InventoryType DROPPER           = new InventoryType("DROPPER", 9, "Dropper", 9);
    public static final InventoryType FURNACE           = new InventoryType("FURNACE", 10, "Furnace", 3);
    public static final InventoryType WORKBENCH         = new InventoryType("WORKBENCH", 11, "Crafting", 10);
    public static final InventoryType ENCHANTING        = new InventoryType("ENCHANTING", 12, "Enchanting", 2);
    public static final InventoryType BREWING           = new InventoryType("BREWING", 13, "Brewing", 4);
    public static final InventoryType MERCHANT          = new InventoryType("MERCHANT", 14, "Villager", 3);
    public static final InventoryType ENDER_CHEST       = new InventoryType("ENDER_CHEST", 15, "Ender Chest", 27);
    public static final InventoryType ANVIL             = new InventoryType("ANVIL", 16, "Repairing", 3);
    public static final InventoryType BEACON            = new InventoryType("BEACON", 17, "Beacon", 1);
    public static final InventoryType HOPPER            = new InventoryType("HOPPER", 18, "Item Hopper", 5);
    public static final InventoryType DONKEY_WITH_CHEST = new InventoryType("DONKEY_WITH_CHEST", 19, "Donkey", 16);
    public static final InventoryType DONKEY_CHEST      = new InventoryType("DONKEY_CHEST", 20, "Donkey", 15, DONKEY_WITH_CHEST, true);
    public static final InventoryType DONKEY            = new InventoryType("DONKEY", 21, "Donkey", 1, DONKEY_WITH_CHEST, false);
    public static final InventoryType HORSE             = new InventoryType("HORSE", 22, "Horse", 2);

    private static final Map<String, InventoryType>   byName = new SimpleStringHashMap<>(16, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<InventoryType> byID   = new TIntObjectHashMap<>(16, SMALL_LOAD_FACTOR);

    private final String        enumName;
    private final int           id;
    private final String        defaultTitle;
    private final int           size;
    private final InventoryType parent;
    private final boolean       needParent;

    public InventoryType(final String enumName, final int id, final String defaultTitle, final int size, final InventoryType parent, final boolean needParent)
    {
        this.enumName = enumName;
        this.id = id;
        this.defaultTitle = defaultTitle;
        this.size = size;
        this.parent = parent;
        this.needParent = needParent;
    }

    public InventoryType(final String enumName, final int id, final String defaultTitle, final int size)
    {
        this(enumName, id, defaultTitle, size, null, false);
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

    /**
     * @return Default title of inventory
     */
    public String getDefaultTitle()
    {
        return this.defaultTitle;
    }

    /**
     * If this inventory is can be part of bigger one,
     * this method return parent type.
     * Otherwise it will return null.
     *
     * @return parent inventory or null.
     */
    public InventoryType getParent()
    {
        return this.parent;
    }

    /**
     * @return true if this inventory is always part of parent inventory.
     */
    public boolean needParent()
    {
        return this.needParent;
    }

    /**
     * @return default size of inventory.
     */
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
        register(PLAYER);
        register(PLAYER_CRAFTING);
        register(PLAYER_ARMOR);
        register(PLAYER_HOTBAR);
        register(PLAYER_EQ);
        register(CHEST);
        register(LARGE_CHEST);
        register(DISPENSER);
        register(DROPPER);
        register(FURNACE);
        register(WORKBENCH);
        register(ENCHANTING);
        register(BREWING);
        register(MERCHANT);
        register(ENDER_CHEST);
        register(ANVIL);
        register(BEACON);
        register(HOPPER);
        register(DONKEY_WITH_CHEST);
        register(DONKEY_CHEST);
        register(DONKEY);
        register(HORSE);
    }
}
