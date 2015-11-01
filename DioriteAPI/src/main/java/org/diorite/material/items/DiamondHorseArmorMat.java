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

package org.diorite.material.items;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Diamond Horse Armor' item material in minecraft. <br>
 * ID of material: 419 <br>
 * String ID of material: minecraft:diamond_horse_armor <br>
 * Max item stack size: 1
 */
@SuppressWarnings("JavaDoc")
public class DiamondHorseArmorMat extends AbstractHorseArmor
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DiamondHorseArmorMat DIAMOND_HORSE_ARMOR = new DiamondHorseArmorMat();

    private static final Map<String, DiamondHorseArmorMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<DiamondHorseArmorMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DiamondHorseArmorMat()
    {
        super("DIAMOND_HORSE_ARMOR", 419, "minecraft:diamond_horse_armor", "DIAMOND_HORSE_ARMOR", (short) 0x00);
    }

    protected DiamondHorseArmorMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected DiamondHorseArmorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public DiamondHorseArmorMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public DiamondHorseArmorMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of DiamondHorseArmor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DiamondHorseArmor or null
     */
    public static DiamondHorseArmorMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of DiamondHorseArmor sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DiamondHorseArmor or null
     */
    public static DiamondHorseArmorMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DiamondHorseArmorMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DiamondHorseArmorMat[] types()
    {
        return DiamondHorseArmorMat.diamondHorseArmorTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static DiamondHorseArmorMat[] diamondHorseArmorTypes()
    {
        return byID.values(new DiamondHorseArmorMat[byID.size()]);
    }

    static
    {
        DiamondHorseArmorMat.register(DIAMOND_HORSE_ARMOR);
    }
}

