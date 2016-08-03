/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

/**
 * Class representing 'Cooked Fish' item material in minecraft. <br>
 * ID of material: 350 <br>
 * String ID of material: minecraft:cooked_fish <br>
 * Max item stack size: 64
 */
@SuppressWarnings("JavaDoc")
public class CookedFishMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    // TODO salmon, clownfish and pufferfish
    public static final CookedFishMat COOKED_FISH = new CookedFishMat();

    private static final Map<String, CookedFishMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final Short2ObjectMap<CookedFishMat> byID   = new Short2ObjectOpenHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    public CookedFishMat()
    {
        super("COOKED_FISH", 350, "minecraft:cooked_fish", "COOKED_FISH", (short) 0x00, 5, 6);
    }

    public CookedFishMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    public CookedFishMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public CookedFishMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public CookedFishMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of CookedFish sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CookedFish or null
     */
    public static CookedFishMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of CookedFish sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CookedFish or null
     */
    public static CookedFishMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CookedFishMat element)
    {
        allItems.incrementAndGet();
        byID.put((short) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CookedFishMat[] types()
    {
        return CookedFishMat.cookedFishTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static CookedFishMat[] cookedFishTypes()
    {
        return byID.values().toArray(new CookedFishMat[byID.size()]);
    }

    static
    {
        CookedFishMat.register(COOKED_FISH);
    }
}

