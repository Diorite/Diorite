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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.SimpleConfigManager;
import org.diorite.cfg.messages.Messages.MessagePack;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

/**
 * Class for loading messages from selected files.
 */
public class MessageLoader
{
    private static final Function<Locale, Reader> TO_NULL = l -> null;

    /**
     * languages to be used by this message loader.
     */
    protected final Locale[] languages;

    private final SimpleConfigManager yaml = new SimpleConfigManager();

    /**
     * Construct new message loader for given array of languages.
     *
     * @param languages languages to be used by this message loader.
     */
    public MessageLoader(final Locale... languages)
    {
        this.languages = languages;
    }

    /**
     * Returns languages used by this message loader.
     *
     * @return languages used by this message loader.
     */
    public Locale[] getLanguages()
    {
        return this.languages.clone();
    }

    /**
     * Load messages from given folder with language files, each file means one language so they need contains language code at the end, like: <br>
     * myLangFile_pl-PL.yml<br>
     * myLangFile_en-US.yml<br>
     * etc... <br>
     *
     * @param predicate             files need match this predictate to be loaded.
     * @param func                  this function should get {@link Locale} in which are messages in file written.
     * @param folder                folder where language files will be created/loaded
     * @param defaultValuesFunction function that returns Reader with default values for given language.
     *
     * @return loaded messages instance.
     */
    public Messages loadMessages(final Predicate<File> predicate, final Function<File, Locale> func, final File folder, final Function<Locale, Reader> defaultValuesFunction)
    {
        return this.loadMessages(predicate, func, new Messages(this.languages), folder, defaultValuesFunction);
    }

    /**
     * Load messages from given folder with language files, each file means one language so they need contains language code at the end, like: <br>
     * myLangFile_pl-PL.yml<br>
     * myLangFile_en-US.yml<br>
     * etc... <br>
     *
     * @param predicate             files need match this predictate to be loaded.
     * @param func                  this function should get {@link Locale} in which are messages in file written.
     * @param node                  messages node to use, usefull when you want load messages to one of subnodes.
     * @param folder                folder where language files will be created/loaded
     * @param defaultValuesFunction function that returns Reader with default values for given language.
     *
     * @return this same messages instance as given after loading messages.
     */
    public Messages loadMessages(final Predicate<File> predicate, final Function<File, Locale> func, final Messages node, final File folder, final Function<Locale, Reader> defaultValuesFunction)
    {
        final DataTree result = new DataTree("");
        if (! folder.exists())
        {
            folder.mkdirs();
        }
        else if (! folder.isDirectory())
        {
            throw new IllegalArgumentException("Given file isn't a folder, can't load language files from: " + folder.toPath());
        }
        final File[] files = folder.listFiles();
        assert files != null;
        final DataTree masterDefault = new DataTree("");
        final Map<Locale, DataTree> defaults = new HashMap<>(files.length + 1);
        {
            for (final Locale locale : this.languages)
            {
                try (final Reader reader = defaultValuesFunction.apply(locale))
                {
                    if (reader != null)
                    {
                        result.putDefaults(this.toTree(locale, "", this.yaml.load(Map.class, reader), new DataTree("")));
                    }
                } catch (final IOException e)
                {
                    e.printStackTrace();
                }
                defaults.put(locale, masterDefault);
            }
        }
        for (final File file : files)
        {
            if (! predicate.test(file))
            {
                continue;
            }
            Locale locale = func.apply(file);
            if (locale == null)
            {
                locale = this.languages[0];
            }
            {
                final DataTree dataTree = defaults.get(locale);
                if (dataTree == null)
                {
                    continue;
                }
            }
            final Map<?, ?> loaded;
            try
            {
                loaded = this.yaml.load(Map.class, new FileInputStream(file));
            } catch (final IOException e)
            {
                e.printStackTrace();
                continue;
            }
            this.toTree(locale, "", loaded, result);
        }
        return result.construct(node);
    }

