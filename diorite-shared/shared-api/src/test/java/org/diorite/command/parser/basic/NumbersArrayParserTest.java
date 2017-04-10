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

public class NumbersArrayParserTest extends ParserTest<Collection<Integer>>
{
    public NumbersArrayParserTest() {super(new ArrayParser<>(IntegerParser.DECIMAL));}

    @Test
    public void parseTest()
    {
        this.assertResult("[]", true, List.of());
        this.assertResult("[1]", true, List.of(1));
        this.assertResult("[1.0]", false,null);
        this.assertResult("[15345436546325456435353532]", false,null);
        this.assertResult("[1,2]", true, List.of(1,2));
        this.assertResult("[1,    2]", true, List.of(1,2));
        this.assertResult("[1     ,    2]", true, List.of(1,2));
        this.assertResult("[1,2,3,4,5]", true, List.of(1,2,3,4,5));
        this.assertResult("1,2,3,4,5", true, List.of(1,2,3,4,5));
        this.assertResult("[1,   2,  3,4   ,5  ]", true, List.of(1,2,3,4,5));
        this.assertResult("<1,   2,  3,4   ,5  >", true, List.of(1,2,3,4,5));
    }

}
