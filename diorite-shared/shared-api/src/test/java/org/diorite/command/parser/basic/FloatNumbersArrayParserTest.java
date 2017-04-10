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

import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class FloatNumbersArrayParserTest extends ParserTest<Collection<Double>>
{
    public FloatNumbersArrayParserTest() {super(new ArrayParser<>(DoubleParser.ALLOW_NAN_INFINITY));}

    @Test
    public void parseTest()
    {
        this.assertResult("[]", true, List.of());
        this.assertResult("[1]", true, List.of(1.0));
        this.assertResult("[1,2]", true, List.of(1.0, 2.0));
        this.assertResult("[1,    2]", true, List.of(1.0, 2.0));
        this.assertResult("[1     ,    2]", true, List.of(1.0, 2.0));
        this.assertResult("[1,2,3,4,5]", true, List.of(1.0, 2.0, 3.0, 4.0, 5.0));
        this.assertResult("[1,   2,  3,4   ,5  ]", true, List.of(1.0, 2.0, 3.0, 4.0, 5.0));
        this.assertResult("<1,   2,  3,4   ,5  >", true, List.of(1.0, 2.0, 3.0, 4.0, 5.0));
        this.assertResult("[1,   2,  NaN,4   ,5  , Infinity   ,   -Infinity   ]", true,
                          List.of(1.0, 2.0, Double.NaN, 4.0, 5.0, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
    }

}
