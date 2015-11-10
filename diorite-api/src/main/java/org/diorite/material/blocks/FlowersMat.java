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
 * Class representing 'Flowers' block material in minecraft. <br>
 * ID of block: 38 <br>
 * String ID of block: minecraft:red_flower <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * OXEYE_DAISY:
 * Type name: 'Oxeye Daisy' <br>
 * SubID: 8 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * PINK_TULIP:
 * Type name: 'Pink Tulip' <br>
 * SubID: 7 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * WHITE_TULIP:
 * Type name: 'White Tulip' <br>
 * SubID: 6 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ORANGE_TULIP:
 * Type name: 'Orange Tulip' <br>
 * SubID: 5 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * RED_TULIP:
 * Type name: 'Red Tulip' <br>
 * SubID: 4 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * AZURE_BLUET:
 * Type name: 'Azure Bluet' <br>
 * SubID: 3 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ALLIUM:
 * Type name: 'Allium' <br>
 * SubID: 2 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * BLUE_ORCHID:
 * Type name: 'Blue Orchid' <br>
 * SubID: 1 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * POPPY:
 * Type name: 'Poppy' <br>
 * SubID: 0 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class FlowersMat extends FlowerMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 9;

    public static final FlowersMat FLOWERS_POPPY        = new FlowersMat();
    public static final FlowersMat FLOWERS_BLUE_ORCHID  = new FlowersMat(0x1, FlowerTypeMat.BLUE_ORCHID);
    public static final FlowersMat FLOWERS_ALLIUM       = new FlowersMat(0x2, FlowerTypeMat.ALLIUM);
    public static final FlowersMat FLOWERS_AZURE_BLUET  = new FlowersMat(0x3, FlowerTypeMat.AZURE_BLUET);
    public static final FlowersMat FLOWERS_RED_TULIP    = new FlowersMat(0x4, FlowerTypeMat.RED_TULIP);
    public static final FlowersMat FLOWERS_ORANGE_TULIP = new FlowersMat(0x5, FlowerTypeMat.ORANGE_TULIP);
    public static final FlowersMat FLOWERS_WHITE_TULIP  = new FlowersMat(0x6, FlowerTypeMat.WHITE_TULIP);
    public static final FlowersMat FLOWERS_PINK_TULIP   = new FlowersMat(0x7, FlowerTypeMat.PINK_TULIP);
    public static final FlowersMat FLOWERS_OXEYE_DAISY  = new FlowersMat(0x8, FlowerTypeMat.OXEYE_DAISY);

    private static final Map<String, FlowersMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<FlowersMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected FlowersMat()
    {
        super("FLOWERS", 38, "minecraft:red_flower", (byte) 0x00, FlowerTypeMat.POPPY, 0, 0);
    }

    protected FlowersMat(final int type, final FlowerTypeMat flowerType)
    {
        super(FLOWERS_POPPY.name(), FLOWERS_POPPY.ordinal(), FLOWERS_POPPY.getMinecraftId(), (byte) type, flowerType, FLOWERS_POPPY.getHardness(), FLOWERS_POPPY.getBlastResistance());
    }

    protected FlowersMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final FlowerTypeMat flowerType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, flowerType, hardness, blastResistance);
    }

    @Override
    public FlowersMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public FlowersMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public FlowersMat getFlowerType(final FlowerTypeMat flowerType)
    {
        return getFlowers(flowerType);
    }

    /**
     * Returns one of Flowers sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Flowers or null
     */
    public static FlowersMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Flowers sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Flowers or null
     */
    public static FlowersMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Flowers sub-type based on {@link FlowerTypeMat}
     * If this flower don't supprot given type, it will return default one.
     *
     * @param flowerType type of flower
     *
     * @return sub-type of Flowers
     */
    public static FlowersMat getFlowers(final FlowerTypeMat flowerType)
    {
        for (final FlowersMat mat : byName.values())
        {
            if (mat.flowerType == flowerType)
            {
                return mat;
            }
        }
        return FLOWERS_POPPY;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FlowersMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public FlowersMat[] types()
    {
        return FlowersMat.flowersTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static FlowersMat[] flowersTypes()
    {
        return byID.values(new FlowersMat[byID.size()]);
    }

    static
    {
        FlowersMat.register(FLOWERS_POPPY);
        FlowersMat.register(FLOWERS_BLUE_ORCHID);
        FlowersMat.register(FLOWERS_ALLIUM);
        FlowersMat.register(FLOWERS_AZURE_BLUET);
        FlowersMat.register(FLOWERS_RED_TULIP);
        FlowersMat.register(FLOWERS_ORANGE_TULIP);
        FlowersMat.register(FLOWERS_WHITE_TULIP);
        FlowersMat.register(FLOWERS_PINK_TULIP);
        FlowersMat.register(FLOWERS_OXEYE_DAISY);
    }
}
