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

import javax.inject.Provider;
import javax.inject.Singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;

import org.diorite.inject.AfterInject;
import org.diorite.inject.BeforeInject;
import org.diorite.inject.InjectionLibrary;
import org.diorite.inject.EmptyAnn;
import org.diorite.inject.Inject;

public class ExampleObject
{
    private static final Collection<String> invoked_pattern = List.of("injectEdit", "beforeMoreModules", "injectMoreModules", "afterMoreModules");
    private final        Collection<String> invoked         = new ArrayList<>(4);

    @Inject()
    @Singleton
    private Module module1;
    @Inject()
    private Module module2;
    @Inject("essentials")
    @EmptyAnn
    private final Module module3 = InjectionLibrary.inject();
    @Inject()
    @EmptyAnn
    @Singleton
    private Provider<Module> someModuleProvider;

    @Inject()
    @EmptyAnn
    private Module edit = this.injectEdit();

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

    public Provider<Module> getSomeModuleProvider()
    {
        return this.someModuleProvider;
    }

    public Module getEdit()
    {
        return this.edit;
    }

    // this should found invoke to DILibrary.inject() in method body and replace it with proper injectField invoke
    private Module injectEdit()
    {
        System.out.println("Before inject");
        Module inject = InjectionLibrary.inject();
        System.out.println("After inject: " + inject);
        Assert.assertEquals(inject, "edit");
        this.invoked.add("injectEdit");
        return inject;
//        return Injector.injectField(this, 0, 4);
    }

    @Inject
    @Singleton
    private void injectMoreModules(Module module1, Module module2, @EmptyAnn Module guard)
    {
//        System.out.println("injectMoreModules: " + module1 + " & " + module2 + " & " + guard);
        Assert.assertEquals(module1.getName(), new Module1().getName());
        Assert.assertEquals(module2.getName(), new Module2().getName());
        Assert.assertEquals(guard.getName(), "guard");
        this.invoked.add("injectMoreModules");
    }

    @AfterInject("MoreModules")
    private void afterMoreModules()
    {
        this.invoked.add("afterMoreModules");
    }

    @BeforeInject("MoreModules")
    private void beforeMoreModules()
    {
        this.invoked.add("beforeMoreModules");
    }

    public void assertInjections()
    {
        Assert.assertEquals(invoked_pattern, this.invoked);
        Assert.assertEquals(this.module1.getName(), "module1");
        Assert.assertEquals(this.module2.getName(), "module2");
        Assert.assertEquals(this.module3.getName(), "essentials");
        Assert.assertEquals(this.someModuleProvider.get().getName(), "someModule");
        Assert.assertEquals(this.edit.getName(), "edit");
    }

    public String toString()
    {
        return this.module1 + " & " + this.module2 + " & " + this.module3 + " & " + this.someModuleProvider.get() + " & " + this.edit;
    }
}
