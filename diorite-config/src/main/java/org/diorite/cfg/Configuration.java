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

package org.diorite.cfg;

import java.io.File;
import java.io.IOException;

import org.diorite.cfg.system.Template;
import org.diorite.cfg.system.TemplateCreator;
import org.diorite.utils.DioriteUtils;

public final class Configuration
{
    private Configuration()
    {
    }

    /**
     * Method to load config class for selected file.
     *
     * @param f        file to use.
     * @param clazz    class of config.
     * @param defaults default values.
     * @param <T>      type of config class.
     *
     * @return loaded config class.
     *
     * @throws IOException if any file operation throw it.
     */
    public static <T> T loadConfigFile(final File f, final Class<T> clazz, final T defaults) throws IOException
    {
        T result;
        final Template<T> cfgTemp = TemplateCreator.getTemplate(clazz);
        if (f.exists())
        {
            result = cfgTemp.load(f);
            if ((result == null) && (defaults != null))
            {
                result = cfgTemp.fillDefaults(defaults);
            }
        }
        else
        {
            result = cfgTemp.fillDefaults(defaults);

            DioriteUtils.createFile(f);
        }
        cfgTemp.dump(f, result, false);
        return result;
    }

    /**
     * Method to load config class for selected file.
     *
     * @param f     file to use.
     * @param clazz class of config.
     * @param <T>   type of config class.
     *
     * @return loaded config class.
     *
     * @throws IOException if any file operation throw it.
     */
    public static <T> T loadConfigFile(final File f, final Class<T> clazz) throws IOException
    {
        T result;
        final Template<T> cfgTemp = TemplateCreator.getTemplate(clazz);
        if (f.exists())
        {
            result = cfgTemp.load(f);
            if (result == null)
            {
                result = cfgTemp.fillDefaults(null);
            }
        }
        else
        {
            result = cfgTemp.fillDefaults(null);

            DioriteUtils.createFile(f);
        }
        cfgTemp.dump(f, result, false);
        return result;
    }

    /**
     * Saves config class to selected file.
     *
     * @param f      file to use.
     * @param clazz  class of config.
     * @param object config object to save.
     * @param <T>    type of config class.
     *
     * @throws IOException if any file operation throw it.
     */
    public static <T> void saveConfigFile(final File f, final Class<T> clazz, final T object) throws IOException
    {
        final Template<T> cfgTemp = TemplateCreator.getTemplate(clazz);
        if (! f.exists())
        {
            DioriteUtils.createFile(f);
        }
        cfgTemp.dump(f, object, false);
    }
}
