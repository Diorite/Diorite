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

class EmptyCommentsNode implements CommentsNode
{
    @Nullable private final EmptyDocumentComments root;
    @Nullable private final EmptyCommentsNode     parent;
    @Nullable private       EmptyCommentsNode     cached;

    EmptyCommentsNode(EmptyCommentsNode parent)
    {
        this.root = parent.getRoot();
        this.parent = parent;
    }

    EmptyCommentsNode()
    {
        this.parent = null;
        this.root = null;
    }

    /**
     * Returns root node.
     *
     * @return root node.
     */
    @Override
    public EmptyDocumentComments getRoot()
    {
        assert this.root != null;
        return this.root;
    }
    @Nullable
    @Override
    public EmptyCommentsNode getParent()
    {
        return this.parent;
    }

    @Override
    public void trim()
    {

    }

    @Override
    public String[] fixPath(String... path)
    {
        return path;
    }

    @Override
    public void join(CommentsNode toJoin)
    {

    }

    @Override
    public void setComment(String path, @Nullable String comment)
    {

    }

    @Nullable
    @Override
    public String getComment(String path)
    {
        return null;
    }

    @Override
    public CommentsNode getNode(String path)
    {
        if (this.cached == null)
        {
            this.cached = new EmptyCommentsNode(this);
        }
        return this.cached;
    }

}
