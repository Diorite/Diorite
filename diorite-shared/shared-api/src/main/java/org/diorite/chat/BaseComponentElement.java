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

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

abstract class BaseComponentElement<ELEMENT extends BaseComponentElement<ELEMENT, EVENT, INSERT>, EVENT extends ChatMessageEvent, INSERT extends EVENT>
{
    @Nullable ELEMENT parent;

    @Nullable String        text;
    @Nullable List<ELEMENT> extra;

    @Nullable String       translate;
    @Nullable List<Object> with;

    @Nullable ChatScore score;
    @Nullable String    selector;

    @Nullable ChatColor color;
    @Nullable Boolean   bold;
    @Nullable Boolean   underlined;
    @Nullable Boolean   italic;
    @Nullable Boolean   strikethrough;
    @Nullable Boolean   obfuscated;

    @Nullable INSERT insertion;
    @Nullable EVENT  hoverEvent;
    @Nullable EVENT  clickEvent;

    /**
     * Changes structure of component to reduce amount of nodes.
     */
    public ELEMENT optimize()
    {
        return this.optimize(this.getThis());
    }

    final boolean optimizeFirstChild()
    {
        if (((this.parent != null) && (this.parent.extra != null)) && (this.text != null) && ! this.hasFormatting() && (this.parent.extra.indexOf(this) == 0))
        {
            this.parent.text += this.text;
            this.text = "";
            this.parent.extra.remove(this);
            if (this.extra != null)
            {
                this.parent.extra.addAll(0, this.extra);
                for (ELEMENT element : this.extra)
                {
                    element.parent = this.parent;
                }
            }
            this.parent = null;
            return true;
        }
        return false;
    }

    final void optimizeSingleExtraInstance(ELEMENT root)
    {
        if ((this.text != null) || (this.translate != null))
        {
            if (this.extra != null)
            {
                for (ELEMENT element : new ArrayList<>(this.extra))
                {
                    element.optimize(root);
                }
                if (this.extra.size() == 1)
                {
                    ELEMENT extra = this.extra.get(0);
                    if (! extra.hasFormatting())
                    {
                        this.text = extra.text;
                        this.translate = extra.translate;
                        this.with = extra.with;
                        this.extra = null;
                    }
                }
            }
        }
    }

    final boolean optimizeWhitespaceByChild()
    {
        if (this.parent == null)
        {
            return false;
        }
        if ((this.extra != null) && ! this.extra.isEmpty())
        {
            ELEMENT extra = this.extra.get(0);
            if ((this.isObfuscated() == extra.isObfuscated()) && (this.isStrikethrough() == extra.isStrikethrough()) &&
                (this.isUnderlined() == extra.isUnderlined()))
            {
                extra.text = this.text + extra.text;
                this.text = "";
                if ((this.extra == null) || this.extra.isEmpty())
                {
                    assert this.parent.extra != null; // can't be null if we are child of it.
                    this.parent.extra.remove(this);
                    this.parent = null;
                }
                return true;
            }
        }
        return false;
    }

