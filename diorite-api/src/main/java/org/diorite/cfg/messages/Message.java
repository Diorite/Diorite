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

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.serialize.ComponentSerializer;
import org.diorite.chat.placeholder.PlaceholderData;
import org.diorite.command.sender.CommandSender;
import org.diorite.utils.math.DioriteRandomUtils;

@SuppressWarnings("ClassHasNoToStringMethod")
public abstract class Message
{
    /**
     * Used to store placeholders used in this message.
     */
    protected final Map<String, Collection<PlaceholderData<?>>> placeholders;

    /**
     * Construct new message with given placeholders.
     *
     * @param placeholders placeholders used in this message.
     */
    protected Message(final Map<String, Collection<PlaceholderData<?>>> placeholders)
    {
        this.placeholders = placeholders;
    }

    /**
     * Load message for given map of locales and objects (string, array or collection), used
     * to load message from language files.
     *
     * @param objects map of locales and objects (string, array or collection).
     *
     * @return Loaded message.
     */
    static Message load(final Map<Locale, Object> objects)
    {
        if (objects.isEmpty())
        {
            throw new RuntimeException("No languages enabled... " + objects);
        }
        if (objects.size() == 1)
        {
            return load(objects.values().iterator().next());
        }
        final Map<Locale, String> mapA = new HashMap<>(objects.size());
        final Map<Locale, String[]> mapB = new HashMap<>(objects.size());
        for (final Entry<Locale, Object> entry : objects.entrySet())
        {
            final Locale locale = entry.getKey();
            final Object obj = entry.getValue();
            if (obj instanceof String)
            {
                mapA.put(locale, (String) obj);
                continue;
            }
            if (obj instanceof String[])
            {
                mapB.put(locale, (String[]) obj);
                continue;
            }
            if (obj instanceof Collection)
            {
                final Collection<?> col = ((Collection<?>) obj);
                final String[] array = new String[col.size()];
                int i = 0;
                for (final Object o : col)
                {
                    array[i++] = o.toString();
                }
                mapB.put(locale, array);
                continue;
            }
            mapA.put(locale, obj.toString());
        }
        if (mapB.isEmpty() && mapA.isEmpty())
        {
            throw new RuntimeException("No languages enabled... " + objects);
        }
        if (mapA.isEmpty())
        {
            return new LocalizedRandomMessage(mapB);
        }
        if (mapB.isEmpty())
        {
            return new LocalizedMessage(mapA);
        }
        return new LocalizedMixedMessage(mapA, mapB);
    }

    /**
     * Load message for given object (string, array or collection), used
     * to load message from language file (only when only one language is used).
     *
     * @param object string, array or collection of messages.
     *
     * @return Loaded message.
     */
    static Message load(final Object object)
    {
        if (object instanceof String)
        {
            return new SimpleMessage((String) object);
        }
        if (object instanceof String[])
        {
            return new SimpleRandomMessage((String[]) object);
        }
        if (object instanceof Collection)
        {
            final Collection<?> col = ((Collection<?>) object);
            final String[] array = new String[col.size()];
            int i = 0;
            for (final Object o : col)
            {
                array[i++] = o.toString();
            }
            return new SimpleRandomMessage(array);
        }
        return new SimpleMessage(object.toString());
    }

    /**
     * Get BaseComponent to send, may return null if message is disabled.
     *
     * @param lang language to use if possible.
     * @param data placeholder objects to use.
     *
     * @return BaseComponent to send or null if disabled.
     */
    public abstract BaseComponent get(Locale lang, MessageData... data);

//    /**
//     * Get BaseComponent to send, may return null if message is disabled.
//     *
//     * @param lang   language to use if possible.
//     * @param name   name of first placeholder object to use.
//     * @param object instance of first placeholder object to use.
//     *
//     * @return BaseComponent to send or null if disabled.
//     */
//    public BaseComponent get(final Locale lang, final String name, final Object object)
//    {
//        return this.get(lang, new MessageData(name, object));
//    }
//
//    /**
//     * Get BaseComponent to send, may return null if message is disabled.
//     *
//     * @param lang    language to use if possible.
//     * @param name1   name of first placeholder object to use.
//     * @param object1 instance of first placeholder object to use.
//     * @param name2   name of second placeholder object to use.
//     * @param object2 instance of second placeholder object to use.
//     *
//     * @return BaseComponent to send or null if disabled.
//     */
//    public BaseComponent get(final Locale lang, final String name1, final Object object1, final String name2, final Object object2)
//    {
//        return this.get(lang, new MessageData(name1, object1), new MessageData(name2, object2));
//    }
//
//    /**
//     * Get BaseComponent to send, may return null if message is disabled.
//     *
//     * @param lang    language to use if possible.
//     * @param name1   name of first placeholder object to use.
//     * @param object1 instance of first placeholder object to use.
//     * @param name2   name of second placeholder object to use.
//     * @param object2 instance of second placeholder object to use.
//     * @param name3   name of third placeholder object to use.
//     * @param object3 instance of third placeholder object to use.
//     *
//     * @return BaseComponent to send or null if disabled.
//     */
//    public BaseComponent get(final Locale lang, final String name1, final Object object1, final String name2, final Object object2, final String name3, final Object object3)
//    {
//        return this.get(lang, new MessageData(name1, object1), new MessageData(name2, object2), new MessageData(name3, object3));
//    }
//
//    /**
//     * Get BaseComponent to send, may return null if message is disabled.
//     *
//     * @param lang    language to use if possible.
//     * @param names   array of names of placeholders objects to use.
//     * @param objects array of instances of placeholders objects to use.
//     *
//     * @return BaseComponent to send or null if disabled.
//     */
//    public BaseComponent get(final Locale lang, final String[] names, final Object... objects)
//    {
//        if (names.length != objects.length)
//        {
//            throw new IllegalArgumentException();
//        }
//        final MessageData[] data = new MessageData[names.length];
//        for (int i = 0; i < names.length; i++)
//        {
//            data[i] = new MessageData(names[i], objects[i]);
//        }
//        return this.get(lang, data);
//    }

