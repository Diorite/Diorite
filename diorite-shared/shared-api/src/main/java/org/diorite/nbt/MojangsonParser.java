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

package org.diorite.nbt;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import org.diorite.command.parser.ArgumentParseResult;
import org.diorite.command.parser.basic.RawStringParser;
import org.diorite.command.parser.basic.StringParser;
import org.diorite.command.parser.basic.tokens.StringTokens;
import org.diorite.commons.ParserContext;

final class MojangsonParser
{
    private static final int MAX_INDEX_ARRAY = 256;

    private static final StringParser keyParser = new StringParser(new StringTokens(new char[]{'"'}, new char[]{'"'}));

    private MojangsonParser() {}

    /**
     * Reads nbt from mojangson format.
     *
     * @param str
     *         string with mojangson.
     * @param skipWhitespaces
     *         if parser can skip whitespaces.
     *
     * @return parsed nbt.
     */
    public static NbtTag fromMojangson(String str, boolean skipWhitespaces)
    {
        return fromMojangson(new ParserContext(str), skipWhitespaces);
    }

    /**
     * Reads nbt from mojangson format.
     *
     * @param context
     *         parser context instance.
     * @param skipWhitespaces
     *         if parser can skip whitespaces.
     *
     * @return parsed nbt.
     */
    public static NbtTag fromMojangson(ParserContext context, boolean skipWhitespaces)
    {
        if (skipWhitespaces)
        {
            context.skipWhitespaces();
        }
        if (! context.hasNext())
        {
            throw new MojangsonParserException("Can't parse mojangson, expected more input", context.getIndex(), context.getText());
        }
        char first = context.next();
        if (first == '{')
        {
            return compoundFromMojangson(null, context, skipWhitespaces);
        }
        if (first == '[')
        {
            return listFromMojangson(null, context, skipWhitespaces);
        }
        throw new MojangsonParserException("Can't parse mojangson, expected `{` or `[` as first character, but: `" + first + "` found.");
    }

    static NbtTag listFromMojangson(@Nullable String name, ParserContext context, boolean skipWhitespaces)
    {
        NbtTagList result = new NbtTagList(name);
        NbtTagType type = null;
        while (true)
        {
            if (! context.hasNext())
            {
                throw new MojangsonParserException("Can't parse mojangson, expected more input", context.getIndex(), context.getText());
            }
            int contextIndex = context.getIndex();
            try
            {
                if (skipWhitespaces)
                {
                    context.skipWhitespaces();
                }
                char c = context.next();
                if (c == ',')
                {
                    if (skipWhitespaces)
                    {
                        context.skipWhitespaces();
                    }
                    c = context.next();
                    if (c == ']')
                    {
                        return new NbtTagIntArray();
                    }
                }
                else if (c == '{')
                {
                    if ((type != null) && (type != NbtTagType.COMPOUND))
                    {
                        throw new MojangsonParserException("Can't parse list element, expected: " + type + " but " + NbtTagType.COMPOUND + " found.",
                                                           context.getIndex(), context.getText());
                    }
                    type = NbtTagType.COMPOUND;
                    NbtTagCompound compound = compoundFromMojangson(null, context, skipWhitespaces);
                    result.add(compound);
                }
                else if (c == '[')
                {
                    if ((type != null) && (type != NbtTagType.LIST))
                    {
                        throw new MojangsonParserException("Can't parse list element, expected: " + type + " but " + NbtTagType.LIST + " found.",
                                                           context.getIndex(), context.getText());
                    }
                    type = NbtTagType.LIST;
                    NbtTag list = listFromMojangson(null, context, skipWhitespaces);
                    result.add(list);
                }
                else
                {
                    context.previous();
                    int index = 0;
                    int preIndex = context.getIndex();
                    StringBuilder digitBuilder = new StringBuilder(6);
                    while (true)
                    {
                        char digit = context.next();
                        if ((digit >= '0') && (digit <= '9'))
                        {
                            digitBuilder.append(digit);
                            continue;
                        }
                        if (digit == ':')
                        {
                            index = Integer.parseInt(digitBuilder.toString());
                            break;
                        }
                        context.setIndex(preIndex);
                        break;
                    }
                    if (index > MAX_INDEX_ARRAY)
                    {
                        throw new MojangsonParserException("Can't parse nbt value from mojangson, too high index: " + index, contextIndex, context.getText());
                    }
                    if (skipWhitespaces)
                    {
                        context.skipWhitespaces();
                    }
                    char first = context.next();
                    NbtTag nbtTag;
                    if (first == '{')
                    {
                        if ((type != null) && (type != NbtTagType.COMPOUND))
                        {
                            throw new MojangsonParserException("Can't parse list element, expected: " + type + " but " + NbtTagType.COMPOUND + " found.",
                                                               context.getIndex(), context.getText());
                        }
                        nbtTag = compoundFromMojangson(null, context, skipWhitespaces);
                    }
                    else if (first == '[')
                    {
                        if ((type != null) && (type != NbtTagType.LIST))
                        {
                            throw new MojangsonParserException("Can't parse list element, expected: " + type + " but " + NbtTagType.LIST + " found.",
                                                               context.getIndex(), context.getText());
                        }
                        nbtTag = listFromMojangson(null, context, skipWhitespaces);
                    }
                    else
                    {
                        nbtTag = tryParsePrimitive(null, context);
                    }
                    if (nbtTag == null)
                    {
                        throw new MojangsonParserException("Can't parse nbt value from mojangson.", contextIndex, context.getText());
                    }
                    type = nbtTag.getTagType();
                    while (index >= result.size())
                    {
                        result.add(null);
                    }
                    result.set(index, nbtTag);
                }
                if (type == null)
                {
                    throw new MojangsonParserException("Can't parse nbt value from mojangson.", contextIndex, context.getText());
                }
                if (skipWhitespaces)
                {
                    context.skipWhitespaces();
                }
                char next = context.next();
                if (next == ',')
                {
                    if (skipWhitespaces)
                    {
                        context.skipWhitespaces();
                    }
                    next = context.next();
                    if (next == ']')
                    {
                        break;
                    }
                    context.previous();
                    continue;
                }
                if (next == ']')
                {
                    break;
                }
            }
            catch (MojangsonParserException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                throw new MojangsonParserException("Can't parse nbt value from mojangson.", contextIndex, context.getText(), e);
            }
        }
        if (type == NbtTagType.INTEGER)
        {
            int[] resultArray = new int[result.size()];
            for (int i = 0; i < resultArray.length; i++)
            {
                NbtTag nbtTag = result.get(i);
                if (nbtTag == null)
                {
                    continue;
                }
                resultArray[i] = ((NbtTagInt) nbtTag).getValue();
            }
            return new NbtTagIntArray(null, resultArray);
        }
        return result;
    }

