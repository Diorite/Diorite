package org.diorite.chat;

import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;

public final class DioriteMarkdownParser
{
    private DioriteMarkdownParser()
    {
    }
    // TODO: rewrite

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
        return TextComponent.fromLegacyText(str);
//        return new DioriteMarkdownParser(colors ? ChatColor.translateAlternateColorCodes(str) : str, defaultColor).parse();
    }
}