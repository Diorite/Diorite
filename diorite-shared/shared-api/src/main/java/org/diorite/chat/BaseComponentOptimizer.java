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

final class BaseComponentOptimizer
{
    private BaseComponentOptimizer() {}

    static <E extends BaseComponentElement<E, V, I>, V extends ChatMessageEvent, I extends V> void optimize(E element)
    {
        copyStyleToExtra(element);
        optimize0(element);
        removeRedundantStyleFromExtra(element);
    }

    private static <E extends BaseComponentElement<E, V, I>, V extends ChatMessageEvent, I extends V> void optimize0(E element)
    {
        // TODO
    }

    // checks if this element have anything that affects chat.
    static <E extends BaseComponentElement<E, V, I>, V extends ChatMessageEvent, I extends V> boolean isImportant(E element, boolean withExtra)
    {
        if (element.text != null)
        {
            return (! element.text.isEmpty()) || (withExtra && areChildrenImportant(element));
        }
        if (element.selector != null)
        {
            return (! element.selector.isEmpty()) || (withExtra && areChildrenImportant(element));
        }
        if (element.translate != null)
        {
            return (! element.translate.isEmpty()) || (withExtra && areChildrenImportant(element));
        }
        if (element.keyBind != null)
        {
            return true;
        }
        return withExtra && areChildrenImportant(element);
    }

    static <E extends BaseComponentElement<E, V, I>, V extends ChatMessageEvent, I extends V> boolean areChildrenImportant(E element)
    {
        if ((element.extra == null) || element.extra.isEmpty())
        {
            return false;
        }
        for (E extra : element.extra)
        {
            if (isImportant(extra, true))
            {
                return true;
            }
        }
        return false;
    }

    static <E extends BaseComponentElement<E, V, I>, V extends ChatMessageEvent, I extends V> void removeRedundantStyleFromExtra(E element)
    {
        if (element.extra == null)
        {
            return;
        }
        for (E extra : element.extra)
        {
            removeRedundantStyleFromExtra(extra);
            if (extra.bold == element.bold)
            {
                extra.bold = null;
            }
            if (extra.italic == element.italic)
            {
                extra.italic = null;
            }
            if (extra.obfuscated == element.obfuscated)
            {
                extra.obfuscated = null;
            }
            if (extra.strikethrough == element.strikethrough)
            {
                extra.strikethrough = null;
            }
            if (extra.underlined == element.underlined)
            {
                extra.underlined = null;
            }
            if (extra.color == element.color)
            {
                extra.color = null;
            }

            if (Objects.equals(extra.insertion, element.insertion))
            {
                extra.insertion = null;
            }
            if (Objects.equals(extra.hoverEvent, element.hoverEvent))
            {
                extra.hoverEvent = null;
            }
            if (Objects.equals(extra.clickEvent, element.clickEvent))
            {
                extra.clickEvent = null;
            }
        }
    }

    @SuppressWarnings("unchecked")
    static <E extends BaseComponentElement<E, V, I>, V extends ChatMessageEvent, I extends V> void copyStyleToExtra(E element)
    {
        if (element.extra == null)
        {
            return;
        }
        for (E extra : element.extra)
        {
            if (extra.bold == null)
            {
                extra.bold = element.bold;
            }
            if (extra.italic == null)
            {
                extra.italic = element.italic;
            }
            if (extra.obfuscated == null)
            {
                extra.obfuscated = element.obfuscated;
            }
            if (extra.strikethrough == null)
            {
                extra.strikethrough = element.strikethrough;
            }
            if (extra.underlined == null)
            {
                extra.underlined = element.underlined;
            }
            if (extra.color == null)
            {
                extra.color = element.color;
            }

            if (extra.insertion == null)
            {
                extra.insertion = (element.insertion == null) ? null : (I) element.insertion.duplicate();
            }
            if (extra.hoverEvent == null)
            {
                extra.hoverEvent = (element.hoverEvent == null) ? null : (V) element.hoverEvent.duplicate();
            }
            if (extra.clickEvent == null)
            {
                extra.clickEvent = (element.clickEvent == null) ? null : (V) element.clickEvent.duplicate();
            }
            copyStyleToExtra(extra);
        }
    }

    static <E extends BaseComponentElement<E, V, I>, V extends ChatMessageEvent, I extends V> boolean hasThisSameFormatting(E elementA, E elementB)
    {
        if (elementA.bold != elementB.bold)
        {
            return false;
        }
        if (elementA.italic != elementB.italic)
        {
            return false;
        }
        if (elementA.obfuscated != elementB.obfuscated)
        {
            return false;
        }
        if (elementA.strikethrough != elementB.strikethrough)
        {
            return false;
        }
        if (elementA.underlined != elementB.underlined)
        {
            return false;
        }
        if (elementA.color != elementB.color)
        {
            return false;
        }
        if (! Objects.equals(elementA.clickEvent, elementB.clickEvent))
        {
            return false;
        }
        if (! Objects.equals(elementA.hoverEvent, elementB.hoverEvent))
        {
            return false;
        }
        return Objects.equals(elementA.insertion, elementB.insertion);
    }
}
