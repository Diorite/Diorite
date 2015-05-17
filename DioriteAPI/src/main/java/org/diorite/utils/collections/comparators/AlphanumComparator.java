package org.diorite.utils.collections.comparators;


import java.io.Serializable;
import java.util.Comparator;

/**
 * Human-friendly string comparator,
 * instead of simple string comparator, this check numbers in strings.
 * <p>
 * Default comparator:
 * Some-1-String
 * Some-10-String
 * Some-100-String
 * Some-2-String
 * Some-5-String
 * Some-50-String
 * <p>
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
    private static final long serialVersionUID = - 6791054430669282740L;

    private static boolean isDigit(final char ch)
    {
        return (ch >= '0') && (ch <= '9');
    }

    /**
     * Length of string is passed in for improved efficiency (only need to calculate it once) *
     */
    private static String getChunk(final String s, final int slength, int marker)
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
            int result = 0;
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
