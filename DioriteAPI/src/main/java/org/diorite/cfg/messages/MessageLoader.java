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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.yaml.snakeyaml.Yaml;

import org.diorite.Diorite;
import org.diorite.cfg.messages.Messages.MessagePack;

/**
 * Class for loading messages from selected files.
 */
public final class MessageLoader
{
    private static Messages masterNode;

    private MessageLoader()
    {
    }

    /**
     * Load messages from given array of files, each file means one language so they need contains language code at the end, like: <br>
     * myLangFile_pl-PL.yml<br>
     * myLangFile_en-US.yml<br>
     * etc... <br>
     *
     * @param predicate files need match this predictate to be loaded.
     * @param func      this function should get {@link Locale} in which are messages in file written.
     * @param files     files to load
     *
     * @return loaded messages instance.
     */
    public static Messages loadMessages(final Predicate<File> predicate, final Function<File, Locale> func, final File... files)
    {
        return loadMessages(predicate, func, new Messages(), files);
    }

    /**
     * Load messages from given array of files, each file means one language so they need contains language code at the end, like: <br>
     * myLangFile_pl-PL.yml<br>
     * myLangFile_en-US.yml<br>
     * etc... <br>
     *
     * @param predicate files need match this predictate to be loaded.
     * @param func      this function should get {@link Locale} in which are messages in file written.
     * @param node      messages node to use, usefull when you want load messages to one of subnodes.
     * @param files     files to load
     *
     * @return this same messages instance as given after loading messages.
     */
    public static Messages loadMessages(final Predicate<File> predicate, final Function<File, Locale> func, final Messages node, final File... files)
    {
        final Yaml yaml = new Yaml();
        final DataTree result = new DataTree("");
        for (final File file : files)
        {
            if (! predicate.test(file))
            {
                continue;
            }
            Locale locale = func.apply(file);
            if (locale == null)
            {
                locale = Diorite.getConfig().getLanguages()[0];
            }
            final Map<?, ?> loaded;
            try
            {
                loaded = yaml.loadAs(new FileInputStream(file), Map.class);
            } catch (final FileNotFoundException e)
            {
                e.printStackTrace();
                continue;
            }
            toTree(locale, "", loaded, result);
        }
        return result.construct(node);
    }

    /**
     * Load messages from given array of files, each file means one language so they need contains language code at the end, like: <br>
     * myLangFile_pl-PL.yml<br>
     * myLangFile_en-US.yml<br>
     * etc... <br>
     * Files not starting from selected prefix will be skipped.
     *
     * @param prefix prefix of each file.
     * @param files  files to load
     *
     * @return loaded messages instance.
     */
    public static Messages loadMessages(final String prefix, final File... files)
    {
        if (prefix == null)
        {
            return null;
        }
        return loadMessages(f -> f.getName().startsWith(prefix), f -> getLocale(f.getName().substring(prefix.length(), f.getName().lastIndexOf('.'))), files);
    }

    /**
     * Load messages from given array of files, each file means one language so they need contains language code at the end, like: <br>
     * myLangFile_pl-PL.yml<br>
     * myLangFile_en-US.yml<br>
     * etc...
     *
     * @param files files to load
     *
     * @return loaded messages instance.
     */
    public static Messages loadMessages(final File... files)
    {
        return loadMessages(findPrefix(0, files), files);
    }

    private static Locale getLocale(final String str)
    {
        final Locale locale = Locale.forLanguageTag(str);
        if (locale.getDisplayName().isEmpty())
        {
            return new Locale(str);
        }
        return locale;
    }

    static String findPrefix(int i, final File... files)
    {
        if (files.length == 0)
        {
            return null;
        }
        if (files.length == 1)
        {
            return files[0].getName();
        }
        String prefix = files[0].getName().substring(0, files[0].getName().length() - i);
        if (prefix.isEmpty())
        {
            return prefix;
        }
        for (final File f : files)
        {
            final String name = f.getName();
            if (! prefix.equals(name.substring(0, name.length() - i)))
            {
                i++;
                prefix = null;
                break;
            }
        }
        if (prefix != null)
        {
            return prefix;
        }
        return findPrefix(i, files);
    }

    private static DataTree toTree(final Locale locale, final String node, final Map<?, ?> map, final DataTree tree)
    {
        for (final Entry<?, ?> entry : map.entrySet())
        {
            final String key = entry.getKey().toString();
            if (entry.getValue() instanceof Map)
            {
                DataTree subTree = tree.nodes.get(key);
                if (subTree == null)
                {
                    subTree = new DataTree(key);
                    tree.nodes.put(key, subTree);
                }
                toTree(locale, key, (Map<?, ?>) entry.getValue(), subTree);
            }
            else
            {
                tree.addString(locale, entry.getValue(), key);
            }
        }
        return tree;
    }

    private static class DataTree
    {
        private final String                           name;
        private final Map<String, Map<Locale, Object>> strings;
        private final Map<String, DataTree>            nodes;

        private DataTree(final String name)
        {
            this.name = name;
            this.strings = new HashMap<>(20, .5f);
            this.nodes = new HashMap<>(5, .2f);
        }

        private void put(final String node, final Locale locale, final Object message)
        {
            Map<Locale, Object> map = this.strings.get(node);
            if (map == null)
            {
                map = new HashMap<>(5, .1f);
                this.strings.put(node, map);
            }
            map.put(locale, message);
        }

        private void addString(final Locale locale, final Object message, final String... path)
        {
            if (path.length == 0)
            {
                throw new IllegalArgumentException("Added mesage must have name");
            }
            if (path.length == 1)
            {
                this.put(path[0], locale, message);
                return;
            }
            DataTree prevNode;
            DataTree node = this;
            for (int i = 0; i < (path.length - 1); i++)
            {
                prevNode = node;
                node = node.nodes.get(path[i]);
                if (node == null)
                {
                    node = new DataTree(path[i]);
                    prevNode.nodes.put(path[i], node);
                }
            }
            node.put(path[path.length - 1], locale, message);
        }

        private Messages construct(final Messages root)
        {
            for (final Entry<String, Map<Locale, Object>> entry : this.strings.entrySet())
            {
                root.addMessage(Message.load(entry.getValue()), entry.getKey());
            }
            for (final Entry<String, DataTree> entry : this.nodes.entrySet())
            {
                final MessagePack node = new MessagePack(root, entry.getKey());
                entry.getValue().construct(node);
                root.addNode(node);
            }
            return root;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).toString();
        }
    }
}