    /**
     * Load messages from given folder with language files, each file means one language so they need contains language code at the end, like: <br>
     * myLangFile_pl-PL.yml<br>
     * myLangFile_en-US.yml<br>
     * etc... <br>
     * Files not starting from selected prefix will be skipped.
     *
     * @param prefix          prefix of each file.
     * @param folder          folder where language files will be created/loaded
     * @param clazz           target of {@link Class#getResourceAsStream(String)} method.
     * @param resourcesFolder location of source files in .jar.
     *
     * @return loaded messages instance.
     */
    @SuppressWarnings("resource")
    public Messages loadMessages(final String prefix, final File folder, final Class<?> clazz, final String resourcesFolder)
    {
        if (prefix == null)
        {
            throw new IllegalArgumentException("Prefix can't be null, can't load resources from: " + folder.toPath());
        }
        final String validResourcesFolder;
        if (resourcesFolder.endsWith("/"))
        {
            validResourcesFolder = resourcesFolder;
        }
        else
        {
            validResourcesFolder = resourcesFolder + "/";
        }
        return this.loadMessages(f -> f.getName().startsWith(prefix) && f.getName().endsWith(".yml"), f -> this.getLocale(f.getName().substring(prefix.length(), f.getName().lastIndexOf('.'))), folder, l -> getInputStreamReader(prefix, clazz, validResourcesFolder, l));
    }

    /**
     * Load messages from given folder with language files, each file means one language so they need contains language code at the end, like: <br>
     * myLangFile_pl-PL.yml<br>
     * myLangFile_en-US.yml<br>
     * etc... <br>
     * Files not starting from selected prefix will be skipped.
     *
     * @param prefix                prefix of each file.
     * @param folder                folder where language files will be created/loaded
     * @param defaultValuesFunction function that returns Reader with default values for given language.
     *
     * @return loaded messages instance.
     */
    public Messages loadMessages(final String prefix, final File folder, final Function<Locale, Reader> defaultValuesFunction)
    {
        if (prefix == null)
        {
            throw new IllegalArgumentException("Prefix can't be null, can't load resources from: " + folder.toPath());
        }
        return this.loadMessages(f -> f.getName().startsWith(prefix) && f.getName().endsWith(".yml"), f -> this.getLocale(f.getName().substring(prefix.length(), f.getName().lastIndexOf('.'))), folder, defaultValuesFunction);
    }

    /**
     * Load messages from given folder with language files, each file means one language so they need contains language code at the end, like: <br>
     * myLangFile_pl-PL.yml<br>
     * myLangFile_en-US.yml<br>
     * etc...
     *
     * @param folder          folder where language files will be created/loaded
     * @param clazz           target of {@link Class#getResourceAsStream(String)} method.
     * @param resourcesFolder location of source files in .jar.
     *
     * @return loaded messages instance.
     */
    @SuppressWarnings("resource")
    public Messages loadMessages(final File folder, final Class<?> clazz, final String resourcesFolder)
    {
        final String prefix = this.findPrefix(0, folder.listFiles());
        final String validResourcesFolder;
        if (resourcesFolder.endsWith("/"))
        {
            validResourcesFolder = resourcesFolder;
        }
        else
        {
            validResourcesFolder = resourcesFolder + "/";
        }
        return this.loadMessages(prefix, folder, l -> getInputStreamReader(prefix, clazz, validResourcesFolder, l));
    }

    /**
     * Load messages from given folder with language files, each file means one language so they need contains language code at the end, like: <br>
     * myLangFile_pl-PL.yml<br>
     * myLangFile_en-US.yml<br>
     * etc...
     *
     * @param folder folder where language files will be created/loaded
     *
     * @return loaded messages instance.
     */
    public Messages loadMessages(final File folder)
    {
        return this.loadMessages(this.findPrefix(0, folder.listFiles()), folder, TO_NULL);
    }

    /**
     * Load messages from given folder with language files, each file means one language so they need contains language code at the end, like: <br>
     * myLangFile_pl-PL.yml<br>
     * myLangFile_en-US.yml<br>
     * etc...
     *
     * @param folder                folder where language files will be created/loaded
     * @param defaultValuesFunction function that returns Reader with default values for given language.
     *
     * @return loaded messages instance.
     */
    public Messages loadMessages(final File folder, final Function<Locale, Reader> defaultValuesFunction)
    {
        return this.loadMessages(this.findPrefix(0, folder.listFiles()), folder, defaultValuesFunction);
    }

