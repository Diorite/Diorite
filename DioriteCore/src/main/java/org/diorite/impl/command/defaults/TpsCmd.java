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
    private static final NumberFormat format;

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
            sender.sendSimpleColoredMessage("&aAverage tps (1,5,15 min): " + sb.toString() + "&7, TPS limit: &3" + sender.getCore().getTps() + "&7, Server speed multi: &3" + format.format(sender.getCore().getSpeedMutli()));
        });
    }
}
