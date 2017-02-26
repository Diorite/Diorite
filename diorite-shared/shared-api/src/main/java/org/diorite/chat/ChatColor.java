/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import javax.annotation.Nullable;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;

import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;

public enum ChatColor
{
    BLACK("black", '0'),
    DARK_BLUE("dark_blue", '1'),
    DARK_GREEN("dark_green", '2'),
    DARK_AQUA("dark_aqua", '3'),
    DARK_RED("dark_red", '4'),
    DARK_PURPLE("dark_purple", '5'),
    GOLD("gold", '6'),
    GRAY("gray", '7'),
    DARK_GRAY("dark_gray", '8'),
    BLUE("blue", '9'),
    GREEN("green", 'a'),
    AQUA("aqua", 'b'),
    RED("red", 'c'),
    LIGHT_PURPLE("light_purple", 'd'),
    YELLOW("yellow", 'e'),
    WHITE("white", 'f'),
    OBFUSCATE("obfuscated", 'k', true),
    BOLD("bold", 'l', true),
    STRIKETHROUGH("strikethrough", 'm', true),
    UNDERLINE("underline", 'n', true),
    ITALIC("italic", 'o', true),
    RESET("reset", 'r');

    public static final char COLOR_CHAR                   = '\u00A7';
    public static final char DEFAULT_ALTERNATE_COLOR_CHAR = '&';
    private static final Pattern                   STRIP_COLOR_PATTERN;
    private static final Map<Character, ChatColor> BY_CHAR;
    private final        String                    name;
    private final        char                      code;
    private final        boolean                   isFormat;
    private final        String                    toString;

    ChatColor(String name, char code)
    {
        this(name, code, false);
    }

    ChatColor(String name, char code, boolean isFormat)
    {
        this.name = name;
        this.code = code;
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

    @Nullable
    public static ChatColor getByChar(char code)
    {
        return BY_CHAR.get(code);
    }

    @Nullable
    public static ChatColor getByChar(String code)
    {
        Validate.isTrue(! code.isEmpty(), "Code must have at least one char");

        return BY_CHAR.get(code.charAt(0));
    }

    public static String stripColor(CharSequence input)
    {
        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static ChatMessage translateAlternateColorCodes(char altColorChar, String textToTranslate)
    {
        return ChatMessage.fromLegacy(translateAlternateColorCodesInString(altColorChar, textToTranslate));
    }

    public static ChatMessage translateAlternateColorCodes(String textToTranslate)
    {
        return ChatMessage.fromLegacy(translateAlternateColorCodesInString(DEFAULT_ALTERNATE_COLOR_CHAR, textToTranslate));
    }

    public static String translateAlternateColorCodesInString(char altColorChar, String textToTranslate)
    {
        char[] b = textToTranslate.toCharArray();
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

    public static String translateAlternateColorCodesInString(String textToTranslate)
    {
        return translateAlternateColorCodesInString(DEFAULT_ALTERNATE_COLOR_CHAR, textToTranslate);
    }

    public static String removeColorCodesInString(char altColorChar, String textToTranslate)
    {
        char[] b = textToTranslate.toCharArray();
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

    public static String getLastColors(CharSequence input)
    {
        StringBuilder result = new StringBuilder();
        int length = input.length();
        for (int index = length - 1; index > - 1; index--)
        {
            char section = input.charAt(index);
            if ((section == COLOR_CHAR) && (index < (length - 1)))
            {
                char c = input.charAt(index + 1);
                ChatColor color = getByChar(c);
                if (color != null)
                {
                    result.insert(0, color.toString());
                    if ((color.isColor()) || (color == RESET))
                    {
                        break;
                    }
                }
            }
        }
        return result.toString();
    }

    static
    {
        STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf(COLOR_CHAR) + "[0-9A-FK-OR]");

        ChatColor[] values = values();
        BY_CHAR = new Char2ObjectOpenHashMap<>(values.length);
        for (ChatColor color : values)
        {
            BY_CHAR.put(color.code, color);
        }
    }
}
