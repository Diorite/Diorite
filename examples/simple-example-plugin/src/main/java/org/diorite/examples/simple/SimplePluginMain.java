/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.examples.simple;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.diorite.Diorite;
import org.diorite.cfg.Configuration;
import org.diorite.command.PluginCommand;
import org.diorite.command.PluginCommandBuilder;
import org.diorite.command.SubCommand;
import org.diorite.examples.simple.ExampleCommand.ExampleSubCmdCommand;
import org.diorite.plugin.DioritePlugin;
import org.diorite.plugin.FakeDioritePlugin;
import org.diorite.plugin.Plugin;
import org.diorite.plugin.PluginException;

@Plugin(name = "SimpleExamplePlugin", version = "1.0", author = "Diorite", website = "www.diorite.org")
public class SimplePluginMain extends DioritePlugin
{
    private static SimplePluginMain instance;
    private static MyCfg            cfg;

    @Override
    public void onLoad()
    {
        instance = this;
        this.getLogger().info("Hello world in onLoad!");
        this.registerFakePlugin();
    }

    @Override
    public void onEnable()
    {
        this.getLogger().info("Hello world in onEnable!");
        // TODO: change to plugin folder method
        try
        {
            cfg = Configuration.loadConfigFile(new File("plugins/SimpleExamplePlugin/cfg.yml"), MyCfg.class);
        } catch (final IOException e)
        {
            this.getLogger().warn("Can't load config file.");
            e.printStackTrace();
        }
        this.registerCommand();

        this.getLogger().info("Loaded config file: " + cfg);
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info("Hello world in onDisable!");
        try
        {
            Configuration.saveConfigFile(new File("plugins/SimpleExamplePlugin/cfg.yml"), MyCfg.class, cfg);
        } catch (final IOException e)
        {
            this.getLogger().warn("Can't save config file.");
            e.printStackTrace();
        }
    }

    public static SimplePluginMain getInstance()
    {
        return instance;
    }

    private void registerCommand()
    {
        Diorite.getCommandMap().registerCommand(Diorite.getCore().createCommand(this, "helloworld").alias("hello").executor((sender, command, label, matchedPattern, args) -> {
            sender.sendSimpleColoredMessage("&cHello world!", "&a#OnlyDiorite");
        }).build());

        final PluginCommandBuilder builder = Diorite.createCommand(this, "example");
        builder.pattern(Pattern.compile("example(|:?<superArg>(\\d))")); // you can now use command like /example:4, /example:1 and still just /example
        builder.executor(new ExampleCommand());
        final PluginCommand cmd = builder.build();
        final SubCommand subCommand = cmd.registerSubCommand("subcmd", new ExampleSubCmdCommand()); // you can use patterns here too.
//         you can register sub commands for that subCommand object too.
//        subCommand.registerSubCommand()

    }

    private void registerFakePlugin()
    {
        //noinspection AnonymousInnerClassMayBeStatic
        final FakeDioritePlugin fake = new FakeDioritePlugin(this, "My-sub-plugin", "0.1", "NorthPL93")
        {
            @Override
            public void onEnable()
            {
                this.getLogger().info("Some message from sub plugin.");
            }
        };

        try
        {
            Diorite.getPluginManager().injectPlugin(fake);
        } catch (final PluginException e)
        {
            e.printStackTrace();
        }
    }
}
