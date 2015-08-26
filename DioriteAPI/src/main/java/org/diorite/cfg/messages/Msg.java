package org.diorite.cfg.messages;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Msg
{


    public static String[] getPlaceholdersKeys(final String str)
    {
        final Collection<String> keys = new ArrayList<>(10);
        StringBuilder key = null;
        char lastChar = '\u0000';
        for (final char c : str.toCharArray())
        {
            if (key == null)
            {
                if (c == '$')
                {
                    if (lastChar != '\\')
                    {
                        lastChar = c;
                    }
                    continue;
                }
                if ((lastChar == '$') && (c == '<'))
                {
                    key = new StringBuilder(64);
                    lastChar = c;
                    continue;
                }
                lastChar = c;
                continue;
            }
            if (c == '>')
            {
                keys.add(key.toString());
                key = null;
            }
            else if (((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || (c == '_') || (c == '.'))
            {
                key.append(c);
            }
            else
            {
                key = null;
            }
            lastChar = c;
        }
        return keys.toArray(new String[keys.size()]);
    }
}
