/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import org.diorite.block.BlockFace;
import org.diorite.material.Material;
import org.diorite.material.StairsMat;
import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;

/**
 * Class representing 'Birch Stairs' block material in minecraft. <br>
 * ID of block: 135 <br>
 * String ID of block: minecraft:birch_stairs <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * NORTH_UPSIDE_DOWN:
 * Type name: 'North Upside Down' <br>
 * SubID: 7 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SOUTH_UPSIDE_DOWN:
 * Type name: 'South Upside Down' <br>
 * SubID: 6 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * WEST_UPSIDE_DOWN:
 * Type name: 'West Upside Down' <br>
 * SubID: 5 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * EAST_UPSIDE_DOWN:
 * Type name: 'East Upside Down' <br>
 * SubID: 4 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 3 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 2 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 1 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 0 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class BirchStairsMat extends WoodenStairsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 8;

    public static final BirchStairsMat BIRCH_STAIRS_EAST  = new BirchStairsMat();
    public static final BirchStairsMat BIRCH_STAIRS_WEST  = new BirchStairsMat(BlockFace.WEST, false);
    public static final BirchStairsMat BIRCH_STAIRS_SOUTH = new BirchStairsMat(BlockFace.SOUTH, false);
    public static final BirchStairsMat BIRCH_STAIRS_NORTH = new BirchStairsMat(BlockFace.NORTH, false);

    public static final BirchStairsMat BIRCH_STAIRS_EAST_UPSIDE_DOWN  = new BirchStairsMat(BlockFace.EAST, true);
    public static final BirchStairsMat BIRCH_STAIRS_WEST_UPSIDE_DOWN  = new BirchStairsMat(BlockFace.WEST, true);
    public static final BirchStairsMat BIRCH_STAIRS_SOUTH_UPSIDE_DOWN = new BirchStairsMat(BlockFace.SOUTH, true);
    public static final BirchStairsMat BIRCH_STAIRS_NORTH_UPSIDE_DOWN = new BirchStairsMat(BlockFace.NORTH, true);

    private static final Map<String, BirchStairsMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final Byte2ObjectMap<BirchStairsMat> byID   = new Byte2ObjectOpenHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    public BirchStairsMat()
    {
        super("BIRCH_STAIRS", 135, "minecraft:birch_stairs", WoodType.BIRCH, BlockFace.EAST, false, 2, 15);
    }

    public BirchStairsMat(final BlockFace face, final boolean upsideDown)
    {
        super(BIRCH_STAIRS_EAST.name(), BIRCH_STAIRS_EAST.ordinal(), BIRCH_STAIRS_EAST.getMinecraftId(), WoodType.BIRCH, face, upsideDown, BIRCH_STAIRS_EAST.getHardness(), BIRCH_STAIRS_EAST.getBlastResistance());
    }

    public BirchStairsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType, final BlockFace face, final boolean upsideDown, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, face, upsideDown, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return BIRCH_STAIRS;
    }

    @Override
    public BirchStairsMat getBlockFacing(final BlockFace face)
    {
        return getByID(StairsMat.combine(face, this.upsideDown));
    }

    @Override
    public BirchStairsMat getUpsideDown(final boolean upsideDown)
    {
        return getByID(StairsMat.combine(this.face, upsideDown));
    }

    @Override
    public BirchStairsMat getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(face, upsideDown));
    }

    @Override
    public BirchStairsMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BirchStairsMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of BirchStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BirchStairs or null
     */
    public static BirchStairsMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BirchStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BirchStairs or null
     */
    public static BirchStairsMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of BirchStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of BirchStairs
     */
    public static BirchStairsMat getBirchStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BirchStairsMat element)
    {
        allBlocks.incrementAndGet();
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BirchStairsMat[] types()
    {
        return BirchStairsMat.birchStairsTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BirchStairsMat[] birchStairsTypes()
    {
        return byID.values().toArray(new BirchStairsMat[byID.size()]);
    }

    static
    {
        BirchStairsMat.register(BIRCH_STAIRS_EAST);
        BirchStairsMat.register(BIRCH_STAIRS_WEST);
        BirchStairsMat.register(BIRCH_STAIRS_SOUTH);
        BirchStairsMat.register(BIRCH_STAIRS_NORTH);
        BirchStairsMat.register(BIRCH_STAIRS_EAST_UPSIDE_DOWN);
        BirchStairsMat.register(BIRCH_STAIRS_WEST_UPSIDE_DOWN);
        BirchStairsMat.register(BIRCH_STAIRS_SOUTH_UPSIDE_DOWN);
        BirchStairsMat.register(BIRCH_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
