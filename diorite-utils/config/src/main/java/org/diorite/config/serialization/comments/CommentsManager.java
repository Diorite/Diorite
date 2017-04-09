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

package org.diorite.config.serialization.comments;

import javax.annotation.WillNotClose;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.diorite.config.serialization.Serialization;

public class CommentsManager
{
    private final Map<Class<?>, DocumentComments> commentsMap = new ConcurrentHashMap<>(10);

    public DocumentComments getComments(Class<?> clazz)
    {
        DocumentComments documentComments = this.commentsMap.get(clazz);
        if (documentComments != null)
        {
            return documentComments;
        }
        DocumentComments comments = DocumentComments.parseFromAnnotations(clazz);
        this.commentsMap.put(clazz, comments);
        return comments;
    }

    public void joinComments(Class<?> clazz, String path, DocumentComments comments)
    {
        this.getComments(clazz).join(path, comments);
    }

    public void joinComments(Class<?> clazz, DocumentComments comments)
    {
        this.getComments(clazz).join(comments);
    }

    public void addComments(Class<?> clazz, DocumentComments comments)
    {
        this.commentsMap.put(clazz, comments);
    }

    public void addComments(Class<?> clazz, @WillNotClose InputStream stream)
    {
        this.addComments(clazz, new InputStreamReader(stream, StandardCharsets.UTF_8.newDecoder()
                                                                                    .onMalformedInput(CodingErrorAction.REPORT)
                                                                                    .onUnmappableCharacter(CodingErrorAction.REPORT)));
    }

    public void addComments(Class<?> clazz, @WillNotClose Reader reader)
    {
        DocumentComments comments = Serialization.getInstance().fromYaml(reader, DocumentComments.class);
        this.addComments(clazz, (comments == null) ? DocumentComments.empty() : comments);
    }

}
