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

package org.diorite.impl.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import org.diorite.impl.CoreMain;
import org.diorite.plugin.PluginsDirectory;

public class PluginsDirectoryImpl implements PluginsDirectory
{
    private static final String  CACHE_PATTERN_SEP   = " == ";
    private static final Pattern CACHE_PATTERN       = Pattern.compile(CACHE_PATTERN_SEP);
    private final Map<String, String> mainClassCache = new HashMap<>(20);
    private boolean isInitialised;
    private final File directory;

    public PluginsDirectoryImpl(final File directory)
    {
        this.directory = directory;
    }

    public void init() throws IOException
    {
        if (this.isInitialised)
        {
            throw new IllegalStateException("Arleady initialised!");
        }
        final File cache = this.getCacheFile();
        if (cache.exists())
        {
            Files.readAllLines(cache.toPath(), StandardCharsets.UTF_8).forEach(s -> {
                final String[] parts = CACHE_PATTERN.split(s, 2);
                if ((parts != null) && (parts.length == 2))
                {
                    mainClassCache.put(parts[1], parts[0]);
                }
            });
        }
        else
        {
            cache.createNewFile();
        }
        this.isInitialised = true;
        CoreMain.debug("Loaded " + this.mainClassCache.size() + " cached main classes!");
    }

    @Override
    public File getDirectory()
    {
        return this.directory;
    }

    @Override
    public Map<String, String> getMainClassCache()
    {
        this.checkInitialised();
        return ImmutableMap.copyOf(this.mainClassCache);
    }

    @Override
    public void resetMainClassCache()
    {
        this.checkInitialised();
        this.getCacheFile().delete();
        this.mainClassCache.clear();
    }

    public String getCachedClass(final String file)
    {
        this.checkInitialised();
        try
        {
            return this.mainClassCache.get(file);
        } catch (final Exception e)
        {
            CoreMain.debug(e);
            return null;
        }
    }

    public void setCachedClass(final String file, final Class<?> clazz)
    {
        this.checkInitialised();
        try
        {
            this.mainClassCache.put(file, clazz.getName());
        } catch (final Exception e)
        {
            CoreMain.debug(e);
        }
    }

    public void saveCache()
    {
        this.checkInitialised();
        try
        {
            Files.write(this.getCacheFile().toPath(), mainClassCache.entrySet().stream().map(e -> e.getValue() + CACHE_PATTERN_SEP + e.getKey()).collect(Collectors.toList()), StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        } catch (final Exception e)
        {
            CoreMain.debug(e);
        }
    }

    private void checkInitialised()
    {
        if (! this.isInitialised)
        {
            throw new IllegalStateException();
        }
    }
}
