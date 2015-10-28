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

package org.diorite;

import org.diorite.utils.Color;
import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

@SuppressWarnings({"MagicNumber", "ClassHasNoToStringMethod"})
public class DyeColor extends ASimpleEnum<DyeColor>
{
    static
    {
        init(DyeColor.class, 16);
    }

    public static final DyeColor WHITE      = new DyeColor("WHITE", 0x0, 0xF, Color.fromRGB(0xFFFFFF), Color.fromRGB(0xF0F0F0));
    public static final DyeColor ORANGE     = new DyeColor("ORANGE", 0x1, 0xE, Color.fromRGB(0xD87F33), Color.fromRGB(0xEB8844));
    public static final DyeColor MAGENTA    = new DyeColor("MAGENTA", 0x2, 0xD, Color.fromRGB(0xB24CD8), Color.fromRGB(0xC354CD));
    public static final DyeColor LIGHT_BLUE = new DyeColor("LIGHT_BLUE", 0x3, 0xC, Color.fromRGB(0x6699D8), Color.fromRGB(0x6689D3));
    public static final DyeColor YELLOW     = new DyeColor("YELLOW", 0x4, 0xB, Color.fromRGB(0xE5E533), Color.fromRGB(0xDECF2A));
    public static final DyeColor LIME       = new DyeColor("LIME", 0x5, 0xA, Color.fromRGB(0x7FCC19), Color.fromRGB(0x41CD34));
    public static final DyeColor PINK       = new DyeColor("PINK", 0x6, 0x9, Color.fromRGB(0xF27FA5), Color.fromRGB(0xD88198));
    public static final DyeColor GRAY       = new DyeColor("GRAY", 0x7, 0x8, Color.fromRGB(0x4C4C4C), Color.fromRGB(0x434343));
    public static final DyeColor LIGHT_GRAY = new DyeColor("LIGHT_GRAY", 0x8, 0x7, Color.fromRGB(0x999999), Color.fromRGB(0xABABAB));
    public static final DyeColor CYAN       = new DyeColor("CYAN", 0x9, 0x6, Color.fromRGB(0x4C7F99), Color.fromRGB(0x287697));
    public static final DyeColor PURPLE     = new DyeColor("PURPLE", 0xA, 0x5, Color.fromRGB(0x7F3FB2), Color.fromRGB(0x7B2FBE));
    public static final DyeColor BLUE       = new DyeColor("BLUE", 0xB, 0x4, Color.fromRGB(0x334CB2), Color.fromRGB(0x253192));
    public static final DyeColor BROWN      = new DyeColor("BROWN", 0xC, 0x3, Color.fromRGB(0x664C33), Color.fromRGB(0x51301A));
    public static final DyeColor GREEN      = new DyeColor("GREEN", 0xD, 0x2, Color.fromRGB(0x667F33), Color.fromRGB(0x3B511A));
    public static final DyeColor RED        = new DyeColor("RED", 0xE, 0x1, Color.fromRGB(0x993333), Color.fromRGB(0xB3312C));
    public static final DyeColor BLACK      = new DyeColor("BLACK", 0xF, 0x0, Color.fromRGB(0x191919), Color.fromRGB(0x1E1B1B));

    private static final TByteObjectMap<DyeColor> byBlockFlag = new TByteObjectHashMap<>(16, SMALL_LOAD_FACTOR, (byte) - 1);
    private static final TByteObjectMap<DyeColor> byItemFlag  = new TByteObjectHashMap<>(16, SMALL_LOAD_FACTOR, (byte) - 1);

    private final byte  blockFlag;
    private final byte  itemFlag;
    private final Color color;
    private final Color fireworkColor;

    protected DyeColor(final String enumName, final int enumId, final int blockFlag, final int itemFlag, final Color color, final Color fireworkColor)
    {
        super(enumName, enumId);
        this.blockFlag = (byte) blockFlag;
        this.itemFlag = (byte) itemFlag;
        this.color = color;
        this.fireworkColor = fireworkColor;
    }

    protected DyeColor(final String enumName, final int blockFlag, final int itemFlag, final Color color, final Color fireworkColor)
    {
        super(enumName);
        this.blockFlag = (byte) blockFlag;
        this.itemFlag = (byte) itemFlag;
        this.color = color;
        this.fireworkColor = fireworkColor;
    }

    /**
     * Returns color representation for firework.
     *
     * @return color representation for firework.
     */
    public Color getFireworkColor()
    {
        return this.fireworkColor;
    }

    /**
     * Returns int peresentation used by items.
     *
     * @return int peresentation used by items.
     */
    public int getItemFlag()
    {
        return this.itemFlag;
    }

    /**
     * Returns int peresentation used by blocks.
     *
     * @return int peresentation used by blocks.
     */
    public int getBlockFlag()
    {
        return this.blockFlag;
    }


    /**
     * Returns color representation of this dye color.
     *
     * @return color representation of this dye color.
     */
    public Color getColor()
    {
        return this.color;
    }

    /**
     * Register new {@link DyeColor} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final DyeColor element)
    {
        ASimpleEnum.register(DyeColor.class, element);
        byBlockFlag.put(element.blockFlag, element);
        byItemFlag.put(element.itemFlag, element);
    }

    /**
     * Get one of {@link DyeColor} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static DyeColor getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(DyeColor.class, ordinal);
    }

    /**
     * Get one of DyeColor entry by its item flag.
     *
     * @param flag item flag of entry.
     *
     * @return one of entry or null.
     */
    public static DyeColor getByItemFlag(final int flag)
    {
        return byItemFlag.get((byte) flag);
    }

    /**
     * Get one of DyeColor entry by its block flag.
     *
     * @param flag block flag of entry.
     *
     * @return one of entry or null.
     */
    public static DyeColor getByBlockFlag(final int flag)
    {
        return byBlockFlag.get((byte) flag);
    }

    /**
     * Get one of DyeColor entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static DyeColor getByEnumName(final String name)
    {
        return getByEnumName(DyeColor.class, name);
    }

    /**
     * @return all values in array.
     */
    public static DyeColor[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(DyeColor.class);
        return (DyeColor[]) map.values(new DyeColor[map.size()]);
    }

    static
    {
        DyeColor.register(WHITE);
        DyeColor.register(ORANGE);
        DyeColor.register(MAGENTA);
        DyeColor.register(LIGHT_BLUE);
        DyeColor.register(YELLOW);
        DyeColor.register(LIME);
        DyeColor.register(PINK);
        DyeColor.register(GRAY);
        DyeColor.register(LIGHT_GRAY);
        DyeColor.register(CYAN);
        DyeColor.register(PURPLE);
        DyeColor.register(BLUE);
        DyeColor.register(BROWN);
        DyeColor.register(GREEN);
        DyeColor.register(RED);
        DyeColor.register(BLACK);
    }
}
