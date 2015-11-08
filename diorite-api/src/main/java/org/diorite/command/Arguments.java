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

package org.diorite.command;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockLocation;
import org.diorite.Diorite;
import org.diorite.ImmutableLocation;
import org.diorite.Loc;
import org.diorite.command.exceptions.InvalidCommandArgumentException;
import org.diorite.entity.Entity;
import org.diorite.entity.Player;
import org.diorite.utils.SimpleEnum;
import org.diorite.utils.math.DioriteMathUtils;
import org.diorite.utils.reflections.DioriteReflectionUtils;

/**
 * Class used to read command parametrs instead of String[] to make some stuff easier/simpler
 */
public class Arguments implements Iterable<String>
{
    private final String[] args;

    /**
     * Construct new arguments wrapper.
     * WARN: it don't make copy/clone of given array!
     *
     * @param args string array to wrap.
     */
    public Arguments(final String[] args)
    {
        this.args = args;
    }

    /**
     * @return raw, editable, wrapped String[]
     */
    public String[] getRawArgs()
    {
        return this.args;
    }

    /**
     * @return length of wrapped string array. returns args.length
     */
    public int length()
    {
        return this.args.length;
    }

    /**
     * Check if array length is {@code >} than selected index.
     * Remember that elements are counted from 0, not 1.
     *
     * @param index index of element, 0 is first element.
     *
     * @return {@code eargs.length > index}
     */
    public boolean has(final int index)
    {
        return this.args.length > index;
    }

    /**
     * Get argument of any type using given function{@literal <String, T>}.
     *
     * @param index index of element, 0 is first element.
     * @param func  function used to change string to any type.
     * @param <T>   Type of returned
     *
     * @return selected element after converting to given type.
     *
     * @throws NoSuchElementException if {@code args.length > index}
     */
    public <T> T get(final int index, final Function<String, T> func) throws NoSuchElementException
    {
        return func.apply(this.asString(index));
    }

    /**
     * Check if any argument is equals to given string.
     *
     * @param str string to find.
     *
     * @return true if array contains given string.
     *
     * @throws NoSuchElementException if {@code args.length > index}
     */
    public boolean contains(final String str) throws NoSuchElementException
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

    /**
     * Check if any argument is equals with ignore case to given string.
     *
     * @param str string to find.
     *
     * @return true if array contains given string.
     *
     * @throws NoSuchElementException if {@code args.length > index}
     */
    public boolean containsIgnoreCase(final String str) throws NoSuchElementException
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

    /**
     * Get value from enum for given argument,
     * it will try match it as name or ordinal id.
     * Name is ignore-case.
     *
     * @param enumClass class of enum.
     * @param index     index of element, 0 is first element.
     * @param <T>       type of enum.
     *
     * @return enum element or null.
     */
    public <T extends SimpleEnum<T>> T asSimpleEnumValue(final Class<T> enumClass, final int index)
    {
        final String name = this.asString(index);
        final int num = asInt(name, - 1);
        return DioriteReflectionUtils.getSimpleEnumValueSafe(name, num, enumClass);
    }

    /**
     * Get value from enum for given argument,
     * it will try match it as name or ordinal id.
     * Name is ignore-case.
     *
     * @param index index of element, 0 is first element.
     * @param def   default value, can't be null.
     * @param <T>   type of enum.
     *
     * @return enum element or default.
     */
    public <T extends SimpleEnum<T>> T asSimpleEnumValue(final int index, final T def)
    {
        final String name = this.asString(index);
        final int num = asInt(name, - 1);
        return DioriteReflectionUtils.getSimpleEnumValueSafe(name, num, def);
    }

    /**
     * Get value from enum for given argument,
     * it will try match it as name or ordinal id.
     * Name is ignore-case.
     *
     * @param enumClass class of enum.
     * @param index     index of element, 0 is first element.
     * @param <T>       type of enum.
     *
     * @return enum element or null.
     */
    public <T extends Enum<T>> T asEnumValue(final Class<T> enumClass, final int index)
    {
        final String name = this.asString(index);
        final int num = asInt(name, - 1);
        return DioriteReflectionUtils.getEnumValueSafe(name, num, enumClass);
    }