    /**
     * Try send this message to given {@link CommandSender}, if message is disabled method will just return false.
     *
     * @param target target of message.
     * @param lang   language to use if possible.
     * @param data   placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean sendMessage(final CommandSender target, final Locale lang, final MessageData... data)
    {
        final BaseComponent msg = this.get(lang, data);
        if (msg == null)
        {
            return false;
        }
        target.sendMessage(msg);
        return true;
    }

    /**
     * Try broadcast this message (to all players), if message is disabled method will just return false.
     *
     * @param lang language to use if possible.
     * @param data placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean broadcastStaticMessage(final Locale lang, final MessageData... data)
    {
        final BaseComponent msg = this.get(lang, data);
        if (msg == null)
        {
            return false;
        }
        Diorite.broadcastMessage(msg);
        return true;
    }

    /**
     * Try broadcast this message to selected comamnd senders, if message is disabled method will just return false.
     *
     * @param targets targets of message.
     * @param lang    language to use if possible.
     * @param data    placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean broadcastStaticMessage(final Iterable<? extends CommandSender> targets, final Locale lang, final MessageData... data)
    {
        final BaseComponent msg = this.get(lang, data);
        if (msg == null)
        {
            return false;
        }
        targets.forEach(s -> s.sendMessage(msg));
        return true;
    }

    /**
     * Try broadcast this message (to all players) in target player language if possible, if message is disabled method will just return false.
     *
     * @param lang default language to use if target don't have any.
     * @param data placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean broadcastMessage(final Locale lang, final MessageData... data)
    {
        return this.broadcastMessage(Diorite.getOnlinePlayers(), lang, data);
    }

    /**
     * Try broadcast this message to selected comamnd senders in target sender language if possible, if message is disabled method will just return false.
     *
     * @param targets targets of message.
     * @param lang    default language to use if target don't have any.
     * @param data    placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean broadcastMessage(final Iterable<? extends CommandSender> targets, Locale lang, final MessageData... data)
    {
        if (lang == null)
        {
            lang = Diorite.getConfig().getLanguages()[0];
        }
        final Map<Locale, BaseComponent> groups = new HashMap<>(Diorite.getConfig().getLanguages().length);
        boolean anyMsgSent = false;
        for (final CommandSender target : targets)
        {
            Locale locale = target.getPreferedLocale();
            if (locale == null)
            {
                locale = Diorite.getConfig().getLanguages()[0];
            }
            BaseComponent msg;
            if (groups.containsKey(locale))
            {
                msg = groups.get(locale);
            }
            else
            {
                msg = this.get(locale, data);
                groups.put(locale, msg);
            }
            if (msg == null)
            {
                if (groups.containsKey(lang))
                {
                    msg = groups.get(lang);
                }
                else
                {
                    msg = this.get(lang, data);
                    groups.put(lang, msg);
                }
                if (msg == null)
                {
                    continue;
                }
            }
            target.sendMessage(msg);
            anyMsgSent = true;
        }
        return anyMsgSent;
    }

    /**
     * Try broadcast this message (to all players) in target player language if possible, if message is disabled method will just return false.
     *
     * @param data placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean broadcastMessage(final MessageData... data)
    {
        return this.broadcastMessage(Diorite.getOnlinePlayers(), null, data);
    }

    /**
     * Try broadcast this message to selected comamnd senders in target sender language if possible, if message is disabled method will just return false.
     *
     * @param targets targets of message.
     * @param data    placeholder objects to use.
     *
     * @return true if message was send.
     */
    public boolean broadcastMessage(final Iterable<? extends CommandSender> targets, final MessageData... data)
    {
        return this.broadcastMessage(targets, null, data);
    }

