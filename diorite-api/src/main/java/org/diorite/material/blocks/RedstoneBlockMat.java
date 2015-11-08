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
 * Class representing 'Redstone Block' block material in minecraft. <br>
 * ID of block: 152 <br>
 * String ID of block: minecraft:redstone_block <br>
 * Hardness: 5 <br>
 * Blast Resistance 30
 */
@SuppressWarnings("JavaDoc")
public class RedstoneBlockMat extends OreBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RedstoneBlockMat REDSTONE_BLOCK = new RedstoneBlockMat();

    private static final Map<String, RedstoneBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected RedstoneBlockMat()
    {
        super("REDSTONE_BLOCK", 152, "minecraft:redstone_block", "REDSTONE_BLOCK", (byte) 0x00, REDSTONE_ORE, REDSTONE, 5, 30);
    }

    protected RedstoneBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreMat ore, final OreItemMat item, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, ore, item, hardness, blastResistance);
    }

    @Override
    public RedstoneBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneBlockMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of RedstoneBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneBlock or null
     */
    public static RedstoneBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneBlock or null
     */
    public static RedstoneBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneBlockMat[] types()
    {
        return RedstoneBlockMat.redstoneBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneBlockMat[] redstoneBlockTypes()
    {
        return byID.values(new RedstoneBlockMat[byID.size()]);
    }

    static
    {
        RedstoneBlockMat.register(REDSTONE_BLOCK);
    }
}
