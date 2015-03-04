package diorite.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.ChatColor;

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

    public static BaseComponent[] fromLegacyText(final String message)
    {
        final ArrayList<BaseComponent> components = new ArrayList<>(message.length());
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
                    components.add(old);
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
                    components.add(old);
                }

                final TextComponent old = component;
                component = new TextComponent(old);
                final String urlString = message.substring(i, pos);
                component.setText(urlString);
                component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, urlString.startsWith("http") ? urlString : ("http://" + urlString)));
                components.add(component);
                i += pos - i - 1;
                component = old;
                continue;
            }
            builder.append(c);
        }
        if (builder.length() > 0)
        {
            component.setText(builder.toString());
            components.add(component);
        }

        // The client will crash if the array is empty
        if (components.isEmpty())
        {
            components.add(new TextComponent(""));
        }

        return components.toArray(new BaseComponent[components.size()]);
    }
}
