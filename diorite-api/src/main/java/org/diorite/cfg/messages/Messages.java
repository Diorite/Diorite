/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.messages.Message.MessageData;
import org.diorite.chat.component.BaseComponent;
import org.diorite.command.sender.CommandSender;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

/**
 * Represent messages node, every node may contains message objects and other nodes.
 */
public class Messages
{
    /**
     * languages supported by this messages instance.
     */
    protected final Locale[]              languages;
    /**
     * Used in get/set method to separate nodes.
     */
    protected final char                  nodeSeparator; // '.' by default
    private final   Messages              parentNode;
    private final   Map<String, Message>  messages;
    private final   Map<String, Messages> nodes;

    private Messages(final Locale[] languages, final char nodeSeparator, final Messages parentNode, final Map<String, Message> messages, final Map<String, Messages> nodes)
    {
        this.languages = languages;
        this.nodeSeparator = nodeSeparator;
        this.messages = messages;
        this.nodes = nodes;
        this.parentNode = parentNode;
    }

    private Messages(final Locale[] languages, final char nodeSeparator, final Messages parentNode)
    {
        this.languages = languages;
        this.nodeSeparator = nodeSeparator;
        this.messages = new CaseInsensitiveMap<>(20, .5f);
        this.nodes = new CaseInsensitiveMap<>(5, .2f);
        this.parentNode = parentNode;
    }


    /**
     * Construct new Messages root node with given separator, messages and sub-nodes.
     *
     * @param languages     languages supported by this messages instance.
     * @param nodeSeparator node separator.
     * @param messages      messages in this node.
     * @param nodes         sub-nodes for this node.
     */
    public Messages(final Locale[] languages, final char nodeSeparator, final Map<String, Message> messages, final Map<String, Messages> nodes)
    {
        this.languages = languages;
        this.nodeSeparator = nodeSeparator;
        this.messages = new CaseInsensitiveMap<>(messages);
        this.nodes = new CaseInsensitiveMap<>(nodes);
        this.parentNode = null;
    }

    /**
     * Construct new Messages root node with given separator.
     *
     * @param languages     languages supported by this messages instance.
     * @param nodeSeparator node separator.
     */
    public Messages(final Locale[] languages, final char nodeSeparator)
    {
        this.languages = languages;
        this.nodeSeparator = nodeSeparator;
        this.messages = new CaseInsensitiveMap<>(20, .5f);
        this.nodes = new CaseInsensitiveMap<>(5, .2f);
        this.parentNode = null;
    }

    /**
     * Construct new Messages root node with given messages and sub-nodes.
     *
     * @param languages languages supported by this messages instance.
     * @param messages  messages in this node.
     * @param nodes     sub-nodes for this node.
     */
    public Messages(final Locale[] languages, final Map<String, Message> messages, final Map<String, Messages> nodes)
    {
        this.languages = languages;
        this.nodeSeparator = '.';
        this.messages = new CaseInsensitiveMap<>(messages);
        this.nodes = new CaseInsensitiveMap<>(nodes);
        this.parentNode = null;
    }

    /**
     * Construct new Messages root.
     *
     * @param languages languages supported by this messages instance.
     */
    public Messages(final Locale... languages)
    {
        this.languages = languages;
        this.nodeSeparator = '.';
        this.messages = new CaseInsensitiveMap<>(20, .5f);
        this.nodes = new CaseInsensitiveMap<>(5, .2f);
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
     * @param path    path to message.
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
     * @param path path to message.
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
     * @param path    path to message.
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
     * @param path path to message.
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
     * @param path    path to message.
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
     * @param path path to message.
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
     * Try send this message to given {@link CommandSender}, if message is disabled method will just return false.
     *
     * @param path   path to message.
     * @param target target of message.
     * @param data   placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean sendMessage(final String path, final CommandSender target, final MessageData... data)
    {
        final Message message = this.getMessage(path);
        return (message != null) && message.sendMessage(target, target.getPreferredLocale(), data);
    }

    /**
     * Serialize all messages to given map, all base components are serialized to json string.
     *
     * @param map             map where all messages will be added.
     * @param defaultLanguage default locale of messages.
     *
     * @return this same map as given.
     *
     * @see BaseComponent#canBeLegacy()
     */
    @SuppressWarnings("unchecked")
    public Map<Locale, Map<String, Object>> toMap(final Map<Locale, Map<String, Object>> map, final Locale defaultLanguage)
    {
        for (final Entry<String, Message> entry : this.messages.entrySet())
        {
            final String key = entry.getKey();
            final Message message = entry.getValue();

            final Map<Locale, Map<String, Object>> messageValues = message.toMap(new HashMap<>(this.languages.length), defaultLanguage, key);
            for (final Entry<Locale, Map<String, Object>> localeMapEntry : messageValues.entrySet())
            {
                final Locale locale = localeMapEntry.getKey();
                final Map<String, Object> valueMap = localeMapEntry.getValue();

                Map<String, Object> targetMap = map.get(locale);
                if (targetMap == null)
                {
                    targetMap = new CaseInsensitiveMap<>(20);
                    map.put(locale, targetMap);
                }
                targetMap.putAll(valueMap);
            }
        }
        for (final Entry<String, Messages> entry : this.nodes.entrySet())
        {
            final String key = entry.getKey();
            final Messages messages = entry.getValue();

            final Map<Locale, Map<String, Object>> messageValues = messages.toMap(new HashMap<>(this.languages.length), defaultLanguage);
            for (final Entry<Locale, Map<String, Object>> localeMapEntry : messageValues.entrySet())
            {
                final Locale locale = localeMapEntry.getKey();
                final Map<String, Object> valueMap = localeMapEntry.getValue();

                Map<String, Object> nestedMap = map.get(locale);
                if (nestedMap == null)
                {
                    nestedMap = new CaseInsensitiveMap<>(20);
                    map.put(locale, nestedMap);
                }
                final Object tmp = nestedMap.get(key);
                if ((tmp != null) && ! (tmp instanceof Map))
                {
                    throw new RuntimeException("Duplicated node and message for key: " + key + ": " + tmp);
                }
                final Map<String, Object> targetMap;
                if (tmp == null)
                {
                    targetMap = new CaseInsensitiveMap<>(20);
                    nestedMap.put(key, targetMap);
                }
                else
                {
                    targetMap = (Map<String, Object>) tmp;
                }
                targetMap.putAll(valueMap);
            }
        }
        return map;
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
            super(parentNode.languages, parentNode.nodeSeparator, parentNode);
            this.node = node;
        }

        protected MessagePack(final Messages parentNode, final String node, final Map<String, Message> messages, final Map<String, Messages> nodes)
        {
            super(parentNode.languages, parentNode.nodeSeparator, parentNode, messages, nodes);
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
