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

import org.diorite.material.FuelMat;
import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Blaze Rod' item material in minecraft. <br>
 * ID of material: 369 <br>
 * String ID of material: minecraft:blaze_rod <br>
 * Max item stack size: 64
 */
@SuppressWarnings("JavaDoc")
public class BlazeRodMat extends ItemMaterialData implements FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final BlazeRodMat BLAZE_ROD = new BlazeRodMat();

    private static final Map<String, BlazeRodMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<BlazeRodMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected BlazeRodMat()
    {
        super("BLAZE_ROD", 369, "minecraft:blaze_rod", "BLAZE_ROD", (short) 0x00);
    }

    protected BlazeRodMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected BlazeRodMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public BlazeRodMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public BlazeRodMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 12000;
    }

    /**
     * Returns one of BlazeRod sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BlazeRod or null
     */
    public static BlazeRodMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of BlazeRod sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BlazeRod or null
     */
    public static BlazeRodMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BlazeRodMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BlazeRodMat[] types()
    {
        return BlazeRodMat.blazeRodTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static BlazeRodMat[] blazeRodTypes()
    {
        return byID.values(new BlazeRodMat[byID.size()]);
    }

    static
    {
        BlazeRodMat.register(BLAZE_ROD);
    }
}

