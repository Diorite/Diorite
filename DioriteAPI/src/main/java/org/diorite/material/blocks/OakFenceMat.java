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

import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Oak Fence' block material in minecraft. <br>
 * ID of block: 85 <br>
 * String ID of block: minecraft:fence <br>
 * Hardness: 2 <br>
 * Blast Resistance 15
 */
@SuppressWarnings("JavaDoc")
public class OakFenceMat extends WoodenFenceMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final OakFenceMat OAK_FENCE = new OakFenceMat();

    private static final Map<String, OakFenceMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<OakFenceMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected OakFenceMat()
    {
        super("OAK_FENCE", 85, "minecraft:fence", "OAK_FENCE", WoodType.OAK, 2, 15);
    }

    protected OakFenceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
    }

    @Override
    public OakFenceMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public OakFenceMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of OakFence sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of OakFence or null
     */
    public static OakFenceMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of OakFence sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of OakFence or null
     */
    public static OakFenceMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final OakFenceMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public OakFenceMat[] types()
    {
        return OakFenceMat.oakFenceTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static OakFenceMat[] oakFenceTypes()
    {
        return byID.values(new OakFenceMat[byID.size()]);
    }

    static
    {
        OakFenceMat.register(OAK_FENCE);
    }
}
