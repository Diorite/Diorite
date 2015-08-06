package org.diorite.examples.coremod.simple;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.plugin.CoreMod;
import org.diorite.impl.plugin.DioriteMod;
import org.diorite.plugin.Plugin;

@Plugin(name = "ExampleCoreMod", version = "1.0", author = "Diorite", website = "www.diorite.org")
@CoreMod
public class CoreModMain extends DioriteMod
{
    @Override
    public void onLoad()
    {
        DioriteCore.getInitPipeline().addFirst(this.getName() + "|Test", (core, pipeline, data) -> this.getLogger().info("YeY, example core mod started!"));
        DioriteCore.getStartPipeline().addFirst(this.getName() + "|Test", (core, pipeline, options) -> this.getLogger().info("YeY, example core mod started even more!"));
        DioriteCore.getStartPipeline().addBefore("DioriteCore|Run", this.getName() + "|Test2", (core, pipeline, options) -> this.getLogger().info("Just before run!"));
    }
}
