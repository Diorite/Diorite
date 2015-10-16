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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.ChatColor;

/**
 * Builder class for easier building chat messages.
 */
public class ComponentBuilder
{
    private final TextComponent parts = new TextComponent("");
    private BaseComponent current;

    /**
     * Construct new ComponentBuilder as copy of old one.
     *
     * @param original ComponentBuilder to copy.
     */
    protected ComponentBuilder(final ComponentBuilder original)
    {
        this.current = new TextComponent(original.current);
        original.parts.getExtra().stream().map(BaseComponent::duplicate).forEach(this.parts::addExtra);
    }

    /**
     * Construct new ComponentBuilder strating from given string ({@link TextComponent}).
     *
     * @param text first element of message.
     */
    protected ComponentBuilder(final String text)
    {
        this.current = new TextComponent(text);
    }

    /**
     * Add previously edited component to other parts, and set given component as current one.
     *
     * @param component new component to edit and add.
     *
     * @return this same ComponentBuilder for method chains.
     */
    public ComponentBuilder append(final BaseComponent component)
    {
        this.parts.addExtra(this.current);
        this.current = component;
        return this;
    }

    /**
     * Add previously edited component to other parts, and set given component as current one.
     *
     * @param text new component to edit and add as legacy string. {@link TextComponent#fromLegacyText(String)}
     *
     * @return this same ComponentBuilder for method chains.
     */
    public ComponentBuilder appendLegacy(final String text)
    {
        this.parts.addExtra(this.current);
        this.current = TextComponent.fromLegacyText(text);
        return this;
    }

    /**
     * Add previously edited component to other parts, and set given component as current one.
     *
     * @param text new component to edit and add as string.
     *
     * @return this same ComponentBuilder for method chains.
     */
    public ComponentBuilder append(final String text)
    {
        this.parts.addExtra(this.current);
        this.current = new TextComponent(text);
        return this;
    }

    /**
     * Set color of current element to given one, may be null.
     *
     * @param color color to use, may be null.
     *
     * @return this same ComponentBuilder for method chains.
     */
    public ComponentBuilder color(final ChatColor color)
    {
        this.current.setColor(color);
        return this;
    }

    /**
     * Set bold style flag of current element to given one, may be null.
     *
     * @param bold bold style flag to use, may be null.
     *
     * @return this same ComponentBuilder for method chains.
     */
    public ComponentBuilder bold(final Boolean bold)
    {
        this.current.setBold(bold);
        return this;
    }

    /**
     * Set italic style flag of current element to given one, may be null.
     *
     * @param italic italic style flag to use, may be null.
     *
     * @return this same ComponentBuilder for method chains.
     */
    public ComponentBuilder italic(final Boolean italic)
    {
        this.current.setItalic(italic);
        return this;
    }

    /**
     * Set underlined style flag of current element to given one, may be null.
     *
     * @param underlined underlined style flag to use, may be null.
     *
     * @return this same ComponentBuilder for method chains.
     */
    public ComponentBuilder underlined(final Boolean underlined)
    {
        this.current.setUnderlined(underlined);
        return this;
    }

    /**
     * Set strikethrough style flag of current element to given one, may be null.
     *
     * @param strikethrough strikethrough style flag to use, may be null.
     *
     * @return this same ComponentBuilder for method chains.
     */
    public ComponentBuilder strikethrough(final Boolean strikethrough)
    {
        this.current.setStrikethrough(strikethrough);
        return this;
    }

    /**
     * Set obfuscated style flag of current element to given one, may be null.
     *
     * @param obfuscated obfuscated style flag to use, may be null.
     *
     * @return this same ComponentBuilder for method chains.
     */
    public ComponentBuilder obfuscated(final Boolean obfuscated)
    {
        this.current.setObfuscated(obfuscated);
        return this;
    }

    /**
     * Set click event of current element to given one, may be null.
     *
     * @param clickEvent click event to use, may be null.
     *
     * @return this same ComponentBuilder for method chains.
     */
    public ComponentBuilder event(final ClickEvent clickEvent)
    {
        this.current.setClickEvent(clickEvent);
        return this;
    }

    /**
     * Set hover event of current element to given one, may be null.
     *
     * @param hoverEvent hover event to use, may be null.
     *
     * @return this same ComponentBuilder for method chains.
     */
    public ComponentBuilder event(final HoverEvent hoverEvent)
    {
        this.current.setHoverEvent(hoverEvent);
        return this;
    }

    /**
     * Finish builder, and create {@link TextComponent} with all created parts.
     *
     * @return builded {@link TextComponent}.
     */
    public TextComponent create()
    {
        this.parts.addExtra(this.current);
        return this.parts;
    }

    /**
     * Construct new ComponentBuilder strating from given string ({@link TextComponent}).
     *
     * @param text first element of message.
     *
     * @return new ComponentBuilder instance.
     */
    public static ComponentBuilder start(final String text)
    {
        return new ComponentBuilder(text);
    }

    /**
     * Construct new ComponentBuilder as copy of old one.
     *
     * @param original ComponentBuilder to copy.
     *
     * @return new ComponentBuilder instance.
     */
    public static ComponentBuilder start(final ComponentBuilder original)
    {
        return new ComponentBuilder(original);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("parts", this.parts).append("current", this.current).toString();
    }
}
