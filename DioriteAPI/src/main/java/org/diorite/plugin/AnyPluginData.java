package org.diorite.plugin;

interface AnyPluginData
{

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
     * Returns log prefix of plugin, can't be null.
     *
     * @return prefix of plugin.
     */
    String getPrefix();

    /**
     * Returns author/s of plugin, can't be null, may contains custom text.
     *
     * @return author/s of plugin.
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
}
