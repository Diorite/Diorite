/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;

import org.diorite.command.Argument;
import org.diorite.command.CommandHolder;
import org.diorite.command.CommandObject;
import org.diorite.command.CommandManager;
import org.diorite.command.annotation.Command;
import org.diorite.command.types.HeldCommand;
import org.diorite.command.types.PluginCommand;
import org.diorite.plugin.Plugin;

public class DioriteCommandManager implements CommandManager
{

    private final BiMap<CommandObject, String> commandMap = HashBiMap.create();
    private final Plugin implementingPlugin;

    public DioriteCommandManager(Plugin implementingPlugin)
    {
        this.implementingPlugin = implementingPlugin;
    }

    @Override
    public Plugin getImplementingPlugin()
    {
        return implementingPlugin;
    }

    @Override
    public void addCommand(CommandObject command)
    {
        commandMap.put(command, command.getName());
        for(String name : command.getAliases())
        {
            commandMap.put(command, name);
        }
    }

    @Override
    public void addCommands(CommandHolder holder)
    {
        Method[] methods = holder.getClass().getMethods();
        for(Method method : methods)
        {
            if(method.isAnnotationPresent(Command.class))
            {
                Command annotation = method.getAnnotation(Command.class);
                CommandObject newCommand = new HeldCommand(annotation.value()[0], method);

                List<Class<? extends Argument<?>>> arguments = ImmutableList.copyOf(annotation.arguments());
                List<Argument<?>> argumentList = new ArrayList<>();
                for(Class<? extends Argument<?>> arg : arguments)
                {
                    try
                    {
                        argumentList.add(arg.getConstructor().newInstance());
                    }
                    catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e)
                    {
                        //error logging
                    }
                }
                newCommand.setArguments(argumentList);

                if(annotation.value().length == 0)
                {
                    newCommand.setName(method.getName());
                }
                else
                {
                    newCommand.setName(annotation.value()[0]);
                }

                List<String> aliases = Arrays.asList(annotation.value());
                aliases.remove(0);
                newCommand.setAliases(aliases);

                newCommand.setDescription(annotation.description());

                if(annotation.usage().equals(""))
                {
                    StringBuilder builder = new StringBuilder("/");
                    builder.append(newCommand.getName());
                    builder.append(" ");
                    int i = 0;
                    for(Argument<?> argument : argumentList)
                    {
                        boolean optional = false;
                        for(Integer optionalInt : annotation.optional())
                        {
                           if(optionalInt.equals(i))
                               optional = true;
                        }
                        if(optional)
                        {
                            builder.append("[");
                            builder.append(argument.toString().toLowerCase());
                            builder.append("]");
                        }
                        else
                        {
                            builder.append("<");
                            builder.append(argument.toString().toLowerCase());
                            builder.append(">");
                        }
                    }
                    newCommand.setUse(builder.toString());
                }
                else
                {
                    newCommand.setUse(annotation.usage());
                }

                newCommand.setOptional(annotation.optional());
                newCommand.register(this);

                commandMap.put(newCommand, newCommand.getName());
                for(String name : newCommand.getAliases())
                {
                    commandMap.put(newCommand, name);
                }
            }
        }
    }

    @Override
    public PluginCommand registerCommand(String name, Plugin plugin)
    {
        return new PluginCommand(name, plugin);
    }

    @Override
    public void removeCommand(CommandObject command)
    {
        commandMap.remove(command);
    }

    @Override
    public void removeCommands(CommandHolder holder)
    {
        Method[] methods = holder.getClass().getMethods();
        for(Method method : methods)
        {
            if(method.isAnnotationPresent(Command.class))
            {
                Command annotation = method.getAnnotation(Command.class);
                commandMap.inverse().remove(annotation.value()[0]);
            }
        }
    }

    @Override
    public void removeCommand(String commandName)
    {
        commandMap.inverse().remove(commandName);
    }

    @Override
    public CommandObject getCommandFromName(String commandName)
    {
        return commandMap.inverse().get(commandName);
    }
}
