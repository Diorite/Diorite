package org.diorite.plugin;

import java.io.File;

public interface PluginLoader
{
    PluginMainClass loadPlugin(File file) throws PluginException;
    void enablePlugin(String name) throws PluginException;
    void disablePlugin(String name) throws PluginException;
    String getFileExtension();
}
