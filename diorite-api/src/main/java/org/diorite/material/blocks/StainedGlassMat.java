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
 * Class representing 'Stained Glass' block material in minecraft. <br>
 * ID of block: 95 <br>
 * String ID of block: minecraft:stained_glass <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * BLACK:
 * Type name: 'Black' <br>
 * SubID: 15 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * RED:
 * Type name: 'Red' <br>
 * SubID: 14 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * GREEN:
 * Type name: 'Green' <br>
 * SubID: 13 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * BROWN:
 * Type name: 'Brown' <br>
 * SubID: 12 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * BLUE:
 * Type name: 'Blue' <br>
 * SubID: 11 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * PURPLE:
 * Type name: 'Purple' <br>
 * SubID: 10 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * CYAN:
 * Type name: 'Cyan' <br>
 * SubID: 9 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * LIGHT_GRAY:
 * Type name: 'Light Gray' <br>
 * SubID: 8 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * GRAY:
 * Type name: 'Gray' <br>
 * SubID: 7 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * PINK:
 * Type name: 'Pink' <br>
 * SubID: 6 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * LIME:
 * Type name: 'Lime' <br>
 * SubID: 5 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * YELLOW:
 * Type name: 'Yellow' <br>
 * SubID: 4 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * LIGHT_BLUE:
 * Type name: 'Light Blue' <br>
 * SubID: 3 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * MAGENTA:
 * Type name: 'Magenta' <br>
 * SubID: 2 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * ORANGE:
 * Type name: 'Orange' <br>
 * SubID: 1 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * <li>
 * WHITE:
 * Type name: 'White' <br>
 * SubID: 0 <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class StainedGlassMat extends BlockMaterialData implements ColorableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final StainedGlassMat STAINED_GLASS_WHITE      = new StainedGlassMat();
    public static final StainedGlassMat STAINED_GLASS_ORANGE     = new StainedGlassMat(DyeColor.ORANGE);
    public static final StainedGlassMat STAINED_GLASS_MAGENTA    = new StainedGlassMat(DyeColor.MAGENTA);
    public static final StainedGlassMat STAINED_GLASS_LIGHT_BLUE = new StainedGlassMat(DyeColor.LIGHT_BLUE);
    public static final StainedGlassMat STAINED_GLASS_YELLOW     = new StainedGlassMat(DyeColor.YELLOW);
    public static final StainedGlassMat STAINED_GLASS_LIME       = new StainedGlassMat(DyeColor.LIME);
    public static final StainedGlassMat STAINED_GLASS_PINK       = new StainedGlassMat(DyeColor.PINK);
    public static final StainedGlassMat STAINED_GLASS_GRAY       = new StainedGlassMat(DyeColor.GRAY);
    public static final StainedGlassMat STAINED_GLASS_SILVER     = new StainedGlassMat(DyeColor.LIGHT_GRAY);
    public static final StainedGlassMat STAINED_GLASS_CYAN       = new StainedGlassMat(DyeColor.CYAN);
    public static final StainedGlassMat STAINED_GLASS_PURPLE     = new StainedGlassMat(DyeColor.PURPLE);
    public static final StainedGlassMat STAINED_GLASS_BLUE       = new StainedGlassMat(DyeColor.BLUE);
    public static final StainedGlassMat STAINED_GLASS_BROWN      = new StainedGlassMat(DyeColor.BROWN);
    public static final StainedGlassMat STAINED_GLASS_GREEN      = new StainedGlassMat(DyeColor.GREEN);
    public static final StainedGlassMat STAINED_GLASS_RED        = new StainedGlassMat(DyeColor.RED);
    public static final StainedGlassMat STAINED_GLASS_BLACK      = new StainedGlassMat(DyeColor.BLACK);

    private static final Map<String, StainedGlassMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StainedGlassMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final DyeColor color;

    @SuppressWarnings("MagicNumber")
    protected StainedGlassMat()
    {
        super("STAINED_GLASS", 95, "minecraft:stained_glass", "WHITE", (byte) 0x00, 0.3f, 1.5f);
        this.color = DyeColor.WHITE;
    }

    protected StainedGlassMat(final DyeColor color)
    {
        super(STAINED_GLASS_WHITE.name(), STAINED_GLASS_WHITE.ordinal(), STAINED_GLASS_WHITE.getMinecraftId(), color.name(), (short) color.getBlockFlag(), STAINED_GLASS_WHITE.getHardness(), STAINED_GLASS_WHITE.getBlastResistance());
        this.color = color;
    }

    protected StainedGlassMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final DyeColor color, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.color = color;
    }

    @Override
    public StainedGlassMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StainedGlassMat getType(final int id)
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
    public StainedGlassMat getColor(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Returns one of StainedGlass sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StainedGlass or null
     */
    public static StainedGlassMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StainedGlass sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StainedGlass or null
     */
    public static StainedGlassMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of StainedGlass sub-type based on {@link DyeColor}.
     * It will never return null;
     *
     * @param color color of StainedGlass
     *
     * @return sub-type of StainedGlass
     */
    public static StainedGlassMat getStainedGlass(final DyeColor color)
    {
        return getByID(color.getBlockFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StainedGlassMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public StainedGlassMat[] types()
    {
        return StainedGlassMat.stainedGlassTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StainedGlassMat[] stainedGlassTypes()
    {
        return byID.values(new StainedGlassMat[byID.size()]);
    }

    static
    {
        StainedGlassMat.register(STAINED_GLASS_WHITE);
        StainedGlassMat.register(STAINED_GLASS_ORANGE);
        StainedGlassMat.register(STAINED_GLASS_MAGENTA);
        StainedGlassMat.register(STAINED_GLASS_LIGHT_BLUE);
        StainedGlassMat.register(STAINED_GLASS_YELLOW);
        StainedGlassMat.register(STAINED_GLASS_LIME);
        StainedGlassMat.register(STAINED_GLASS_PINK);
        StainedGlassMat.register(STAINED_GLASS_GRAY);
        StainedGlassMat.register(STAINED_GLASS_SILVER);
        StainedGlassMat.register(STAINED_GLASS_CYAN);
        StainedGlassMat.register(STAINED_GLASS_PURPLE);
        StainedGlassMat.register(STAINED_GLASS_BLUE);
        StainedGlassMat.register(STAINED_GLASS_BROWN);
        StainedGlassMat.register(STAINED_GLASS_GREEN);
        StainedGlassMat.register(STAINED_GLASS_RED);
        StainedGlassMat.register(STAINED_GLASS_BLACK);
    }
}
