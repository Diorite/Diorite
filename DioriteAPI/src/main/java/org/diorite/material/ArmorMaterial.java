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

package org.diorite.material;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;
import org.diorite.utils.collections.maps.SimpleEnumMap;

import gnu.trove.map.TIntObjectMap;

/**
 * Represent material of armor.
 */
@SuppressWarnings({"ClassHasNoToStringMethod", "MagicNumber", "JavaDoc"})
public class ArmorMaterial extends ASimpleEnum<ArmorMaterial>
{
    /**
     * Armor made from {@link Material#LEATHER}
     */
    public static final ArmorMaterial LEATHER;
    /**
     * Armor made from {@link Material#IRON_INGOT}
     */
    public static final ArmorMaterial IRON;
    /**
     * Armor made from {@link Material#FIRE} <br>
     * Can't be crafted.
     */
    public static final ArmorMaterial CHAINMAIL; // can't be crafted :<
    /**
     * Armor made from {@link Material#GOLD_INGOT}
     */
    public static final ArmorMaterial GOLD;
    /**
     * Armor made from {@link Material#DIAMOND}
     */
    public static final ArmorMaterial DIAMOND;

    static
    {
        init(ArmorMaterial.class, 5);
        {
            final SimpleEnumMap<ArmorType, ArmorData> properties = new SimpleEnumMap<>(4);
            LEATHER = new ArmorMaterial("LEATHER", Material.LEATHER, 15, properties);
            properties.put(ArmorType.HELMET, new ArmorData(LEATHER, ArmorType.HELMET, 56, 1));
            properties.put(ArmorType.CHESTPLATE, new ArmorData(LEATHER, ArmorType.CHESTPLATE, 81, 3));
            properties.put(ArmorType.LEGGINGS, new ArmorData(LEATHER, ArmorType.LEGGINGS, 76, 2));
            properties.put(ArmorType.BOOTS, new ArmorData(LEATHER, ArmorType.BOOTS, 66, 1));
            ArmorMaterial.register(LEATHER);
        }
        {
            final SimpleEnumMap<ArmorType, ArmorData> properties = new SimpleEnumMap<>(4);
            IRON = new ArmorMaterial("IRON", Material.IRON_INGOT, 9, properties);
            properties.put(ArmorType.HELMET, new ArmorData(IRON, ArmorType.HELMET, 166, 2));
            properties.put(ArmorType.CHESTPLATE, new ArmorData(IRON, ArmorType.CHESTPLATE, 241, 6));
            properties.put(ArmorType.LEGGINGS, new ArmorData(IRON, ArmorType.LEGGINGS, 226, 5));
            properties.put(ArmorType.BOOTS, new ArmorData(IRON, ArmorType.BOOTS, 196, 2));
            ArmorMaterial.register(IRON);
        }
        {
            final SimpleEnumMap<ArmorType, ArmorData> properties = new SimpleEnumMap<>(4);
            CHAINMAIL = new ArmorMaterial("CHAINMAIL", Material.FIRE, 12, properties);
            properties.put(ArmorType.HELMET, new ArmorData(CHAINMAIL, ArmorType.HELMET, 166, 2));
            properties.put(ArmorType.CHESTPLATE, new ArmorData(CHAINMAIL, ArmorType.CHESTPLATE, 241, 5));
            properties.put(ArmorType.LEGGINGS, new ArmorData(CHAINMAIL, ArmorType.LEGGINGS, 226, 4));
            properties.put(ArmorType.BOOTS, new ArmorData(CHAINMAIL, ArmorType.BOOTS, 196, 1));
            ArmorMaterial.register(CHAINMAIL);
        }
        {
            final SimpleEnumMap<ArmorType, ArmorData> properties = new SimpleEnumMap<>(4);
            GOLD = new ArmorMaterial("GOLD", Material.GOLD_INGOT, 25, properties);
            properties.put(ArmorType.HELMET, new ArmorData(GOLD, ArmorType.HELMET, 78, 2));
            properties.put(ArmorType.CHESTPLATE, new ArmorData(GOLD, ArmorType.CHESTPLATE, 113, 5));
            properties.put(ArmorType.LEGGINGS, new ArmorData(GOLD, ArmorType.LEGGINGS, 106, 3));
            properties.put(ArmorType.BOOTS, new ArmorData(GOLD, ArmorType.BOOTS, 92, 1));
            ArmorMaterial.register(GOLD);
        }
        {
            final SimpleEnumMap<ArmorType, ArmorData> properties = new SimpleEnumMap<>(4);
            DIAMOND = new ArmorMaterial("DIAMOND", Material.DIAMOND, 10, properties);
            properties.put(ArmorType.HELMET, new ArmorData(DIAMOND, ArmorType.HELMET, 364, 3));
            properties.put(ArmorType.CHESTPLATE, new ArmorData(DIAMOND, ArmorType.CHESTPLATE, 529, 8));
            properties.put(ArmorType.LEGGINGS, new ArmorData(DIAMOND, ArmorType.LEGGINGS, 496, 6));
            properties.put(ArmorType.BOOTS, new ArmorData(DIAMOND, ArmorType.BOOTS, 430, 3));
            ArmorMaterial.register(DIAMOND);
        }
    }

    /**
     * Basic material used in this armor material.
     */
    protected final Material                            material;
    /**
     * Enchantability level, used when enchanting.
     */
    protected final int                                 enchantability;
    /**
     * Properties for each {@link ArmorType}.
     */
    protected final SimpleEnumMap<ArmorType, ArmorData> properties;

    protected ArmorMaterial(final String enumName, final int enumId, final Material material, final int enchantability, final SimpleEnumMap<ArmorType, ArmorData> properties)
    {
        super(enumName, enumId);
        this.material = material;
        this.enchantability = enchantability;
        this.properties = properties;
    }

    protected ArmorMaterial(final String enumName, final Material material, final int enchantability, final SimpleEnumMap<ArmorType, ArmorData> properties)
    {
        super(enumName);
        this.material = material;
        this.enchantability = enchantability;
        this.properties = properties;
    }

    /**
     * Returns base material item of armor, like iron ingot for iron armor. <br>
     * Crafting don't needs to match this material.
     *
     * @return base material item of armor.
     */
    public Material getMaterial()
    {
        return this.material;
    }

    /**
     * Returns enchantability level of armor, used to get possible enchantemnts using enchanting table.
     *
     * @return enchantability level of armor.
     */
    public int getEnchantability()
    {
        return this.enchantability;
    }

    /**
     * Returns properties of armor for given armor element type.
     *
     * @param armorType type of armor element.
     *
     * @return properties of armor.
     */
    public ArmorData getProperties(final ArmorType armorType)
    {
        return this.properties.get(armorType);
    }

    /**
     * Register new {@link ArmorMaterial} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ArmorMaterial element)
    {
        ASimpleEnum.register(ArmorMaterial.class, element);
    }

    /**
     * Get one of {@link ArmorMaterial} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ArmorMaterial getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ArmorMaterial.class, ordinal);
    }

    /**
     * Get one of ToolMaterial entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ArmorMaterial getByEnumName(final String name)
    {
        return getByEnumName(ArmorMaterial.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ArmorMaterial[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ArmorMaterial.class);
        return (ArmorMaterial[]) map.values(new ArmorMaterial[map.size()]);
    }

}