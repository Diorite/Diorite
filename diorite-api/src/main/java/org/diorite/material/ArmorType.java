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

import org.diorite.inventory.EntityEquipment;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

/**
 * Represent type of armor element.
 */
@SuppressWarnings("JavaDoc")
public abstract class ArmorType extends ASimpleEnum<ArmorType>
{
    static
    {
        init(ArmorType.class, 4);
    }

    public static final ArmorType HELMET     = new ArmorType("HELMET")
    {
        @Override
        public boolean setItem(final EntityEquipment eq, final ItemStack armor)
        {
            eq.setHelmet(armor);
            return true;
        }
    };
    public static final ArmorType CHESTPLATE = new ArmorType("CHESTPLATE")
    {
        @Override
        public boolean setItem(final EntityEquipment eq, final ItemStack armor)
        {
            eq.setChestplate(armor);
            return true;
        }
    };
    public static final ArmorType LEGGINGS   = new ArmorType("LEGGINGS")
    {
        @Override
        public boolean setItem(final EntityEquipment eq, final ItemStack armor)
        {
            eq.setLeggings(armor);
            return true;
        }
    };
    public static final ArmorType BOOTS      = new ArmorType("BOOTS")
    {
        @Override
        public boolean setItem(final EntityEquipment eq, final ItemStack armor)
        {
            eq.setBoots(armor);
            return true;
        }
    };

    protected ArmorType(final String enumName, final int enumId)
    {
        super(enumName, enumId);
    }

    protected ArmorType(final String enumName)
    {
        super(enumName);
    }

    /**
     * Register new {@link ArmorType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ArmorType element)
    {
        ASimpleEnum.register(ArmorType.class, element);
    }

    /**
     * Get one of {@link ArmorType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ArmorType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ArmorType.class, ordinal);
    }

    /**
     * Get one of ToolType entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ArmorType getByEnumName(final String name)
    {
        return getByEnumName(ArmorType.class, name);
    }

    /**
     * Put the given ItemStack into the given armor slot. This does not check if
     * the ItemStack is a valid type for this slot.
     *
     * @param eq    entity equipment to use.
     * @param armor The ItemStack to use as armor.
     *
     * @return true if item was set.
     */
    public abstract boolean setItem(final EntityEquipment eq, final ItemStack armor);

    /**
     * @return all values in array.
     */
    public static ArmorType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ArmorType.class);
        return (ArmorType[]) map.values(new ArmorType[map.size()]);
    }

    static
    {
        ArmorType.register(HELMET);
        ArmorType.register(CHESTPLATE);
        ArmorType.register(LEGGINGS);
        ArmorType.register(BOOTS);
    }
}