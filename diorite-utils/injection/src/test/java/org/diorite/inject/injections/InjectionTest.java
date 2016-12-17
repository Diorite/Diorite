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

import org.junit.Test;

public class InjectionTest
{
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
//    @Test
//    public void singletonTest()
//    {
//        SimpleExampleObject exampleObject1 = new SimpleExampleObject();
//        SimpleExampleObject exampleObject2 = new SimpleExampleObject();
//        Assert.assertEquals(exampleObject1.getModule1(), exampleObject2.getModule1());
//        Assert.assertNotEquals(exampleObject1.getModule2(), exampleObject2.getModule2());
//    }
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
