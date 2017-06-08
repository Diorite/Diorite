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

package org.diorite.command.parser;

import javax.annotation.Nullable;

import java.text.CharacterIterator;

import org.apache.commons.lang3.ArrayUtils;

import org.diorite.commons.ParserContext;
import org.diorite.commons.function.predicate.CharPredicate;

/**
 * Represents type parser, each parser must be state-less and may only contain final/immutable configuration fields;
 *
 * @param <T>
 *         type of object to parse.
 */
public interface TypeParser<T>
{
    char          ESCAPE          = '\\';
    char          SPACE           = ' ';
    char          NULL            = '\0';
    char          END             = CharacterIterator.DONE;
    /**
     * Space predicate used by many parsers, with end of parser stream also interpreted as space.
     */
    CharPredicate SPACE_PREDICATE = c -> (c == SPACE) || (c == CharacterIterator.DONE);

    /**
     * Run parser with given context and end predicate.
     *
     * @param context
     *         parser char iterator.
     * @param endPredicate
     *         predicate used to determine when parser should stop parsing.
     *
     * @return result of parse operation, sometimes might be null.
     */
    @Nullable
    ArgumentParseResult<? extends T> parse(ParserContext context, CharPredicate endPredicate);

    /**
     * Run parser with given context and end predicate as array of chars that ends stream for this type.
     *
     * @param context
     *         parser char iterator.
     * @param endPredicate
     *         chars used to determine when parser should stop parsing.
     *
     * @return result of parse operation, sometimes might be null.
     */
    @Nullable
    default ArgumentParseResult<? extends T> parse(ParserContext context, char... endPredicate)
    {
        return this.parse(context, c -> ArrayUtils.contains(endPredicate, c));
    }

    /**
     * Returns true if this parser can only be used for last argument of command.
     *
     * @return true if this parser can only be used for last argument of command.
     */
    default boolean mustBeLast()
    {
        return false;
    }

    /**
     * Run parser with given context and end predicate as array of chars that ends stream for this type. <br>
     * This method does additional checks to prevent returning of null value.
     *
     * @param context
     *         parser char iterator.
     * @param endPredicate
     *         chars used to determine when parser should stop parsing.
     *
     * @return result of parse operation.
     */
    default ArgumentParseResult<? extends T> checkAndParse(ParserContext context, CharPredicate endPredicate)
    {
        if (! context.hasNext())
        {
            return ArgumentParseResultType.EMPTY.empty();
        }
        ArgumentParseResult<? extends T> parse = this.parse(context, endPredicate);
        return (parse == null) ? ArgumentParseResultType.SYNTAX_ERROR.empty() : parse;
    }

    /**
     * Helper method for removing trailing spaces from parser/
     *
     * @param context
     *         parser context.
     * @param returnPrevious
     *         if parser should stop at last trailing space, or on first non-trailing space char.
     *         Should be true if parser is later given to other parser and return value is omitted.
     *
     * @return last skipped char.
     */
    static char removeSpaces(ParserContext context, boolean returnPrevious)
    {
        char c = context.next();
        if (! returnPrevious)
        {
            while (c == SPACE)
            {
                c = context.next();
            }
            return c;
        }
        if (c == SPACE)
        {
            while (c == SPACE)
            {
                c = context.next();
            }
            c = context.previous();
        }
        else
        {
            c = context.previous();
        }
        return c;
    }
}
