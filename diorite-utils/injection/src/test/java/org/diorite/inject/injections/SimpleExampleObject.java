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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;

import org.diorite.inject.AfterInject;
import org.diorite.inject.BeforeInject;
import org.diorite.inject.EmptyAnn;
import org.diorite.inject.InjectableClass;
import org.diorite.inject.InjectionLibrary;
import org.diorite.inject.NamedInject;
import org.diorite.inject.Singleton;

@InjectableClass
public class SimpleExampleObject
{
    private static final Collection<String> invoked_pattern =
            List.of("beforeAll", "beforeModule3", "afterModule3", "beforeIdk", "Module1", "afterIdk", "beforeIndirect", "injectIndirect", "afterIndirect",
                    "afterAll");
    private static final Collection<String> invoked         = new ArrayList<>(3);

    @NamedInject()
    @Singleton
    private       Module module1 = InjectionLibrary.inject();
    @NamedInject()
    private       Module module2 = InjectionLibrary.inject();
    @NamedInject("essentials")
    @EmptyAnn
    private final Module module3 = InjectionLibrary.inject();
    @NamedInject()
    @EmptyAnn
    private final Module idk     = this.heh();
    @NamedInject()
    @EmptyAnn
    private final Module indirect;

    {
        // test for indirectly tracking
        Module inject = this.injectIndirect();
        Assert.assertNotNull(inject);
        Assert.assertEquals(inject.getName(), "indirect");
        Module module = this.someMethod(inject);
        Module temp = module;
        module = inject;
        inject = temp;
        this.indirect = module;
        Assert.assertNotNull(this.indirect);
        Assert.assertEquals(this.indirect.getName(), "indirect");
    }

    private Module injectIndirect()
    {
        Module inject = InjectionLibrary.inject();
        Assert.assertEquals(inject.getName(), "indirect");
        invoked.add("injectIndirect");
        return inject;
    }

    private Module heh()
    {
        // test for indirectly tracking
        Module inject = InjectionLibrary.inject();
        Assert.assertNotNull(inject);
        Assert.assertEquals(inject.getName(), "idk");
        Module module = this.someMethod(inject);
        Module temp = module;
        module = inject;
        inject = temp;
        invoked.add(inject.getName());
        return module;
    }

    Module someMethod(Module module)
    {
        return new Module1();
    }

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

    @BeforeInject
    public void beforeAll()
    {
        invoked.clear();
        invoked.add("beforeAll");
    }

    @AfterInject
    public void afterAll()
    {
        invoked.add("afterAll");
    }

    @AfterInject("module3")
    public void afterModule3()
    {
        invoked.add("afterModule3");
    }

    @BeforeInject("module3")
    public void beforeModule3()
    {
        invoked.add("beforeModule3");
    }

    @AfterInject("idk")
    public void afterIdk()
    {
        invoked.add("afterIdk");
    }

    @BeforeInject("idk")
    public void beforeIdk()
    {
        invoked.add("beforeIdk");
    }

    @AfterInject("indirect")
    public void afterIndirect()
    {
        invoked.add("afterIndirect");
    }

    @BeforeInject("indirect")
    public void beforeIndirect()
    {
        invoked.add("beforeIndirect");
    }

    public void assertInjections()
    {
        Assert.assertNotNull(this.module1);
        Assert.assertNotNull(this.module2);
        Assert.assertNotNull(this.module3);
        Assert.assertNotNull(this.idk);
        Assert.assertNotNull(this.indirect);
        Assert.assertEquals(invoked_pattern, invoked);
        Assert.assertEquals(this.module1.getName(), "Module1");
        Assert.assertEquals(this.module2.getName(), "Module2");
        Assert.assertEquals(this.module3.getName(), "essentials");
        Assert.assertEquals(this.idk.getName(), "idk");
        Assert.assertEquals(this.indirect.getName(), "indirect");
    }

    public String toString()
    {
        return this.module1 + " & " + this.module2 + " & " + this.module3 + " & " + this.idk + " & " + this.indirect;
    }
}