    /**
     * Get value from enum for given argument,
     * it will try match it as name or ordinal id.
     * Name is ignore-case.
     *
     * @param index index of element, 0 is first element.
     * @param def   default value, can't be null.
     * @param <T>   type of enum.
     *
     * @return enum element or default.
     */
    public <T extends Enum<T>> T asEnumValue(final int index, final T def)
    {
        final String name = this.asString(index);
        final int num = asInt(name, - 1);
        return DioriteReflectionUtils.getEnumValueSafe(name, num, def);
    }

    /**
     * Get selected argument as {@link Player}, may return null if player is offline.
     *
     * @param index index of element, 0 is first element.
     *
     * @return {@link Player} or null if player is offline.
     *
     * @throws NoSuchElementException if {@code args.length > index}
     */
    public Player asPlayer(final int index) throws NoSuchElementException
    {
        return Diorite.getCore().getPlayer(this.asString(index));
    } // TODO: add asOfflinePlayer

    /**
     * Read x, y, z block coordinates from next 3 elements.
     * If argument starts from <B>~</B> then returned coordinates are relative to given origin.
     *
     * @param startIndex index of first coordinate, 0 is first element.
     * @param origin     used to get relative coordinates, if null {@link BlockLocation#ZERO} is used.
     *
     * @return x, y, z block coordinates as {@link BlockLocation}
     *
     * @throws InvalidCommandArgumentException if any of numbers can't be parsed to coordinate.
     * @throws NoSuchElementException          if {@code args.length > startIndex + 2}
     */
    public BlockLocation readCoordinates(final int startIndex, BlockLocation origin) throws NoSuchElementException, InvalidCommandArgumentException
    {
        this.check(startIndex + 2);
        if (origin == null)
        {
            origin = BlockLocation.ZERO;
        }
        return new BlockLocation(this.readCoordinate(startIndex, origin.getX()), this.readCoordinate(startIndex + 1, origin.getY()), this.readCoordinate(startIndex + 2, origin.getZ()), origin.getWorld());
    }

    /**
     * Read x, y, z (and yaw, pitch if needed) coordinates.
     * If argument starts from <B>~</B> then returned coordinates are relative to given entity.
     *
     * @param startIndex   index of first coordinate, 0 is first element.
     * @param withRotation if yaw and pitch should be also read.
     * @param entity       entity to get origin location used to get relative coordinates.
     *
     * @return x, y, z, yaw, pitch as {@link ImmutableLocation}
     *
     * @throws InvalidCommandArgumentException if any of numbers can't be parsed to coordinate.
     * @throws NoSuchElementException          if {@code args.length > startIndex + 2} or + 4 if withRotation is true.
     */
    public ImmutableLocation readCoordinates(final int startIndex, final boolean withRotation, final Entity entity) throws NoSuchElementException, InvalidCommandArgumentException
    {
        return this.readCoordinates(startIndex, withRotation, entity.getLocation());
    }

    /**
     * Read x, y, z (and yaw, pitch if needed) coordinates.
     * If argument starts from <B>~</B> then returned coordinates are relative to given location.
     *
     * @param startIndex   index of first coordinate, 0 is first element.
     * @param withRotation if yaw and pitch should be also read.
     * @param origin       used to get relative coordinates, if null {@link ImmutableLocation#ZERO} is used.
     *
     * @return x, y, z, yaw, pitch as {@link ImmutableLocation}
     *
     * @throws InvalidCommandArgumentException if any of numbers can't be parsed to coordinate.
     * @throws NoSuchElementException          if {@code args.length > startIndex + 2} or + 4 if withRotation is true.
     */
    public ImmutableLocation readCoordinates(final int startIndex, final boolean withRotation, Loc origin) throws NoSuchElementException, InvalidCommandArgumentException
    {
        this.check(startIndex + (withRotation ? 4 : 2));
        if (origin == null)
        {
            origin = ImmutableLocation.ZERO;
        }
        final double x = this.readCoordinate(startIndex, origin.getX());
        final double y = this.readCoordinate(startIndex + 1, origin.getY());
        final double z = this.readCoordinate(startIndex + 2, origin.getZ());
        if (! withRotation)
        {
            return new ImmutableLocation(x, y, z, origin.getYaw(), origin.getPitch(), origin.getWorld());
        }
        return new ImmutableLocation(x, y, z, this.readRotation(startIndex + 3, origin.getYaw()), this.readRotation(startIndex + 4, origin.getPitch()), origin.getWorld());
    }

