package org.diorite.plugin;

public interface BasePlugin
{
    void onLoad();
    void onEnable();
    void onDisable();
    String getName();
    String getVersion();
    String getAuthor();
    String getDescription();
    String getWebsite();
    PluginLoader getPluginLoader();
    void init(final PluginClassLoader classLoader, final PluginLoader pluginLoader, final DioritePlugin instance, final String name, final String version, final String author, final String description, final String website);
    void setEnabled(boolean enabled);
    boolean isInitialised();
    boolean isEnabled();
}
