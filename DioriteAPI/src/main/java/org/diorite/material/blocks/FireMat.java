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

import org.diorite.material.AgeableBlockMat;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Fire' block material in minecraft. <br>
 * ID of block: 51 <br>
 * String ID of block: minecraft:fire <br>
 * This block can't be used in inventory, valid material for this block: 'Fire Charge' (minecraft:fire_charge(385):0) <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * 15:
 * Type name: '15' <br>
 * SubID: 15 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 14:
 * Type name: '14' <br>
 * SubID: 14 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 13:
 * Type name: '13' <br>
 * SubID: 13 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 12:
 * Type name: '12' <br>
 * SubID: 12 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 11:
 * Type name: '11' <br>
 * SubID: 11 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 10:
 * Type name: '10' <br>
 * SubID: 10 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 9:
 * Type name: '9' <br>
 * SubID: 9 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 8:
 * Type name: '8' <br>
 * SubID: 8 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 7:
 * Type name: '7' <br>
 * SubID: 7 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 6:
 * Type name: '6' <br>
 * SubID: 6 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 5:
 * Type name: '5' <br>
 * SubID: 5 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 4:
 * Type name: '4' <br>
 * SubID: 4 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 3:
 * Type name: '3' <br>
 * SubID: 3 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 2:
 * Type name: '2' <br>
 * SubID: 2 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 1:
 * Type name: '1' <br>
 * SubID: 1 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 0:
 * Type name: '0' <br>
 * SubID: 0 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class FireMat extends BlockMaterialData implements AgeableBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final FireMat FIRE_0  = new FireMat();
    public static final FireMat FIRE_1  = new FireMat(0x1);
    public static final FireMat FIRE_2  = new FireMat(0x2);
    public static final FireMat FIRE_3  = new FireMat(0x3);
    public static final FireMat FIRE_4  = new FireMat(0x4);
    public static final FireMat FIRE_5  = new FireMat(0x5);
    public static final FireMat FIRE_6  = new FireMat(0x6);
    public static final FireMat FIRE_7  = new FireMat(0x7);
    public static final FireMat FIRE_8  = new FireMat(0x8);
    public static final FireMat FIRE_9  = new FireMat(0x9);
    public static final FireMat FIRE_10 = new FireMat(0xA);
    @SuppressWarnings("MagicNumber")
    public static final FireMat FIRE_11 = new FireMat(0xB);
    @SuppressWarnings("MagicNumber")
    public static final FireMat FIRE_12 = new FireMat(0xC);
    @SuppressWarnings("MagicNumber")
    public static final FireMat FIRE_13 = new FireMat(0xD);
    @SuppressWarnings("MagicNumber")
    public static final FireMat FIRE_14 = new FireMat(0xE);
    @SuppressWarnings("MagicNumber")
    public static final FireMat FIRE_15 = new FireMat(0xF);

    private static final Map<String, FireMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<FireMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected FireMat()
    {
        super("FIRE", 51, "minecraft:fire", "0", (byte) 0x0, 0, 0);
    }

    protected FireMat(final int age)
    {
        super(FIRE_0.name(), FIRE_0.ordinal(), FIRE_0.getMinecraftId(), Integer.toString(age), (byte) age, FIRE_0.getHardness(), FIRE_0.getBlastResistance());
    }

    protected FireMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.FIRE_CHARGE;
    }

    @Override
    public FireMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public FireMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public int getAge()
    {
        return this.type;
    }

    @Override
    public FireMat getAge(final int age)
    {
        return getByID(age);
    }

    /**
     * Returns one of Fire sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Fire or null
     */
    public static FireMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Fire sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Fire or null
     */
    public static FireMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Fire sub-type based on age.
     * It will never return null.
     *
     * @param age age of fire.
     *
     * @return sub-type of Fire
     */
    public static FireMat getFire(final int age)
    {
        final FireMat fire = getByID(age);
        if (fire == null)
        {
            return FIRE_0;
        }
        return fire;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FireMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public FireMat[] types()
    {
        return FireMat.fireTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static FireMat[] fireTypes()
    {
        return byID.values(new FireMat[byID.size()]);
    }

    static
    {
        FireMat.register(FIRE_0);
        FireMat.register(FIRE_1);
        FireMat.register(FIRE_2);
        FireMat.register(FIRE_3);
        FireMat.register(FIRE_4);
        FireMat.register(FIRE_5);
        FireMat.register(FIRE_6);
        FireMat.register(FIRE_7);
        FireMat.register(FIRE_8);
        FireMat.register(FIRE_9);
        FireMat.register(FIRE_10);
        FireMat.register(FIRE_11);
        FireMat.register(FIRE_12);
        FireMat.register(FIRE_13);
        FireMat.register(FIRE_14);
        FireMat.register(FIRE_15);
    }
}
