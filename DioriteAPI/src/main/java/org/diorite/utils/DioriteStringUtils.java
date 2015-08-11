package org.diorite.utils;

import java.util.ArrayList;
import java.util.List;

public final class DioriteStringUtils // other name to allow simple import StringUtils from other libs.
{
    public static final char NULL_CHAR   = '\0';
    @SuppressWarnings("HardcodedFileSeparator")
    public static final char ESCAPE_CHAR = '\\';
    public static final char SPACE       = ' ';
    public static final char QUOTE       = '"';
    public static final char APOSTROPHE  = '\'';

    private DioriteStringUtils()
    {
    }

    public static String[] splitArguments(final String str)
    {
        final char[] charArray = str.toCharArray();
        final List<String> args = new ArrayList<>(10);
        StringBuilder builder = new StringBuilder(32);
        boolean isBuildingString = false;
        char buildingChar = NULL_CHAR;
        for (int i = 0, charArrayLength = charArray.length; i < charArrayLength; i++)
        {
            final char c = charArray[i];
            switch (c)
            {
                case SPACE:
                    if (! isBuildingString && ((i == 0) || (charArray[i - 1] != ESCAPE_CHAR)))
                    {
                        args.add(builder.toString());
                        builder = new StringBuilder(32);
                        break;
                    }
                    builder.append(c);
                    break;
                case APOSTROPHE:
                case QUOTE:
                    if ((i == 0) || (charArray[i - 1] != ESCAPE_CHAR))
                    {
                        if (! isBuildingString)
                        {
                            isBuildingString = true;
                            buildingChar = c;
                            break;
                        }
                        if (c == buildingChar)
                        {
                            isBuildingString = false;
                            buildingChar = NULL_CHAR;
                            args.add(builder.toString());
                            builder = new StringBuilder(32);
                            while (++ i < charArrayLength)
                            {
                                if (charArray[i] == SPACE)
                                {
                                    break;
                                }
                            }
                            break;
                        }
                    }
                default:
                    builder.append(c);
                    break;
                case ESCAPE_CHAR:
                    break;
            }
        }
        if (builder.length() > 0)
        {
            args.add(builder.toString());
        }
        return args.toArray(new String[args.size()]);
    }
}
