/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.chat.component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.ChatColor;

/**
 * Base class for all chat component API elements.
 */
public abstract class BaseComponent extends ReplacableComponent
{
    /**
     * parent node of this node.
     */
    protected BaseComponent       parent;
    /**
     * color of component, var may be null.
     */
    protected ChatColor           color;
    /**
     * if component use bold style, may be null.
     */
    protected Boolean             bold;
    /**
     * if component use italic style, may be null.
     */
    protected Boolean             italic;
    /**
     * if component use underlined style, may be null.
     */
    protected Boolean             underlined;
    /**
     * if component use strikethrough style, may be null.
     */
    protected Boolean             strikethrough;
    /**
     * if component use obfuscated style, may be null.
     */
    protected Boolean             obfuscated;
    /**
     * extra/next elements appended after this element.
     */
    protected List<BaseComponent> extra;
    /**
     * Click event for this component, may be null.
     */
    protected ClickEvent          clickEvent;
    /**
     * Hover event for this component, may be null.
     */
    protected HoverEvent          hoverEvent;

    /**
     * Construct new BaseComponent as copy of old one.
     *
     * @param old component to copy.
     */
    BaseComponent(final BaseComponent old)
    {
        this.color = old.getColorRaw();
        this.bold = old.isBoldRaw();
        this.italic = old.isItalicRaw();
        this.underlined = old.isUnderlinedRaw();
        this.strikethrough = old.isStrikethroughRaw();
        this.obfuscated = old.isObfuscatedRaw();
        this.clickEvent = (old.clickEvent == null) ? null : old.clickEvent.duplicate();
        this.hoverEvent = (old.hoverEvent == null) ? null : old.hoverEvent.duplicate();
        if (old.extra != null)
        {
            this.extra = new ArrayList<>(old.extra.size());
            this.extra.addAll(old.extra.stream().map(BaseComponent::duplicate).collect(Collectors.toList()));
        }
    }

    /**
     * Construct new empty base component.
     */
    public BaseComponent()
    {
    }

    @Override
    protected int replace_(final String text, final BaseComponent component, int limit)
    {
        if (this.extra != null)
        {
            for (final BaseComponent bs : this.extra)
            {
                limit = bs.replace_(text, component, limit);
                if (limit == 0)
                {
                    return 0;
                }
            }
        }
        if (this.hoverEvent != null)
        {
            limit = this.hoverEvent.replace_(text, component, limit);
            if (limit == 0)
            {
                return 0;
            }
        }
        if (this.clickEvent != null)
        {
            limit = this.clickEvent.replace_(text, component, limit);
            if (limit == 0)
            {
                return 0;
            }
        }
        return limit;
    }

    @Override
    protected int replace_(final String text, final String repl, int limit)
    {
        if (this.extra != null)
        {
            for (final BaseComponent bs : this.extra)
            {
                limit = bs.replace_(text, repl, limit);
                if (limit == 0)
                {
                    return 0;
                }
            }
        }
        if (this.hoverEvent != null)
        {
            limit = this.hoverEvent.replace_(text, repl, limit);
            if (limit == 0)
            {
                return 0;
            }
        }
        if (this.clickEvent != null)
        {
            limit = this.clickEvent.replace_(text, repl, limit);
            if (limit == 0)
            {
                return 0;
            }
        }
        return limit;
    }

    /**
     * Returns parent node for this chat node, may be null.
     *
     * @return parent node for this chat node, may be null.
     */
    public BaseComponent getParent()
    {
        return this.parent;
    }

    /**
     * Set parent node for this chat node, may be null.
     *
     * @param parent new parent node for this chat node, may be null.
     */
    public void setParent(final BaseComponent parent)
    {
        this.parent = parent;
    }

    /**
     * Returns list of extra/next elements appended after this one.
     *
     * @return list of extra/next elements appended after this one.
     */
    public List<BaseComponent> getExtra()
    {
        return this.extra;
    }

    /**
     * Set list of extra/next elements appended after this one.
     *
     * @param components new list of extra/next elements.
     */
    public void setExtra(final List<BaseComponent> components)
    {
        for (final BaseComponent component : components)
        {
            component.parent = this;
        }
        this.extra = new ArrayList<>(components);
    }

    /**
     * Returns click event for this node, may be null.
     *
     * @return click event for this node, may be null.
     */
    public ClickEvent getClickEvent()
    {
        return this.clickEvent;
    }

