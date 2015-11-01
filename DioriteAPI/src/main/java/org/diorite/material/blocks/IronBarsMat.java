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

package org.diorite.material.blocks;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.FenceMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Iron Bars' block material in minecraft. <br>
 * ID of block: 101 <br>
 * String ID of block: minecraft:iron_bars <br>
 * Hardness: 5 <br>
 * Blast Resistance 30
 */
@SuppressWarnings("JavaDoc")
public class IronBarsMat extends BlockMaterialData implements FenceMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final IronBarsMat IRON_BARS = new IronBarsMat();

    private static final Map<String, IronBarsMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronBarsMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected IronBarsMat()
    {
        super("IRON_BARS", 101, "minecraft:iron_bars", "IRON_BARS", (byte) 0x00, 5, 30);
    }

    protected IronBarsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public IronBarsMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronBarsMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of IronBars sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of IronBars or null
     */
    public static IronBarsMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of IronBars sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronBars or null
     */
    public static IronBarsMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IronBarsMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public IronBarsMat[] types()
    {
        return IronBarsMat.ironBarsTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static IronBarsMat[] ironBarsTypes()
    {
        return byID.values(new IronBarsMat[byID.size()]);
    }

    static
    {
        IronBarsMat.register(IRON_BARS);
    }
}
