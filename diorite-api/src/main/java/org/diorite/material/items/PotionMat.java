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

import org.diorite.inventory.item.meta.PotionMeta;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Potion' item material in minecraft. <br>
 * ID of material: 373 <br>
 * String ID of material: minecraft:potion <br>
 * Max item stack size: 1
 */
@SuppressWarnings("JavaDoc")
public class PotionMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final PotionMat POTION = new PotionMat();

    private static final Map<String, PotionMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<PotionMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected PotionMat()
    {
        super("POTION", 373, "minecraft:potion", 1, "POTION", (short) 0x00, 5, 6);
    }

    protected PotionMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, 1, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected PotionMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    {
        this.metaType = PotionMeta.class;
    }

    @Override
    public PotionMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public PotionMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Potion sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Potion or null
     */
    public static PotionMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Potion sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Potion or null
     */
    public static PotionMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PotionMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PotionMat[] types()
    {
        return PotionMat.potionTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static PotionMat[] potionTypes()
    {
        return byID.values(new PotionMat[byID.size()]);
    }

    static
    {
        PotionMat.register(POTION);
    }
}

