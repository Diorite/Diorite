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

import org.diorite.material.BlockMaterialData;
import org.diorite.material.ChangeablePowerElementMat;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.math.ByteRange;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Redstone Wire' block material in minecraft. <br>
 * ID of block: 55 <br>
 * String ID of block: minecraft:redstone_wire <br>
 * This block can't be used in inventory, valid material for this block: 'Redstone' (minecraft:redstone(331):0) <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * ON_15:
 * Type name: 'On 15' <br>
 * SubID: 15 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_14:
 * Type name: 'On 14' <br>
 * SubID: 14 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_13:
 * Type name: 'On 13' <br>
 * SubID: 13 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_12:
 * Type name: 'On 12' <br>
 * SubID: 12 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_11:
 * Type name: 'On 11' <br>
 * SubID: 11 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_10:
 * Type name: 'On 10' <br>
 * SubID: 10 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_9:
 * Type name: 'On 9' <br>
 * SubID: 9 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_8:
 * Type name: 'On 8' <br>
 * SubID: 8 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_7:
 * Type name: 'On 7' <br>
 * SubID: 7 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_6:
 * Type name: 'On 6' <br>
 * SubID: 6 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_5:
 * Type name: 'On 5' <br>
 * SubID: 5 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_4:
 * Type name: 'On 4' <br>
 * SubID: 4 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_3:
 * Type name: 'On 3' <br>
 * SubID: 3 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_2:
 * Type name: 'On 2' <br>
 * SubID: 2 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ON_1:
 * Type name: 'On 1' <br>
 * SubID: 1 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * OFF:
 * Type name: 'Off' <br>
 * SubID: 0 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings({"JavaDoc", "MagicNumber"})
public class RedstoneWireMat extends BlockMaterialData implements ChangeablePowerElementMat
{
    /**
     * Range of valid power strength, from 0 to 15
     */
    public static final ByteRange POWER_RANGE      = new ByteRange(0, 15);
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int       USED_DATA_VALUES = 16;

    public static final RedstoneWireMat REDSTONE_WIRE_OFF   = new RedstoneWireMat();
    public static final RedstoneWireMat REDSTONE_WIRE_ON_1  = new RedstoneWireMat(0x1);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_2  = new RedstoneWireMat(0x2);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_3  = new RedstoneWireMat(0x3);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_4  = new RedstoneWireMat(0x4);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_5  = new RedstoneWireMat(0x5);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_6  = new RedstoneWireMat(0x6);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_7  = new RedstoneWireMat(0x7);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_8  = new RedstoneWireMat(0x8);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_9  = new RedstoneWireMat(0x9);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_10 = new RedstoneWireMat(0xA);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_11 = new RedstoneWireMat(0xB);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_12 = new RedstoneWireMat(0xC);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_13 = new RedstoneWireMat(0xD);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_14 = new RedstoneWireMat(0xE);
    public static final RedstoneWireMat REDSTONE_WIRE_ON_15 = new RedstoneWireMat(0xF);

    private static final Map<String, RedstoneWireMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneWireMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected RedstoneWireMat()
    {
        super("REDSTONE_WIRE", 55, "minecraft:redstone_wire", "OFF", (byte) 0x00, 0, 0);
    }

    protected RedstoneWireMat(final int type)
    {
        super(REDSTONE_WIRE_OFF.name(), REDSTONE_WIRE_OFF.ordinal(), REDSTONE_WIRE_OFF.getMinecraftId(), ((type == 0) ? "OFF" : ("ON_" + type)), (byte) type, REDSTONE_WIRE_OFF.getHardness(), REDSTONE_WIRE_OFF.getBlastResistance());
    }

    protected RedstoneWireMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.REDSTONE;
    }

    @Override
    public RedstoneWireMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneWireMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public int getPowerStrength()
    {
        return this.type;
    }

    @Override
    public RedstoneWireMat getPowerStrength(final int strength)
    {
        return getByID(POWER_RANGE.getIn(strength));
    }

    @Override
    public RedstoneWireMat getPowered(final boolean powered)
    {
        return getByID(powered ? 15 : 0);
    }

    @Override
    public boolean isPowered()
    {
        return this.type != 0;
    }

    /**
     * Returns one of RedstoneWire sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneWire or null
     */
    public static RedstoneWireMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneWire sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneWire or null
     */
    public static RedstoneWireMat getByEnumName(final String name)
    {
        return byName.get(name);
    }


    /**
     * Returns sub-type of RedstoneWire, based on power strenght.
     *
     * @param power power in block.
     *
     * @return sub-type of RedstoneWire
     */
    public static RedstoneWireMat getRedstoneWire(final int power)
    {
        return getByID(power);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneWireMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneWireMat[] types()
    {
        return RedstoneWireMat.redstoneWireTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneWireMat[] redstoneWireTypes()
    {
        return byID.values(new RedstoneWireMat[byID.size()]);
    }

    static
    {
        RedstoneWireMat.register(REDSTONE_WIRE_OFF);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_1);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_2);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_3);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_4);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_5);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_6);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_7);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_8);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_9);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_10);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_11);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_12);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_13);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_14);
        RedstoneWireMat.register(REDSTONE_WIRE_ON_15);
    }
}
