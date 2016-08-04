package org.diorite.examples.nbteditor;

import static org.diorite.examples.nbteditor.NBTEditor.AUTHOR;
import static org.diorite.examples.nbteditor.NBTEditor.NAME;
import static org.diorite.examples.nbteditor.NBTEditor.VERSION;
import static org.diorite.examples.nbteditor.NBTEditor.WEBSITE;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.messages.PluginMessages;
import org.diorite.plugin.DioritePlugin;
import org.diorite.plugin.Plugin;

@Plugin(name = NAME, version = VERSION, author = AUTHOR, website = WEBSITE)
public class NBTEditor extends DioritePlugin
{
    public static final String NAME    = "NBTEditor";
    public static final String VERSION = "1.0";
    public static final String AUTHOR  = "mibac138";
    public static final String WEBSITE = "diorite.org";

    private PluginMessages language;
    private NBTCommand     commands;

    @Override
    public void onEnable()
    {
        this.getDataFolder().mkdir();
        this.getLanguageFolder().mkdir();
        this.reloadLanguage();
        this.registerCommands();
    }

    public PluginMessages getLanguage()
    {
        return this.language;
    }

    public void reloadLanguage()
    {
        // TODO loading messages
        this.language = new PluginMessages(this, "lang_");
    }

    private void registerCommands()
    {
        this.commands = new NBTCommand(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("language", this.language).append("commands", this.commands).toString();
    }
}
