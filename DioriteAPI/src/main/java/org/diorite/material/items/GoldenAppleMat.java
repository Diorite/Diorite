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
 * Class representing 'Golded Apple' item material in minecraft. <br>
 * ID of material: 322 <br>
 * String ID of material: minecraft:golden_apple <br>
 * Max item stack size: 64 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * ENCHANTED:
 * Type name: 'Enchanted' <br>
 * SubID: 1 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * GOLDED_APPLE:
 * Type name: 'Golded Apple' <br>
 * SubID: 0 <br>
 * Max item stack size: 64
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class GoldenAppleMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 2;

    public static final GoldenAppleMat GOLDED_APPLE           = new GoldenAppleMat();
    public static final GoldenAppleMat GOLDED_ENCHANTED_APPLE = new GoldenAppleMat("ENCHANTED", 0x01);

    private static final Map<String, GoldenAppleMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<GoldenAppleMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected GoldenAppleMat()
    {
        super("GOLDED_APPLE", 322, "minecraft:golden_apple", "GOLDED_APPLE", (short) 0x00, 4, 9.6F);
    }

    @SuppressWarnings("MagicNumber")
    protected GoldenAppleMat(final String name, final int type)
    {
        super(GOLDED_APPLE.name(), GOLDED_APPLE.getId(), GOLDED_APPLE.getMinecraftId(), name, (short) type, 4, 9.6F);
    }

    protected GoldenAppleMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected GoldenAppleMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public GoldenAppleMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public GoldenAppleMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of GoldenApple sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of GoldenApple or null
     */
    public static GoldenAppleMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of GoldenApple sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of GoldenApple or null
     */
    public static GoldenAppleMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final GoldenAppleMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public GoldenAppleMat[] types()
    {
        return GoldenAppleMat.goldenAppleTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static GoldenAppleMat[] goldenAppleTypes()
    {
        return byID.values(new GoldenAppleMat[byID.size()]);
    }

    static
    {
        GoldenAppleMat.register(GOLDED_APPLE);
        GoldenAppleMat.register(GOLDED_ENCHANTED_APPLE);
    }
}

