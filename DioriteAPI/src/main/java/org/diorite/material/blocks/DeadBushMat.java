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

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Dead Bush' block material in minecraft. <br>
 * ID of block: 32 <br>
 * String ID of block: minecraft:deadbush <br>
 * Hardness: 0 <br>
 * Blast Resistance 0
 */
@SuppressWarnings("JavaDoc")
public class DeadBushMat extends FlowerMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DeadBushMat DEAD_BUSH = new DeadBushMat();

    private static final Map<String, DeadBushMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DeadBushMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DeadBushMat()
    {
        super("DEAD_BUSH", 32, "minecraft:deadbush", (byte) 0x00, FlowerTypeMat.DEAD_BUSH, 0, 0);
    }

    protected DeadBushMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final FlowerTypeMat flowerType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, flowerType, hardness, blastResistance);
    }

    @Override
    public DeadBushMat getFlowerType(final FlowerTypeMat flowerType)
    {
        return getDeadBush(flowerType);
    }

    @Override
    public DeadBushMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DeadBushMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DeadBush sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DeadBush or null
     */
    public static DeadBushMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DeadBush sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DeadBush or null
     */
    public static DeadBushMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DeadBush sub-type based on {@link FlowerTypeMat}
     * If this flower don't supprot given type, it will return default one.
     *
     * @param flowerType type of flower
     *
     * @return sub-type of DeadBush
     */
    public static DeadBushMat getDeadBush(final FlowerTypeMat flowerType)
    {
        for (final DeadBushMat mat : byName.values())
        {
            if (mat.flowerType == flowerType)
            {
                return mat;
            }
        }
        return DEAD_BUSH;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DeadBushMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DeadBushMat[] types()
    {
        return DeadBushMat.deadBushTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DeadBushMat[] deadBushTypes()
    {
        return byID.values(new DeadBushMat[byID.size()]);
    }

    static
    {
        DeadBushMat.register(DEAD_BUSH);
    }
}
