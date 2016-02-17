/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.utils.math;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("MagicNumber")
public class RomanNumeral
{
    private static final Map<Integer, String> baseValues = new HashMap<>(10);
    private final     int    num;
    private transient String romanStr;

    static
    {
        baseValues.put(0, "");
        baseValues.put(1, "I");
        baseValues.put(2, "II");
        baseValues.put(3, "III");
        baseValues.put(4, "IV");
        baseValues.put(5, "V");
        baseValues.put(6, "VI");
        baseValues.put(7, "VII");
        baseValues.put(8, "VIII");
        baseValues.put(9, "IX");
        baseValues.put(10, "X");
        baseValues.put(20, "XX");
        baseValues.put(30, "XXX");
        baseValues.put(40, "XL");
        baseValues.put(50, "L");
        baseValues.put(60, "LX");
        baseValues.put(70, "LXX");
        baseValues.put(80, "LXXX");
        baseValues.put(90, "XC");
        baseValues.put(100, "C");
        baseValues.put(200, "CC");
        baseValues.put(300, "CCC");
        baseValues.put(400, "CD");
        baseValues.put(500, "D");
        baseValues.put(600, "DC");
        baseValues.put(700, "DCC");
        baseValues.put(800, "DCCC");
        baseValues.put(900, "CM");
        baseValues.put(1000, "M");
    }

    public RomanNumeral(final int arabic, final boolean valid)
    {
        if (valid && (arabic < 0))
        {
            throw new NumberFormatException("Value of RomanNumeral must be positive.");
        }
        if (valid && (arabic > 3999))
        {
            throw new NumberFormatException("Value of RomanNumeral must be 3999 or less.");
        }
        this.num = arabic;
    }

    public RomanNumeral(final int arabic)
    {
        this(arabic, true);
    }

    public RomanNumeral(final String roman, final boolean safeForm)
    {
        if (safeForm)
        {
            this.romanStr = roman;
        }
        this.num = toInt(roman);
    }

    public RomanNumeral(final String roman)
    {
        this(roman, false);
    }

    public RomanNumeral add(final RomanNumeral other)
    {
        return new RomanNumeral(this.num + other.num, false);
    }

    public RomanNumeral subtract(final RomanNumeral other)
    {
        return new RomanNumeral(this.num - other.num, false);
    }

    public RomanNumeral multiple(final RomanNumeral other)
    {
        return new RomanNumeral(this.num * other.num, false);
    }

    public RomanNumeral divide(final RomanNumeral other)
    {
        return new RomanNumeral(this.num / other.num, false);
    }

    @Override
    public String toString()
    {
        if (this.romanStr == null)
        {
            this.romanStr = toString(this.num);
        }
        return this.romanStr;
    }

    public int toInt()
    {
        return this.num;
    }

    private static int letterToNumber(final char letter)
    {
        switch (letter)
        {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new NumberFormatException("Illegal character \"" + letter + "\" in Roman numeral");
        }
    }

    private static int pow(int i, final int pow)
    {
        if (pow == 0)
        {
            return 1;
        }
        if (pow == 1)
        {
            return i;
        }
        for (int k = 0; k < (pow - 1); k++)
        {
            i *= i;
        }
        return i;
    }

    public static int toInt(String roman) throws NumberFormatException
    {
        final boolean negative = roman.startsWith("-");
        if (negative)
        {
            roman = roman.substring(1);
        }
        if (roman.isEmpty())
        {
            return 0;
        }
        final char[] charArray = roman.toUpperCase().toCharArray();
        int lastValue = 0;
        int lastMulti = 1;
        int result = 0;
        for (int i = 0, charArrayLength = charArray.length; i < charArrayLength; i++)
        {
            final char c = charArray[i];
            final int value = letterToNumber(c);
            if (i == 0)
            {
                if ((i + 1) >= charArrayLength)
                {
                    result += value;
                    break;
                }
                lastValue = value;
                continue;
            }
            if (value == lastValue)
            {
                lastMulti++;
                continue;
            }
            if (lastValue < value)
            {
                if (lastMulti != 0)
                {
                    result += (value - (lastValue * lastMulti));
                }
                else
                {
                    result += (value - (lastValue << 1));
                }
            }
            else
            {
                result += lastValue * lastMulti;
                result += value;
            }
            lastMulti = 0;
            lastValue = value;

        }
        if (lastMulti != 0)
        {
            result += (lastValue * lastMulti);
        }
        return negative ? - result : result;
    }

    public static String toString(int num)
    {
        boolean negative = false;
        if (num < 0)
        {
            num *= - 1;
            negative = true;
        }
        String roman = baseValues.get(num);
        if (roman == null)
        {
            roman = negative ? "-" : "";
        }
        else
        {
            return roman;
        }
        while (num >= 1000)
        {
            roman += "M";
            num -= 1000;
        }
        if (num == 0)
        {
            return roman;
        }
        final String str = Integer.toString(num);
        final char[] charArray = str.toCharArray();
        final String[] romanParts = new String[charArray.length];
        int k = 0;
        for (int i = charArray.length - 1; i >= 0; i--)
        {
            final char c = charArray[i];
            switch (c)
            {
                case '1':
                    romanParts[i] = baseValues.get(pow(10, k));
                    break;
                case '2':
                    romanParts[i] = baseValues.get(2 * pow(10, k));
                    break;
                case '3':
                    romanParts[i] = baseValues.get(3 * pow(10, k));
                    break;
                case '4':
                    romanParts[i] = baseValues.get(4 * pow(10, k));
                    break;
                case '5':
                    romanParts[i] = baseValues.get(5 * pow(10, k));
                    break;
                case '6':
                    romanParts[i] = baseValues.get(6 * pow(10, k));
                    break;
                case '7':
                    romanParts[i] = baseValues.get(7 * pow(10, k));
                    break;
                case '8':
                    romanParts[i] = baseValues.get(8 * pow(10, k));
                    break;
                case '9':
                    romanParts[i] = baseValues.get(9 * pow(10, k));
                    break;
                default:
                    break;
            }
            k++;
        }
        return roman + StringUtils.join(romanParts);
    }
}