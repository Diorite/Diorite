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
