package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.Diorite;
import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.chat.ChatColor;
import org.diorite.command.CommandPriority;
import org.diorite.plugin.BasePlugin;
import org.diorite.plugin.ChildPlugin;
import org.diorite.plugin.DioritePlugin;

public class VersionCmd extends SystemCommandImpl
{
    public VersionCmd()
    {
        super("version", (Pattern) null, CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            if (args.length() == 0)
            {
                sender.sendSimpleColoredMessage("This server is running Diorite " + Diorite.getVersion());
            }
            else if (args.length() == 1)
            {
                final BasePlugin pl = Diorite.getPluginManager().getPlugin(args.asString(0));
                if (pl == null)
                {
                    sender.sendMessage(ChatColor.RED + "Plugin not found");
                    return;
                }

                sender.sendSimpleColoredMessage("&7Plugin overview:");
                sender.sendSimpleColoredMessage("  &7Name: &3" + pl.getName());
                sender.sendSimpleColoredMessage("  &7Author: &3" + pl.getAuthor());
                sender.sendSimpleColoredMessage("  &7Version: &3" + pl.getVersion());
                if (pl.getDescription() != null)
                {
                    sender.sendSimpleColoredMessage("  &7Description: &3" + pl.getDescription());
                }
                if (pl.getWebsite() != null)
                {
                    sender.sendSimpleColoredMessage("  &7Support website: &3" + pl.getWebsite());
                }
                sender.sendSimpleColoredMessage("&7Stats for nerds:");
                if (pl instanceof ChildPlugin)
                {
                    sender.sendSimpleColoredMessage("  &7Parent plugin: &3" + ((ChildPlugin) pl).getParent().getName());
                }
                else
                {
                    sender.sendSimpleColoredMessage("  &7Loaded classes: &3" + ((DioritePlugin) pl).getClassLoader().loadedClasses());
                }
                sender.sendSimpleColoredMessage("  &7Plugin loader: &3" + pl.getPluginLoader().getClass().getSimpleName());
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "Usage: /version [plugin]");
            }
        });
    }
}
