package org.diorite.impl.plugin;

import java.io.File;

import org.diorite.plugin.BasePlugin;
import org.diorite.plugin.DioritePlugin;
import org.diorite.plugin.PluginException;
import org.diorite.plugin.PluginLoader;

public class FakePluginLoader implements PluginLoader
{
    public static final String FAKE_PLUGIN_SUFFIX = "$fake";

    @Override
    public DioritePlugin loadPlugin(final File file) throws PluginException
    {
        return null; // unused
    }

    @Override
    public void enablePlugin(final BasePlugin plugin) throws PluginException
    {
        plugin.setEnabled(true);
    }

    @Override
    public void disablePlugin(final BasePlugin plugin) throws PluginException
    {
        plugin.setEnabled(false);
    }

    @Override
    public String getFileSuffix()
    {
        return FAKE_PLUGIN_SUFFIX;
    }
}
