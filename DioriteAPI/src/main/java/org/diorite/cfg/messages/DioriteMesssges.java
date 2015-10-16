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

package org.diorite.cfg.messages;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import org.diorite.Core;
import org.diorite.Diorite;
import org.diorite.cfg.messages.Message.MessageData;
import org.diorite.command.sender.CommandSender;
import org.diorite.plugin.BasePlugin;
import org.diorite.plugin.ChildPlugin;

/**
 * Static bindings for message methods related with Diorite.
 */
public final class DioriteMesssges
{
    /**
     * Helper variable to prevent a typo. Separator used in path.
     */
    public static final char   SEP           = '.';
    /**
     * Helper variable to prevent a typo. Common player node.
     */
    public static final String KEY_PLAYER    = "player";
    /**
     * Helper variable to prevent a typo. Common entity node.
     */
    public static final String KEY_ENTITY    = "entity";
    /**
     * Helper variable to prevent a typo. Common join node.
     */
    public static final String KEY_JOIN      = "join";
    /**
     * Helper variable to prevent a typo. Common quit node.
     */
    public static final String KEY_QUIT      = "quit";
    /**
     * Helper variable to prevent a typo. Common killed by node.
     */
    public static final String KEY_KILLED_BY = "killedby";

    private static Messages msgs;

    private DioriteMesssges()
    {
    }

    /**
     * Returns main instance of {@link Messages} used to store all diorite and many diorite plugins messages.<br>
     * Note that returned value will be not updated after reload, so you should not cache it.
     *
     * @return main instance of {@link Messages}
     */
    public static Messages getMessages()
    {
        return msgs;
    }

    /**
     * Reloads diorite message files (including plugin ones)
     */
    public static void reload()
    {
        final File langFolder = new File("lang");
        langFolder.mkdirs();
        final File[] files = new File[Diorite.getConfig().getLanguages().length];
        final Locale[] languages = Diorite.getConfig().getLanguages();
        for (int i = 0; i < languages.length; i++)
        {
            final Locale loc = languages[i];
            String name = loc.toLanguageTag();
            if (name.equals("und"))
            {
                name = loc.getDisplayName();
            }
            final File file = new File(langFolder, "lang_" + name + ".yml");
            if (! file.exists())
            {
                try (final InputStream is = Core.class.getClassLoader().getResourceAsStream("lang/" + name + ".yml"))
                {
                    if (is == null)
                    {
                        try (final InputStream defIs = Core.class.getClassLoader().getResourceAsStream("lang/en-US.yml"))
                        {
                            Files.copy(defIs, file.toPath());
                        }
                    }
                    else
                    {
                        Files.copy(is, file.toPath());
                    }
                } catch (final IOException e)
                {
                    e.printStackTrace();
                }

            }
            files[i] = file;
        }
        msgs = MessageLoader.loadMessages("lang_", files);
    }

    /**
     * Try send this message to given {@link CommandSender}, if message is disabled method will just return false.
     *
     * @param path   path to message.
     * @param target target of message.
     * @param lang   language to use if possible.
     * @param data   placeholder objects to use.
     *
     * @return true if message was send.
     */
    public static boolean sendMessage(final String path, final CommandSender target, final Locale lang, final MessageData... data)
    {
        return msgs.sendMessage(path, target, lang, data);
    }

    /**
     * Try broadcast this message (to all players), if message is disabled method will just return false.
     *
     * @param path path to message.
     * @param lang language to use if possible.
     * @param data placeholder objects to use.
     *
     * @return true if message was send.
     */
    public static boolean broadcastStaticMessage(final String path, final Locale lang, final MessageData... data)
    {
        return msgs.broadcastStaticMessage(path, lang, data);
    }

    /**
     * Try broadcast this message to selected comamnd senders, if message is disabled method will just return false.
     *
     * @param path    path to message.
     * @param targets targets of message.
     * @param lang    language to use if possible.
     * @param data    placeholder objects to use.
     *
     * @return true if message was send.
     */
    public static boolean broadcastStaticMessage(final String path, final Iterable<? extends CommandSender> targets, final Locale lang, final MessageData... data)
    {
        return msgs.broadcastStaticMessage(path, targets, lang, data);
    }

