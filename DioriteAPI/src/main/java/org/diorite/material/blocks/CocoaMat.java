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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.AttachableMat;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.math.ByteRange;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Cocoa North 0' block material in minecraft. <br>
 * ID of block: 127 <br>
 * String ID of block: minecraft:cocoa <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 15 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * WEST_RIPE:
 * Type name: 'West Ripe' <br>
 * SubID: 11 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SOUTH_RIPE:
 * Type name: 'South Ripe' <br>
 * SubID: 10 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * EAST_RIPE:
 * Type name: 'East Ripe' <br>
 * SubID: 9 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * NORTH_RIPE:
 * Type name: 'North Ripe' <br>
 * SubID: 8 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * WEST_1:
 * Type name: 'West 1' <br>
 * SubID: 7 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SOUTH_1:
 * Type name: 'South 1' <br>
 * SubID: 6 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * EAST_1:
 * Type name: 'East 1' <br>
 * SubID: 5 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * NORTH_1:
 * Type name: 'North 1' <br>
 * SubID: 4 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * WEST_0:
 * Type name: 'West 0' <br>
 * SubID: 3 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SOUTH_0:
 * Type name: 'South 0' <br>
 * SubID: 2 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * EAST_0:
 * Type name: 'East 0' <br>
 * SubID: 1 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * NORTH_0:
 * Type name: 'North 0' <br>
 * SubID: 0 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class CocoaMat extends CropsMat implements AttachableMat
{
    /**
     * Age range of Cocoa, from 0 to 2.
     */
    public static final ByteRange AGE_RANGE        = new ByteRange(0, 2);
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int       USED_DATA_VALUES = 12;

    public static final CocoaMat COCOA_NORTH_0 = new CocoaMat();
    public static final CocoaMat COCOA_EAST_0  = new CocoaMat("EAST_0", BlockFace.EAST, 0);
    public static final CocoaMat COCOA_SOUTH_0 = new CocoaMat("SOUTH_0", BlockFace.SOUTH, 0);
    public static final CocoaMat COCOA_WEST_0  = new CocoaMat("WEST_0", BlockFace.WEST, 0);

    public static final CocoaMat COCOA_NORTH_1 = new CocoaMat("NORTH_1", BlockFace.NORTH, 1);
    public static final CocoaMat COCOA_EAST_1  = new CocoaMat("EAST_1", BlockFace.EAST, 1);
    public static final CocoaMat COCOA_SOUTH_1 = new CocoaMat("SOUTH_1", BlockFace.SOUTH, 1);
    public static final CocoaMat COCOA_WEST_1  = new CocoaMat("WEST_1", BlockFace.WEST, 1);

    public static final CocoaMat COCOA_NORTH_RIPE = new CocoaMat("NORTH_RIPE", BlockFace.NORTH, 2);
    public static final CocoaMat COCOA_EAST_RIPE  = new CocoaMat("EAST_RIPE", BlockFace.EAST, 2);
    public static final CocoaMat COCOA_SOUTH_RIPE = new CocoaMat("SOUTH_RIPE", BlockFace.SOUTH, 2);
    public static final CocoaMat COCOA_WEST_RIPE  = new CocoaMat("WEST_RIPE", BlockFace.WEST, 2);

    private static final Map<String, CocoaMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CocoaMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;
    protected final int       age;

    @SuppressWarnings("MagicNumber")
    protected CocoaMat()
    {
        super("COCOA_NORTH_0", 127, "minecraft:cocoa", "NORTH_0", (byte) 0x00, 0.2f, 15);
        this.face = BlockFace.NORTH;
        this.age = 0;
    }

    protected CocoaMat(final String enumName, final BlockFace face, final int age)
    {
        super(COCOA_NORTH_0.name(), COCOA_NORTH_0.ordinal(), COCOA_NORTH_0.getMinecraftId(), enumName, combine(face, age), COCOA_NORTH_0.getHardness(), COCOA_NORTH_0.getBlastResistance());
        this.face = face;
        this.age = age;
    }

    protected CocoaMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final int age, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
        this.age = age;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return COCOA;
    }

    @Override
    public int getAge()
    {
        return this.age;
    }

    @Override
    public CocoaMat getAge(final int age)
    {
        return getByID(combine(this.face, age));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public CocoaMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.age));
    }

    @Override
    public CocoaMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CocoaMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("age", this.age).toString();
    }

    /**
     * Returns one of Cocoa sub-type based on {@link BlockFace} and age.
     * It will never return null.
     *
     * @param face facing direction of Cocoa.
     * @param age  age of Cocoa.
     *
     * @return sub-type of Cocoa
     */
    public CocoaMat getType(final BlockFace face, final int age)
    {
        return getByID(combine(face, this.age));
    }

    @Override
    public CocoaMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace(), this.age));
    }

    private static byte combine(final BlockFace face, final int age)
    {
        byte result;
        switch (face)
        {
            case EAST:
                result = 0x1;
                break;
            case SOUTH:
                result = 0x2;
                break;
            case WEST:
                result = 0x3;
                break;
            default:
                result = 0x0;
                break;
        }
        result |= (AGE_RANGE.getIn(age) << 2);
        return result;
    }

    /**
     * Returns one of Cocoa sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Cocoa or null
     */
    public static CocoaMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Cocoa sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Cocoa or null
     */
    public static CocoaMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Cocoa sub-type based on {@link BlockFace} and age.
     * It will never return null.
     *
     * @param face facing direction of Cocoa.
     * @param age  age of Cocoa.
     *
     * @return sub-type of Cocoa
     */
    public static CocoaMat getCocoa(final BlockFace face, final int age)
    {
        return getByID(combine(face, age));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CocoaMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CocoaMat[] types()
    {
        return CocoaMat.cocoaTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CocoaMat[] cocoaTypes()
    {
        return byID.values(new CocoaMat[byID.size()]);
    }

    static
    {
        CocoaMat.register(COCOA_NORTH_0);
        CocoaMat.register(COCOA_EAST_0);
        CocoaMat.register(COCOA_SOUTH_0);
        CocoaMat.register(COCOA_WEST_0);
        CocoaMat.register(COCOA_NORTH_1);
        CocoaMat.register(COCOA_EAST_1);
        CocoaMat.register(COCOA_SOUTH_1);
        CocoaMat.register(COCOA_WEST_1);
        CocoaMat.register(COCOA_NORTH_RIPE);
        CocoaMat.register(COCOA_EAST_RIPE);
        CocoaMat.register(COCOA_SOUTH_RIPE);
        CocoaMat.register(COCOA_WEST_RIPE);
    }
}
