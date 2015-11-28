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

import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;

/**
 * Class representing 'Dandelion' block material in minecraft. <br>
 * ID of block: 37 <br>
 * String ID of block: minecraft:yellow_flower <br>
 * Hardness: 0 <br>
 * Blast Resistance 0
 */
@SuppressWarnings("JavaDoc")
public class DandelionMat extends FlowerMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DandelionMat DANDELION = new DandelionMat();

    private static final Map<String, DandelionMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final Byte2ObjectMap<DandelionMat> byID   = new Byte2ObjectOpenHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DandelionMat()
    {
        super("DANDELION", 37, "minecraft:yellow_flower", (byte) 0x00, FlowerTypeMat.DANDELION, 0, 0);
    }

    protected DandelionMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final FlowerTypeMat flowerType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, flowerType, hardness, blastResistance);
    }

    @Override
    public DandelionMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DandelionMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DandelionMat getFlowerType(final FlowerTypeMat flowerType)
    {
        return getDandelion(flowerType);
    }

    /**
     * Returns one of Dandelion sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Dandelion or null
     */
    public static DandelionMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Dandelion sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Dandelion or null
     */
    public static DandelionMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Dandelion sub-type based on {@link FlowerTypeMat}
     * If this flower don't supprot given type, it will return default one.
     *
     * @param flowerType type of flower
     *
     * @return sub-type of Dandelion
     */
    public static DandelionMat getDandelion(final FlowerTypeMat flowerType)
    {
        for (final DandelionMat mat : byName.values())
        {
            if (mat.flowerType == flowerType)
            {
                return mat;
            }
        }
        return DANDELION;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DandelionMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DandelionMat[] types()
    {
        return DandelionMat.dandelionTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DandelionMat[] dandelionTypes()
    {
        return byID.values().toArray(new DandelionMat[byID.size()]);
    }

    static
    {
        DandelionMat.register(DANDELION);
    }
}
