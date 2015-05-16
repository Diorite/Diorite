package org.diorite.cfg.system.elements;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import org.diorite.cfg.annotations.CfgStringStyle.StringStyle;
import org.diorite.cfg.system.CfgEntryData;
import org.diorite.cfg.system.FieldOptions;

public class StringTemplateElement extends TemplateElement<String>
{
    public static final StringTemplateElement INSTANCE = new StringTemplateElement();

    public StringTemplateElement()
    {
        super(String.class, Object::toString, c -> true);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final String element, final int level) throws IOException
    {
        StringStyle style = field.getOption(FieldOptions.STRING_STYLE, StringStyle.DEFAULT);
        if ((style == StringStyle.ALWAYS_MULTI_LINE) && (level < 0)) // level under 0 means that we are in simple array, multi line strings don't work here
        {
            style = StringStyle.DEFAULT;
        }
        if (style == StringStyle.DEFAULT)
        {
            final int linesCount = StringUtils.countMatches(element, '\n');
            final int maxNewLines = field.getOption(FieldOptions.STRING_MULTILINE_THRESHOLD, 2);
            if ((level >= 0) && (linesCount >= maxNewLines))
            {
                writer.append("|-");
                final String[] lines = StringUtils.split(element, '\n');
                for (final String line : lines)
                {
                    appendElement(writer, level + 1, line);
                }
                return;
            }
            boolean needQuote = false;
            for (int i = 0, size = element.length(); i < size; i++)
            {
                final char c = element.charAt(i);
                if ((c == '\"') || (c == '\''))
                {
                    needQuote = true;
                    break;
                }
            }
            if (needQuote)
            {
                writer.append('\"');
                writer.append(StringUtils.replace(element, "\"", "\\\""));
                writer.append('\"');
                return;
            }
            writer.append('\'');
            writer.append(element);
            writer.append('\'');
            return;
        }
        switch (style)
        {
            case ALWAYS_QUOTED:
                writer.append('\"');
                writer.append(StringUtils.replace(element, "\"", "\\\""));
                writer.append('\"');
                break;
            case ALWAYS_SINGLE_QUOTED:
                writer.append('\'');
                writer.append(StringUtils.replaceEach(element, new String[]{"\"", "'"}, new String[]{"\\\"", "\\'"}));
                writer.append('\'');
                break;
            case ALWAYS_MULTI_LINE:
                writer.append("|-");
                final String[] lines = StringUtils.split(element, '\n');
                for (final String line : lines)
                {
                    appendElement(writer, level + 1, line);
                }
                break;
            default:
                break;
        }
    }
}
