package org.diorite.impl.command.defaults;

import java.util.Collections;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.command.CommandPriority;
import org.diorite.entity.Player;

public class ItemCmd extends SystemCommandImpl
{
    public ItemCmd()
    {
        super("item", Collections.singletonList("i"), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            if (! sender.isPlayer())
            {
                sender.sendSimpleColoredMessage("&4Only for players!"); // TODO: change message and add it to config.
            }
            GiveCmd.parseGiveCommand((Player) sender, sender, args, 0);
        });
    }
}