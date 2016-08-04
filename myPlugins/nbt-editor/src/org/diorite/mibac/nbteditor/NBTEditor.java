package org.diorite.mibac.nbteditor;

import static org.diorite.mibac.nbteditor.NBTEditor.AUTHOR;
import static org.diorite.mibac.nbteditor.NBTEditor.NAME;
import static org.diorite.mibac.nbteditor.NBTEditor.VERSION;


import org.diorite.cfg.messages.PluginMessages;
import org.diorite.plugin.DioritePlugin;
import org.diorite.plugin.Plugin;

@Plugin(name = NAME, version = VERSION, author = AUTHOR)
public class NBTEditor extends DioritePlugin
{
    public static final String NAME    = "NBTEditor";
    public static final String VERSION = "1.0";
    public static final String AUTHOR  = "mibac138";

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
}
