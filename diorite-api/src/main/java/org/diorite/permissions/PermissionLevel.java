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

package org.diorite.permissions;

import java.util.regex.Pattern;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

public enum PermissionLevel
{
    TRUE("true")
            {
                @Override
                public boolean getValue(final boolean op)
                {
                    return true;
                }
            },
    FALSE("false")
            {
                @Override
                public boolean getValue(final boolean op)
                {
                    return false;
                }
            },
    OP("op", "isop", "operator", "isoperator", "admin", "isadmin")
            {
                @Override
                public boolean getValue(final boolean op)
                {
                    return op;
                }
            },
    NOT_OP("!op", "notop", "!operator", "notoperator", "!admin", "notadmin")
            {
                @Override
                public boolean getValue(final boolean op)
                {
                    return !op;
                }
            };

    private static final Pattern INVALID_CHARS = Pattern.compile("[^a-zA-Z!]");
    private final String[] names;
    private static final CaseInsensitiveMap<PermissionLevel> lookup = new CaseInsensitiveMap<>(15, .1f);

    PermissionLevel(final String... names)
    {
        this.names = names;
    }

    public abstract boolean getValue(boolean op);

    public static PermissionLevel getByName(final CharSequence name)
    {
        return lookup.get(INVALID_CHARS.matcher(name).replaceAll(""));
    }

    public String toString()
    {
        return this.names[0];
    }

    static
    {
        for (final PermissionLevel value : values())
        {
            for (final String name : value.names)
            {
                lookup.put(name, value);
            }
        }
    }
}
