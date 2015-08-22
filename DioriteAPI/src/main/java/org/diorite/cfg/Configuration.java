package org.diorite.cfg;

import java.io.File;
import java.io.IOException;

import org.diorite.cfg.system.Template;
import org.diorite.cfg.system.TemplateCreator;
import org.diorite.utils.DioriteUtils;

public class Configuration
{
    /**
     * @param f
     * @param clazz
     * @param defaults
     * @param <T>
     *
     * @return
     *
     * @throws IOException
     */
    public <T> T loadConfigFile(final File f, final Class<T> clazz, final T defaults) throws IOException
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

    public <T> T loadConfigFile(final File f, final Class<T> clazz) throws IOException
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
}
