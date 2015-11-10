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

package org.diorite.utils;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.DyeColor;

/*
 * Class from Bukkit project, edited by Diorite team.
 */
@SuppressWarnings("MagicNumber")
public class Color
{
    private static final int BIT_MASK = 0xff;

    /**
     * White, or (0xFF,0xFF,0xFF) in (R,G,B)
     */
    public static final Color WHITE = fromRGB(0xFFFFFF);

    /**
     * Silver, or (0xC0,0xC0,0xC0) in (R,G,B)
     */
    public static final Color SILVER = fromRGB(0xC0C0C0);

    /**
     * Gray, or (0x80,0x80,0x80) in (R,G,B)
     */
    public static final Color GRAY = fromRGB(0x808080);

    /**
     * Black, or (0x00,0x00,0x00) in (R,G,B)
     */
    public static final Color BLACK = fromRGB(0x000000);

    /**
     * Red, or (0xFF,0x00,0x00) in (R,G,B)
     */
    public static final Color RED = fromRGB(0xFF0000);

    /**
     * Maroon, or (0x80,0x00,0x00) in (R,G,B)
     */
    public static final Color MAROON = fromRGB(0x800000);

    /**
     * Yellow, or (0xFF,0xFF,0x00) in (R,G,B)
     */
    public static final Color YELLOW = fromRGB(0xFFFF00);

    /**
     * Olive, or (0x80,0x80,0x00) in (R,G,B)
     */
    public static final Color OLIVE = fromRGB(0x808000);

    /**
     * Lime, or (0x00,0xFF,0x00) in (R,G,B)
     */
    public static final Color LIME = fromRGB(0x00FF00);

    /**
     * Green, or (0x00,0x80,0x00) in (R,G,B)
     */
    public static final Color GREEN = fromRGB(0x008000);

    /**
     * Aqua, or (0x00,0xFF,0xFF) in (R,G,B)
     */
    public static final Color AQUA = fromRGB(0x00FFFF);

    /**
     * Teal, or (0x00,0x80,0x80) in (R,G,B)
     */
    public static final Color TEAL = fromRGB(0x008080);

    /**
     * Blue, or (0x00,0x00,0xFF) in (R,G,B)
     */
    public static final Color BLUE = fromRGB(0x0000FF);

    /**
     * Navy, or (0x00,0x00,0x80) in (R,G,B)
     */
    public static final Color NAVY = fromRGB(0x000080);

    /**
     * Fuchsia, or (0xFF,0x00,0xFF) in (R,G,B)
     */
    public static final Color FUCHSIA = fromRGB(0xFF00FF);

    /**
     * Purple, or (0x80,0x00,0x80) in (R,G,B)
     */
    public static final Color PURPLE = fromRGB(0x800080);

    /**
     * Orange, or (0xFF,0xA5,0x00) in (R,G,B)
     */
    public static final Color ORANGE = fromRGB(0xFFA500);

    private final byte red;
    private final byte green;
    private final byte blue;

    /**
     * Creates a new Color object from a red, green, and blue
     *
     * @param red   integer from 0-255
     * @param green integer from 0-255
     * @param blue  integer from 0-255
     *
     * @return a new Color object for the red, green, blue
     *
     * @throws IllegalArgumentException if any value is strictly {@literal >255 or <0}
     */
    public static Color fromRGB(final int red, final int green, final int blue) throws IllegalArgumentException
    {
        return new Color(red, green, blue);
    }

    /**
     * Creates a new Color object from a blue, green, and red
     *
     * @param blue  integer from 0-255
     * @param green integer from 0-255
     * @param red   integer from 0-255
     *
     * @return a new Color object for the red, green, blue
     *
     * @throws IllegalArgumentException if any value is strictly {@literal >255 or <0}
     */
    public static Color fromBGR(final int blue, final int green, final int red) throws IllegalArgumentException
    {
        return new Color(red, green, blue);
    }

    /**
     * Creates a new color object from an integer that contains the red,
     * green, and blue bytes in the lowest order 24 bits.
     *
     * @param rgb the integer storing the red, green, and blue values
     *
     * @return a new color object for specified values
     *
     * @throws IllegalArgumentException if any data is in the highest order 8
     *                                  bits
     */
    public static Color fromRGB(final int rgb) throws IllegalArgumentException
    {
        Validate.isTrue((rgb >> 24) == 0, "Extrenuous data in: ", rgb);
        return fromRGB((rgb >> 16) & BIT_MASK, (rgb >> 8) & BIT_MASK, (rgb) & BIT_MASK);
    }

