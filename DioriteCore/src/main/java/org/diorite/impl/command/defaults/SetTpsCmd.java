package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.Core;
import org.diorite.command.CommandPriority;

public class SetTpsCmd extends SystemCommandImpl
{
    public SetTpsCmd()
    {
        super("setTps", (Pattern) null, CommandPriority.LOW);
        //noinspection HardcodedFileSeparator
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> sender.sendMessage("§4Invalid usage, please type /setTps <number from 1 to 99>"));
        this.registerSubCommand("core", Pattern.compile("(?<tps>([0-9]{2})|([1-9]))((-multi=(?<multi>\\d+((\\.\\d+)|)))|)", Pattern.CASE_INSENSITIVE), (sender, command, label, matchedPattern, args) -> {
            final int tps = Integer.parseInt(matchedPattern.group("tps"));
            final String temp = matchedPattern.group("multi");
            final double multi = (temp == null) ? (((double) Core.DEFAULT_TPS) / tps) : Double.parseDouble(temp);
            sender.sendSimpleColoredMessage("&7TPS set to: &3" + tps + "&7, and server speed to: &3" + TpsCmd.format.format(multi));
            sender.getCore().setTps(tps);
            sender.getCore().setSpeedMutli(multi);
            sender.getCore().resetRecentTps();
        });
    }
}
