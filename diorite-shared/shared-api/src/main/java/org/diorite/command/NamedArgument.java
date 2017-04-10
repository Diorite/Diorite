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

package org.diorite.command;

import java.util.Collection;

/**
 * Represent argument with name. Named arguments of command can be used in any order, named arguments are optional too, like:
 * <pre>
 *     {@code
 *     public void someCommand(..., double money, @Arg(named=true) String name, @Arg(named=true) int value)
 *     }
 * </pre>
 * can be invoked as:
 * <pre>
 *     <ul>
 *         <li>/someCommand 22.12 name=MyName value=56</li>
 *         <li>/someCommand name=MyName 22.12 value=56</li>
 *         <li>/someCommand name=MyName value=56 22.12</li>
 *         <li>/someCommand value=56 22.12 name:MyName</li>
 *         <li>/someCommand value:56 22.12</li>
 *         <li>/someCommand 22.12 name=MyName</li>
 *     </ul>
 * </pre>
 *
 * @param <T>
 *         type of argument object.
 */
public interface NamedArgument<T> extends Argument<T>
{
    /**
     * Returns name of argument.
     *
     * @return name of argument.
     */
    String getName();

    /**
     * Returns name aliases of argument.
     *
     * @return name aliases of argument.
     */
    Collection<String> getAliases();
}
