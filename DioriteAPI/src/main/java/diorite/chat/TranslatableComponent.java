package diorite.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.ChatColor;

public class TranslatableComponent extends BaseComponent
{
    private final ResourceBundle locales = ResourceBundle.getBundle("mojang-translations/en_US");
    private final Pattern format = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
    private String              translate;
    private List<BaseComponent> with;

    public TranslatableComponent(final TranslatableComponent original)
    {
        super(original);
        this.translate = original.getTranslate();
        this.with.addAll(original.getWith().stream().map(BaseComponent::duplicate).collect(Collectors.toList()));
    }
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
    public TranslatableComponent()
    {
    }

    public ResourceBundle getLocales()
    {
        return this.locales;
    }

    public Pattern getFormat()
    {
        return this.format;
    }

    public String getTranslate()
    {
        return this.translate;
    }

    public void setTranslate(final String translate)
    {
        this.translate = translate;
    }

    public List<BaseComponent> getWith()
    {
        return this.with;
    }

    public void setWith(final List<BaseComponent> components)
    {
        for (final BaseComponent component : components)
        {
            component.setParent(this);
        }
        this.with = components;
    }

    public void addWith(final String text)
    {
        this.addWith(new TextComponent(text));
    }

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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("locales", this.locales).append("format", this.format).append("translate", this.translate).append("with", this.with).toString();
    }

    @Override
    protected void toPlainText(final StringBuilder builder)
    {
        try
        {
            final String trans = this.locales.getString(this.translate);
            final Matcher matcher = this.format.matcher(trans);
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
            final String trans = this.locales.getString(this.translate);
            final Matcher matcher = this.format.matcher(trans);
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
}