    /**
     * Try broadcast this message (to all players) in target player language if possible, if message is disabled method will just return false.
     *
     * @param path path to message.
     * @param lang default language to use if target don't have any.
     * @param data placeholder objects to use.
     *
     * @return true if message was send.
     */
    public static boolean broadcastMessage(final String path, final Locale lang, final MessageData... data)
    {
        return msgs.broadcastMessage(path, lang, data);
    }

    /**
     * Try broadcast this message to selected comamnd senders in target sender language if possible, if message is disabled method will just return false.
     *
     * @param path    path to message.
     * @param targets targets of message.
     * @param lang    default language to use if target don't have any.
     * @param data    placeholder objects to use.
     *
     * @return true if message was send.
     */
    public static boolean broadcastMessage(final String path, final Iterable<? extends CommandSender> targets, final Locale lang, final MessageData... data)
    {
        return msgs.broadcastMessage(path, targets, lang, data);
    }

    /**
     * Try broadcast this message (to all players) in target player language if possible, if message is disabled method will just return false.
     *
     * @param path path to message.
     * @param data placeholder objects to use.
     *
     * @return true if message was send.
     */
    public static boolean broadcastMessage(final String path, final MessageData... data)
    {
        return msgs.broadcastMessage(path, data);
    }

    /**
     * Try broadcast this message to selected comamnd senders in target sender language if possible, if message is disabled method will just return false.
     *
     * @param path    path to message.
     * @param targets targets of message.
     * @param data    placeholder objects to use.
     *
     * @return true if message was send.
     */
    public static boolean broadcastMessage(final String path, final Iterable<? extends CommandSender> targets, final MessageData... data)
    {
        return msgs.broadcastMessage(path, targets, data);
    }

    /**
     * Returns messages for given plugin, may return null if plugin don't use diorite message system. :(
     *
     * @param plugin plugin to get messages.
     *
     * @return messages for given plugin or null.
     */
    public static Messages getPluginMessages(final BasePlugin plugin)
    {
        return getMessages("plugins" + getNodeSeparator() + StringUtils.replaceEach(plugin.getName(), new String[]{Character.toString(getNodeSeparator()), ChildPlugin.CHILD_SEPARATOR}, new String[]{"_", getNodeSeparator() + "plugins" + getNodeSeparator()}));
    }

    /**
     * Returns node with messages on given path.
     *
     * @param path path to messages node, you may use '.' (by default) as path node separator. {@link #getNodeSeparator()}
     *
     * @return Another {@link Messages} node if exist.
     */
    public static Messages getMessages(final String path)
    {
        return msgs.getMessages(path);
    }

    /**
     * Returns node with messages on given path.
     *
     * @param path path to messages node, do not use any separator like dots here.
     *
     * @return Another {@link Messages} node if exist.
     */
    public static Messages getMessages(final String... path)
    {
        return msgs.getMessages(path);
    }

    /**
     * Returns message instance on given path.
     *
     * @param path path to messages node, you may use '.' (by default) as path node separator. {@link #getNodeSeparator()}
     *
     * @return message instance on given path.
     */
    public static Message getMessage(final String path)
    {
        return msgs.getMessage(path);
    }

    /**
     * Returns message instance on given path.
     *
     * @param path path to messages node, do not use any separator like dots here.
     *
     * @return message instance on given path.
     */
    public static Message getMessage(final String... path)
    {
        return msgs.getMessage(path);
    }

    /**
     * Add new message instance on given path.
     *
     * @param message message to add.
     * @param path    path to new messages node, do not use any separator like dots here.
     */
    public static void addMessage(final Message message, final String... path)
    {
        msgs.addMessage(message, path);
    }

    /**
     * Add new message instance on given path.
     *
     * @param message message to add.
     * @param path    path to new messages node, you may use '.' (by default) as path node separator. {@link #getNodeSeparator()}
     */
    public static void addMessage(final Message message, final String path)
    {
        msgs.addMessage(message, path);
    }

    /**
     * Returns node separator, '.' by default.
     *
     * @return node separator.
     */
    public static char getNodeSeparator()
    {
        return msgs.getNodeSeparator();
    }
}
