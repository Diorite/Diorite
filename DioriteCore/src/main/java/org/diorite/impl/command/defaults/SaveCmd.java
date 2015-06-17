package org.diorite.impl.command.defaults;

import java.util.concurrent.ForkJoinPool;
import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.Server;
import org.diorite.command.CommandPriority;

public class SaveCmd extends SystemCommandImpl
{
    public SaveCmd()
    {
        super("save", (Pattern) null, CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            sender.getServer().broadcastSimpleColoredMessage(Server.PREFIX_MSG + "§7Saving all worlds...");
            ForkJoinPool.commonPool().submit(() -> {
                sender.getServer().getWorldsManager().getWorlds().parallelStream().forEach(w -> w.save(args.has(0) && args.asBoolean(0)));
                sender.getServer().broadcastSimpleColoredMessage(Server.PREFIX_MSG + "§7All worlds saved!");
            });
        });
    }
}