    /**
     * Set click event for this node, may be null.
     *
     * @param clickEvent click event for this node, may be null.
     */
    public void setClickEvent(final ClickEvent clickEvent)
    {
        this.clickEvent = clickEvent;
    }

    /**
     * Returns hover event for this node, may be null.
     *
     * @return hover event for this node, may be null.
     */
    public HoverEvent getHoverEvent()
    {
        return this.hoverEvent;
    }

    /**
     * Set hover event for this node, may be null.
     *
     * @param hoverEvent new hover event for this node, may be null.
     */
    public void setHoverEvent(final HoverEvent hoverEvent)
    {
        this.hoverEvent = hoverEvent;
    }

    /**
     * Returns color of this node, if color of this node is null it will return color of parent node,
     * if it is also null it will return default, {@link ChatColor#WHITE} color.
     *
     * @return color of this node.
     */
    public ChatColor getColor()
    {
        if (this.color == null)
        {
            if (this.parent == null)
            {
                return ChatColor.WHITE;
            }
            return this.parent.getColor();
        }
        return this.color;
    }

    /**
     * Set color of this node.
     *
     * @param color new color of this node.
     */
    public void setColor(final ChatColor color)
    {
        this.color = color;
    }

    /**
     * Returns raw color of this node, may be null. <br>
     * Even if this method return null node can still be colored as it may inherit color from parent.
     *
     * @return color of this node, may be null.
     *
     * @see #getColor()
     */
    public ChatColor getColorRaw()
    {
        return this.color;
    }

    /**
     * Check if this node use bold style, it will check value of this node and parent nodes.
     *
     * @return true if this node use bold style.
     */
    public boolean isBold()
    {
        if (this.bold == null)
        {
            return (this.parent != null) && (this.parent.isBold());
        }
        return this.bold;
    }

    /**
     * Set bold flag of this node, may be null.
     *
     * @param bold new bold flag of this node, may be null.
     */
    public void setBold(final Boolean bold)
    {
        this.bold = bold;
    }

    /**
     * Returns bold flag of this node, may be null. <br>
     * Even if this method return null node can still be bold as it may inherit style from parent.
     *
     * @return bold flag of this node, may be null.
     */
    public Boolean isBoldRaw()
    {
        return this.bold;
    }

    /**
     * Check if this node use italic style, it will check value of this node and parent nodes.
     *
     * @return true if this node use italic style.
     */
    public boolean isItalic()
    {
        if (this.italic == null)
        {
            return (this.parent != null) && (this.parent.isItalic());
        }
        return this.italic;
    }

    /**
     * Set italic flag of this node, may be null.
     *
     * @param italic new italic flag of this node, may be null.
     */
    public void setItalic(final Boolean italic)
    {
        this.italic = italic;
    }

    /**
     * Returns italic flag of this node, may be null. <br>
     * Even if this method return null node can still be italic as it may inherit style from parent.
     *
     * @return italic flag of this node, may be null.
     */
    public Boolean isItalicRaw()
    {
        return this.italic;
    }

    /**
     * Check if this node use underlined style, it will check value of this node and parent nodes.
     *
     * @return true if this node use underlined style.
     */
    public boolean isUnderlined()
    {
        if (this.underlined == null)
        {
            return (this.parent != null) && (this.parent.isUnderlined());
        }
        return this.underlined;
    }

    /**
     * Set underlined flag of this node, may be null.
     *
     * @param underlined new underlined flag of this node, may be null.
     */
    public void setUnderlined(final Boolean underlined)
    {
        this.underlined = underlined;
    }

    /**
     * Returns underlined flag of this node, may be null. <br>
     * Even if this method return null node can still be underlined as it may inherit style from parent.
     *
     * @return underlined flag of this node, may be null.
     */
    public Boolean isUnderlinedRaw()
    {
        return this.underlined;
    }

    /**
     * Check if this node use strikethrough style, it will check value of this node and parent nodes.
     *
     * @return true if this node use strikethrough style.
     */
    public boolean isStrikethrough()
    {
        if (this.strikethrough == null)
        {
            return (this.parent != null) && (this.parent.isStrikethrough());
        }
        return this.strikethrough;
    }

    /**
     * Set strikethrough flag of this node, may be null.
     *
     * @param strikethrough new strikethrough flag of this node, may be null.
     */
    public void setStrikethrough(final Boolean strikethrough)
    {
        this.strikethrough = strikethrough;
    }

