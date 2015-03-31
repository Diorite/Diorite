package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.Server;
import org.diorite.command.CommandPriority;
import org.diorite.world.World;

public class SaveCmd extends SystemCommandImpl
{
    public SaveCmd()
    {
        super("save", (Pattern) null, CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            sender.getServer().broadcastSimpleColoredMessage(Server.PREFIX_MSG + "§7Saving all worlds...");
            sender.getServer().getWorldsManager().getWorlds().parallelStream().forEach(World::save);
            sender.getServer().broadcastSimpleColoredMessage(Server.PREFIX_MSG + "§7All worlds saved!");
        });
    }
}
