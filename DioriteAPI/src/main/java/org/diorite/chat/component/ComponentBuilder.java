package org.diorite.chat.component;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.ChatColor;

public class ComponentBuilder
{
    private final TextComponent parts = new TextComponent("");
    private BaseComponent current;

    public ComponentBuilder(final ComponentBuilder original)
    {
        this.current = new TextComponent(original.current);
        original.parts.getExtra().stream().map(BaseComponent::duplicate).forEach(this.parts::addExtra);
    }

    public ComponentBuilder(final String text)
    {
        this.current = new TextComponent(text);
    }

    public ComponentBuilder append(final BaseComponent component)
    {
        this.parts.addExtra(this.current);
        this.current = component;
        return this;
    }

    public ComponentBuilder appendLegacy(final String text)
    {
        this.parts.addExtra(this.current);
        this.current = TextComponent.fromLegacyText(text);
        return this;
    }

    public ComponentBuilder append(final String text)
    {
        this.parts.addExtra(this.current);
        this.current = new TextComponent(text);
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

    public TextComponent create()
    {
        this.parts.addExtra(this.current);
        return this.parts;
    }

    public static ComponentBuilder start(final String text)
    {
        return new ComponentBuilder(text);
    }

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
