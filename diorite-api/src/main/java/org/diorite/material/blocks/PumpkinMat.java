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
 * Class representing 'Pumpkin' block material in minecraft. <br>
 * ID of block: 86 <br>
 * String ID of block: minecraft:pumpkin <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * SELF:
 * Type name: 'Self' <br>
 * SubID: 4 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 3 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 2 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 1 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 0 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class PumpkinMat extends AbstractPumpkinMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 5;

    public static final PumpkinMat PUMPKIN_SOUTH = new PumpkinMat();
    public static final PumpkinMat PUMPKIN_WEST  = new PumpkinMat(BlockFace.WEST);
    public static final PumpkinMat PUMPKIN_NORTH = new PumpkinMat(BlockFace.NORTH);
    public static final PumpkinMat PUMPKIN_EAST  = new PumpkinMat(BlockFace.EAST);
    public static final PumpkinMat PUMPKIN_SELF  = new PumpkinMat(BlockFace.SELF);

    private static final Map<String, PumpkinMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PumpkinMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected PumpkinMat()
    {
        super("PUMPKIN", 86, "minecraft:pumpkin", BlockFace.SOUTH, 1, 5);
    }

    protected PumpkinMat(final BlockFace face)
    {
        super(PUMPKIN_SOUTH.name(), PUMPKIN_SOUTH.ordinal(), PUMPKIN_SOUTH.getMinecraftId(), face, PUMPKIN_SOUTH.getHardness(), PUMPKIN_SOUTH.getBlastResistance());
    }

    protected PumpkinMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return PUMPKIN;
    }

    @Override
    public PumpkinMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PumpkinMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PumpkinMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Returns one of Pumpkin sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Pumpkin or null
     */
    public static PumpkinMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Pumpkin sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Pumpkin or null
     */
    public static PumpkinMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Pumpkin sub-type based on {@link BlockFace}.
     * It will never return null;
     *
     * @param face facing of Pumpkin
     *
     * @return sub-type of Pumpkin
     */
    public static PumpkinMat getPumpkin(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PumpkinMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PumpkinMat[] types()
    {
        return PumpkinMat.pumpkinTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PumpkinMat[] pumpkinTypes()
    {
        return byID.values(new PumpkinMat[byID.size()]);
    }

    static
    {
        PumpkinMat.register(PUMPKIN_SOUTH);
        PumpkinMat.register(PUMPKIN_WEST);
        PumpkinMat.register(PUMPKIN_NORTH);
        PumpkinMat.register(PUMPKIN_EAST);
        PumpkinMat.register(PUMPKIN_SELF);
    }
}