    static NbtTagCompound compoundFromMojangson(@Nullable String name, ParserContext context, boolean skipWhitespaces)
    {
        NbtTagCompound result = new NbtTagCompound(name);
        while (true)
        {
            if (! context.hasNext())
            {
                throw new MojangsonParserException("Can't parse mojangson, expected more input", context.getIndex(), context.getText());
            }
            String key = keyFromMojangson(context, skipWhitespaces);
            if (skipWhitespaces)
            {
                context.skipWhitespaces();
            }
            NbtTag nbtTag = valueFromMojangson(key, context, skipWhitespaces);
            result.addTag(nbtTag);
            char next = context.next();
            if (next == ',')
            {
                if (skipWhitespaces)
                {
                    context.skipWhitespaces();
                }
                next = context.next();
                if (next == '}')
                {
                    return result;
                }
                context.previous();
                continue;
            }
            if (next == '}')
            {
                return result;
            }
        }
    }

    @Nullable
    private static NbtTag tryParsePrimitive(@Nullable String key, ParserContext context)
    {
        if (! context.hasNext())
        {
            throw new MojangsonParserException("Can't parse mojangson, expected more input", context.getIndex(), context.getText());
        }
        int contextIndex = context.getIndex();
        NbtTag resultTag = null;
        char first = context.next();
        context.previous();
        String result = null;
        try
        {
            if (((first >= '0') && (first <= '9')) || (first == '.'))
            {
                ArgumentParseResult<? extends String> parse = RawStringParser.INSTANCE.checkAndParse(context, c -> (c == ' ') || (c == ',') || (c == '}'));
                result = parse.getResult();
                if (! parse.isSuccess() || (result == null))
                {
                    Exception exception = parse.getException();
                    if (exception != null)
                    {
                        throw new MojangsonParserException("Can't parse nbt value from mojangson.", contextIndex, context.getText(), exception);
                    }
                    throw new MojangsonParserException("Can't parse nbt value from mojangson.", contextIndex, context.getText());
                }
                if (result.isEmpty())
                {
                    resultTag = new NbtTagString(key, removeQuotes(result));
                }
                else
                {
                    char lastChar = Character.toUpperCase(result.charAt(result.length() - 1));
                    if (lastChar == 'B')
                    {
                        byte num = Byte.parseByte(result.substring(0, result.length() - 1));
                        resultTag = new NbtTagByte(key, num);
                    }
                    else if (lastChar == 'S')
                    {
                        short num = Short.parseShort(result.substring(0, result.length() - 1));
                        resultTag = new NbtTagShort(key, num);
                    }
                    else if (lastChar == 'L')
                    {
                        long num = Long.parseLong(result.substring(0, result.length() - 1));
                        resultTag = new NbtTagLong(key, num);
                    }
                    else if (lastChar == 'F')
                    {
                        float num = Float.parseFloat(result.substring(0, result.length() - 1));
                        if (Float.isNaN(num) || Float.isInfinite(num))
                        {
                            throw new MojangsonParserException("Can't parse nbt value from mojangson, found invalid number: " + num,
                                                               contextIndex, context.getText());
                        }
                        resultTag = new NbtTagFloat(key, num);
                    }
                    else if ((lastChar == 'D') || StringUtils.contains(result, '.'))
                    {
                        double num = (lastChar == 'D') ? Double.parseDouble(result.substring(0, result.length() - 1)) : Double.parseDouble(result);
                        if (Double.isNaN(num) || Double.isInfinite(num))
                        {
                            throw new MojangsonParserException("Can't parse nbt value from mojangson, found invalid number: " + num, contextIndex,
                                                               context.getText());
                        }
                        resultTag = new NbtTagDouble(key, num);
                    }
                    else
                    {
                        int num = Integer.parseInt(result);
                        resultTag = new NbtTagInt(key, num);
                    }
                }
            }
        }
        catch (NumberFormatException e)
        {
            if (result == null)
            {
                throw new MojangsonParserException("Can't parse nbt value from mojangson.", contextIndex, context.getText(), e);
            }
            resultTag = new NbtTagString(key, removeQuotes(result));
        }
        return resultTag;
    }

