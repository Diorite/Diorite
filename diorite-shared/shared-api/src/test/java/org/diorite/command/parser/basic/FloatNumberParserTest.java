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

public class FloatNumberParserTest extends ParserTest<Double>
{
    public FloatNumberParserTest() {super(DoubleParser.ALLOW_NAN_INFINITY);}

    @Test
    public void parseTest()
    {
        this.assertResult("12", true, 12.0);
        this.assertResult("12.6352365225", true, 12.6352365225);
        this.assertResult("-12", true, - 12.0);
        this.assertResult("-12.0", true, - 12.0);
        this.assertResult(".56", true, .56);
        this.assertResult(".56 cookie", true, .56, "extra input ignored");
        this.assertResult("NaN", true, Double.NaN, "ALLOW_NAN_INFINITY");
        this.assertResult("Infinity", true, Double.POSITIVE_INFINITY, "ALLOW_NAN_INFINITY");
        this.assertResult("-Infinity", true, Double.NEGATIVE_INFINITY, "ALLOW_NAN_INFINITY");
        this.assertResult(DoubleParser.ALLOW_INFINITY, "NaN", false, null, "ALLOW_INFINITY");
        this.assertResult(DoubleParser.ALLOW_INFINITY, "Infinity", true, Double.POSITIVE_INFINITY, "ALLOW_INFINITY");
        this.assertResult(DoubleParser.ALLOW_INFINITY, "-Infinity", true, Double.NEGATIVE_INFINITY, "ALLOW_INFINITY");
        this.assertResult(DoubleParser.ALLOW_NAN, "NaN", true, Double.NaN, "ALLOW_NAN");
        this.assertResult(DoubleParser.ALLOW_NAN, "Infinity", false, null, "ALLOW_NAN");
        this.assertResult(DoubleParser.ALLOW_NAN, "-Infinity", false, null, "ALLOW_NAN");
        this.assertResult(DoubleParser.INSTANCE, "NaN", false, null, "INSTANCE");
        this.assertResult(DoubleParser.INSTANCE, "Infinity", false, null, "INSTANCE");
        this.assertResult(DoubleParser.INSTANCE, "-Infinity", false, null, "INSTANCE");

        this.assertResult("nah", false, null);
    }

}
