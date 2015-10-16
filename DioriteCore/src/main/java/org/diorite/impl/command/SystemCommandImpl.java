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

package org.diorite.impl.command;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemCommandImpl extends MainCommandImpl
{
    private static final Pattern SYSTEM_CMD_PATTERN = Pattern.compile("diorite" + COMMAND_PLUGIN_SEPARATOR, Pattern.LITERAL);

    public SystemCommandImpl(final String name, final Collection<String> aliases, final byte priority)
    {
        super(name, aliases, priority);
    }

    public SystemCommandImpl(final String name, final Pattern pattern, final byte priority)
    {
        super(name, pattern, priority);
    }

    @Override
    public final String getFullName()
    {
        return "diorite" + COMMAND_PLUGIN_SEPARATOR + this.getName();
    }

    @Override
    public Matcher matcher(final String name)
    {
        if (name.toLowerCase().startsWith("diorite" + COMMAND_PLUGIN_SEPARATOR))
        {
            return super.matcher(SYSTEM_CMD_PATTERN.matcher(name).replaceAll(""));
        }
        else
        {
            return super.matcher(name);
        }
    }
}
