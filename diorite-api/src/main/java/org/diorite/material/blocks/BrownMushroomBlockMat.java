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

import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Brown Mushroom Block' block material in minecraft. <br>
 * ID of block: 99 <br>
 * String ID of block: minecraft:brown_mushroom_block <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * STEAM_FULL:
 * Type name: 'Steam Full' <br>
 * SubID: 15 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * CAP_FULL:
 * Type name: 'Cap Full' <br>
 * SubID: 14 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * STEAM:
 * Type name: 'Steam' <br>
 * SubID: 10 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * CAP_SOUTH_EAST:
 * Type name: 'Cap South East' <br>
 * SubID: 9 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * CAP_SOUTH:
 * Type name: 'Cap South' <br>
 * SubID: 8 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * CAP_SOUTH_WEST:
 * Type name: 'Cap South West' <br>
 * SubID: 7 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * CAP_EAST:
 * Type name: 'Cap East' <br>
 * SubID: 6 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * CAP:
 * Type name: 'Cap' <br>
 * SubID: 5 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * CAP_WEST:
 * Type name: 'Cap West' <br>
 * SubID: 4 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * CAP_NORTH_EAST:
 * Type name: 'Cap North East' <br>
 * SubID: 3 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * CAP_NORTH:
 * Type name: 'Cap North' <br>
 * SubID: 2 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * CAP_NORTH_WEST:
 * Type name: 'Cap North West' <br>
 * SubID: 1 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * PORES_FULL:
 * Type name: 'Pores Full' <br>
 * SubID: 0 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class BrownMushroomBlockMat extends MushroomBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 13;

    public static final BrownMushroomBlockMat BROWN_MUSHROOM_BLOCK_PORES_FULL     = new BrownMushroomBlockMat();
    public static final BrownMushroomBlockMat BROWN_MUSHROOM_BLOCK_CAP_NORTH_WEST = new BrownMushroomBlockMat(Type.CAP_NORTH_WEST);
    public static final BrownMushroomBlockMat BROWN_MUSHROOM_BLOCK_CAP_NORTH      = new BrownMushroomBlockMat(Type.CAP_NORTH);
    public static final BrownMushroomBlockMat BROWN_MUSHROOM_BLOCK_CAP_NORTH_EAST = new BrownMushroomBlockMat(Type.CAP_NORTH_EAST);
    public static final BrownMushroomBlockMat BROWN_MUSHROOM_BLOCK_CAP_WEST       = new BrownMushroomBlockMat(Type.CAP_WEST);
    public static final BrownMushroomBlockMat BROWN_MUSHROOM_BLOCK_CAP            = new BrownMushroomBlockMat(Type.CAP);
    public static final BrownMushroomBlockMat BROWN_MUSHROOM_BLOCK_CAP_EAST       = new BrownMushroomBlockMat(Type.CAP_EAST);
    public static final BrownMushroomBlockMat BROWN_MUSHROOM_BLOCK_CAP_SOUTH_WEST = new BrownMushroomBlockMat(Type.CAP_SOUTH_WEST);
    public static final BrownMushroomBlockMat BROWN_MUSHROOM_BLOCK_CAP_SOUTH      = new BrownMushroomBlockMat(Type.CAP_SOUTH);
    public static final BrownMushroomBlockMat BROWN_MUSHROOM_BLOCK_CAP_SOUTH_EAST = new BrownMushroomBlockMat(Type.CAP_SOUTH_EAST);
    public static final BrownMushroomBlockMat BROWN_MUSHROOM_BLOCK_STEAM          = new BrownMushroomBlockMat(Type.STEAM);
    public static final BrownMushroomBlockMat BROWN_MUSHROOM_BLOCK_CAP_FULL       = new BrownMushroomBlockMat(Type.CAP_FULL);
    public static final BrownMushroomBlockMat BROWN_MUSHROOM_BLOCK_STEAM_FULL     = new BrownMushroomBlockMat(Type.STEAM_FULL);

    private static final Map<String, BrownMushroomBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BrownMushroomBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected BrownMushroomBlockMat()
    {
        super("BROWN_MUSHROOM_BLOCK", 99, "minecraft:brown_mushroom_block", Type.PORES_FULL, 0.2f, 1);
    }

    protected BrownMushroomBlockMat(final Type type)
    {
        super(BROWN_MUSHROOM_BLOCK_PORES_FULL.name(), BROWN_MUSHROOM_BLOCK_PORES_FULL.ordinal(), BROWN_MUSHROOM_BLOCK_PORES_FULL.getMinecraftId(), type, BROWN_MUSHROOM_BLOCK_PORES_FULL.getHardness(), BROWN_MUSHROOM_BLOCK_PORES_FULL.getBlastResistance());
    }

    protected BrownMushroomBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final Type mushroomType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, mushroomType, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return BROWN_MUSHROOM_BLOCK;
    }

    @Override
    public BrownMushroomBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BrownMushroomBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public BrownMushroomBlockMat getMushroomType(final Type mushroomType)
    {
        return getByID(mushroomType.getFlag());
    }

    /**
     * Returns one of BrownMushroomBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BrownMushroomBlock or null
     */
    public static BrownMushroomBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BrownMushroomBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BrownMushroomBlock or null
     */
    public static BrownMushroomBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of BrownMushroomBlock sub-type based on {@link Type}.
     * It will never return null;
     *
     * @param type type of mushroom texture/block.
     *
     * @return sub-type of BrownMushroomBlock
     */
    public static BrownMushroomBlockMat getBrownMushroomBlock(final Type type)
    {
        return getByID(type.getFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BrownMushroomBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BrownMushroomBlockMat[] types()
    {
        return BrownMushroomBlockMat.brownMushroomBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BrownMushroomBlockMat[] brownMushroomBlockTypes()
    {
        return byID.values(new BrownMushroomBlockMat[byID.size()]);
    }

    static
    {
        BrownMushroomBlockMat.register(BROWN_MUSHROOM_BLOCK_PORES_FULL);
        BrownMushroomBlockMat.register(BROWN_MUSHROOM_BLOCK_CAP_NORTH_WEST);
        BrownMushroomBlockMat.register(BROWN_MUSHROOM_BLOCK_CAP_NORTH);
        BrownMushroomBlockMat.register(BROWN_MUSHROOM_BLOCK_CAP_NORTH_EAST);
        BrownMushroomBlockMat.register(BROWN_MUSHROOM_BLOCK_CAP_WEST);
        BrownMushroomBlockMat.register(BROWN_MUSHROOM_BLOCK_CAP);
        BrownMushroomBlockMat.register(BROWN_MUSHROOM_BLOCK_CAP_EAST);
        BrownMushroomBlockMat.register(BROWN_MUSHROOM_BLOCK_CAP_SOUTH_WEST);
        BrownMushroomBlockMat.register(BROWN_MUSHROOM_BLOCK_CAP_SOUTH);
        BrownMushroomBlockMat.register(BROWN_MUSHROOM_BLOCK_CAP_SOUTH_EAST);
        BrownMushroomBlockMat.register(BROWN_MUSHROOM_BLOCK_STEAM);
        BrownMushroomBlockMat.register(BROWN_MUSHROOM_BLOCK_CAP_FULL);
        BrownMushroomBlockMat.register(BROWN_MUSHROOM_BLOCK_STEAM_FULL);
    }
}
