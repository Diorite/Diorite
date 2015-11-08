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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.messages.Message.MessageData;
import org.diorite.command.sender.CommandSender;

public class Messages
{
    private final char nodeSeparator; // '.' by default

    private final Messages              parentNode;
    private final Map<String, Message>  messages;
    private final Map<String, Messages> nodes;

    private Messages(final char nodeSeparator, final Messages parentNode, final Map<String, Message> messages, final Map<String, Messages> nodes)
    {
        this.nodeSeparator = nodeSeparator;
        this.messages = messages;
        this.nodes = nodes;
        this.parentNode = parentNode;
    }

    private Messages(final char nodeSeparator, final Messages parentNode)
    {
        this.nodeSeparator = nodeSeparator;
        this.messages = new HashMap<>(20, .5f);
        this.nodes = new HashMap<>(5, .2f);
        this.parentNode = parentNode;
    }


    /**
     * Construct new Messages root node with given separator, messages and sub-nodes.
     *
     * @param nodeSeparator node separator.
     * @param messages      messages in this node.
     * @param nodes         sub-nodes for this node.
     */
    public Messages(final char nodeSeparator, final Map<String, Message> messages, final Map<String, Messages> nodes)
    {
        this.nodeSeparator = nodeSeparator;
        this.messages = messages;
        this.nodes = nodes;
        this.parentNode = null;
    }

    /**
     * Construct new Messages root node with given separator.
     *
     * @param nodeSeparator node separator.
     */
    public Messages(final char nodeSeparator)
    {
        this.nodeSeparator = nodeSeparator;
        this.messages = new HashMap<>(20, .5f);
        this.nodes = new HashMap<>(5, .2f);
        this.parentNode = null;
    }

    /**
     * Construct new Messages root node with given messages and sub-nodes.
     *
     * @param messages messages in this node.
     * @param nodes    sub-nodes for this node.
     */
    public Messages(final Map<String, Message> messages, final Map<String, Messages> nodes)
    {
        this.nodeSeparator = '\u0000';
        this.messages = messages;
        this.nodes = nodes;
        this.parentNode = null;
    }

    /**
     * Construct new Messages root.
     */
    public Messages()
    {
        this.nodeSeparator = '\u0000';
        this.messages = new HashMap<>(20, .5f);
        this.nodes = new HashMap<>(5, .2f);
        this.parentNode = null;
    }

    /**
     * Returns node separator, '.' by default.
     *
     * @return node separator.
     */
    public char getNodeSeparator()
    {
        return this.nodeSeparator;
    }

    /**
     * Returns parent node if any exist.
     *
     * @return parent node or null.
     */
    public Messages getParentNode()
    {
        return this.parentNode;
    }

    /**
     * Add new message instance on given path.
     *
     * @param message message to add.
     * @param path    path to new messages node, you may use '.' (by default) as path node separator. {@link #getNodeSeparator()}
     */
    public void addMessage(final Message message, final String path)
    {
        this.addMessage(message, StringUtils.split(path, this.nodeSeparator));
    }

    /**
     * Add new message instance on given path.
     *
     * @param message message to add.
     * @param path    path to new messages node, do not use any separator like dots here.
     */
    public void addMessage(final Message message, final String... path)
    {
        if (path.length == 0)
        {
            throw new IllegalArgumentException("Added mesage must have name");
        }
        if (path.length == 1)
        {
            this.messages.put(path[0], message);
            return;
        }
        Messages prevNode;
        Messages node = this;
        for (int i = 0; i < (path.length - 1); i++)
        {
            prevNode = node;
            node = node.nodes.get(path[i]);
            if (node == null)
            {
                node = new MessagePack(prevNode, path[i]);
                prevNode.nodes.put(path[i], node);
            }
        }
        node.addMessage(message, new String[]{path[path.length - 1]});
    }

    /**
     * Returns message instance on given path.
     *
     * @param path path to messages node, do not use any separator like dots here.
     *
     * @return message instance on given path.
     */
    public Message getMessage(final String... path)
    {
        if (path.length == 0)
        {
            return null;
        }
        if (path.length == 1)
        {
            return this.messages.get(path[0]);
        }
        Messages node = this;
        for (int i = 0; i < (path.length - 1); i++)
        {
            node = node.nodes.get(path[i]);
            if (node == null)
            {
                return null;
            }
        }
        return node.messages.get(path[path.length - 1]);
    }

