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

import java.util.Collection;

import org.diorite.commons.ParserContext;
import org.diorite.commons.function.predicate.CharPredicate;

/**
 * Represent parser of custom argument type. <br>
 * Each parser should only use one factor of custom object, if you have Guild object that can be matched both by name and tag, two different parsers should
 * be registered.
 *
 * @param <T>
 *         Type of command argument.
 * @param <E>
 *         Type of custom argument object.
 */
public interface CustomArgumentParser<T, E> extends TypeParser<E>
{
    /**
     * Returns name of argument factor used by this parser. Might return empty string if there is only one factor and name of it is unknown.
     *
     * @return name of argument factor used by this parser.
     */
    String getFactorName();

    /**
     * Returns parser of raw command argument.
     *
     * @return parser of raw command argument.
     */
    TypeParser<T> getTypeParser();

    /**
     * Returns collection of all available elements.
     *
     * @return collection of all available elements.
     */
    Collection<? extends E> getAllElements();

    /**
     * Returns collection of elements of which keys starts with given string.
     *
     * @return collection of elements of which keys starts with given string.
     */
    Collection<? extends E> getAllElements(String startsWith);

    /**
     * Converts element to raw command argument.
     *
     * @param element
     *         element to convert.
     *
     * @return raw command argument.
     */
    T toType(E element);

    /**
     * Convert raw command argument to custom argument object.
     *
     * @param key
     *         raw command argument to convert.
     *
     * @return custom argument object.
     */
    ArgumentParseResult<? extends E> convert(T key);

    @Override
    default ArgumentParseResult<? extends E> parse(ParserContext context, CharPredicate endPredicate)
    {
        ArgumentParseResult<? extends T> result = ArgumentParseResultType.ensureNotNull(this.getTypeParser().parse(context, endPredicate));
        if (! result.isSuccess())
        {
            return result.getType().empty();
        }
        T resultResult = result.getResult();
        assert resultResult != null; // success should not be null
        return this.convert(resultResult);
    }

    @Override
    default boolean mustBeLast()
    {
        return this.getTypeParser().mustBeLast();
    }
}
