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
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.command.Command;
import org.diorite.command.SubCommand;

public class SubCommandImpl extends CommandImpl implements SubCommand
{
    private final CommandImpl parent;

    public SubCommandImpl(final String name, final Pattern pattern, final CommandImpl parent)
    {
        super(name, pattern);
        this.parent = parent;
    }

    public SubCommandImpl(final String name, final Collection<String> aliases, final CommandImpl parent)
    {
        super(name, aliases);
        this.parent = parent;
    }

    public SubCommandImpl(final String name, final CommandImpl parent)
    {
        super(name);
        this.parent = parent;
    }

    @Override
    public Command getParent()
    {
        return this.parent;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.parent.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof SubCommandImpl))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final SubCommandImpl that = (SubCommandImpl) o;

        return this.parent.equals(that.parent);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("parent", this.parent.getName()).toString();
    }
}
