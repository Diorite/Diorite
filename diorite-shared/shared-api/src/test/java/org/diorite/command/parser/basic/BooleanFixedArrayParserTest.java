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

public class BooleanFixedArrayParserTest extends ParserTest<Collection<Boolean>>
{
    public BooleanFixedArrayParserTest() {super(new FixedArrayParser<>(new BooleanParser(), 1));}

    @Test
    public void parseTest()
    {
        this.assertResult(new FixedArrayParser<>(new BooleanParser(), 1), "[]", false, null);
        this.assertResult(new FixedArrayParser<>(new BooleanParser(), 1), "[1]", true, List.of(true), "1 element");
        this.assertResult(new FixedArrayParser<>(new BooleanParser(), 1), "1", true, List.of(true), "1 element");
        this.assertResult(new FixedArrayParser<>(new BooleanParser(), 2), "[1, 0]", true, List.of(true, false), "2 elements");
        this.assertResult(new FixedArrayParser<>(new BooleanParser(), 2), "[1 , 0]", true, List.of(true, false), "2 elements");
        this.assertResult(new FixedArrayParser<>(new BooleanParser(), 2), "1 0", true, List.of(true, false), "2 elements");
        this.assertResult(new FixedArrayParser<>(new BooleanParser(), 4), "1 0 true yes", true, List.of(true, false, true, true), "4 elements");
        this.assertResult(new FixedArrayParser<>(new BooleanParser(), 2), "1 0 true yes", true, List.of(true, false), "2 elements");
        this.assertResult(new FixedArrayParser<>(new BooleanParser(), 2), "1, 0", false, null, "2 elements");
        this.assertResult(new FixedArrayParser<>(new BooleanParser(), 2), "1,0", true, List.of(true, false), "2 elements");
    }

}
