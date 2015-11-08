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
 * Class representing 'Stained Hardened Clay' block material in minecraft. <br>
 * ID of block: 159 <br>
 * String ID of block: minecraft:stained_hardened_clay <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * BLACK:
 * Type name: 'Black' <br>
 * SubID: 15 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * RED:
 * Type name: 'Red' <br>
 * SubID: 14 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * GREEN:
 * Type name: 'Green' <br>
 * SubID: 13 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * BROWN:
 * Type name: 'Brown' <br>
 * SubID: 12 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * BLUE:
 * Type name: 'Blue' <br>
 * SubID: 11 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * PURPLE:
 * Type name: 'Purple' <br>
 * SubID: 10 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * CYAN:
 * Type name: 'Cyan' <br>
 * SubID: 9 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * LIGHT_GRAY:
 * Type name: 'Light Gray' <br>
 * SubID: 8 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * GRAY:
 * Type name: 'Gray' <br>
 * SubID: 7 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * PINK:
 * Type name: 'Pink' <br>
 * SubID: 6 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * LIME:
 * Type name: 'Lime' <br>
 * SubID: 5 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * YELLOW:
 * Type name: 'Yellow' <br>
 * SubID: 4 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * LIGHT_BLUE:
 * Type name: 'Light Blue' <br>
 * SubID: 3 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * MAGENTA:
 * Type name: 'Magenta' <br>
 * SubID: 2 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * ORANGE:
 * Type name: 'Orange' <br>
 * SubID: 1 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * WHITE:
 * Type name: 'White' <br>
 * SubID: 0 <br>
 * Hardness: 1,25 <br>
 * Blast Resistance 30 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class StainedHardenedClayMat extends BlockMaterialData implements ColorableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_WHITE      = new StainedHardenedClayMat();
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_ORANGE     = new StainedHardenedClayMat(DyeColor.ORANGE);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_MAGENTA    = new StainedHardenedClayMat(DyeColor.MAGENTA);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_LIGHT_BLUE = new StainedHardenedClayMat(DyeColor.LIGHT_BLUE);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_YELLOW     = new StainedHardenedClayMat(DyeColor.YELLOW);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_LIME       = new StainedHardenedClayMat(DyeColor.LIME);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_PINK       = new StainedHardenedClayMat(DyeColor.PINK);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_GRAY       = new StainedHardenedClayMat(DyeColor.GRAY);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_SILVER     = new StainedHardenedClayMat(DyeColor.LIGHT_GRAY);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_CYAN       = new StainedHardenedClayMat(DyeColor.CYAN);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_PURPLE     = new StainedHardenedClayMat(DyeColor.PURPLE);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_BLUE       = new StainedHardenedClayMat(DyeColor.BLUE);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_BROWN      = new StainedHardenedClayMat(DyeColor.BROWN);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_GREEN      = new StainedHardenedClayMat(DyeColor.GREEN);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_RED        = new StainedHardenedClayMat(DyeColor.RED);
    public static final StainedHardenedClayMat STAINED_HARDENED_CLAY_BLACK      = new StainedHardenedClayMat(DyeColor.BLACK);

    private static final Map<String, StainedHardenedClayMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StainedHardenedClayMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected StainedHardenedClayMat()
    {
        super("STAINED_HARDENED_CLAY", 159, "minecraft:stained_hardened_clay", "WHITE", (byte) 0x00, 1.25f, 30);
        this.color = DyeColor.WHITE;
    }

    protected StainedHardenedClayMat(final DyeColor color)
    {
        super(STAINED_HARDENED_CLAY_WHITE.name(), STAINED_HARDENED_CLAY_WHITE.ordinal(), STAINED_HARDENED_CLAY_WHITE.getMinecraftId(), color.name(), (short) color.getBlockFlag(), STAINED_HARDENED_CLAY_WHITE.getHardness(), STAINED_HARDENED_CLAY_WHITE.getBlastResistance());
        this.color = color;
    }

    protected StainedHardenedClayMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final DyeColor color, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.color = color;
    }

    @Override
    public StainedHardenedClayMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StainedHardenedClayMat getType(final int id)
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
    public StainedHardenedClayMat getColor(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Returns one of StainedHardenedClay sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StainedHardenedClay or null
     */
    public static StainedHardenedClayMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StainedHardenedClay sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StainedHardenedClay or null
     */
    public static StainedHardenedClayMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of StainedHardenedClay sub-type based on {@link DyeColor}.
     * It will never return null;
     *
     * @param color color of StainedHardenedClay
     *
     * @return sub-type of StainedHardenedClay
     */
    public static StainedHardenedClayMat getStainedHardenedClay(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StainedHardenedClayMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public StainedHardenedClayMat[] types()
    {
        return StainedHardenedClayMat.stainedHardenedClayTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StainedHardenedClayMat[] stainedHardenedClayTypes()
    {
        return byID.values(new StainedHardenedClayMat[byID.size()]);
    }

    static
    {
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_WHITE);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_ORANGE);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_MAGENTA);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_LIGHT_BLUE);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_YELLOW);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_LIME);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_PINK);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_GRAY);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_SILVER);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_CYAN);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_PURPLE);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_BLUE);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_BROWN);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_GREEN);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_RED);
        StainedHardenedClayMat.register(STAINED_HARDENED_CLAY_BLACK);
    }
}
