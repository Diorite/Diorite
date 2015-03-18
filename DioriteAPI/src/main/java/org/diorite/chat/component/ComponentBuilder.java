package org.diorite.chat.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.ChatColor;

public class ComponentBuilder
{
    private final Collection<BaseComponent> parts = new ArrayList<>(8);
    private TextComponent current;

    public ComponentBuilder(final ComponentBuilder original)
    {
        this.current = new TextComponent(original.current);
        this.parts.addAll(original.parts.stream().map(BaseComponent::duplicate).collect(Collectors.toList()));
    }

    public ComponentBuilder(final String text)
    {
        this.current = new TextComponent(text);
    }

    public ComponentBuilder append(final String text)
    {
        this.parts.add(this.current);
        this.current = new TextComponent(this.current);
        this.current.setText(text);
        return this;
    }

    public ComponentBuilder color(final ChatColor color)
    {
        this.current.setColor(color);
        return this;
    }

    public ComponentBuilder bold(final boolean bold)
    {
        this.current.setBold(bold);
        return this;
    }

    public ComponentBuilder italic(final boolean italic)
    {
        this.current.setItalic(italic);
        return this;
    }

    public ComponentBuilder underlined(final boolean underlined)
    {
        this.current.setUnderlined(underlined);
        return this;
    }

    public ComponentBuilder strikethrough(final boolean strikethrough)
    {
        this.current.setStrikethrough(strikethrough);
        return this;
    }

    public ComponentBuilder obfuscated(final boolean obfuscated)
    {
        this.current.setObfuscated(obfuscated);
        return this;
    }

    public ComponentBuilder event(final ClickEvent clickEvent)
    {
        this.current.setClickEvent(clickEvent);
        return this;
    }

    public ComponentBuilder event(final HoverEvent hoverEvent)
    {
        this.current.setHoverEvent(hoverEvent);
        return this;
    }

    public BaseComponent[] create()
    {
        this.parts.add(this.current);
        return this.parts.toArray(new BaseComponent[this.parts.size()]);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("parts", this.parts).append("current", this.current).toString();
    }
}
