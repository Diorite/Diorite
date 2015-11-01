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

import org.diorite.material.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Diamond Block' block material in minecraft. <br>
 * ID of block: 57 <br>
 * String ID of block: minecraft:diamond_block <br>
 * Hardness: 5 <br>
 * Blast Resistance 30
 */
@SuppressWarnings("JavaDoc")
public class DiamondBlockMat extends OreBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DiamondBlockMat DIAMOND_BLOCK = new DiamondBlockMat();

    private static final Map<String, DiamondBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DiamondBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DiamondBlockMat()
    {
        super("DIAMOND_BLOCK", 57, "minecraft:diamond_block", "DIAMOND_BLOCK", (byte) 0x00, DIAMOND_ORE, DIAMOND, 5, 30);
    }

    protected DiamondBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreMat ore, final OreItemMat item, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, ore, item, hardness, blastResistance);
    }

    @Override
    public DiamondBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DiamondBlockMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DiamondBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DiamondBlock or null
     */
    public static DiamondBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DiamondBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DiamondBlock or null
     */
    public static DiamondBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DiamondBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DiamondBlockMat[] types()
    {
        return DiamondBlockMat.diamondBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DiamondBlockMat[] diamondBlockTypes()
    {
        return byID.values(new DiamondBlockMat[byID.size()]);
    }

    static
    {
        DiamondBlockMat.register(DIAMOND_BLOCK);
    }
}
