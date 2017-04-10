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

import java.text.CharacterIterator;

/**
 * Represent parser context, each parsed command will create own instance of this class.
 */
public interface ParserContext extends CharacterIterator, Cloneable
{
    /**
     * Run parser.
     *
     * @return parser result object.
     */
    ParserResult parse();

    /**
     * Returns expected amount of arguments.
     *
     * @return expected amount of arguments.
     */
    int getExpectedSize();

    /**
     * Returns if there is more characters in parser/iterator.
     *
     * @return if there is more characters in parser/iterator.
     */
    boolean hasNext();

    @Override
    char first();

    @Override
    char last();

    @Override
    char setIndex(int p);

    @Override
    char current();

    @Override
    char next();

    @Override
    char previous();

    @Override
    int getBeginIndex();

    @Override
    int getEndIndex();

    @Override
    int getIndex();

    @Override
    ParserContext clone();
}
