/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.commons.math;

import org.junit.Assert;
import org.junit.Test;

public class RomanNumeralTest
{
    @Test
    public void testToRoman()
    {
        Assert.assertEquals("I", RomanNumeral.toString(1));
        Assert.assertEquals("I", RomanNumeral.toString(1));
        Assert.assertEquals("III", RomanNumeral.toString(3));
        Assert.assertEquals("IV", RomanNumeral.toString(4));
        Assert.assertEquals("V", RomanNumeral.toString(5));
        Assert.assertEquals("VI", RomanNumeral.toString(6));
        Assert.assertEquals("IX", RomanNumeral.toString(9));
        Assert.assertEquals("X", RomanNumeral.toString(10));
        Assert.assertEquals("XL", RomanNumeral.toString(40));
        Assert.assertEquals("XLV", RomanNumeral.toString(45));
        Assert.assertEquals("XLIX", RomanNumeral.toString(49));
        Assert.assertEquals("L", RomanNumeral.toString(50));
        Assert.assertEquals("MCMXCIX", RomanNumeral.toString(1999));
    }

    @Test
    public void testThisSame()
    {
        for (int i = (Short.MIN_VALUE / 5); i < (Short.MAX_VALUE / 5); i++)
        {
            String roman = RomanNumeral.toString(i);
            int backToInt = RomanNumeral.toInt(roman);
            Assert.assertEquals(i, backToInt);
            Assert.assertEquals(roman, RomanNumeral.toString(backToInt));
        }
    }
}
