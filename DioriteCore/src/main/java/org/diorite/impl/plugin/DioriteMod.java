package org.diorite.impl.plugin;

import org.diorite.impl.DioriteCore;
import org.diorite.plugin.DioritePlugin;

@SuppressWarnings("RedundantMethodOverride")
public class DioriteMod extends DioritePlugin
{
    /**
     * Invokes very early, in constructor of {@link DioriteCore} <br>
     * It is possible to change init pipeline here: {@link DioriteCore#getInitPipeline()}
     * that contains all actions done when loading basics of diorite.
     */
    @Override
    public void onLoad()
    {
    }

    /**
     * Invokes before start method, still before creating logger, worlds or loading plugins. <br>
     * It is possible to change start pipeline here: {@link DioriteCore#getStartPipeline()} ()}
     * that contains all actions done when starting diorite.
     */
    @Override
    public void onEnable()
    {
    }

    @Override
    public boolean isCoreMod()
    {
        return true;
    }
}
