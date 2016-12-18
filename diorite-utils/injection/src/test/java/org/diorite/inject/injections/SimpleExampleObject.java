/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.inject.injections;

import org.junit.Assert;

import org.diorite.inject.EmptyAnn;
import org.diorite.inject.InjectionLibrary;
import org.diorite.inject.NamedInject;
import org.diorite.inject.Singleton;

public class SimpleExampleObject
{
    @NamedInject()
    @Singleton
    private Module module1;
    @NamedInject()
    private Module module2;
    @NamedInject("essentials")
    @EmptyAnn
    private final Module module3 = InjectionLibrary.inject();

    public Module getModule1()
    {
        return this.module1;
    }

    public Module getModule2()
    {
        return this.module2;
    }

    public Module getModule3()
    {
        return this.module3;
    }

    public void assertInjections()
    {
        Assert.assertEquals(this.module1.getName(), "module1");
        Assert.assertEquals(this.module2.getName(), "module2");
        Assert.assertEquals(this.module3.getName(), "essentials");
    }

    public String toString()
    {
        return this.module1 + " & " + this.module2 + " & " + this.module3;
    }
}
