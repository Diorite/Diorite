package org.diorite.plugin;

import java.io.File;

public interface PluginLoader
{
    void loadPlugin(File file);
    void enablePlugin(String name);
    void disablePlugin(String name);
    String getFileExtension();
}
