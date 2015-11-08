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

import org.diorite.BlockFace;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Redstone Repeater Off' block material in minecraft. <br>
 * ID of block: 93 <br>
 * String ID of block: minecraft:unpowered_repeater <br>
 * This block can't be used in inventory, valid material for this block: 'Redstone Repeater Item' (minecraft:repeater(356):0) <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * WEST_4:
 * Type name: 'West 4' <br>
 * SubID: 15 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * SOUTH_4:
 * Type name: 'South 4' <br>
 * SubID: 14 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * EAST_4:
 * Type name: 'East 4' <br>
 * SubID: 13 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * NORTH_4:
 * Type name: 'North 4' <br>
 * SubID: 12 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * WEST_3:
 * Type name: 'West 3' <br>
 * SubID: 11 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * SOUTH_3:
 * Type name: 'South 3' <br>
 * SubID: 10 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * EAST_3:
 * Type name: 'East 3' <br>
 * SubID: 9 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * NORTH_3:
 * Type name: 'North 3' <br>
 * SubID: 8 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * WEST_2:
 * Type name: 'West 2' <br>
 * SubID: 7 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * SOUTH_2:
 * Type name: 'South 2' <br>
 * SubID: 6 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * EAST_2:
 * Type name: 'East 2' <br>
 * SubID: 5 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * NORTH_2:
 * Type name: 'North 2' <br>
 * SubID: 4 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * WEST_1:
 * Type name: 'West 1' <br>
 * SubID: 3 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * SOUTH_1:
 * Type name: 'South 1' <br>
 * SubID: 2 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * EAST_1:
 * Type name: 'East 1' <br>
 * SubID: 1 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * NORTH_1:
 * Type name: 'North 1' <br>
 * SubID: 0 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class RedstoneRepeaterOffMat extends RedstoneRepeaterMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_NORTH_1 = new RedstoneRepeaterOffMat();
    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_EAST_1  = new RedstoneRepeaterOffMat(BlockFace.EAST, 1);
    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_SOUTH_1 = new RedstoneRepeaterOffMat(BlockFace.SOUTH, 1);
    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_WEST_1  = new RedstoneRepeaterOffMat(BlockFace.WEST, 1);

    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_NORTH_2 = new RedstoneRepeaterOffMat(BlockFace.NORTH, 2);
    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_EAST_2  = new RedstoneRepeaterOffMat(BlockFace.EAST, 2);
    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_SOUTH_2 = new RedstoneRepeaterOffMat(BlockFace.SOUTH, 2);
    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_WEST_2  = new RedstoneRepeaterOffMat(BlockFace.WEST, 2);

    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_NORTH_3 = new RedstoneRepeaterOffMat(BlockFace.NORTH, 3);
    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_EAST_3  = new RedstoneRepeaterOffMat(BlockFace.EAST, 3);
    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_SOUTH_3 = new RedstoneRepeaterOffMat(BlockFace.SOUTH, 3);
    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_WEST_3  = new RedstoneRepeaterOffMat(BlockFace.WEST, 3);

    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_NORTH_4 = new RedstoneRepeaterOffMat(BlockFace.NORTH, 4);
    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_EAST_4  = new RedstoneRepeaterOffMat(BlockFace.EAST, 4);
    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_SOUTH_4 = new RedstoneRepeaterOffMat(BlockFace.SOUTH, 4);
    public static final RedstoneRepeaterOffMat REDSTONE_REPEATER_OFF_WEST_4  = new RedstoneRepeaterOffMat(BlockFace.WEST, 4);

    private static final Map<String, RedstoneRepeaterOffMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneRepeaterOffMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected RedstoneRepeaterOffMat()
    {
        super("REDSTONE_REPEATER_OFF", 93, "minecraft:unpowered_repeater", BlockFace.NORTH, 1, 0, 0);
    }

    protected RedstoneRepeaterOffMat(final BlockFace face, final int delay)
    {
        super(REDSTONE_REPEATER_OFF_NORTH_1.name(), REDSTONE_REPEATER_OFF_NORTH_1.ordinal(), REDSTONE_REPEATER_OFF_NORTH_1.getMinecraftId(), face, delay, REDSTONE_REPEATER_OFF_NORTH_1.getHardness(), REDSTONE_REPEATER_OFF_NORTH_1.getBlastResistance());
    }

    protected RedstoneRepeaterOffMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final int delay, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face, delay, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return REDSTONE_REPEATER_ITEM;
    }

    @Override
    public RedstoneRepeaterOffMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneRepeaterOffMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isPowered()
    {
        return false;
    }

    @Override
    public RedstoneRepeaterOffMat getDelay(final int delay)
    {
        return getByID(combine(this.face, delay));
    }

    @Override
    public RedstoneRepeaterOffMat getType(final BlockFace face, final int delay)
    {
        return getByID(combine(face, delay));
    }

    @Override
    public RedstoneRepeaterOffMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.delay));
    }

    /**
     * Returns one of RedstoneRepeaterOff sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneRepeaterOff or null
     */
    public static RedstoneRepeaterOffMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneRepeaterOff sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneRepeaterOff or null
     */
    public static RedstoneRepeaterOffMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneRepeaterOffMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneRepeaterOffMat[] types()
    {
        return RedstoneRepeaterOffMat.redstoneRepeaterOffTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneRepeaterOffMat[] redstoneRepeaterOffTypes()
    {
        return byID.values(new RedstoneRepeaterOffMat[byID.size()]);
    }

    static
    {
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_NORTH_1);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_EAST_1);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_SOUTH_1);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_WEST_1);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_NORTH_2);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_EAST_2);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_SOUTH_2);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_WEST_2);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_NORTH_3);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_EAST_3);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_SOUTH_3);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_WEST_3);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_NORTH_4);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_EAST_4);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_SOUTH_4);
        RedstoneRepeaterOffMat.register(REDSTONE_REPEATER_OFF_WEST_4);
    }
}
