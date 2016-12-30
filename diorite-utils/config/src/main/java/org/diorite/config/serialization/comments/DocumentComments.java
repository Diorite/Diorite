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

import org.diorite.config.serialization.Serializable;
import org.diorite.config.serialization.annotations.DelegateSerializable;

/**
 * Root comments entry.
 */
@DelegateSerializable(DocumentCommentsImpl.class)
public interface DocumentComments extends CommentsNode, Serializable
{
    /**
     * Returns itself.
     *
     * @return itself.
     */
    @Override
    default DocumentComments getRoot()
    {
        return this;
    }

    /**
     * Always returns null, as this is root node.
     *
     * @return null, as this is root node.
     */
    @Nullable
    @Override
    default CommentsNode getParent()
    {
        return null;
    }

    /**
     * Returns always empty instance of Document Comments
     *
     * @return always empty instance of Document Comments
     */
    static DocumentComments getEmpty()
    {
        return EmptyDocumentComments.getEmpty();
    }

    /**
     * Returns new instance of document comments.
     *
     * @return new instance of document comments.
     */
    static DocumentComments create()
    {
        return new DocumentCommentsImpl();
    }
}
