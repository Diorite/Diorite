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

package org.diorite.config.serialization;

import org.junit.Assert;
import org.junit.Test;

import org.diorite.config.serialization.comments.CommentsNode;
import org.diorite.config.serialization.comments.DocumentComments;

public class CommentsConfigTest
{
    @Test
    public void testComments()
    {
        Serialization global = Serialization.getGlobal();
        DocumentComments comments = DocumentComments.create();
        comments.setComment("test", "dsdsa");
        comments.getNode("testNode").setComment("test", "nah");
        comments.getNode("testNode").setComment("test2", "nah2");
        comments.getNode("testNode", "next").setComment("test", "nah3");
        comments.getNode("testNode2").setComment("test", "nah4");
        comments.getNode("playerMap", CommentsNode.ANY).setComment("test", "anyTest");
        String testStr = comments.getComment("playerMap", "sdfevterrevg", "test");
        System.out.println(testStr);
        Assert.assertNotNull(testStr);
        Assert.assertEquals("anyTest", testStr);
        testStr = comments.getComment("playerMap", "retf4tf4t4", "test");
        Assert.assertEquals("anyTest", testStr);
        Assert.assertEquals("nah4", comments.getComment("testNode2", "test"));
        Assert.assertEquals("nah3", comments.getComment("testNode", "next", "test"));

        System.out.println(global.toYaml(comments));
        System.out.println(global.fromYaml(global.toYaml(comments), DocumentComments.class).equals(comments));
        System.out.println(global.toJson(comments));
        System.out.println(global.fromJson(global.toJson(comments), DocumentComments.class).equals(comments));

    }
}