    /**
     * Read single coordinate (x, y or z).
     * If argument starts from <B>~</B> then returned coordinate is relative to given origin.
     * {@literal 4   -> 4}
     * {@literal ~   -> origin}
     * {@literal ~3  -> origin + 3}
     * {@literal ~-3 -> origin - 3}
     *
     * @param index  index of element, 0 is first element.
     * @param origin used to get relative coordinates.
     *
     * @return coordinate.
     *
     * @throws InvalidCommandArgumentException if number can't be parsed to coordinate.
     * @throws NoSuchElementException          if {@code args.length > index}
     */
    public double readCoordinate(final int index, final double origin) throws NoSuchElementException, InvalidCommandArgumentException
    {
        this.check(index);
        final String str = this.args[index];
        if (str.charAt(0) == '~')
        {
            if (str.length() == 1)
            {
                return origin;
            }
            final Double d = asDouble(str.substring(1));
            if (d == null)
            {
                throw new InvalidCommandArgumentException("can't parse coordinate from: " + str);
            }
            return origin + d;
        }
        final Double d = asDouble(str);
        if (d == null)
        {
            throw new InvalidCommandArgumentException("can't parse coordinate from: " + str);
        }
        return d;
    }

    /**
     * Read single rotation (yaw or pitch).
     * If argument starts from <B>~</B> then returned rotation is relative to given origin.
     * {@literal 0.4   -> 0.4}
     * {@literal ~     -> origin}
     * {@literal ~0.3  -> origin + 0.3}
     * {@literal ~-0.3 -> origin - 0.3}
     *
     * @param index  index of element, 0 is first element.
     * @param origin used to get relative rotation.
     *
     * @return rotation.
     *
     * @throws InvalidCommandArgumentException if number can't be parsed to rotation.
     * @throws NoSuchElementException          if {@code args.length > index}
     */
    public float readRotation(final int index, final float origin) throws NoSuchElementException, InvalidCommandArgumentException
    {
        this.check(index);
        final String str = this.args[index];
        if (str.charAt(0) == '~')
        {
            if (str.length() == 1)
            {
                return origin;
            }
            final Float f = asFloat(str.substring(1));
            if (f == null)
            {
                throw new InvalidCommandArgumentException("can't parse rotation from: " + str);
            }
            return origin + f;
        }
        final Float f = asFloat(str);
        if (f == null)
        {
            throw new InvalidCommandArgumentException("can't parse rotation from: " + str);
        }
        return f;
    }

    /**
     * Read single coordinate (x, y or z).
     * If argument starts from <B>~</B> then returned coordinate is relative to given origin.
     * {@literal 4   -> 4}
     * {@literal ~   -> origin}
     * {@literal ~3  -> origin + 3}
     * {@literal ~-3 -> origin - 3}
     *
     * @param index  index of element, 0 is first element.
     * @param origin used to get relative coordinates.
     *
     * @return coordinate.
     *
     * @throws InvalidCommandArgumentException if number can't be parsed to coordinate.
     * @throws NoSuchElementException          if {@code args.length > index}
     */
    public int readCoordinate(final int index, final int origin) throws NoSuchElementException, InvalidCommandArgumentException
    {
        this.check(index);
        final String str = this.args[index];
        if (str.charAt(0) == '~')
        {
            if (str.length() == 1)
            {
                return origin;
            }
            final Integer i = asInt(str.substring(1));
            if (i == null)
            {
                throw new InvalidCommandArgumentException("can't parse coordinate from: " + str);
            }
            return origin + i;
        }
        final Integer i = asInt(str);
        if (i == null)
        {
            throw new InvalidCommandArgumentException("can't parse coordinate from: " + str);
        }
        return i;
    }

    /**
     * Return raw value from wrapped array of strings.
     *
     * @param index index of element, 0 is first element.
     *
     * @return raw value from wrapped array of strings.
     *
     * @throws NoSuchElementException if {@code args.length > index}
     */
    public String asString(final int index) throws NoSuchElementException
    {
        this.check(index);
        return this.args[index];
    }

    /**
     * Parse selected argument to int, if argument can't be parsed to int, then it will return null.
     *
     * @param index index of element, 0 is first element.
     *
     * @return parsed value or null.
     *
     * @throws NoSuchElementException if {@code args.length > index}
     */
    public Integer asInt(final int index) throws NoSuchElementException
    {
        this.check(index);
        return asInt(this.args[index]);
    }

