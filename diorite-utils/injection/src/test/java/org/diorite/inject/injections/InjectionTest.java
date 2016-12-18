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
import org.junit.BeforeClass;
import org.junit.Test;

import org.diorite.inject.EmptyAnn;
import org.diorite.inject.InjectionLibrary;
import org.diorite.inject.Named;
import org.diorite.inject.Qualifiers;
import org.diorite.inject.controller.DefaultInjectionController;

public class InjectionTest
{
    public static void main(String[] args)
    {
        prepare();
        InjectionTest injectionTest = new InjectionTest();
        injectionTest.simpleInjectionTest();
    }

    @BeforeClass
    public static void prepare()
    {
        DefaultInjectionController controller = InjectionLibrary.getController();
        controller.bindToClass(Module.class).with(Named.class, EmptyAnn.class).dynamic(
                (object, data) ->
                {
                    Named qualifier = data.getQualifier(Named.class);
                    if (qualifier == null)
                    {
                        throw new RuntimeException();
                    }
                    if (qualifier.value().equals("module1"))
                    {
                        return new Module1();
                    }
                    if (qualifier.value().equals("module2"))
                    {
                        return new Module2();
                    }
                    return new AnyModule(qualifier.value());
                });
        controller.bindToClass(Module.class).with(Qualifiers.of(Named.class, "module2")).toInstance(new Module2());
        controller.bindToClass(Module.class).with(Qualifiers.of(Named.class, "module1")).toType(Module1.class);
        controller.rebind();
    }

    @Test
    public void simpleInjectionTest()
    {
        SimpleExampleObject exampleObject = new SimpleExampleObject();
        exampleObject.assertInjections();
    }

    //    @Test
//    public void injectionTest()
//    {
//        ExampleObject exampleObject = new ExampleObject();
//        exampleObject.assertInjections();
//    }
//
//    @Test
//    public void advancedInjectionTest()
//    {
//        AdvancedExampleObject exampleObject = new AdvancedExampleObject();
//        exampleObject.assertInjections();
//    }
//
    @Test
    public void singletonTest()
    {
        SimpleExampleObject exampleObject1 = new SimpleExampleObject();
        SimpleExampleObject exampleObject2 = new SimpleExampleObject();
        Assert.assertEquals(exampleObject1.getModule1(), exampleObject2.getModule1());
    }
//
//    @Test
//    public void advancedSingletonTest()
//    {
//        ExampleObject exampleObject1 = new ExampleObject();
//        ExampleObject exampleObject2 = new ExampleObject();
//        Assert.assertEquals(exampleObject1.getModule1(), exampleObject2.getModule1());
//        Assert.assertNotEquals(exampleObject1.getModule2(), exampleObject2.getModule2());
//        Assert.assertEquals(exampleObject1.getSomeModuleProvider().get(), exampleObject2.getSomeModuleProvider().get());
//    }
}
