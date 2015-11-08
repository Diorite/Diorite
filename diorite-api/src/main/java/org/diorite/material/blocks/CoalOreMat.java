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
 * Class representing 'Coal Ore' block material in minecraft. <br>
 * ID of block: 16 <br>
 * String ID of block: minecraft:coal_ore <br>
 * Hardness: 3 <br>
 * Blast Resistance 15
 */
@SuppressWarnings("JavaDoc")
public class CoalOreMat extends OreMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final CoalOreMat COAL_ORE = new CoalOreMat();

    private static final Map<String, CoalOreMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CoalOreMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected CoalOreMat()
    {
        super("COAL_ORE", 16, "minecraft:coal_ore", "COAL_ORE", (byte) 0x00, COAL, COAL_BLOCK, 3, 15);
    }

    protected CoalOreMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreItemMat item, final OreBlockMat block, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, item, block, hardness, blastResistance);
    }

    @Override
    public CoalOreMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CoalOreMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of CoalOre sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CoalOre or null
     */
    public static CoalOreMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of CoalOre sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CoalOre or null
     */
    public static CoalOreMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CoalOreMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CoalOreMat[] types()
    {
        return CoalOreMat.coalOreTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CoalOreMat[] coalOreTypes()
    {
        return byID.values(new CoalOreMat[byID.size()]);
    }

    static
    {
        CoalOreMat.register(COAL_ORE);
    }
}