    /**
     * Returns message instance on given path.
     *
     * @param path path to messages node, you may use '.' (by default) as path node separator. {@link #getNodeSeparator()}
     *
     * @return message instance on given path.
     */
    public Message getMessage(final String path)
    {
        return this.getMessage(StringUtils.split(path, this.nodeSeparator));
    }

    /**
     * Returns node with messages on given path.
     *
     * @param path path to messages node, do not use any separator like dots here.
     *
     * @return Another {@link Messages} node if exist.
     */
    public Messages getMessages(final String... path)
    {
        if (path.length == 0)
        {
            return this;
        }
        if (path.length == 1)
        {
            return this.nodes.get(path[0]);
        }
        Messages node = this;
        for (final String pathNode : path)
        {
            node = node.nodes.get(pathNode);
            if (node == null)
            {
                return null;
            }
        }
        return node;
    }

    /**
     * Returns node with messages on given path.
     *
     * @param path path to messages node, you may use '.' (by default) as path node separator. {@link #getNodeSeparator()}
     *
     * @return Another {@link Messages} node if exist.
     */
    public Messages getMessages(final String path)
    {
        return this.getMessages(StringUtils.split(path, this.nodeSeparator));
    }

    /**
     * Try broadcast this message to selected comamnd senders in target sender language if possible, if message is disabled method will just return false.
     *
     * @param path   path to message.
     * @param targets targets of message.
     * @param data    placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean broadcastMessage(final String path, final Iterable<? extends CommandSender> targets, final MessageData... data)
    {
        final Message message = this.getMessage(path);
        return (message != null) && message.broadcastMessage(targets, data);
    }

    /**
     * Try broadcast this message (to all players) in target player language if possible, if message is disabled method will just return false.
     *
     * @param path   path to message.
     * @param data placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean broadcastMessage(final String path, final MessageData... data)
    {
        final Message message = this.getMessage(path);
        return (message != null) && message.broadcastMessage(data);
    }

    /**
     * Try broadcast this message to selected comamnd senders in target sender language if possible, if message is disabled method will just return false.
     *
     * @param path   path to message.
     * @param targets targets of message.
     * @param lang    default language to use if target don't have any.
     * @param data    placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean broadcastMessage(final String path, final Iterable<? extends CommandSender> targets, final Locale lang, final MessageData... data)
    {
        final Message message = this.getMessage(path);
        return (message != null) && message.broadcastMessage(targets, lang, data);
    }

    /**
     * Try broadcast this message (to all players) in target player language if possible, if message is disabled method will just return false.
     *
     * @param path   path to message.
     * @param lang default language to use if target don't have any.
     * @param data placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean broadcastMessage(final String path, final Locale lang, final MessageData... data)
    {
        final Message message = this.getMessage(path);
        return (message != null) && message.broadcastMessage(lang, data);
    }

    /**
     * Try broadcast this message to selected comamnd senders, if message is disabled method will just return false.
     *
     * @param path   path to message.
     * @param targets targets of message.
     * @param lang    language to use if possible.
     * @param data    placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean broadcastStaticMessage(final String path, final Iterable<? extends CommandSender> targets, final Locale lang, final MessageData... data)
    {
        final Message message = this.getMessage(path);
        return (message != null) && message.broadcastStaticMessage(targets, lang, data);
    }

    /**
     * Try broadcast this message (to all players), if message is disabled method will just return false.
     *
     * @param path   path to message.
     * @param lang language to use if possible.
     * @param data placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean broadcastStaticMessage(final String path, final Locale lang, final MessageData... data)
    {
        final Message message = this.getMessage(path);
        return (message != null) && message.broadcastStaticMessage(lang, data);
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
    public boolean sendMessage(final String path, final CommandSender target, final Locale lang, final MessageData... data)
    {
        final Message message = this.getMessage(path);
        return (message != null) && message.sendMessage(target, lang, data);
    }

    /**
     * Add given node to nodes map.
     *
     * @param pack node to add.
     */
    protected void addNode(final MessagePack pack)
    {
        this.nodes.put(pack.node, pack);
    }

    protected static class MessagePack extends Messages
    {
        private final String node;

        protected MessagePack(final Messages parentNode, final String node)
        {
            super(parentNode.nodeSeparator, parentNode);
            this.node = node;
        }

        protected MessagePack(final Messages parentNode, final String node, final Map<String, Message> messages, final Map<String, Messages> nodes)
        {
            super(parentNode.nodeSeparator, parentNode, messages, nodes);
            this.node = node;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("node", this.node).toString();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("messages", this.messages).toString();
    }
}
