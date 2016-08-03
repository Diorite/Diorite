package org.diorite.mibac.nbteditor;

import java.util.regex.Matcher;

import org.diorite.Diorite;
import org.diorite.command.Arguments;
import org.diorite.command.Command;
import org.diorite.command.CommandExecutor;
import org.diorite.command.PluginCommand;
import org.diorite.command.SubCommand;
import org.diorite.command.sender.CommandSender;

public class NBTCommand implements CommandExecutor
{
    private final NBTEditor     plugin;
    private final PluginCommand nbt;
    private final SubCommand    help;
    private final SubCommand    lang;

    public NBTCommand(final NBTEditor plugin)
    {
        this.plugin = plugin;
        this.nbt = Diorite.createCommand(plugin, "nbt").executor(this).register();
        this.help = this.nbt.registerSubCommand("help", this);
        this.lang = this.nbt.registerSubCommand("lang", this);
    }

    @Override
    public void runCommand(final CommandSender sender, final Command command, final String label, final Matcher matchedPattern, final Arguments args)
    {
        sender.sendMessage("NBT>> " + command.getName());

        if (command.getName().equalsIgnoreCase(this.nbt.getName()))
        {
            this.help.dispatch(sender, label, matchedPattern, args.getRawArgs());
        }
        else if (command.getName().equalsIgnoreCase(this.help.getName()))
        {
            sender.sendMessage("Hi! #help");
            this.plugin.getLanguage().sendMessage("help", sender);
        }
        else if (command.getName().equalsIgnoreCase(this.lang.getName()))
        {
            this.plugin.reloadLanguage();
        }
    }
}
