package org.diorite.chat;


import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.Validate;

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

    public static final char COLOR_CHAR                   = '§';
    public static final char DEFAULT_ALTERNATE_COLOR_CHAR = '&';
    private static final Pattern                   STRIP_COLOR_PATTERN;
    private static final Map<Integer, ChatColor>   BY_ID;
    private static final Map<Character, ChatColor> BY_CHAR;
    private final        String                    name;
    private final        int                       intCode;
    private final        char                      code;
    private final        boolean                   isFormat;
    private final        String                    toString;

    private ChatColor(final String name, final char code, final int intCode)
    {
        this(name, code, intCode, false);
    }

    private ChatColor(final String name, final char code, final int intCode, final boolean isFormat)
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

    public static String translateAlternateColorCodes(final char altColorChar, final String textToTranslate)
    {
        final char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < (b.length - 1); i++)
        {
            if ((b[i] == altColorChar) && ("0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[(i + 1)]) > - 1))
            {
                b[i] = '§';
                b[(i + 1)] = Character.toLowerCase(b[(i + 1)]);
            }
        }
        return new String(b);
    }

    public static String translateAlternateColorCodes(final String textToTranslate)
    {
        return translateAlternateColorCodes(DEFAULT_ALTERNATE_COLOR_CHAR, textToTranslate);
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
        STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");


        BY_ID = Maps.newHashMap();
        BY_CHAR = Maps.newHashMap();
        for (final ChatColor color : values())
        {
            BY_ID.put(color.intCode, color);
            BY_CHAR.put(color.code, color);
        }
    }
}