    final boolean optimizeWhitespaceByRightNeighborhood(int indexOf)
    {
        assert (this.parent != null) && (this.parent.extra != null); // we are a child of it.
        if (this.parent.extra.size() > (indexOf + 1))
        {
            ELEMENT element = this.parent.extra.get(indexOf + 1);
            if ((element.text != null))
            {
                if ((this.isObfuscated() == element.isObfuscated()) && (this.isStrikethrough() == element.isStrikethrough()) &&
                    (this.isUnderlined() == element.isUnderlined()))
                {
                    element.text = this.text + element.text;
                    this.text = "";
                    if ((this.extra == null) || this.extra.isEmpty())
                    {
                        this.parent.extra.remove(this);
                        this.parent = null;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    final boolean optimizeWhitespaceByLeftNeighborhood(int indexOf)
    {
        assert (this.parent != null) && (this.parent.extra != null); // we are a child of it.
        ELEMENT element = this.parent.extra.get(indexOf - 1);
        if (element.text != null)
        {
            if ((this.isObfuscated() == element.isObfuscated()) && (this.isStrikethrough() == element.isStrikethrough()) &&
                (this.isUnderlined() == element.isUnderlined()))
            {
                element.text += this.text;
                this.text = "";
                if ((this.extra == null) || this.extra.isEmpty())
                {
                    this.parent.extra.remove(this);
                    this.parent = null;
                }
                return true;
            }
        }
        return false;
    }

    final boolean optimizeWhitespaceByParent()
    {
        assert (this.parent != null) && (this.parent.extra != null); // we are a child of it.
        if ((this.parent.text != null))
        {
            if ((this.isObfuscated() == this.parent.isObfuscated()) && (this.isStrikethrough() == this.parent.isStrikethrough()) &&
                (this.isUnderlined() == this.parent.isUnderlined()))
            {
                this.parent.text += this.text;
                this.text = "";
                if ((this.extra == null) || this.extra.isEmpty())
                {
                    this.parent.extra.remove(this);
                    this.parent = null;
                }
                return true;
            }
        }
        return false;
    }

    final boolean optimizeWhitespaces()
    {
        boolean repeat = false;
        if ((this.text != null) && ! this.text.isEmpty() && this.text.trim().isEmpty())
        {
            if ((this.parent != null))
            {
                assert this.parent.extra != null; // can't be null if there are children!
                int indexOf = this.parent.extra.indexOf(this);
                assert indexOf >= 0;
                if (indexOf == 0)
                {
                    repeat = this.optimizeWhitespaceByParent();
                }
                else
                {
                    repeat = this.optimizeWhitespaceByLeftNeighborhood(indexOf) || this.optimizeWhitespaceByRightNeighborhood(indexOf);
                }
            }
            if (! repeat)
            {
                repeat = this.optimizeWhitespaceByChild();
            }
        }
        return repeat;
    }

    ELEMENT optimize(ELEMENT root)
    {
        ELEMENT other = this.getThis();
        while (true)
        {
            if ((other.parent == null) && (root != other))
            {
                return other.getThis();
            }
            boolean repeat = other.optimizeFirstChild();
            if (! repeat)
            {
                other.optimizeSingleExtraInstance(root);
            }
            if (! repeat)
            {
                repeat = other.optimizeWhitespaces();
            }
            if (repeat)
            {
                other = root;
                continue;
            }
            return other.getThis();
        }
    }

    @Nullable
    public ELEMENT getParent()
    {
        return this.parent;
    }

    public ELEMENT setParent(@Nullable ELEMENT parent)
    {
        this.parent = parent;
        return this.getThis();
    }

    @SuppressWarnings("unchecked")
    protected ELEMENT getThis()
    {
        return (ELEMENT) this;
    }

    /**
     * Returns true if this base component can be changed to legacy string without removing any features. <br>
     * Method just check if this component use any events that can't be used in legacy chat format.
     *
     * @return true if this base component can be changed to legacy string without removing any features.
     */
    public boolean canBeLegacy()
    {
        if (this.text == null)
        {
            return false;
        }
        if (this.clickEvent != null)
        {
            return false;
        }
        if (this.hoverEvent != null)
        {
            return false;
        }
        if (this.insertion != null)
        {
            return false;
        }
        List<ELEMENT> extra = this.extra;
        if ((extra == null) || extra.isEmpty())
        {
            return true;
        }
        for (ELEMENT component : extra)
        {
            if (! component.canBeLegacy())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if this node have any formatting settings.
     *
     * @return true if this node have any formatting settings.
     */
    public boolean hasFormatting()
    {
        return (this.text == null) || (this.color != null) || (this.bold != null) || (this.italic != null) || (this.underlined != null) ||
               (this.strikethrough != null) || (this.obfuscated != null) || (this.hoverEvent != null) || (this.clickEvent != null) || (this.insertion != null);
    }

    @Nullable
    public String getText()
    {
        return this.text;
    }

    public ELEMENT setText(@Nullable String text)
    {
        this.text = text;
        this.translate = null;
        this.with = null;
        this.selector = null;
        this.score = null;
        return this.getThis();
    }

    @Nullable
    public List<ELEMENT> getExtra()
    {
        return this.extra;
    }

    public ELEMENT setExtra(@Nullable List<ELEMENT> extra)
    {
        this.extra = extra;
        return this.getThis();
    }

    @SuppressWarnings("unchecked")
    public ELEMENT addExtra(ELEMENT element)
    {
        if (this.extra == null)
        {
            this.extra = new ArrayList<>(1);
        }
        this.extra.add(element);
        element.setParent((ELEMENT) this);
        return this.getThis();
    }

    @Nullable
    public String getTranslate()
    {
        return this.translate;
    }

    public ELEMENT setTranslate(@Nullable String translate)
    {
        this.translate = translate;
        this.text = null;
        this.with = null;
        this.selector = null;
        this.score = null;
        return this.getThis();
    }

    @Nullable
    public List<Object> getWith()
    {
        return this.with;
    }

    public ELEMENT setWith(@Nullable List<Object> with)
    {
        this.with = with;
        if (this.translate == null)
        {
            this.translate = StringUtils.EMPTY;
        }
        this.text = null;
        this.with = null;
        this.selector = null;
        this.score = null;
        return this.getThis();
    }

    @Nullable
    public String getSelector()
    {
        return this.selector;
    }

    public ELEMENT setSelector(@Nullable String selector)
    {
        this.selector = selector;
        this.text = null;
        this.with = null;
        this.translate = null;
        this.score = null;
        return this.getThis();
    }

    @Nullable
    public ChatColor getRawColor()
    {
        return this.color;
    }

    @Nullable
    public ChatColor getColor()
    {
        BaseComponentElement<ELEMENT, EVENT, INSERT> other = this;
        while (true)
        {
            if (other.color == null)
            {
                if (other.parent == null)
                {
                    return null;
                }
                other = other.parent;
                continue;
            }
            return other.color;
        }
    }

    public ELEMENT setColor(@Nullable ChatColor color)
    {
        if ((color != null) && (color == this.getColor()))
        {
            this.color = null;
        }
        this.color = color;
        return this.getThis();
    }

    public boolean isBold()
    {
        BaseComponentElement<ELEMENT, EVENT, INSERT> other = this;
        while (true)
        {
            if (other.bold == null)
            {
                if (other.parent == null)
                {
                    return false;
                }
                other = other.parent;
                continue;
            }
            return other.bold;
        }
    }

    public ELEMENT setBold(@Nullable Boolean bold)
    {
        if ((bold != null) && (bold == this.isBold()))
        {
            this.bold = null;
        }
        this.bold = bold;
        return this.getThis();
    }

    @Nullable
    public Boolean getRawBold()
    {
        return this.bold;
    }

    public boolean isUnderlined()
    {
        BaseComponentElement<ELEMENT, EVENT, INSERT> other = this;
        while (true)
        {
            if (other.underlined == null)
            {
                if (other.parent == null)
                {
                    return false;
                }
                other = other.parent;
                continue;
            }
            return other.underlined;
        }
    }

    public ELEMENT setUnderlined(@Nullable Boolean underlined)
    {
        if ((underlined != null) && (underlined == this.isUnderlined()))
        {
            this.underlined = null;
        }
        this.underlined = underlined;
        return this.getThis();
    }

    @Nullable
    public Boolean getRawUnderlined()
    {
        return this.underlined;
    }

    public boolean isItalic()
    {
        BaseComponentElement<ELEMENT, EVENT, INSERT> other = this;
        while (true)
        {
            if (other.italic == null)
            {
                if (other.parent == null)
                {
                    return false;
                }
                other = other.parent;
                continue;
            }
            return other.italic;
        }
    }

    public ELEMENT setItalic(@Nullable Boolean italic)
    {
        if ((italic != null) && (italic == this.isItalic()))
        {
            this.italic = null;
        }
        this.italic = italic;
        return this.getThis();
    }

    @Nullable
    public Boolean getRawItalic()
    {
        return this.italic;
    }

    public boolean isStrikethrough()
    {
        BaseComponentElement<ELEMENT, EVENT, INSERT> other = this;
        while (true)
        {
            if (other.strikethrough == null)
            {
                if (other.parent == null)
                {
                    return false;
                }
                other = other.parent;
                continue;
            }
            return other.strikethrough;
        }
    }

    public ELEMENT setStrikethrough(@Nullable Boolean strikethrough)
    {
        if ((strikethrough != null) && (strikethrough == this.isStrikethrough()))
        {
            this.strikethrough = null;
        }
        this.strikethrough = strikethrough;
        return this.getThis();
    }

    @Nullable
    public Boolean getRawStrikethrough()
    {
        return this.strikethrough;
    }

    public boolean isObfuscated()
    {
        BaseComponentElement<ELEMENT, EVENT, INSERT> other = this;
        while (true)
        {
            if (other.obfuscated == null)
            {
                if (other.parent == null)
                {
                    return false;
                }
                other = other.parent;
                continue;
            }
            return other.obfuscated;
        }
    }

    public ELEMENT setObfuscated(@Nullable Boolean obfuscated)
    {
        if ((obfuscated != null) && (obfuscated == this.isObfuscated()))
        {
            this.obfuscated = null;
        }
        this.obfuscated = obfuscated;
        return this.getThis();
    }

    @Nullable
    public Boolean getRawObfuscated()
    {
        return this.obfuscated;
    }

    @Nullable
    public INSERT getInsertion()
    {
        BaseComponentElement<ELEMENT, EVENT, INSERT> other = this;
        while (true)
        {
            if (other.insertion == null)
            {
                if (other.parent == null)
                {
                    return null;
                }
                other = other.parent;
                continue;
            }
            return other.insertion;
        }
    }

    public ELEMENT setInsertion(@Nullable INSERT insertion)
    {
        this.insertion = insertion;
        return this.getThis();
    }

    @Nullable
    public INSERT getRawInsertion()
    {
        return this.insertion;
    }

    @Nullable
    public EVENT getHoverEvent()
    {
        BaseComponentElement<ELEMENT, EVENT, INSERT> other = this;
        while (true)
        {
            if (other.hoverEvent == null)
            {
                if (other.parent == null)
                {
                    return null;
                }
                other = other.parent;
                continue;
            }
            return other.hoverEvent;
        }
    }

    public ELEMENT setHoverEvent(@Nullable EVENT hoverEvent)
    {
        this.hoverEvent = hoverEvent;
        return this.getThis();
    }

    @Nullable
    public EVENT getRawHoverEvent()
    {
        return this.hoverEvent;
    }

    @Nullable
    public EVENT getClickEvent()
    {
        BaseComponentElement<ELEMENT, EVENT, INSERT> other = this;
        while (true)
        {
            if (other.clickEvent == null)
            {
                if (other.parent == null)
                {
                    return null;
                }
                other = other.parent;
                continue;
            }
            return other.clickEvent;
        }
    }

    public ELEMENT setClickEvent(@Nullable EVENT clickEvent)
    {
        this.clickEvent = clickEvent;
        return this.getThis();
    }

    @Nullable
    public EVENT getRawClickEvent()
    {
        return this.clickEvent;
    }

    protected abstract ELEMENT createElement();

    @SuppressWarnings("unchecked")
    public ELEMENT duplicate()
    {
        ELEMENT copy = this.createElement();
        copy.bold = this.bold;
        copy.italic = this.italic;
        copy.obfuscated = this.obfuscated;
        copy.strikethrough = this.strikethrough;
        copy.underlined = this.underlined;
        copy.color = this.color;

        copy.insertion = (this.insertion == null) ? null : (INSERT) this.insertion.duplicate();
        copy.hoverEvent = (this.hoverEvent == null) ? null : (EVENT) this.hoverEvent.duplicate();
        copy.clickEvent = (this.clickEvent == null) ? null : (EVENT) this.clickEvent.duplicate();

        copy.score = this.score;
        copy.text = this.text;
        copy.translate = this.translate;

        if (this.extra == null)
        {
            copy.extra = null;
        }
        else
        {
            ArrayList<ELEMENT> objects = new ArrayList<>(this.extra.size());
            for (ELEMENT componentElement : this.extra)
            {
                objects.add(componentElement.duplicate());
            }
            copy.extra = objects;
        }

        if (this.with == null)
        {
            copy.with = null;
        }
        else
        {
            ArrayList<Object> objects = new ArrayList<>(this.with.size());
            for (Object object : this.with)
            {
                if (object instanceof BaseComponentElement)
                {
                    objects.add(((ELEMENT) object).duplicate());
                }
                else
                {
                    objects.add(object.toString());
                }

            }
            copy.with = objects;
        }

        return copy;
    }

    @Override
    public String toString()
    {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString());
        if (this.text != null)
        {
            toStringBuilder.append("text", this.text);
        }
        if ((this.extra != null) && ! this.extra.isEmpty())
        {
            toStringBuilder.append("extra", this.extra);
        }
        if (this.translate != null)
        {
            toStringBuilder.append("translate", this.translate);
        }
        if ((this.with != null) && ! this.with.isEmpty())
        {
            toStringBuilder.append("with", this.with);
        }
        if (this.score != null)
        {
            toStringBuilder.append("score", this.score);
        }
        if (this.selector != null)
        {
            toStringBuilder.append("selector", this.selector);
        }
        if (this.color != null)
        {
            toStringBuilder.append("color", this.color.name().toLowerCase());
        }
        if (this.bold != null)
        {
            toStringBuilder.append("bold", this.bold);
        }
        if (this.underlined != null)
        {
            toStringBuilder.append("underlined", this.underlined);
        }
        if (this.italic != null)
        {
            toStringBuilder.append("italic", this.italic);
        }
        if (this.strikethrough != null)
        {
            toStringBuilder.append("strikethrough", this.strikethrough);
        }
        if (this.obfuscated != null)
        {
            toStringBuilder.append("obfuscated", this.obfuscated);
        }
        if (this.insertion != null)
        {
            toStringBuilder.append("insertion", this.insertion);
        }
        if (this.hoverEvent != null)
        {
            toStringBuilder.append("hoverEvent", this.hoverEvent);
        }
        if (this.clickEvent != null)
        {
            toStringBuilder.append("clickEvent", this.clickEvent);
        }
        return toStringBuilder.toString();
    }
}
