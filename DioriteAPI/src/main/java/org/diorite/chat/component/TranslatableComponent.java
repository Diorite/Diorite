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
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.ChatColor;

/**
 * Represents text components containing translatable string, so every client can see it in own language. (string must be supported by client)
 */
public class TranslatableComponent extends BaseComponent
{
    private static final ResourceBundle locales = ResourceBundle.getBundle("mojang-translations/en_US");
    private static final Pattern        format  = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
    /**
     * String key for this message.
     */
    protected String              translate;
    /**
     * Arguments of this message.
     */
    protected List<BaseComponent> with;

    /**
     * Construct new TranslatableComponent as copy of old one.
     *
     * @param original component to copy.
     */
    public TranslatableComponent(final TranslatableComponent original)
    {
        super(original);
        this.translate = original.getTranslate();
        this.with.addAll(original.getWith().stream().map(BaseComponent::duplicate).collect(Collectors.toList()));
    }

    /**
     * Construct new TranslatableComponent for given key and arguments.
     *
     * @param translate key for this message.
     * @param with      arguments for this message.
     */
    public TranslatableComponent(final String translate, final Object... with)
    {
        this.translate = translate;
        final List<BaseComponent> temp = new ArrayList<>(with.length);
        for (final Object w : with)
        {
            if ((w instanceof String))
            {
                temp.add(new TextComponent((String) w));
            }
            else
            {
                temp.add((BaseComponent) w);
            }
        }
        this.setWith(temp);
    }

    /**
     * Construct new empty TranslatableComponent.
     */
    public TranslatableComponent()
    {
    }

    @Override
    public int replace_(final String text, final BaseComponent component, int limit)
    {
        final int startIndex = this.translate.indexOf(text);
        if (startIndex != - 1)
        {
            final int endIndex = startIndex + text.length();
            this.translate = this.translate.substring(0, startIndex) + component.toLegacyText() + this.translate.substring(endIndex);
            if (-- limit == 0)
            {
                return 0;
            }
        }
        if (this.with != null)
        {
            for (final BaseComponent w : this.with)
            {
                limit = w.replace_(text, component, limit);
                if (limit == 0)
                {
                    return 0;
                }
            }
        }
        return super.replace_(text, component, limit);
    }

    @Override
    public int replace_(final String text, final String repl, int limit)
    {
        final int startIndex = this.translate.indexOf(text);
        if (startIndex != - 1)
        {
            final int endIndex = startIndex + text.length();
            this.translate = this.translate.substring(0, startIndex) + repl + this.translate.substring(endIndex);
            if (-- limit == 0)
            {
                return 0;
            }
        }
        if (this.with != null)
        {
            for (final BaseComponent w : this.with)
            {
                limit = w.replace_(text, repl, limit);
                if (limit == 0)
                {
                    return 0;
                }
            }
        }
        return super.replace_(text, repl, limit);
    }

    /**
     * Returns locales used to represent this component as legacy/plain text.
     *
     * @return locales used to represent this component as legacy/plain text.
     */
    public ResourceBundle getLocales()
    {
        return locales;
    }

    /**
     * Returns Reg-ex pattern for keys.
     *
     * @return Reg-ex pattern for keys.
     */
    public Pattern getFormat()
    {
        return format;
    }

    /**
     * Returns string key for this message.
     *
     * @return string key for this message.
     */
    public String getTranslate()
    {
        return this.translate;
    }

    /**
     * Set string key for this message.
     *
     * @param translate new string key for this message.
     */
    public void setTranslate(final String translate)
    {
        this.translate = translate;
    }

    /**
     * Returns arguments of this message.
     *
     * @return arguments of this message.
     */
    public List<BaseComponent> getWith()
    {
        return this.with;
    }

    /**
     * Set arguments of this message.
     *
     * @param components new arguments of this message.
     */
    public void setWith(final List<BaseComponent> components)
    {
        for (final BaseComponent component : components)
        {
            component.setParent(this);
        }
        this.with = components;
    }

    /**
     * Add new string argument for this message.
     *
     * @param text new string argument.
     */
    public void addWith(final String text)
    {
        this.addWith(new TextComponent(text));
    }

    /**
     * Add new {@link BaseComponent} argument for this message.
     *
     * @param component new argument.
     */
    public void addWith(final BaseComponent component)
    {
        if (this.with == null)
        {
            this.with = new ArrayList<>(4);
        }
        component.setParent(this);
        this.with.add(component);
    }

    private void addFormat(final StringBuilder builder)
    {
        builder.append(this.getColor());
        if (this.isBold())
        {
            builder.append(ChatColor.BOLD);
        }
        if (this.isItalic())
        {
            builder.append(ChatColor.ITALIC);
        }
        if (this.isUnderlined())
        {
            builder.append(ChatColor.UNDERLINE);
        }
        if (this.isStrikethrough())
        {
            builder.append(ChatColor.STRIKETHROUGH);
        }
        if (this.isObfuscated())
        {
            builder.append(ChatColor.MAGIC);
        }
    }

    @Override
    protected void toPlainText(final StringBuilder builder)
    {
        try
        {
            final String trans = locales.getString(this.translate);
            final Matcher matcher = format.matcher(trans);
            int position = 0;
            int i = 0;
            while (matcher.find(position))
            {
                final int pos = matcher.start();
                if (pos != position)
                {
                    builder.append(trans.substring(position, pos));
                }
                position = matcher.end();

                final String formatCode = matcher.group(2);
                switch (formatCode.charAt(0))
                {
                    case 'd':
                    case 's':
                        final String withIndex = matcher.group(1);
                        this.with.get((withIndex != null) ? (Integer.parseInt(withIndex) - 1) : i++).toPlainText(builder);
                        break;
                    case '%':
                        builder.append('%');
                    default:
                        break;
                }
            }
            if (trans.length() != position)
            {
                builder.append(trans.substring(position, trans.length()));
            }
        } catch (final MissingResourceException e)
        {
            builder.append(this.translate);
        }
        super.toPlainText(builder);
    }

    @Override
    protected void toLegacyText(final StringBuilder builder)
    {
        try
        {
            final String trans = locales.getString(this.translate);
            final Matcher matcher = format.matcher(trans);
            int position = 0;
            int i = 0;
            while (matcher.find(position))
            {
                final int pos = matcher.start();
                if (pos != position)
                {
                    this.addFormat(builder);
                    builder.append(trans.substring(position, pos));
                }
                position = matcher.end();

                final String formatCode = matcher.group(2);
                switch (formatCode.charAt(0))
                {
                    case 'd':
                    case 's':
                        final String withIndex = matcher.group(1);
                        this.with.get((withIndex != null) ? (Integer.parseInt(withIndex) - 1) : i++).toLegacyText(builder);
                        break;
                    case '%':
                        this.addFormat(builder);
                        builder.append('%');
                    default:
                        break;
                }
            }
            if (trans.length() != position)
            {
                this.addFormat(builder);
                builder.append(trans.substring(position, trans.length()));
            }
        } catch (final MissingResourceException e)
        {
            this.addFormat(builder);
            builder.append(this.translate);
        }
        super.toLegacyText(builder);
    }

    @Override
    public BaseComponent duplicate()
    {
        return new TranslatableComponent(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("locales", locales).append("format", format).append("translate", this.translate).append("with", this.with).toString();
    }
}
