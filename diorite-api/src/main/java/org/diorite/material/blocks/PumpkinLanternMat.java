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
 * Class representing 'Pumpkin Lantern' block material in minecraft. <br>
 * ID of block: 91 <br>
 * String ID of block: minecraft:lit_pumpkin <br>
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
public class PumpkinLanternMat extends AbstractPumpkinMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 5;

    public static final PumpkinLanternMat PUMPKIN_LANTERN_SOUTH = new PumpkinLanternMat();
    public static final PumpkinLanternMat PUMPKIN_LANTERN_WEST  = new PumpkinLanternMat(BlockFace.WEST);
    public static final PumpkinLanternMat PUMPKIN_LANTERN_NORTH = new PumpkinLanternMat(BlockFace.NORTH);
    public static final PumpkinLanternMat PUMPKIN_LANTERN_EAST  = new PumpkinLanternMat(BlockFace.EAST);
    public static final PumpkinLanternMat PUMPKIN_LANTERN_SELF  = new PumpkinLanternMat(BlockFace.SELF);

    private static final Map<String, PumpkinLanternMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PumpkinLanternMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected PumpkinLanternMat()
    {
        super("PUMPKIN_LANTERN", 91, "minecraft:lit_pumpkin", BlockFace.SOUTH, 1, 5);
    }

    protected PumpkinLanternMat(final BlockFace face)
    {
        super(PUMPKIN_LANTERN_SOUTH.name(), PUMPKIN_LANTERN_SOUTH.ordinal(), PUMPKIN_LANTERN_SOUTH.getMinecraftId(), face, PUMPKIN_LANTERN_SOUTH.getHardness(), PUMPKIN_LANTERN_SOUTH.getBlastResistance());
    }

    protected PumpkinLanternMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return PUMPKIN_LANTERN;
    }

    @Override
    public PumpkinLanternMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PumpkinLanternMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PumpkinLanternMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Returns one of PumpkinLantern sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PumpkinLantern or null
     */
    public static PumpkinLanternMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PumpkinLantern sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PumpkinLantern or null
     */
    public static PumpkinLanternMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of PumpkinLantern sub-type based on {@link BlockFace}.
     * It will never return null;
     *
     * @param face facing of PumpkinLantern
     *
     * @return sub-type of PumpkinLantern
     */
    public static PumpkinLanternMat getPumpkinLantern(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PumpkinLanternMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PumpkinLanternMat[] types()
    {
        return PumpkinLanternMat.pumpkinLanternTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PumpkinLanternMat[] pumpkinLanternTypes()
    {
        return byID.values(new PumpkinLanternMat[byID.size()]);
    }

    static
    {
        PumpkinLanternMat.register(PUMPKIN_LANTERN_SOUTH);
        PumpkinLanternMat.register(PUMPKIN_LANTERN_WEST);
        PumpkinLanternMat.register(PUMPKIN_LANTERN_NORTH);
        PumpkinLanternMat.register(PUMPKIN_LANTERN_EAST);
        PumpkinLanternMat.register(PUMPKIN_LANTERN_SELF);
    }
}
