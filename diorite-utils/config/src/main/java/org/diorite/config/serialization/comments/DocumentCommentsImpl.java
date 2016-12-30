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

package org.diorite.config.serialization.comments;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.diorite.config.serialization.DeserializationData;
import org.diorite.config.serialization.Serializable;
import org.diorite.config.serialization.SerializationData;
import org.diorite.config.serialization.annotations.SerializableAs;

/**
 * Root comments node.
 */
@SerializableAs(DocumentComments.class)
class DocumentCommentsImpl extends CommentsNodeImpl implements Serializable, DocumentComments
{
    public static DocumentCommentsImpl deserialize(DeserializationData data)
    {
        return fromMap(data.getMap("", String.class, Object.class));
    }

    @Override
    public DocumentComments getRoot()
    {
        return this;
    }

    @Override
    public void serialize(SerializationData data)
    {
        data.addMap("", this.toMap(), String.class, Object.class);
    }

    @SuppressWarnings("unchecked")
    private static DocumentCommentsImpl fromMap(Map<String, Object> map)
    {
        DocumentCommentsImpl root = new DocumentCommentsImpl();
        for (Entry<String, Object> entry : map.entrySet())
        {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String)
            {
                root.setComment(key, (String) value);
            }
            else if (value instanceof String[])
            {
                root.setComment(key, (String[]) value);
            }
            else if (value instanceof Collection)
            {
                Collection<String> stringCollection = (Collection<String>) value;
                root.setComment(key, stringCollection.toArray(new String[stringCollection.size()]));
            }
            else if (value instanceof Map)
            {
                CommentsNode commentsNode = fromMap((Map<String, Object>) value, root);
                root.nodes.put(key, commentsNode);
            }
        }
        return root;
    }
}
