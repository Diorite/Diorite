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

import org.junit.Assert;
import org.junit.Test;

import org.diorite.config.serialization.comments.DocumentComments;

public class SerializationWithCommentsTest
{
    @Test
    public void testSaveWithComments() throws IOException
    {
        Serialization serialization = SerializationTest.prepareSerialization();

        EntityStorage entityStorage = SerializationTest.prepareObject();
        DocumentComments comments = DocumentComments.parse(CommentsConfigTest.class.getResourceAsStream("/entitystorage-comment.yml"));

        System.out.println("[Serializing with manual loaded comments:]");
        System.out.println(serialization.toYamlWithComments(entityStorage, comments));


        System.out.println("\n\n[Serializing with class loaded comments:]");
        System.out.println(serialization.toYamlWithComments(entityStorage));

        Assert.assertEquals(serialization.toYamlWithComments(entityStorage, comments), serialization.toYamlWithComments(entityStorage));
    }
}
