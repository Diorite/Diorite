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

package org.diorite.command.parser.basic;

import org.diorite.command.parser.ArgumentParseResult;
import org.diorite.command.parser.ArgumentParseResultType;
import org.diorite.commons.math.DioriteMathUtils;

/**
 * Represent double parser, parser might but don't need to allow NaN or Infinity values. <br>
 * If parser does not allow NaN/Infinity values then it will return error result if it will get one.
 */
public final class DoubleParser extends NumberFloatParser<Double>
{
    /** Allows for both NaN and Infinity values. */
    public static final DoubleParser ALLOW_NAN_INFINITY = new DoubleParser(true, true);
    /** Allows only for NaN values. */
    public static final DoubleParser ALLOW_NAN          = new DoubleParser(true, false);
    /** Allows only for Infinity values. */
    public static final DoubleParser ALLOW_INFINITY     = new DoubleParser(false, true);
    /** Disallow both NaN and Infinity values. */
    public static final DoubleParser INSTANCE           = new DoubleParser(false, false);

    /**
     * Returns cached instance of double parser with given properties.
     *
     * @param allowNaN
     *         if parser should allow for NaN values.
     * @param allowInfinity
     *         if parser should allow for Infinity values.
     *
     * @return cached instance of double parser with given properties.
     */
    public static DoubleParser getInstance(boolean allowNaN, boolean allowInfinity)
    {
        if (allowInfinity)
        {
            if (allowNaN)
            {
                return ALLOW_NAN_INFINITY;
            }
            return ALLOW_INFINITY;
        }
        if (allowNaN)
        {
            return ALLOW_NAN;
        }
        return INSTANCE;
    }

    private DoubleParser(boolean allowNaN, boolean allowInfinity)
    {
        super(allowNaN, allowInfinity);
    }

    @Override
    public ArgumentParseResult<Double> parse(String context)
    {
        Double aDouble = DioriteMathUtils.asDouble(context);
        if (aDouble == null)
        {
            return ArgumentParseResultType.SYNTAX_ERROR.empty();
        }
        if (! this.allowNaN && aDouble.isNaN())
        {
            return ArgumentParseResultType.SYNTAX_ERROR.empty();
        }
        if (! this.allowInfinity && aDouble.isInfinite())
        {
            return ArgumentParseResultType.SYNTAX_ERROR.empty();
        }
        return ArgumentParseResultType.SUCCESS.of(aDouble);
    }
}