    /**
     * Creates a new color object from an integer that contains the blue,
     * green, and red bytes in the lowest order 24 bits.
     *
     * @param bgr the integer storing the blue, green, and red values
     *
     * @return a new color object for specified values
     *
     * @throws IllegalArgumentException if any data is in the highest order 8
     *                                  bits
     */
    public static Color fromBGR(final int bgr) throws IllegalArgumentException
    {
        Validate.isTrue((bgr >> 24) == 0, "Extrenuous data in: ", bgr);
        return fromBGR((bgr >> 16) & BIT_MASK, (bgr >> 8) & BIT_MASK, (bgr) & BIT_MASK);
    }

    private Color(final int red, final int green, final int blue)
    {
        Validate.isTrue((red >= 0) && (red <= BIT_MASK), "Red is not between 0-255: ", red);
        Validate.isTrue((green >= 0) && (green <= BIT_MASK), "Green is not between 0-255: ", green);
        Validate.isTrue((blue >= 0) && (blue <= BIT_MASK), "Blue is not between 0-255: ", blue);

        this.red = (byte) red;
        this.green = (byte) green;
        this.blue = (byte) blue;
    }

    /**
     * Gets the red component
     *
     * @return red component, from 0 to 255
     */
    public int getRed()
    {
        return BIT_MASK & this.red;
    }

    /**
     * Creates a new Color object with specified component
     *
     * @param red the red component, from 0 to 255
     *
     * @return a new color object with the red component
     */
    public Color setRed(final int red)
    {
        return fromRGB(red, this.getGreen(), this.getBlue());
    }

    /**
     * Gets the green component
     *
     * @return green component, from 0 to 255
     */
    public int getGreen()
    {
        return BIT_MASK & this.green;
    }

    /**
     * Creates a new Color object with specified component
     *
     * @param green the red component, from 0 to 255
     *
     * @return a new color object with the red component
     */
    public Color setGreen(final int green)
    {
        return fromRGB(this.getRed(), green, this.getBlue());
    }

    /**
     * Gets the blue component
     *
     * @return blue component, from 0 to 255
     */
    public int getBlue()
    {
        return BIT_MASK & this.blue;
    }

    /**
     * Creates a new Color object with specified component
     *
     * @param blue the red component, from 0 to 255
     *
     * @return a new color object with the red component
     */
    public Color setBlue(final int blue)
    {
        return fromRGB(this.getRed(), this.getGreen(), blue);
    }

    /**
     * @return An integer representation of this color, as 0xRRGGBB
     */
    public int asRGB()
    {
        return (this.getRed() << 16) | (this.getGreen() << 8) | (this.getBlue());
    }

    /**
     * @return An integer representation of this color, as 0xBBGGRR
     */
    public int asBGR()
    {
        return (this.getBlue() << 16) | (this.getGreen() << 8) | (this.getRed());
    }

    /**
     * Creates a new color with its RGB components changed as if it was dyed
     * with the colors passed in, replicating vanilla workbench dyeing
     * (average colors of all arguments)
     *
     * @param colors The DyeColors to dye with
     *
     * @return A new color with the changed rgb components
     */
    public Color mixDyes(final DyeColor... colors)
    {
        final Color[] toPass = new Color[colors.length];
        for (int i = 0; i < colors.length; i++)
        {
            toPass[i] = colors[i].getColor();
        }
        return this.mixColors(toPass);
    }

    /**
     * Creates a new color with its RGB components changed as if it was dyed
     * with the colors passed in, replicating vanilla workbench dyeing
     * (average colors of all arguments)
     *
     * @param colors The colors to dye with
     *
     * @return A new color with the changed rgb components
     */
    public Color mixColors(final Color... colors)
    {
        int totalRed = this.getRed();
        int totalGreen = this.getGreen();
        int totalBlue = this.getBlue();
        int totalMax = Math.max(Math.max(totalRed, totalGreen), totalBlue);
        for (final Color color : colors)
        {
            totalRed += color.getRed();
            totalGreen += color.getGreen();
            totalBlue += color.getBlue();
            totalMax += Math.max(Math.max(color.getRed(), color.getGreen()), color.getBlue());
        }

        final float averageRed = totalRed / (colors.length + 1);
        final float averageGreen = totalGreen / (colors.length + 1);
        final float averageBlue = totalBlue / (colors.length + 1);
        final float averageMax = totalMax / (colors.length + 1);

        final float maximumOfAverages = Math.max(Math.max(averageRed, averageGreen), averageBlue);
        final float gainFactor = averageMax / maximumOfAverages;

        return Color.fromRGB((int) (averageRed * gainFactor), (int) (averageGreen * gainFactor), (int) (averageBlue * gainFactor));
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof Color))
        {
            return false;
        }

        final Color color = (Color) o;
        return (this.blue == color.blue) && (this.green == color.green) && (this.red == color.red);
    }

    @Override
    public int hashCode()
    {
        int result = (int) this.red;
        result = (31 * result) + (int) this.green;
        result = (31 * result) + (int) this.blue;
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("rgb", Integer.toHexString(this.asRGB())).toString();
    }
}
