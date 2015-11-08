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