    private static String removeQuotes(String result)
    {
        return StringUtils.replace(result, "\\\"", "\"");
    }

    static NbtTag valueFromMojangson(@Nullable String key, ParserContext context, boolean skipWhitespaces)
    {
        if (skipWhitespaces)
        {
            context.skipWhitespaces();
        }
        if (! context.hasNext())
        {
            throw new MojangsonParserException("Can't parse mojangson, expected more input", context.getIndex(), context.getText());
        }
        int contextIndex = context.getIndex();
        if (! context.hasNext())
        {
            throw new MojangsonParserException("Can't parse nbt value from mojangson.", contextIndex, context.getText());
        }
        NbtTag resultTag;
        char first = context.next();
        if (first == '[')
        {
            resultTag = listFromMojangson(key, context, skipWhitespaces);
        }
        else if (first == '{')
        {
            resultTag = compoundFromMojangson(key, context, skipWhitespaces);
        }
        else
        {
            resultTag = tryParsePrimitive(key, context);
            if (resultTag == null)
            {
                context.previous();
                ArgumentParseResult<? extends String> parse = keyParser.checkAndParse(context, c -> (c == ' ') || (c == ',') || (c == '}'));
                String result = parse.getResult();
                if (! parse.isSuccess() || (result == null))
                {
                    Exception exception = parse.getException();
                    if (exception != null)
                    {
                        throw new MojangsonParserException("Can't parse nbt value from mojangson.", contextIndex, context.getText(), exception);
                    }
                    throw new MojangsonParserException("Can't parse nbt value from mojangson.", contextIndex, context.getText());
                }
                resultTag = new NbtTagString(key, removeQuotes(result));
            }
        }
//        if (resultTag == null)
//        {
//            throw new MojangsonParserException("Can't parse nbt value from mojangson.", contextIndex, context.getText());
//        }
        resultTag.setName(key);
        return resultTag;
    }

    static String keyFromMojangson(ParserContext context, boolean skipWhitespaces)
    {
        if (skipWhitespaces)
        {
            context.skipWhitespaces();
        }
        if (! context.hasNext())
        {
            throw new MojangsonParserException("Can't parse mojangson, expected more input", context.getIndex(), context.getText());
        }
        int contextIndex = context.getIndex();
        if (! context.hasNext())
        {
            throw new MojangsonParserException("Can't parse nbt key from mojangson.", contextIndex, context.getText());
        }
        ArgumentParseResult<? extends String> parse = keyParser.checkAndParse(context, c -> (c == ' ') || (c == ':'));
        String result = parse.getResult();
        if (! parse.isSuccess() || (result == null))
        {
            Exception exception = parse.getException();
            if (exception != null)
            {
                throw new MojangsonParserException("Can't parse nbt key from mojangson.", contextIndex, context.getText(), exception);
            }
            throw new MojangsonParserException("Can't parse nbt key from mojangson.", contextIndex, context.getText());
        }
        if (skipWhitespaces)
        {
            context.skipWhitespaces();
        }
        char next = context.next();
        if (next == ':')
        {
            return result;
        }
        throw new MojangsonParserException("Can't parse nbt key from mojangson,  unexpected end of key, expected `:` but `" + next + "` found.", contextIndex,
                                           context.getText());
    }
}
