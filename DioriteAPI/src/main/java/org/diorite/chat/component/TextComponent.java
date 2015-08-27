package org.diorite.chat.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.ChatColor;

public class TextComponent extends BaseComponent
{
    private static final Pattern url = Pattern.compile("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,4})(/\\S*)?$");
    private String text;

    public TextComponent(final String text)
    {
        this.text = text;
    }

    public TextComponent(final TextComponent textComponent)
    {
        super(textComponent);
        this.text = textComponent.getText();
    }

    public TextComponent(final BaseComponent... extras)
    {
        this.text = "";
        this.setExtra(Arrays.asList(extras));
    }

    public TextComponent()
    {
    }

    public String getText()
    {
        return this.text;
    }

    public void setText(final String text)
    {
        this.text = text;
    }

    @Override
    public int replace_(final String text, final BaseComponent component, int limit)
    {
        final int startIndex = this.text.indexOf(text);
        if (startIndex != - 1)
        {
            final int endIndex = startIndex + text.length();
            final String pre = this.text.substring(0, startIndex);
            final String post = this.text.substring(endIndex);
            this.text = pre;
            if (this.extra != null)
            {
                this.extra.addAll(0, Arrays.asList(component.duplicate(), new TextComponent(post)));
            }
            else
            {
                this.extra = new ArrayList<>(2);
                this.extra.add(component.duplicate());
                this.extra.add(new TextComponent(post));
            }
            if (-- limit == 0)
            {
                return 0;
            }
        }
        return super.replace_(text, component, limit);
    }

    @Override
    public int replace_(final String text, final String repl, int limit)
    {
        final int startIndex = this.text.indexOf(text);
        if (startIndex != - 1)
        {
            final int endIndex = startIndex + text.length();
            this.text = this.text.substring(0, startIndex) + repl + this.text.substring(endIndex);
            if (-- limit == 0)
            {
                return 0;
            }
        }
        return super.replace_(text, repl, limit);
    }

    @Override
    protected void toPlainText(final StringBuilder builder)
    {
        builder.append(this.text);
        super.toPlainText(builder);
    }

    @Override
    protected void toLegacyText(final StringBuilder builder)
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
        builder.append(this.text);
        super.toLegacyText(builder);
    }

    @Override
    public BaseComponent duplicate()
    {
        return new TextComponent(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("text", this.text).toString();
    }

    public static TextComponent join(final BaseComponent... components)
    {
        if ((components == null) || (components.length == 0))
        {
            return new TextComponent("");
        }
        if (components.length == 1)
        {
            if (components[0] instanceof TextComponent)
            {
                return (TextComponent) components[0];
            }
            return (components[0] == null) ? new TextComponent("") : new TextComponent(components[0]);
        }
        final TextComponent base = new TextComponent();
        for (int i = 0, componentsLength = components.length; i < componentsLength; i++)
        {
            final BaseComponent component = components[i];
            if (component == null)
            {
                continue;
            }
            if ((i + 1) < componentsLength)
            {
                component.addExtra("\n");
            }
            base.addExtra(component);
        }
        return base;
    }

    public static BaseComponent fromLegacyText(final String message)
    {
        final TextComponent base = new TextComponent("");
        StringBuilder builder = new StringBuilder();
        TextComponent component = new TextComponent();
        final Matcher matcher = url.matcher(message);

        for (int i = 0; i < message.length(); i++)
        {
            char c = message.charAt(i);
            if (c == ChatColor.COLOR_CHAR)
            {
                i++;
                c = message.charAt(i);
                if ((c >= 'A') && (c <= 'Z'))
                {
                    //noinspection MagicNumber
                    c += 32;
                }
                ChatColor format = ChatColor.getByChar(c);
                if (format == null)
                {
                    continue;
                }
                if (builder.length() > 0)
                {
                    final TextComponent old = component;
                    component = new TextComponent(old);
                    old.setText(builder.toString());
                    builder = new StringBuilder();
                    base.addExtra(old);
                }
                switch (format)
                {
                    case BOLD:
                        component.setBold(true);
                        break;
                    case ITALIC:
                        component.setItalic(true);
                        break;
                    case UNDERLINE:
                        component.setUnderlined(true);
                        break;
                    case STRIKETHROUGH:
                        component.setStrikethrough(true);
                        break;
                    case MAGIC:
                        component.setObfuscated(true);
                        break;
                    case RESET:
                        format = ChatColor.WHITE;
                    default:
                        component = new TextComponent();
                        component.setColor(format);
                        break;
                }
                continue;
            }
            int pos = message.indexOf(' ', i);
            if (pos == - 1)
            {
                pos = message.length();
            }
            if (matcher.region(i, pos).find())
            { //Web link handling

                if (builder.length() > 0)
                {
                    final TextComponent old = component;
                    component = new TextComponent(old);
                    old.setText(builder.toString());
                    builder = new StringBuilder();
                    base.addExtra(old);
                }

                final TextComponent old = component;
                component = new TextComponent(old);
                final String urlString = message.substring(i, pos);
                component.setText(urlString);
                component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, urlString.startsWith("http") ? urlString : ("http://" + urlString)));
                base.addExtra(component);
                i += pos - i - 1;
                component = old;
                continue;
            }
            builder.append(c);
        }
        if (builder.length() > 0)
        {
            component.setText(builder.toString());
            base.addExtra(component);
        }
        return base;
    }
}
