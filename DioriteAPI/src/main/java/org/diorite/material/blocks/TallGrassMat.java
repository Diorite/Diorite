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
 * Class representing 'Tall Grass' block material in minecraft. <br>
 * ID of block: 31 <br>
 * String ID of block: minecraft:tallgrass <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * FERN:
 * Type name: 'Fern' <br>
 * SubID: 2 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * GRASS:
 * Type name: 'Grass' <br>
 * SubID: 1 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * SHRUB:
 * Type name: 'Shrub' <br>
 * SubID: 0 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class TallGrassMat extends FlowerMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 3;

    public static final TallGrassMat TALL_GRASS_SHRUB = new TallGrassMat();
    public static final TallGrassMat TALL_GRASS_GRASS = new TallGrassMat(0x1, FlowerTypeMat.GRASS);
    public static final TallGrassMat TALL_GRASS_FERN  = new TallGrassMat(0x2, FlowerTypeMat.FERN);

    private static final Map<String, TallGrassMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<TallGrassMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected TallGrassMat()
    {
        super("TALL_GRASS", 31, "minecraft:tallgrass", (byte) 0x00, FlowerTypeMat.SHRUB, 0, 0);
    }

    protected TallGrassMat(final int type, final FlowerTypeMat flowerType)
    {
        super(TALL_GRASS_SHRUB.name(), TALL_GRASS_SHRUB.ordinal(), TALL_GRASS_SHRUB.getMinecraftId(), (byte) type, flowerType, TALL_GRASS_SHRUB.getHardness(), TALL_GRASS_SHRUB.getBlastResistance());
    }

    protected TallGrassMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final FlowerTypeMat flowerType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, flowerType, hardness, blastResistance);
    }

    @Override
    public TallGrassMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public TallGrassMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public TallGrassMat getFlowerType(final FlowerTypeMat flowerType)
    {
        return getTallGrass(flowerType);
    }

    /**
     * Returns one of TallGrass sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of TallGrass or null
     */
    public static TallGrassMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of TallGrass sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of TallGrass or null
     */
    public static TallGrassMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of TallGrass sub-type based on {@link FlowerTypeMat}
     * If this flower don't supprot given type, it will return default one.
     *
     * @param flowerType type of flower
     *
     * @return sub-type of TallGrass
     */
    public static TallGrassMat getTallGrass(final FlowerTypeMat flowerType)
    {
        for (final TallGrassMat mat : byName.values())
        {
            if (mat.flowerType == flowerType)
            {
                return mat;
            }
        }
        return TALL_GRASS_SHRUB;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final TallGrassMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public TallGrassMat[] types()
    {
        return TallGrassMat.tallGrassTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static TallGrassMat[] tallGrassTypes()
    {
        return byID.values(new TallGrassMat[byID.size()]);
    }

    static
    {
        TallGrassMat.register(TALL_GRASS_SHRUB);
        TallGrassMat.register(TALL_GRASS_GRASS);
        TallGrassMat.register(TALL_GRASS_FERN);
    }
}
