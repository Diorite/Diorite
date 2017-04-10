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

public class StringFixedArrayParserTest extends ParserTest<Collection<String>>
{
    public StringFixedArrayParserTest() {super(new FixedArrayParser<>(new StringParser(), 1));}

    @Test
    public void parseTest()
    {
        this.assertResult(new FixedArrayParser<>(new StringParser(), 1), "[]", false, List.of(), "1 element");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 1), "[abc]", true, List.of("abc"), "1 element");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 1), "abc", true, List.of("abc"), "1 element");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 2), "[abc,cd]", true, List.of("abc", "cd"), "2 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 2), "abc,cd", true, List.of("abc", "cd"), "2 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 2), "abc cd", true, List.of("abc", "cd"), "2 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 2), "[abc,    cd]", true, List.of("abc", "cd"), "2 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 2), "[abc     ,    cd]", true, List.of("abc", "cd"), "2 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 5), "[a,b,c,d,e]", true, List.of("a", "b", "c", "d", "e"), "5 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 5), "a,b,c,d,e", true, List.of("a", "b", "c", "d", "e"), "5 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 5), "a b c d e", true, List.of("a", "b", "c", "d", "e"), "5 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 5), "[a,b, 'i\\'m longer' ,d,e]", true, List.of("a", "b", "i'm longer", "d", "e"),
                          "5 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 5), "a,b, 'i\\'m longer' ,d,e", false, null, "5 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 5), "a,b,'i\\'m longer',d,e", true, List.of("a", "b", "i'm longer", "d", "e"),
                          "5 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 5), "a b 'i\\'m longer' d e", true, List.of("a", "b", "i'm longer", "d", "e"),
                          "5 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 3), "<\"more elements, hah\", nah ugh, idk>", true,
                          List.of("more elements, hah", "nah ugh", "idk"), "3 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 4), "\"more elements, hah\",nah ugh,idk", true,
                          List.of("more elements, hah", "nah", "ugh", "idk"), "4 elements");
        this.assertResult(new FixedArrayParser<>(new StringParser(), 3), "\"more elements, hah\" 'nah ugh' idk", true,
                          List.of("more elements, hah", "nah ugh", "idk"), "3 elements");
    }

}
