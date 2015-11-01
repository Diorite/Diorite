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

import org.diorite.DyeColor;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.ColorableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Wool' block material in minecraft. <br>
 * ID of block: 35 <br>
 * String ID of block: minecraft:wool <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * BLACK:
 * Type name: 'Black' <br>
 * SubID: 15 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * RED:
 * Type name: 'Red' <br>
 * SubID: 14 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * GREEN:
 * Type name: 'Green' <br>
 * SubID: 13 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * BROWN:
 * Type name: 'Brown' <br>
 * SubID: 12 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * BLUE:
 * Type name: 'Blue' <br>
 * SubID: 11 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * PURPLE:
 * Type name: 'Purple' <br>
 * SubID: 10 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * CYAN:
 * Type name: 'Cyan' <br>
 * SubID: 9 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * LIGHT_GRAY:
 * Type name: 'Light Gray' <br>
 * SubID: 8 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * GRAY:
 * Type name: 'Gray' <br>
 * SubID: 7 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * PINK:
 * Type name: 'Pink' <br>
 * SubID: 6 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * LIME:
 * Type name: 'Lime' <br>
 * SubID: 5 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * YELLOW:
 * Type name: 'Yellow' <br>
 * SubID: 4 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * LIGHT_BLUE:
 * Type name: 'Light Blue' <br>
 * SubID: 3 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * MAGENTA:
 * Type name: 'Magenta' <br>
 * SubID: 2 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * ORANGE:
 * Type name: 'Orange' <br>
 * SubID: 1 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * WHITE:
 * Type name: 'White' <br>
 * SubID: 0 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class WoolMat extends BlockMaterialData implements ColorableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final WoolMat WOOL_WHITE      = new WoolMat();
    public static final WoolMat WOOL_ORANGE     = new WoolMat(DyeColor.ORANGE);
    public static final WoolMat WOOL_MAGENTA    = new WoolMat(DyeColor.MAGENTA);
    public static final WoolMat WOOL_LIGHT_BLUE = new WoolMat(DyeColor.LIGHT_BLUE);
    public static final WoolMat WOOL_YELLOW     = new WoolMat(DyeColor.YELLOW);
    public static final WoolMat WOOL_LIME       = new WoolMat(DyeColor.LIME);
    public static final WoolMat WOOL_PINK       = new WoolMat(DyeColor.PINK);
    public static final WoolMat WOOL_GRAY       = new WoolMat(DyeColor.GRAY);
    public static final WoolMat WOOL_SILVER     = new WoolMat(DyeColor.LIGHT_GRAY);
    public static final WoolMat WOOL_CYAN       = new WoolMat(DyeColor.CYAN);
    public static final WoolMat WOOL_PURPLE     = new WoolMat(DyeColor.PURPLE);
    public static final WoolMat WOOL_BLUE       = new WoolMat(DyeColor.BLUE);
    public static final WoolMat WOOL_BROWN      = new WoolMat(DyeColor.BROWN);
    public static final WoolMat WOOL_GREEN      = new WoolMat(DyeColor.GREEN);
    public static final WoolMat WOOL_RED        = new WoolMat(DyeColor.RED);
    public static final WoolMat WOOL_BLACK      = new WoolMat(DyeColor.BLACK);

    private static final Map<String, WoolMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WoolMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected WoolMat()
    {
        super("WOOL", 35, "minecraft:wool", "WHITE", (byte) 0x00, 0.8f, 4);
        this.color = DyeColor.WHITE;
    }

    protected WoolMat(final DyeColor color)
    {
        super(WOOL_WHITE.name(), WOOL_WHITE.ordinal(), WOOL_WHITE.getMinecraftId(), color.name(), (short) color.getBlockFlag(), WOOL_WHITE.getHardness(), WOOL_WHITE.getBlastResistance());
        this.color = color;
    }

    protected WoolMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final DyeColor color, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.color = color;
    }

    @Override
    public WoolMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WoolMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("color", this.color).toString();
    }

    @Override
    public DyeColor getColor()
    {
        return this.color;
    }

    @Override
    public WoolMat getColor(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Returns one of Wool sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Wool or null
     */
    public static WoolMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Wool sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Wool or null
     */
    public static WoolMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Wool sub-type based on {@link DyeColor}.
     * It will never return null;
     *
     * @param color color of Wool
     *
     * @return sub-type of Wool
     */
    public static WoolMat getWool(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WoolMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public WoolMat[] types()
    {
        return WoolMat.woolTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WoolMat[] woolTypes()
    {
        return byID.values(new WoolMat[byID.size()]);
    }

    static
    {
        WoolMat.register(WOOL_WHITE);
        WoolMat.register(WOOL_ORANGE);
        WoolMat.register(WOOL_MAGENTA);
        WoolMat.register(WOOL_LIGHT_BLUE);
        WoolMat.register(WOOL_YELLOW);
        WoolMat.register(WOOL_LIME);
        WoolMat.register(WOOL_PINK);
        WoolMat.register(WOOL_GRAY);
        WoolMat.register(WOOL_SILVER);
        WoolMat.register(WOOL_CYAN);
        WoolMat.register(WOOL_PURPLE);
        WoolMat.register(WOOL_BLUE);
        WoolMat.register(WOOL_BROWN);
        WoolMat.register(WOOL_GREEN);
        WoolMat.register(WOOL_RED);
        WoolMat.register(WOOL_BLACK);
    }
}