    /**
     * Represent placeholder data, name of object and object instance.
     */
    public static class MessageData
    {
        private final String name;
        private final Object object;

        /**
         * Construct new message placeholder data, with given name and object.
         *
         * @param name   name of placeholder object.
         * @param object object instance used in placeholder.
         */
        public MessageData(final String name, final Object object)
        {
            this.name = name;
            this.object = object;
        }

        /**
         * Simpler costructor for this object. <br>
         * Construct new message placeholder data, with given name and object.
         *
         * @param name   name of placeholder object.
         * @param object object instance used in placeholder.
         *
         * @return new instance of {@link org.diorite.cfg.messages.Message.MessageData}
         */
        public static MessageData e(final String name, final Object object)
        {
            return new MessageData(name, object);
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("object", this.object).toString();
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static BaseComponent replace(BaseComponent component, final Map<String, Collection<PlaceholderData<?>>> placeholders, final MessageData... data)
    {
        component = component.duplicate();
        for (final MessageData d : data)
        {
            final Collection<PlaceholderData<?>> placeholderDatas = placeholders.get(d.name);
            if (placeholderDatas == null)
            {
                continue;
            }
            for (final PlaceholderData placeholderData : placeholderDatas)
            {
                placeholderData.replace(component, d.object);
            }
        }
        return component;
    }

    private static class SimpleMessage extends Message
    {
        private final BaseComponent msg;

        SimpleMessage(final String msg)
        {
            super(PlaceholderData.parseString(msg, true));
            this.msg = (msg == null) ? null : ComponentSerializer.safeParseOne(msg, '&');
        }

        @Override
        public BaseComponent get(final Locale lang, final MessageData... data)
        {
            if (this.msg == null)
            {
                return null;
            }
            return replace(this.msg, this.placeholders, data);
        }

        @Override
        public String toString()
        {
            if (this.msg == null)
            {
                return "";
            }
            return this.msg.toPlainText();
        }
    }

    private static class SimpleRandomMessage extends Message
    {
        private final BaseComponent[] msg;

        SimpleRandomMessage(final String[] msg)
        {
            super(PlaceholderData.parseString((msg == null) ? null : StringUtils.join(msg), true));
            this.msg = (msg == null) ? null : ComponentSerializer.safeParseOne(msg, '&');
        }

        @Override
        public BaseComponent get(final Locale lang, final MessageData... data)
        {
            if ((this.msg == null) || (this.msg.length == 0))
            {
                return null;
            }
            return replace(this.msg[DioriteRandomUtils.nextInt(this.msg.length)], this.placeholders, data);
        }

        @Override
        public String toString()
        {
            if (this.msg.length == 0)
            {
                return "";
            }
            return this.msg[0].toPlainText();
        }
    }


    private static class LocalizedMessage extends Message
    {
        private final Map<Locale, BaseComponent> msg;

        LocalizedMessage(final Map<Locale, String> msg)
        {
            super(PlaceholderData.parseString((msg == null) ? null : StringUtils.join(msg.values(), ' '), true));
            if (msg == null)
            {
                this.msg = null;
                return;
            }
            this.msg = new HashMap<>(msg.size(), .1f);
            for (final Entry<Locale, String> entry : msg.entrySet())
            {
                this.msg.put(entry.getKey(), ComponentSerializer.safeParseOne(entry.getValue(), '&'));
            }
        }

        @Override
        public BaseComponent get(final Locale lang, final MessageData... data)
        {
            if ((this.msg == null) || this.msg.isEmpty())
            {
                return null;
            }
            final BaseComponent selected;
            if (lang == null)
            {
                selected = this.msg.get(Diorite.getConfig().getLanguages()[0]);
            }
            else
            {
                selected = this.msg.getOrDefault(lang, this.msg.get(Diorite.getConfig().getLanguages()[0]));
            }
            if (selected == null)
            {
                return null;
            }
            return replace(selected, this.placeholders, data);
        }

        @Override
        public String toString()
        {
            if ((this.msg == null) || this.msg.isEmpty())
            {
                return "";
            }
            final BaseComponent msg = this.msg.get(Diorite.getConfig().getLanguages()[0]);
            if (msg == null)
            {
                return "";
            }
            return msg.toPlainText();
        }
    }

    private static class LocalizedRandomMessage extends Message
    {
        private final Map<Locale, BaseComponent[]> msg;

        private static String joinString(final Map<Locale, String[]> msg)
        {
            if ((msg == null) || msg.isEmpty())
            {
                return null;
            }
            return msg.values().stream().map(StringUtils::join).reduce((a, b) -> a + b).orElse(null);
        }

        LocalizedRandomMessage(final Map<Locale, String[]> msg)
        {
            super(PlaceholderData.parseString(joinString(msg), true));
            if (msg == null)
            {
                this.msg = null;
                return;
            }
            this.msg = new HashMap<>(msg.size(), .1f);
            for (final Entry<Locale, String[]> entry : msg.entrySet())
            {
                this.msg.put(entry.getKey(), ComponentSerializer.safeParseOne(entry.getValue(), '&'));
            }
        }

        @Override
        public BaseComponent get(final Locale lang, final MessageData... data)
        {
            if ((this.msg == null) || this.msg.isEmpty())
            {
                return null;
            }
            final BaseComponent[] selected;
            if (lang == null)
            {
                selected = this.msg.get(Diorite.getConfig().getLanguages()[0]);
            }
            else
            {
                selected = this.msg.getOrDefault(lang, this.msg.get(Diorite.getConfig().getLanguages()[0]));
            }
            if ((selected == null) || (selected.length == 0))
            {
                return null;
            }
            return replace(selected[DioriteRandomUtils.nextInt(selected.length)], this.placeholders, data);
        }

        @Override
        public String toString()
        {
            if ((this.msg == null) || this.msg.isEmpty())
            {
                return "";
            }
            final BaseComponent[] msg = this.msg.get(Diorite.getConfig().getLanguages()[0]);
            if ((msg == null) || (msg.length == 0))
            {
                return "";
            }
            return msg[0].toPlainText();
        }
    }

    private static class LocalizedMixedMessage extends Message
    {
        private final Map<Locale, BaseComponent>   msg1;
        private final Map<Locale, BaseComponent[]> msg2;

        private static String joinString(final Map<Locale, String> msg1, final Map<Locale, String[]> msg2)
        {
            final String msg1Str = (msg1 == null) ? null : StringUtils.join(msg1.values(), ' ');
            final String msg2Str = LocalizedRandomMessage.joinString(msg2);
            return (msg1Str == null) ? msg2Str : (msg1Str + ((msg2Str == null) ? "" : msg2Str));
        }

        LocalizedMixedMessage(final Map<Locale, String> msg1, final Map<Locale, String[]> msg2)
        {
            super(PlaceholderData.parseString(joinString(msg1, msg2), true));
            if (msg1 == null)
            {
                this.msg1 = null;
            }
            else
            {
                this.msg1 = new HashMap<>(msg1.size(), .1f);
                for (final Entry<Locale, String> entry : msg1.entrySet())
                {
                    this.msg1.put(entry.getKey(), ComponentSerializer.safeParseOne(entry.getValue(), '&'));
                }
            }
            if (msg2 == null)
            {
                this.msg2 = null;
            }
            else
            {
                this.msg2 = new HashMap<>(msg2.size(), .1f);
                for (final Entry<Locale, String[]> entry : msg2.entrySet())
                {
                    this.msg2.put(entry.getKey(), ComponentSerializer.safeParseOne(entry.getValue(), '&'));
                }
            }
        }

        @Override
        public BaseComponent get(Locale lang, final MessageData... data)
        {
            if (lang == null)
            {
                lang = Diorite.getConfig().getLanguages()[0];
            }
            {
                BaseComponent selected = null;
                if ((this.msg1 != null) && ! this.msg1.isEmpty())
                {
                    selected = this.msg1.getOrDefault(lang, this.msg1.get(Diorite.getConfig().getLanguages()[0]));
                }
                if (selected != null)
                {
                    return replace(selected, this.placeholders, data);
                }
            }
            if ((this.msg2 == null) || this.msg2.isEmpty())
            {
                return null;
            }
            final BaseComponent[] selected = this.msg2.getOrDefault(lang, this.msg2.get(Diorite.getConfig().getLanguages()[0]));
            if ((selected == null) || (selected.length == 0))
            {
                return null;
            }
            return replace(selected[DioriteRandomUtils.nextInt(selected.length)], this.placeholders, data);
        }

        @Override
        public String toString()
        {
            if ((this.msg1 == null) || this.msg1.isEmpty())
            {
                if ((this.msg2 == null) || this.msg2.isEmpty())
                {
                    return "";
                }
                final BaseComponent[] msg = this.msg2.get(Diorite.getConfig().getLanguages()[0]);
                if ((msg == null) || (msg.length == 0))
                {
                    return "";
                }
                return msg[0].toPlainText();
            }
            final BaseComponent msg = this.msg1.get(Diorite.getConfig().getLanguages()[0]);
            if (msg == null)
            {
                return "";
            }
            return msg.toPlainText();
        }
    }
}
