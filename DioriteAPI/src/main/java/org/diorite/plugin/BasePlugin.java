package org.diorite.plugin;

public interface BasePlugin
{
    /**
     * Invoked on load of plugin.
     */
    void onLoad();

    /**
     * Invoked when enabling plugin, always after {@link #onLoad()}
     */
    void onEnable();

    /**
     * Invoked when disabling plugin.
     */
    void onDisable();

    /**
     * Returns name of plugin, can't be null.
     *
     * @return name of plugin.
     */
    String getName();

    /**
     * Returns version of plugin, can't be null.
     *
     * @return version of plugin.
     */
    String getVersion();

    /**
     * Returns author/s of plugin, can't be null, may contains custom text.
     *
     * @return author.s of plugin.
     */
    String getAuthor();

    /**
     * Returns description of plugin, can't be null.
     *
     * @return description of plugin.
     */
    String getDescription();

    /**
     * Returns website of plugin, can't be null.
     *
     * @return website of plugin.
     */
    String getWebsite();

    /**
     * Returns PluginLoaded for this plugin.
     *
     * @return PluginLoaded for this plugin.
     */
    PluginLoader getPluginLoader();

    /**
     * Method invoked on plugin init, do not override.
     *
     * @param classLoader  used class loader.
     * @param pluginLoader used plugin loader.
     * @param name         name of plugin.
     * @param version      version of plugin.
     * @param author       author/s of plugin.
     * @param description  description of plugin.
     * @param website      webiste of plugin.
     */
    void init(final PluginClassLoader classLoader, final PluginLoader pluginLoader, final String name, final String version, final String author, final String description, final String website);

    /**
     * Set if plugin is enabled.
     *
     * @param enabled if plugin should be enabled.
     */
    void setEnabled(boolean enabled);

    /**
     * Returns true if plugin is already initialised.
     *
     * @return true if plugin is already initialised.
     */
    boolean isInitialised();

    /**
     * Returns true if plugin is already enabled.
     *
     * @return true if plugin is already enabled.
     */
    boolean isEnabled();

    /**
     * Return true if this is core mod for diorite.
     *
     * @return true if this is core mod for diorite.
     */
    default boolean isCoreMod()
    {
        return false;
    }
}
