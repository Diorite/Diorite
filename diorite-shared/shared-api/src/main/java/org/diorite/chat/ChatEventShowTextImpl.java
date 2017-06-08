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

package org.diorite.chat;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

class ChatEventShowTextImpl implements ChatEventShowText
{
    protected final ChatMessage value;

    protected ChatEventShowTextImpl(ChatMessage value) {this.value = value;}

    @Override
    public ChatMessage getValue()
    {
        return this.value;
    }

    @Override
    public ChatMessageEvent duplicate()
    {
        return new ChatEventShowTextImpl(this.value);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChatEventShowTextImpl))
        {
            return false;
        }
        ChatEventShowTextImpl that = (ChatEventShowTextImpl) o;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.value);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("value", this.value).toString();
    }
}