    /**
     * Saves all messages from given messages object to given folder, each language in separate folder like that:<br>
     * prefixpl-PL.yml<br>
     * prefixen-US.yml<br>
     * etc...
     *
     * @param messages message object to save.
     * @param folder   folder to save all language files.
     * @param prefix   prefix of each file, use null or empty string to save without prefix.
     */
    public void saveMessages(final Messages messages, final File folder, String prefix)
    {
        if (prefix == null)
        {
            prefix = "";
        }
        folder.mkdirs();
        final Map<Locale, Map<String, Object>> map = messages.toMap(new HashMap<>(this.languages.length), this.languages[0]);
        for (final Entry<Locale, Map<String, Object>> entry : map.entrySet())
        {
            try
            {
                this.yaml.save(new File(folder, prefix + entry.getKey().toLanguageTag() + ".yml"), entry.getValue());
            } catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private static InputStreamReader getInputStreamReader(final String prefix, final Class<?> clazz, final String resourcesFolder, final Locale locale)
    {
        final InputStreamReader inputStreamReader = inputStreamToReader(clazz.getResourceAsStream(resourcesFolder + prefix + locale.toLanguageTag() + ".yml"));
        if (inputStreamReader == null)
        {
            return inputStreamToReader(clazz.getResourceAsStream(resourcesFolder + locale.toLanguageTag() + ".yml"));
        }
        return inputStreamReader;
    }

    /**
     * Wraps inputStream in {@link InputStreamReader} so it can be used in loadMessages methods. <br>
     * Returns null if null value is given.
     *
     * @param inputStream inputStream to wrap.
     *
     * @return inputStream wrapped in {@link InputStreamReader} or null.
     */
    public static InputStreamReader inputStreamToReader(final InputStream inputStream)
    {
        if (inputStream == null)
        {
            return null;
        }
        return new InputStreamReader(inputStream);
    }

    private Locale getLocale(final String str)
    {
        final Locale locale = Locale.forLanguageTag(str);
        if (locale.getDisplayName().isEmpty())
        {
            return new Locale(str);
        }
        return locale;
    }

    String findPrefix(int i, final File... files)
    {
        if ((files == null) || (files.length == 0))
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
        return this.findPrefix(i, files);
    }

    private DataTree toTree(final Locale locale, final String node, final Map<?, ?> map, final DataTree tree)
    {
        if (map == null)
        {
            return tree;
        }
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
                this.toTree(locale, key, (Map<?, ?>) entry.getValue(), subTree);
            }
            else
            {
                tree.addString(locale, entry.getValue(), key);
            }
        }
        return tree;
    }

    private class DataTree
    {
        private final String                           name;
        private final Map<String, Map<Locale, Object>> strings;
        private final Map<String, DataTree>            nodes;

        private DataTree(final String name)
        {
            this.name = name;
            this.strings = new CaseInsensitiveMap<>(20, .5f);
            this.nodes = new CaseInsensitiveMap<>(5, .2f);
        }

        private DataTree putDefaults(final DataTree defaults)
        {
            for (final Entry<String, DataTree> entry : defaults.nodes.entrySet())
            {
                final DataTree value = entry.getValue();
                DataTree oldTree = this.nodes.get(value.name);
                if (oldTree == null)
                {
                    oldTree = new DataTree(value.name);
                    this.nodes.put(entry.getKey(), oldTree);
                }
                oldTree.putDefaults(value);
            }
            for (final Entry<String, Map<Locale, Object>> entry : defaults.strings.entrySet())
            {
                final Map<Locale, Object> value = entry.getValue();
                for (final Entry<Locale, Object> localeEntry : value.entrySet())
                {
                    this.put(entry.getKey(), localeEntry.getKey(), localeEntry.getValue());
                }
            }
            return this;
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("languages", this.languages).toString();
    }
}