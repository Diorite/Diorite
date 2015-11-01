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
 * Class representing 'Wooden Button' block material in minecraft. <br>
 * ID of block: 143 <br>
 * String ID of block: minecraft:wooden_button <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * UP_POWERED:
 * Type name: 'Up Powered' <br>
 * SubID: 13 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * NORTH_POWERED:
 * Type name: 'North Powered' <br>
 * SubID: 12 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * SOUTH_POWERED:
 * Type name: 'South Powered' <br>
 * SubID: 11 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * WEST_POWERED:
 * Type name: 'West Powered' <br>
 * SubID: 10 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * EAST_POWERED:
 * Type name: 'East Powered' <br>
 * SubID: 9 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * DOWN_POWERED:
 * Type name: 'Down Powered' <br>
 * SubID: 8 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * UP:
 * Type name: 'Up' <br>
 * SubID: 5 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 4 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 3 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 2 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 1 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * DOWN:
 * Type name: 'Down' <br>
 * SubID: 0 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class WoodenButtonMat extends ButtonMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 12;

    public static final WoodenButtonMat WOODEN_BUTTON_DOWN          = new WoodenButtonMat();
    public static final WoodenButtonMat WOODEN_BUTTON_EAST          = new WoodenButtonMat(BlockFace.EAST, false);
    public static final WoodenButtonMat WOODEN_BUTTON_WEST          = new WoodenButtonMat(BlockFace.WEST, false);
    public static final WoodenButtonMat WOODEN_BUTTON_SOUTH         = new WoodenButtonMat(BlockFace.SOUTH, false);
    public static final WoodenButtonMat WOODEN_BUTTON_NORTH         = new WoodenButtonMat(BlockFace.NORTH, false);
    public static final WoodenButtonMat WOODEN_BUTTON_UP            = new WoodenButtonMat(BlockFace.UP, false);
    public static final WoodenButtonMat WOODEN_BUTTON_DOWN_POWERED  = new WoodenButtonMat(BlockFace.DOWN, true);
    public static final WoodenButtonMat WOODEN_BUTTON_EAST_POWERED  = new WoodenButtonMat(BlockFace.EAST, true);
    public static final WoodenButtonMat WOODEN_BUTTON_WEST_POWERED  = new WoodenButtonMat(BlockFace.WEST, true);
    public static final WoodenButtonMat WOODEN_BUTTON_SOUTH_POWERED = new WoodenButtonMat(BlockFace.SOUTH, true);
    public static final WoodenButtonMat WOODEN_BUTTON_NORTH_POWERED = new WoodenButtonMat(BlockFace.NORTH, true);
    public static final WoodenButtonMat WOODEN_BUTTON_UP_POWERED    = new WoodenButtonMat(BlockFace.UP, true);

    private static final Map<String, WoodenButtonMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WoodenButtonMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected WoodenButtonMat()
    {
        super("WOODEN_BUTTON", 143, "minecraft:wooden_button", BlockFace.DOWN, false, 0.5f, 2.5f);
    }

    protected WoodenButtonMat(final BlockFace face, final boolean powered)
    {
        super(WOODEN_BUTTON_DOWN.name(), WOODEN_BUTTON_DOWN.ordinal(), WOODEN_BUTTON_DOWN.getMinecraftId(), face, powered, WOODEN_BUTTON_DOWN.getHardness(), WOODEN_BUTTON_DOWN.getBlastResistance());
    }

    protected WoodenButtonMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face, powered, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return WOODEN_BUTTON;
    }

    @Override
    public WoodenButtonMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WoodenButtonMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public WoodenButtonMat getType(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    @Override
    public WoodenButtonMat getPowered(final boolean powered)
    {
        return getByID(combine(this.face, powered));
    }

    @Override
    public WoodenButtonMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.powered));
    }

    @Override
    public WoodenButtonMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace(), this.powered));
    }

    /**
     * Returns one of WoodenButton sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WoodenButton or null
     */
    public static WoodenButtonMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WoodenButton sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WoodenButton or null
     */
    public static WoodenButtonMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of WoodenButton based on {@link BlockFace} and powered state.
     * It will never return null.
     *
     * @param face    facing direction of button
     * @param powered if button should be powered.
     *
     * @return sub-type of WoodenButton
     */
    public static WoodenButtonMat getWoodenButton(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WoodenButtonMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public WoodenButtonMat[] types()
    {
        return WoodenButtonMat.woodenButtonTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WoodenButtonMat[] woodenButtonTypes()
    {
        return byID.values(new WoodenButtonMat[byID.size()]);
    }

    static
    {
        WoodenButtonMat.register(WOODEN_BUTTON_DOWN);
        WoodenButtonMat.register(WOODEN_BUTTON_EAST);
        WoodenButtonMat.register(WOODEN_BUTTON_WEST);
        WoodenButtonMat.register(WOODEN_BUTTON_SOUTH);
        WoodenButtonMat.register(WOODEN_BUTTON_NORTH);
        WoodenButtonMat.register(WOODEN_BUTTON_UP);
        WoodenButtonMat.register(WOODEN_BUTTON_DOWN_POWERED);
        WoodenButtonMat.register(WOODEN_BUTTON_EAST_POWERED);
        WoodenButtonMat.register(WOODEN_BUTTON_WEST_POWERED);
        WoodenButtonMat.register(WOODEN_BUTTON_SOUTH_POWERED);
        WoodenButtonMat.register(WOODEN_BUTTON_NORTH_POWERED);
        WoodenButtonMat.register(WOODEN_BUTTON_UP_POWERED);
    }
}
