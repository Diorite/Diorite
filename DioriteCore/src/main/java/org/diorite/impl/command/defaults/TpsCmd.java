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

package org.diorite.impl.command.defaults;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.command.CommandPriority;

public class TpsCmd extends SystemCommandImpl
{
    static final NumberFormat format;

    static
    {
        final DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator('`');
        format = new DecimalFormat("##0.00##", otherSymbols);
    }

    public TpsCmd()
    {
        super("tps", (Pattern) null, CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {

            if (args.has(0) && args.asString(0).equalsIgnoreCase("-r"))
            {
                sender.getCore().resetRecentTps();
            }
            final StringBuilder sb = new StringBuilder(128);
            sb.append("&8[");
            final double[] recentTps = sender.getCore().getRecentTps();
            for (int i = 0, recentTpsLength = recentTps.length; i < recentTpsLength; i++)
            {
                final double tps = recentTps[i];
                sb.append("&3").append(format.format(tps));
                if ((i + 1) != recentTpsLength)
                {
                    sb.append("&7, ");
                }
            }
            sb.append("&8]");
            sender.sendSimpleColoredMessage("&7Average tps (1,5,15 min): " + sb.toString() + "&7, TPS limit: &3" + sender.getCore().getTps() + "&7, Server speed multi: &3" + format.format(sender.getCore().getSpeedMutli()));
        });
    }
}
