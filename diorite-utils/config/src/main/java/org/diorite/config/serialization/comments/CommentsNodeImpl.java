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

import javax.annotation.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

class CommentsNodeImpl implements CommentsNode
{
    @Nullable private final DocumentComments root;
    @Nullable private final CommentsNode     parent;
    Map<String, CommentsNode> nodes    = new HashMap<>(3);
    Map<String, String>       comments = new HashMap<>(5);

    CommentsNodeImpl(CommentsNode parent)
    {
        this.root = parent.getRoot();
        this.parent = parent;
    }

    CommentsNodeImpl()
    {
        this.parent = null;
        this.root = null;
    }

    @Override
    public DocumentComments getRoot()
    {
        assert this.root != null;
        return this.root;
    }

    @Override
    public Map<String, Object> toMap()
    {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>(this.nodes.size() + this.comments.size());
        result.putAll(this.comments);
        for (Entry<String, CommentsNode> entry : this.nodes.entrySet())
        {
            Map<String, Object> objectMap = entry.getValue().toMap();
            if (! objectMap.isEmpty())
            {
                result.put(entry.getKey(), objectMap);
            }
        }
        return result;
    }

    @Override
    @Nullable
    public CommentsNode getParent()
    {
        return this.parent;
    }

    @Override
    public void setComment(String path, @Nullable String comment)
    {
        if (comment == null)
        {
            this.comments.remove(path);
        }
        this.comments.put(path, comment);
    }

    @Override
    @Nullable
    public String getComment(String path)
    {
        return this.comments.get(path);
    }

    @Override
    public CommentsNode getNode(String path)
    {
        CommentsNode node = this.nodes.get(path);
        if (node == null)
        {
            node = this.nodes.get(ANY);
            if (node == null)
            {
                CommentsNodeImpl commentsNode = new CommentsNodeImpl(this);
                this.nodes.put(path, commentsNode);
                return commentsNode;
            }
            return node;
        }
        return node;
    }

    @SuppressWarnings("unchecked")
    static CommentsNode fromMap(Map<String, Object> map, CommentsNode parent)
    {
        CommentsNodeImpl node = new CommentsNodeImpl(parent);
        for (Entry<String, Object> entry : map.entrySet())
        {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String)
            {
                node.setComment(key, (String) value);
            }
            else if (value instanceof String[])
            {
                node.setComment(key, (String[]) value);
            }
            else if (value instanceof Collection)
            {
                Collection<String> stringCollection = (Collection<String>) value;
                node.setComment(key, stringCollection.toArray(new String[stringCollection.size()]));
            }
            else if (value instanceof Map)
            {
                CommentsNode commentsNode = fromMap((Map<String, Object>) value, node);
                node.nodes.put(key, commentsNode);
            }
        }
        return node;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (! (object instanceof CommentsNodeImpl))
        {
            return false;
        }
        CommentsNodeImpl node = (CommentsNodeImpl) object;
        return Objects.equals(this.comments, node.comments) && Objects.equals(this.nodes, node.nodes);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.nodes, this.comments);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("nodes", this.nodes).append("comments", this.comments).toString();
    }
}
