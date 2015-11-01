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
import org.diorite.material.SlabTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Stone Slab' block material in minecraft. <br>
 * ID of block: 44 <br>
 * String ID of block: minecraft:stone_slab <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * RED_SANDSTONE_UPPER:
 * Type name: 'Red Sandstone Upper' <br>
 * SubID: 24 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * RED_SANDSTONE:
 * Type name: 'Red Sandstone' <br>
 * SubID: 16 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * QUARTZ_UPPER:
 * Type name: 'Quartz Upper' <br>
 * SubID: 15 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * NETHER_BRICKS_UPPER:
 * Type name: 'Nether Bricks Upper' <br>
 * SubID: 14 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * STONE_BRICKS_UPPER:
 * Type name: 'Stone Bricks Upper' <br>
 * SubID: 13 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * BRICKS_UPPER:
 * Type name: 'Bricks Upper' <br>
 * SubID: 12 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * COBBLESTONE_UPPER:
 * Type name: 'Cobblestone Upper' <br>
 * SubID: 11 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * WOODEN_UPPER:
 * Type name: 'Wooden Upper' <br>
 * SubID: 10 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * SANDSTONE_UPPER:
 * Type name: 'Sandstone Upper' <br>
 * SubID: 9 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * STONE_UPPER:
 * Type name: 'Stone Upper' <br>
 * SubID: 8 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * QUARTZ:
 * Type name: 'Quartz' <br>
 * SubID: 7 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * NETHER_BRICKS:
 * Type name: 'Nether Bricks' <br>
 * SubID: 6 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * STONE_BRICKS:
 * Type name: 'Stone Bricks' <br>
 * SubID: 5 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * BRICKS:
 * Type name: 'Bricks' <br>
 * SubID: 4 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * COBBLESTONE:
 * Type name: 'Cobblestone' <br>
 * SubID: 3 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * WOODEN:
 * Type name: 'Wooden' <br>
 * SubID: 2 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * SANDSTONE:
 * Type name: 'Sandstone' <br>
 * SubID: 1 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * STONE:
 * Type name: 'Stone' <br>
 * SubID: 0 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class StoneSlabMat extends StonySlabMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 18;

    public static final StoneSlabMat STONE_SLAB_STONE         = new StoneSlabMat();
    public static final StoneSlabMat STONE_SLAB_SANDSTONE     = new StoneSlabMat("SANDSTONE", SlabTypeMat.BOTTOM, StoneSlabTypeMat.SANDSTONE);
    public static final StoneSlabMat STONE_SLAB_WOODEN        = new StoneSlabMat("WOODEN", SlabTypeMat.BOTTOM, StoneSlabTypeMat.WOODEN);
    public static final StoneSlabMat STONE_SLAB_COBBLESTONE   = new StoneSlabMat("COBBLESTONE", SlabTypeMat.BOTTOM, StoneSlabTypeMat.COBBLESTONE);
    public static final StoneSlabMat STONE_SLAB_BRICKS        = new StoneSlabMat("BRICKS", SlabTypeMat.BOTTOM, StoneSlabTypeMat.BRICKS);
    public static final StoneSlabMat STONE_SLAB_STONE_BRICKS  = new StoneSlabMat("STONE_BRICKS", SlabTypeMat.BOTTOM, StoneSlabTypeMat.STONE_BRICKS);
    public static final StoneSlabMat STONE_SLAB_NETHER_BRICKS = new StoneSlabMat("NETHER_BRICKS", SlabTypeMat.BOTTOM, StoneSlabTypeMat.NETHER_BRICKS);
    public static final StoneSlabMat STONE_SLAB_QUARTZ        = new StoneSlabMat("QUARTZ", SlabTypeMat.BOTTOM, StoneSlabTypeMat.QUARTZ);
    public static final StoneSlabMat STONE_SLAB_RED_SANDSTONE = new StoneSlab2("RED_SANDSTONE", SlabTypeMat.BOTTOM, StoneSlabTypeMat.RED_SANDSTONE);

    public static final StoneSlabMat STONE_SLAB_STONE_UPPER         = new StoneSlabMat("STONE_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.STONE);
    public static final StoneSlabMat STONE_SLAB_SANDSTONE_UPPER     = new StoneSlabMat("SANDSTONE_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.SANDSTONE);
    public static final StoneSlabMat STONE_SLAB_WOODEN_UPPER        = new StoneSlabMat("WOODEN_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.WOODEN);
    public static final StoneSlabMat STONE_SLAB_COBBLESTONE_UPPER   = new StoneSlabMat("COBBLESTONE_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.COBBLESTONE);
    public static final StoneSlabMat STONE_SLAB_BRICKS_UPPER        = new StoneSlabMat("BRICKS_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.BRICKS);
    public static final StoneSlabMat STONE_SLAB_STONE_BRICKS_UPPER  = new StoneSlabMat("STONE_BRICKS_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.STONE_BRICKS);
    public static final StoneSlabMat STONE_SLAB_NETHER_BRICKS_UPPER = new StoneSlabMat("NETHER_BRICKS_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.NETHER_BRICKS);
    public static final StoneSlabMat STONE_SLAB_QUARTZ_UPPER        = new StoneSlabMat("QUARTZ_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.QUARTZ);
    public static final StoneSlabMat STONE_SLAB_RED_SANDSTONE_UPPER = new StoneSlab2("RED_SANDSTONE_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.RED_SANDSTONE);

    private static final Map<String, StoneSlabMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StoneSlabMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected StoneSlabMat()
    {
        super("STONE_SLAB", 44, "minecraft:stone_slab", "STONE", SlabTypeMat.BOTTOM, StoneSlabTypeMat.STONE, 2, 30);
    }

    @SuppressWarnings("MagicNumber")
    protected StoneSlabMat(final String enumName, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
    {
        super(STONE_SLAB_STONE.name() + (stoneType.isSecondStoneSlabID() ? "2" : ""), stoneType.isSecondStoneSlabID() ? 182 : 44, STONE_SLAB_STONE.getMinecraftId() + (stoneType.isSecondStoneSlabID() ? "2" : ""), enumName, slabType, stoneType, STONE_SLAB_STONE.getHardness(), STONE_SLAB_STONE.getBlastResistance());
    }

    @SuppressWarnings("MagicNumber")
    protected StoneSlabMat(final String enumName, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType, final float hardness, final float blastResistance)
    {
        super(STONE_SLAB_STONE.name() + (stoneType.isSecondStoneSlabID() ? "2" : ""), stoneType.isSecondStoneSlabID() ? 182 : 44, STONE_SLAB_STONE.getMinecraftId() + (stoneType.isSecondStoneSlabID() ? "2" : ""), enumName, slabType, stoneType, hardness, blastResistance);
    }

    protected StoneSlabMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, slabType, stoneType, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return this.getType(SlabTypeMat.BOTTOM, this.stoneType);
    }

    @Override
    public StoneSlabMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StoneSlabMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of StoneSlab sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StoneSlab or null
     */
    public static StoneSlabMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StoneSlab sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StoneSlab or null
     */
    public static StoneSlabMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Return one of StoneSlab sub-type, based on {@link SlabTypeMat} and {@link StoneSlabTypeMat}.
     * It will never return null.
     *
     * @param slabType  type of slab.
     * @param stoneType type of stone slab.
     *
     * @return sub-type of StoneSlab
     */
    public static StoneSlabMat getStoneSlab(final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
    {
        return getByID(combine(slabType, stoneType));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StoneSlabMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public StoneSlabMat[] types()
    {
        return StoneSlabMat.stoneSlabTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StoneSlabMat[] stoneSlabTypes()
    {
        return byID.values(new StoneSlabMat[byID.size()]);
    }

    /**
     * Helper class for second stone slab ID
     */
    public static class StoneSlab2 extends StoneSlabMat
    {
        public StoneSlab2(final String enumName, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
        {
            super(enumName, slabType, stoneType);
        }

        /**
         * Returns one of StoneSlab sub-type based on sub-id, may return null
         *
         * @param id sub-type id
         *
         * @return sub-type of StoneSlab or null
         */
        @SuppressWarnings("MagicNumber")
        public static StoneSlabMat getByID(final int id)
        {
            return byID.get((byte) (id + 16));
        }
    }

    static
    {
        StoneSlabMat.register(STONE_SLAB_STONE);
        StoneSlabMat.register(STONE_SLAB_SANDSTONE);
        StoneSlabMat.register(STONE_SLAB_WOODEN);
        StoneSlabMat.register(STONE_SLAB_COBBLESTONE);
        StoneSlabMat.register(STONE_SLAB_BRICKS);
        StoneSlabMat.register(STONE_SLAB_STONE_BRICKS);
        StoneSlabMat.register(STONE_SLAB_NETHER_BRICKS);
        StoneSlabMat.register(STONE_SLAB_QUARTZ);
        StoneSlabMat.register(STONE_SLAB_RED_SANDSTONE);
        StoneSlabMat.register(STONE_SLAB_STONE_UPPER);
        StoneSlabMat.register(STONE_SLAB_SANDSTONE_UPPER);
        StoneSlabMat.register(STONE_SLAB_WOODEN_UPPER);
        StoneSlabMat.register(STONE_SLAB_COBBLESTONE_UPPER);
        StoneSlabMat.register(STONE_SLAB_BRICKS_UPPER);
        StoneSlabMat.register(STONE_SLAB_STONE_BRICKS_UPPER);
        StoneSlabMat.register(STONE_SLAB_NETHER_BRICKS_UPPER);
        StoneSlabMat.register(STONE_SLAB_QUARTZ_UPPER);
        StoneSlabMat.register(STONE_SLAB_RED_SANDSTONE_UPPER);
    }
}
