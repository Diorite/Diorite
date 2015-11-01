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
 * Class representing 'Carpet' block material in minecraft. <br>
 * ID of block: 171 <br>
 * String ID of block: minecraft:carpet <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * BLACK:
 * Type name: 'Black' <br>
 * SubID: 15 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * RED:
 * Type name: 'Red' <br>
 * SubID: 14 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * GREEN:
 * Type name: 'Green' <br>
 * SubID: 13 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * BROWN:
 * Type name: 'Brown' <br>
 * SubID: 12 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * BLUE:
 * Type name: 'Blue' <br>
 * SubID: 11 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * PURPLE:
 * Type name: 'Purple' <br>
 * SubID: 10 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * CYAN:
 * Type name: 'Cyan' <br>
 * SubID: 9 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * LIGHT_GRAY:
 * Type name: 'Light Gray' <br>
 * SubID: 8 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * GRAY:
 * Type name: 'Gray' <br>
 * SubID: 7 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * PINK:
 * Type name: 'Pink' <br>
 * SubID: 6 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * LIME:
 * Type name: 'Lime' <br>
 * SubID: 5 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * YELLOW:
 * Type name: 'Yellow' <br>
 * SubID: 4 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * LIGHT_BLUE:
 * Type name: 'Light Blue' <br>
 * SubID: 3 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * MAGENTA:
 * Type name: 'Magenta' <br>
 * SubID: 2 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * ORANGE:
 * Type name: 'Orange' <br>
 * SubID: 1 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * <li>
 * WHITE:
 * Type name: 'White' <br>
 * SubID: 0 <br>
 * Hardness: 0,1 <br>
 * Blast Resistance 0,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class CarpetMat extends BlockMaterialData implements ColorableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final CarpetMat CARPET_WHITE      = new CarpetMat();
    public static final CarpetMat CARPET_ORANGE     = new CarpetMat(DyeColor.ORANGE);
    public static final CarpetMat CARPET_MAGENTA    = new CarpetMat(DyeColor.MAGENTA);
    public static final CarpetMat CARPET_LIGHT_BLUE = new CarpetMat(DyeColor.LIGHT_BLUE);
    public static final CarpetMat CARPET_YELLOW     = new CarpetMat(DyeColor.YELLOW);
    public static final CarpetMat CARPET_LIME       = new CarpetMat(DyeColor.LIME);
    public static final CarpetMat CARPET_PINK       = new CarpetMat(DyeColor.PINK);
    public static final CarpetMat CARPET_GRAY       = new CarpetMat(DyeColor.GRAY);
    public static final CarpetMat CARPET_SILVER     = new CarpetMat(DyeColor.LIGHT_GRAY);
    public static final CarpetMat CARPET_CYAN       = new CarpetMat(DyeColor.CYAN);
    public static final CarpetMat CARPET_PURPLE     = new CarpetMat(DyeColor.PURPLE);
    public static final CarpetMat CARPET_BLUE       = new CarpetMat(DyeColor.BLUE);
    public static final CarpetMat CARPET_BROWN      = new CarpetMat(DyeColor.BROWN);
    public static final CarpetMat CARPET_GREEN      = new CarpetMat(DyeColor.GREEN);
    public static final CarpetMat CARPET_RED        = new CarpetMat(DyeColor.RED);
    public static final CarpetMat CARPET_BLACK      = new CarpetMat(DyeColor.BLACK);

    private static final Map<String, CarpetMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CarpetMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected CarpetMat()
    {
        super("CARPET", 171, "minecraft:carpet", "WHITE", (short) DyeColor.WHITE.getBlockFlag(), 0.1f, 0.5f);
        this.color = DyeColor.WHITE;
    }

    protected CarpetMat(final DyeColor color)
    {
        super(CARPET_WHITE.name(), CARPET_WHITE.ordinal(), CARPET_WHITE.getMinecraftId(), color.name(), (short) color.getBlockFlag(), CARPET_WHITE.getHardness(), CARPET_WHITE.getBlastResistance());
        this.color = color;
    }

    protected CarpetMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final DyeColor color, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.color = color;
    }

    @Override
    public CarpetMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CarpetMat getType(final int id)
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
    public CarpetMat getColor(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Returns one of Carpet sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Carpet or null
     */
    public static CarpetMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Carpet sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Carpet or null
     */
    public static CarpetMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Carpet sub-type based on {@link DyeColor}.
     * It will never return null;
     *
     * @param color color of Carpet
     *
     * @return sub-type of Carpet
     */
    public static CarpetMat getCarpet(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CarpetMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CarpetMat[] types()
    {
        return CarpetMat.carpetTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CarpetMat[] carpetTypes()
    {
        return byID.values(new CarpetMat[byID.size()]);
    }

    static
    {
        CarpetMat.register(CARPET_WHITE);
        CarpetMat.register(CARPET_ORANGE);
        CarpetMat.register(CARPET_MAGENTA);
        CarpetMat.register(CARPET_LIGHT_BLUE);
        CarpetMat.register(CARPET_YELLOW);
        CarpetMat.register(CARPET_LIME);
        CarpetMat.register(CARPET_PINK);
        CarpetMat.register(CARPET_GRAY);
        CarpetMat.register(CARPET_SILVER);
        CarpetMat.register(CARPET_CYAN);
        CarpetMat.register(CARPET_PURPLE);
        CarpetMat.register(CARPET_BLUE);
        CarpetMat.register(CARPET_BROWN);
        CarpetMat.register(CARPET_GREEN);
        CarpetMat.register(CARPET_RED);
        CarpetMat.register(CARPET_BLACK);
    }
}