    /**
     * Parse selected argument to long, if argument can't be parsed to long, then it will return null.
     *
     * @param index index of element, 0 is first element.
     *
     * @return parsed value or null.
     *
     * @throws NoSuchElementException if {@code args.length > index}
     */
    public Long asLong(final int index) throws NoSuchElementException
    {
        this.check(index);
        return asLong(this.args[index]);
    }

    /**
     * Parse selected argument to double, if argument can't be parsed to double, then it will return null.
     *
     * @param index index of element, 0 is first element.
     *
     * @return parsed value or null.
     *
     * @throws NoSuchElementException if {@code args.length > index}
     */
    public Double asDouble(final int index) throws NoSuchElementException
    {
        this.check(index);
        return asDouble(this.args[index]);
    }

    /**
     * Parse selected argument to float, if argument can't be parsed to float, then it will return null.
     *
     * @param index index of element, 0 is first element.
     *
     * @return parsed value or null.
     *
     * @throws NoSuchElementException if {@code args.length > index}
     */
    public Float asFloat(final int index) throws NoSuchElementException
    {
        this.check(index);
        return asFloat(this.args[index]);
    }

    /**
     * Simple parse selected argument to boolean.
     *
     * @param index index of element, 0 is first element.
     *
     * @return parsed value/
     *
     * @throws NoSuchElementException if {@code args.length > index}
     * @see Boolean#parseBoolean(String)
     */
    public boolean asBoolean(final int index) throws NoSuchElementException
    {
        this.check(index);
        return Boolean.parseBoolean(this.args[index]);
    }

    /**
     * Parse element to boolean using two collections of words, for true and false values.
     * If any of trueWords is equals (equalsIgnoreCase) to given element, then method returns ture.
     * If any of falseWords is equals (equalsIgnoreCase) to given element, then method returns false.
     * If given word don't match any words from collections, then method returns null
     *
     * @param index      index of element, 0 is first element.
     * @param trueWords  words that mean "true"
     * @param falseWords words that mean "false"
     *
     * @return true/false or null.
     *
     * @throws NoSuchElementException if {@code args.length > index}
     */
    public Boolean asBoolean(final int index, final Collection<String> trueWords, final Collection<String> falseWords) throws NoSuchElementException
    {
        final String str = this.asString(index);
        if (trueWords.stream().anyMatch(s -> s.equalsIgnoreCase(str)))
        {
            return Boolean.TRUE;
        }
        if (falseWords.stream().anyMatch(s -> s.equalsIgnoreCase(str)))
        {
            return Boolean.FALSE;
        }
        return null;
    }

    /**
     * @return all arguments as single string.
     *
     * @see StringUtils#join(Object[], char)
     */
    public String asText()
    {
        return StringUtils.join(this.args, ' ');
    }

    /**
     * Join all arguments starting from selected index.
     *
     * @param fromIndex index of first element.
     *
     * @return arguments as single string.
     *
     * @see StringUtils#join(Object[], char, int, int)
     */
    public String asText(final int fromIndex)
    {
        return StringUtils.join(this.args, ' ', fromIndex, this.args.length);
    }

    /**
     * Join arguments starting from selected index to other selected index.
     *
     * @param fromIndex index of first element.
     * @param toIndex   index of last element.
     *
     * @return arguments as single string.
     *
     * @throws NoSuchElementException if {@code args.length > index}
     * @see StringUtils#join(Object[], char, int, int)
     */
    public String asText(final int fromIndex, final int toIndex) throws NoSuchElementException
    {
        return StringUtils.join(this.args, ' ', fromIndex, toIndex);
    }

    private void check(final int index) throws NoSuchElementException
    {
        if (this.args.length <= index)
        {
            throw new NoSuchElementException("Out of range, length: " + Arguments.this.args.length + ", index: " + index);
        }
    }

    /**
     * Parse string to int, if string can't be parsed to int, then it will return null.
     *
     * @param str string to parse
     *
     * @return parsed value or null.
     *
     * @see DioriteMathUtils#asInt(String)
     */
    public static Integer asInt(final String str)
    {
        return DioriteMathUtils.asInt(str);
    }

    /**
     * Parse string to long, if string can't be parsed to long, then it will return null.
     *
     * @param str string to parse
     *
     * @return parsed value or null.
     *
     * @see DioriteMathUtils#asLong(String)
     */
    public static Long asLong(final String str)
    {
        return DioriteMathUtils.asLong(str);
    }

