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

import java.io.IOException;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

import org.diorite.annotations.NullableByDefault;
import org.diorite.commons.io.StringBuilderWriter;
import org.diorite.config.serialization.comments.CommentsNode;
import org.diorite.config.serialization.comments.DocumentComments;

@NullableByDefault
public class CommentsConfigTest
{
    private static final String HEADER                =
            "Header of file, first space of comments is always skipped, if you want indent a comment just use more spaces.\n" +
            "    Like this.\n" +
            "\n" +
            "But first char don't need to be a space.";
    private static final String FOOTER                = "Footer of file.";
    private static final String ENTITIES              =
            "Entity list, comments in a list are like in maps, just skip list syntax and add comments directly to list element nodes.";
    private static final String ENTITIES_TYPE         = "Comment for `type` node inside list element.";
    private static final String ENTITIES_NAME         = "Name of entity\n" +
                                                        "   Multiline test\n" +
                                                        "test test";
    private static final String ENTITIES_POWERED      = "also indent of comments is ignored.";
    private static final String ENTITIES_DISPLAY_NAME = null;
    private static final String BEAN                  = "Some other object";
    private static final String BEAN_MAP              = "Weird map of int arrays:";
    private static final String ANY_META              = "asterisk means that any key may appear here, you can use (*) too to still use valid yaml syntax.";
    private static final String ANY_META_NAME         = "name of meta";

    @Test
    public void simpleObjectTest() throws IOException
    {
        Serialization global = Serialization.getInstance();
        DocumentComments comments = DocumentComments.create();
        comments.setComment("test", "dsdsa");
        comments.getNode("testNode").setComment("test", "nah\nlonger nah");
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
        comments.writeTo(System.out);
    }

    @Test
    public void testComments() throws IOException
    {
        // now load config from file:
        DocumentComments parse = DocumentComments.parse(CommentsConfigTest.class.getResourceAsStream("/entitystorage-comment.yml"));

        Assert.assertEquals(HEADER, parse.getHeader());
        Assert.assertEquals(FOOTER, parse.getFooter());
        Assert.assertEquals(ENTITIES, parse.getComment("entities"));
        Assert.assertEquals(ENTITIES_TYPE, parse.getComment("entities", "type"));
        Assert.assertEquals(ENTITIES_NAME, parse.getComment("entities", "name"));
        Assert.assertEquals(ENTITIES_POWERED, parse.getComment("entities", "powered"));
        Assert.assertSame(ENTITIES_DISPLAY_NAME, parse.getComment("entities", "displayName"));
        Assert.assertEquals(BEAN, parse.getComment("beanObject"));
        Assert.assertEquals(BEAN_MAP, parse.getComment("beanObject", "intMap"));
        Assert.assertEquals(ANY_META, parse.getComment("entities", "metaObjects", "anything here?"));
        Assert.assertEquals(ANY_META_NAME, parse.getComment("entities", "metaObjects", "yup, anything.", "name"));

        // save it back to string, load again, and check if equals:
        StringBuilderWriter stringWriter = new StringBuilderWriter(1024);
        parse.writeTo(stringWriter);
        System.out.println(stringWriter);
        Assert.assertEquals(parse, DocumentComments.parse(new StringReader(stringWriter.toString())));
    }
}
