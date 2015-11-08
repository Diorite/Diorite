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
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Nether Brick Fence' block material in minecraft. <br>
 * ID of block: 113 <br>
 * String ID of block: minecraft:nether_brick_fence <br>
 * Hardness: 2 <br>
 * Blast Resistance 30
 */
@SuppressWarnings("JavaDoc")
public class NetherBrickFenceMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final NetherBrickFenceMat NETHER_BRICK_FENCE = new NetherBrickFenceMat();

    private static final Map<String, NetherBrickFenceMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherBrickFenceMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected NetherBrickFenceMat()
    {
        super("NETHER_BRICK_FENCE", 113, "minecraft:nether_brick_fence", "NETHER_BRICK_FENCE", (byte) 0x00, 2, 30);
    }

    protected NetherBrickFenceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public NetherBrickFenceMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherBrickFenceMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of NetherBrickFence sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of NetherBrickFence or null
     */
    public static NetherBrickFenceMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of NetherBrickFence sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of NetherBrickFence or null
     */
    public static NetherBrickFenceMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final NetherBrickFenceMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public NetherBrickFenceMat[] types()
    {
        return NetherBrickFenceMat.netherBrickFenceTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static NetherBrickFenceMat[] netherBrickFenceTypes()
    {
        return byID.values(new NetherBrickFenceMat[byID.size()]);
    }

    static
    {
        NetherBrickFenceMat.register(NETHER_BRICK_FENCE);
    }
}
