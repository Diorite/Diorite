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

import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.diorite.cfg.messages.DioriteMesssges;
import org.diorite.cfg.messages.Message;
import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.Diorite;
import org.diorite.chat.ChatColor;
import org.diorite.command.CommandPriority;
import org.diorite.plugin.BasePlugin;
import org.diorite.plugin.ChildPlugin;
import org.diorite.plugin.DioritePlugin;

public class PluginsCmd extends SystemCommandImpl
{
    public PluginsCmd()
    {
        super("plugins", Pattern.compile("(pl(ugin(s|)|))", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        this.setDescription("Displays plugin list");
        this.setUsage("plugins <plugin>");
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            // TODO check permissions
            if (args.length() == 0)
            {
                final Collection<BasePlugin> plugins = Diorite.getCore().getPluginManager().getPlugins();
                String msg = "&7Plugins (&3" + plugins.size() + "&7): ";
                //msg += plugins.stream().map(pl -> pl.isEnabled() ? (pl.isCoreMod() ? (ChatColor.DARK_AQUA + pl.getName()) : (ChatColor.GREEN + pl.getName())) : (pl.isCoreMod() ? (ChatColor.DARK_RED + pl.getName()) : (ChatColor.RED + pl.getName()))).collect(Collectors.joining("&7, "));
                msg += plugins.stream().map(pl -> pl.isEnabled() ? (pl.isCoreMod() ? (ChatColor.DARK_AQUA + pl.getName()) : (ChatColor.GREEN + pl.getName())) : (pl.isCoreMod() ? (ChatColor.DARK_RED + pl.getName()) : (ChatColor.RED + pl.getName()))).collect(Collectors.joining("&7, "));

                sender.sendSimpleColoredMessage(msg);
            }
            else if (args.length() == 1)
            {
                final BasePlugin pl = Diorite.getPluginManager().getPlugin(args.asString(0));
                if (pl == null)
                {
                    DioriteMesssges.sendMessage(DioriteMesssges.MSG_PLUGIN_NOTFOUND, sender, sender.getPreferedLocale());
                    return;
                }

                DioriteMesssges.sendMessage(DioriteMesssges.MSG_PLUGIN_OVERVIEW, sender, sender.getPreferedLocale(), Message.MessageData.e("plugin", pl));
                //sender.sendSimpleColoredMessage("  &7Name: &3" + pl.getName());
                DioriteMesssges.sendMessage(DioriteMesssges.MSG_PLUGIN_NAME, sender, sender.getPreferedLocale(), Message.MessageData.e("plugin", pl));
                //sender.sendSimpleColoredMessage("  &7Author: &3" + pl.getAuthor());
                DioriteMesssges.sendMessage(DioriteMesssges.MSG_PLUGIN_AUTHOR, sender, sender.getPreferedLocale(), Message.MessageData.e("plugin", pl));
                //sender.sendSimpleColoredMessage("  &7Version: &3" + pl.getVersion());
                DioriteMesssges.sendMessage(DioriteMesssges.MSG_PLUGIN_VERSION, sender, sender.getPreferedLocale(), Message.MessageData.e("plugin", pl));
                if (pl.getDescription() != null)
                {
                    //sender.sendSimpleColoredMessage("  &7Description: &3" + pl.getDescription());
                    DioriteMesssges.sendMessage(DioriteMesssges.MSG_PLUGIN_DESC, sender, sender.getPreferedLocale(), Message.MessageData.e("plugin", pl));
                }
                if (pl.getWebsite() != null)
                {
                    //sender.sendSimpleColoredMessage("  &7Support website: &3" + pl.getWebsite());
                    DioriteMesssges.sendMessage(DioriteMesssges.MSG_PLUGIN_WEBSITE, sender, sender.getPreferedLocale(), Message.MessageData.e("plugin", pl));
                }
                //sender.sendSimpleColoredMessage("&7Stats for nerds:");
                DioriteMesssges.sendMessage(DioriteMesssges.MSG_PLUGIN_STATS, sender, sender.getPreferedLocale(), Message.MessageData.e("plugin", pl));
                if (pl instanceof ChildPlugin)
                {
                    //sender.sendSimpleColoredMessage("  &7Parent plugin: &3" + ((ChildPlugin) pl).getParent().getName());
                    DioriteMesssges.sendMessage(DioriteMesssges.MSG_PLUGIN_PARENT, sender, sender.getPreferedLocale(), Message.MessageData.e("plugin", pl));
                }
                else
                {
                    //sender.sendSimpleColoredMessage("  &7Loaded classes: &3" + ((DioritePlugin) pl).getClassLoader().loadedClasses());
                    DioriteMesssges.sendMessage(DioriteMesssges.MSG_PLUGIN_LOADED, sender, sender.getPreferedLocale(), Message.MessageData.e("plugin", pl));
                }
                //sender.sendSimpleColoredMessage("  &7Plugin loader: &3" + pl.getPluginLoader().getClass().getSimpleName());
                DioriteMesssges.sendMessage(DioriteMesssges.MSG_PLUGIN_LOADER, sender, sender.getPreferedLocale(), Message.MessageData.e("plugin", pl));
            }
            else
            {
                //sender.sendMessage(ChatColor.RED + "Usage: /plugins [plugin]");
                DioriteMesssges.sendMessage(DioriteMesssges.MSG_CMD_CORRECT, sender, sender.getPreferedLocale(), Message.MessageData.e("command", command));
            }
        });
    }
}
