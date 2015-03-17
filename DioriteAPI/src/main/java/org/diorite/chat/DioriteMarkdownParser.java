package org.diorite.chat;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.diorite.ChatColor;

public class DioriteMarkdownParser
{
    private static final Map<String, Action> actions = new HashMap<>(10);

    static
    {
        actions.put("**", parser -> {
            System.out.println("checkLevel bold");
            parser.checkLevel(parser.bold);
            parser.bold = ! parser.bold;
        });
        actions.put("__", parser -> {
            System.out.println("checkLevel under");
            parser.checkLevel(parser.underlined);
            parser.underlined = ! parser.underlined;
        });
        actions.put("~~", parser -> {
            System.out.println("checkLevel strike");
            parser.checkLevel(parser.strikethrough);
            parser.strikethrough = ! parser.strikethrough;
        });
        //noinspection HardcodedFileSeparator
        actions.put("//", parser -> {
            System.out.println("checkLevel italic");
            parser.checkLevel(parser.italic);
            parser.italic = ! parser.italic;
        });
        actions.put("%%", parser -> {
            System.out.println("checkLevel Magic");
            parser.checkLevel(parser.magic);
            parser.magic = ! parser.magic;
        });
    }

    private final ChatColor defaultColor;
    private final TextComponent base = new TextComponent("§r");
    private final char[] chars;
    private       int    pointer;
    private StringBuilder sb = new StringBuilder(128);
    private TextComponent last;
    private boolean escaped = false;

    private boolean strikethrough = false;
    private boolean underlined    = false;
    private boolean italic        = false;
    private boolean magic         = false;
    private boolean bold          = false;

    private int level = 1;

    private ColorLevel colorLevel;

    private class ColorLevel
    {
        private final ChatColor  color;
        private final int        lvl;
        private final ColorLevel prev;

        private ColorLevel(final ChatColor color, final int lvl, final ColorLevel prev)
        {
            this.color = color;
            this.lvl = lvl;
            this.prev = prev;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("color", this.color).append("lvl", this.lvl).append("prev", this.prev).toString();
        }
    }

    private void checkLevel(final boolean bool)
    {
        if (! bool)
        {
            this.level++;
        }
        else
        {
            this.level--;
        }
        System.out.println("Changed lvl from " + (this.level - (bool ? - 1 : 1)) + " to " + this.level);
        this.addExtra();
    }

    private DioriteMarkdownParser(final String str, final ChatColor defaultColor)
    {
        this.defaultColor = defaultColor;
        this.chars = str.toCharArray();
    }

    private BaseComponent parse()
    {
        for (final int charsLength = this.chars.length; this.pointer < charsLength; this.pointer++)
        {
            final char c = this.chars[this.pointer];
            if (this.escaped)
            {
                this.sb.append(c);
                this.escaped = false;
                continue;
            }
            //noinspection HardcodedFileSeparator
            if (c == '\\')
            {
                this.escaped = true;
                continue;
            }
            if ((c == ChatColor.COLOR_CHAR) && ((this.pointer + 1) < charsLength))
            {
                final ChatColor temp = ChatColor.getByChar(this.chars[this.pointer + 1]);
                if ((temp != null) && temp.isColor())
                {
                    this.pointer++;
                    if ((this.colorLevel != null) && ((this.level) == this.colorLevel.lvl))
                    {
                        System.out.println("checkLevel color true");
//                        this.checkLevel(true);
                        this.addExtra();
                        if (this.colorLevel != null)
                        {
                            this.colorLevel = this.colorLevel.prev;
                        }
                    }
                    else
                    {
                        System.out.println("checkLevel color false");
                        this.addExtra();
//                        this.checkLevel(false);
                    }
                    this.colorLevel = new ColorLevel(temp, this.level, this.colorLevel);
                    continue;
                }
            }
            final Action a = actions.get(this.getNextTwoChars());
            if (a != null)
            {
                this.pointer++;
                a.accept(this);
            }
            else
            {
                this.sb.append(c);
            }
        }
        if (this.sb.length() > 0)
        {
            System.out.println("checkLevel last");
            this.checkLevel(true);
        }
        return this.base;
    }

