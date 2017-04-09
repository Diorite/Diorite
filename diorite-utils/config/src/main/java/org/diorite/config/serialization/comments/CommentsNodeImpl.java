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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

class CommentsNodeImpl implements CommentsNode
{
    @Nullable private final DocumentComments root;
    @Nullable private final CommentsNode     parent;
    final Map<String, MutablePair<String, CommentsNodeImpl>> dataMap = new LinkedHashMap<>(15);

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
    @Nullable
    public CommentsNode getParent()
    {
        return this.parent;
    }

    public Map<String, MutablePair<String, CommentsNodeImpl>> copyMap(CommentsNodeImpl parent)
    {
        Map<String, MutablePair<String, CommentsNodeImpl>> result = new HashMap<>(this.dataMap.size());
        for (Entry<String, MutablePair<String, CommentsNodeImpl>> entry : this.dataMap.entrySet())
        {
            String keyToCpy = entry.getKey();
            MutablePair<String, CommentsNodeImpl> valueToCpy = entry.getValue();
            CommentsNodeImpl nodeToCpy = valueToCpy.getRight();
            CommentsNodeImpl copiedNode;
            if (nodeToCpy == null)
            {
                copiedNode = null;
            }
            else
            {
                copiedNode = new CommentsNodeImpl(parent);
                copiedNode.dataMap.putAll(nodeToCpy.copyMap(parent));
            }
            MutablePair<String, CommentsNodeImpl> copied = new MutablePair<>(valueToCpy.getLeft(), copiedNode);
            result.put(keyToCpy, copied);
        }
        return result;
    }

    @Override
    public void trim()
    {
        for (Iterator<Entry<String, MutablePair<String, CommentsNodeImpl>>> iterator = this.dataMap.entrySet().iterator(); iterator.hasNext(); )
        {
            Entry<String, MutablePair<String, CommentsNodeImpl>> entry = iterator.next();
            MutablePair<String, CommentsNodeImpl> value = entry.getValue();
            CommentsNodeImpl right = value.getRight();
            if (right != null)
            {
                right.trim();
            }
            if (((right == null) || right.dataMap.isEmpty()) && (value.getLeft() == null))
            {
                iterator.remove();
                continue;
            }
            if (right == null)
            {
                continue;
            }
            right.trim();
        }
    }

    Map<String, MutablePair<String, ?>> buildMap()
    {
        Map<String, MutablePair<String, ?>> resultMap = new LinkedHashMap<>(this.dataMap.size());
        for (Entry<String, MutablePair<String, CommentsNodeImpl>> entry : this.dataMap.entrySet())
        {
            MutablePair<String, CommentsNodeImpl> value = entry.getValue();
            CommentsNodeImpl right = value.getRight();
            String left = value.getLeft();
            if ((right == null) && (left == null))
            {
                continue;
            }
            Map<String, MutablePair<String, ?>> rightMap = null;
            if (right != null)
            {
                rightMap = right.buildMap();
                if (rightMap.isEmpty())
                {
                    rightMap = null;
                    if (left == null)
                    {
                        continue;
                    }
                }
            }
            resultMap.put(entry.getKey(), new MutablePair<>(left, rightMap));
        }
        return resultMap;
    }

    @Override
    public String[] fixPath(String... path)
    {
        if (path.length == 0)
        {
            return path;
        }
        CommentsNodeImpl node = this;
        for (int i = 0; i < path.length; i++)
        {
            String fixPath = node.fixPath(path[i]);
            path[i] = fixPath;
            node = node.getNode(fixPath);
        }
        return path;
    }

    @Override
    public void join(CommentsNode toJoin_)
    {
        if (toJoin_ instanceof EmptyCommentsNode)
        {
            return;
        }
        if (! (toJoin_ instanceof CommentsNodeImpl))
        {
            throw new IllegalArgumentException("Can't join to unknown node type.");
        }
        CommentsNodeImpl toJoin = (CommentsNodeImpl) toJoin_;
        for (Entry<String, MutablePair<String, CommentsNodeImpl>> entry : toJoin.dataMap.entrySet())
        {
            String nodeKey = entry.getKey();
            MutablePair<String, CommentsNodeImpl> pair = entry.getValue();
            String nodeComment = pair.getLeft();
            CommentsNodeImpl subNode = pair.getRight();

            if (nodeComment != null)
            {
                this.setComment(nodeKey, nodeComment);
            }
            if (subNode != null)
            {
                this.join(nodeKey, subNode);
            }
        }
    }

    private String fixPath(String path)
    {
        Pair<String, CommentsNodeImpl> node = this.dataMap.get(path);
        if ((node == null) || (node.getLeft() == null))
        {
            Pair<String, CommentsNodeImpl> any = this.dataMap.get(ANY);
            if ((any != null) && (any.getRight() != null))
            {
                return ANY;
            }
        }
        return path;
    }

    @Override
    public void setComment(String path, @Nullable String comment)
    {
        MutablePair<String, CommentsNodeImpl> nodePair = this.dataMap.computeIfAbsent(path, k -> new MutablePair<>(null, null));
        if (comment == null)
        {
            nodePair.setLeft(null);
            return;
        }
        nodePair.setLeft(comment);
    }

    @Override
    @Nullable
    public String getComment(String path)
    {
        MutablePair<String, CommentsNodeImpl> nodePair = this.dataMap.get(path);
        if (nodePair != null)
        {
            String comment = nodePair.getLeft();
            if (comment != null)
            {
                return comment;
            }
        }
        MutablePair<String, CommentsNodeImpl> anyNodePair = this.dataMap.get(ANY);
        if (anyNodePair != null)
        {
            return anyNodePair.getKey();
        }
        return null;
    }

    @Override
    public CommentsNodeImpl getNode(String path)
    {
        MutablePair<String, CommentsNodeImpl> nodePair = this.dataMap.get(path);
        CommentsNodeImpl node = (nodePair == null) ? null : nodePair.getRight();
        if (node == null)
        {
            MutablePair<String, CommentsNodeImpl> anyNodePair = this.dataMap.get(ANY);
            node = (anyNodePair == null) ? null : anyNodePair.getRight();
            if (node == null)
            {
                CommentsNodeImpl commentsNode = new CommentsNodeImpl(this);
                if (nodePair != null)
                {
                    nodePair.setRight(commentsNode);
                }
                else
                {
                    this.dataMap.put(path, new MutablePair<>(null, commentsNode));
                }
                return commentsNode;
            }
            return node;
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
        return Objects.equals(this.buildMap(), node.buildMap());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.buildMap(), this.buildMap());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("nodes", this.dataMap).toString();
    }
}
