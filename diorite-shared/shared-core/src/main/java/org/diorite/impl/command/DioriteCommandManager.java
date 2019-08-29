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

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import org.diorite.impl.command.argument.SpecialArguments;
import org.diorite.command.argument.AnnotationHandler;
import org.diorite.command.argument.Argument;
import org.diorite.command.CommandHolder;
import org.diorite.command.CommandObject;
import org.diorite.command.CommandManager;
import org.diorite.command.annotation.Command;
import org.diorite.command.argument.ArgumentBuilder;
import org.diorite.command.argument.ArgumentCommandBuilder;
import org.diorite.command.argument.ArgumentManager;
import org.diorite.command.argument.Arguments;
import org.diorite.command.types.HeldCommand;
import org.diorite.command.types.PluginCommand;
import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.plugin.Plugin;

import sun.reflect.annotation.AnnotationType;

public class DioriteCommandManager implements CommandManager
{

    private final BiMap<CommandObject, String> commandMap = HashBiMap.create();
    private final Plugin implementingPlugin;
    private final ArgumentManager manager;

    public DioriteCommandManager(Plugin implementingPlugin, ArgumentManager manager)
    {
        this.implementingPlugin = implementingPlugin;
        this.manager = manager;
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
    public void addCommands(CommandHolder holder, Plugin plugin)
    {
        Method[] methods = holder.getClass().getMethods();
        for(Method method : methods)
        {
            if(method.isAnnotationPresent(Command.class))
            {
                Command commandAnnotation = method.getAnnotation(Command.class);
                String commandName;
                if(commandAnnotation.value().length == 0)
                {
                    commandName = method.getName();
                }
                else
                {
                    commandName = commandAnnotation.value()[0];
                }

                ArgumentCommandBuilder builder = createCommandWithArgs(commandName, plugin);
                int paramCount = method.getParameterCount();
                AnnotatedType[] params = method.getAnnotatedParameterTypes();

                SpecialArguments specialArgs = SpecialArguments.create();
                int paramIndex = 0;
                for(AnnotatedType param : params)
                {
                    specialArgs.parse(paramIndex++, param);
                }

                for(int i = 0; i < paramCount; i++)
                {
                    if(specialArgs.isSpecial(i)) continue;
                    AnnotatedType param = params[i];
                    ArgumentBuilder argBuilder = Arguments.of(param.getType());

                    for(Annotation annotation : param.getAnnotations())
                    {
                        AnnotationHandler handler = manager.getAnnotationHandler(annotation);
                        if(handler == null) continue;
                        argBuilder.apply(handler);
                    }
                    builder.withArgument(argBuilder);
                }

                MethodInvoker methodInvoker = new MethodInvoker(method);
                methodInvoker.ensureAccessible();
                MethodHandle methodHandle = methodInvoker.getHandle();

                builder.withExecutor(((sender, command, alias, args) ->
                {
                    Object[] arguments = new Object[paramCount];
                    specialArgs.provide(arguments, sender, command, alias);
                    for(int i = 0; i < paramCount; i++)
                    {
                        Object argument = arguments[i];
                        if(argument != null) continue;
                        arguments[i] = args.get(i);
                    }
                    try
                    {
                        return (Boolean) methodHandle.invokeWithArguments(arguments);
                    }
                    catch(Throwable throwable)
                    {
                        //TODO: error logging
                    }
                    return false;
                }));
                builder.register();
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

    @Override
    public ArgumentCommandBuilder createCommandWithArgs(String name, Plugin plugin)
    {
        return new ArgumentCommandBuilder(name, plugin, this);
    }
}