    /**
     * Parse string to double, if string can't be parsed to double, then it will return null.
     *
     * @param str string to parse
     *
     * @return parsed value or null.
     *
     * @see DioriteMathUtils#asDouble(String)
     */
    public static Double asDouble(final String str)
    {
        return DioriteMathUtils.asDouble(str);
    }

    /**
     * Parse string to float, if string can't be parsed to float, then it will return null.
     *
     * @param str string to parse
     *
     * @return parsed value or null.
     *
     * @see DioriteMathUtils#asFloat(String)
     */
    public static Float asFloat(final String str)
    {
        return DioriteMathUtils.asFloat(str);
    }

    /**
     * Parse string to int, if string can't be parsed to int, then it will return given default value.
     *
     * @param str string to parse
     * @param def default value.
     *
     * @return parsed value or default value.
     *
     * @see DioriteMathUtils#asInt(String, int)
     */
    public static int asInt(final String str, final int def)
    {
        return DioriteMathUtils.asInt(str, def);
    }

    /**
     * Parse string to long, if string can't be parsed to long, then it will return given default value.
     *
     * @param str string to parse
     * @param def default value.
     *
     * @return parsed value or default value.
     *
     * @see DioriteMathUtils#asLong(String, long)
     */
    public static Long asLong(final String str, final long def)
    {
        return DioriteMathUtils.asLong(str, def);
    }

    /**
     * Parse string to double, if string can't be parsed to double, then it will return given default value.
     *
     * @param str string to parse
     * @param def default value.
     *
     * @return parsed value or default value.
     *
     * @see DioriteMathUtils#asDouble(String, double)
     */
    public static Double asDouble(final String str, final double def)
    {
        return DioriteMathUtils.asDouble(str, def);
    }

    /**
     * Parse string to float, if string can't be parsed to float, then it will return given default value.
     *
     * @param str string to parse
     * @param def default value.
     *
     * @return parsed value or default value.
     *
     * @see DioriteMathUtils#asFloat(String, float)
     */
    public static Float asFloat(final String str, final float def)
    {
        return DioriteMathUtils.asFloat(str, def);
    }

    /**
     * Simple parse boolean.
     *
     * @param str string to parse
     *
     * @return parsed value
     *
     * @see Boolean#parseBoolean(String)
     * @see DioriteMathUtils#asBoolean(String)
     */
    public static boolean asBoolean(final String str)
    {
        return DioriteMathUtils.asBoolean(str);
    }

    /**
     * Parse string to boolean using two collections of words, for true and false values.
     * If any of trueWords is equals (equalsIgnoreCase) to given string, then method returns ture.
     * If any of falseWords is equals (equalsIgnoreCase) to given string, then method returns false.
     * If given word don't match any words from collections, then method returns null
     *
     * @param str        string to parse.
     * @param trueWords  words that mean "true"
     * @param falseWords words that mean "false"
     *
     * @return true/false or null.
     *
     * @see DioriteMathUtils#asBoolean(String, Collection, Collection)
     */
    public static Boolean asBoolean(final String str, final Collection<String> trueWords, final Collection<String> falseWords)
    {
        return DioriteMathUtils.asBoolean(str, trueWords, falseWords);
    }

    /**
     * Join all strings from array to one single string,
     * separate elements by single space.
     *
     * @param strs string to join.
     *
     * @return joined strings, separated by single space.
     */
    public static String asText(final String[] strs)
    {
        if (strs == null)
        {
            return "";
        }
        return StringUtils.join(strs, ' ');
    }

    /**
     * Join all strings from array starting from given index
     * to one single string, separate elements by single space.
     *
     * @param strs      string to join.
     * @param fromIndex starting index.
     *
     * @return joined strings, separated by single space.
     */
    public static String asText(final String[] strs, final int fromIndex)
    {
        if (strs == null)
        {
            return "";
        }
        return StringUtils.join(strs, ' ', fromIndex, strs.length);
    }

    /**
     * Join all strings from array starting from given index to other given index,
     * to one single string, separate elements by single space.
     *
     * @param strs      string to join.
     * @param fromIndex starting index.
     * @param toIndex   end index.
     *
     * @return joined strings, separated by single space.
     */
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
