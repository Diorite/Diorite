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
 * Class representing 'Double Stone Slab' block material in minecraft. <br>
 * ID of block: 43 <br>
 * String ID of block: minecraft:double_stone_slab <br>
 * This block can't be used in inventory, valid material for this block: 'Stone Slab'/'Stone' (minecraft:stone_slab(44):0) <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * RED_SANDSTONE_SMOOTH:
 * Type name: 'Red Sandstone Smooth' <br>
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
 * QUARTZ_SMOOTH:
 * Type name: 'Quartz Smooth' <br>
 * SubID: 15 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * NETHER_BRICKS_SMOOTH:
 * Type name: 'Nether Bricks Smooth' <br>
 * SubID: 14 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * STONE_BRICKS_SMOOTH:
 * Type name: 'Stone Bricks Smooth' <br>
 * SubID: 13 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * BRICKS_SMOOTH:
 * Type name: 'Bricks Smooth' <br>
 * SubID: 12 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * COBBLESTONE_SMOOTH:
 * Type name: 'Cobblestone Smooth' <br>
 * SubID: 11 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * WOODEN_SMOOTH:
 * Type name: 'Wooden Smooth' <br>
 * SubID: 10 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * SANDSTONE_SMOOTH:
 * Type name: 'Sandstone Smooth' <br>
 * SubID: 9 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * STONE_SMOOTH:
 * Type name: 'Stone Smooth' <br>
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
public class DoubleStoneSlabMat extends StonySlabMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 18;

    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_STONE         = new DoubleStoneSlabMat();
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_SANDSTONE     = new DoubleStoneSlabMat("SANDSTONE", SlabTypeMat.FULL, StoneSlabTypeMat.SANDSTONE);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_WOODEN        = new DoubleStoneSlabMat("WOODEN", SlabTypeMat.FULL, StoneSlabTypeMat.WOODEN);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_COBBLESTONE   = new DoubleStoneSlabMat("COBBLESTONE", SlabTypeMat.FULL, StoneSlabTypeMat.COBBLESTONE);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_BRICKS        = new DoubleStoneSlabMat("BRICKS", SlabTypeMat.FULL, StoneSlabTypeMat.BRICKS);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_STONE_BRICKS  = new DoubleStoneSlabMat("STONE_BRICKS", SlabTypeMat.FULL, StoneSlabTypeMat.STONE_BRICKS);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_NETHER_BRICKS = new DoubleStoneSlabMat("NETHER_BRICKS", SlabTypeMat.FULL, StoneSlabTypeMat.NETHER_BRICKS);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_QUARTZ        = new DoubleStoneSlabMat("QUARTZ", SlabTypeMat.FULL, StoneSlabTypeMat.QUARTZ);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_RED_SANDSTONE = new DoubleStoneSlab2("RED_SANDSTONE", SlabTypeMat.FULL, StoneSlabTypeMat.RED_SANDSTONE);

    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_STONE_SMOOTH         = new DoubleStoneSlabMat("STONE_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.STONE);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_SANDSTONE_SMOOTH     = new DoubleStoneSlabMat("SANDSTONE_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.SANDSTONE);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_WOODEN_SMOOTH        = new DoubleStoneSlabMat("WOODEN_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.WOODEN);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_COBBLESTONE_SMOOTH   = new DoubleStoneSlabMat("COBBLESTONE_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.COBBLESTONE);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_BRICKS_SMOOTH        = new DoubleStoneSlabMat("BRICKS_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.BRICKS);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_STONE_BRICKS_SMOOTH  = new DoubleStoneSlabMat("STONE_BRICKS_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.STONE_BRICKS);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_NETHER_BRICKS_SMOOTH = new DoubleStoneSlabMat("NETHER_BRICKS_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.NETHER_BRICKS);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_QUARTZ_SMOOTH        = new DoubleStoneSlabMat("QUARTZ_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.QUARTZ);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_RED_SANDSTONE_SMOOTH = new DoubleStoneSlab2("RED_SANDSTONE_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.RED_SANDSTONE);

    private static final Map<String, DoubleStoneSlabMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DoubleStoneSlabMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DoubleStoneSlabMat()
    {
        super("DOUBLE_STONE_SLAB", 43, "minecraft:double_stone_slab", "STONE", SlabTypeMat.FULL, StoneSlabTypeMat.STONE, 2, 30);
    }

    @SuppressWarnings("MagicNumber")
    protected DoubleStoneSlabMat(final String enumName, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType, final float hardness, final float blastResistance)
    {
        super(DOUBLE_STONE_SLAB_STONE.name() + (stoneType.isSecondStoneSlabID() ? "2" : ""), (stoneType.isSecondStoneSlabID() ? 181 : 43), DOUBLE_STONE_SLAB_STONE.getMinecraftId() + (stoneType.isSecondStoneSlabID() ? "2" : ""), enumName, slabType, stoneType, hardness, blastResistance);
    }

    @SuppressWarnings("MagicNumber")
    protected DoubleStoneSlabMat(final String enumName, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
    {
        super(DOUBLE_STONE_SLAB_STONE.name() + (stoneType.isSecondStoneSlabID() ? "2" : ""), (stoneType.isSecondStoneSlabID() ? 181 : 43), DOUBLE_STONE_SLAB_STONE.getMinecraftId() + (stoneType.isSecondStoneSlabID() ? "2" : ""), enumName, slabType, stoneType, DOUBLE_STONE_SLAB_STONE.getHardness(), DOUBLE_STONE_SLAB_STONE.getBlastResistance());
    }

    protected DoubleStoneSlabMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, slabType, stoneType, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return STONE_SLAB.getType(SlabTypeMat.BOTTOM, this.stoneType);
    }

    @Override
    public DoubleStoneSlabMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DoubleStoneSlabMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DoubleStoneSlab sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DoubleStoneSlab or null
     */
    public static DoubleStoneSlabMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DoubleStoneSlab sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DoubleStoneSlab or null
     */
    public static DoubleStoneSlabMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Return one of DoubleStoneSlab sub-type, based on {@link SlabTypeMat} and {@link StoneSlabTypeMat}.
     * It will never return null.
     *
     * @param slabType  type of slab.
     * @param stoneType type of stone slab.
     *
     * @return sub-type of DoubleStoneSlab
     */
    public static DoubleStoneSlabMat getDoubleStoneSlab(final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
    {
        return getByID(combine(slabType, stoneType));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DoubleStoneSlabMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DoubleStoneSlabMat[] types()
    {
        return DoubleStoneSlabMat.doubleStoneSlabTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DoubleStoneSlabMat[] doubleStoneSlabTypes()
    {
        return byID.values(new DoubleStoneSlabMat[byID.size()]);
    }

    /**
     * Helper class for second stone slab ID
     */
    public static class DoubleStoneSlab2 extends DoubleStoneSlabMat
    {

        public DoubleStoneSlab2(final String enumName, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
        {
            super(enumName, slabType, stoneType);
        }

        /**
         * Returns one of DoubleStoneSlab sub-type based on sub-id, may return null
         *
         * @param id sub-type id
         *
         * @return sub-type of DoubleStoneSlab or null
         */
        @SuppressWarnings("MagicNumber")
        public static DoubleStoneSlabMat getByID(final int id)
        {
            return byID.get((byte) (id + 16));
        }
    }

    static
    {
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_STONE);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_SANDSTONE);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_WOODEN);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_COBBLESTONE);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_BRICKS);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_STONE_BRICKS);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_NETHER_BRICKS);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_QUARTZ);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_RED_SANDSTONE);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_STONE_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_SANDSTONE_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_WOODEN_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_COBBLESTONE_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_BRICKS_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_STONE_BRICKS_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_NETHER_BRICKS_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_QUARTZ_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_RED_SANDSTONE_SMOOTH);
    }
}
