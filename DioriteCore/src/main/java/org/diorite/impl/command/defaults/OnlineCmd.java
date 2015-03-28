package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.command.CommandPriority;

public class OnlineCmd extends SystemCommandImpl
{
    public OnlineCmd()
    {
        super("online", Pattern.compile("(online|who|list)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> sender.sendSimpleColoredMessage("&3Online players: &7" + StringUtils.join(ServerImpl.getInstance().getPlayersManager().getOnlinePlayersNames(), "&9, &7")));
    }
}