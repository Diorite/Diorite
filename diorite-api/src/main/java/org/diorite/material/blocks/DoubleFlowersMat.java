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
 * Class representing 'Double Flowers' block material in minecraft. <br>
 * ID of block: 175 <br>
 * String ID of block: minecraft:double_plant <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * DOUBLE_TOP:
 * Type name: 'Double Top' <br>
 * SubID: 8 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * PEONY:
 * Type name: 'Peony' <br>
 * SubID: 5 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ROSE_BUSH:
 * Type name: 'Rose Bush' <br>
 * SubID: 4 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * TALL_FERN:
 * Type name: 'Tall Fern' <br>
 * SubID: 3 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * TALL_GRASS:
 * Type name: 'Tall Grass' <br>
 * SubID: 2 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * LILAC:
 * Type name: 'Lilac' <br>
 * SubID: 1 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * SUNFLOWER:
 * Type name: 'Sunflower' <br>
 * SubID: 0 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class DoubleFlowersMat extends FlowerMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 7;

    public static final DoubleFlowersMat DOUBLE_FLOWERS_SUNFLOWER  = new DoubleFlowersMat();
    public static final DoubleFlowersMat DOUBLE_FLOWERS_LILAC      = new DoubleFlowersMat(0x1, FlowerTypeMat.LILAC);
    public static final DoubleFlowersMat DOUBLE_FLOWERS_TALL_GRASS = new DoubleFlowersMat(0x2, FlowerTypeMat.TALL_GRASS);
    public static final DoubleFlowersMat DOUBLE_FLOWERS_TALL_FERN  = new DoubleFlowersMat(0x3, FlowerTypeMat.TALL_FERN);
    public static final DoubleFlowersMat DOUBLE_FLOWERS_ROSE_BUSH  = new DoubleFlowersMat(0x4, FlowerTypeMat.ROSE_BUSH);
    public static final DoubleFlowersMat DOUBLE_FLOWERS_PEONY      = new DoubleFlowersMat(0x5, FlowerTypeMat.PEONY);
    public static final DoubleFlowersMat DOUBLE_FLOWERS_DOUBLE_TOP = new DoubleFlowersMat(0x8, FlowerTypeMat.DOUBLE_TOP);

    private static final Map<String, DoubleFlowersMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DoubleFlowersMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DoubleFlowersMat()
    {
        super("DOUBLE_FLOWERS", 175, "minecraft:double_plant", (byte) 0x00, FlowerTypeMat.SUNFLOWER, 0, 0);
    }

    protected DoubleFlowersMat(final int type, final FlowerTypeMat flowerType)
    {
        super(DOUBLE_FLOWERS_SUNFLOWER.name(), DOUBLE_FLOWERS_SUNFLOWER.ordinal(), DOUBLE_FLOWERS_SUNFLOWER.getMinecraftId(), (byte) type, flowerType, DOUBLE_FLOWERS_SUNFLOWER.getHardness(), DOUBLE_FLOWERS_SUNFLOWER.getBlastResistance());
    }

    protected DoubleFlowersMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final FlowerTypeMat flowerType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, flowerType, hardness, blastResistance);
    }

    @Override
    public DoubleFlowersMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DoubleFlowersMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DoubleFlowersMat getFlowerType(final FlowerTypeMat flowerType)
    {
        return getDoubleFlowers(flowerType);
    }

    /**
     * Returns one of DoubleFlowers sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DoubleFlowers or null
     */
    public static DoubleFlowersMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DoubleFlowers sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DoubleFlowers or null
     */
    public static DoubleFlowersMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DoubleFlowers sub-type based on {@link FlowerTypeMat}
     * If this flower don't supprot given type, it will return default one.
     *
     * @param flowerType type of flower
     *
     * @return sub-type of DoubleFlowers
     */
    public static DoubleFlowersMat getDoubleFlowers(final FlowerTypeMat flowerType)
    {
        for (final DoubleFlowersMat mat : byName.values())
        {
            if (mat.flowerType == flowerType)
            {
                return mat;
            }
        }
        return DOUBLE_FLOWERS_SUNFLOWER;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DoubleFlowersMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DoubleFlowersMat[] types()
    {
        return DoubleFlowersMat.doubleFlowersTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DoubleFlowersMat[] doubleFlowersTypes()
    {
        return byID.values(new DoubleFlowersMat[byID.size()]);
    }

    static
    {
        DoubleFlowersMat.register(DOUBLE_FLOWERS_SUNFLOWER);
        DoubleFlowersMat.register(DOUBLE_FLOWERS_LILAC);
        DoubleFlowersMat.register(DOUBLE_FLOWERS_TALL_GRASS);
        DoubleFlowersMat.register(DOUBLE_FLOWERS_TALL_FERN);
        DoubleFlowersMat.register(DOUBLE_FLOWERS_ROSE_BUSH);
        DoubleFlowersMat.register(DOUBLE_FLOWERS_PEONY);
        DoubleFlowersMat.register(DOUBLE_FLOWERS_DOUBLE_TOP);
    }
}
