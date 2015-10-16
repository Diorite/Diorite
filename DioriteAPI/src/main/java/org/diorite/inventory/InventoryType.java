/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.inventory;

import java.util.Map;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TIntObjectMap;

@SuppressWarnings({"MagicNumber", "ClassHasNoToStringMethod"})
public class InventoryType extends ASimpleEnum<InventoryType>
{
    static
    {
        init(InventoryType.class, 23);
    }

    public static final InventoryType PLAYER            = new InventoryType("PLAYER", "", "Player", 45);
    public static final InventoryType PLAYER_CRAFTING   = new InventoryType("PLAYER_CRAFTING", "", "Crafting", 5, PLAYER, true);
    public static final InventoryType PLAYER_ARMOR      = new InventoryType("PLAYER_ARMOR", "", "Armor", 4, PLAYER, true);
    public static final InventoryType PLAYER_HOTBAR     = new InventoryType("PLAYER_HOTBAR", "", "Hotbar", 9, PLAYER, true);
    public static final InventoryType PLAYER_EQ         = new InventoryType("PLAYER_EQ", "", "Inventory", 27, PLAYER, true);
    public static final InventoryType PLAYER_FULL_EQ    = new InventoryType("PLAYER_FULL_EQ", "", "Inventory", 36, PLAYER, true);
    public static final InventoryType CHEST             = new InventoryType("CHEST", "minecraft:chest", "Chest", 27);
    public static final InventoryType LARGE_CHEST       = new InventoryType("LARGE_CHEST", "minecraft:chest", "Large Chest", 54);
    public static final InventoryType DISPENSER         = new InventoryType("DISPENSER", "minecraft:dispenser", "Dispenser", 9);
    public static final InventoryType DROPPER           = new InventoryType("DROPPER", "minecraft:dropper", "Dropper", 9);
    public static final InventoryType FURNACE           = new InventoryType("FURNACE", "minecraft:furnace", "Furnace", 3);
    public static final InventoryType CRAFTING          = new InventoryType("CRAFTING", "minecraft:crafting_table", "Crafting", 10);
    public static final InventoryType ENCHANTING        = new InventoryType("ENCHANTING", "minecraft:enchanting_table", "Enchanting", 2);
    public static final InventoryType BREWING           = new InventoryType("BREWING", "minecraft:brewing_stand", "Brewing", 4);
    public static final InventoryType MERCHANT          = new InventoryType("MERCHANT", "minecraft:villager", "Villager", 3);
    public static final InventoryType ENDER_CHEST       = new InventoryType("ENDER_CHEST", "", "Ender Chest", 27);
    public static final InventoryType ANVIL             = new InventoryType("ANVIL", "minecraft:anvil", "Repairing", 3);
    public static final InventoryType BEACON            = new InventoryType("BEACON", "minecraft:beacon", "Beacon", 1);
    public static final InventoryType HOPPER            = new InventoryType("HOPPER", "minecraft:hopper", "Item Hopper", 5);
    public static final InventoryType DONKEY_WITH_CHEST = new InventoryType("DONKEY_WITH_CHEST", "EntityHorse", "Donkey", 16);
    public static final InventoryType DONKEY_CHEST      = new InventoryType("DONKEY_CHEST", "EntityHorse", "Donkey", 15, DONKEY_WITH_CHEST, true);
    public static final InventoryType DONKEY            = new InventoryType("DONKEY", "EntityHorse", "Donkey", 1, DONKEY_WITH_CHEST, false);
    public static final InventoryType HORSE             = new InventoryType("HORSE", "EntityHorse", "Horse", 2);

    private static final Map<String, InventoryType> byMcId = new CaseInsensitiveMap<>(16, SMALL_LOAD_FACTOR);

    private final String        minecraftId;
    private final String        defaultTitle;
    private final int           size;
    private final InventoryType parent;
    private final boolean       needParent;

    public InventoryType(final String enumName, final int enumId, final String minecraftId, final String defaultTitle, final int size, final InventoryType parent, final boolean needParent)
    {
        super(enumName, enumId);
        this.minecraftId = minecraftId;
        this.defaultTitle = defaultTitle;
        this.size = size;
        this.parent = parent;
        this.needParent = needParent;
    }

    public InventoryType(final String enumName, final String minecraftId, final String defaultTitle, final int size, final InventoryType parent, final boolean needParent)
    {
        super(enumName);
        this.minecraftId = minecraftId;
        this.defaultTitle = defaultTitle;
        this.size = size;
        this.parent = parent;
        this.needParent = needParent;
    }

    public InventoryType(final String enumName, final String minecraftId, final String defaultTitle, final int size)
    {
        this(enumName, minecraftId, defaultTitle, size, null, false);
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

    /**
     * @return minecraft key
     */
    public String getMinecraftId()
    {
        return this.minecraftId;
    }

    public static InventoryType getByMinecraftId(final String minecraftId)
    {
        return byMcId.get(minecraftId);
    }


    /**
     * Register new option.
     *
     * @param element option to register.
     */
    public static void register(final InventoryType element)
    {
        ASimpleEnum.register(InventoryType.class, element);
        if (! element.getMinecraftId().isEmpty())
        {
            byMcId.put(element.getMinecraftId(), element);
        }
    }

    /**
     * Get one of {@link InventoryType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static InventoryType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(InventoryType.class, ordinal);
    }

    /**
     * Get one of InventoryType entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static InventoryType getByEnumName(final String name)
    {
        return getByEnumName(InventoryType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static InventoryType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(InventoryType.class);
        return (InventoryType[]) map.values(new InventoryType[map.size()]);
    }

    static
    {
        InventoryType.register(PLAYER);
        InventoryType.register(PLAYER_CRAFTING);
        InventoryType.register(PLAYER_ARMOR);
        InventoryType.register(PLAYER_HOTBAR);
        InventoryType.register(PLAYER_EQ);
        InventoryType.register(CHEST);
        InventoryType.register(LARGE_CHEST);
        InventoryType.register(DISPENSER);
        InventoryType.register(DROPPER);
        InventoryType.register(FURNACE);
        InventoryType.register(CRAFTING);
        InventoryType.register(ENCHANTING);
        InventoryType.register(BREWING);
        InventoryType.register(MERCHANT);
        InventoryType.register(ENDER_CHEST);
        InventoryType.register(ANVIL);
        InventoryType.register(BEACON);
        InventoryType.register(HOPPER);
        InventoryType.register(DONKEY_WITH_CHEST);
        InventoryType.register(DONKEY_CHEST);
        InventoryType.register(DONKEY);
        InventoryType.register(HORSE);
    }
}
