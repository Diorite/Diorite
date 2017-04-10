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

package org.diorite.command.parser.basic.tokens;

import javax.annotation.Nullable;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.command.annotation.arguments.BoolArg;

/**
 * Implementation of {@link BoolArg.Tokens}
 */
public final class BooleanTokens implements BoolArg.Tokens
{
    // TODO: load default true/false words from config file
    private static String[] defaultYes = {"on", "enable", "enabled", "yes", "allow", "allowed", "y", "true", "tak", "t", "wlaczony", "prawda", "p", "1"};
    private static String[] defaultNo  = {"off", "disable", "disabled", "no", "disallow", "disallowed", "n", "false", "f", "nie", "wylaczony", "falsz", "0"};

    // TODO: load default replacments from config file
    private static String defaultReplacesFrom = new String(new char[]{'ę', 'ó', 'ą', 'ś', 'ł', 'ż', 'ź', 'ć', 'ń'});
    private static String defaultReplacesTo   = new String(new char[]{'e', 'o', 'a', 's', 'l', 'z', 'z', 'c', 'n'});

    static
    {
        Arrays.sort(defaultYes);
        Arrays.sort(defaultNo);
    }

    public static void setDefaultYes(Collection<String> defaultYes)
    {
        String[] strings = new String[defaultYes.size()];
        defaultYes.toArray(strings);
        Arrays.sort(strings);
        BooleanTokens.defaultYes = strings;
    }

    public static void setDefaultNo(Collection<String> defaultNo)
    {
        String[] strings = new String[defaultNo.size()];
        defaultNo.toArray(strings);
        Arrays.sort(strings);
        BooleanTokens.defaultNo = strings;
    }

    /**
     * Parse given string to boolean using default and given token values.
     *
     * @param str
     *         string to parse.
     * @param tokens
     *         optional additional tokens.
     *
     * @return parsed string or null if it does not match any value.
     */
    @Nullable
    public static Boolean parse(String str, @Nullable BoolArg.Tokens tokens)
    {
        str = str.toLowerCase();
        if (tokens == null)
        {
            str = StringUtils.replaceChars(str, defaultReplacesFrom, defaultReplacesTo);
            if (Arrays.binarySearch(defaultYes, str) >= 0)
            {
                return true;
            }
            if (Arrays.binarySearch(defaultNo, str) >= 0)
            {
                return false;
            }
            return null;
        }
        char[] replaces = tokens.replaces();
        if (replaces.length > 0)
        {
            StringBuilder from = new StringBuilder(replaces.length / 2);
            StringBuilder to = new StringBuilder(replaces.length / 2);
            for (int i = 0; i < replaces.length; i += 2)
            {
                from.append(replaces[i]);
                to.append(replaces[i + 1]);
            }
            str = StringUtils.replaceChars(str, from.toString(), to.toString());
        }
        if (tokens.addToDefaults())
        {
            str = StringUtils.replaceChars(str, defaultReplacesFrom, defaultReplacesTo);
            if (Arrays.binarySearch(defaultYes, str) >= 0)
            {
                return true;
            }
            if (Arrays.binarySearch(defaultNo, str) >= 0)
            {
                return false;
            }
        }
        if (ArrayUtils.contains(tokens.yesWords(), str))
        {
            return true;
        }
        if (ArrayUtils.contains(tokens.noWords(), str))
        {
            return false;
        }
        return null;
    }

    private final boolean  addToDefaults;
    private final String[] yesWords;
    private final String[] noWords;
    private final char[]   replaces;

    public BooleanTokens(boolean addToDefaults, String[] yesWords, String[] noWords, char[] replaces)
    {
        this.addToDefaults = addToDefaults;
        this.yesWords = yesWords;
        this.noWords = noWords;
        this.replaces = replaces;
    }

    @Override
    public boolean addToDefaults()
    {
        return this.addToDefaults;
    }

    @Override
    public String[] yesWords()
    {
        return this.yesWords;
    }

    @Override
    public String[] noWords()
    {
        return this.noWords;
    }

    @Override
    public char[] replaces()
    {
        return this.replaces;
    }

    @Override
    public boolean equals(@Nullable Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof BooleanTokens))
        {
            return false;
        }
        BooleanTokens that = (BooleanTokens) o;
        return new EqualsBuilder()
                       .append(this.addToDefaults, that.addToDefaults)
                       .append(this.yesWords, that.yesWords)
                       .append(this.noWords, that.noWords)
                       .append(this.replaces, that.replaces)
                       .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                       .append(this.addToDefaults)
                       .append(this.yesWords)
                       .append(this.noWords)
                       .append(this.replaces)
                       .toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("addToDefaults", this.addToDefaults).append("yesWords", this.yesWords)
                                        .append("noWords", this.noWords).append("replaces", this.replaces).toString();
    }

    @Override
    public Class<BoolArg.Tokens> annotationType()
    {
        return BoolArg.Tokens.class;
    }

}
