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

package org.diorite.chat;

import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.Validate;

import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;

@SuppressWarnings("MagicNumber")
public enum ChatColor
{
    BLACK("black", '0', 0),
    DARK_BLUE("dark_blue", '1', 1),
    DARK_GREEN("dark_green", '2', 2),
    DARK_AQUA("dark_aqua", '3', 3),
    DARK_RED("dark_red", '4', 4),
    DARK_PURPLE("dark_purple", '5', 5),
    GOLD("gold", '6', 6),
    GRAY("gray", '7', 7),
    DARK_GRAY("dark_gray", '8', 8),
    BLUE("blue", '9', 9),
    GREEN("green", 'a', 10),
    AQUA("aqua", 'b', 11),
    RED("red", 'c', 12),
    LIGHT_PURPLE("light_purple", 'd', 13),
    YELLOW("yellow", 'e', 14),
    WHITE("white", 'f', 15),
    MAGIC("obfuscated", 'k', 16, true),
    BOLD("bold", 'l', 17, true),
    STRIKETHROUGH("strikethrough", 'm', 18, true),
    UNDERLINE("underline", 'n', 19, true),
    ITALIC("italic", 'o', 20, true),
    RESET("reset", 'r', 21);

    public static final char COLOR_CHAR                   = '\u00A7'; // to fix encoding problems on Intellij 14.1
    public static final char DEFAULT_ALTERNATE_COLOR_CHAR = '&';
    private static final Pattern                   STRIP_COLOR_PATTERN;
    private static final Map<Integer, ChatColor>   BY_ID;
    private static final Map<Character, ChatColor> BY_CHAR;
    private final        String                    name;
    private final        int                       intCode;
    private final        char                      code;
    private final        boolean                   isFormat;
    private final        String                    toString;

    ChatColor(final String name, final char code, final int intCode)
    {
        this(name, code, intCode, false);
    }

    ChatColor(final String name, final char code, final int intCode, final boolean isFormat)
    {
        this.name = name;
        this.code = code;
        this.intCode = intCode;
        this.isFormat = isFormat;
        this.toString = new String(new char[]{COLOR_CHAR, code});
    }

    public String getName()
    {
        return this.name;
    }

    public char getChar()
    {
        return this.code;
    }

    public String toString()
    {
        return this.toString;
    }

    public boolean isFormat()
    {
        return this.isFormat;
    }

    public boolean isColor()
    {
        return (! this.isFormat) && (this != RESET);
    }

    public static ChatColor getByChar(final char code)
    {
        return BY_CHAR.get(code);
    }

    public static ChatColor getByChar(final String code)
    {
        Validate.notNull(code, "Code cannot be null");
        Validate.isTrue(! code.isEmpty(), "Code must have at least one char");

        return BY_CHAR.get(code.charAt(0));
    }

    public static String stripColor(final CharSequence input)
    {
        if (input == null)
        {
            return null;
        }
        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static BaseComponent translateAlternateColorCodes(final char altColorChar, final String textToTranslate)
    {
        return TextComponent.fromLegacyText(translateAlternateColorCodesInString(altColorChar, textToTranslate));
    }

    public static BaseComponent translateAlternateColorCodes(final String textToTranslate)
    {
        return TextComponent.fromLegacyText(translateAlternateColorCodesInString(DEFAULT_ALTERNATE_COLOR_CHAR, textToTranslate));
    }

    public static String translateAlternateColorCodesInString(final char altColorChar, final String textToTranslate)
    {
        final char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < (b.length - 1); i++)
        {
            if ((b[i] == altColorChar) && ("0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[(i + 1)]) > - 1))
            {
                b[i] = COLOR_CHAR;
                b[(i + 1)] = Character.toLowerCase(b[(i + 1)]);
            }
        }
        return new String(b);
    }

    public static String translateAlternateColorCodesInString(final String textToTranslate)
    {
        return translateAlternateColorCodesInString(DEFAULT_ALTERNATE_COLOR_CHAR, textToTranslate);
    }

    public static String removeColorCodesInString(final char altColorChar, final String textToTranslate)
    {
        final char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < (b.length - 1); i++)
        {
            if ((b[i] == COLOR_CHAR) && ("0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[(i + 1)]) > - 1))
            {
                b[i] = altColorChar;
                b[(i + 1)] = Character.toLowerCase(b[(i + 1)]);
            }
        }
        return new String(b);
    }

    public static String getLastColors(final CharSequence input)
    {
        String result = "";
        final int length = input.length();
        for (int index = length - 1; index > - 1; index--)
        {
            final char section = input.charAt(index);
            if ((section == COLOR_CHAR) && (index < (length - 1)))
            {
                final char c = input.charAt(index + 1);
                final ChatColor color = getByChar(c);
                if (color != null)
                {
                    result = color.toString() + result;
                    if ((color.isColor()) || (color.equals(RESET)))
                    {
                        break;
                    }
                }
            }
        }
        return result;
    }

    static
    {
        STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf(COLOR_CHAR) + "[0-9A-FK-OR]");


        BY_ID = Maps.newHashMap();
        BY_CHAR = Maps.newHashMap();
        for (final ChatColor color : values())
        {
            BY_ID.put(color.intCode, color);
            BY_CHAR.put(color.code, color);
        }
    }
}
