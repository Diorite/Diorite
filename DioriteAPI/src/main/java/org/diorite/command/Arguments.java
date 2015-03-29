package org.diorite.command;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.entity.Player;

public class Arguments implements Iterable<String>
{
    private final String[] args;

    public Arguments(final String[] args)
    {
        this.args = args;
    }

    public String[] geRawtArgs()
    {
        return this.args;
    }

    public int length()
    {
        return this.args.length;
    }

    public boolean has(final int index)
    {
        return this.args.length > index;
    }

    public <T> T get(final int index, final Function<String, T> func)
    {
        return func.apply(this.asString(index));
    }

    public boolean contains(final String str)
    {
        for (final String arg : this.args)
        {
            if (arg.equals(str))
            {
                return true;
            }
        }
        return false;
    }

    public boolean containsIgnoreCase(final String str)
    {
        for (final String arg : this.args)
        {
            if (arg.equalsIgnoreCase(str))
            {
                return true;
            }
        }
        return false;
    }

    public Player asPlayer(final int index)
    {
        return Diorite.getServer().getPlayer(this.asString(index));
    }

    public String asString(final int index)
    {
        this.check(index);
        return this.args[index];
    }

    public Integer asInt(final int index)
    {
        this.check(index);
        try
        {
            return Integer.valueOf(this.args[index]);
        } catch (final NumberFormatException e)
        {
            return null;
        }
    }

    public Long asLong(final int index)
    {
        this.check(index);
        try
        {
            return Long.valueOf(this.args[index]);
        } catch (final NumberFormatException e)
        {
            return null;
        }
    }

    public Double asDouble(final int index)
    {
        this.check(index);
        try
        {
            return Double.valueOf(this.args[index]);
        } catch (final NumberFormatException e)
        {
            return null;
        }
    }

    public boolean asBoolean(final int index)
    {
        this.check(index);
        return Boolean.parseBoolean(this.args[index]);
    }

    public Boolean asBoolean(final int index, final Collection<String> trueWords, final Collection<String> falseWords)
    {
        final String str = this.asString(index);
        if (trueWords.parallelStream().anyMatch(s -> s.equalsIgnoreCase(str)))
        {
            return Boolean.TRUE;
        }
        if (falseWords.parallelStream().anyMatch(s -> s.equalsIgnoreCase(str)))
        {
            return Boolean.FALSE;
        }
        return null;
    }

    public String asText()
    {
        return StringUtils.join(this.args, ' ');
    }

    public String asText(final int fromIndex)
    {
        return StringUtils.join(this.args, ' ', fromIndex, this.args.length);
    }

    public String asText(final int fromIndex, final int toIndex)
    {
        return StringUtils.join(this.args, ' ', fromIndex, toIndex);
    }

    private void check(final int index)
    {
        if (this.args.length <= index)
        {
            throw new NoSuchElementException("Out of range, length: " + Arguments.this.args.length + ", index: " + index);
        }
    }

    public static Integer asInt(final String str)
    {
        try
        {
            return Integer.valueOf(str);
        } catch (final NumberFormatException e)
        {
            return null;
        }
    }

    public static Long asLong(final String str)
    {
        try
        {
            return Long.valueOf(str);
        } catch (final NumberFormatException e)
        {
            return null;
        }
    }

    public static Double asDouble(final String str)
    {
        try
        {
            return Double.valueOf(str);
        } catch (final NumberFormatException e)
        {
            return null;
        }
    }

    public static int asInt(final String str, final int def)
    {
        try
        {
            return Integer.parseInt(str);
        } catch (final NumberFormatException e)
        {
            return def;
        }
    }

    public static Long asLong(final String str, final long def)
    {
        try
        {
            return Long.parseLong(str);
        } catch (final NumberFormatException e)
        {
            return def;
        }
    }

    public static Double asDouble(final String str, final double def)
    {
        try
        {
            return Double.parseDouble(str);
        } catch (final NumberFormatException e)
        {
            return def;
        }
    }

    public static boolean asBoolean(final String str)
    {
        return Boolean.parseBoolean(str);
    }

    public static Boolean asBoolean(final String str, final Collection<String> trueWords, final Collection<String> falseWords)
    {
        if (trueWords.parallelStream().anyMatch(s -> s.equalsIgnoreCase(str)))
        {
            return Boolean.TRUE;
        }
        if (falseWords.parallelStream().anyMatch(s -> s.equalsIgnoreCase(str)))
        {
            return Boolean.FALSE;
        }
        return null;
    }

    public static String asText(final String[] strs)
    {
        if (strs == null)
        {
            return "";
        }
        return StringUtils.join(strs, ' ');
    }

    public static String asText(final String[] strs, final int fromIndex)
    {
        if (strs == null)
        {
            return "";
        }
        return StringUtils.join(strs, ' ', fromIndex, strs.length);
    }

    public static String asText(final String[] strs, final int fromIndex, final int toIndex)
    {
        if (strs == null)
        {
            return "";
        }
        return StringUtils.join(strs, ' ', fromIndex, toIndex);
    }

    @Override
    public Iterator<String> iterator()
    {
        return new Iterator<String>()
        {
            private int index = 0;

            @Override
            public boolean hasNext()
            {
                return this.index < Arguments.this.args.length;
            }

            @Override
            public String next()
            {
                if (Arguments.this.args.length <= this.index)
                {
                    throw new NoSuchElementException("Out of range, length: " + Arguments.this.args.length + ", index: " + this.index);
                }
                return Arguments.this.args[this.index++];
            }
        };
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("args", this.args).toString();
    }
}