    // 0    1     2  2    2   3    3     3     2      3   3   3   3     2   2 2     1
// gdfgdf __dfgfd&5gfhgfgfd ** -> dfgdg <- ** ghfh ** -> gffgfdg <- ** sdfsdfsdf__  fdsfsd
    private void addExtra()
    {
        final TextComponent ntc = new TextComponent(this.sb.toString());
        if (this.last == null)
        {
            this.base.addExtra(ntc);
        }
        else
        {
            this.last.addExtra(ntc);
        }
        this.last = ntc;
        this.checkOthers();
        this.sb = new StringBuilder(128);
    }

    private int lastLevel = this.level;

    private void checkOthers()
    {
        BaseComponent bc = this.last;
        int l = this.lastLevel;
        System.out.println("Last: " + l + ", current: " + this.level);
        while ((l > this.level) && (bc.getParent() != null))
        {
            l--;
            bc = bc.getParent();
        }
        this.lastLevel = l;
        if (this.colorLevel != null)
        {
//            if (this.level < this.colorLevel.lvl)
//            {
////                this.level--;
////                this.lastLevel = this.level;
//                this.colorLevel = this.colorLevel.prev;
//                if (this.colorLevel != null)
//                {
//                    bc.setColor(this.colorLevel.color);
//                }
//                else
//                {
//                    bc.setColor(this.defaultColor);
//                }
//
//            }
//            else
            {
                bc.setColor(this.colorLevel.color);
                this.colorLevel = this.colorLevel.prev;
            }
        }
        if (bc.getParent() == null)
        {
            bc.setBold(this.bold);

            bc.setObfuscated(this.magic);

            bc.setUnderlined(this.underlined);

            bc.setItalic(this.italic);

            bc.setStrikethrough(this.strikethrough);
            return;
        }

        if (bc.getParent().isBoldRaw() == null)
        {
            bc.setBold(this.bold);
        }
        else
        {
            bc.setBold((bc.getParent().isBold() == this.bold) ? null : this.bold);
        }

        if (bc.getParent().isObfuscatedRaw() == null)
        {
            bc.setObfuscated(this.magic);
        }
        else
        {
            bc.setObfuscated((bc.getParent().isObfuscated() == this.magic) ? null : this.magic);
        }

        if (bc.getParent().isUnderlinedRaw() == null)
        {
            bc.setUnderlined(this.underlined);
        }
        else
        {
            bc.setUnderlined((bc.getParent().isUnderlined() == this.underlined) ? null : this.underlined);
        }

        if (bc.getParent().isItalicRaw() == null)
        {
            bc.setItalic(this.italic);
        }
        else
        {
            bc.setItalic((bc.getParent().isItalic() == this.italic) ? null : this.italic);
        }

        if (bc.getParent().isStrikethroughRaw() == null)
        {
            bc.setStrikethrough(this.strikethrough);
        }
        else
        {
            bc.setStrikethrough((bc.getParent().isStrikethrough() == this.strikethrough) ? null : this.strikethrough);
        }
//        bc.setBold(this.bold);
//
//        bc.setObfuscated(this.magic);
//
//        bc.setUnderlined(this.underlined);
//
//        bc.setItalic(this.italic);
//
//        bc.setStrikethrough(this.strikethrough);

    }

    private String getNextTwoChars()
    {
        if (this.pointer >= this.chars.length)
        {
            return "";
        }
        int pointer = this.pointer;
        final char c1 = this.chars[pointer++];
        if (pointer >= this.chars.length)
        {
            return Character.toString(c1);
        }
        return Character.toString(c1) + Character.toString(this.chars[pointer]);
    }

    public static BaseComponent parse(final String str)
    {
        return parse(str, true);
    }

    public static BaseComponent parse(final String str, final boolean colors)
    {
        return parse(str, ChatColor.WHITE, colors);
    }

    public static BaseComponent parse(final String str, final ChatColor defaultColor)
    {
        return parse(str, defaultColor, true);
    }

    public static BaseComponent parse(final String str, final ChatColor defaultColor, final boolean colors)
    {
        return new DioriteMarkdownParser(colors ? ChatColor.translateAlternateColorCodes(str) : str, defaultColor).parse();
    }

    @FunctionalInterface
    private interface Action
    {
        void accept(DioriteMarkdownParser parser);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("base", this.base).append("chars", this.chars).append("pointer", this.pointer).append("sb", this.sb).append("escaped", this.escaped).append("strikethrough", this.strikethrough).append("underlined", this.underlined).append("italic", this.italic).append("magic", this.magic).append("bold", this.bold).toString();
    }
}
