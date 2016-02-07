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

package org.diorite.utils.collections.comparators;


import java.io.Serializable;
import java.util.Comparator;

/**
 * Human-friendly string comparator,
 * instead of simple string comparator, this check numbers in strings.
 * <br>
 * Default comparator:
 * Some-1-String
 * Some-10-String
 * Some-100-String
 * Some-2-String
 * Some-5-String
 * Some-50-String
 * <br>
 * AlphanumComparator:
 * Some-1-String
 * Some-2-String
 * Some-5-String
 * Some-10-String
 * Some-50-String
 * Some-100-String
 */
public class AlphanumComparator implements Comparator<String>, Serializable
{
    private static final long serialVersionUID = 0;

    private static boolean isDigit(final char ch)
    {
        return (ch >= '0') && (ch <= '9');
    }

    /**
     * Length of string is passed in for improved efficiency (only need to calculate it once) *
     */
    private static String getChunk(final CharSequence s, final int slength, int marker)
    {
        final StringBuilder chunk = new StringBuilder();
        char c = s.charAt(marker);
        chunk.append(c);
        marker++;
        if (isDigit(c))
        {
            while (marker < slength)
            {
                c = s.charAt(marker);
                if (! isDigit(c))
                {
                    break;
                }
                chunk.append(c);
                marker++;
            }
        }
        else
        {
            while (marker < slength)
            {
                c = s.charAt(marker);
                if (isDigit(c))
                {
                    break;
                }
                chunk.append(c);
                marker++;
            }
        }
        return chunk.toString();
    }

    @Override
    public int compare(final String s1, final String s2)
    {
        return compareStatic(s1, s2);
    }

    public static int compareStatic(final String s1, final String s2)
    {
        int thisMarker = 0;
        int thatMarker = 0;
        final int s1Length = s1.length();
        final int s2Length = s2.length();

        while ((thisMarker < s1Length) && (thatMarker < s2Length))
        {
            final String thisChunk = getChunk(s1, s1Length, thisMarker);
            thisMarker += thisChunk.length();

            final String thatChunk = getChunk(s2, s2Length, thatMarker);
            thatMarker += thatChunk.length();

            // If both chunks contain numeric characters, sort them numerically
            int result;
            if (isDigit(thisChunk.charAt(0)) && isDigit(thatChunk.charAt(0)))
            {
                // Simple chunk comparison by length.
                final int thisChunkLength = thisChunk.length();
                result = thisChunkLength - thatChunk.length();
                // If equal, the first different number counts
                if (result == 0)
                {
                    for (int i = 0; i < thisChunkLength; i++)
                    {
                        result = thisChunk.charAt(i) - thatChunk.charAt(i);
                        if (result != 0)
                        {
                            return result;
                        }
                    }
                }
            }
            else
            {
                result = thisChunk.compareTo(thatChunk);
            }

            if (result != 0)
            {
                return result;
            }
        }

        return s1Length - s2Length;
    }
}
