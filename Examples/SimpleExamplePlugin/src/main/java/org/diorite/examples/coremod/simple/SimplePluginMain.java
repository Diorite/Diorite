package org.diorite.examples.coremod.simple;

import java.util.regex.Pattern;

import org.diorite.Diorite;
import org.diorite.command.PluginCommand;
import org.diorite.command.PluginCommandBuilder;
import org.diorite.command.SubCommand;
import org.diorite.examples.coremod.simple.ExampleCommand.ExampleSubCmdCommand;
import org.diorite.plugin.DioritePlugin;
import org.diorite.plugin.FakeDioritePlugin;
import org.diorite.plugin.Plugin;
import org.diorite.plugin.PluginException;

@Plugin(name = "SimpleExamplePlugin", version = "1.0", author = "Diorite", website = "www.diorite.org")
public class SimplePluginMain extends DioritePlugin
{
    private static SimplePluginMain instance;

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
        this.registerCommand();
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info("Hello world in onDisable!");
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
