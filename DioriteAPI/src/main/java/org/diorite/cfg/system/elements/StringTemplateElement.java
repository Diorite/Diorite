package org.diorite.cfg.system.elements;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import org.diorite.cfg.annotations.CfgStringStyle.StringStyle;
import org.diorite.cfg.system.CfgEntryData;
import org.diorite.cfg.system.FieldOptions;

/**
 * Template handler for all string-based objects.
 *
 * @see String
 * @see CharSequence
 */
@SuppressWarnings("HardcodedFileSeparator")
public class StringTemplateElement extends TemplateElement<String>
{
    /**
     * Chars that can't start key/value without quotes
     */
    public static final char[] CANT_BE_FIRST = {'`', '!', '@', '#', '\'', '\"', '%', '&', '*', '|', '{', '[', ']', '}', ',', '>'};

    /**
     * Instance of template to direct-use.
     */
    public static final StringTemplateElement INSTANCE = new StringTemplateElement();

    private static final String[] REP_PREV_1 = new String[]{"\"", "'"};
    private static final String[] REP_PREV_2 = new String[]{"\\\"", "\\'"};
    private static final String[] REP_LAST_1 = new String[]{"\"", "\n"};
    private static final String[] REP_LAST_2 = new String[]{"\\\"", "\\n"};

    /**
     * Construct new string template handler.
     */
    public StringTemplateElement()
    {
        super(String.class, (obj) -> {
            if (obj instanceof CharSequence)
            {
                return obj.toString();
            }
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to String: " + obj);
        }, CharSequence.class::isAssignableFrom);
    }

    @Override
    protected String convertDefault(final Object def)
    {
        return def.toString();
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        StringStyle style = field.getOption(FieldOptions.STRING_STYLE, StringStyle.DEFAULT);
        if ((style == StringStyle.ALWAYS_MULTI_LINE) && (elementPlace == ElementPlace.SIMPLE_LIST_OR_MAP)) // multi line strings don't works in simple lists/maps.
        {
            style = StringStyle.DEFAULT;
        }
        final String element = elementRaw.toString();
        switch (style)
        {
            case ALWAYS_QUOTED:
            {
                writeQuoted(writer, element);
                break;
            }
            case ALWAYS_SINGLE_QUOTED:
            {
                writeSingleQuoted(writer, StringUtils.replaceEach(element, REP_PREV_1, REP_PREV_2));
                break;
            }
            case ALWAYS_MULTI_LINE:
            {
                writeMultiLine(writer, element, level, elementPlace);
                break;
            }
            default:
            {
                final boolean haveNewLines = StringUtils.contains(element, '\n');
                if ((elementPlace != ElementPlace.SIMPLE_LIST_OR_MAP) && haveNewLines)
                {
                    writeMultiLine(writer, element, level, elementPlace);
                    return;
                }
                boolean needQuote = false;
                if (element.isEmpty())
                {
                    writeSingleQuoted(writer, element);
                    return;
                }
                final char f = element.charAt(0);
                for (final char c : CANT_BE_FIRST)
                {
                    if (c == f)
                    {
                        needQuote = true;
                        break;
                    }
                }
                if (needQuote)
                {
                    writeQuoted(writer, element);
                    return;
                }
                if (StringUtils.isNumeric(element))
                {
                    writeQuoted(writer, element);
                }
                else
                {
                    writer.append(StringUtils.replace(element, "\n", "\\n"));
                }
                break;
            }
        }
    }

    private static void writeQuoted(final Appendable writer, final String element) throws IOException
    {
        writer.append('\"');
        writer.append(StringUtils.replaceEach(element, REP_LAST_1, REP_LAST_2));
        writer.append('\"');
    }

    private static void writeSingleQuoted(final Appendable writer, final String element) throws IOException
    {
        writer.append('\'');
        writer.append(StringUtils.replace(element, "\n", "\\n"));
        writer.append('\'');
    }

    private static void writeMultiLine(final Appendable writer, final String element, final int level, final ElementPlace elementPlace) throws IOException
    {
        writer.append("|2-\n");
        final String[] lines = StringUtils.split(element, '\n');
        final int lvl = level + ((elementPlace == ElementPlace.LIST) ? 0 : 1);
        for (int i = 0, linesLength = lines.length; i < linesLength; i++)
        {
            final String line = lines[i];
            appendElement(writer, lvl, line);
            if ((i + 1) < linesLength)
            {
                writer.append('\n');
            }
        }
    }
}
