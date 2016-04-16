/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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
        super(String.class);
    }

    @Override
    protected boolean canBeConverted0(final Class<?> c)
    {
        return CharSequence.class.isAssignableFrom(c);
    }

    @Override
    protected String convertObject0(final Object obj) throws UnsupportedOperationException
    {
        if (obj instanceof Enum)
        {
            return ((Enum<?>) obj).name();
        }
        if (obj instanceof CharSequence)
        {
            return obj.toString();
        }
        throw this.getException(obj);
    }

    @Override
    protected String convertDefault0(final Object obj, final Class<?> fieldType)
    {
        return obj.toString();
    }

    @Override
    public void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        StringStyle style = field.getOption(FieldOptions.STRING_STYLE, StringStyle.DEFAULT);
        if ((style == StringStyle.ALWAYS_MULTI_LINE) && (elementPlace == ElementPlace.SIMPLE_LIST_OR_MAP)) // multi line strings don't works in simple lists/maps.
        {
            style = StringStyle.DEFAULT;
        }
        final String element = Enum.class.isAssignableFrom(elementRaw.getClass()) ? ((Enum<?>) elementRaw).name() : elementRaw.toString();
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
                if (StringUtils.containsAny(element, ':', '#'))
                {
                    needQuote = true;
                }
                else
                {
                    for (final char c : CANT_BE_FIRST)
                    {
                        if (c == f)
                        {
                            needQuote = true;
                            break;
                        }
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
        final int lvl = level + 1;//((elementPlace == ElementPlace.LIST) ? 0 : 1);
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
