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

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents node of comments
 */
public interface CommentsNode
{
    /**
     * Represent any path.
     */
    String ANY = "(*)";

    /**
     * Returns root node.
     *
     * @return root node.
     */
    DocumentComments getRoot();

    /**
     * Returns parent node, or null if it is root node.
     *
     * @return parent node, or null if it is root node.
     */
    @Nullable
    CommentsNode getParent();

    /**
     * Removes unused nodes.
     */
    void trim();

    /**
     * Replaces wildcard strings with proper {@link #ANY} string.
     *
     * @param path
     *         path to fix.
     *
     * @return this same array as given after changes.
     */
    String[] fixPath(String... path);

    /**
     * Join given node to this node.
     *
     * @param toJoin
     *         node to join.
     */
    void join(CommentsNode toJoin);

    /**
     * Join given node to this node, on given path.
     *
     * @param path
     *         join path.
     * @param toJoin
     *         node to join.
     */
    default void join(String path, CommentsNode toJoin)
    {
        this.getNode(path).join(toJoin);
    }

    /**
     * Join given node to this node, on given path.
     *
     * @param path
     *         join path.
     * @param toJoin
     *         node to join.
     */
    default void join(String[] path, CommentsNode toJoin)
    {
        if (path.length == 0)
        {
            this.join(toJoin);
            return;
        }
        this.getNode(path).join(toJoin);
    }

    /**
     * Set comment on given path.
     *
     * @param path
     *         path of comment.
     * @param comment
     *         comment to set.
     */
    void setComment(String path, @Nullable String comment);

    /**
     * Set comment on given path.
     *
     * @param path
     *         path of comment.
     * @param comment
     *         comment to set.
     */
    default void setComment(String[] path, @Nullable String... comment)
    {
        this.setComment(path, StringUtils.join(comment, '\n'));
    }

    /**
     * Set comment on given path.
     *
     * @param path
     *         path of comment.
     * @param comment
     *         comment to set.
     */
    default void setComment(String[] path, @Nullable String comment)
    {
        if (path.length == 0)
        {
            this.setComment(ANY, comment);
            return;
        }
        if (path.length == 1)
        {
            this.setComment(path[0], comment);
            return;
        }
        CommentsNode node = this;
        for (int i = 1; i < path.length; i++)
        {
            String s = path[i - 1];
            node = node.getNode(s);
        }
        node.setComment(path[path.length - 1], comment);
    }

    /**
     * Set comment on given path.
     *
     * @param path
     *         path of comment.
     * @param comment
     *         comment to set.
     */
    default void setComment(String path, @Nullable String... comment)
    {
        if ((comment == null) || (comment.length == 0))
        {
            this.setComment(path, (String) null);
            return;
        }
        this.setComment(path, StringUtils.join(comment, '\n'));
    }

    /**
     * Set comment on given node.
     *
     * @param path
     *         path of node.
     * @param comments
     *         comments to set.
     */
    default void setNode(String path, Map<String, String> comments)
    {
        CommentsNode node = this.getNode(path);
        for (Entry<String, String> stringEntry : comments.entrySet())
        {
            node.setComment(stringEntry.getKey(), stringEntry.getValue());
        }
    }

    /**
     * Returns comment on given path.
     *
     * @param path
     *         path to check for comments.
     *
     * @return comment on given path.
     */
    @Nullable
    String getComment(String path);

    /**
     * Returns comment on given path, each string is next node in tree.
     *
     * @param pathNodes
     *         path to check for comments.
     *
     * @return comment on given path.
     */
    @Nullable
    default String getComment(String... pathNodes)
    {
        if (pathNodes.length == 0)
        {
            return this.getComment(ANY);
        }
        if (pathNodes.length == 1)
        {
            return this.getComment(pathNodes[0]);
        }
        CommentsNode node = this;
        for (int i = 0; i < pathNodes.length; i++)
        {
            if ((i + 1) >= pathNodes.length)
            {
                return node.getComment(pathNodes[i]);
            }
            node = node.getNode(pathNodes[i]);
        }
        // loop should always return value.
        throw new AssertionError("Message not found.");
    }

    /**
     * Returns node on given path, if there is no node on given path it will be created and returned.
     *
     * @param path
     *         path to check for node.
     *
     * @return node on given path.
     */
    CommentsNode getNode(String path);

    /**
     * Returns node on given path, if there is no node on given path it will be created and returned, each string is next node in tree.
     *
     * @param pathNodes
     *         path to check for node.
     *
     * @return node on given path.
     */
    default CommentsNode getNode(String... pathNodes)
    {
        if (pathNodes.length == 0)
        {
            throw new IllegalArgumentException("Can't get node on empty path.");
        }
        if (pathNodes.length == 1)
        {
            return this.getNode(pathNodes[0]);
        }
        CommentsNode node = this;
        for (String path : pathNodes)
        {
            node = node.getNode(path);
        }
        return node;
    }
}
