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

import org.junit.Test;

public class NumberParserTest extends ParserTest<Integer>
{
    public NumberParserTest() {super(IntegerParser.DECIMAL);}

    @Test
    public void parseTest()
    {
        this.assertResult("12", true, 12);
        this.assertResult("86563443245643523424325435632", false, null);
        this.assertResult("-12", true, - 12);
        this.assertResult("0xFF", true, 0xFF);
        this.assertResult("012", true, 012);
        this.assertResult("0b110", true, 0b110);
        this.assertResult("012 cookie", true, 012, "extra input ignored");
        this.assertResult("0b110 cookie", true, 0b110, "extra input ignored");
        this.assertResult("0b1102", false, null);
        this.assertResult(IntegerParser.forRadix(3), "2121112", true, Integer.parseInt("2121112", 3), "radix of 3");
        this.assertResult(IntegerParser.forRadix(3), "21211123", false, null, "radix of 3");

        this.assertResult("nah", false, null);
    }

}