    /**
     * Returns strikethrough flag of this node, may be null. <br>
     * Even if this method return null node can still be strikethrough as it may inherit style from parent.
     *
     * @return strikethrough flag of this node, may be null.
     */
    public Boolean isStrikethroughRaw()
    {
        return this.strikethrough;
    }

    /**
     * Check if this node use obfuscated style, it will check value of this node and parent nodes.
     *
     * @return true if this node use obfuscated style.
     */
    public boolean isObfuscated()
    {
        if (this.obfuscated == null)
        {
            return (this.parent != null) && (this.parent.isObfuscated());
        }
        return this.obfuscated;
    }

    /**
     * Set obfuscated flag of this node, may be null.
     *
     * @param obfuscated new obfuscated flag of this node, may be null.
     */
    public void setObfuscated(final Boolean obfuscated)
    {
        this.obfuscated = obfuscated;
    }

    /**
     * Returns obfuscated flag of this node, may be null. <br>
     * Even if this method return null node can still be obfuscated as it may inherit style from parent.
     *
     * @return obfuscated flag of this node, may be null.
     */
    public Boolean isObfuscatedRaw()
    {
        return this.obfuscated;
    }

    /**
     * Add new {@link TextComponent} to this node.
     *
     * @param text new string to add.
     */
    public void addExtra(final String text)
    {
        this.addExtra(new TextComponent(text));
    }

    /**
     * Add new {@link BaseComponent} to this node.
     *
     * @param component new component to add.
     */
    public void addExtra(final BaseComponent component)
    {
        if (this.extra == null)
        {
            this.extra = new ArrayList<>(5);
        }
        component.parent = this;
        this.extra.add(component);
    }

    /**
     * Returns true if this node have any formatting settings.
     *
     * @return true if this node have any formatting settings.
     */
    public boolean hasFormatting()
    {
        return (this.color != null) || (this.bold != null) || (this.italic != null) || (this.underlined != null) || (this.strikethrough != null) || (this.obfuscated != null) || (this.hoverEvent != null) || (this.clickEvent != null);
    }

    /**
     * Returns string contains this component as plain text. (without style)
     *
     * @return string contains this component as plain text. (without style)
     */
    public String toPlainText()
    {
        final StringBuilder builder = new StringBuilder();
        this.toPlainText(builder);
        return builder.toString();
    }

    void toPlainText(final StringBuilder builder)
    {
        if (this.extra != null)
        {
            for (final BaseComponent e : this.extra)
            {
                e.toPlainText(builder);
            }
        }
    }

    /**
     * Returns string contains this component as legacy text. (without events etc, but with colors)
     *
     * @return string contains this component as legacy text. (without events etc, but with colors)
     */
    public String toLegacyText()
    {
        final StringBuilder builder = new StringBuilder();
        this.toLegacyText(builder);
        return builder.toString();
    }

    void toLegacyText(final StringBuilder builder)
    {
        if (this.extra != null)
        {
            for (final BaseComponent e : this.extra)
            {
                e.toLegacyText(builder);
            }
        }
    }

    @Override
    public abstract BaseComponent duplicate();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("parent", this.parent).append("color", this.color).append("bold", this.bold).append("italic", this.italic).append("underlined", this.underlined).append("strikethrough", this.strikethrough).append("obfuscated", this.obfuscated).append("extra", this.extra).append("clickEvent", this.clickEvent).append("hoverEvent", this.hoverEvent).toString();
    }

    /**
     * Returns string contains given components as legacy text. (without events etc, but with colors)
     *
     * @param components components to convert to legacy text.
     *
     * @return string contains given components as legacy text. (without events etc, but with colors)
     */
    public static String toLegacyText(final BaseComponent... components)
    {
        final StringBuilder builder = new StringBuilder();
        for (final BaseComponent msg : components)
        {
            builder.append(msg.toLegacyText());
        }
        return builder.toString();
    }

    /**
     * Returns string contains given components as plain text. (without style)
     *
     * @param components components to convert to plain text.
     *
     * @return string contains given components as plain text. (without style)
     */
    public static String toPlainText(final BaseComponent... components)
    {
        final StringBuilder builder = new StringBuilder();
        for (final BaseComponent msg : components)
        {
            builder.append(msg.toPlainText());
        }
        return builder.toString();
    }
}
