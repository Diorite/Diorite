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

    @Nullable
    public ELEMENT getParent()
    {
        return this.parent;
    }

    public void setParent(@Nullable ELEMENT parent)
    {
        this.parent = parent;
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

    public void setText(@Nullable String text)
    {
        this.text = text;
        this.translate = null;
        this.with = null;
        this.selector = null;
        this.score = null;
    }

    @Nullable
    public List<ELEMENT> getExtra()
    {
        return this.extra;
    }

    public void setExtra(@Nullable List<ELEMENT> extra)
    {
        this.extra = extra;
    }

    @SuppressWarnings("unchecked")
    public void addExtra(ELEMENT element)
    {
        if (this.extra == null)
        {
            this.extra = new ArrayList<>(1);
        }
        this.extra.add(element);
        element.setParent((ELEMENT) this);
    }

    @Nullable
    public String getTranslate()
    {
        return this.translate;
    }

    public void setTranslate(@Nullable String translate)
    {
        this.translate = translate;
        this.text = null;
        this.with = null;
        this.selector = null;
        this.score = null;
    }

    @Nullable
    public List<Object> getWith()
    {
        return this.with;
    }

    public void setWith(@Nullable List<Object> with)
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
    }

    @Nullable
    public String getSelector()
    {
        return this.selector;
    }

    public void setSelector(@Nullable String selector)
    {
        this.selector = selector;
        this.text = null;
        this.with = null;
        this.translate = null;
        this.score = null;
    }

    @Nullable
    public ChatColor getRawColor()
    {
        return this.color;
    }

    public ChatColor getColor()
    {
        BaseComponentElement<ELEMENT, EVENT, INSERT> other = this;
        while (true)
        {
            if (other.color == null)
            {
                if (other.parent == null)
                {
                    return ChatColor.WHITE;
                }
                other = other.parent;
                continue;
            }
            return other.color;
        }
    }

    public void setColor(@Nullable ChatColor color)
    {
        if ((color != null) && (color == this.getColor()))
        {
            this.color = null;
        }
        this.color = color;
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

    @Nullable
    public Boolean getRawBold()
    {
        return this.bold;
    }

    public void setBold(@Nullable Boolean bold)
    {
        if ((bold != null) && (bold == this.isBold()))
        {
            this.bold = null;
        }
        this.bold = bold;
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

    @Nullable
    public Boolean getRawUnderlined()
    {
        return this.underlined;
    }

    public void setUnderlined(@Nullable Boolean underlined)
    {
        if ((underlined != null) && (underlined == this.isUnderlined()))
        {
            this.underlined = null;
        }
        this.underlined = underlined;
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

    @Nullable
    public Boolean getRawItalic()
    {
        return this.italic;
    }

    public void setItalic(@Nullable Boolean italic)
    {
        if ((italic != null) && (italic == this.isItalic()))
        {
            this.italic = null;
        }
        this.italic = italic;
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

    @Nullable
    public Boolean getRawStrikethrough()
    {
        return this.strikethrough;
    }

    public void setStrikethrough(@Nullable Boolean strikethrough)
    {
        if ((strikethrough != null) && (strikethrough == this.isStrikethrough()))
        {
            this.strikethrough = null;
        }
        this.strikethrough = strikethrough;
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

    @Nullable
    public Boolean getRawObfuscated()
    {
        return this.obfuscated;
    }

    public void setObfuscated(@Nullable Boolean obfuscated)
    {
        if ((obfuscated != null) && (obfuscated == this.isObfuscated()))
        {
            this.obfuscated = null;
        }
        this.obfuscated = obfuscated;
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

    @Nullable
    public INSERT getRawInsertion()
    {
        return this.insertion;
    }

    public void setInsertion(@Nullable INSERT insertion)
    {
        this.insertion = insertion;
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

    @Nullable
    public EVENT getRawHoverEvent()
    {
        return this.hoverEvent;
    }

    public void setHoverEvent(@Nullable EVENT hoverEvent)
    {
        this.hoverEvent = hoverEvent;
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

    @Nullable
    public EVENT getRawClickEvent()
    {
        return this.clickEvent;
    }

    public void setClickEvent(@Nullable EVENT clickEvent)
    {
        this.clickEvent = clickEvent;
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

        copy.text = this.text;
        copy.insertion = this.insertion;
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
}
