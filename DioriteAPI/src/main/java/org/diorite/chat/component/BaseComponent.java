package org.diorite.chat.component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.ChatColor;

public abstract class BaseComponent
{
    protected BaseComponent       parent;
    protected ChatColor           color;
    protected Boolean             bold;
    protected Boolean             italic;
    protected Boolean             underlined;
    protected Boolean             strikethrough;
    protected Boolean             obfuscated;
    protected List<BaseComponent> extra;
    protected ClickEvent          clickEvent;
    protected HoverEvent          hoverEvent;

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

    public BaseComponent()
    {
    }


    public void replace(final String text, final BaseComponent component, final int limit)
    {
        this.replace_(text, component, limit);
    }

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

    public void replace(final String text, final BaseComponent component)
    {
        this.replace(text, component, - 1);
    }

    public void replaceOnce(final String text, final BaseComponent component)
    {
        this.replace(text, component, 1);
    }

    public BaseComponent getParent()
    {
        return this.parent;
    }

    public void setParent(final BaseComponent parent)
    {
        this.parent = parent;
    }

    public List<BaseComponent> getExtra()
    {
        return this.extra;
    }

    public void setExtra(final List<BaseComponent> components)
    {
        for (final BaseComponent component : components)
        {
            component.parent = this;
        }
        this.extra = new ArrayList<>(components);
    }

    public ClickEvent getClickEvent()
    {
        return this.clickEvent;
    }

    public void setClickEvent(final ClickEvent clickEvent)
    {
        this.clickEvent = clickEvent;
    }

    public HoverEvent getHoverEvent()
    {
        return this.hoverEvent;
    }

    public void setHoverEvent(final HoverEvent hoverEvent)
    {
        this.hoverEvent = hoverEvent;
    }

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

    public void setColor(final ChatColor color)
    {
        this.color = color;
    }

    public ChatColor getColorRaw()
    {
        return this.color;
    }

    public boolean isBold()
    {
        if (this.bold == null)
        {
            return (this.parent != null) && (this.parent.isBold());
        }
        return this.bold;
    }

    public void setBold(final Boolean bold)
    {
        this.bold = bold;
    }

    public Boolean isBoldRaw()
    {
        return this.bold;
    }

    public boolean isItalic()
    {
        if (this.italic == null)
        {
            return (this.parent != null) && (this.parent.isItalic());
        }
        return this.italic;
    }

    public void setItalic(final Boolean italic)
    {
        this.italic = italic;
    }

    public Boolean isItalicRaw()
    {
        return this.italic;
    }

    public boolean isUnderlined()
    {
        if (this.underlined == null)
        {
            return (this.parent != null) && (this.parent.isUnderlined());
        }
        return this.underlined;
    }

    public void setUnderlined(final Boolean underlined)
    {
        this.underlined = underlined;
    }

    public Boolean isUnderlinedRaw()
    {
        return this.underlined;
    }

    public boolean isStrikethrough()
    {
        if (this.strikethrough == null)
        {
            return (this.parent != null) && (this.parent.isStrikethrough());
        }
        return this.strikethrough;
    }

    public void setStrikethrough(final Boolean strikethrough)
    {
        this.strikethrough = strikethrough;
    }

    public Boolean isStrikethroughRaw()
    {
        return this.strikethrough;
    }

    public boolean isObfuscated()
    {
        if (this.obfuscated == null)
        {
            return (this.parent != null) && (this.parent.isObfuscated());
        }
        return this.obfuscated;
    }

    public void setObfuscated(final Boolean obfuscated)
    {
        this.obfuscated = obfuscated;
    }

    public Boolean isObfuscatedRaw()
    {
        return this.obfuscated;
    }

    public void addExtra(final String text)
    {
        this.addExtra(new TextComponent(text));
    }

    public void addExtra(final BaseComponent component)
    {
        if (this.extra == null)
        {
            this.extra = new ArrayList<>(5);
        }
        component.parent = this;
        this.extra.add(component);
    }

    public boolean hasFormatting()
    {
        return (this.color != null) || (this.bold != null) || (this.italic != null) || (this.underlined != null) || (this.strikethrough != null) || (this.obfuscated != null) || (this.hoverEvent != null) || (this.clickEvent != null);
    }

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

    public abstract BaseComponent duplicate();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("parent", this.parent).append("color", this.color).append("bold", this.bold).append("italic", this.italic).append("underlined", this.underlined).append("strikethrough", this.strikethrough).append("obfuscated", this.obfuscated).append("extra", this.extra).append("clickEvent", this.clickEvent).append("hoverEvent", this.hoverEvent).toString();
    }

    public static String toLegacyText(final BaseComponent... components)
    {
        final StringBuilder builder = new StringBuilder();
        for (final BaseComponent msg : components)
        {
            builder.append(msg.toLegacyText());
        }
        return builder.toString();
    }

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
